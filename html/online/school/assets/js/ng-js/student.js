var app=angular.module("app",["ngFilter","ngSelects"]);app.controller("Student",["$scope","$rootScope","$filter","Selects",function(t,e,a,i){function s(e){var a={url:config.basePath+"student/lili-batch",data:{pageNo:e,pageSize:parseInt(t.pageSize),applyCarType:t.carType,accountStatus:t.accountStatus,learningProg:t.learningProg,entryStatus:t.entryStatus,applyexam:t.applystateTotal.split(",")[0],applystate:t.applystateTotal.split(",")[1],type:0}};return a.data[t.searchType]=t.search,a}function n(e){var a={phoneNum:t.editData.phoneNum,sex:parseInt(t.editData.sex),applyCarType:parseInt(t.editData.applyCarType),idNumber:t.editData.idNumber,name:t.editData.name,studentId:t.editData.studentId};return{url:config.basePath+e,data:a}}t.defaultPage=location.hash.substring(2)||1,t.pageSize=10,t.carType="",t.search="",t.searchType="name",t.applystateTotal="",t.applyttid="",t.getDataList=function(){var e=s(t.defaultPage);$.AJAX({type:"get",url:e.url,data:e.data,success:function(e){var a=getListData(e);t.total=a.total,t.datas=a.dataList,i.selects({datas:t.datas,whichId:"studentId"}),t.$apply(),new Page({parent:$("#copot-page"),nowPage:t.defaultPage,pageSize:t.pageSize,totalCount:a.total})}})},t.getDataList(),window.onhashchange=function(){win.showLoading(),t.defaultPage=location.hash.substring(2)||1,t.getDataList()},t.getDataForCarType=function(e,a){$(e.target).addClass("active").siblings().removeClass("active"),win.showLoading(),t.defaultPage=1,t.carType=a,t.checkAllTag.carType=$(e.target).text(),t.objElement.carType=$(e.target),t.getDataList()},t.studentStatesList=studentStatesListForSchool,t.comSearTabCheck=function(e,a){var i="DIV"==e.target.nodeName?$(e.target):$(e.target).parent("div");$("div.tab-par").removeClass("active"),$("div.tab-chr").hide(),i.addClass("active").next("div").show(),$("div.tab-par").find("span").attr("class","ion-arrow-up-b"),i.find("span").attr("class","ion-arrow-down-b"),"all"==a&&(t.defaultPage=1,t.getDataForStudentState(e,""))},t.getDataForStudentState=function(e,a){$(e.target).addClass("active").siblings().removeClass("active"),win.showLoading(),t.defaultPage=1,t.applystateTotal=a,t.checkAllTag.applystateTotal=$(e.target).text(),t.objElement.applystateTotal=$(e.target),t.getDataList()},t.getDataForPage=function(){win.showLoading(),t.defaultPage=1,t.getDataList()},t.getDataForSearch=function(){win.showLoading(),t.defaultPage=1,t.getDataList()},t.studentDataExport=function(){dataExportForIframe({getSearchs:s(t.defaultPage).data,total:t.total,url:"student/export-excel"})},t.checkAllTag={},t.objElement={},t.removeTag=function(e){win.showLoading(),t.checkAllTag=deleteJson(t.checkAllTag,e),t.objElement[e]&&("LI"==t.objElement[e][0].nodeName?t.objElement[e].parent().children().eq(0).addClass("active").siblings().removeClass("active"):"children"==$(t.objElement[e][0]).attr("data-chr")&&(t.objElement[e].parent().hide().children().removeClass("active"),t.objElement[e].parents("ul").find("div.tab-par").removeClass("active"),t.objElement[e].parents("ul").children().eq(0).children().addClass("active"))),t[e]="",t.getDataList()},t.removeAllTag=function(){jQuery.isEmptyObject(t.objElement)||(win.showLoading(),t.carType="",t.applystateTotal="",clearAllActive(t.objElement),t.checkAllTag={},t.objElement={},$("div.tab-line ul").find(".tab-par").removeClass("active"),$("div.tab-line ul").find("li").eq(0).children("div").addClass("active"),$("div.tab-chr").hide().children("div").removeClass("active"),t.getDataList())},t.uploadSerialnumber=function(){for(var a=getDataForKey({datas:t.datas,id:"studentId",idList:e.idList}),i=!0,s=0;s<a.length;s++)if(!(6==a[s].applyexam&&1==a[s].applystate||6==a[s].applyexam&&101==a[s].applystate)){i=!1;break}return 0==i?(Layer.alert({width:400,height:175,type:"msg",title:"导入失败，请检查导入学员的状态是否<br>是［受理中］或［受理失败］",header:"上传流水号"}),!1):void cerateFileFormData({url:config.basePath+"student/uploadWater",data:{type:1},callback:function(e){Layer.alertMor({width:450,height:220,type:"success",title:e.msg,callback:function(){$.AJAX({url:config.basePath+"student/uploadWater",data:{tagFileName:e.tagFileName,type:2},success:function(e){Layer.closeNowLayer("alertMorMask"),Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList()}})},close:function(){$.AJAX({url:config.basePath+"student/uploadWater",data:{tagFileName:e.tagFileName,type:3},success:function(){}})}})}})},t.sutdentEditLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.edit-alert").fadeOut("fast")})},t.editData={},t.studentEdit=function(e){console.log(e),$(".student-alert").fadeIn("fast"),$("#student-alert").css("marginTop",parseInt(($(win).height()-$("#student-alert").height()-100)/2)+"px"),t.editData=e},t.submitEditMsg=function(e){var a=n("student/update");return t.editData.phoneNum.length<11||!regCombination("*").test(t.editData.phoneNum)?(Layer.alert({type:"msg",height:150,title:"请填手机正确的号码"}),!1):void $.AJAX({url:a.url,data:a.data,success:function(a){$(e.target).parents("div.edit-alert").fadeOut("fast"),Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList()}})},t.closeAlert=function(){t.getDataList()},t.studentStates=[],t.haveStudentState=!1,t.incompleteData="",t.StudentStateLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.student-state").fadeOut("fast")})},t.changeStudentState=function(){return t.studentStates=[],$("#studentState").val(""),e.idList.length?($(".student-state").fadeIn("fast"),$("#student-state").css("marginTop",parseInt(($(win).height()-$("#student-state").height()-100)/2)+"px"),void getDataForStudentConfig({datas:getDataForKey({datas:t.datas,idList:t.idList,id:"studentId"}),check:"applyexam,applystate",checkData:studentSchoolJurisConfig,callback:function(e){t.studentStates=e,t.haveStudentState=!!e.length,console.log(e)}})):(Layer.alert({width:300,height:150,type:"msg",title:"请选择需要置状态的学员！"}),!1)},t.changeErrorStudentLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.change-error-students").fadeOut("fast")})},t.submitStudentState=function(a){var i=$("#studentState").val();return regCombination("*").test(i)?void $.AJAX({url:config.basePath+"student/reset-state",data:{applyexam:i.split(",")[0],applystate:i.split(",")[1],studentIds:e.idList.toString()},success:function(e){"fail"==e.status?($(".change-error-students").fadeIn("fast"),$("#change-error-students").css("marginTop","200px"),t.errDataList=studentStateRes(getListData(e)),t.$apply()):($(a.target).parents("div.student-state").fadeOut("fast"),Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList())}}):(Layer.alert({width:300,height:150,type:"msg",title:"请选择学员需要置的状态！"}),!1)},t.closeChanErrorStud=function(t){$(t.target).parents("div.change-error-students").fadeOut("fast")},t.collectData=function(){if(!e.idList.length)return Layer.alert({width:300,height:150,type:"msg",title:"请选择'表已寄出'的学员！",header:"驾校收表"}),!1;var a=getDataForKey({datas:t.datas,id:"studentId",idList:e.idList});t.applyttid=a[0].applyttid,console.log(t.applyttid);for(var i=[],s=0;s<a.length;s++){i.push(a[s].applyttid);var n=i.indexOf(i[s])}return 0!=n?(Layer.alert({width:330,height:175,type:"msg",title:"您只能选择同一城市的学员，<br>请重新选择学员！"}),!1):5!=a[0].applyexam||1!=a[0].applystate?(Layer.alert({width:355,height:175,type:"msg",title:"您只能把'表已寄出'的学员标记为驾校<br>已收表，请重新选择学员！",header:"驾校收表"}),!1):void Layer.confirm({width:300,height:160,title:"您确认已经收到所选学员的<br>报名表资料吗？",header:"驾校收表"},function(){$.AJAX({url:config.basePath+"student/reset-state",data:{applyexam:5,applystate:100,applyttid:t.applyttid,studentIds:e.idList.toString()},success:function(e){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList()}})})},t.acceptData=function(){if(!e.idList.length)return Layer.alert({width:300,height:150,type:"msg",title:"请选择'已收表'的学员！",header:"交表受理"}),!1;var a=getDataForKey({datas:t.datas,id:"studentId",idList:e.idList});return t.applyttid=a[0].applyttid,6!=a[0].applyexam||0!=a[0].applystate?(Layer.alert({width:355,height:175,type:"msg",title:"您只能把'已收表'的学员交到车管所<br>受理，请重新选择学员！",header:"交表受理"}),!1):void Layer.confirm({width:340,height:160,title:"您确认把所选学员的报名表资料<br>交到车管所受理吗？",header:"交表受理"},function(){$.AJAX({url:config.basePath+"student/reset-state",data:{applyexam:6,applystate:1,applyttid:t.applyttid,studentIds:e.idList.toString()},success:function(e){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList()}})})},t.errorData=function(){if(!e.idList.length)return Layer.alert({width:300,height:150,type:"msg",title:"请选择'受理中'的学员！",header:"交表失败"}),!1;var a=getDataForKey({datas:t.datas,id:"studentId",idList:e.idList});return t.applyttid=a[0].applyttid,6!=a[0].applyexam||1!=a[0].applystate?(Layer.alert({width:355,height:175,type:"msg",title:"您只能把'受理中'的学员标记为受理<br>失败，请重新选择学员！",header:"交表失败"}),!1):void Layer.confirmNotByTextAlert({header:"交表受理",width:380,height:250,title:"<b>学员在车管所受理失败</b>"},function(a){$.AJAX({url:config.basePath+"student/reset-state",data:{applyexam:6,applystate:101,applyttid:t.applyttid,studentIds:e.idList.toString(),note:a},success:function(e){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList()}})})}}]);