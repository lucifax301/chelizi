var app=angular.module("app",["ngFilter","ngSelects"]);app.controller("arrangeStudentLessonController",["$scope","$rootScope","$filter","Selects",function(t,e,a,i){t.defaultPage=location.hash.substring(2)||1,t.pageSize=10,t.theoryId=getQueryString("theoryId"),t.isImport=0,t.applystate,t.searchPhoneNum,$.AJAX({url:config.basePath+"school/enroll/theory/one",type:"GET",async:!1,data:{theoryId:t.theoryId},success:function(e){t.classItem=getListData(e)}}),t.getDataList=function(){var e=0==t.isImport?"student/lili-batch":"student/jx-batch";$.AJAX({type:"GET",url:config.basePath+e,data:{pageNo:t.defaultPage,pageSize:parseInt(t.pageSize),applyexam:101,phoneNum:t.searchPhoneNum,isImport:t.isImport,applystate:t.applystate},success:function(e){var a=getListData(e);t.total=a.total,t.datas=a.dataList,i.selects({datas:t.datas,whichId:"studentId"}),t.$apply(),new Page({parent:$("#copot-page"),nowPage:t.defaultPage,pageSize:t.pageSize,totalCount:a.total})}})},t.getDataList(),t.getDataForType=function(e,a){$(e.target).addClass("active").siblings().removeClass("active"),t.isImport=a,t.getDataForPage()},t.getDataForState=function(e,a){$(e.target).addClass("active").siblings().removeClass("active"),t.applystate=a,t.getDataForPage()},t.getDataForPage=function(){win.showLoading(),t.defaultPage=1,t.getDataList()},window.onhashchange=function(){win.showLoading(),t.defaultPage=location.hash.substring(2)||1,t.getDataList()},t.comfirmSubmit=function(){return e.idList.length?void Layer.confirm({width:350,height:160,title:"您确认当前的学生安排吗？",header:"确认"},function(){$.AJAX({url:config.basePath+"school/enroll/theoryStudent",type:"POST",data:{studentIds:e.idList.toString(),theoryId:t.theoryId},success:function(a){e.idList=[],Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList()}})}):(Layer.alert({width:300,height:150,type:"msg",title:"请选择需要安排的学员！"}),!1)},t.cancelSubmit=function(){return e.idList.length?void Layer.confirm({width:350,height:160,title:"您确认取消当前的学生安排吗？",header:"取消"},function(){$.AJAX({url:config.basePath+"school/enroll/theoryStudent/delete",type:"POST",data:{studentIds:e.idList.toString(),theoryId:t.theoryId},success:function(a){e.idList=[],Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList()}})}):(Layer.alert({width:300,height:150,type:"msg",title:"请选择需要取消的学员！"}),!1)}}]);