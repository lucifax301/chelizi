var app=angular.module("app",["ngFilter"]);app.controller("car-brand",["$scope","$filter",function(a,t){function e(t){var e={url:config.basePath+"brand-car/batch",data:{pageNo:t,pageSize:parseInt(a.pageSize),brandname:a.brandname,carname:a.carname}};return e}function i(t){var e={carNo:a.editData.carNo,carLevel:parseInt(a.editData.carLevel),driveNumber:a.editData.driveNumber,driveType:parseInt(a.editData.driveType)};return"edit"==a.editType&&angular.extend(e,{carId:a.editData.carId}),{url:config.basePath+t,data:e}}a.defaultPage=location.hash.substring(2)||1,a.pageSize=10,a.carType="",a.brandNo="",a.brandName="",a.carNo="",a.carName="",a.isCommon=0,a.getDataList=function(){var t=e(a.defaultPage);$.AJAX({type:"get",url:t.url,data:t.data,success:function(t){var e=getListData(t);a.total=e.total,a.datas=e.dataList,a.$apply(),new Page({parent:$("#copot-page"),nowPage:a.defaultPage,pageSize:a.pageSize,totalCount:e.total})}})},a.getDataList(),$.AJAX({type:"get",url:config.basePath+"brand-car/all-brand",success:function(t){a.brands=JSON.parse(t.result.pageData),a.$apply()}}),$.AJAX({type:"get",url:config.basePath+"brand-car/car",data:{brandname:a.brandName},success:function(t){a.carBrands=JSON.parse(t.result.pageData),a.$apply()}}),window.onhashchange=function(){win.showLoading(),a.defaultPage=location.hash.substring(2)||1,a.getDataList()},a.getBrandName=function(t){win.showLoading(),a.brandName=t,a.defaultPage=1,a.getDataList()},a.getDataForCarName=function(t,e){$(t.target).addClass("active").siblings().removeClass("active"),win.showLoading(),a.defaultPage=1,a.seqid=e,a.getDataList()},a.carEditLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.edit-alert").fadeOut("fast")})},a.editData={},a.editType="add",a.carEdit=function(t,e){$(".edit-alert").fadeIn("fast"),$("#edit-content").css("marginTop",parseInt(($(win).height()-$("#edit-content").height()-100)/2)+"px"),"edit"==t?(a.editData=e,a.editType="edit"):(a.editData={},a.editType="add")},a.submitEditMsg=function(t){var e=i("add"==a.editType?"brand-car/add":"brand-car/update");return a.editData.carNo&&regCombination("special").test(a.editData.carNo)?void $.AJAX({url:e.url,data:e.data,success:function(e){$(t.target).parents("div.edit-alert").fadeOut("fast"),Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),a.getDataList()}}):(Layer.alert({width:300,height:150,type:"msg",title:"请填写车牌号"}),!1)},a.closeAlert=function(){a.getDataList()}}]);