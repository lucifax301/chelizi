var app=angular.module("app",["ngFilter"]);app.controller("studentMessage",["$scope","$filter",function(t,a){function e(a){var e={url:config.basePath+"ept/batch",data:{pageNo:a,pageSize:parseInt(t.pageSize)}};return e}function i(a){var e={name:t.editData.name,phoneNum:t.editData.phoneNum};return"edit"==t.editType&&angular.extend(e,{fieldId:t.editData.fieldId}),{url:config.basePath+a,data:e}}t.defaultPage=location.hash.substring(2)||1,t.pageSize=10,t.getDataList=function(){var a=e(t.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var e=getListData(a);t.datas=e.dataList,t.$apply(),console.log(t.datas),new Page({parent:$("#copot-page"),nowPage:t.defaultPage,pageSize:t.pageSize,totalCount:e.total})}})},t.getDataList(),window.onhashchange=function(){win.showLoading(),t.defaultPage=location.hash.substring(2)||1,t.getDataList()},$.AJAX({type:"get",url:config.basePath+"school/queryCity",data:{rlevel:2,pid:""},success:function(a){t.citys=JSON.parse(a.result.pageData)}}),t.packEditLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.pack-alert").fadeOut("fast")})},t.editData={},t.editType="add",t.packEdit=function(a,e){$(".pack-alert").fadeIn("fast"),$("#edit-content").css("marginTop",parseInt(($(win).height()-$("#edit-content").height()-100)/2)+"px"),"edit"==a?(t.editData=e,e.cityId&&(t.editData.region=e.cityId),t.editType="edit"):(t.editData={},t.editType="add")},t.submitEditMsg=function(a){var e=i("add"==t.editType?"ept/add":"ept/update");return t.editData.name&&regCombination("*").test(t.editData.name)?void $.AJAX({url:e.url,data:e.data,success:function(e){$(a.target).parents("div.add-new-school").fadeOut("fast"),t.getDataList()},error:function(t){console.log(t)}}):(Layer.alert({width:300,height:150,type:"msg",title:"请填写驾校名称"}),!1)}}]);