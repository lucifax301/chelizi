/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("Order",["$scope","$filter",function($s,$filter){

/*------------------------------------------订单分页数据及其查询-----------------------------------------------------*/	
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.dltype="";   	//驾照类型
	$s.isdel="";  //订单状态
	$s.searchType="orderId"; //高级查询默然查询订单

/*--------------------------------------------报名订单查询-----------------------------------------------*/
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
				/*数据渲染页面*/
				$s.datas=DATA.dataList;
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
			url:config.basePath+"enrollOrder/query",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "dltype":$s.dltype,
			    "isdel":$s.isdel,
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


	/*按订单状态筛选列表数据*/
	$s.getDataForIsdel=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.isdel=type;
		$s.getDataList();
	}

	/*按驾照类型筛选列表数据*/
	$s.getDataForDriveType=function($event,type){
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

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

/*--------------------------------------------报名订单编辑-----------------------------------------------*/
	

}]);