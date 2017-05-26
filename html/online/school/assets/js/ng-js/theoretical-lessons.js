var app=angular.module("app",["ngFilter"]);app.controller("lessonController",["$scope","$filter",function(e,t){e.defaultPage=location.hash.substring(2)||1,e.pageSize=10,e.editData={},e.getDataList=function(){$.AJAX({type:"GET",url:config.basePath+"school/enroll/theory",data:{pageNo:e.defaultPage,pageSize:parseInt(e.pageSize),ctimeBegin:e.ctimeBegin,ctimeEnd:e.ctimeEnd,dateBegin:e.dateBegin,dateEnd:e.dateEnd,state:e.state},success:function(t){var a=getListData(t);e.total=a.total,e.datas=a.dataList,e.$apply(),new Page({parent:$("#copot-page"),nowPage:e.defaultPage,pageSize:e.pageSize,totalCount:a.total})}})},e.getDataList(),window.onhashchange=function(){win.showLoading(),e.defaultPage=location.hash.substring(2)||1,e.getDataList()},e.getDataForPage=function(){win.showLoading(),e.defaultPage=1,e.getDataList()},e.closeWin=function(){$("#edit-win").fadeOut("fast")},e.editItem=function(t){$("#edit-win").fadeIn(),$("#edit-form").css("marginTop",parseInt(($(win).height()-$("#edit-form").height()-100)/2)+"px"),e.editData=t,e.editData.classDate=new Date(t.classDate).format("yyyy-MM-dd"),""!=t.classTime&&void 0!=t.classTime&&(e.editData.classTime_s=t.classTime.split("-")[0],e.editData.classTime_e=t.classTime.split("-")[1])},e.addItem=function(){$("#edit-win").fadeIn(),$("#edit-form").css("marginTop",parseInt(($(win).height()-$("#edit-form").height()-100)/2)+"px"),e.editData={}},e.getDataForState=function(t,a){$(t.target).addClass("active").siblings().removeClass("active"),e.state=a,e.getDataForPage()},e.getDataForCTime=function(a,i){switch($(a.target).addClass("active").siblings().removeClass("active"),i){case"":e.ctimeBegin=e.ctimeEnd="";break;case"0":e.ctimeBegin=t("date")((new Date).getTime()-6048e5,"yyyy-MM-dd"),e.ctimeEnd=t("date")((new Date).getTime(),"yyyy-MM-dd");break;case"1":e.ctimeBegin=t("date")((new Date).getTime()-1296e6,"yyyy-MM-dd"),e.ctimeEnd=t("date")((new Date).getTime(),"yyyy-MM-dd")}$("#cdate").val(""),e.getDataForPage()},e.getDataForSTime=function(a,i){switch($(a.target).addClass("active").siblings().removeClass("active"),i){case"":e.dateBegin=e.dateEnd="";break;case"0":e.dateBegin=t("date")((new Date).getTime()-6048e5,"yyyy-MM-dd"),e.dateEnd=t("date")((new Date).getTime(),"yyyy-MM-dd");break;case"1":e.dateBegin=t("date")((new Date).getTime()-1296e6,"yyyy-MM-dd"),e.dateEnd=t("date")((new Date).getTime(),"yyyy-MM-dd")}$("#sdate").val(""),e.getDataForPage()},e.submitItemInfo=function(){var t,a=!1;if($("#edit-form .form-control").each(function(e,i){return""==$(this).val()?(t=$(this).attr("placeholder"),a=!0,!1):void 0}),a)return void Layer.alert({width:300,height:150,type:"msg",title:t+"必须填写！"});var i=void 0==e.editData.theoryId?"school/enroll/theory":"school/enroll/theory/modify";Layer.confirm({width:320,height:160,title:"确认提交当前的理论课信息？",header:"提示"},function(){$.AJAX({type:"POST",url:config.basePath+i,data:{theoryId:e.editData.theoryId,classDate:e.editData.classDate,classTime:e.editData.classTime_s+"-"+e.editData.classTime_e,className:e.editData.className,cardDesc:e.editData.cardDesc,contactName:e.editData.contactName,classPlace:e.editData.classPlace,contactMobile:e.editData.contactMobile},success:function(t){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),$("#edit-win").fadeOut("fast"),e.getDataList()}})})},e.comfirmLesson=function(t){var a=new Date(t.classDate).format("yyyy-MM-dd"),i=a+" "+t.classTime.substring(0,5);if(new Date(i).getTime()-(new Date).getTime()<0)return void Layer.alert({width:400,height:150,type:"msg",title:"已超过上课时间,本课程无效!"});if(0==t.total)return void Layer.alert({width:400,height:150,type:"msg",title:"请先安排上课学生,然后再确认开课!"});var s;$.AJAX({url:config.basePath+"school/enroll/theory/msgInfo?state=1",async:!1,type:"GET",success:function(e){s=e.result.pageData,s=s.format(a,t.cardDesc,t.classPlace,t.className,t.classTime,t.contactName+" "+t.contactMobile)}});var n="您是否确认要提交该课程并短信通知学员上课？";new Date(i).getTime()-(new Date).getTime()<72e4&&(n="距离上课时间不足12小时，您确认继续提交该课程并短信通知学员上课？");var o="<h4 align='center'><strong>"+n+"</strong></h4>";o+="<div class='msg-padding'>短信内容预览：<br><hr class='sub-line'/>"+s+"</div>",Layer.confirm({width:650,height:350,title:o,header:"确认开课"},function(){$.AJAX({url:config.basePath+"school/enroll/theory/class",data:{theoryId:t.theoryId,state:1},success:function(t){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),e.getDataForPage()}})})},e.cancelLesson=function(t){var a,i=new Date(t.classDate).format("yyyy-MM-dd");$.AJAX({url:config.basePath+"school/enroll/theory/msgInfo?state=3",async:!1,type:"GET",success:function(e){a=e.result.pageData,a=a.format(i,t.className)}});var s="您是否确认要取消该课程并短信通知学员？",n="<h4 align='center'><strong>"+s+"</strong></h4>";n+="<div class='msg-padding'>短信内容预览：<br><hr class='sub-line'/>"+a+"</div>",Layer.confirm({width:600,height:350,title:n,header:"取消课程"},function(){$.AJAX({url:config.basePath+"school/enroll/theory/class",data:{theoryId:t.theoryId,state:3},success:function(t){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),e.getDataForPage()}})})},$(".classsDate").datetimepicker({startDate:new Date,autoclose:1,startView:2,minView:2,maxView:2,forceParse:0}),$(".timepicker").datetimepicker({autoclose:1,startView:1,minView:0,maxView:1,forceParse:0}),$(".timepicker").datetimepicker().on("show",function(t){$(".timepicker").datetimepicker("setStartDate",e.editData.classDate)}),$("#cdate").daterangepicker({format:"YYYY-MM-DD"},function(a,i,s){e.ctimeBegin=t("date")(a.toISOString(),"yyyy-MM-dd"),e.ctimeEnd=t("date")(i.toISOString(),"yyyy-MM-dd"),e.getDataForPage()}),$("#sdate").daterangepicker({format:"YYYY-MM-DD"},function(a,i,s){e.dateBegin=t("date")(a.toISOString(),"yyyy-MM-dd"),e.dateEnd=t("date")(i.toISOString(),"yyyy-MM-dd"),e.getDataForPage()})}]);