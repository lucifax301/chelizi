var app=angular.module("app",["ngFilter","ngSelects"]);app.controller("lessonsDetailController",["$scope","$rootScope","$filter","Selects",function(t,e,a,s){t.defaultPage=location.hash.substring(2)||1,t.pageSize=10,t.theoryId=getQueryString("theoryId"),$.AJAX({url:config.basePath+"school/enroll/theory/one",type:"GET",async:!1,data:{theoryId:t.theoryId},success:function(e){t.classItem=getListData(e)}}),t.getDataList=function(){$.AJAX({type:"GET",url:config.basePath+"school/enroll/theoryStudent",data:{pageNo:t.defaultPage,pageSize:parseInt(t.pageSize),theoryId:t.theoryId,isImport:t.isImport,state:t.state},success:function(e){var a=getListData(e);t.total=a.total,t.datas=a.dataList,s.selects({datas:t.datas,whichId:"studentId",num:""}),t.$apply(),new Page({parent:$("#copot-page"),nowPage:t.defaultPage,pageSize:t.pageSize,totalCount:a.total})}})},t.getDataList(),t.exportExcel=function(){$.AJAX({type:"GET",url:config.basePath+"school/enroll/theoryStudent/export-excel",data:{pageNo:t.defaultPage,pageSize:parseInt(t.pageSize),theoryId:t.theoryId,isImport:t.isImport,state:t.state}})},window.onhashchange=function(){win.showLoading(),t.defaultPage=location.hash.substring(2)||1,t.getDataList()},t.getDataForPage=function(){win.showLoading(),t.defaultPage=1,t.getDataList()},t.getDataForType=function(e,a){$(e.target).addClass("active").siblings().removeClass("active"),t.isImport=a,t.getDataForPage()},t.getDataForState=function(e,a){$(e.target).addClass("active").siblings().removeClass("active"),t.state=a,t.getDataForPage()},t.getDataForPage=function(){win.showLoading(),t.defaultPage=1,t.getDataList()},t.arrangeStudent=function(){var e=new Date(t.classItem.classDate).format("yyyy-MM-dd"),a=e+" "+t.classItem.classTime.substring(0,5);return new Date(a).getTime()-(new Date).getTime()<0?void Layer.alert({width:400,height:150,type:"msg",title:"已超过上课时间,本课程无效!"}):void(window.location.href="arrange-student-lesson.html?theoryId="+t.classItem.theoryId)},t.deleteItem=function(a){Layer.confirm({width:350,height:160,title:"您确认取消当前的学生安排吗？",header:"取消"},function(){$.AJAX({url:config.basePath+"school/enroll/theoryStudent/delete",type:"POST",data:{studentIds:a,theoryId:t.theoryId},success:function(a){e.idList=[],Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList()}})})},t.changeAttendState=function(a){if(!e.idList.length)return Layer.alert({width:300,height:150,type:"msg",title:"请选择操作学员！"}),!1;var s=1==a?"出勤":"缺勤";Layer.confirm({width:350,height:160,title:"您确认把所选学员置为["+s+"]？",header:"已出勤"},function(){$.AJAX({url:config.basePath+"school/enroll/theoryStudent/state",type:"POST",data:{studentIds:e.idList.toString(),theoryId:t.theoryId,state:a},success:function(a){e.idList=[],Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList()}})})},t.cancelLesson=function(){var e,a=new Date(t.classItem.classDate).format("yyyy-MM-dd");$.AJAX({url:config.basePath+"school/enroll/theory/msgInfo?state=3",async:!1,type:"GET",success:function(s){e=s.result.pageData,e=e.format(a,t.classItem.className)}});var s="您是否确认要取消该课程并短信通知学员？",o="<h4 align='center'><strong>"+s+"</strong></h4>";o+="<div class='msg-padding'>短信内容预览：<br><hr class='sub-line'/>"+e+"</div>",Layer.confirm({width:600,height:350,title:o,header:"取消课程"},function(){$.AJAX({url:config.basePath+"school/enroll/theory/class",data:{theoryId:t.classItem.theoryId,state:3},success:function(t){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),window.location.href="theoretical-lessons.html"}})})},t.comfirmLesson=function(){var e=new Date(t.classItem.classDate).format("yyyy-MM-dd"),a=e+" "+t.classItem.classTime.substring(0,5);if(new Date(a).getTime()-(new Date).getTime()<0)return void Layer.alert({width:400,height:150,type:"msg",title:"已超过上课时间,本课程无效!"});if(0==t.classItem.total)return void Layer.alert({width:400,height:150,type:"msg",title:"请先安排上课学生,然后再确认开课!"});var s;$.AJAX({url:config.basePath+"school/enroll/theory/msgInfo",async:!1,type:"GET",success:function(a){s=a.result.pageData,s=s.format(e,t.classItem.cardDesc,t.classItem.classPlace,t.classItem.className,t.classItem.classTime,t.classItem.contactName+" "+t.classItem.contactMobile)}});var o="您是否确认要提交该课程并短信通知学员上课？";new Date(a).getTime()-(new Date).getTime()<72e4&&(o="距离上课时间不足12小时，您确认继续提交该课程并短信通知学员上课？");var i="<h4 align='center'><strong>"+o+"</strong></h4>";i+="<div class='msg-padding'>短信内容预览：<br><hr class='sub-line'/>"+s+"</div>",Layer.confirm({width:650,height:350,title:i,header:"确认开课"},function(){$.AJAX({url:config.basePath+"school/enroll/theory/class",data:{theoryId:t.classItem.theoryId,state:1},success:function(t){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),window.location.href="theoretical-lessons.html"}})})}}]);