/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("signUpOrderDetails",["$scope","$filter",function($s,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.orderId=getQueryString("orderId");
	$s.pageSize=10;    //每页显示-显示条数
	$s.index=0;
	$s.tableSwitch=function($event){
		$s.index=tabPageDetails($event);
		win.showLoading();
		/*tab 其切换按需加载*/
		location.hash="##1";
		tabDate($s.index)
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
				/*教练历史订单列表*/
				$s.getOrderDetailsData();
				break;
		}
	}
/*--------------------------------------订单详情-----------------------------------------------------*/
	/*获取数据列表并展示*/
	$s.getOrderDetailsData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"enrollOrder/detail",
			data:{
				orderId : $s.orderId
			},
			success:function(data){
				$s.data=JSON.parse(data.result.pageData);
				$s.$apply();
			}
		});
	};	
	$s.getOrderDetailsData();
	/*参数配置函数*/




}]);