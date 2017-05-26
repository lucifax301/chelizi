/*angular for 教练*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Coach",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.state=null;   //账号状态
	$s.search="";            //高级查询
	$s.searchType="name";    //默认搜索字段
	$s.schoolNo=""; //学校ID

	/*模拟数据*/
	$s.data={
		pages:10,
		total:100,
		pageSize:10,
		pageNo:1,
		dataList:[
			{coachId:101,name:"张教练",sex:0,phoneNum:"13476002586",applyCarType:0,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
		
		]
	}; 
	//或得的数据列表
	$s.datas=$s.data.dataList;

	
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
				Selects.selects({datas:$s.datas,whichId:'coachId',num:''});
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
			url:config.basePath+"coach/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "state": $s.state,
			    "isImport":1,
			    "schoolNo":$s.schoolNo
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
	
	/*加载驾校数据列表*/
	//$.AJAX({
	//	type:'get',
	//	url:config.basePath+"school/query",
	//	success:function(data){
	//		$s.schools=JSON.parse(data.result.pageData);
	//		$s.$apply();
	//	}
	//});

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
	

	/*按学员来源按驾校筛选列表数据*/
	$s.getDataForSchool=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.schoolNo=$s.schoolNo;
		$s.getDataList();
	}

	/*按账号状态筛选列表数据*/
	$s.getDataForAccountStatus=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.state=type;
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
	$s.coachDataExport=function(){
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'coach/export-excel',
		});
	}

/*-------------------------------教练 新增|修改 弹出层操作---------------------------------------*/
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
	
	/*点击 编辑|新增 教练信息*/
	$s.editData={};
	$s.editType="add";
	$s.coachEdit=function(type,data){
		$(".edit-coach").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#edit-coach").css("marginTop",parseInt(($(win).height()- $("#edit-coach").height()-100)/2)+"px");
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
	function editCoachJson(url){
		var json={
				phoneNum:$s.editData.phoneNum,
				sex:parseInt($s.editData.sex),
				idNumber:$s.editData.idNumber,
				name:$s.editData.name
			};
		if($s.editType=="edit"){
			angular.extend(json,{coachId:$s.editData.coachId});
		}	
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*修改 | 新增 教练信息*/
	$s.submitEditMsg=function($event){
		var json=$s.editType=="add"?editCoachJson("coach/add"):editCoachJson("coach/update");
		//if(!$s.editData.name || !regCombination('chinese',[2,8]).test($s.editData.name)){
		//	Layer.alert({width:300,height:150,type:"msg",title:"请填写姓名(2-8个中文)"});
		//	return false;
		//}
		//if(!$s.editData.sex.toString() || !regCombination('*').test($s.editData.sex)){
		//	Layer.alert({width:300,height:150,type:"msg",title:"请选择性别"});
		//	return false;
		//}
		//if(!$s.editData.phoneNum || !regCombination('phone').test($s.editData.phoneNum)){
		//	Layer.alert({width:300,height:150,type:"msg",title:"请填写手机正确的号码"});
		//	return false;
		//}
		if($s.editData.phoneNum.length < 11 || !regCombination('*').test($s.editData.phoneNum)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写手机正确的号码"});
			return false;
		}
		//if(!$s.editData.idNumber || !regCombination('id').test($s.editData.idNumber)){
		//	Layer.alert({width:300,height:150,type:"msg",title:"请填写正确的身份证号码"});
		//	return false;
		//}
		
		$.AJAX({
			url:json.url,
			data:json.data,
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.edit-coach").fadeOut("fast");
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


	/*-----------------------------------教练封号--------------------------------------------------------*/

	$s.coachLockEditLoad = function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.coach-lock-alert").fadeOut("fast");
		});
		/*时间输入控件*/
		$('#reservation').daterangepicker({'format': 'YYYY-MM-DD','singleDatePicker': true,'autoclose': true, "timePicker": true,"timePicker24Hour": true, "timePickerSeconds": true,"autoApply": true})
	}

	$s.editData.state = 1;//默认为临时封号

	$s.lockTimeShow = function($event){$(".coach-lock-edit-time").slideDown("fast");$s.editData.state = $($event.target).val();}
	$s.lockTimeHidden = function($event){$(".coach-lock-edit-time").slideUp("fast");$s.editData.state = $($event.target).val();}
	
	$s.dataLock=function(){
		
		/*未选教练时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择要封号的教练！",header:"教练封号"});
			return false;
		};

		//弹出编辑层
		$(".coach-lock-alert").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#edit-content").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");

		$s.submitCoachLock = function(){
			if(!$s.editData.note){
				Layer.alert({width:300,height:150,type:"error",title:"请填写封号/解封理由"});
				return false;
			}
			//根据所选操作，制定检查数据和提示信息
			var coachLockTittle = "";
			var coachLockRequire= "";
			switch(parseInt($s.editData.state)){
				case 0:
					coachLockTittle = "确定将此教练帐号解封？";
					coachLockRequire = "被封号";
					break;
				case 1:
					coachLockTittle = "确定将此教练帐号临时封号？";
					coachLockRequire = "正常";
					break;
				case 2:
					coachLockTittle = "确定将此教练帐号永久封号？";
					coachLockRequire = "正常";
					break;
			}

			/*获得选择的data数据*/
			var datas=getDataForKey({datas:$s.datas,id:'coachId',idList:$rootScope.idList});
			/*只能选择同一组别的成员（封号或未封号）*/
			var dataOK = true;//选择的数据，默认为合格
			for (var i=0;i<datas.length;i++){
				if($s.editData.state == datas[i].state){//
					dataOK = false; break; //数据不合格，跳出
				}
			}
			/*判断数据是否合格*/
			if(!dataOK){
				Layer.alert({width:430,height:175,type:"msg",title:"您只能选择状态相同的、状态"+coachLockRequire+"的教练，<br>请重新选择教练！"});
				return false;
			}

			//alert($s.coachLockTittle)
			Layer.confirm({width:300,height:160,title:coachLockTittle,header:"教练封号"},function(){
				$.AJAX({
					url:config.basePath+"coach/lock-batch",
					data:{
						ids: $rootScope.idList.toString(),
						state:$s.editData.state,
						reviveTime: $s.editData.reviveTime,
						note:$s.editData.note
					},
					type:"post",
					success:function(data){
						/*关闭弹出层*/
						Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
						/*更新列表*/
						$s.getDataList();
						$("div.coach-lock-alert").fadeOut("fast");
					}
				})
			})
		}
		
		
	}

}]);