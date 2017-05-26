/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("historyPayment",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.search="";            //高级查询
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.searchType="orderId";    //默认搜索字段
	$s.schoolNo="";       //驾校id
	$s.cityId="";         //城市id
	$s.cityError=false; //检测是否选择城市 默认为否
	$s.applystateTotal=",";  //学员所处阶段
	$s.statusId="";    //身份信息id
	$s.orderId="";  //订单id
	/*------------------------------------数据列表查询-------------------------------------------------------*/
	/*模拟数据*/
	$s.data={
		pages:10,
		total:100,
		pageSize:10,
		pageNo:1,
		dataList:[
			{studentId:0,name:"小张",sex:0,applyexam:4,applystate:1,phoneNum:"13476002586",applyCarType:1,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
			{studentId:2,name:"小张",sex:1,applyexam:7,applystate:100,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
		
		]
	}; 

	//或得的数据列表
	$s.datas=$s.data.dataList;

	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'orderId'});
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
				Selects.selects({datas:$s.datas,whichId:'orderId'});
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
			url:config.basePath+"enrollOrder/payQuery",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
				"schoolNo":$s.schoolNo,
				"startTime": $s.startTime,
				"endTime": $s.endTime,
				"orderId":$s.orderId,
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

	/*--------------------------选择城市和驾校--------------------------------------*/
	/*加载城市列表*/
	queryCity({
		callback:function(data){
			$s.citys=data; $s.$apply();
		}
	});

    /*加载驾校列表*/
    $s.getSchools=function(){
    	if($s.cityId){
    		$s.checkHaveCity(); //判断是否选择城市
    		$('#select-school li').last().addClass('active').siblings().removeClass("active");
    		/*加载驾校列表*/
			querySchool({
				cityId:$s.cityId,
				callback:function(data){
					$s.schools=data;
					$s.$apply();
				}
			});
    	}else{
    		$('#select-school li').first().addClass('active').siblings().removeClass("active");
    		$s.schoolNo=""; //情况驾校number
    		$s.schools=""; //清空数据列表
    	}
    }
    /*判断是否选择城市*/
    $s.checkHaveCity=function(){
    	$s.cityError=$s.cityId?false:true;
    }
    /*--------------------------选择城市和驾校--------------------------------------*/

	/*按已分还是未分驾校筛选数据列表*/
	$s.getDataForDistSchool=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.schoolType=type;
		$s.defaultPage=1; //默认第一页
		$s.schoolNo="";       //清空驾校id
		$s.cityId="";         //清空城市id
		$s.getDataList();
	}

	/*按驾校筛选列表数据*/
	$s.getDataForSchool=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.schoolType=2;
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


	/*学员状态data*/
	$s.studentStatesList=studentStatesListForLiLi;


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
		$s.checkAllTag['search']=$s.search;
		$s.getDataList();
	}

	/*tag 查询的增删*/
	$s.checkAllTag={};
	$s.objElement={};


	/*数据导出*/
	$s.orderDataExport=function(){
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'enrollOrder/payDownLoadExl',
		});
	}


}]);