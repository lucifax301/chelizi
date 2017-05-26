app.controller("UserPermission",["$scope","$rootScope","$filter",function($scope,$rootScope,$filter){
	$scope.defaultPage = getUrl('page') || 1; 
	$scope.pageSize = 10;  
    $scope.enabled = -1;  
    $scope.issuper = JSON.parse(sessionStorage.getItem("userInfo")).issuper;

	$scope.getDataList = function(){
		$.AJAX({
			type : "GET",
			url : config.basePath+"privilege/listRole",
			data : {
                enabled:$scope.enabled,
                pageNo : $scope.defaultPage,
                pageSize : $scope.pageSize
            },
			success : function(data){
                $scope.datas = data.result.list;
                $scope.total = data.result.total;
                $scope.$apply();
				new Page({
					parent:$("#copot-page"),
                    nowPage:$scope.defaultPage,
                    pageSize:$scope.pageSize,
                    totalCount:$scope.total,
				}); 
			}
		});
	};	
	$scope.getDataList();

    window.onhashchange = function(){
        $scope.defaultPage = getUrl('page') || 1;  
    }


    /*-----启用停用---------------------------*/
    $scope.activeAction = function(cAction,id){
        var actionInfo = {msgWords:"",msgUrl:"",msgCode:""};
        switch (cAction) {
            case "cActive":
                actionInfo.msgWords = "启用";
                actionInfo.msgUrl = config.basePath + "privilege/activeRole?id=" + id;
                actionInfo.msgCode = 1;
                actionInfo.msgWords2 = "停用";
                break;
            case "cCancle":
                actionInfo.msgWords = "停用";
                actionInfo.msgUrl = config.basePath + "privilege/inactiveRole?id=" + id;
                actionInfo.msgCode = 0;
                actionInfo.msgWords2 = "启用";
                break;
            default: break;
        }

        Layer.confirm({width:300,height:160,title:"确认"+actionInfo.msgWords+"该角色吗？",header:actionInfo.msgWords+"角色"},function(){
            $.AJAX({
                type : "GET",
                url : actionInfo.msgUrl,
                data : $scope.activeJson,
                success : function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataList();
                }
            });
        });
    }
}]);