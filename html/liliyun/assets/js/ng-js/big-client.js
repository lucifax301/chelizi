var app=angular.module("app",["ngFilter","ngSelects"]);

app.controller("bigClient",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	$s.defaultPage=location.hash.substring(2) || 1;  
	$s.pageSize=10;    
	$s.searchType = "company";
	$s.search = "";

	/*获取数据列表并展示*/
	$s.getDataList = function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data: json.data,
			success:function(data){
				var DATA = getListData(data);
				$s.datas = DATA.dataList;
				Selects.selects({datas:$s.datas,whichId:'coid',num:''});
				$s.$apply();
				new Page({
					parent:$("#copot-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:DATA.total,
				}); 
			}
		});
	};
	$s.getDataList();

	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"vip/company-batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			}
		};
		/*增加搜索条件*/
		json.data[$s.searchType]=$s.search;
		return json;
	};	

	/*加载城市数据列表*/
	$.AJAX({
		type:'get',
		url:config.basePath + "school/queryCity",
		data:{
			"rlevel":2,
			"pid":"",
			shield:0
		},
		success:function(data){
			$s.citys = JSON.parse(data.result.pageData);
		}
	});

	/*加载优惠方案数据列表*/
	$.AJAX({
		type:'get',
		url:config.basePath + "school/queryCity",
		data:{
			"rlevel":2,
			"pid":"",
			shield:0
		},
		success:function(data){
			$s.promotion = JSON.parse(data.result.pageData);
		}
	});


	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
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

	/*更新搜索框提示文字*/
	$("#search_type").change(function(){
		$("#search_placeholder").attr("placeholder","输入"+$(this).children("option:selected").text()+"查询")
	})

	/*点击新建大客户*/
	$s.addClient = function(){		
		$s.item = {};
		$(".client-alert").fadeIn("fast");
		$("#client-alert").css("marginTop",parseInt(($(win).height()- $("#client-alert").height()-100)/2)+"px"); //弹出层垂直居中	
		$(".closeAlert").click(function(){
			$(this).parents("div.client-alert").fadeOut("fast");
		});
	}

	/*点击编辑大客户*/
	$s.editClient = function(data){		
		$s.item = data;
		$(".client-alert").fadeIn("fast");
		$("#client-alert").css("marginTop",parseInt(($(win).height()- $("#client-alert").height()-100)/2)+"px"); //弹出层垂直居中
		$(".closeAlert").click(function(){
			$(this).parents("div.client-alert").fadeOut("fast");
		});
	}

	/*数据导出*/
	$s.downLoadData=function(){
		Layer.confirm({width:300,height:160,title:"本次导出最多支持1000条数据",header:"数据导出"},function(){
			//window.location.href=config.basePath+"/vip/export-excel?coid="+$s.coid;
			dataExportForIframe({
				getSearchs:getJson($s.defaultPage).data,
				total:$s.total,
				url:'/vip/export-company',
			});
		});
	}

	//激活大客户
	$s.active = function() {
		var datas=getDataForKey({datas:$s.datas,id:'coid',idList:$rootScope.idList});
		if(!datas.length) {
          	Layer.alert({width:300,height:150,type:"msg",title:"请选择操作大客戶！"});
          	return false;
        };
        for(var i=0;i<datas.length;i++){
        	if(datas[i].active!=0){
        		Layer.alert({width:300,height:150,type:"msg",title:"您只能选择未激活的大客户！"});
          	return false;
        	}
        }

        Layer.confirm({width:380,height:160,title:"您确认把所选大客户置为[激活启用]？",header:"大客户操作"},function(){
            $.AJAX({
                url: config.basePath + "vip/active-company",
                type : "POST",
                data: {
                    coids : $rootScope.idList.toString()
                },
                success : function(data){
                    $rootScope.idList = [];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            });
        });
	}

	$s.inactive = function() {
		var datas=getDataForKey({datas:$s.datas,id:'coid',idList:$rootScope.idList});
		if(!datas.length) {
          	Layer.alert({width:300,height:150,type:"msg",title:"请选择操作大客戶！"});
          	return false;
        };
        for(var i=0;i<datas.length;i++){
        	if(datas[i].active!=1){
        		Layer.alert({width:300,height:150,type:"msg",title:"您只能选择已激活的大客户！"});
          	return false;
        	}
        }
        Layer.confirm({width:380,height:160,title:"您确认把所选大客户置为[停用状态]？",header:"大客户操作"},function(){
            $.AJAX({
                url: config.basePath + "vip/inactive-company",
                type : "POST",
                data: {
  					coids : $rootScope.idList.toString()
                },
                success : function(data){
                    $rootScope.idList = [];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            });
        });
	}

	   /*编辑大客户提交*/
    $s.submitItemInfo = function() {
    	
    	if(!$s.item.company){
    		Layer.alert({width:300,height:150,type:"msg",title:"请填写大客户名称"});
          	return false;
    	}
    	if(!$s.item.shorter){
    		Layer.alert({width:300,height:150,type:"msg",title:"请填写标识简称"});
          	return false;
    	}
    	if(!$s.item.cityId){
    		Layer.alert({width:300,height:150,type:"msg",title:"请选择所在城市"});
          	return false;
    	}
    	if(!$s.item.manger){
    		Layer.alert({width:300,height:150,type:"msg",title:"请填写客户经理"});
          	return false;
    	}
    	if(!$s.item.mobile||$s.item.mobile.length<11){
    		Layer.alert({width:300,height:150,type:"msg",title:"请检查电话号码"});
          	return false;
    	}
        var url = $s.item.coid == undefined ? 
            "vip/add-company" : "vip/modify-company";
        var dataJson = {
        	coid : $s.item.coid,
            company : $s.item.company,
            shorter : $s.item.shorter,
            cityId : $s.item.cityId,
            manger : $s.item.manger,
            mobile : $s.item.mobile,
            city: $("#searchCity").find("option:checked").attr("dataName"),
        }
        if(!$s.item.coid){dataJson.active=0}

        Layer.confirm({width:320,height:160,title:"确认提交当前的大客户信息？",header:"提示"},function() {
            $.AJAX({
                type : "POST",
                url : config.basePath + url,
                data :dataJson,
                success : function(data) {
                	console.log(dataJson)
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $("#client-alert").parents("div.client-alert").fadeOut("fast");
                    $s.getDataList();
                }
            });
        });
    };   

}]);