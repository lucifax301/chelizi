/*angular for 财务收支统计*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("AccountsBalance",["$scope","$filter",function($s,$filter){
	
/*-----------------------------------------第三方账户收入对账情况-------------------------------------------------*/
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.carType="";           //所学车型
	$s.accountStatus="";    //账号状态
	$s.search="";            //高级查询
	$s.searchType="name";    //默认搜索字段
	$s.learningProg="";
	$s.entryStatus="";
	// /*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{time:"2016-02-25",coachM:1000,stuM:1000,chongzhi:1000,jiangjin:1000,tixian:1000,yezf:1000,zhuangtai:"平"},
	// 		{time:"2016-02-25",coachM:1000,stuM:1000,chongzhi:1000,jiangjin:1000,tixian:1000,yezf:1000,zhuangtai:"+200"},
	// 		{time:"2016-02-25",coachM:1000,stuM:1000,chongzhi:1000,jiangjin:1000,tixian:1000,yezf:1000,zhuangtai:"-200"},
			
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
			url:config.basePath+"student/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "applyCarType": $s.carType,
			    "accountStatus": $s.accountStatus,
			    "learningProg":$s.learningProg,
			    "entryStatus":$s.entryStatus
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
		$s.getDataList();
	}

	/*按输入时间筛选数据列表*/
	$('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		win.showLoading();
		$s.getDataList();
		$s.$apply();
	});
	
	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.getDataList();
	}

	/*table tr 样式*/
	$s.getWhichClass=function(type){
		switch($filter('accBalaStateText')(type)){
			case 1:
				return '';
				break;
			case 2:	
				return 'warning';
				break;
			case 3:	
				return 'danger';
				break;	
		}
	}

/*-----------------------------------------第三方账户支出对账情况-------------------------------------------------*/
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.carType="";           //所学车型
	$s.accountStatus="";    //账号状态
	$s.search="";            //高级查询
	$s.searchType="name";    //默认搜索字段
	$s.learningProg="";
	$s.entryStatus="";
	// /*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{time:"2016-02-25",coachM:1000,stuM:1000,chongzhi:1000,jiangjin:1000,tixian:1000,yezf:1000,zhuangtai:"平"},
	// 		{time:"2016-02-25",coachM:1000,stuM:1000,chongzhi:1000,jiangjin:1000,tixian:1000,yezf:1000,zhuangtai:"+200"},
	// 		{time:"2016-02-25",coachM:1000,stuM:1000,chongzhi:1000,jiangjin:1000,tixian:1000,yezf:1000,zhuangtai:"-200"},
			
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
			url:config.basePath+"student/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "applyCarType": $s.carType,
			    "accountStatus": $s.accountStatus,
			    "learningProg":$s.learningProg,
			    "entryStatus":$s.entryStatus
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
		$s.getDataList();
	}

	/*按输入时间筛选数据列表*/
	$('#reservationtwo').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		win.showLoading();
		$s.getDataList();
		$s.$apply();
	});
	
	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.getDataList();
	}

	/*table tr 样式*/
	$s.getWhichClass=function(type){
		switch($filter('accBalaStateText')(type)){
			case 1:
				return '';
				break;
			case 2:	
				return 'warning';
				break;
			case 3:	
				return 'danger';
				break;	
		}
	}




}]);