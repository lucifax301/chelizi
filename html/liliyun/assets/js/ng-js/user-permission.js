/*angular for 教练*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("UserPermission",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
    $s.enabled = -1;    //-1为筛选全部，0为已启用，1为未启用

	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"post",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=data.data;
				$s.total=DATA.total;
				/*数据渲染页面*/
				$s.datas=DATA.dataList;
				/*全选与取消全选*/
				Selects.selects({datas:$s.datas,whichId:'coachId',num:''});
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
            url:config.basePath+"privilege/listRole",
			//url:config.basePath+"privilege/listRole",
			data: {
                "enabled":$s.enabled,
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
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

	/*按账号状态筛选列表数据*/
	$s.getDataForStatus=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
        $s.enabled=type;
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}


    /*-----启用停用---------------------------*/
    $s.activeAction = function(cAction){
        var actionInfo = {msgWords:"",msgUrl:"",msgCode:""};
        switch (cAction) {
            case "cActive":
                actionInfo.msgWords = "激活";
                actionInfo.msgUrl = config.basePath+"privilege/activeRole";
                actionInfo.msgCode = 1;//点激活时要求的状态值（为停用状态值）
                actionInfo.msgWords2 = "停用";
                break;
            case "cCancle":
                actionInfo.msgWords = "停用";
                actionInfo.msgUrl = config.basePath+"privilege/inactiveRole";
                actionInfo.msgCode = 0;//点停用时要求的状态值（为激活状态值）
                actionInfo.msgWords2 = "激活";
                break;
            default: break;
        }

        /*检测是否选择*/
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要"+actionInfo.msgWords+"的角色！"});
            return false;
        };

        /*获得选择的data数据*/
        var datas=getDataForKey({datas:$s.datas,id:'id',idList:$rootScope.idList});
        /*只能选择同一组别的方案（停用或启用）*/
        var dataOK = true;//选择的数据，默认为合格
        var errorWorlds = "";//提示语句
        /*判断所选数据是否合法*/
        for (var i=0;i<datas.length;i++){
            if(datas[i].enabled!=actionInfo.msgCode ){
                errorWorlds = "您只能选择"+actionInfo.msgWords2+"的角色，<br>请重新选择！";
                dataOK = false; break; //数据不合格，跳出
            }
        }
        if(!dataOK){
            Layer.alert({width:330,height:175,type:"msg",title:errorWorlds});
            return false;
        }
        $s.activeJson = {}
        /*当勾选数多项时，取集合传ids,  当勾选项为一项时，取单个id*/
        if(datas.length==1){$s.activeJson.id=$rootScope.idList.toString()}
        if(datas.length>1){$s.activeJson.ids=$rootScope.idList.toString()}
        /*弹出确认框*/
        Layer.confirm({width:300,height:160,title:"确认"+actionInfo.msgWords+"选中角色吗？",header:actionInfo.msgWords+"角色"},function(){
            $.AJAX({
                type:"get",
                url:actionInfo.msgUrl,
                data:$s.activeJson,
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            });
        });/*layer end*/
    }







}]);