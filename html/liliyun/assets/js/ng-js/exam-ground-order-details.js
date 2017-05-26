/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("ExamGroundOrderDetails",["$scope","$filter",function($s,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.orderId=getQueryString("orderId");

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
	}

/*--------------------------------------订单详情-----------------------------------------------------*/
	/*获取数据列表并展示*/
	$s.getOrderDetailsData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"examPlace/order/info",
			data:{
				orderId : $s.orderId
			},
			success:function(data){
				$s.data=JSON.parse(data.result.pageData)[0];
                console.log($s.data)
				$s.$apply();
			}
		});
	};	
	$s.getOrderDetailsData();
	/*参数配置函数*/

    /*-----------------------------------关闭订单------------------------------------------------------*/
    /*关闭订单*/
    $s.closeOrder=function(){
        Layer.confirmNotByTextAlert({
            header:"关闭订单",
            width:400,
            height:260,
            botByText:'请填写关闭理由',
            title:"请确认关闭该订单",
        },function(notByText){
            $.AJAX({
                url:config.basePath+"examPlace/order/update",
                data:{
                    orderId:$s.orderId,
                    remark:notByText,
                    state:5
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    /*更新列表*/
                    $s.getOrderDetailsData();
                }
            });/*AJAX end*/
        });
    }




}]);