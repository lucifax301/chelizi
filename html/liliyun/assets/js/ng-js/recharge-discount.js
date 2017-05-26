/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Recharge",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	/*-----------------------------------------查询数据列表----------------------------------------------------*/
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.vtype="";  //用户类型
	$s.phoneNum="";  //手机号
    $s.vstate="";    //按审核状态筛选
    $s.utype=1;//默认为学员充值
	$s.searchType="mobile";    //默认搜索字段
	$s.search="";
	$s.verifyType = "";//用于审核通过与不通过的操作


	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'rrid'});

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
				//新优惠券信息建成数组
				for(var i = 0 in $s.datas){
					if($s.datas[i].couponName){
						var name = $s.datas[i].couponName.split('|');
						var id = $s.datas[i].couponId.split('|');
						$s.datas[i].coupon = {};
						for(var j = 0 in name){
							$s.datas[i].coupon[j] = {
								couponName : name[j],
								couponId : id[j]
							}
						}
					}
				}
				console.log($s.datas);
				/*全选与取消全选*/
				Selects.selects({datas:$s.datas,whichId:'rrid'});
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
			type:"get",
			url:config.basePath+"vip/record-batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "startTime": $s.startTime,
			    "endTime": $s.endTime,
			    "vstate":$s.vstate,
                "vtype":$s.vtype,
                "utype":$s.utype
			}
		};
		/*增加搜索条件*/
		/*增加搜索条件*/
		json.data[$s.searchType]=$s.search;
		return json;
	};

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getDataList();
	}

	/*按时间筛选列表数据*/
	$s.getDataForTime=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		switch(type){
			case 'all':
				$s.startTime=$s.endTime="";
				break;
			case '0':
				$s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd HH:mm:ss');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd HH:mm:ss');
				break;
			case '1':
				$s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd HH:mm:ss');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd HH:mm:ss');
				break;		
		};
		$s.defaultPage=1; //默认第一页
		win.showLoading();
		$s.getDataList();
	}

	/*按输入时间筛选数据列表*/
	$('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd HH:mm:ss');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd HH:mm:ss');
		$s.defaultPage=1; //默认第一页
		win.showLoading();
		$s.getDataList();
		$s.$apply();
	});

	/*按用户类型筛选列表数据*/
	$s.getDataForUserType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.vtype=type;
		$s.getDataList();
	}

    /*按审核状态筛选列表数据*/
    $s.getDataForvState=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.vstate=type;
        $s.getDataList();
    }

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*更新搜索框提示文字*/
	$("#search_type").change(function(){
		$("#search_placeholder").attr("placeholder","输入"+$(this).children("option:selected").text()+"查询")
	})

	/*--审核通过 审核不通过--------------------*/
	/*include 加载完成后执行*/
	$s.rdStateEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.recharge-discount-state").fadeOut("fast");
		})
	}

	$s.actionInfo = {msgWords:"",msgUrl:"",msgCode:[]};
	$s.verifyAction = function(vAction){
		$s.verifyType = vAction;

		$s.alertText = "";//定义提示语句
		
		if(vAction == "vPass"){
			$s.actionInfo.msgWords="审核通过";
			$s.actionInfo.msgUrl = "vip/pass-record";
			$s.actionInfo.msgCode = 0;
			$s.actionInfo.msgWords2="未审核";
		}
		if(vAction == "vFail"){
			$s.actionInfo.msgWords="审核不通过";
			$s.actionInfo.msgUrl = "vip/refuse-record";
			$s.actionInfo.msgCode = 0;
			$s.actionInfo.msgWords2="未审核";
		}

		/*检测是否选择*/
		if(!$rootScope.idList.length){
			Layer.alert({width:325,height:160,type:"msg",title:"请选择需要"+$s.actionInfo.msgWords+"的券模板！"});
			return false;
		};

		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'rcid',idList:$rootScope.idList});
		/*只能选择待审状态的数据*/
		// for (var i=0;i<datas.length;i++){
		// 	if(actionInfo.msgCode!= datas[i].vstate){
		// 		Layer.alert({width:430,height:175,type:"msg",title:"您只能选择状态为"+actionInfo.msgWords2+"的交易，<br>请重新选择！"});
		// 		return false;
		// 	}
		// }

		$(".recharge-discount-state").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#edit-content").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");
	}

	$s.submitRDState = function(){
		//若审核不通过，验证理由
		if(($s.verifyType == "vFail")&&(!$("#vFailReason").val())){
			Layer.alert({width:325,height:160,type:"msg",title:"请输入审核不通过理由"});
			return false;
		}
		

		$.AJAX({
			url:config.basePath+$s.actionInfo.msgUrl,
			data:{
				rcids:$rootScope.idList.toString(),
				reason:$s.verifyType == "vFail" ? $("#vFailReason").val() :"",
			},
			success:function(data){
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				$(".recharge-discount-state").fadeOut();
				$s.getDataList();
			}
		})
	}
		


		

		

	

}]);