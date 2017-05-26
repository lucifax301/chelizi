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
	$s.cityId="";         //城市id
	$s.cityError=false; //检测是否选择城市 默认为否
	$s.applystateTotal=",";  //学员所处阶段
	$s.schoolType="";       //学员是否分配
	$s.statusId="";    //身份信息id
	$s.textRemark="";  //缺失的资料说明
	$s.applyttid="" ;//报名包
	$s.lockState="1";//封号状态，默认为临时封号
	/*------------------------------------数据列表查询-------------------------------------------------------*/


	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'studentId'});
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
			    "applyexam":$s.applystateTotal.split(',')[0],
				"applystate":$s.applystateTotal.split(',')[1],
				"schoolNo":$s.schoolNo,
				"schoolType":$s.schoolType,
				"cityId":$s.cityId,
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
			$s.getDataForCity();
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
		$s.schools="";		//清空驾校列表
		$s.getDataList();
	}

	/*按城市筛选列表数据*/
	$s.getDataForCity=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.schoolType=2;
		$s.schoolNo=""; //清空驾校id
		$s.getDataList();
	}
	
	/*按驾校筛选列表数据*/
	$s.getDataForSchool=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.schoolType=2;
		$s.getDataList();
	}

	/*按所学车型筛选列表数据*/
	$s.getDataForCarType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.carType=type;
		$s.defaultPage=1; //默认第一页
		$s.checkAllTag['carType']=$($event.target).text();
		$s.objElement['carType']=$($event.target);
		$s.getDataList();
	}

	/*学员状态data*/
	$s.studentStatesList=studentStatesListForLiLi;

	/*学员状态tab切换*/
	$s.comSearTabCheck=function($event,type){
		var elemObj=$event.target.nodeName=='DIV'?$($event.target):$($event.target).parent('div');
		$('div.tab-par').removeClass('active');
		$('div.tab-chr').hide();
		elemObj.addClass('active').next('div').show();
		$('div.tab-par').find('span').attr('class','ion-arrow-up-b');
		elemObj.find('span').attr("class","ion-arrow-down-b");
		//因报名相关子项太多，此处JS动态控制其高度
        console.log($(".tab-line").width())
        $(".tab-line").css("margin-bottom","80px")
        if(((elemObj.parent("li").index()==1)&&($(".tab-line").width()<1220))||((elemObj.parent("li").index()==2)&&($(".tab-line").width()<960))){
            $(".tab-line").css("margin-bottom","110px")
        }
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
		$s.checkAllTag['search']=$s.search;
		$s.getDataList();
	}

	/*数据导出*/
	$s.studentDataExport=function(){
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
				$s.objElement[key].parent().removeClass('active')
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

/*-----------------------------------编辑学员 弹出层操作------------------------------------------------*/
	/*include 加载完成后执行*/
	$s.sutdentEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.student-alert").fadeOut("fast");
		});
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
		var json={
				phoneNum:$s.editData.phoneNum,
				sex:parseInt($s.editData.sex),
				applyCarType:parseInt($s.editData.applyCarType),
				idNumber:$s.editData.idNumber,
				name:$s.editData.name,
				studentId:$s.editData.studentId,
				schoolId:$s.editData.schoolId
			};
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*加载驾校列表*/
    $s.getSchoolDatas=function(cityId){
    	//console.log(cityId)
    	if(cityId){
    		$s.cityDataError=false;
    		/*加载驾校列表*/
			querySchool({
				cityId:cityId,
				callback:function(data){
					$s.schools=data;
					$s.$apply();
				}
			});
    	}else{
    		$s.schools=""; //清空城市列表数据
    		$s.cityDataError=true;
    	}
    }

    //检测是否存在cityId
    $s.checkIsCityId=function(cityId){
    	return $s.cityDataError=cityId?false:true;
    }

	/*修改 学员信息*/
	$s.submitEditMsg=function($event){
		var json=editStudentJson("student/update");
		//if(!$s.editData.name || !regCombination('special',[2,8]).test($s.editData.name)){
		//	Layer.alert({type:"msg",title:"请填写姓名(2-8个字符)"});
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
		/*ajax*/
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

	/*取消时执行*/
	$s.closeAlert=function(){
		$s.editData={};
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
			checkData:studentLiLiJurisConfig,
			callback:function(data){
				$s.studentStates=data;
				$s.haveStudentState=data.length?true:false;
			}
		});
    }

    /*include 加载完成后执行*/
	$s.changeErrorStudentLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.change-error-students").fadeOut("fast");
		});
		/*填写资料不全的原因*/
		$('#studentState').change(function(){
			if($(this).val()=='5,101'){
				$('#queshizl').show();
			}else{
				$('#queshizl').hide();
			}
		});
	}

   /*确认置学员状态*/
    $s.submitStudentState=function($event,incompleteData){
    	var studentState=$('#studentState').val();
    	if(!regCombination('*').test(studentState)){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择学员需要置的状态！"});
			return false;
		};
		if(studentState=='5,101'){
			if(!regCombination('*').test(incompleteData)){
				Layer.alert({width:300,height:150,type:"msg",title:"请补充资料不全的理由！"});
				return false;
			}
		};
    	$.AJAX({
			url:config.basePath+"student/reset-state",
			data:{
				applyexam:studentState.split(',')[0],
				applystate:studentState.split(',')[1],
				studentIds:$rootScope.idList.toString(),
				remarks:incompleteData,
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





	/*-----------------------------------资料不全--------------------------------------------------------*/
	/*include 加载完成后执行*/
	$s.infImperfect=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.data-imperfect").fadeOut("fast");
		});
	}

	/*加载是否有身份信息*/
	//$s.getStatusDatas=function(statusId){
	//	if(statusId){
	//		$s.statusDataError=false;
	//		$s.ptype=statusId;  //定义‘居民类别’全局变量
	//		var pmids = $s.statusdatas[statusId-1].pmid.split(",");
	//		var perdatas = $s.allperdatas;  //定义全局变量
	//		var newperdatas = new Array();
	//		for (var i = 0;i < pmids.length; i++){
	//			var pmid = pmids[i];
	//			for(var j = 0; j < perdatas.length; j++){
	//				if(pmid == perdatas[j].pmid){
	//					newperdatas.push(perdatas[j]);
	//				}
	//			}
	//		}
	//		$s.perdatas = newperdatas;
	//	}else{
	//		$s.isstatus="";
	//		$s.ptype="";
	//		$s.statusDataError=true;
	//		$s.perdatas = $s.allperdatas;
	//	}
	//}
	$s.getStatusDatas=function(statusId){
		$s.ptype=statusId;  //定义‘居民类别’全局变量
		var pmids = $s.statusdatas[statusId-1].pmid.split(",");  // 获取报名材料表id值
		var perdatas = $s.allperdatas;  //定义全局变量（获取报名资料）
		var newperdatas = new Array();  //定义一个空数组
		for (var i = 0;i < pmids.length; i++){
			var pmid = pmids[i];
			for(var j = 0; j < perdatas.length; j++){
				if(pmid == perdatas[j].pmid){
					newperdatas.push(perdatas[j]);
				}
			}
		}
		$s.perdatas = newperdatas;
	}

	/*资料已邮寄弹出层*/
	$s.dataHasMail=function(){
		$s.ptype="";
		/*未选中学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'资料待邮寄'的学员！"});
			return false;
		};

		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		$s.applyttid=datas[0].applyttid;  //获取到信息包id
		$s.studentId=datas[0].studentId;  //获取学员id
		var arrly=[]; //定义一个数组
		for (var i=0;i<datas.length;i++){
			arrly.push(datas[i].applyttid)  //将获取到applyttid注入到数组中
			var applyid=arrly.indexOf(arrly[i]);  //判断城市是否一致
		}
		/*判断是否为同一城市*/
/*		if(!(applyid==0)){
			Layer.alert({width:330,height:175,type:"msg",title:"您只能选择同一城市的学员，<br>请重新选择学员！"});
			return false;
		}*/

		/*选中学员状态为非‘资料待邮寄’时的提示*/
		if(!((datas[0].applyexam==4 || datas[0].applyexam==3) && datas[0].applystate==0)){	//20160901 未填写资料的也允许设置资料已邮寄
			Layer.alert({width:410,height:175,type:"msg",title:"您只能把'未邮寄资料'的学员标记为资料<br>已邮寄，请重新选择学员！"});
			return false;
		}
		if(datas[0].idNumber == null || datas[0].applyCarType == null ){	//20160905 未编辑添加身份证信息则不允许设置资料已邮寄
			Layer.alert({width:410,height:175,type:"msg",title:"请您先编辑添加学员身份证及<br>报考类别信息！"});
			return false;
		}

		Layer.confirm({width:300,height:160,title:"您确认收到了所选学员的<br>报名表资料吗？",header:"资料已邮寄"},function(){
			$.AJAX({
				url: config.basePath + "student/reset-state",
				data: {
					applyexam: 4,
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

	/*资料不全弹出层*/
	$s.dataImperfect=function(){
		$s.ptype="";
		/*未选中学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'资料已邮寄'的学员！"});
			return false;
		};

		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		$s.applyttid=datas[0].applyttid;  //获取到信息包id
		$s.studentId=datas[0].studentId;  //获取学员id
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
		if(!(datas[0].applyexam==4 && datas[0].applystate==1)){
			Layer.alert({width:410,height:175,type:"msg",title:"您只能把'资料已邮寄'的学员标记为资料<br>不全，请重新选择学员！"});
			return false;
		}

		/*获取'身份信息'及'报名资料'*/
		$.AJAX({
			url:config.basePath+"student/pack-detail",
			type:'get',
			data:{
				applyttid:$s.applyttid,//$s.applyttid
			},
			success:function(data){
				var statusdata=data.data.people;  //获取身份信息
				var perdata=data.data.items;  //获取报名资料
				/*数据渲染页面*/
				$s.statusdatas=statusdata;
				$s.perdatas=perdata;
				$s.allperdatas=perdata;
				$s.$apply();
			}
		})


		/*显示弹出层且垂直居中*/
		$(".data-imperfect").fadeIn("fast");  //显示弹出层
		$("#imperfect-alert").css("marginTop",parseInt(($(win).height()- $("#imperfect-alert").height()-100)/2)+"px");  //居中

		/*点点击确定按钮改变学员状态，进行判断且加载ajax*/
		$s.submitImperfectMsg = function ($event) {
			var idarr=$s.idList;  //获得报名资料id值和学员id值
			var newarr=idarr.shift();  //去除第一项选中的学员id
			//console.log($s.idList);
			console.log($s.ptype)
			if (!regCombination('*').test($s.ptype)) { //判断身份信息是否为空
				Layer.alert({width:300,height:150,type:"msg",title:"请选择身份信息！"});
				return false;
			}else if (!regCombination('*').test($s.textRemark)) { //判断缺失的资料说明是否为空
				Layer.alert({width:300,height:150,type:"msg",title:"请输入缺失的资料说明！"});
				return false;
			}
			$.AJAX({
				url: config.basePath + "student/reset-state",
				data: {
					applyexam: 4,
					applystate: 101,
					applyttid: $s.applyttid,
					pmid: $s.idList.toString(),
					ptype: $s.ptype,
					studentIds: $s.studentId,
					note: $s.textRemark,
				},
				success: function (data) {
					/*关闭弹出层*/
					$($event.target).parents("div.data-imperfect").fadeOut("fast");
					Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
					/*更新列表*/
					$s.getDataList();
				}
			});
			/*AJAX end*/
		}
	}



	/*-----------------------------------资料齐全--------------------------------------------------------*/
	/*include 加载完成后执行*/
	$s.infComplete=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.data-complete").fadeOut("fast");
		});
	}

	/*资料齐全-加载是否有身份信息 */
	$s.getStatusinf=function(statusId){
		//console.log($s.statusId)
		if(statusId){
			$s.ptypeed=statusId;
			$s.statusDataError=false;
		}else{
			$s.isstatus=""; //清空身份信息列表数据
			$s.statusDataError=true;
			$s.ptypeed="";
		}
	}


	/*资料齐全弹出层*/
	$s.dataComplete=function(){
		$s.ptypeed="";
		/*选中学员状态为非‘资料已邮寄’时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'资料已邮寄'的学员！"});
			return false;
		};

		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		$s.applyttid=datas[0].applyttid;  //获取到信息包id
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
		if(!(datas[0].applyexam==4 && datas[0].applystate==1)){
			Layer.alert({width:410,height:175,type:"msg",title:"您只能把'资料已邮寄'的学员标记为资料<br>不全，请重新选择学员！"});
			return false;
		}

		/*获取'身份信息'及'报名资料'*/
		$.AJAX({
			url:config.basePath+"student/pack-detail",
			type:'get',
			data:{
				applyttid:$s.applyttid,//$s.applyttid
			},
			success:function(data){
				var statusdata=data.data.people;  //获取身份信息
				/*数据渲染页面*/
				$s.statusdatas=statusdata;
				$s.$apply();
			}
		})

		/*显示弹出层且垂直居中*/
		$(".data-complete").fadeIn("fast");  //显示弹出层
		$("#complete-alert").css("marginTop",parseInt(($(win).height()- $("#complete-alert").height()-100)/2)+"px");  //居中

		/*点击确定按钮改变学员状态，进行判断且加载ajax*/
		$s.submitCompleteMsg=function($event){
			if (!regCombination('*').test($s.ptypeed)) { //判断身份信息是否为空
				Layer.alert({width:300,height:150,type:"msg",title:"请选择身份信息！"});
				return false;
			}
			$.AJAX({
				url: config.basePath + "student/reset-state",
				data: {
					applyexam: 4,
					applystate: 100,
					applyttid: $s.applyttid,
					ptype: $s.ptypeed,
					studentIds: $rootScope.idList.toString(),
				},
				success: function (data) {
					/*关闭弹出层*/
					$($event.target).parents("div.data-complete").fadeOut("fast");
					Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
					/*更新列表*/
					$s.getDataList();
				}
			});
			/*AJAX end*/
		}
	}


	/*-----------------------------------表已寄出--------------------------------------------------------*/
	$s.dataSent=function(){
		/*未选学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'未交表'的学员！",header:"表已寄出"});
			return false;
		};
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		//console.log($rootScope.idList);
		
		$s.applyttid=datas[0].applyttid;  //获取到信息包id
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

		if(!(datas[0].applyexam==5 && datas[0].applystate==0)){  /*选中学员状态为非‘未交表’时的提示*/
			Layer.alert({width:355,height:175,type:"msg",title:"您只能把'未交表'的学员标记为表已<br>寄出，请重新选择学员！",header:"表已寄出"});
			return false;
		}
		Layer.confirm({width:300,height:160,title:"您确认寄出所选学员的报<br>名表资料吗？",header:"表已寄出"},function(){
			$.AJAX({
				url: config.basePath + "student/reset-state",
				data: {
					applyexam: 5,
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


	/*-----------------------------------学员封号--------------------------------------------------------*/
	$s.sutdentLockEditLoad = function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.student-lock-alert").fadeOut("fast");
		});
		/*时间输入控件*/
		$('#reservation').daterangepicker({'format': 'YYYY-MM-DD','singleDatePicker': true,'autoclose': true, "timePicker": true,"timePicker24Hour": true, "timePickerSeconds": true,"autoApply": true})
	}

	$s.editData.state = 1;//默认为临时封号
	$s.editData.lockManRequire = "";
	$s.lockTimeShow = function($event){
		$(".student-lock-edit-time").slideDown("fast");
		$s.editData.state = $($event.target).val();
	}
	$s.lockTimeHidden = function($event){
		$(".student-lock-edit-time").slideUp("fast");
		$s.editData.state = $($event.target).val();
	}
	
	$s.dataLock=function(){
		
		/*未选学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择要封号的学员！",header:"学员封号"});
			return false;
		};

		//弹出编辑层
		$(".student-lock-alert").fadeIn("fast");

		/*弹出层垂直居中*/
		$("#edit-content").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");

		$s.submitStudentLock = function(){
			if(!$s.editData.note){
				Layer.alert({width:300,height:150,type:"error",title:"请填写封号/解封理由"});
				return false;
			}
			//根据所选操作，制定检查数据和提示信息
			var studentLockTittle = "";
			var studentLockRequire= "";
			switch(parseInt($s.editData.state)){
				case 0:
					studentLockTittle = "确定将此学员帐号解封？";
					studentLockRequire = "被封号";
					break;
				case 1:
					studentLockTittle = "确定将此学员帐号临时封号？";
					studentLockRequire = "正常";
					break;
				case 2:
					studentLockTittle = "确定将此学员帐号永久封号？";
					studentLockRequire = "正常";
					break;
			}

			/*获得选择的data数据*/
			var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
			/*只能选择同一组别的成员（封号或未封号）*/
			var dataOK = true;//选择的数据，默认为合格
			for (var i=0;i<datas.length;i++){
				if($s.editData.state == datas[i].state){//
					dataOK = false; break; //数据不合格，跳出
				}
			}
			/*判断数据是否合格*/
			if(!dataOK){
				Layer.alert({width:430,height:175,type:"msg",title:"您只能选择状态"+studentLockRequire+"的学员，<br>请重新选择！"});
				return false;
			}
			Layer.confirm({width:300,height:160,title:studentLockTittle,header:"学员封号"},function(){
				$.AJAX({
					url:config.basePath+"student/lock-batch",
					data:{
						ids: $rootScope.idList.toString(),
						state:$s.editData.state,
						reviveTime: $s.editData.reviveTime,
						note:$s.editData.note
					},
					success:function(data){
						/*关闭弹出层*/
						Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
						/*更新列表*/
						$s.getDataList();
						$("div.student-lock-alert").fadeOut("fast");
					}
				})
			})
		}
		
		
	}

}]);