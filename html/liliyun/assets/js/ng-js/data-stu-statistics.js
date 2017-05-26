/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("DataOrdStatistics",["$scope","$filter",function($s,$filter){

/*-----------------------------------------查询训练场数据列表----------------------------------------------------*/	
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.cityId="";    //城市ID
	$s.applyexam=-1;  
	$s.applystate=-1;  
	$s.callint=1;
	/*模拟数据*/
	$s.data={
		pages:10,
		total:100,
		pageSize:10,
		pageNo:1,
		dataList:[
			{schoolId:201,name:"深港",region:"广东-深圳",reverseLim:"1000",phoneNum:"13476225415",coachCount:25121,posDesc:"这里面是深圳港的介绍" },
			
		]
	}; 
	//或得的数据列表
	$s.datas=$s.data.dataList;

	/*点击tab切换执行*/
	$s.tableSwitch=function($event){
		$s.index=tabPageDetails($event);
		win.showLoading();
		/*tab 其切换按需加载*/
		location.hash="##1";
		tabDate($s.index)
	}

	/*tab 切换加载列表*/
	function tabDate(index){
		switch (index) {
			case 0:
				/*请求学员详情数据*/ 
				$s.getDataList();
			 	break;
			case 1:
				/*请求学员关联的教练*/
				$s.studentProgressReportData();
				break;
			case 2:
				/*请求学员关联的教练*/
				$s.studentProgressData();
				break;
		}
	}
	
	$s.studentProgressReportData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"statistics-total/progress/student/report",
			data:{
				
			},
			success:function(data){
				var DATA=JSON.parse(data.result.dataList);
				var sumDATA=JSON.parse(data.result.sum);
				console.log(DATA);
				$s.reportList=DATA;
				$s.sumList=sumDATA;
				$s.$apply();
			}
		});
	};	

	$s.studentProgressData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"statistics-total/progress/student",
			data:{
				
			},
			success:function(data){
				var DATA=JSON.parse(data.result.progressList);
				console.log(DATA);
				$s.progressList=DATA;
				$s.$apply();
			}
		});
	};	
	$s.studentProgressData();

	/*数据导出*/
	$s.progressDataExport=function(){
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'statistics-total/progress/export',
		});
	}

	/*数据导出*/
	$s.studentDataExport=function(){
		dataExportForIframe({
			getSearchs:getProgressJson($s.defaultPage).data,
			total:$s.total,
			url:'student/progressStudent/export',
		});
	}
	
	$s.progressStudentData=function(){
		var json=getProgressJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				
				var DATA=getListData(data);
				console.log(DATA);
				$s.total=DATA.total;
				/*数据渲染页面*/
				$s.pdatas=DATA.dataList;
				
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#copot-page3"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:DATA.total, 
				}); //分页请求完毕

			}
		});
	};	
	
	$s.viewProgresStudent=function(data){
		$("#order-table").children().eq(3).show().siblings().hide();
		$s.applyexam=data.applyexam;
		$s.applystate=data.applystate;
		$s.callint=2;
		$s.progressStudentData();
	}

	$s.backProgres=function(){
		$s.tableSwitch(2)
	}

	/*获取数据列表并展示*/
	$s.getDataList=function(){
		$s.callint=1;
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				$s.allDatas=data.result;
				var DATA=JSON.parse(data.result.pageData);
				console.log(DATA)
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
			url:config.basePath+"statistics-total/student",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "startTime": $s.startTime,
			    "endTime": $s.endTime,
			    "cityId":$s.cityId,
			}
		};
		return json;
	};

	/*参数配置函数*/
	function getProgressJson(nowPage){
		var json={
			url:config.basePath+"student/progressStudent",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "applyexam":$s.applyexam,
				"applystate":$s.applystate
			}
		};
		return json;
	};

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		if($s.callint==1)
			$s.getDataList();
		if($s.callint==2)
			$s.progressStudentData();
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

	/*加载城市数据列表*/
	queryCity({
		callback:function(data){
			$s.citys=data;
			$s.$apply();
		}
	});

	/*按所在城市筛选列表数据*/
	$s.getDataForCity=function(){
		win.showLoading();
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.getDataList();
	}


}]);