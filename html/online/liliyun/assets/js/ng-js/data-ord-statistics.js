var app=angular.module("app",["ngFilter"]);app.controller("DataOrdStatistics",["$scope","$filter",function(a,t){function e(t){var e={url:config.basePath+"statistics-total/order",data:{pageNo:t,pageSize:parseInt(a.pageSize),startTime:a.startTime,endTime:a.endTime,cityId:a.cityId}};return e}a.defaultPage=location.hash.substring(2)||1,a.pageSize=10,a.startTime="",a.endTime="",a.cityId="",a.data={pages:10,total:100,pageSize:10,pageNo:1,dataList:[{schoolId:201,name:"深港",region:"广东-深圳",reverseLim:"1000",phoneNum:"13476225415",coachCount:25121,posDesc:"这里面是深圳港的介绍"}]},a.datas=a.data.dataList,a.getDataList=function(){var t=e(a.defaultPage);$.AJAX({type:"get",url:t.url,data:t.data,success:function(t){a.allDatas=t.result;var e=JSON.parse(t.result.pageData);console.log(e),a.datas=e.dataList,a.$apply(),new Page({parent:$("#copot-page"),nowPage:a.defaultPage,pageSize:a.pageSize,totalCount:e.total})}})},a.getDataList(),window.onhashchange=function(){win.showLoading(),a.defaultPage=location.hash.substring(2)||1,a.getDataList()},a.getDataForTime=function(e,i){switch($(e.target).addClass("active").siblings().removeClass("active"),i){case"all":a.startTime=a.endTime="";break;case"0":a.startTime=t("date")((new Date).getTime()-6048e5,"yyyy-MM-dd"),a.endTime=t("date")((new Date).getTime(),"yyyy-MM-dd");break;case"1":a.startTime=t("date")((new Date).getTime()-1296e6,"yyyy-MM-dd"),a.endTime=t("date")((new Date).getTime(),"yyyy-MM-dd")}win.showLoading(),a.getDataList()},$("#reservation").daterangepicker({format:"YYYY/MM/DD"},function(e,i,n){a.startTime=a.endTime="",a.startTime=t("date")(e.toISOString(),"yyyy-MM-dd"),a.endTime=t("date")(i.toISOString(),"yyyy-MM-dd"),win.showLoading(),a.getDataList(),a.$apply()}),queryCity({callback:function(t){a.citys=t,a.$apply()}}),a.getDataForCity=function(){win.showLoading(),a.getDataList()},a.getDataForPage=function(){win.showLoading(),a.getDataList()}}]);