var app=angular.module("app",["ngFilter"]);app.controller("BonusDetails",["$scope","$filter",function(a,t){function e(t){var e={url:config.basePath+"bonus/queryDetail",data:{pageNo:t,pageSize:parseInt(a.pageSize),id:a.id}};return e}a.defaultPage=1,a.pageSize=5,a.id=getQueryString("id"),a.getDataList=function(){var t=e(a.defaultPage);$.AJAX({type:"get",url:t.url,data:t.data,success:function(t){var l=getListData(t);a.total=l.total,a.datas=l.dataList,console.log(a.datas),a.$apply(),new Page({parent:$("#copot-page"),nowPage:a.defaultPage,pageSize:a.pageSize,totalCount:l.total,type:2,callback:function(t,l){var o=e(t);$.AJAX({type:"get",url:o.url,data:o.data,success:function(t){var e=getListData(t);a.datas=e.dataList,console.log(a.datas),a.$apply()}})}})}})},a.getDataList()}]);