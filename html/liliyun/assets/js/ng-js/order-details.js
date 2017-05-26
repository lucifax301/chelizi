/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("OrderDetails",["$scope","$rootScope","$filter",function($s,$rootScope,$filter){

	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.orderId=getQueryString("orderId");
	$s.pageSize=10;    //每页显示-显示条数
	$s.index=0;
	$s.refundsSum=""; //退款金额
	$s.refundsRemark=""; //退款理由
	$s.paytotal="";  //实付金额
	$s.tableSwitch=function($event){
		$s.index=tabPageDetails($event);
		win.showLoading();
		/*tab 其切换按需加载*/
		location.hash="##1";
		tabDate($s.index)
	}

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		tabDate($s.index);
	}

	/*tab 切换加载列表*/
	function tabDate(index){
		switch (index) {
			case 0: 
				/*教练历史订单列表*/
				$s.getOrderDetailsData();
				break;
			case 1:
				/*请求绑定学员单数据列表*/
				$s.getOrderLogs();
				break;
		}
	}
/*--------------------------------------订单详情-----------------------------------------------------*/	
	/*获取数据列表并展示*/
	$s.getOrderDetailsData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"order/view",
			data:{
				orderId : $s.orderId
			},
			success:function(data){
				$s.data=JSON.parse(data.result.order);  //订单详情数据
				$s.paytotal=$s.data.payTotal/100;       //获得实付金额
				$s.datastu=JSON.parse(data.result.stuComment);  // 教练评价学员数据
				$s.datacoach=JSON.parse(data.result.coachComment);  //学员评价教练数据
				//星级评价
				scoreHtml(1,'#zjzw')  //直角转弯
				scoreHtml(1,'#pddd')  //坡道定点
				scoreHtml(2,'#dcrk')  //倒车入库
				scoreHtml(3,'#qxxs')  //曲线行驶
				scoreHtml(4,'#cbtc')  //侧边停车
				scoreHtml(5,'#zhpf')  //综合评分
				$s.$apply();
			}
		});
	};	
	$s.getOrderDetailsData();
	/*参数配置函数*/







	/*--------------------------------------关闭订单-----------------------------------------------------*/
    /*关闭订单*/
    $s.closeOrder=function(){
		Layer.confirm({width:300,height:160,title:"确认关闭选中的订单吗？",header:"关闭订单"},function(){
			$.AJAX({
				url:config.basePath+"order/update-batch",
				data:{
					orderIds:$s.orderId,
					studentIds:$s.data.studentId,
					coachIds:$s.data.coachId,
				},
				success:function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getOrderDetailsData();
				}
			});
		});/*layer end*/
    }

	/*--------------------------------------申请退款-----------------------------------------------------*/
	/*申请退款*/
	/*include 加载完成后执行*/
	$s.applyRefunds=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.data-refunds").fadeOut("fast");
		});
	}

	$s.refunds=function(){
		/*显示弹出层且垂直居中*/
		$(".data-refunds").fadeIn("fast");  //显示弹出层
		$("#refunds-alert").css("marginTop",parseInt(($(win).height()- $("#refunds-alert").height()-100)/2)+"px");  //居中

		/*确认申请退款*/
		$s.submitRefundsMsg=function($event){
			if(!regCombination('*').test($s.refundsSum)){
				Layer.alert({width:300,height:150,type:"msg",title:"请填写退款金额！",header:"申请退款"});
				return false;
			}else if($s.refundsSum >= $s.paytotal){
				Layer.alert({width:300,height:150,type:"msg",title:"退款金额必须小于实付金额！",header:"申请退款"});
				return false;
			}else if ($s.refundsSum < 0){
				Layer.alert({width:300,height:150,type:"msg",title:"退款金额不能为负数！",header:"申请退款"});
				return false;
			}else if(!regCombination('number').test($s.refundsSum)){
				Layer.alert({width:300,height:150,type:"msg",title:"退款金额请填写数字格式！",header:"申请退款"});
				return false;
			}else if(!regCombination('*').test($s.refundsRemark)){
				Layer.alert({width:300,height:150,type:"msg",title:"请填写退款理由！",header:"申请退款"});
				return false;
			}else {
				$.AJAX({
					url:config.basePath+"order/refund",
					data:{
						orderId:$s.orderId,
						refundMoney:$s.refundsSum,
						remark:$s.refundsRemark,
					},
					success:function(data){
						/*关闭弹出层*/
						$($event.target).parents("div.data-refunds").fadeOut("fast");
						Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
						/*更新列表*/
						$s.getOrderDetailsData();
					}
				});
			};
		};
	};






	/*-----------------------------------订单操作日志---------------------------------------------------*/
	
	/*获取数据列表并展示*/
	$s.getOrderLogs=function(){
		var json=orderLogsJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.orderLogs=DATA.dataList;
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#log-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:DATA.total, 
				}); //分页请求完毕
			}
		});
	};	
	/*参数配置函数*/
	function orderLogsJson(nowPage){
		var json={
			url:config.basePath+"logCommon/batch",
			data: {
				"pageNo": nowPage,
				"pageSize":10,
				"menuId":3,
				"relateId":$s.orderId
			}
		};
		return json;
	};



}]);