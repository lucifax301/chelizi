/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("CarDetails",["$scope",function($s){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.index=0;
	$s.carId=getQueryString("carId");
	
	/*点击tab切换执行*/
	$s.tableSwitch=function($event){
		$s.index=tabPageDetails($event);
		win.showLoading();
		location.hash="##1";
		/*tab 其切换按需加载*/
		tabDate($s.index);
	}

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		tabDate($s.index);
	}

	/*tab 切换加载列表*/
	function tabDate(index){
		switch (index) {
			case 0: 
				/*请求学员详情数据*/ 
				$s.getDataList();
			 	break;
			case 1:
				/*请求操作日志数据列表*/
				$s.getCarLogs();
				break;	
		}
	}

/*-------------------------------------学员详情-------------------------------------------------------*/
	$s.getStudentData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"car/view",
			data:{
				carId : $s.carId
			},
			success:function(data){
				$s.carDetails=JSON.parse(data.result.car);
				$s.carId=$s.carDetails.carId;
				/*请求相应的订单数据列表*/
				$s.getDataList();
				$s.$apply();
			}
		});
	};	
	$s.getStudentData();

/*-------------------------------------order数据列表---------------------------------------------------*/
	/*参数配置函数*/
	$s.orderId="";    //数据查询-姓名

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
	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"order/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "carId": $s.carId,
			    "orderId": $s.orderId,
			}
		};
		return json;
	};

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.getDataList();
	}

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.getDataList();
	}

/*-------------------------------------教练操作日志---------------------------------------------------*/
	
	/*获取数据列表并展示*/
	$s.getCarLogs=function(){
		var json=carLogsJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.carLogs=DATA.dataList;
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#log-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:DATA.total, 
				}); //分页请求完毕
			}
		});
	};	
	/*参数配置函数*/
	function carLogsJson(nowPage){
		var json={
			url:config.basePath+"logCommon/batch",
			data: {
				"pageNo": nowPage,
				"pageSize":10,
				"menuId":4,
				"relateId":$s.carId
			}
		};
		return json;
	};



}]);