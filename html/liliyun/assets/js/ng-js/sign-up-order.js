/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("Order",["$scope","$filter",function($s,$filter){

/*------------------------------------------订单分页数据及其查询-----------------------------------------------------*/	
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.cityId="";	   //数据查询-报名城市
	$s.payState="";    //数据查询-支付状态
	$s.dltype="";   	//驾照类型
	$s.searchType="orderId"; //高级查询默然查询订单
	$s.orderState=""; //高级查询默然查询订单
	$s.search=""; //高级查询默然查询订单
	$s.orderby="";
	$s.sort="";
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
			    "startTime": $s.startTime,
			    "endTime": $s.endTime,
			    "cityId": $s.cityId,
			    "payState": $s.payState,
			    "dltype":$s.dltype,
			    "orderState":$s.orderState,
				"orderby":$s.orderby,
				"sort":$s.sort
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
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
		$s.$apply();
	});

	/*按报名城市筛选列表数据*/
	$s.getDataForCity=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.cityId=type;
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
	$s.getDataForIsdel=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.orderState=type;
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

	/*数据导出*/
	$s.orderDataExport=function(){
		if($s.startTime==""){
			$s.startTime=(new Date(new Date()-24*60*60*1000*7)).date("y-m-d");
            //$s.startTime="2016-08-09";
		}
		if($s.endTime==""){
			$s.endTime=(new Date()).date("y-m-d");
			//$s.endTime="2016-09-01";
		}
        console.log($s.startTime+"~"+$s.endTime)
		
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'enrollOrder/export-excel',
		});
	}
	
	//$s.sortby=function(key){
	//	$s.orderby=key;
	//	$s.sort="desc";
	//	$s.getDataList();
	//}

    $(".orderby").click(
        function(){
            $s.orderby = $(this).attr("data-order-by");
            if($(this).hasClass("sortbydesc")){
                $s.sort = "asc";
                $(this).removeClass("sortbydesc").addClass("sortbyasc");
            }else{
                $s.sort = "desc";
                $(this).removeClass("sortbyasc").addClass("sortbydesc");
            }
            $s.getDataList();
        }
    )


/*--------------------------------------------报名订单编辑-----------------------------------------------*/
	

}]);