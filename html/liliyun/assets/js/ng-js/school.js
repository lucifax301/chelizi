/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("School",["$scope","$filter",function($s,$filter,$scope){

/*-----------------------------------------查询训练场数据列表----------------------------------------------------*/	
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.name="";    //高级查询
	$s.checkSchoolSameName = 0//检查同名驾校

	// /*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{schoolId:201,name:"深港",region:"广东-深圳",reverseLim:"1000",phoneNum:"13476225415",coachCount:25121,posDesc:"这里面是深圳港的介绍" },
			
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
				//console.log($s.datas);
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
			url:config.basePath+"school/querySchool",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "cityId":$s.cityId,
			    "name": $s.name
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

	/*加载城市数据列表*/
	$.AJAX({
		type:'get',
		url:config.basePath+"school/queryCity",
		data:{
			"rlevel":2,
			"pid":"",
			shield:0
		},
		success:function(data){
			/*数据渲染页面*/
			//console.log(data);
			$s.citys=JSON.parse(data.result.pageData);
			//console.log($s.citys);
		}
	});

	/*按所在城市筛选列表数据*/
	$s.getDataForCity=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.cityId=$("#getDataForCitySel").val();
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

/*------------------------------------------编辑 | 新增 驾校---------------------------------------------*/

	/*include 加载完成后执行*/
	$s.schoolEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.school-alert").fadeOut("fast");
		})
	}
	
	/*点击 编辑|新增 训练场信息*/
	$s.editData={};
	$s.editType="add";
	$s.schoolEdit=function(type,data){
		$(".school-alert").fadeIn("fast");
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
	function editSchoolJson(url){
		var json={
				schoolNo:$s.editData.schoolId,
				name:$s.editData.name,
				cityId:parseInt($s.editData.cityId),
				address:$s.editData.address,
				phoneNum:$s.editData.phoneNum
			};

		if($s.editType=="edit"){
			angular.extend(json,{schoolId:$s.editData.schoolId});
		}	
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*修改 | 新增 驾校信息*/
	$s.submitEditMsg=function($event){
		$s.checkSchoolSameName = 0;
		var json=$s.editType=="add"?editSchoolJson("school/add"):editSchoolJson("school/update");
		if(!$s.editData.name || !regCombination('*').test($s.editData.name)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写驾校名称"});
			return false;
		}
		if(!$s.editData.cityId){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择驾校城市"});
			return false;
		}

		checkDataList({name:$s.editData.name},"school/querySchool");

		if($s.checkSchoolSameName > 0){
			Layer.alert({width:300,height:150,type:"msg",title:"该驾校已存在"});
			return false;
		}

		$.AJAX({
			url:json.url,
			data:json.data,
			type:'get',
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.school-alert").fadeOut("fast");
				/*更新列表*/
				$s.getDataList();
			}
		});/*AJAX end*/

		/*检查重名*/
		function checkDataList(checkData,queryUrl){
			//判断驾校重复

			$.AJAX({
				url:config.basePath+queryUrl,
				type:'get',
				async:false,
				data:checkData,
				timeout:1000000,
				success:function(data){
					var DATA = JSON.parse(data.result.pageData).dataList;
					//var dataNum = ;
					//console.log("查出同名的驾校数量："+dataNum+",更新方式："+$s.editType)
					//新增时直接检查是否存在重名项
					if(DATA.length>0 && $s.editType=="add"){
						$s.checkSchoolSameName = 1;
					}
					//编辑时检查现有重名项的ID与当前编辑项ID是否一致，如不一致，则不通过
					if(DATA.length>1 && $s.editType=="edit"){
						alert("编辑，多项同名直接阻止")
						//console.log("编辑时的数据"+DATA+",查出来的数据条目："+DATA.length);
						$s.checkSchoolSameName = 1;
					}
					if(DATA.length=1 && $s.editType=="edit" && DATA[0].schoolId && DATA[0].schoolId != $s.editData.schoolId){
						//console.log(DATA);
						//console.log("编辑，仅一项同名且ID不一样，查到的已有ID为："+ DATA[0].schoolId+",当前编辑的ID为："+$s.editData.schoolId)
						//console.log("编辑时的数据"+DATA+",查出来的数据条目："+DATA.length);
						$s.checkSchoolSameName = 1;
					}
					//console.log(DATA+"DATA的长度为"+DATA.length+",dataNum为："+DATA.length);

					//$s.$apply();
				}
			})
		}

	}

	
















}]);