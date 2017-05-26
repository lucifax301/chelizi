/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Order",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

/*------------------------------------------订单分页数据及其查询-----------------------------------------------------*/	
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	
	$s.orderId = getQueryString("orderId");
	$s.old_placeInfo = getChineseQueryString("placeInfo");
	$s.old_lae = getQueryString("lae");
	$s.old_lge = getQueryString("lge");
	$s.pageSize=10;    //每页显示-显示条数
	$s.stuMobile = ''; //数据查询-手机号
	$s.isVip = '';//特约教练
	$s.dltype = ''; //学车类型

	/*模拟数据*/
	$s.data={
		pages:10,
		total:100,
		pageSize:10,
		pageNo:1,
		dataList:[
			{orderId:'0121545wfewefwefwvrt0rg',studentId:25,studentName:"小张",coachId:1151,coachName:"张教练",payment:0},
			{orderId:'15616516egergerferferfer',studentId:26,studentName:"小张",coachId:1152,coachName:"张教练",payment:1},
			{orderId:'216sefewsfs516516eferfer',studentId:27,studentName:"小张",coachId:1153,coachName:"张教练",payment:0},
		]
	}; 
	//或得的数据列表
	$s.datas=$s.data.dataList;
	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'orderId'});

	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				$s.total=DATA.total;
				/*数据渲染页面*/
				$s.datas=DATA.dataList;
				console.log($s.datas);
				/*全选与取消全选*/
				Selects.selects({datas:$s.datas,whichId:'orderId'});
				$s.$apply();
				/*冒泡弹出太长的文字*/
				topLongText();
				//分页请求
				new Page({
					parent:$("#copot-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:DATA.total, 
				}); //分页请求完毕
			}
		});
	};	
	$s.getDataList();
	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"order/schedule/coaches",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),			    
			    "orderId": $s.orderId,
			    "dltype": $s.dltype,				
	            'isVip':$s.isVip,//调度状态：0-正常；1-已成功；2-已取消；3-已超时；-1-全部
			}
		};
		return json;
	};

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getDataList();
	}

	
	
	/*按所调度状态筛选列表数据*/
	$s.getDataisVip=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.isVip=type;
		$s.getDataList();
	}
	$s.getDatadltype=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.dltype=type;
		$s.getDataList();
	}
	
	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}
	
	
	//关闭弹出层
	$(".closeAlert").click(function(){
		$(this).parents("div.alert_address").fadeOut("fast");
	});
	//点击指派教练事件
	$s.appoint = function(id){
		console.log($s.chelist);
		if(!$s.chelist){
			Layer.alert({width:300,height:150,type:"msg",title:"未选择教练",header:"错误"});
			return
		}
		$s.address_btn_lock = false;
		$s.address = $s.old_placeInfo;
		$s.lge = $s.old_lge;
		$s.lae = $s.old_lae;
		$('.alert_address').fadeIn('fast');
		$("#alert_address").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");
		var watch = $s.$watch('address',function(newValue, scope){
			if(newValue == $s.old_placeInfo){return}
		   	console.log(newValue);
		   	if(newValue == $s.address_chelock){return}
		   	$s.address_btn_lock = false;
		   	// 百度地图API功能
		   	
			var map = new BMap.Map("l-map");        
			map.centerAndZoom(new BMap.Point($s.old_lge, $s.old_lge), 16);
			var options = {
				onSearchComplete: function(results){
					console.log(results);
					// 判断状态是否正确
					if (local.getStatus() == BMAP_STATUS_SUCCESS){
						$s.address_watch = true;
						$s.address_list = results.wr;
						$s.$apply();
					}
				}
			};
			var local = new BMap.LocalSearch(map, options);
			var point = new BMap.Point($s.old_lge,$s.old_lae);
			var gc = new BMap.Geocoder();
			gc.getLocation(point, function(rs){
			   	var addComp = rs.addressComponents;
			   	$s.thecity = addComp.city;
			  	local.search(newValue+$s.thecity);
				console.log(newValue+$s.thecity);
			});
			
		});
		$s.che_address = function(i){
			$s.address_btn_lock = true;
			//赋值
			$s.address = $s.address_list[i].title;
			$s.lae = $s.address_list[i].point.lat;
			$s.lge = $s.address_list[i].point.lng;
			$s.address_chelock = $s.address_list[i].title;
			$s.address_watch = false;
		}
		$s.address_btn =function(){
			if($s.address_btn_lock == true || $s.address == $s.old_placeInfo){
				$.AJAX({
					type:"post",
					url:config.basePath+"order/schedule/coach",
					data:{
						'orderId':  $s.orderId,//订单号
			            'coachId' : $s.chelist,
			            'placeName':$s.address,
			            'placeLge':$s.lge,
			            'placeLae' :$s.lae,
					},
					success:function(data){
						console.log(data);
						$("div.alert_address").fadeOut("fast");//关闭弹层
						Layer.miss({width:250,height:90,title:"指派成功",closeMask:true});
						setTimeout(function(){
							location.href = 'order-attemper.html';
						},2000)
					}
				});
			}else{
				Layer.alert({width:300,height:150,type:"msg",title:"请选择地址",header:"错误"});
				return;
			}
		}
	}
	
}]);