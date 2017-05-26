/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("OrderDetails",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	$s.studentId=getQueryString("studentId");
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.index=0;
	/*页面加载完后执行*/
	$(function(){if(getQueryString("tab")){$s.tableSwitch(parseInt(getQueryString("tab")));}});
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
				/*请求学员关联的教练*/
				$s.studentCoachData();
				break;
			case 2:
				/*请求个人账户信息*/
				$s.indAccount();
				break;			
			case 3:
				/*请求操作日志数据列表*/
				$s.getStudentLogs();
				break;	
		}
	}
/*-----------------------------------学员详情--------------------------------------------------------*/
	$s.getStudentData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"student/view",
			data:{
				studentId : $s.studentId
			},
			success:function(data){
				$s.studentDetails=JSON.parse(data.result.student);
				/*请求相应的订单数据列表*/
				$s.getDataList();
				$s.$apply();
			}
		});
	};	
	$s.getStudentData();

/*-----------------------------------编辑学员信息----------------------------------------------------*/

	/*include 加载完成后执行*/
	$s.sutdentEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.edit-alert").fadeOut("fast");
		})

		/*---daterangepicker begin--*/
		// $('#birthday').daterangepicker({
		//  	startDate: new Date(),
		// 	parentElement:$("#student-alert"),
		// 	singleDatePicker: true
		// },
		// function(start, end, label) {
		// 	$s.editData.birthday = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		// });	
	}
	
	/*点击 编辑 学员信息*/
	$s.editData={};
	$s.studentEdit=function(data){
		$(".student-alert").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#student-alert").css("marginTop",parseInt(($(win).height()- $("#student-alert").height()-100)/2)+"px");
		/*修改*/
		$s.editData = data;
	}

	/*参数配置函数*/
	function editStudentJson(url){
		return {
			url:config.basePath+url,
			data:{
				studentId:$s.editData.studentId,
				phoneNum:$s.editData.phoneNum,
				sex:parseInt($s.editData.sex),
				applyCarType:parseInt($s.editData.applyCarType),
				idNumber:$s.editData.idNumber,
				name:$s.editData.name
			}
		}
	}

	/*修改 学员信息*/
	$s.submitEditMsg=function($event){
		var json=editStudentJson("student/update");
		if(!$s.editData.name || !regCombination('*').test($s.editData.name)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写学员姓名"});
			return false;	
		}

		$.AJAX({
			url:json.url,
			data:json.data,
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.edit-alert").fadeOut("fast");
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				/*更新学员信息*/
				$s.getStudentData();
			}
		});/*AJAX end*/
	}

	/*取消时执行*/
	$s.closeAlert=function(){
		$s.getStudentData(); //取消时还原页面数据
	}
	
/*-----------------------------------历史订单--------------------------------------------------------*/
	/*参数配置函数*/
	$s.orderId="";    //数据查询-姓名
	$s.payState="";   //付款状态

	// /*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{id:0,orderState:0,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:0},
	// 		{id:1,orderState:1,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:1},
	// 		{id:2,orderState:2,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:0},
	// 		{id:3,orderState:3,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:1},
	// 		{id:4,orderState:4,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:0}
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
	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"order/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "studentId": $s.studentId,
			    "orderId": $s.orderId,
			    "payState":$s.payState
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

	/*按付款状态筛选列表数据*/
	$s.getDataForPayment=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.payState=type;
		$s.getDataList();
	}

	/*-----------------------关闭订单-----------------------------*/
    /*关闭订单*/
    $s.closeOrder=function(){
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要关闭的订单！"});	
			return false;
		};
		/*获得选择的data数据*/
    	var datas=getDataForKey({datas:$s.datas,id:'orderId',idList:$rootScope.idList});
    	/*确认关闭订单*/
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

/*-----------------------------------绑定教练--------------------------------------------------------*/
	//$s.stuCoData={coachIcon:'assets/img/logo.png',carNo:'sfgsefsf,sdgerg,dgdgr,dghrdgr,dgrdg',coachName:'汪教练',coachId:102,phoneNum:'13476002154',totalOrderNum:21}	
	$s.studentCoachData=function(){
		$.AJAX({
			type:"GET",
			url:config.basePath+"student/coach",
			data:{
				studentId:$s.studentId
			},
			success:function(data){
				$s.stuCoData=data.result.cco?JSON.parse(data.result.cco):'';
				$s.$apply();
			}
		});
	}

	/*-----------------绑定教练车弹出层-----------------------*/
	/*include 加载完成后执行*/
	$s.addNewCoachLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.add-new-coach").fadeOut("fast");
			$s.isBindCoachShow=false; //弹出层默认无数据
		});
	}

	$s.isBindCoachShow=false; //弹出层默认无数据
	$s.editType='分配';
	/*点击新增教练车按钮绑定教练*/
	$s.addNewCoach=function(coachId){
		//取消选择
		$s.c_coachId="";
		$s.o_coachId="";
		$(".add-new-coach").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#add-new-coach").css("marginTop",parseInt(($(win).height()- $("#add-new-coach").height()-200)/2)+"px");
		$s.isBindCoachShow=false; //弹出层默认无数据
		/*判断是否是更换教练*/
		if(angular.isNumber(coachId)){
			$s.o_coachId=coachId;
			$s.editType='更换';
		}
	}
	
	/*查询教练详情*/
	$s.searchCoachDetails=function(newCoachNome){
		if(!newCoachNome || !regCombination('*').test(newCoachNome)){
			Layer.alert({type:"msg",title:"请输入教练名!"});
			return false;	
		}
		$s.isBindCoachShow=true;
		// 测试数据
		//$s.bindCoachDetails={coachId:102,coachIcon:'assets/img/header1.png',sex:1,coachName:'汪教练',coachId:1020,phoneNum:'13476002154',totalStudentNum:21}

		win.showLoading();
		$.AJAX({
			type:"get",
			url:config.basePath+"coach/batch",
			data:{
			    "name": newCoachNome
			},
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.bindCoachDetails=DATA.dataList[0];
				$s.$apply();
			}
		});/*AJAX end*/
	}

	/*教练信息弹出层*/
	$s.alertCoachMsg=function(coachId){
		Layer.iframe({
			header:"教练信息",
			width:$(window).width()*0.8,
			height:$(window).height()*0.8,
			href:"coach-details.html?coachId="+coachId,
			closeMask:true
		});
	}

	/*选择教练*/
	$s.c_coachId='';
	$s.getNewCoach=function(coachId){
		$s.c_coachId=coachId;
	}

	/*确定绑定搜索的教练*/
	$s.submitBindCoach=function($event){
		if(!$s.c_coachId){
			Layer.alert({type:"msg",title:"请选择需要绑定的教练!"});
			return false;
		}
		$.AJAX({
			url:config.basePath+'student/new-coach',
			data:{
				o_coachId:$s.o_coachId,
				c_coachId:$s.c_coachId,
				id:$s.studentId
			},
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.edit-alert").fadeOut("fast");
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				/*更新教练绑定教练的数据列表*/
				$s.studentCoachData();
				$s.$apply();
			}
		});/*AJAX end*/
	}

	/*删除教练绑定的教练*/
	$s.deleteCoachBindStudent=function(coachId){
		Layer.confirm({width:300,height:160,title:"确认取消绑定此教练吗？",header:"删除绑定"},function(){
			$.AJAX({
				url:config.basePath+'student/del-rel',
				data:{
				    "id":$s.studentId,
				    "coachId":coachId
				},
				success:function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.studentCoachData();
				}
			});
		});
	}

/*-----------------------------------个人账户--------------------------------------------------------*/

	/*获取数据列表并展示*/
	$s.indAccount=function(){
		var json=indAccountJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				/*数据渲染页面*/
				$s.stuAccount=JSON.parse(data.result.stuInfo);
				$s.stuBankList=JSON.parse(data.result.bankList);
				$s.stuMoneyList=JSON.parse(data.result.moneyList);
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#total-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:$s.stuMoneyList.total, 
				}); //分页请求完毕
			}
		});
	};	
	/*参数配置函数*/
	function indAccountJson(nowPage){
		var json={
			url:config.basePath+"student/account",
			data: {
				"pageNo": nowPage,
				"pageSize":$s.pageSize,
				"studentId":$s.studentId,
				"startTime":$s.startTime?$s.startTime:$filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd'),
				"endTime":$s.endTime?$s.endTime:$filter('date')((new Date().getTime()), 'yyyy-MM-dd'),
                channel:1,
                isEarning:0,
                isBalance:''
			}
		};
		return json;
	};

	/*按时间筛选列表数据*/
	$s.getDataForTime=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		switch(type){
			case '1':
				$s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
			case '2':
				$s.startTime = $filter('date')((new Date().getTime()-2592000000), 'yyyy-MM-dd');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
			case '3':
				$s.startTime = $filter('date')((new Date().getTime()-31536000000), 'yyyy-MM-dd');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;		
		};
		win.showLoading();
		$s.indAccount();
	}

	/*按输入时间筛选数据列表*/
	$('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		win.showLoading();
		$s.indAccount();
		$s.$apply();
	});

/*-----------------------------------操作日志--------------------------------------------------------*/
	
	/*模拟数据*/
	// $s.studentLogs=[
	// 	{updateTime:new Date(),people:'admin',dec:'修改了订单0125161656'},
	// 	{updateTime:new Date(),people:'admin',dec:'修改了订单0125161656'},
	// 	{updateTime:new Date(),people:'admin',dec:'修改了订单0125161656'},
	// ];	
	$s.logPageSize=10;    //每页显示-显示条数
	/*获取数据列表并展示*/
	$s.getStudentLogs=function(){
		var json=studentLogsJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.studentLogs=DATA.dataList;
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
	function studentLogsJson(nowPage){
		var json={
			url:config.basePath+"logCommon/batch",
			data: {
				"pageNo": nowPage,
				"pageSize":$s.logPageSize,
				"menuId":1,
				"relateId":$s.studentId
			}
		};
		return json;
	};


}]);