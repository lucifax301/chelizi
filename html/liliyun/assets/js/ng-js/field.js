/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Student",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.name="";    //高级查询
	$s.schoolNo=""; //学校ID
	$s.cityId="";  //城市ID

	/*-----------------------------------------查询训练场数据列表----------------------------------------------------*/
	/*模拟数据*/
	 $s.data={
	 	pages:10,
	 	total:100,
	 	pageSize:10,
	 	pageNo:1,
	 	dataList:[
	 		{fieldId:201,name:"深圳港",reverseLim:"1000",phoneNum:"13476225415",posDesc:"这里面是深圳港的介绍" },
			
	 	]
	 };
	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'fieldId'});
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
				Selects.selects({datas:$s.datas,whichId:'fieldId'});
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
			url:config.basePath+"field/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "name": $s.name,
			    "schoolNo":$s.schoolNo,
				"region":$s.cityId,
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
	
	/*加载驾校数据列表*/
	$.AJAX({
		type:'get',
		url:config.basePath+"school/query",
		success:function(data){
			$s.schools=JSON.parse(data.result.pageData);
			$s.$apply();
		}
	});

	/*按城市筛选数据*/
	$s.getDataForCity=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.cityId=$s.cityId;
		$s.getDataList();
	}

	/*按学员来源按驾校筛选列表数据*/
	$s.getDataForSchool=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.schoolNo=$s.schoolNo;
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
	$s.fieldDataExport=function(){
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'field/export-excel',
		});
	}

	/*------------------------------------------激活启用---------------------------------------------*/
	$s.fieldDisconnected=function(){
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'fieldId',idList:$rootScope.idList});
		var isdel=true;   //isdel启用状态
		for (var i=0;i<datas.length;i++){
			if (datas[i].isdel != 1) {
				isdel=false;
				break;
			}
		}
		/*激活启用选择为空时*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'已停用'的状态！",header:"激活启用"});
			return false;
		};
		if(isdel==false){
			Layer.alert({width:300,height:150,type:"msg",title:"您只能选择'已停用'的状态！",header:"激活启用"});
			return false;
		}
		Layer.confirm({width:315,height:160,title:"您确认激活启用该训练场？",header:"激活启用"},function(){
			$.AJAX({
				url:config.basePath+"field/isDel",
				data:{
					idList:$rootScope.idList.toString(),
					isDel:0,
				},
				success:function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			})
		})
	}

	/*------------------------------------------停用维护---------------------------------------------*/
	$s.fieldMaintenance=function(){
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'fieldId',idList:$rootScope.idList});
		var isdel=true;   //isdel启用状态
		for (var i=0;i<datas.length;i++){
			if (datas[i].isdel != 0) {
				isdel=false;
				break;
			}
		}
		/*激活启用选择为空时*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'启用中'的状态！",header:"停用维护"});
			return false;
		};
		if(isdel==false){
			Layer.alert({width:300,height:150,type:"msg",title:"您只能选择'启用中'的状态！",header:"停用维护"});
			return false;
		}
		Layer.confirm({width:315,height:160,title:"您确认停用维护该训练场？",header:"停用维护"},function(){
			$.AJAX({
				url:config.basePath+"field/isDel",
				data:{
					idList:$rootScope.idList.toString(),
					isDel:1,
				},
				success:function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			})
		})
	}

	/*------------------------------------------编辑 | 新增 训练场---------------------------------------------*/

	/*include 加载完成后执行*/
	$s.siteEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.edit-alert").fadeOut("fast");
		})
	}
	
	/*点击 编辑|新增 训练场信息*/
	$s.editData={};
	$s.editType="add";
	$s.siteEdit=function(type,data){
		$(".edit-alert").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#edit-content").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");
		if(type=="edit"){
		/*修改*/
			$s.editData = data;
			if($s.editData.region!=null && $s.editData.region!=""){
				var region=$s.editData.region.toString();
				$s.editData.cityId=region.substring(0,6);
				//$s.cityId=region.substring(0,6);
				$s.getSchools($s.editData.cityId);
		        $s.editData.region=data.region.toString();
				$s.editData.schoolId=data.schoolId.toString();
			}

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
				name:$s.editData.name,
				reverseLim:parseInt($s.editData.reverseLim),
				phoneNum:$s.editData.phoneNum,
				posDesc:$s.editData.posDesc,
				schoolId:$s.editData.schoolId,
                region:$s.editData.region
			};
		if($s.editType=="edit"){
			angular.extend(json,{fieldId:$s.editData.fieldId});
		}	
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*--------------------------选择城市和驾校--------------------------------------*/
	/*加载城市列表*/
	queryCity({
		callback:function(data){
			$s.citys=data; $s.$apply();
		}
	});


    /*加载驾校列表*/
    $s.getSchools=function(cityId){
     	$s.cityId=cityId;
        $s.city = $("#city_id option:selected").text();
    	if($s.cityId){
    		$s.checkHaveCity(); //判断是否选择城市
    		$('#select-school li').last().addClass('active').siblings().removeClass("active");
    		/*加载驾校列表*/
			$.ajax({
				type:"get",
				async:false,
				timeout:10000,
				url:config.basePath+"school/query",
				data:{
					cityId:cityId,   //驾校id
				},
				success:function(data){
					$s.schools=getListData(data);
					$s.$apply();
				}
			});

			$.ajax({
				type:"get",
				async:false,
				url:config.basePath+"school/queryCity",
				timeout:10000,
				data:{
					rlevel:3,  //区域等级:1代表省，2代表市，3代表区
					pid:cityId,   //父节点id
					shield:0
				},
				success:function(data){
					$s.areas=getListData(data);
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
    /*获得schoolId*/
    //$s.getSchoolId=function(schoolNo){
    //	$s.editData.schoolId=schoolNo;
    //}
    /*--------------------------选择城市和驾校--------------------------------------*/

	/*修改 | 新增 训练场信息*/
	$s.submitEditMsg=function($event){
		var json=$s.editType=="add"?editCarJson("field/add"):editCarJson("field/update");
        //console.log("name:"+$s.editData.name+",面积范围:"+$s.editData.reverseLim+",电话:"+$s.editData.phoneNum+",驾校:"+$s.editData.schoolId+""+""+""+""+""+""+)
		if(!$s.editData.name || !regCombination('*').test($s.editData.name)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写场地名"});
			return false;	
		}
		if(!$s.editData.reverseLim || !regCombination('number').test($s.editData.reverseLim)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写正确的面积范围"});
			return false;	
		}
		if(!$s.editData.phoneNum || !regCombination('phone').test($s.editData.phoneNum)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写正确的联系电话"});
			return false;	
		}
		if(!$s.editData.schoolId || !regCombination('*').test($s.editData.schoolId)){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择驾校"});
			return false;	
		}
		if(!$s.editData.posDesc || !regCombination('*').test($s.editData.posDesc)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写地理位置"});
			return false;	
		}

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