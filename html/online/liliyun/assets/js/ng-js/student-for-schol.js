var app=angular.module("app",["ngFilter","ngSelects"]);app.controller("Student",["$scope","$rootScope","$filter","Selects",function(t,a,e,s){function i(a){var e={url:config.basePath+"student/jx-batch",data:{pageNo:a,pageSize:parseInt(t.pageSize),applyCarType:t.carType,schoolNo:t.schoolNo,schoolType:1,applyexam:t.applystateTotal.split(",")[0],applystate:t.applystateTotal.split(",")[1],type:1}};return e.data[t.searchType]=t.search,e}function n(a){var e={phoneNum:t.editData.phoneNum,sex:parseInt(t.editData.sex),applyCarType:parseInt(t.editData.applyCarType),idNumber:t.editData.idNumber,name:t.editData.name,schoolId:t.editData.schoolId};return"edit"==t.editType&&angular.extend(e,{studentId:t.editData.studentId}),{url:config.basePath+a,data:e}}t.defaultPage=location.hash.substring(2)||1,t.pageSize=10,t.carType="",t.search="",t.searchType="name",t.schoolNo="",t.cityId="",t.cityError=!1,t.applystateTotal=",",t.data={pages:10,total:100,pageSize:10,pageNo:1,dataList:[{studentId:0,name:"小张",applyexam:5,applystate:100,sex:0,phoneNum:"13476002586",applyCarType:1,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},{studentId:2,name:"小张",applyexam:6,applystate:100,sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},{studentId:3,name:"小张",applyexam:301,applystate:101,sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},{studentId:4,name:"小张",applyexam:302,applystate:100,sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0}]},t.datas=t.data.dataList,s.selects({datas:t.datas,whichId:"studentId"}),t.getDataList=function(){var a=i(t.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var e=getListData(a);t.total=e.total,t.datas=e.dataList,s.selects({datas:t.datas,whichId:"studentId"}),t.$apply(),new Page({parent:$("#copot-page"),nowPage:t.defaultPage,pageSize:t.pageSize,totalCount:e.total})}})},t.getDataList(),window.onhashchange=function(){win.showLoading(),t.defaultPage=location.hash.substring(2)||1,t.getDataList()},queryCity({callback:function(a){t.citys=a,t.$apply()}}),t.getSchools=function(){t.cityId?(t.checkHaveCity(),querySchool({cityId:t.cityId,callback:function(a){t.schools=a,t.$apply()}})):(t.schoolNo="",t.schools="")},t.checkHaveCity=function(){t.cityError=!t.cityId},t.getDataForSchool=function(){win.showLoading(),t.schoolNo=t.schoolNo,t.defaultPage=1,t.getDataList()},t.getDataForCarType=function(a,e){$(a.target).addClass("active").siblings().removeClass("active"),win.showLoading(),t.carType=e,t.defaultPage=1,t.checkAllTag.carType=$(a.target).text(),t.objElement.carType=$(a.target),t.getDataList()},t.studentStatesList=studentStatesListForSchool,t.comSearTabCheck=function(a,e){var s="DIV"==a.target.nodeName?$(a.target):$(a.target).parent("div");$("div.tab-par").removeClass("active"),$("div.tab-chr").hide(),s.addClass("active").next("div").show(),$("div.tab-par").find("span").attr("class","ion-arrow-up-b"),s.find("span").attr("class","ion-arrow-down-b"),"all"==e&&(t.defaultPage=1,t.getDataForStudentState(a,""))},t.getDataForStudentState=function(a,e){$(a.target).addClass("active").siblings().removeClass("active"),win.showLoading(),t.applystateTotal=e,t.defaultPage=1,t.checkAllTag.applystateTotal=$(a.target).text(),t.objElement.applystateTotal=$(a.target),t.getDataList()},t.getDataForPage=function(){win.showLoading(),t.defaultPage=1,t.getDataList()},t.getDataForSearch=function(){win.showLoading(),t.defaultPage=1,t.getDataList()},t.studentDataExport=function(){dataExportForIframe({getSearchs:i(t.defaultPage).data,total:t.total,url:"student/export-excel"})},t.checkAllTag={},t.objElement={},t.removeTag=function(a){win.showLoading(),t.checkAllTag=deleteJson(t.checkAllTag,a),t.objElement[a]&&("LI"==t.objElement[a][0].nodeName?t.objElement[a].parent().children().eq(0).addClass("active").siblings().removeClass("active"):"children"==$(t.objElement[a][0]).attr("data-chr")&&(t.objElement[a].parent().hide().children().removeClass("active"),t.objElement[a].parents("ul").find("div.tab-par").removeClass("active"),t.objElement[a].parents("ul").children().eq(0).children().addClass("active"))),t[a]="",t.getDataList()},t.removeAllTag=function(){jQuery.isEmptyObject(t.objElement)||(win.showLoading(),t.carType="",t.schoolNo="",t.applystateTotal="",clearAllActive(t.objElement),t.checkAllTag={},t.objElement={},$("div.tab-line ul").find(".tab-par").removeClass("active"),$("div.tab-line ul").find("li").eq(0).children("div").addClass("active"),$("div.tab-chr").hide().children("div").removeClass("active"),t.getDataList())},t.sutdentEditLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.edit-alert").fadeOut("fast")})},t.editData={},t.editType="add",t.studentEdit=function(a,e){$(".student-alert").fadeIn("fast"),$("#student-alert").css("marginTop",parseInt(($(win).height()-$("#student-alert").height()-100)/2)+"px"),"edit"==a?(t.editData=e,t.editType="edit"):(t.editData={},t.editType="add")},t.getSchoolDatas=function(a){console.log(a),a?(t.cityDataError=!1,querySchool({cityId:a,callback:function(a){t.schools=a,t.$apply()}})):(t.schools="",t.cityDataError=!0)},t.checkIsCityId=function(a){return t.cityDataError=!a},t.submitEditMsg=function(a){var e=n("add"==t.editType?"student/add":"student/update");return t.editData.name&&regCombination("chinese",[2,8]).test(t.editData.name)?t.editData.sex.toString()&&regCombination("*").test(t.editData.sex)?t.editData.phoneNum&&regCombination("phone").test(t.editData.phoneNum)?t.editData.applyCarType&&regCombination("*").test(t.editData.applyCarType)?void $.AJAX({url:e.url,data:e.data,success:function(e){$(a.target).parents("div.student-alert").fadeOut("fast"),Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList()}}):(Layer.alert({type:"msg",title:"请选择驾驶类别"}),!1):(Layer.alert({type:"msg",title:"请填手机正确的号码"}),!1):(Layer.alert({type:"msg",title:"请填选择性别"}),!1):(Layer.alert({type:"msg",title:"请填写姓名(2-8个中文)"}),!1)},t.closeAlert=function(){t.getDataList()},t.studentStates=[],t.haveStudentState=!1,t.incompleteData="",t.StudentStateLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.student-state").fadeOut("fast")})},t.changeStudentState=function(){return t.studentStates=[],$("#studentState").val(""),a.idList.length?($(".student-state").fadeIn("fast"),$("#student-state").css("marginTop",parseInt(($(win).height()-$("#student-state").height()-100)/2)+"px"),void getDataForStudentConfig({datas:getDataForKey({datas:t.datas,idList:t.idList,id:"studentId"}),check:"applyexam,applystate",checkData:studentSchoolJurisConfig,callback:function(a){t.studentStates=a,t.haveStudentState=!!a.length}})):(Layer.alert({width:300,height:150,type:"msg",title:"请选择需要置状态的学员！"}),!1)},t.changeErrorStudentLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.change-error-students").fadeOut("fast")})},t.studentStateChange=function(){"5,101"==$("#studentState").val()?$("#zhiliaoqs").show():$("#zhiliaoqs").hide()},t.submitStudentState=function(e){var s=$("#studentState").val();return alert(s),regCombination("*").test(s)?void $.AJAX({url:config.basePath+"student/reset-state",data:{applyexam:s.split(",")[0],applystate:s.split(",")[1],studentIds:a.idList.toString()},success:function(a){"fail"==a.status?($(".change-error-students").fadeIn("fast"),$("#change-error-students").css("marginTop","150px"),t.errDataList=studentStateRes(getListData(a)),t.$apply()):($(e.target).parents("div.student-state").fadeOut("fast"),Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getDataList())}}):(Layer.alert({width:300,height:150,type:"msg",title:"请选择学员需要置的状态！"}),!1)},t.closeChanErrorStud=function(t){$(t.target).parents("div.change-error-students").fadeOut("fast")}}]);