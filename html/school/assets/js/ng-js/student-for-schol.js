/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Student",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.carType="";           //所学车型
	$s.search="";            //高级查询
	$s.searchType="name";    //默认搜索字段
	$s.schoolNo="";       //驾校id
	$s.applystateTotal="";  //学员所处阶段

/*-----------------------------------学员列表等操作---------------------------------------------------*/
	/*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{studentId:0,name:"小张",applyexam:5,applystate:100,sex:0,phoneNum:"13476002586",applyCarType:1,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
	// 		{studentId:2,name:"小张",applyexam:6,applystate:100,sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
	// 		{studentId:3,name:"小张",applyexam:301,applystate:101,sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
	// 		{studentId:4,name:"小张",applyexam:302,applystate:100,sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},

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
			url:config.basePath+"student/jx-batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "applyCarType": $s.carType,
			    "schoolId":$s.schoolNo,
			    "applyexam":$s.applystateTotal.split(',')[0],
				"applystate":$s.applystateTotal.split(',')[1],
				"type":1,
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
		$s.applystateTotal=type;
		$s.defaultPage=1; //默认第一页
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
		var json=getJson($s.defaultPage).data;
		var urlstr=jsonToGetUrl(json);
		if($s.total>config.explTotal){
			Layer.confirm({width:300,height:160,title:config.explPrompt,header:"导出"},function(){
				window.location.href=config.basePath+"student/export-excel?"+urlstr;
			});
		}else{
			window.location.href=config.basePath+"student/export-excel?"+urlstr;
		}
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
			$s.schoolNo="";
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
		/*FormData 上传*/
		cerateFileFormData({
			url:config.basePath+"student/uploadWater",
			data:{type:1},
			code:true,
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

/*-------------------------------学员 新增|修改 弹出层操作---------------------------------------*/
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
	
	/*点击 编辑|新增 学员信息*/
	$s.editData={};
	$s.editType="add";
	$s.studentEdit=function(type,data){
		$(".student-alert").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#student-alert").css("marginTop",parseInt(($(win).height()- $("#student-alert").height()-100)/2)+"px");
		if(type=="edit"){
		/*修改*/
			$s.editData = data;
			$s.editType="edit";
		}else{
		/*新增*/
			$s.editData={};
			$s.editType="add";
		}
	}

	/*参数配置函数*/
	function editStudentJson(url){
		var json={
				phoneNum:$s.editData.phoneNum,
				sex:parseInt($s.editData.sex),
				applyCarType:parseInt($s.editData.applyCarType),
				idNumber:$s.editData.idNumber,
				name:$s.editData.name
			};
		if($s.editType=="edit"){
			angular.extend(json,{studentId:$s.editData.studentId});
		}	
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*修改 | 新增 学员信息*/
	$s.submitEditMsg=function($event){
		var json=$s.editType=="add"?editStudentJson("student/add"):editStudentJson("student/update");
		//if(!$s.editData.name || !regCombination('chinese',[2,8]).test($s.editData.name)){
		//	Layer.alert({width:300,height:160,type:"msg",title:"请填写姓名(2-8个中文)"});
		//	return false;
		//}
		//if(!$s.editData.sex.toString() || !regCombination('*').test($s.editData.sex)){
		//	Layer.alert({width:300,height:160,type:"msg",title:"请填选择性别"});
		//	return false;
		//}
		//if(!$s.editData.phoneNum || !regCombination('phone').test($s.editData.phoneNum)){
		//	Layer.alert({width:300,height:160,type:"msg",title:"请填手机正确的号码"});
		//	return false;
		//}
		if($s.editData.phoneNum.length < 11 || !regCombination('*').test($s.editData.phoneNum)){
			Layer.alert({width:300,height:160,type:"msg",title:"请填手机正确的号码"});
			return false;
		}
		//if(!$s.editData.applyCarType || !regCombination('*').test($s.editData.applyCarType)){
		//	Layer.alert({width:300,height:160,type:"msg",title:"请选择驾驶类别"});
		//	return false;
		//}

		$.AJAX({
			url:json.url,
			data:json.data,
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.student-alert").fadeOut("fast");
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				/*更新列表*/
				$s.getDataList();
			}
		});/*AJAX end*/
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
    	$('#studentState').val(""); //默认上次默然选择的学员状态
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
					$("#change-error-students").css("marginTop","150px");	
					/*返回数据列表*/
					$s.errDataList=studentStateRes(getListData(data));
					console.log(getListData(data))
					console.log($s.errDataList)
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

}]);