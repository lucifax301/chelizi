/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("AddStuForPlan",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
    $s.coid="";
    $s.vipBig="";
	$s.search="";            //高级查询
	$s.searchType="mobile";    //默认搜索字段

    /*获取相关充值方案ID和方案名*/
    $s.rcid=getQueryString("rcid"); //获取充值送ID值
    $.AJAX({
        type:"get",
        url:config.basePath+"vip/plan-batch",
        data:{
            rcid:$s.rcid,
            "pageNo": 1,
            "pageSize": 10,
        },
        success:function(data){
            $s.rcinfo=JSON.parse(data.result.pageData).dataList[0];
        }
    })




	/*------------------------------------数据列表查询-------------------------------------------------------*/
	/*获取所有大客户列表供筛选*/

    $.AJAX({
        type:"get",
        url:config.basePath+"vip/company-batch",
        data: {
            "pageNo": $s.defaultPage,
            "pageSize": 100,
        },
        success:function(data){
            var DATA = getListData(data);
            $s.companys = DATA.dataList;
            $s.$apply();
        }
    });


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
			url:config.basePath+"vip/custom-batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
                "vipBig":$s.vipBig,
                "coid":$s.coid,
                "isDel":0,
                "rcid":$s.rcid
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

	/*--------------------------筛选数据--------------------------------------*/
	/*按学员类型筛选列表数据*/
	$s.getDataForStuType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.vipBig=type;
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

    $s.getDataForCompany = function(){
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.vipBig="true";
        $('#select-student li').last().addClass('active').siblings().removeClass("active");
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

    /*提交选择*/
    $s.submitStuForPlan = function(){
        /*检测是否选择学员*/
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要添加的学员！"});
            return false;
        };
        /*获得选择的data数据*/
        var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
        //当学员没有公司ID时，默认为普惠公司（coid=4）
        var coidArrTmp = getKeyArrFromData(datas,'coid');
        for(var i=0;i<coidArrTmp.length;i++){
            if(!coidArrTmp[i]){coidArrTmp[i]=4}
        }
        /*确认提交*/
        Layer.confirm({width:300,height:160,title:"确认添加至方案吗？",header:"关闭订单"},function(){
            $.AJAX({
                url:config.basePath+"vip/new-studentBatch",
                data:{
                    studentIds:$rootScope.idList.toString(),
                    mobiles:getKeyArrFromData(datas,'phoneNum').toString(),
                    coids:coidArrTmp.toString(),
                }
            })
        })
    }








}]);