/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("coachDetails",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	$s.coachId=getQueryString("coachId");
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.index=0;
	/*页面加载完后执行*/
	$(function(){if(getQueryString("tab")){$s.tableSwitch(parseInt(getQueryString("tab")));}});
	/*点击tab切换执行*/
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
				$s.getOrderDataList();
				break;
			case 1:
				/*请求绑定学员单数据列表*/
				$s.getCoachDataList();
				break;
			case 2:
				/*请求绑定车辆数据列表*/
				$s.getNewCarData();
				break;
			case 3:
				/*个人账户信息*/
				$s.indAccount();
				break;		
			case 4:
				/*请求操作日志数据列表*/
				$s.getCoachLogs();
				break;	
			case 5:
				/*请求操作日志数据列表*/
				$s.getCoachLogs();
				break;	
		}
	}
/*-------------------------------教练详情--------------------------------------------------------*/
	$s.getCoachData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"coach/view",
			data:{
				coachId : $s.coachId
			},
			success:function(data){
				$s.coachDetails=JSON.parse(data.result.coach);
				console.log($s.coachDetails)
				/*教练评分*/
				scoreHtml($s.coachDetails.starLevel?$s.coachDetails.starLevel:0);
				/*请求历史订单数据列表*/
				$s.getOrderDataList();
				$s.$apply();
			}
		});
	};	
	$s.getCoachData();

/*-------------------------------编辑教练信息----------------------------------------------------*/
	/*include 加载完成后执行*/
	$s.coachEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.edit-coach").fadeOut("fast");
		})

		$('#birthday').daterangepicker({
		 	startDate: new Date(),
			parentElement:$("#edit-coach"),
			singleDatePicker: true
		},
		function(start, end, label) {
			$s.editData.birthday = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		});	
	}
	
	/*点击 编辑教练信息*/
	$s.editData={};
	$s.coachEdit=function(data){
		$(".edit-coach").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#edit-coach").css("marginTop",parseInt(($(win).height()- $("#edit-coach").height()-100)/2)+"px");
		/*修改*/
		$s.editData = data;
	}

	/*参数配置函数*/
	function editCoachJson(url){
		return {
			url:config.basePath+url,
			data:{
				coachId:$s.coachId,
				phoneNum:$s.editData.phoneNum,
				sex:parseInt($s.editData.sex),
				carType:parseInt($s.editData.carType),
				idNumber:$s.editData.idNumber,
				name:$s.editData.name
			}
		}
	}

	/*修改 教练信息*/
	$s.submitEditMsg=function($event){
		var json=editCoachJson("coach/update");
		if(!$s.editData.name || !regCombination('*').test($s.editData.name)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写教练姓名"});
			return false;	
		}
		
		$.AJAX({
			url:json.url,
			data:json.data,
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.edit-alert").fadeOut("fast");
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				/*更新教练信息*/
				$s.getCoachData();
			}
		});/*AJAX end*/
	}

	/*取消时执行*/
	$s.closeAlert=function(){
		$s.getCoachData(); //取消时还原页面数据
	}
/*-------------------------------历史订单--------------------------------------------------------*/
	$s.orderPageSize=10;    //每页显示-显示条数
	$s.orderId="";    //数据查询-姓名
	$s.payState="";   //付款状态
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间

	// /*订单模拟数据*/
	// $s.orderData={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{coachId:0,orderState:0,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:0},
	// 		{coachId:1,orderState:1,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:1},
	// 		{coachId:2,orderState:2,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:0},
	// 		{coachId:3,orderState:3,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:1},
	// 		{coachId:4,orderState:4,orderNumber:"123545155",student:"小张",coach:"张教练",schoolTime:"2015-12-12 12:13",classTime:"2015-12-12 12:13",subject:"科目二",field:"南山科技园十路38号",coachCar:"粤B 1234",money:"160.00",payment:0}
	// 	]
	// }; 
	// //或得的数据列表
	// $s.orderDatas=$s.orderData.dataList;

	/*获取数据列表并展示*/
	$s.getOrderDataList=function(){
		var json=getOrderJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.orderDatas=DATA.dataList;
				/*全选与取消全选*/
				Selects.selects({datas:$s.orderDatas,whichId:'orderId'});
				$s.$apply();
				/*冒泡弹出太长的文字*/
				topLongText();
				//分页请求
				new Page({
					parent:$("#order-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.orderPageSize,
					totalCount:DATA.total, 
				}); //分页请求完毕
			}
		});
	};
	$s.getOrderDataList();	
	/*参数配置函数*/
	function getOrderJson(nowPage){
		var json={
			url:config.basePath+"order/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.orderPageSize),
			    "coachId": $s.coachId,
			    "orderId": $s.orderId,
			    "startTime":$s.startTime,
			    "endTime":$s.endTime,
			    "payState":$s.payState

			}
		};
		return json;
	};

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.getOrderDataList();
	}

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.getOrderDataList();
	}

	/*按时间筛选列表数据*/
	$s.getDataForOederTime=function($event,type){
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
		$s.getOrderDataList();
	}

	/*按输入时间筛选数据列表*/
	$('#reservationorder').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		win.showLoading();
		$s.getOrderDataList();
		$s.$apply();
	});


	/*按付款状态筛选列表数据*/
	$s.getDataForPayment=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.payState=type;
		$s.getOrderDataList();
	}

	/*-------------------关闭订单--------------------------*/
    /*关闭订单*/
    $s.closeOrder=function(){
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要关闭的订单！"});	
			return false;
		};
		/*获得选择的data数据*/
    	var datas=getDataForKey({datas:$s.orderDatas,id:'orderId',idList:$rootScope.idList});
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
					$s.getOrderDataList();
				}
			});
		});/*layer end*/	
    }

/*-------------------------------绑定学员--------------------------------------------------------*/

	// /*学员模拟数据*/
	// $s.studentData={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{studentId:0,name:"小张",sex:0,phoneNum:"13476002586",applyCarType:0,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
	// 		{studentId:1,name:"小张",sex:0,phoneNum:"13476002586",applyCarType:0,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
		
	// 	]
	// }; 
	// //或得的数据列表
	// $s.studentDatas=$s.studentData.dataList;

	$s.studentPageSize=10;   //每页显示-显示条数
	$s.name="";              //高级查询

	/*获取数据列表并展示*/
	$s.getCoachDataList=function(){
		var json=getStudentJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.studentDatas=DATA.dataList;
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#student-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.studentPageSize,
					totalCount:DATA.total, 
				}); //分页请求完毕
			}
		});
	};	
	
	/*参数配置函数*/
	function getStudentJson(nowPage){
		var json={
			url:config.basePath+"coach/student",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.studentPageSize),
			    "coachId": $s.coachId,
			    "name":$s.name
			}
		};
		return json;
	};

	/*按分页条数筛选列表数据*/
	$s.getCoachDataForPage=function(){
		win.showLoading();
		$s.getCoachDataList();
	}

	/*高级查询*/
	$s.getStudentDataForSearch=function(){
		win.showLoading();
		$s.getCoachDataList();
	}

	/*删除绑定学员*/
	$s.studentDelete=function(studentId){
		$.AJAX({
			url:config.basePath+"bankDeposit/pass",
			data:{
				"studentId":studentId
			},
			success:function(data){
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				$s.getCoachDataList();
			}
		});
	}

	/*-------------------分配新学员----------------------*/
	
	/*include 加载完成后执行*/
	$s.addNewStudLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.add-new-student").fadeOut("fast");
		})
	}
	
	/*点击 分配学员*/
	$s.addNewStudent=function(){
		$(".add-new-student").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#add-new-student").css("marginTop",parseInt(($(win).height()- $("#add-new-student").height()-200)/2)+"px");
		$s.newStudentDatas=[];
		$rootScope.idList1=[];
		/*---------点击按钮加载为分配学员列表---------*/
		win.showLoading();
		$s.getNewStudentDataList();
	}

	/*为分配学员模拟数据*/
	// $s.newStudentData={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{studentId:0,name:"小张",age:25,sex:0,phoneNum:"13476002586",applyCarType:1,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
	// 		{studentId:1,name:"小张",age:30,sex:0,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
		
	// 	]
	// }; 
	// //获得的数据列表
	// $s.newStudentDatas=$s.newStudentData.dataList;
	$s.newStudent=""; //按学员名称查询
	$s.newStudentPageSize=5; //为分配学员数据分页
	/*获取数据列表并展示*/
	$s.getNewStudentDataList=function(){
		var json=getNewStudentJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.newStudentDatas=DATA.dataList;
				/*全选与取消全选*/
				Selects.selects({datas:$s.newStudentDatas,whichId:'studentId',num:'1'});
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#new-student-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.orderPageSize,
					totalCount:DATA.total, 
					type:2,
					callback:function(nowPage,totalPage){
						//a标签点击请求数据
						var json=getNewStudentJson(nowPage);
						$.AJAX({
							type:"get",
							url:json.url,
							data:json.data,
							success:function(data){
								var DATA=getListData(data);
								$s.newStudentDatas=DATA.dataList;
								$s.$apply();
							}
						});
					}
				}); //分页请求完毕
			}
		});
	};	
	/*参数配置函数*/
	function getNewStudentJson(nowPage){
		var json={
			url:config.basePath+"student/no-coach",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.newStudentPageSize),
			    "name":$s.newStudent
			}
		};
		return json;
	};

	/*按学习进度筛选列表数据*/
	$s.getDataForJid=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.payState=type;
		$s.getNewStudentDataList();
	}

	/*按学员名字查询数据*/
	$s.getNewStudentDataForSearch=function(newStudent){
		win.showLoading();
		$s.newStudent=newStudent;
		$s.getNewStudentDataList();
	}

    /*确认绑定学员信息*/
	$s.submitStudentMsg=function($event){
		if(!$rootScope.idList1.length){
			Layer.alert({type:'msg',title:'请选择需要绑定的学员！'})
			return false;
		}
		Layer.confirm({
			width:300,height:170,
			title:"您确认分配已选择的 <i class='mainColor'>"+$rootScope.idList1.length+"</i> 位学员<br>到该教练吗？",
			header:"分配新学员"
		},function(){
			$.AJAX({
				url:config.basePath+'coach/new-stu',
				data:{
					coachId:$s.coachId,
					studentIds:$rootScope.idList1.toString()
				},
				success:function(data){
					/*关闭弹出层*/
					$($event.target).parents("div.add-new-student").fadeOut("fast");
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					/*更新教练绑定学员数据列表*/
					$s.getCoachDataList();
				}
			});/*AJAX end*/
		});
	}

/*-------------------------------绑定教练车------------------------------------------------------*/
	/*绑定教练测试数据*/
	// $s.coachCarDatas=[
	// 	{carId:1000004691,carNo:'粤362',carType:'一汽大众',driveType:1,student1:52,hour2:50,student2:152,hour1:502,},
	// 	{carId:1000004691,carNo:'粤362',carType:'奥迪A4',driveType:2,student1:52,hour2:50,student2:152,hour1:502,},
	// ]
	/*查询教练绑定车辆数据列表*/
	$s.getNewCarData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+'coach/car',
			data:{
			    "coachId": $s.coachId,
			},
			success:function(data){
				$s.coachCarDatas=JSON.parse(data.result.coachCar);
				$s.$apply();
			}
		});
	}

	/*-----------------绑定教练车弹出层-----------------------*/
	/*include 加载完成后执行*/
	$s.addNewCarLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.add-new-car").fadeOut("fast");
			$s.isBindCarShow=false; //弹出层默认无数据
		});
	}

	$s.isBindCarShow=false; //弹出层默认无数据
	$s.editType='分配';
	/*点击新增教练车按钮绑定车辆*/
	$s.addNewCar=function(carId){
		$(".add-new-car").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#add-new-car").css("marginTop",parseInt(($(win).height()- $("#add-new-car").height()-200)/2)+"px");
		$s.isBindCarShow=false; //弹出层默认无数据	
		/*判断是否是更换车辆*/
		if(carId+''){
			$s.o_carId=carId;
			$s.editType='更换';
		}
	}
	
	/*查询车辆详情*/
	$s.searchCarDetails=function(newCarNo){
		if(!newCarNo || !regCombination('*').test(newCarNo)){
			Layer.alert({type:"msg",title:"请输入车牌号!"});
			return false;	
		}
		$s.isBindCarShow=true;
		// 测试数据
		// $s.bindCarDetails={carId:990,carNo:"粤25415",carType:"一汽大众",driveType:2,coachName:"李英爱",carLevel:2}

		win.showLoading();
		$.AJAX({
			type:"get",
			url:config.basePath+"car/coach",
			data:{
				carNo:newCarNo,
				schoolNo:$s.coachDetails.schoolId,
			},
			success:function(data){
				$s.bindCarDetails=JSON.parse(data.result.car);
				$s.$apply();
			}
		});/*AJAX end*/
	}

	/*确定绑定搜索的车辆*/
	$s.submitBindCar=function($event){
		for(var i in $s.coachCarDatas){
			if($s.bindCarDetails.carId==$s.coachCarDatas[i].carId ){
				Layer.alert({width:300,height:160,title:"你绑定的教练车已存在！"});
				return false;
			}
		};
		$.AJAX({
			url:config.basePath+'coach/new-car',
			data:{
				carNo:$s.bindCarDetails.carNo,
				o_carId:$s.o_carId,
				c_carId:$s.bindCarDetails.carId,
				coachId:$s.coachId
			},
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.edit-alert").fadeOut("fast");
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				/*更新教练绑定车辆的数据列表*/
				$s.getNewCarData();
			}
		});/*AJAX end*/
	}

	/*删除教练绑定的车辆*/
	$s.deleteCoachBindCar=function(carId){
		Layer.confirm({width:300,height:160,title:"确实取消绑定此车辆吗？",header:"删除绑定"},function(){
			$.AJAX({
				url:config.basePath+'coach/del-rel',
				data:{
				    "coachId":$s.coachId,
				    "carId":carId
				},
				success:function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getNewCarData();
				}
			});
		});
	}

/*-------------------------------个人账户--------------------------------------------------------*/

	/*获取数据列表并展示*/
	$s.pageSize=10;
	$s.indAccount=function(){
		var json=indAccountJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				$s.stuAccount=JSON.parse(data.result.CoachInfo);
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
			url:config.basePath+"coach/account",
			data: {
				"pageNo": nowPage,
				"pageSize":$s.pageSize,
				"coachId": $s.coachId,
				"startTime":$s.startTime?$s.startTime:$filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd'),
				"endTime":$s.endTime?$s.endTime:$filter('date')((new Date().getTime()), 'yyyy-MM-dd'),
				"operateType":$s.operateType,
			}
		};
		return json;
	};

	/*按时间筛选列表数据*/
	$s.getDataForTime=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		switch(type){
			case '1':
				$s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');;
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
			case '2':
				$s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');;
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
			case '3':
				$s.startTime = $filter('date')((new Date().getTime()-31536000000), 'yyyy-MM-dd');;
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;		
		};
		win.showLoading();
		$s.indAccount();
	}

	/*按输入时间筛选数据列表*/
	$('#coachAccount').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		win.showLoading();
		$s.indAccount();
		$s.$apply();
	});

	/*按交易类型来筛选数据列表*/
	$s.getDataForOperate=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.operateType=$s.operateType;
		$s.indAccount();
	}

	/*-------------------------------操作日志--------------------------------------------------------*/
	$s.logPageSize=10;    //每页显示-显示条数
	/*模拟数据*/
	// $s.coachLogs=[
	// 	{updateTime:new Date(),people:'admin',dec:'修改了订单0125161656'},
	// 	{updateTime:new Date(),people:'admin',dec:'修改了订单0125161656'},
	// 	{updateTime:new Date(),people:'admin',dec:'修改了订单0125161656'},
	// ];	
	
	/*获取数据列表并展示*/
	$s.getCoachLogs=function(){
		var json=coachLogsJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.coachLogs=DATA.dataList;
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#log-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.logPageSize,
					totalCount:DATA.total, 
				}); //分页请求完毕
			}
		});
	};	
	/*参数配置函数*/
	function coachLogsJson(nowPage){
		var json={
			url:config.basePath+"logCommon/batch",
			data: {
				"pageNo": nowPage,
				"pageSize":$s.logPageSize,
				"menuId":2,
				"relateId":$s.coachId
			}
		};
		return json;
	};


}]);