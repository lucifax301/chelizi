/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Student",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.carType="";           //所学车型
	$s.search="";            //高级查询
	$s.searchType="name";    //默认搜索字段
	$s.applystateTotal="";  //学员所处阶段
	$s.applyttid="" //报名包


	/*-----------------------------------学员列表等操作---------------------------------------------------*/
	/*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{studentId:0,name:"小张",sex:0,applyexam:5,applystate:100,phoneNum:"13476002586",applyCarType:1,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
	// 		{studentId:1,name:"小张",sex:1,applyexam:7,applystate:100,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
	// 		{studentId:2,name:"小张",applyexam:5,applystate:100,sex:0,phoneNum:"13476002586",applyCarType:1,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
	// 		{studentId:3,name:"小张",applyexam:6,applystate:100,sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
	// 		{studentId:4,name:"小张",applyexam:301,applystate:101,sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
	// 		{studentId:5,name:"小张",applyexam:302,applystate:100,sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},

	// 	]
	// };
	// //或得的数据列表
	// $s.datas=$s.data.dataList;
	// /*全选与取消全选*/
	// Selects.selects({datas:$s.datas,whichId:'studentId'});

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
				Selects.selects({datas:$s.datas,whichId:'studentId'});
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
			url:config.basePath+"student/lili-batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "applyCarType": $s.carType,
			    "accountStatus": $s.accountStatus,
			    "learningProg":$s.learningProg,
			    "entryStatus":$s.entryStatus,
			    "applyexam":$s.applystateTotal.split(',')[0],
				"applystate":$s.applystateTotal.split(',')[1],
				"type":0,
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

	/*按所学车型筛选列表数据*/
	$s.getDataForCarType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.carType=type;
		$s.checkAllTag['carType']=$($event.target).text();
		$s.objElement['carType']=$($event.target);
		$s.getDataList();
	}

	/*学员状态data*/
	$s.studentStatesList=studentStatesListForSchool;

	/*学员状态tab切换*/
	$s.comSearTabCheck=function($event,type){
		var elemObj=$event.target.nodeName=='DIV'?$($event.target):$($event.target).parent('div');
		$('div.tab-par').removeClass('active');
		$('div.tab-chr').hide();
		elemObj.addClass('active').next('div').show();
		$('div.tab-par').find('span').attr('class','ion-arrow-up-b');
		elemObj.find('span').attr("class","ion-arrow-down-b");
		/*判断执行*/
		if(type=="all"){
			$s.defaultPage=1; //默认第一页
			$s.getDataForStudentState($event,'');
		}
	}

	/*按学员状态筛选列表数据*/
	$s.getDataForStudentState=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.applystateTotal=type;
		$s.checkAllTag['applystateTotal']=$($event.target).text();
		$s.objElement['applystateTotal']=$($event.target);
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
	$s.studentDataExport=function(){
		//调起数据导出
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'student/export-excel',
		});
	}

	/*tag 查询的增删*/
	$s.checkAllTag={};
	$s.objElement={};
	$s.removeTag=function(key){
		win.showLoading();
		$s.checkAllTag=deleteJson($s.checkAllTag,key);
		if($s.objElement[key]){
			if($s.objElement[key][0].nodeName=='LI'){
				$s.objElement[key].parent().children().eq(0).addClass('active').siblings().removeClass('active');
			}else if($($s.objElement[key][0]).attr('data-chr')=='children'){
				$s.objElement[key].parent().hide().children().removeClass('active');
				$s.objElement[key].parents('ul').find('div.tab-par').removeClass('active');
				$s.objElement[key].parents('ul').children().eq(0).children().addClass('active');
			}
		}
		$s[key]="";
		$s.getDataList();
	}
	/*清除全部tag*/
	$s.removeAllTag=function(){
		if(!jQuery.isEmptyObject($s.objElement)){
			win.showLoading();
			$s.carType="";
			$s.applystateTotal="";
			clearAllActive($s.objElement);
			$s.checkAllTag={};
			$s.objElement={};
			/*二级类 的全部增加 active样式*/
			$('div.tab-line ul').find('.tab-par').removeClass('active');
			$('div.tab-line ul').find('li').eq(0).children('div').addClass('active');
			/*隐藏二级类块*/
			$('div.tab-chr').hide().children('div').removeClass('active');
			$s.getDataList();
		};
	}

	/*单击上传流水号*/
	$s.uploadSerialnumber=function(){
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		var apply=true;
		for (var i = 0; i < datas.length; i++) {
			if(!(datas[i].applyexam==6 && datas[i].applystate==1) && !(datas[i].applyexam==6 && datas[i].applystate==101)){
				apply=false;
				break;
			}
		}
		/*选中学员状态为非‘资料已邮寄’时的提示*/
		if(apply==false){
			Layer.alert({width:400,height:175,type:"msg",title:"导入失败，请检查导入学员的状态是否<br>是［受理中］或［受理失败］",header:"上传流水号"});
			return false;
		}

		cerateFileFormData({
			url:config.basePath+"student/uploadWater",
			data:{type:1},
			callback:function(data){
				Layer.alertMor({width:450,height:220,type:"success",title:data.msg,callback:function(){
					$.AJAX({
						url:config.basePath+"student/uploadWater",
						data:{
							tagFileName:data.tagFileName,
							type:2,
						},
						success:function(data){
							Layer.closeNowLayer('alertMorMask');/*关闭弹出层*/
							Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
							$s.getDataList();/*更新列表*/
						}
					})
				},
				close:function(){
					//清除上传文件
					$.AJAX({
						url:config.basePath+"student/uploadWater",
						data:{
							tagFileName:data.tagFileName,
							type:3,
						},success:function(){}
					})
				}
				});
			}
		});
	}

/*-----------------------------------学员 新增|修改 弹出层操作----------------------------------------*/
	/*include 加载完成后执行*/
	$s.sutdentEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.edit-alert").fadeOut("fast");
		});
	}

	/*点击 编辑|新增 学员信息*/
	$s.editData={};
	$s.studentEdit=function(data){
		console.log(data)
		$(".student-alert").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#student-alert").css("marginTop",parseInt(($(win).height()- $("#student-alert").height()-100)/2)+"px");
		$s.editData = data;
	}

	/*参数配置函数*/
	function editStudentJson(url){
		var json={
				phoneNum:$s.editData.phoneNum,
				sex:parseInt($s.editData.sex),
				applyCarType:parseInt($s.editData.applyCarType),
				idNumber:$s.editData.idNumber,
				name:$s.editData.name,
				studentId:$s.editData.studentId
			};
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*修改 | 新增 学员信息*/
	$s.submitEditMsg=function($event){
		var json=editStudentJson("student/update");
		//if(!$s.editData.name || !regCombination('chinese',[2,8]).test($s.editData.name)){
		//	Layer.alert({type:"msg",title:"请填写姓名(2-8个中文)"});
		//	return false;
		//}
		//if(!$s.editData.sex.toString() || !regCombination('*').test($s.editData.sex)){
		//	Layer.alert({type:"msg",title:"请填选择性别"});
		//	return false;
		//}
		//if(!$s.editData.phoneNum || !regCombination('phone').test($s.editData.phoneNum)){
		//	Layer.alert({type:"msg",title:"请填手机正确的号码"});
		//	return false;
		//}
		if($s.editData.phoneNum.length < 11 || !regCombination('*').test($s.editData.phoneNum)){
			Layer.alert({type:"msg",height:150,title:"请填手机正确的号码"});
			return false;
		}
		//if(!$s.editData.applyCarType || !regCombination('*').test($s.editData.applyCarType)){
		//	Layer.alert({type:"msg",title:"请选择驾驶类别"});
		//	return false;
		//}

		$.AJAX({
			url:json.url,
			data:json.data,
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.edit-alert").fadeOut("fast");
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				/*更新列表*/
				$s.getDataList();
			}
		});/*AJAX end*/
	}

	/*取消时执行*/
	$s.closeAlert=function(){
		$s.getDataList(); //取消时还原页面数据
	}

/*-----------------------------------置学员状态--------------------------------------------------------*/
	/*学员状态数据*/
	$s.studentStates=[];
	$s.haveStudentState=false; //学员是否能够置状态变量
	$s.incompleteData="";//资料不全的备注
	/*全选与取消全选*/

    /*include 加载完成后执行*/
	$s.StudentStateLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.student-state").fadeOut("fast");
		});
	}

    /*置学员状态弹出层*/
    $s.changeStudentState=function(){
    	$s.studentStates=[];//置空学员状态数据
    	$('#studentState').val(""); //置空上传选择的学员状态
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要置状态的学员！"});
			return false;
		};
		$(".student-state").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#student-state").css("marginTop",parseInt(($(win).height()- $("#student-state").height()-100)/2)+"px");
		/*显示可置的学员状态*/
		getDataForStudentConfig({
			datas:getDataForKey({  //获得可置状态的数据列表
				datas:$s.datas,
				idList:$s.idList,
				id:'studentId',
			}),
			check:'applyexam,applystate',
			checkData:studentSchoolJurisConfig,
			callback:function(data){
				$s.studentStates=data;
				$s.haveStudentState=data.length?true:false;
				console.log(data)
			}
		});
    }

    /*include 加载完成后执行*/
	$s.changeErrorStudentLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.change-error-students").fadeOut("fast");
		});
	}


    /*确认置学员状态*/
    $s.submitStudentState=function($event){
    	var studentState=$('#studentState').val();
    	if(!regCombination('*').test(studentState)){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择学员需要置的状态！"});
			return false;
		};
    	$.AJAX({
			url:config.basePath+"student/reset-state",
			data:{
				applyexam:studentState.split(',')[0],
				applystate:studentState.split(',')[1],
				studentIds:$rootScope.idList.toString(),
			},
			success:function(data){
				/*置学员状态失败时执行*/
				if(data.status=='fail'){
					/*-----------------置学员状态弹出层-------------------------*/
					$(".change-error-students").fadeIn("fast");
					/*弹出层垂直居中*/
					$("#change-error-students").css("marginTop","200px");
					/*返回数据列表*/
					$s.errDataList=studentStateRes(getListData(data));
					$s.$apply();
				}else{
					/*关闭弹出层*/
					$($event.target).parents("div.student-state").fadeOut("fast");
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					/*更新列表*/
					$s.getDataList();
				}
			}
		});/*AJAX end*/
    }

    /*关闭弹出层*/
	$s.closeChanErrorStud=function($event){
		/*关闭弹出层*/
		$($event.target).parents("div.change-error-students").fadeOut("fast");
	};




	/*-----------------------------------驾校收表--------------------------------------------------------*/
	$s.collectData=function(){
		/*未选学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'表已寄出'的学员！",header:"驾校收表"});
			return false;
		};

		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		$s.applyttid=datas[0].applyttid;  //获取到信息包id
		console.log($s.applyttid)
		var arrly=[]; //定义一个数组
		for (var i=0;i<datas.length;i++){
			arrly.push(datas[i].applyttid)  //将获取到applyttid注入到数组中
			var applyid=arrly.indexOf(arrly[i]);  //判断城市是否一致
		}
		/*判断是否为同一城市*/
		if(!(applyid==0)){
			Layer.alert({width:330,height:175,type:"msg",title:"您只能选择同一城市的学员，<br>请重新选择学员！"});
			return false;
		}

		/*选中学员状态为非‘资料已邮寄’时的提示*/
		if(!(datas[0].applyexam==5 && datas[0].applystate==1)){
			Layer.alert({width:355,height:175,type:"msg",title:"您只能把'表已寄出'的学员标记为驾校<br>已收表，请重新选择学员！",header:"驾校收表"});
			return false;
		}
		Layer.confirm({width:300,height:160,title:"您确认已经收到所选学员的<br>报名表资料吗？",header:"驾校收表"},function(){
			$.AJAX({
				url: config.basePath + "student/reset-state",
				data: {
					applyexam: 5,
					applystate: 100,
					applyttid: $s.applyttid,
					studentIds: $rootScope.idList.toString(),
				},
				success: function (data) {
					/*关闭弹出层*/
					Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
					/*更新列表*/
					$s.getDataList();
				}
			});
		})
	}


	/*-----------------------------------交表受理--------------------------------------------------------*/
	$s.acceptData=function(){
		/*未选学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'已收表'的学员！",header:"交表受理"});
			return false;
		};

		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		$s.applyttid=datas[0].applyttid;  //获取到信息包id
		/*选中学员状态为非‘资料已邮寄’时的提示*/
		if(!(datas[0].applyexam==6 && datas[0].applystate==0)){
			Layer.alert({width:355,height:175,type:"msg",title:"您只能把'已收表'的学员交到车管所<br>受理，请重新选择学员！",header:"交表受理"});
			return false;
		}
		Layer.confirm({width:340,height:160,title:"您确认把所选学员的报名表资料<br>交到车管所受理吗？",header:"交表受理"},function(){
			$.AJAX({
				url: config.basePath + "student/reset-state",
				data: {
					applyexam: 6,
					applystate: 1,
					applyttid: $s.applyttid,
					studentIds: $rootScope.idList.toString(),
				},
				success: function (data) {
					/*关闭弹出层*/
					Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
					/*更新列表*/
					$s.getDataList();
				}
			});
		})
	}


	/*-----------------------------------交表失败--------------------------------------------------------*/
	$s.errorData=function(){
		/*未选学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'受理中'的学员！",header:"交表失败"});
			return false;
		};
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		$s.applyttid=datas[0].applyttid;  //获取到信息包id
		/*选中学员状态为非‘资料已邮寄’时的提示*/
		if(!(datas[0].applyexam==6 && datas[0].applystate==1)){
			Layer.alert({width:355,height:175,type:"msg",title:"您只能把'受理中'的学员标记为受理<br>失败，请重新选择学员！",header:"交表失败"});
			return false;
		}
		Layer.confirmNotByTextAlert({
			header:"交表受理",
			width:380,
			height:250,
			title:"<b>学员在车管所受理失败</b>",
		},function(notByText){
			$.AJAX({
				url: config.basePath + "student/reset-state",
				data:{
					applyexam: 6,
					applystate: 101,
					applyttid: $s.applyttid,
					studentIds: $rootScope.idList.toString(),
					note:notByText,
				},
				success:function(data){
					/*关闭弹出层*/
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					/*更新列表*/
					$s.getDataList();
				}
			});
		});
	}
}]);