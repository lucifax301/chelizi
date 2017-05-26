var app=angular.module("app",["ngFilter","ngSelects"]);

app.controller("bigClientedit",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	$s.defaultPage=location.hash.substring(2) || 1;  
	$s.pageSize=10;    
	$s.searchType = "company";
	$s.search = "";
	$s.coid = getQueryString("coid");
	

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
			$s.$apply()
		}
	});

	/*加载优惠方案数据列表*/
	$.AJAX({
		type:'get',
		url:config.basePath+"vip/plan-batch",
		data:{
			"pageNo": 1,
		    "pageSize": 100,
		},
		success:function(data){
			$s.promotion = JSON.parse(data.result.pageData);
			$s.$apply();
			$s.getdata();
		}
	});
	$s.getdata = function(){
		if($s.coid){
			$.AJAX({
				type : "GET",
				url : config.basePath + "vip/company-view",
				data: {
				    "coid" : $s.coid
				},
				success:function(data){
					$s.item = getListData(data);
					console.log($s.item);
					$s.$apply();
				}
			});
		}
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
    	if(!$s.item.rcid){
    		Layer.alert({width:300,height:150,type:"msg",title:"请选择优惠方案"});
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
            rcid : $s.item.rcid,
            city: $("#searchCity").find("option:checked").attr("dataName"),
        }
        if(!$s.item.coid){dataJson.active=0}
       
        $.AJAX({
            type : "POST",
            url : config.basePath + url,
            data :dataJson,
            success : function(data) {
            	console.log(dataJson);
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                setTimeout(function(){
                	window.location.href="big-client.html";
                },1500)
            }
        });
       
    };   

}]);