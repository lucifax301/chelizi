/*angular for 教练*/
var app=angular.module("app",["ngFilter","ngSelects"]);
/*main 控制器*/
app.controller("UserPermissionUsers",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
    $s.roleId=getQueryString("roleId");
    $s.roleName=getChineseQueryString("roleName");

    /*获取数据列表并展示*/
    $s.getDataList=function(){
        var json=getJson($s.defaultPage);
        $.AJAX({
            url:json.url,
            data:json.data,
            success:function(data){
                var DATA=data.data;
                $s.total=DATA.total;
                /*数据渲染页面*/
                $s.datas=DATA.dataList;
                /*全选与取消全选*/
                Selects.selects({datas:$s.datas,whichId:'ID'});
                $s.$apply();
                /*冒泡弹出太长的文字*/
                topLongText();
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
            url:config.basePath+"privilege/listRoleUser",
            data: {
                "pageNo": nowPage,
                "pageSize": parseInt($s.pageSize),
                "roleId":$s.roleId
            }
        };
        /*增加搜索条件*/
        return json;
    };

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
        win.showLoading();
        $s.defaultPage=location.hash.substring(2) || 1;
        $s.getDataList();
    }

    /*------删除成员------------------------------------*/
    $s.userDel=function(){
        /*检测是否选择用户*/
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要删除的用户！"});
            return false;
        };

        /*获得选择的data数据*/
        var datas=getDataForKey({datas:$s.datas,id:'ID',idList:$rootScope.idList});

        /*确认删除*/
        Layer.confirm({width:300,height:160,title:"确认删除所选用户吗？",header:"删除用户"},function(){
            $.AJAX({
                url:config.basePath+"privilege/delRoleUser",
                data:{
                    userId:$rootScope.idList.toString(),
                    roleId:$s.roleId
                },
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            });
        });/*layer end*/
    }



}]);