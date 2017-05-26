/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Order",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

/*------------------------------------------订单分页数据及其查询-----------------------------------------------------*/	
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.refundState=""; //订单状态
	$s.searchType="orderId";    //默认搜索字段
	$s.search="";     //搜索内容

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
	Selects.selects({datas:$s.datas,whichId:'id'});

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
				/*全选与取消全选*/
				Selects.selects({datas:$s.datas,whichId:'id'});
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
			url:config.basePath+"order/queryRefund",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "startTime": $s.startTime,
			    "endTime": $s.endTime,
				"status":$s.refundState,//$s.refundState
			}
		};
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
				$s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
			case '1':
				$s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
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
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		$s.defaultPage=1; //默认第一页
		win.showLoading();
		$s.getDataList();
		$s.$apply();
	});


	/*按订单状态筛选列表数据*/
	$s.getRefundState=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.refundState=type;
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



	/*---------------------------------退款成功-----------------------------------------------*/
	$s.refundSuccess=function(){
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'id',idList:$rootScope.idList});
		var arr=[];  //定义一个数组
		for (var i=0;i<datas.length;i++){
			var data=datas[i];
			arr.push(data.orderId);
		}
		$s.orderIdList=arr.toString();  //获取订单ID数组
		/*退款成功选择为空时*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'退款中'的订单！",header:"退款成功"});
			return false;
		};
		/*退款成功选择非"退款中"的提示*/
		if(!(data.status==0)){
			Layer.alert({width:340,height:150,type:"msg",title:"该操作只对'退款中'的订单有效！",header:"退款成功"});
			return false;
		}
		Layer.confirm({width:300,height:160,title:"您确认所选订单已经线下退<br>款成功了吗？",header:"退款成功"},function(){
			$.AJAX({
				url: config.basePath + "order/subRefund",
				data:{
					orderIdList:$s.orderIdList,  //订单ID数组
				},
				success:function(data){
					/*关闭弹出层*/
					Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
					/*更新列表*/
					$s.getDataList();
				}
			})
		});
	}



}]);