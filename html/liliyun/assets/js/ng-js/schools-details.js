/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("SchoolsDetails",["$scope","$filter",function($s,$filter){

	$s.schoolId=getQueryString("schoolId");
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
    $s.startTime = "";
    $s.endTime = "";
    $s.operateType = "";
    $s.orderId=""; //高级查询
    $s.schoolInfo={};
    $s.tabCurrent = "balance";//判断三个TAB选项卡的当前项

    //获取驾校基本信息
    $.AJAX({
        type:"get",
        url:config.basePath+"school/queryAccount",
        data:{schoolId:$s.schoolId,ymDate:new Date().date("y-m-d h:i:s")},
        success:function(data){
            $s.schoolInfo = JSON.parse(data.result.pageData);
            $s.schoolFee = JSON.parse(data.result.moneFree);
            $s.$apply();
        }
    })

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
	}

    $s.getBalance = function(){
        $s.defaultPage=1;
        $s.startTime = "";
        $s.endTime = "";
        $s.operateType = "";
        $s.orderId=""; //高级查询
        $("#reservation").val("自定义时间筛选")
        $s.getBalanceList();
    }
    $s.getIncome = function(){
        $s.defaultPage=1;
        $s.startTime = "";
        $s.endTime = "";
        $s.operateType = "";
        $s.orderId=""; //高级查询
        $("#reservation").val("自定义时间筛选")
        $s.getIncomeList();
    }
    $s.getPay = function(){
        $s.defaultPage=1;
        $s.startTime = "";
        $s.endTime = "";
        $s.operateType = "";
        $s.orderId=""; //高级查询
        $("#reservation").val("自定义时间筛选")
        $s.getPayList();
    }

    //*******************获取余额帐单**************************/
    $s.getBalanceList = function(){
        $s.tabCurrent = "balance";
        var json=balanceJson($s.defaultPage);
        $.AJAX({
            type:"get",
            url:json.url,
            data:json.data,
            success:function(data){
                var DATA=getListData(data);
                $s.total=DATA.total;
                $s.balanceDatas = DATA.dataList;
                $s.$apply();
                //分页请求
                new Page({
                    parent:$("#copot-page"),
                    nowPage:$s.defaultPage,
                    pageSize:$s.pageSize,
                    totalCount:DATA.total,
                }); //分页请求完毕
            }
        })
    }
    $s.getBalanceList();
    //余额帐单参数配置
    function balanceJson(nowPage){
        var json = {
            url:config.basePath+"school/touchBalance",
            data:{
                "schoolId":$s.schoolId,
                "operateType":$s.operateType,
                "pageNo":nowPage,
                "pageSize":$s.pageSize,
                "startTime":$s.startTime,
                "endTime":$s.endTime,
                "orderId":$s.orderId
            }
        }
        /*增加搜索条件*/
        json.data[$s.searchType]=$s.search;
        return json;
    }

    //********************获取收入明细***********************/
    $s.getIncomeList = function(){
        $s.tabCurrent = "income";
        var json=incomeJson($s.defaultPage);
        $.AJAX({
            type:"get",
            url:json.url,
            data:json.data,
            success:function(data){
                var DATA=getListData(data);
                $s.total=DATA.total;
                $s.incomeDatas = DATA.dataList;
                $s.$apply();
                //分页请求
                new Page({
                    parent:$("#copot-page2"),
                    nowPage:$s.defaultPage,
                    pageSize:$s.pageSize,
                    totalCount:DATA.total,
                }); //分页请求完毕
            }
        })
    }
    //余额帐单参数配置
    function incomeJson(nowPage){
        var json = {
            url:config.basePath+"school/accountBalance",
            data:{
                "schoolId":$s.schoolId,
                "operateType":$s.operateType,
                "pageNo":nowPage,
                "pageSize":$s.pageSize,
                "startTime":$s.startTime,
                "endTime":$s.endTime,
                "orderId":$s.orderId
            }
        }
        /*增加搜索条件*/
        json.data[$s.searchType]=$s.search;
        return json;
    }

    //********************获取支出明细***********************/
    $s.getPayList = function(){
        $s.tabCurrent = "pay";
        var json=payJson($s.defaultPage);
        $.AJAX({
            type:"get",
            url:json.url,
            data:json.data,
            success:function(data){
                var DATA=getListData(data);
                $s.total=DATA.total;
                $s.payDatas = DATA.dataList;
                $s.$apply();
                //分页请求
                new Page({
                    parent:$("#copot-page3"),
                    nowPage:$s.defaultPage,
                    pageSize:$s.pageSize,
                    totalCount:DATA.total,
                }); //分页请求完毕
            }
        })
    }
    //余额帐单参数配置
    function payJson(nowPage){
        var json = {
            url:config.basePath+"school/costDetail",
            data:{
                "schoolId":$s.schoolId,
                "operateType":$s.operateType,
                "pageNo":nowPage,
                "pageSize":$s.pageSize,
                "startTime":$s.startTime,
                "endTime":$s.endTime,
                "orderId":$s.orderId
            }
        }
        return json;
    }

    /*******条件筛选时重新加载数据（根据TAB选项加载不同数据）***********************/
    $s.getDataList = function (){
        switch ($s.tabCurrent){
            case "balance":
                $s.getBalanceList();
                break;
            case "income":
                $s.getIncomeList();
                break;
            case "pay":
                $s.getPayList();
                break;
        }
    }




    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
        win.showLoading();
        $s.defaultPage=location.hash.substring(2) || 1;
        $s.getDataList();

    }

    /*按交易类型来筛选数据列表*/
    $s.getDataForOperate=function(){
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.operateType=$s.operateType;
        $s.getDataList();
    }

	/*按输入时间筛选数据列表*/
	$('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		win.showLoading();
        $s.getDataList();
		$s.$apply();
	});

    /*高级查询*/
    $s.getDataForSearch=function(){
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.getDataList();
    }






}]);