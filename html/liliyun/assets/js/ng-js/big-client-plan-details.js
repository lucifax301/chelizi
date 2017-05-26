/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("RechargePriviDetail",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.rcid=getQueryString("rcid"); //获取充值送ID值
	$s.type="";  //使用状态
	$s.sendPriviCnt = false;//按金额赠送
	$s.sendPriviPer = false;//按比例赠送

    //for 关联学员列表
    $s.searchType="mobile";    //默认搜索字段
    $s.search="";               //搜索内容
    $s.vstate="";                //生效状态


/*------充值送方案详情------------------------------*/
	/*获取数据列表并展示*/
	$s.getRcpriviDetailsData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"vip/recharge-plan",
			data:{
				rcid : $s.rcid
			},
			success:function(data){
				$s.data=JSON.parse(data.result.pageData);  //充值送方案详情数据
				console.log($s.data)
				if($s.data.rechargeGearList[0].money){$s.sendPriviCnt = true;}//按金额
				if($s.data.rechargeGearList[0].percent){$s.sendPriviPer = true;}//按比例
				$s.$apply();
			}
		});
	};
	$s.getRcpriviDetailsData();

    /*获取当前大客户公司关联学员列表*/
    $s.getDataList=function(){
        var json=getJson($s.defaultPage);
        $.AJAX({
            type:"get",
            url:json.url,
            data:json.data,
            success:function(data){
                var DATA=getListData(data);
                $s.total=DATA.total;
                $s.datas=DATA.dataList;
                console.log($s.datas)
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
            url:config.basePath+"vip/custom-batch",
            data: {
                "pageNo": nowPage,
                "pageSize": parseInt($s.pageSize),
                vstate:$s.vstate,
                rcid:$s.rcid,
                isDel:0,
                vipBig:"",
            }
        };
        /*增加搜索条件*/
        json.data[$s.searchType]=$s.search;
        return json;
    };

    /*按状态筛选关联学员列表*/
    $s.getDataForState=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.vstate=type;
        $s.getDataList();
    }

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

    /*高级查询*/
    $s.getDataForSearch=function(){
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.getDataList();
    }

    /*删除关联学员*/
    $s.delStuForPlan = function(item){
        Layer.confirm({width:300,height:160,title:"确认从此方案中删除吗",header:"删除关联"},function(){
            $.AJAX({
                url:config.basePath+"vip/custom-delete",
                data:{
                    studentId:item.studentId,
                    mobile:item.mobile,
                    rcid:item.rcid
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"删除成功",closeMask:true});
                    $s.getDataList();
                }
            })
        })
    }

    /*-----停发方案---------------------------------------*/
    $s.inactiveAction = function(data){
    	var idsData = [];
    	idsData.push($s.rcid);
    	console.log(idsData)
    	Layer.confirm({width:315,height:170,title:"确定停止发放该方案？",header:"停止发放"},function(){
			$.AJAX({
				url:config.basePath+"vip/inactive-plan",
				data:{
					ids:$s.rcid,
				},
				success:function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					setTimeout(function(){window.location.href="app-client-plan-details.html?rcid="+$s.rcid;}, 1000)
				}
			});
		});
    }

    /*-----激活方案---------------------------------------*/
    $s.activeAction = function(data){
    	var idsData = [];
    	idsData.push($s.rcid);
    	console.log(idsData)
    	Layer.confirm({width:315,height:170,title:"确定激活启用该方案？",header:"停止发放"},function(){
			$.AJAX({
				url:config.basePath+"vip/active-plan",
				data:{
					ids:$s.rcid,
				},
				success:function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					setTimeout(function(){window.location.href="app-client-plan-details.html?rcid="+$s.rcid;}, 1000)
				}
			});
		});
    }

    /****删除学员**********************************************/
    $s.delItem = function(item){
        Layer.confirm({width:315,height:170,title:"确定激活启用该方案？",header:"停止发放"},function(){
            $.AJAX({
                url:config.basePath+"vip/custom-delete",
                data:{
                    studentId:item.studentId,
                    mobile:item.mobile,
                    rcid:item.rcid
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"删除成功",closeMask:true});
                    $s.getDataList;
                }
            })
        })
    }

}]);