/*angular for 教练*/
var app=angular.module("app",["ngFilter","ngSelects"]);
/*main 控制器*/
app.controller("UserPermissionUsersAdd",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
    $s.roleId=getQueryString("roleId");
    $s.roleName=getChineseQueryString("roleName");

    /*获取所有用户列表，供添加成员用*/
    $s.getDataList=function(){
        var json=getJson($s.defaultPage);
        $.AJAX({
            //type:"get",
            url:json.url,
            data:json.data,
            success:function(data){
                var DATA=getListData(data);
                $s.total=DATA.total;
                /*数据渲染页面*/
                $s.datas=DATA.dataList;
                /*全选与取消全选*/
                Selects.selects({datas:$s.datas,whichId:'userId',num:''});
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
            url:config.basePath+"user/findAvailUser",
            //url:config.basePath+"coupon/cond-batch",
            data: {
                "pageNo":nowPage,
                "pageSize": parseInt($s.pageSize),
                "roleId":$s.roleId
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

	/*include 加载完成后执行*/
	$s.usersAddLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.edit-alert").fadeOut("fast");
		})

	}

    /*------确认添加------------------------------------*/
    $s.submitData=function(){
        /*检测是否选择用户*/
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要添加的用户！"});
            return false;
        };

        /*获得选择的data数据*/
        var datas=getDataForKey({datas:$s.datas,id:'id',idList:$rootScope.idList});

        /*检测DATA数据是否合法*/
        for (var i=0;i<datas.length;i++){
            if(datas[i].enabled!=0){
                Layer.alert({width:300,height:150,type:"msg",title:"您不能选择已离职的成员，<br>请重新选择！"});
                return false;
            }
        }
        /*确认添加*/
        Layer.confirm({width:300,height:160,title:"确认添加所选用户吗？",header:"添加用户"},function(){
            $s.addJson = {
                roleId:$s.roleId
            }
            /*当勾选数多项时，取集合传ids,  当勾选项为一项时，取单个id*/
            if(datas.length==1){$s.addJson.userId=$rootScope.idList.toString()}
            if(datas.length>1){$s.addJson.ids=$rootScope.idList.toString()}

            $.AJAX({
                url:config.basePath+"privilege/addRoleUser",
                data:$s.addJson,
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    setTimeout(function(){
                        window.location.href="user-permission-users.html?roleId="+$s.roleId+"&roleName="+$s.roleName;
                    },1000)
                    $s.getDataList();
                }
            });
        });/*layer end*/
    }



}]);