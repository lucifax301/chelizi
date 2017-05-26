/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("Coach",["$scope","$filter",function($s,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;
	$s.pageSize=10;    //每页显示-显示条数
	$s.accountStatus=null;   //账号状态
	$s.search="";            //高级查询
	$s.searchType="name";    //默认搜索字段

    $s.showWhiteList = false;
    if((sessionStorage.getItem("schoolUserName")=="sgjx")||(sessionStorage.getItem("schoolUserName")=="szga")||(sessionStorage.getItem("schoolUserName")=="szgr")){$s.showWhiteList=true;}

	/*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{coachId:101,name:"张教练",sex:0,phoneNum:"13476002586",applyCarType:0,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},

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
			url:config.basePath+"coach/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "accountStatus": $s.accountStatus,
			    "studentSource":$s.studentSource
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

	/*按账号状态筛选列表数据*/
	$s.getDataForAccountStatus=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.accountStatus=type;
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
		//调起数据导出
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
		console.log(data)
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
		if(!$s.editData.name){
			Layer.alert({type:"msg",title:"请填写姓名"});
			return false;
		}
		if(!$s.editData.sex){
			Layer.alert({type:"msg",title:"请选择性别"});
			return false;
		}
		if(!$s.editData.phoneNum || !regCombination('phone').test($s.editData.phoneNum)){
			Layer.alert({type:"msg",title:"请填写手机正确的号码"});
			return false;
		}
		if(!$s.editData.idNumber || !regCombination('id').test($s.editData.idNumber)){
			Layer.alert({type:"msg",title:"请填写正确的身份证号码"});
			return false;
		}

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

}]);