var app=angular.module("app",["ngFilter"]);app.controller("signUpPackAdd",["$scope","$filter",function(t,a){function e(a){var e={url:config.basePath+"ept/batch",data:{pageNo:a,pageSize:parseInt(t.pageSize),cityId:t.cityId,name:t.name}};return e}function i(a){var e={name:t.editData.name,cityId:parseInt(t.editData.cityId),phoneNum:t.editData.phoneNum};return{url:config.basePath+a,data:e}}t.defaultPage=location.hash.substring(2)||1,t.pageSize=10,t.name="",t.qiniuToken,t.getDataList=function(){var a=e(t.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var e=getListData(a);t.datas=e.dataList,t.$apply(),new Page({parent:$("#copot-page"),nowPage:t.defaultPage,pageSize:t.pageSize,totalCount:e.total})}})},t.getDataList(),t.submitEditMsg=function(a){var e=i("ept/add");return t.editData.name&&regCombination("*").test(t.editData.name)?void $.AJAX({url:e.url,data:e.data,type:"get",success:function(t){clearTimeout(a),Layer.alert({width:300,height:150,type:"msg",title:"添加报名包成功！2秒后跳回报名包列表页<br>如果没有自动跳转，请点击如下链接<br><a href='"+config.basePath+"sign-up-pack-list.html'>"+config.basePath+"sign-up-pack-list.html</a>"});var a=setTimeout(function(){window.location.href=config.basePath+"sign-up-pack-list.html"},2e3)},error:function(t){console.log(t)}}):(Layer.alert({width:300,height:150,type:"msg",title:"请填写驾校名称"}),!1)},t.packItemToggle=function(t){var a=$(t.target).html();"-"==a?$(t.target).html("+").parent("h2.z-toggle-h2").next("div").slideUp():"+"==a?$(t.target).html("-").parent("h2.z-toggle-h2").next("div").slideDown():alert("请注意ICO的+号和-号")}}]);