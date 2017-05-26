/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("Agreement",["$scope","$filter",function($s,$filter,$scope){

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
			url:config.basePath+"html-object/batch",
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


	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}


/*------------------------------------------编辑 | 新增 驾校---------------------------------------------*/

	/*include 加载完成后执行*/
	$s.agreementEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.agreement-alert").fadeOut("fast");
		})
	}
	
	/*点击 编辑|新增 训练场信息*/
	$s.editData={};
	$s.editType="add";
	$s.agreementEdit=function(type,data){
		$(".agreement-alert").fadeIn("fast");
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
	function editAgreementJson(url){
		var json={
				hname:$s.editData.hname,
				hdescription:$s.editData.hdescription
			};

		if($s.editType=="edit"){
			angular.extend(json,{hid:$s.editData.hid});
		}	
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*修改 | 新增 训练场信息*/
	$s.submitEditMsg=function($event){
		var json=$s.editType=="add"?editAgreementJson("html-object/add"):editAgreementJson("html-object/update");
		if(!$s.editData.hname || !regCombination('*').test($s.editData.hname)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写协议名称"});
			return false;
		}
		if(!$s.editData.hdescription){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写协议内容"});
			return false;
		}


		$.AJAX({
			url:json.url,
			data:json.data,
			type:'get',
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.agreement-alert").fadeOut("fast");
				/*更新列表*/
				$s.getDataList();
			}
		});/*AJAX end*/

		

	}

	
















}]);