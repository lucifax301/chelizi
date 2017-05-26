/*angular for 考场管理*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("ExamGround",["$scope","$rootScope","$filter",function($s,$rootScope,$filter){

	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.name="";    //高级查询
	$s.schoolNo=""; //学校ID
	$s.cityId="";  //城市ID

    var typeArr = [];//考场类型


	/*-----------------------------------------查询训练场数据列表----------------------------------------------------*/

	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
                console.log(DATA)
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
			url:config.basePath+"examPlace",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "name": $s.name,
			    "schoolId":$s.schoolNo,
				"cityId":$s.cityId,
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

	/*按驾校筛选数据*/
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


	/*---------编辑 | 新增 训练场-----------------------*/

	/*include 加载完成后执行*/
	$s.siteEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.edit-alert").fadeOut("fast");
		})
	}
	
	/*点击 编辑|新增 考场信息*/
	$s.editType="add";
	$s.siteEdit=function(type,data){
		$(".edit-alert").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#edit-content").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");
		if(type=="edit"){
		/*修改*/
			$s.editData = data;
			$s.editType="edit";
            $s.titleText = "编辑";
		}else{
		/*新增*/
			$s.editData={};
            $s.editData.state=-1;
			$s.editType="add";
            $s.titleText = "新增";
		}
	}

    /*点选考场启用状态*/
    $s.setExamState = function(examval){
        $s.editData.state = examval;
        console.log($s.editData.state);
    }

	/*参数配置函数*/
	function editExamGroundJson(url){
        typeArr=[];
		var json={
            name:$s.editData.name,
            area:parseInt($s.editData.area),
            mobile:$s.editData.mobile,
            address:$s.editData.address,
            schoolId:$s.editData.schoolId,
            cityId:$s.editData.cityId,
            state:$s.editData.state
        };

        //考场类型数据
        $(".examtype").each(function(){
            if($(this).prop("checked")){typeArr.push($(this).attr("value"))}
        })
        $s.editData.type = typeArr.join();
        json.type = $s.editData.type;

		if($s.editType=="edit"){
			angular.extend(json,{placeId:$s.editData.id});
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
    /*获得schoolId*/
    $s.getSchoolId=function(schoolNo){
    	$s.editData.schoolId=schoolNo;
    }
    /*--------------------------选择城市和驾校--------------------------------------*/

	/*修改 | 新增 训练场信息*/
	$s.submitEditMsg=function($event){
		var json=$s.editType=="add"?editExamGroundJson("examPlace"):editExamGroundJson("examPlace/update");
        //console.log("name:"+$s.editData.name+",面积范围:"+$s.editData.reverseLim+",电话:"+$s.editData.phoneNum+",驾校:"+$s.editData.schoolId+""+""+""+""+""+""+)
		if(!$s.editData.name || !regCombination('*').test($s.editData.name)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写场地名"});
			return false;	
		}
		if(!$s.editData.area){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写正确的面积范围"});
			return false;	
		}
		if(!$s.editData.mobile || !regCombination('phone').test($s.editData.mobile)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写正确的联系电话"});
			return false;	
		}
		if(!$s.editData.schoolId || !regCombination('*').test($s.editData.schoolId)){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择驾校"});
			return false;	
		}
        if(!$s.editData.address || !regCombination('*').test($s.editData.posDesc)){
            Layer.alert({width:300,height:150,type:"msg",title:"请填写地理位置"});
            return false;
        }
        if(typeArr.length==0){
            Layer.alert({width:300,height:150,type:"msg",title:"请勾选考场所含科目"});
            return false;
        }
        if($s.editData.state<0){
            Layer.alert({width:300,height:150,type:"msg",title:"请点选考场启用状态"});
            return false;
        }

		$.AJAX({
			url:json.url,
			data:json.data,
			success:function(data){
                $s.schoolNo=""; //置空学校ID
                $s.cityId="";  //置空城市ID
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


    $s.testdata = [{"cityId":0,"city":"长沙市","school":null,"step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":[{"cityId":103100,"city":"长沙市","school":"万马驾校","step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":122,"cmsChannel":null}],"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":null,"cmsChannel":null},{"cityId":0,"city":"东莞市","school":null,"step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":[{"cityId":100101,"city":"东莞市","school":"东莞广仁","step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":2,"cmsChannel":null}],"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":null,"cmsChannel":null},{"cityId":0,"city":"深圳市","school":null,"step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":[{"cityId":100100,"city":"深圳市","school":"深港","step4":2,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":1,"cmsChannel":null},{"cityId":100100,"city":"深圳市","school":"卡尔迅驾校","step4":1,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":36,"cmsChannel":null},{"cityId":100100,"city":"深圳市","school":"二职驾校","step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":6,"cmsChannel":null},{"cityId":100100,"city":"深圳市","school":"冠通驾校","step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":7,"cmsChannel":null},{"cityId":100100,"city":"深圳市","school":"港深通驾校","step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":93,"cmsChannel":null},{"cityId":100100,"city":"深圳市","school":"深港众一昊驾校","step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":8,"cmsChannel":null},{"cityId":100100,"city":"深圳市","school":"福永照明驾校","step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":92,"cmsChannel":null},{"cityId":100100,"city":"深圳市","school":"车博士驾校","step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":89,"cmsChannel":null}],"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":null,"cmsChannel":null},{"cityId":0,"city":"西安市","school":null,"step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":[{"cityId":102100,"city":"西安市","school":"西安市星火驾校","step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":123,"cmsChannel":null},{"cityId":102100,"city":"西安市","school":"碑林驾校","step4":0,"step5":0,"step6":0,"step101":0,"step201":0,"step301":0,"step302":0,"step401":0,"step402":0,"step501":0,"step601":0,"step602":0,"step701":0,"step702":0,"step801":0,"reports":null,"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":121,"cmsChannel":null}],"pageNo":0,"pageSize":0,"startIndex":0,"schoolNo":null,"startTime":null,"endTime":null,"orderby":null,"sort":null,"schoolId":null,"cmsChannel":null}]

}]);