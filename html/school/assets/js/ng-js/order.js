/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Order",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects,getMsgTime){

/*------------------------------------------订单分页数据及其查询-----------------------------------------------------*/	
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.subject="";	   //数据查询-科目状态
	$s.orderId="";    //数据查询-姓名
	$s.orderState=""; //订单状态
	$s.payState="";   //付款状态
	$s.searchType="orderId";    //默认搜索字段
	$s.otype="";      //订单类型
	$s.search="";     //搜索内容
	$s.carType="";   //所学车型

	// /*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{orderId:01215450,orderState:0,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:new Date(),classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:0},
	// 		{orderId:15616516,orderState:1,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:1},
	// 		{orderId:216516516,orderState:2,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:0},
	// 	]
	// };
	// //或得的数据列表
	// $s.datas=$s.data.dataList;
	
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
				Selects.selects({datas:$s.datas,whichId:'orderId',num:''});
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
			url:config.basePath+"order/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "startTime": $s.startTime,
			    "endTime": $s.endTime,
			    "subject": $s.subject,
			    "payState": $s.payState,
			    "orderState":$s.orderState,
			    "applyCarType": $s.carType,
			    "otype":$s.otype
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
				$s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');;
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
			case '1':
				$s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');;
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
		};
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*按输入时间筛选数据列表*/
	$('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
		$s.$apply();
	});

	/*按所学车型筛选列表数据*/
	$s.getDataForCarType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.carType=type;
		$s.getDataList();
	}

	/*按科目筛选列表数据*/
	$s.getDataForSubject=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.subject=type;
		$s.getDataList();
	}

	/*按付款状态筛选列表数据*/
	$s.getDataForPayment=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.payState=type;
		$s.getDataList();
	}

	/*按订单状态筛选列表数据*/
	$s.getOrderState=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.orderState=type;
		$s.getDataList();
	}

	/*按订单类型筛选列表数据*/
	$s.getDataForOtype=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.otype=type;
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

	/*-----------------------------------关闭订单------------------------------------------------------*/
    /*关闭订单*/
    $s.closeOrder=function(){
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要关闭的订单！"});
			return false;
		};
		/*获得选择的data数据*/
    	var datas=getDataForKey({datas:$s.datas,id:'orderId',idList:$rootScope.idList});
    	/*关闭订单*/
		Layer.confirm({width:300,height:160,title:"确认关闭选中的订单吗？",header:"关闭订单"},function(){
			$.AJAX({
				url:config.basePath+"order/update-batch",
				data:{
					orderIds:$rootScope.idList.toString(),
					studentIds:getKeyArrFromData(datas,'studentId').toString(),
					coachIds:getKeyArrFromData(datas,'coachId').toString(),
				},
				success:function(data){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			});
		});/*layer end*/

    }

    /*数据导出*/
    $s.orderDataExport=function(){
		//调起数据导出
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'order/export-excel',
		});
	}
	
}]);