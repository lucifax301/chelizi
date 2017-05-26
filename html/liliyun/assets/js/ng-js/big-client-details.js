var app = angular.module("app",["ngFilter","ngSelects"]);

app.controller("clientDetails",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	$s.defaultPage = location.hash.substring(2) || 1; 
	$s.pageSize = 10;    
	$s.coid = getQueryString("coid");
	$s.cityId = getQueryString("cityId");//学员的CITY跟着大客户走
	$s.searchType="mobile";//搜索类型
	$s.search="";     //搜索内容
	$s.allPlans = "";

	$.AJAX({
		type : "GET",
		url : config.basePath + "vip/company-view",
		data: {
		    "coid" : $s.coid
		},
		success:function(data){
			$s.item = getListData(data);
			$s.$apply();
		}
	});

	/*获取数据列表并展示*/
	$s.getDataList = function() {
		var json=getJson($s.defaultPage);
		$.AJAX({
			type : "GET",
			url : json.url,
			data: json.data,
			success:function(data){
				var DATA = getListData(data);
				$s.datas = DATA.dataList;
				//console.log($s.datas)
				Selects.selects({datas:$s.datas,whichId:'studentId',num:''});
				$s.$apply();
				new Page({
					parent : $("#copot-page"),
					nowPage : $s.defaultPage,
					pageSize : $s.pageSize,
					totalCount : DATA.total,
				});
			}
		});
	};
	$s.getDataList();

	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"vip/custom-batch",
			data: {
				"coid" : $s.coid,
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			}
		};
        /*当为普惠公司时，增加一个参数vipBig为空*/
        if($s.coid==4){json.data["vipBig"]="";json.data["rcid"]=160701003}
		/*增加搜索条件*/
		json.data[$s.searchType]=$s.search;
		return json;
	};

	/*获取充值送方案列表*/
	$s.rcDatas = [];
	$s.rcname = "";
	$.AJAX({
		type:"get",
		url:config.basePath + "vip/plan-batch",
		data: {
				"pageNo": 1,
			    "pageSize": 100,
			    "name": $s.name,
			},
		success:function(data){
			var DATA=getListData(data);
            var rcDatasWamp = DATA.dataList;
            $s.rcDatas = [];
            for(var i=0;i<rcDatasWamp.length;i++){if(rcDatasWamp[i].vtype==1){$s.rcDatas.push(rcDatasWamp[i])}}
			$s.$apply();
		}
	})



	$s.getDataList();

	/*hash值改变的时候加载数据列表*/
	window.onhashchange = function() {
		win.showLoading();
		$s.defaultPage = location.hash.substring(2) || 1;
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage = function() {
		win.showLoading();
		$s.defaultPage = 1;
		$s.getDataList();
	}

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}


//******学员审核通过****************************/
	$s.setVpass = function(){
		/*未选学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"您尚未选择学员！",header:"审核大客户"});
			return false;
		};
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		for(var i=0;i<datas.length;i++){
			if(datas[i].vstate==1){
				Layer.alert({width:300,height:150,type:"msg",title:"您不能审核已通过的学员！",header:"审核大客户"});
				return false;
			}
			if(datas[i].vstate==8){
				Layer.alert({width:300,height:150,type:"msg",title:"该学员已审核，但方案未生效！",header:"审核大客户"});
				return false;
			}
		}
		Layer.confirm({width:300,height:160,title:"确认审核通过吗？",header:"审核通过"},function(){
			$.AJAX({
				url:config.basePath+"vip/pass-custom",
				data:{
					studentIds:$rootScope.idList.toString()
				},
				code:true,
				success:function(){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"审核成功",closeMask:true});
					$s.getDataList();
				}
			})
		});

		
	}


//******学员审核不通过****************************/
	$s.setVfail = function(){
		/*未选学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"您尚未选择学员！",header:"审核大客户"});
			return false;
		};
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		for(var i=0;i<datas.length;i++){
			if(datas[i].vstate!=0){
				Layer.alert({width:300,height:150,type:"msg",title:"您只能选择待审的学员！",header:"审核大客户"});
				return false;
			}
		}
		Layer.confirm({width:300,height:160,title:"确认审核不通过吗？",header:"审核不通过"},function(){
				$.AJAX({
					url:config.basePath+"vip/refuse-custom",
					data:{
						studentIds:$rootScope.idList.toString(),
                        reason:"拒绝"
					},
					success:function(){
						$rootScope.idList=[];
						Layer.miss({width:250,height:90,title:"审核成功",closeMask:true});
						$s.getDataList();
					}
				})
			});
		
	}

//**添加已有学员弹出层****************************************/
	$s.addIn = function() {
		$(".client-alert-in").fadeIn("fast");
		$("#client-alert-in").css("marginTop",parseInt(($(win).height()- $("#client-alert-in").height()-100)/2)+"px"); //弹出层垂直居中	
		$(".closeAlert-in").click(function(){
			$(this).parents("div.client-alert-in").fadeOut("fast");
		});
	}


	$s.addOut = function() {
		$(".client-alert-out").fadeIn("fast");
		$("#client-alert-out").css("marginTop",parseInt(($(win).height()- $("#client-alert-out").height()-100)/2)+"px"); //弹出层垂直居中	
		$(".closeAlert-out").click(function(){
			$(this).parents("div.client-alert-out").fadeOut("fast");
		});
	}

/***通过搜索获取已有学员，并添加到大客户学员列表************************/
	$s.searchStuRzt = {};
	$s.searchStudent = function() {
		if ($s.searchPhoneNum == undefined || $s.searchPhoneNum == "") {
            Layer.alert({width:300,height:150,type:"msg",title:"请填写学员电话号码！"});
            return false;
        }
		$.AJAX({
			type : "GET",
			url : config.basePath + "student/phone",
			data : {
			    "phoneNum" : $s.searchPhoneNum,			
			}, 
			success : function(data){
				$s.searchStuRzt=JSON.parse(data.result.pageData);
				//清除已搜的学员信息
				$("#studentInfo").empty();
                var allowAdd = false;//不搜索出来学员，不允许提交
                if ($s.searchStuRzt != "") {
                    allowAdd = true;//搜索出来学员，允许提交
                    var info = "<i class='ion-checkmark-circled succes'></i> {1} | {2} | {3}"
                    .format($s.searchStuRzt.name ? $s.searchStuRzt.name : "喱喱学员" , $s.searchStuRzt.sex ? $filter('sexText')($s.searchStuRzt.sex) : "性别未知",$s.searchStuRzt.idNumber ? $s.searchStuRzt.idNumber : "身份证号未知");
                    $("#studentInfo").append(info);
                    //添加已有学员

                    $("#addClientStuIn").off('click').on('click',function() {
                        if(!allowAdd){Layer.alert({width:300,height:150,type:"msg",title:"尚未指定学员！"});return false;}
                        if(!$("#selRcid").val()){Layer.alert({width:300,height:150,type:"msg",title:"尚未选择方案！"});return false;}
                        $.AJAX({
							url:config.basePath + "vip/add-custom",
							data:{
								studentId:$s.searchStuRzt.studentId,
								name:$s.searchStuRzt.name,
								vipId:$s.coid,
								vipPackageId:$s.rcid,
								mobile:$s.searchStuRzt.phoneNum,
								cname:$s.searchStuRzt.cname,
								coid:$s.coid,
								rcid:$s.rcid,
							},
							success:function(data){
								Layer.miss({width:250,height:90,title:"添加成功",closeMask:true});
								$(".edit-alert").fadeOut();
								$s.searchPhoneNum="";
								$("#studentInfo").empty();
								$s.rcid="";
								$s.getDataList();
							}
						})
                    });

                } else {
                    $("#studentInfo").append("<i class='ion-close-circled error'></i> 未找到相关学员信息");
                }
			}
		});

	}

	/*导入数据*/
	$s.createFileForm=function(){
		/*FormData 上传*/
		cerateFileFormData({
			url:config.basePath+"vip/upload",
			data:{
				coid:$s.coid
			},
			callback:function(data){
				/*提示成功信息*/
                $s.excelInfo=data.result;
                console.log($s.excelInfo)
				Layer.miss({width:250,height:90,title:"导入成功",closeMask:true});
                setTimeout(function(){window.location.href="import-bigclient-rzt.html?id="+$s.excelInfo.id+"&sum="+$s.excelInfo.sum+"&validSum="+$s.excelInfo.validSum+"&cityId="+$s.cityId+"&coid="+$s.coid},500)
				/*更新列表*/
				//$s.getDataList();
			}
		});
	};

	/*数据导出*/
	$s.downLoadData=function(){
		Layer.confirm({width:300,height:160,title:"本次导出最多支持1000条数据",header:"数据导出"},function(){
			//window.location.href=config.basePath+"/vip/export-excel?coid="+$s.coid;
			dataExportForIframe({
				getSearchs:getJson($s.defaultPage).data,
				total:$s.total,
				url:'/vip/export-excel',
			});
		});
	}
	/*模板导出*/
	$s.downLoadTemp=function(){
		window.location.href=config.basePath+"/vip/download";	
	}


	//出生年份集合,18-70周岁
	$s.bornYears = [];
	for(var i = parseInt(new Date().getFullYear())-17;i>parseInt(new Date().getFullYear())-70;i--){
		$s.bornYears.push(i);
	}
	//出生月份集合
	$s.bornMonth = [];
	for(var i = 1;i<=12;i++){
		$s.bornMonth.push(i);
	}

	//*****************全国省市联动**********/
	$s.provinceArr = provinceArr ; //省份数据
    $s.cityArr = cityArr;    //城市数据
    $s.getCityArr = $s.cityArr[0]; //默认为省份
    $s.getCityIndexArr = ['0','0'] ; //这个是索引数组，根据切换得出切换的索引就可以得到省份及城市

    //改变省份触发的事件 [根据索引更改城市数据]
	$s.provinceChange = function(index)
	{
	    $s.getCityArr = $s.cityArr[index] ; //根据索引写入城市数据
	    $s.getCityIndexArr[1] = '0' ; //清除残留的数据
	    $s.getCityIndexArr[0] = index ;
	    //输出查看数据
	    //console.log($s.getCityIndexArr,provinceArr[$s.getCityIndexArr[0]],cityArr[$s.getCityIndexArr[0]][$s.getCityIndexArr[1]]);
	}
	//改变城市触发的事件
	$s.cityChange = function(index)
	{
	    $s.getCityIndexArr[1] = index ;
	    //输出查看数据
	    //console.log($s.getCityIndexArr,provinceArr[$s.getCityIndexArr[0]],cityArr[$s.getCityIndexArr[0]][$s.getCityIndexArr[1]]);
	}

//新义新增新学员的容器
	$s.addNewData = {}
	$s.addNewStu = function(){
		if(!$s.addNewData.name){
			Layer.alert({width:400,height:175,type:"msg",title:"请输入姓名",header:"新增学员"});
			return false;
		}
		if(!$s.addNewData.sex){
			Layer.alert({width:400,height:175,type:"msg",title:"请选择性别",header:"新增学员"});
			return false;
		}
		if(!$s.addNewData.phoneNum||$s.addNewData.phoneNum.length<11){
			Layer.alert({width:400,height:175,type:"msg",title:"请检查电话号码",header:"新增学员"});
			return false;
		}
		if(!$s.addNewData.idNum || (($s.addNewData.idNum.length!=18) && ($s.addNewData.idNum.length!=15))){
			Layer.alert({width:400,height:175,type:"msg",title:"请检查身份证号",header:"新增学员"});
			return false;
		}
		// if(!$s.addNewData.applyCarType){
		// 	Layer.alert({width:400,height:175,type:"msg",title:"请选择所学车型",header:"新增学员"});
		// 	return false;
		// }
		if(!$s.rcid){
			Layer.alert({width:400,height:175,type:"msg",title:"请选择优惠方案",header:"新增学员"});
			return false;
		}
        if(!$s.addNewData.cid){
            Layer.alert({width:400,height:175,type:"msg",title:"请输入工号",header:"新增学员"});
            return false;
        }

		$.AJAX({
			url:config.basePath+"vip/new-student",
			data:{//姓名 性别 年月 省市 身份证号 电话 方案 公司ID 未审核
				coid:$s.coid,
				name:$s.addNewData.name,
				sex:$s.addNewData.sex,
				vstate:0,
				vipId:$s.coid,
				vipPackageId:$s.rcid,
                rcid:$s.rcid,
				idNumber:$s.addNewData.idNum,
				phoneNum:$s.addNewData.phoneNum,
				cityId:$s.cityId,
                cid:$s.addNewData.cid,
				//applyCarType:$s.addNewData.applyCarType,
				//drType:$s.addNewData.applyCarType,
				hometown:$("#hometownP option:selected").text() + $("#hometownC option:selected").text()
			},
			code:true,
			success:function(){
				$("div.client-alert-out").fadeOut("fast");
				Layer.miss({width:250,height:90,title:"添加成功",closeMask:true});
                //setTimeout(function(){window.location.href="import-bigclient-rzt.html"},500)
				/*更新列表*/
				$s.getDataList();

			}
		})
	}








	}

]);