/*angular for 教练*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("CouponCondition",["$scope","$filter",function($s,$filter,$scope){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数

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
				$s.$apply();
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
			url:config.basePath+"coupon/cond-batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "type": $s.otype,
			    "startTime": $s.startTime,
			    "endTime": $s.endTime,
			}
		};
		/*增加搜索条件*/
		//json.data[$s.searchType]=$s.search;
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

	/*按条件类型筛选列表数据*/
	$s.getDataForStatus=function($event,type){
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

}]);