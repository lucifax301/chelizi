/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("car-brand",["$scope","$filter",function($s,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.carType="";     //所学车型
	$s.brandNo="";   //品牌编码
	$s.brandName=""; //品牌名称
	$s.carNo="";   //汽车编码
	$s.carName="";   //汽车名称
	$s.isCommon=0;   //是否常用

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
	/*参数配置函数,配置请求参数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"brand-car/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "brandname": $s.brandname,
				"carname": $s.carname
			}
		};
		return json;
	};

	/*加载品牌数据列表*/
	$.AJAX({
		type:'get',
		url:config.basePath+"brand-car/all-brand",
		success:function(data){
			$s.brands=JSON.parse(data.result.pageData);
			$s.$apply();
		}
	});
	/*加载车系数据列表*/
	$.AJAX({
		type:'get',
		url:config.basePath+"brand-car/car",
		data:{
			brandname:$s.brandName,
		},
		success:function(data){
			$s.carBrands=JSON.parse(data.result.pageData);
			$s.$apply();
		}
	});



	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getDataList();
	}

	/*按品牌筛选列表数据*/
	$s.getBrandName=function(brandname){
		win.showLoading();
		$s.brandName=brandname;
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*按车系筛选列表数据*/
	$s.getDataForCarName=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.seqid=type;
		$s.getDataList();
	}
	
/*------------------------------------------编辑 | 新增 车型---------------------------------------------*/

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

	/*参数配置函数*/
	function editCarJson(url){
		var json={
				carNo:$s.editData.carNo,
				carLevel:parseInt($s.editData.carLevel),
				driveNumber:$s.editData.driveNumber,
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
		var json=$s.editType=="add"?editCarJson("brand-car/add"):editCarJson("brand-car/update");

		if(!$s.editData.carNo || !regCombination('special').test($s.editData.carNo)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写车牌号"});
			return false;
		}
		//if(!$s.editData.driveType || !regCombination('*').test($s.editData.driveType)){
		//	Layer.alert({width:300,height:150,type:"msg",title:"请选择驾驶类别"});
		//	return false;
		//}
		//if(!$s.editData.carLevel || !regCombination('*').test($s.editData.carLevel)){
		//	Layer.alert({width:300,height:150,type:"msg",title:"请选择汽车等级"});
		//	return false;
		//}
		//if(!$s.editData.driveNumber || !regCombination('special').test($s.editData.driveNumber)){
		//	Layer.alert({width:300,height:150,type:"msg",title:"请填写行驶证编号"});
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