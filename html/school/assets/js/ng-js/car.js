/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("Student",["$scope","$filter",function($s,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.carType="";           //所学车型
	$s.carLevel="";   //汽车等级
	$s.carNo="";              //高级查询
	$s.remark="";
	// /*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{carId:201,carNo:"粤B5312",driveType:1,student:"小张",sex:"男",phone:"13476002586",carLevel:1,coach:"张教练",nameId:"422802198910116575",drivingSchool:"深港",accountStatus:0},
			
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
			url:config.basePath+"car/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "carType": $s.carType,
			    "carLevel": $s.carLevel,
			    "carNo": $s.carNo,
			    "schoolName":$s.schoolName,
			    "remark":$s.remark
			}
		};
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
		$s.getDataList();
	}

	/*按汽车等级筛选列表数据*/
	$s.getDataForAccountStatus=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.carLevel=type;
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

	/*按驾校筛选列表数据*/
	$s.getDataForSchool=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.schoolName=$s.schoolNameSelect;
		$s.getDataList();
	}

	/*数据导出*/
	$s.carDataExport=function(){
		//调起数据导出
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'car/export-excel',
		});
	}
	
/*------------------------------------------编辑 | 新增 教练车---------------------------------------------*/

	/*include 加载完成后执行*/
	$s.carEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.edit-alert").fadeOut("fast");
		})
	}
	
	/*点击 编辑|新增 教练信息*/
	$s.editData={};
	$s.editType="add";
	$s.carEdit=function(type,data){
		$(".edit-alert").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#edit-content").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");
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
	
	$s.carDel=function(data){
		Layer.confirm({width:350,height:160,title:"确认删除车辆"+data.carNo+"？",header:"确认"},function(){
			$.AJAX({
				url: config.basePath+"car/delete",
				type : "POST",
				data: {
					carId: data.carId
				},
				success : function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			});
		});
	}
	

	/*参数配置函数*/
	function editCarJson(url){
		var json={
				carNo:$s.editData.carNo,
				remark:$s.editData.remark,
			//	carLevel:parseInt($s.editData.carLevel),
			//	driveNumber:$s.editData.driveNumber,
				driveType:parseInt($s.editData.driveType)
			};
		if($s.editType=="edit"){
			angular.extend(json,{carId:$s.editData.carId});
		}	
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*修改 | 新增 教练车信息*/
	$s.submitEditMsg=function($event){
		var json=$s.editType=="add"?editCarJson("car/add"):editCarJson("car/update");

		//if(!$s.editData.carNo || !regCombination('special').test($s.editData.carNo)){
		if(!$s.editData.carNo ){
			Layer.alert({type:"msg",title:"请填写车牌号"});
			return false;	
		}
		//if(!$s.editData.driveType || !regCombination('*').test($s.editData.driveType)){
		//	Layer.alert({type:"msg",title:"请选择驾驶类别"});
		//	return false;
		//}
		//if(!$s.editData.carLevel || !regCombination('*').test($s.editData.carLevel)){
		//	Layer.alert({type:"msg",title:"请选择汽车等级"});
		//	return false;
		//}
		//if(!$s.editData.driveNumber || !regCombination('special').test($s.editData.driveNumber)){
		//	Layer.alert({type:"msg",title:"请填写行驶证编号"});
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

}]);