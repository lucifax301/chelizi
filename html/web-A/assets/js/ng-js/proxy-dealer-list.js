app.controller("ProxyDealerList",["$scope",function($scope){
	
	$scope.disablesuccess = "已禁用该代理商，请手动处理该代理商下的门店，教练，学员等资源";
	$scope.enablesuccess = "已启用该代理商!";
	$scope.delsuccess = "已删除该代理商!";
	$scope.deletetooltip = "代理商有绑定门店不可删除!";
	$scope.saveData={};
	$scope.mutliName=false;
	$scope.validemail = false;
	$scope.validphone = false;
	$scope.search={};
	$scope.allscope=[];
	$scope.scopeAreas=[];
	$scope.defaultPage= getUrl('page') || 1;  //默认请求页	
    $scope.pageSize=10;    //每页显示-显示条数
    if(getUrl('search')){
		$scope.condition = getUrl('search');//查询当前url是否存在参数search，如有赋值搜索框
	}
	
	$scope.getList = function(){
		$.AJAX({
			method: "GET",
	    	url:config.basePath+"proxy/list",
	        data:{scopeName:$scope.search.scopeName==''?undefined:$scope.search.scopeName,pageNo :$scope.defaultPage,pageSize :$scope.pageSize},
	        success:function(data){
				$scope.datas = data.result.list;
				$scope.total = data.result.total;
				$scope.$apply();
				new Page({
					parent:$("#copot-page"),
					nowPage:$scope.defaultPage,
					pageSize:$scope.pageSize,
					totalCount:$scope.total,
					search:$scope.condition
				}); //分页请求完毕   
	        }
	    });
	}
	
	$scope.getAllScope = function(){
		$.AJAX({
			method: "GET",
	    	url:config.basePath+"scope/getAll",
	    	async:false,
	        data:{scopeName:$scope.search.scopeName==''?undefined:$scope.search.scopeName},
	        success:function(data){
	        	var list = data.result;
	        	for(i in list){
	        		var temp={};
	        		temp.scopeId=list[i].scopeId;
	        		temp.scopeName=list[i].scopeName;
	        		$scope.allscope.push(temp);
	        	}
	        	
	        }
	    });
	}
	
	$scope.getAllScope();
	$scope.getList();
	
	
	$scope.disableproxy = function(data){
		if(data.scopeStatus=='0') return;
	 	Layer.confirm({width:300,height:160,title:"确认禁用吗？",header:"禁用"},
		 	function(){
			 var json = {proxyId:data.proxyId,dealerStatus:0};
			 $.AJAX({
				url:config.basePath+"proxy/disable",
				data:json,
				success:function(data){
					Layer.miss({width:250,height:160,title:$scope.disablesuccess,closeMask:false});
					$scope.getList();
					$scope.$apply();
				}
			});
		 })
	}
	
	$scope.delproxy = function(data){
		if(data.scopeAreaList != null && data.scopeAreaList.length == 1 && data.scopeAreaList[0].scopeId == 0){
		 	Layer.confirm({width:300,height:160,title:"确认删除吗？",header:"删除"},
			 	function(){
				 var json = {proxyId:data.proxyId};
				 $.AJAX({
					url:config.basePath+"proxy/deleteById",
					data:json,
					success:function(data){
						Layer.miss({width:250,height:90,title:$scope.delsuccess,closeMask:false});
						$scope.getList();
						$scope.$apply();
					}
				});
			 })
		 }else{
		 	Layer.miss({width:300,height:90,title:$scope.deletetooltip,closeMask:false});
		 }
	}
	
	$scope.enableproxy = function(data){
		if(data.dealerStatus!='0') return;
	 	Layer.confirm({width:300,height:160,title:"确认启用吗？",header:"启用"},
		 	function(){
			 var json = {proxyId:data.proxyId,dealerStatus:1};
			 $.AJAX({
				url:config.basePath+"proxy/enable",
				data:json,
				success:function(data){
					Layer.miss({width:250,height:90,title:$scope.enablesuccess,closeMask:false});
					$scope.getList();
					$scope.$apply();
				}
			});
		 })
	}	
	
	$scope.checkMutliName = function(){
		var json = {proxyName:$scope.saveData.proxyName};
		$.AJAX({
			url:config.basePath+"proxy/checkMutliName",
			data:json,
			async:false,
			success:function(data){
				if(data.result == "ok")
					$scope.mutliName = false;
				else
					$scope.mutliName = true;
			}
		});
	}
	
	$scope.submitActionform = function(){
		var json = {
				proxyId:$scope.saveData.proxyId,
				proxyName:$scope.saveData.proxyName,
				dealerPerson:$scope.saveData.dealerPerson,
				dealerPhone:$scope.saveData.dealerPhone,
				dealerEmail:$scope.saveData.dealerEmail,
				//scopeAreas:$scope.scopeAreas.join(","),
				dealerAddr:$scope.saveData.dealerAddr,
				dealerStatus:$scope.saveData.dealerStatus,
				dealerDescription:$scope.saveData.dealerDescription
			};
		
		$scope.scopeAreas=[];
		var checkeds = $("input[type='checkbox']");
		checkeds.each(function(index,e){
			/*console.log(e);
			console.log(e.target);
			console.log($(e.target));
			console.log($(e).attr('data-checked'));*/
		    if($(e).is(':checked')){
		    	$scope.scopeAreas.push($(this).val());
		    }
		});  
		
		json.scopeAreas = $scope.scopeAreas.join(",");
		
		$.AJAX({
            url:config.basePath+"proxy/"+$scope.actionType,
			async:false,
            data:json,
            success:function(data){
				Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
				
            }
        }); 
        $("#actionModal").modal('hide');
		$(".modal-backdrop").remove();
		$scope.getList();
	}
	
	/*$scope.chk = function(event){
		
		console.log(event.target);
		$(event.target).attr("data-checked","true");
		
	}*/
	
	$scope.load4add = function(){
		$scope.saveData = {}
		$scope.mutliName = false;
		$scope.actionType = "add";
		$("input[type='checkbox']").attr("checked",false);
		
	}
	
	$scope.load4update = function(data){
		$scope.saveData = {
			proxyId:data.proxyId,
			proxyName:data.proxyName,
			dealerPerson:data.dealerPerson,
			dealerPhone:data.dealerPhone,
			dealerEmail:data.dealerEmail,
			scopeAreaList:data.scopeAreaList,
			dealerAddr:data.dealerAddr,
			dealerStatus:data.dealerStatus,
			dealerDescription:data.dealerDescription
		}
		$scope.mutliName = false;
		$scope.actionType = "update";
		$("input[type='checkbox']").attr("checked",false);
		
	}
	
	
	$scope.scopeAreaFilter = function(scopeAreaList){
			
		if(scopeAreaList == null || scopeAreaList.length == 0)
			return "-";
		
		var retStr = "";
		for(i in $scope.allscope){
			for(a in scopeAreaList){
				if(scopeAreaList[a].scopeId == $scope.allscope[i].scopeId){
					retStr = retStr + $scope.allscope[i].scopeName +",";
				}
			}
		}
		if(retStr.length == 0 )
			return "-";
			
		return retStr.substring(0,retStr.length-1);
		
	}
	
	$scope.isSelected = function(scopeId,scopeAreaList1){
		if(scopeAreaList1 == null || scopeId == null)
			return false;
			
		for(i in scopeAreaList1){
			if(scopeId == scopeAreaList1[i].scopeId)
				return true;
		}
		return false;
	}
	
	$scope.checkphone = function(){
		
		console.log($scope.saveData.dealerPhone);
		var reg =new RegExp(/^((\d{3,4}-)?\d{7,8})$|(1[0-9]{10})+$/);
		if(!reg.test($scope.saveData.dealerPhone)){
			$scope.validphone = true;
			reutrn;
		}
		$scope.validphone = false;
		
	}
	
	$scope.checkemail = function(){
		
		console.log($scope.saveData.dealerEmail);
		var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
		if(!reg.test($scope.saveData.dealerEmail)){
			$scope.validemail = true;
			reutrn;
		}
		$scope.validemail = false;
		
		/*$scope.validemail = false;
		$scope.validphone = false;*/
	}
	
	
}])





