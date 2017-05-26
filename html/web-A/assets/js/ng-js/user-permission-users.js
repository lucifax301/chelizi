app.controller("UserPermissionUsers",["$scope","$filter",function($scope,$filter){
	$scope.defaultPage = getUrl('page') || 1; 
    $scope.pageSize = 10;  
    $scope.roleId = getUrl("params").split("_")[0];
    $scope.roleName = getUrl("params").split("_")[1];

    $scope.getDataList = function(){
        $.AJAX({
            type : "GET",
            url : config.basePath + "privilege/listRoleUser",
            data : {
                pageNo : $scope.defaultPage,
                pageSize : $scope.pageSize,
                roleId : $scope.roleId
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

    $scope.getUserList = function() {
        $scope.getList = function(page) {
            if(page) {
                var nowpage = page;
            } else {
                var nowpage = 1;
            }
            $.AJAX({
            type : "GET",
            url : config.basePath + "privilege/listNotRoleUser",
            data : {
                pageNo : nowpage,
                pageSize : $scope.pageSize,
                roleId : $scope.roleId
            },
            success : function(data){
                $scope.userdatas = data.result.list;
                $scope.usertotal = data.result.total;
                $scope.$apply();
                new Page({
                    type:2,
                    parent:$("#copot-page2"),
                    nowPage:nowpage,
                    pageSize:$scope.pageSize,
                    totalCount:$scope.usertotal,
                    callback:$scope.getList
                }); 
            }
        });
        }
        $scope.getList();
    }

    window.onhashchange = function(){
        $scope.defaultPage = getUrl('page') || 1; 
        $scope.getDataList();
    }

    $scope.userDel = function(id){
        Layer.confirm({width:300,height:160,title:"确认移除该用户吗？",header:"移除用户"},function(){
            $.AJAX({
                url : config.basePath + "privilege/delRoleUser",
                data: {
                    userId:id,
                    roleId:$scope.roleId
                },
                success : function(data) {
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataList();
                }
            });
        });
    }

    $scope.userAdd = function(id){
        Layer.confirm({width:300,height:160,title:"确认添加该用户吗？",header:"添加用户"},function(){
            $.AJAX({
                url : config.basePath + "privilege/addRoleUser",
                data: {
                    userId:id,
                    roleId:$scope.roleId
                },
                success : function(data) {
                    $("#userList").modal("hide");
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataList();
                }
            });
        });
    }
}]);