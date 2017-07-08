var app=angular.module("app",["ngFilter","ngSelects"]);app.controller("SchoolsDetails",["$scope","$filter",function(e,a){function t(a){var t={url:config.basePath+"school/touchBalance",data:{schoolId:e.schoolId,operateType:e.operateType,pageNo:a,pageSize:e.pageSize,startTime:e.startTime,endTime:e.endTime,orderId:e.orderId}};return t.data[e.searchType]=e.search,t}function o(a){var t={url:config.basePath+"school/accountBalance",data:{schoolId:e.schoolId,operateType:e.operateType,pageNo:a,pageSize:e.pageSize,startTime:e.startTime,endTime:e.endTime,orderId:e.orderId}};return t.data[e.searchType]=e.search,t}function n(a){var t={url:config.basePath+"school/costDetail",data:{schoolId:e.schoolId,operateType:e.operateType,pageNo:a,pageSize:e.pageSize,startTime:e.startTime,endTime:e.endTime,orderId:e.orderId}};return t}e.schoolId=getQueryString("schoolId"),e.defaultPage=location.hash.substring(2)||1,e.pageSize=10,e.startTime="",e.endTime="",e.operateType="",e.orderId="",e.schoolInfo={},e.tabCurrent="balance",$.AJAX({type:"get",url:config.basePath+"school/queryAccount",data:{schoolId:e.schoolId,ymDate:(new Date).date("y-m-d h:i:s")},success:function(a){e.schoolInfo=JSON.parse(a.result.pageData),e.schoolFee=JSON.parse(a.result.moneFree),e.$apply()}}),window.onhashchange=function(){win.showLoading(),e.defaultPage=location.hash.substring(2)||1},e.getBalance=function(){e.defaultPage=1,e.startTime="",e.endTime="",e.operateType="",e.orderId="",$("#reservation").val("自定义时间筛选"),e.getBalanceList()},e.getIncome=function(){e.defaultPage=1,e.startTime="",e.endTime="",e.operateType="",e.orderId="",$("#reservation").val("自定义时间筛选"),e.getIncomeList()},e.getPay=function(){e.defaultPage=1,e.startTime="",e.endTime="",e.operateType="",e.orderId="",$("#reservation").val("自定义时间筛选"),e.getPayList()},e.getBalanceList=function(){e.tabCurrent="balance";var a=t(e.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var t=getListData(a);e.total=t.total,e.balanceDatas=t.dataList,e.$apply(),new Page({parent:$("#copot-page"),nowPage:e.defaultPage,pageSize:e.pageSize,totalCount:t.total})}})},e.getBalanceList(),e.getIncomeList=function(){e.tabCurrent="income";var a=o(e.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var t=getListData(a);e.total=t.total,e.incomeDatas=t.dataList,e.$apply(),new Page({parent:$("#copot-page2"),nowPage:e.defaultPage,pageSize:e.pageSize,totalCount:t.total})}})},e.getPayList=function(){e.tabCurrent="pay";var a=n(e.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var t=getListData(a);e.total=t.total,e.payDatas=t.dataList,e.$apply(),new Page({parent:$("#copot-page3"),nowPage:e.defaultPage,pageSize:e.pageSize,totalCount:t.total})}})},e.getDataList=function(){switch(e.tabCurrent){case"balance":e.getBalanceList();break;case"income":e.getIncomeList();break;case"pay":e.getPayList()}},window.onhashchange=function(){win.showLoading(),e.defaultPage=location.hash.substring(2)||1,e.getDataList()},e.getDataForOperate=function(){win.showLoading(),e.defaultPage=1,e.operateType=e.operateType,e.getDataList()},$("#reservation").daterangepicker({format:"YYYY/MM/DD"},function(t,o,n){e.startTime=e.endTime="",e.startTime=a("date")(t.toISOString(),"yyyy-MM-dd"),e.endTime=a("date")(o.toISOString(),"yyyy-MM-dd"),win.showLoading(),e.getDataList(),e.$apply()}),e.getDataForSearch=function(){win.showLoading(),e.defaultPage=1,e.getDataList()}}]);