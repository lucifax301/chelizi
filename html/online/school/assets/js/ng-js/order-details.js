var app=angular.module("app",["ngFilter"]);app.controller("OrderDetails",["$scope","$filter",function(e,a){function t(a){var t={url:config.basePath+"logCommon/batch",data:{pageNo:a,pageSize:10,menuId:3,relateId:e.orderId}};return t}e.defaultPage=1,e.pageSize=10,e.orderId=getQueryString("orderId"),e.tableSwitch=function(a){switch($(a.target).addClass("active").siblings().removeClass("active"),$("#order-table").children().eq($(a.target).index()).show().siblings().hide(),win.showLoading(),$(a.target).index()){case 0:win.hideLoading();break;case 1:e.getOrderLogs()}},e.getOrderDetailsData=function(){$.AJAX({type:"get",url:config.basePath+"order/view",data:{orderId:e.orderId},success:function(a){e.data=JSON.parse(a.result.order),e.$apply()}})},e.getOrderDetailsData(),e.closeOrder=function(){Layer.confirm({width:300,height:160,title:"确认关闭选中的订单吗？",header:"关闭订单"},function(){$.AJAX({url:config.basePath+"order/update-batch",data:{orderIds:e.orderId,studentIds:e.data.studentId,coachIds:e.data.coachId},success:function(a){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),e.getOrderDetailsData()}})})},e.getOrderLogs=function(){var a=t(e.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var r=getListData(a);e.orderLogs=r.dataList,e.$apply(),new Page({parent:$("#log-page"),nowPage:e.defaultPage,pageSize:e.pageSize,totalCount:r.total,callback:function(a,r){var d=t(a);$.AJAX({type:"get",url:d.url,data:d.data,success:function(a){var t=getListData(a);e.orderLogs=t.dataList,e.$apply()}})}})}})}}]);