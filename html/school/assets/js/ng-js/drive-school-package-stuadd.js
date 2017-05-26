/*angular for 教练*/
var app=angular.module("app",["ngFilter","ngSelects"]);
/*main 控制器*/
app.controller("UserPermissionUsersAdd",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
    $s.ttid=getQueryString("ttid");
    $s.className=getChineseQueryString("className");
    $s.search="";            //高级查询
    $s.searchType="name";    //默认搜索字段

    /*全选与取消全选*/
    Selects.selects({datas:$s.datas,whichId:'studentId'});

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
                Selects.selects({datas:$s.datas,whichId:'studentId'});
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
            type:"get",
            url:config.basePath+"student/noRelevancePackageStudent",
            data: {
                "pageNo": nowPage,
                "pageSize": parseInt($s.pageSize),
                "schoolType":1,
                "type":1,
            }
        };
        /*增加搜索条件*/
        json.data[$s.searchType]=$s.search;
        return json;
    };

    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
        win.showLoading();
        $s.defaultPage=location.hash.substring(2) || 1;
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


    /*------确认添加------------------------------------*/
    $s.submitData=function(){
        /*检测是否选择用户*/
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要添加的学员！"});
            return false;
        };

        /*获得选择的data数据*/
        var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});

        /*确认添加*/
        Layer.confirm({width:300,height:160,title:"确认添加所选学员吗？",header:"添加用户"},function(){
            $.AJAX({
                url:config.basePath+"school/addPackageStudent",
                data:{studentIds:$rootScope.idList.toString(),ttid:$s.ttid},
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"添加成功",closeMask:true});
                    setTimeout(function(){
                        window.location.href="drive-school-package-detail.html?ttid="+$s.ttid
                    },1000)
                }
            });
        });/*layer end*/
    }


}]);