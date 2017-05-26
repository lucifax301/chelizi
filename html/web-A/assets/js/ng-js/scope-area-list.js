app.controller("ScopeAreaList",["$scope",function($scope){
	
	$scope.disablesuccess = "已禁用该片区，请手动处理该片区下——代理商，门店，教练，学员等资源";
	$scope.enablesuccess = "已启用该片区!";
	$scope.delsuccess = "已删除该片区!";
	$scope.deletetooltip = "片区下有代理商，不能删除!";
	$scope.saveData={};
	$scope.mutliName=false;
	$scope.search={};
	$scope.defaultPage= getUrl('page') || 1;  //默认请求页	
    $scope.pageSize=10;    //每页显示-显示条数
    if(getUrl('search')){
		$scope.condition = getUrl('search');//查询当前url是否存在参数search，如有赋值搜索框
	}
	
	$scope.getList = function(){
		$.AJAX({
			method: "GET",
	    	url:config.basePath+"scope/list",
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
	$scope.getList();
	
	$scope.disablescope = function(data){
		if(data.scopeStatus=='0') return;
	 	Layer.confirm({width:300,height:160,title:"确认禁用吗？",header:"禁用"},
		 	function(){
			 var json = {scopeId:data.scopeId,scopeStatus:0};
			 $.AJAX({
				url:config.basePath+"scope/update",
				data:json,
				success:function(data){
					Layer.miss({width:250,height:160,title:$scope.disablesuccess,closeMask:false});
					$scope.getList();
					$scope.$apply();
				}
			});
		 })
	}
	
	$scope.delscope = function(data){
		if(data.proxyCount !='0'){
			Layer.miss({width:300,height:90,title:$scope.deletetooltip,closeMask:false});
			return;
		} 
	 	Layer.confirm({width:300,height:160,title:"确认删除吗？",header:"删除"},
		 	function(){
			 var json = {scopeId:data.scopeId};
			 $.AJAX({
				url:config.basePath+"scope/deleteById",
				data:json,
				success:function(data){
					Layer.miss({width:250,height:90,title:$scope.delsuccess,closeMask:false});
					$scope.getList();
					$scope.$apply();
				}
			});
		 })
	}
	
	$scope.enablescope = function(data){
		if(data.scopeStatus!='0') return;
	 	Layer.confirm({width:300,height:160,title:"确认启用吗？",header:"启用"},
		 	function(){
			 var json = {scopeId:data.scopeId,scopeStatus:1};
			 $.AJAX({
				url:config.basePath+"scope/update",
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
		var json = {scopeName:$scope.saveData.scopeName,scopeId:$scope.saveData.scopeId};
		$.AJAX({
			url:config.basePath+"scope/checnMutliName",
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
			scopeId:$scope.saveData.scopeId,
			scopeName:$scope.saveData.scopeName,
			scopeStatus:$scope.saveData.scopeStatus,
			scopeDescription:$scope.saveData.scopeDescription
			};
		$scope.checkMutliName();
		if($scope.mutliName){
			return false;
		}	
		$.AJAX({
	            url:config.basePath+"scope/"+$scope.actionType,
	            data:json,
	            success:function(data){
					Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					
	            }
	        }); 
		$("#actionModal").modal('hide');
		$(".modal-backdrop").remove();
		$scope.getList();
	}
	
	$scope.load4add = function(){
		$scope.saveData = {}
		$scope.mutliName = false;
		$scope.actionType = "add";
	}
	
	$scope.load4update = function(data){
		$scope.saveData = {
			scopeId:data.scopeId,
			scopeName:data.scopeName,
			scopeStatus:data.scopeStatus,
			scopeDescription:data.scopeDescription
		}
		$scope.mutliName = false;
		$scope.actionType = "update";
	}
	
}])





