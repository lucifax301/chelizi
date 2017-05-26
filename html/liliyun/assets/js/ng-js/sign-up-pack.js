/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("Pack",["$scope","$filter",function($s,$filter){

/*-----------------------------------------查询报名包列表----------------------------------------------------*/	
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数

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
	//或得的数据列表
	//$s.datas=$s.data.dataList;

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
				$s.$apply();
				console.log($s.datas);
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
			url:config.basePath+"ept/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize)
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
			//console.log(data.result.pageData);
			$s.citys=JSON.parse(data.result.pageData);
			//console.log($s.citys);
		}
	});



/*------------------------------------------编辑 | 新增 训练场---------------------------------------------*/

	/*include 加载完成后执行*/
	$s.packEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.pack-alert").fadeOut("fast");
		})
	}
	
	/*点击 编辑|新增 训练场信息*/
	$s.editData={};
	$s.editType="add";
	$s.packEdit=function(type,data){
		$(".pack-alert").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#edit-content").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");
		if(type=="edit"){
		/*修改*/
			$s.editData = data;
			//console.log(data);
			if(data.cityId){$s.editData.region = data.cityId;}
			$s.editType="edit";
		}else{
		/*新增*/
			$s.editData={};

			$s.editType="add";
		}
	}

	/*参数配置函数*/
	function editPackJson(url){
		var json={
				name:$s.editData.name,
				//region:"东莞市",
				//schoolAdress:$s.editData.schoolAdress,
				phoneNum:$s.editData.phoneNum

			};
		if($s.editType=="edit"){
			angular.extend(json,{fieldId:$s.editData.fieldId});
		}	
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*修改 | 新增 训练场信息*/
	$s.submitEditMsg=function($event){
		var json=$s.editType=="add"?editPackJson("ept/add"):editPackJson("ept/update");
		if(!$s.editData.name || !regCombination('*').test($s.editData.name)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写驾校名称"});
			return false;
		}

/*		if(!$s.editData.region || !regCombination('*').test($s.editData.region)){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择所在城市"});
			return false;	
		}*/

		

		$.AJAX({
			url:json.url,
			data:json.data,
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.add-new-school").fadeOut("fast");
				/*更新列表*/
				$s.getDataList();
			},
			error:function(XMLHttpRequest){
				console.log(XMLHttpRequest);
			}
		});/*AJAX end*/
	}





}]);