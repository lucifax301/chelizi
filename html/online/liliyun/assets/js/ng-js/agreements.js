var app=angular.module("app",["ngFilter"]);app.controller("Agreement",["$scope","$filter",function(t,e,a){function i(e){var a={url:config.basePath+"html-object/batch",data:{pageNo:e,pageSize:parseInt(t.pageSize)}};return a}function n(e){var a={hname:t.editData.hname,hdescription:t.editData.hdescription};return"edit"==t.editType&&angular.extend(a,{hid:t.editData.hid}),{url:config.basePath+e,data:a}}t.defaultPage=location.hash.substring(2)||1,t.pageSize=10,t.name="",t.checkSchoolSameName=0,t.getDataList=function(){var e=i(t.defaultPage);$.AJAX({type:"get",url:e.url,data:e.data,success:function(e){var a=getListData(e);t.datas=a.dataList,t.$apply(),new Page({parent:$("#copot-page"),nowPage:t.defaultPage,pageSize:t.pageSize,totalCount:a.total})}})},t.getDataList(),window.onhashchange=function(){win.showLoading(),t.defaultPage=location.hash.substring(2)||1,t.getDataList()},t.getDataForPage=function(){win.showLoading(),t.defaultPage=1,t.getDataList()},t.agreementEditLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.agreement-alert").fadeOut("fast")})},t.editData={},t.editType="add",t.agreementEdit=function(e,a){$(".agreement-alert").fadeIn("fast"),$("#edit-content").css("marginTop",parseInt(($(win).height()-$("#edit-content").height()-100)/2)+"px"),"edit"==e?(t.editData=a,t.editType="edit"):(t.editData={},t.editType="add")},t.submitEditMsg=function(e){var a=n("add"==t.editType?"html-object/add":"html-object/update");return t.editData.hname&&regCombination("*").test(t.editData.hname)?t.editData.hdescription?void $.AJAX({url:a.url,data:a.data,type:"get",success:function(a){$(e.target).parents("div.agreement-alert").fadeOut("fast"),t.getDataList()}}):(Layer.alert({width:300,height:150,type:"msg",title:"请填写协议内容"}),!1):(Layer.alert({width:300,height:150,type:"msg",title:"请填写协议名称"}),!1)}}]);