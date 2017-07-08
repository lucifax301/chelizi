var app=angular.module("app",["ngFilter","ngSelects"]);app.controller("coachDetails",["$scope","$rootScope","$filter","Selects",function(t,a,e,i){function n(a){switch(a){case 0:t.getOrderDataList();break;case 1:t.getCoachDataList();break;case 2:t.getNewCarData();break;case 3:t.indAccount();break;case 4:t.getCoachLogs()}}function d(a){return{url:config.basePath+a,data:{coachId:t.coachId,phoneNum:t.editData.phoneNum,sex:parseInt(t.editData.sex),carType:parseInt(t.editData.carType),idNumber:t.editData.idNumber,name:t.editData.name}}}function s(a){var e={url:config.basePath+"order/batch",data:{pageNo:a,pageSize:parseInt(t.orderPageSize),coachId:t.coachId,orderId:t.orderId,startTime:t.startTime,endTime:t.endTime,payState:t.payState}};return e}function o(a){var e={url:config.basePath+"coach/student",data:{pageNo:a,pageSize:parseInt(t.studentPageSize),coachId:t.coachId,name:t.name}};return e}function c(a){var e={url:config.basePath+"student/no-coach",data:{pageNo:a,pageSize:parseInt(t.newStudentPageSize),name:t.newStudent}};return e}function r(a){var i={url:config.basePath+"coach/account",data:{pageNo:a,pageSize:t.pageSize,coachId:t.coachId,startTime:t.startTime?t.startTime:e("date")((new Date).getTime()-6048e5,"yyyy-MM-dd"),endTime:t.endTime?t.endTime:e("date")((new Date).getTime(),"yyyy-MM-dd"),operateType:t.operateType}};return i}function g(a){var e={url:config.basePath+"logCommon/batch",data:{pageNo:a,pageSize:t.logPageSize,menuId:2,relateId:t.coachId}};return e}t.coachId=getQueryString("coachId"),t.defaultPage=location.hash.substring(2)||1,t.index=0,$(function(){getQueryString("tab")&&t.tableSwitch(parseInt(getQueryString("tab")))}),t.tableSwitch=function(a){t.index=tabPageDetails(a),win.showLoading(),location.hash="##1",n(t.index)},window.onhashchange=function(){win.showLoading(),t.defaultPage=location.hash.substring(2)||1,n(t.index)},t.getCoachData=function(){$.AJAX({type:"get",url:config.basePath+"coach/view",data:{coachId:t.coachId},success:function(a){t.coachDetails=JSON.parse(a.result.coach),console.log(t.coachDetails),scoreHtml(t.coachDetails.starLevel?t.coachDetails.starLevel:0),t.getOrderDataList(),t.$apply()}})},t.getCoachData(),t.coachEditLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.edit-coach").fadeOut("fast")}),$("#birthday").daterangepicker({startDate:new Date,parentElement:$("#edit-coach"),singleDatePicker:!0},function(a,i,n){t.editData.birthday=e("date")(a.toISOString(),"yyyy-MM-dd")})},t.editData={},t.coachEdit=function(a){$(".edit-coach").fadeIn("fast"),$("#edit-coach").css("marginTop",parseInt(($(win).height()-$("#edit-coach").height()-100)/2)+"px"),t.editData=a},t.submitEditMsg=function(a){var e=d("coach/update");return t.editData.name&&regCombination("*").test(t.editData.name)?void $.AJAX({url:e.url,data:e.data,success:function(e){$(a.target).parents("div.edit-alert").fadeOut("fast"),Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getCoachData()}}):(Layer.alert({width:300,height:150,type:"msg",title:"请填写教练姓名"}),!1)},t.closeAlert=function(){t.getCoachData()},t.orderPageSize=10,t.orderId="",t.payState="",t.startTime="",t.endTime="",t.getOrderDataList=function(){var a=s(t.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var e=getListData(a);t.orderDatas=e.dataList,i.selects({datas:t.orderDatas,whichId:"orderId"}),t.$apply(),topLongText(),new Page({parent:$("#order-page"),nowPage:t.defaultPage,pageSize:t.orderPageSize,totalCount:e.total})}})},t.getOrderDataList(),t.getDataForPage=function(){win.showLoading(),t.getOrderDataList()},t.getDataForSearch=function(){win.showLoading(),t.getOrderDataList()},t.getDataForOederTime=function(a,i){switch($(a.target).addClass("active").siblings().removeClass("active"),i){case"all":t.startTime=t.endTime="";break;case"0":t.startTime=e("date")((new Date).getTime()-6048e5,"yyyy-MM-dd"),t.endTime=e("date")((new Date).getTime(),"yyyy-MM-dd");break;case"1":t.startTime=e("date")((new Date).getTime()-1296e6,"yyyy-MM-dd"),t.endTime=e("date")((new Date).getTime(),"yyyy-MM-dd")}win.showLoading(),t.getOrderDataList()},$("#reservationorder").daterangepicker({format:"YYYY/MM/DD"},function(a,i,n){t.startTime=t.endTime="",t.startTime=e("date")(a.toISOString(),"yyyy-MM-dd"),t.endTime=e("date")(i.toISOString(),"yyyy-MM-dd"),win.showLoading(),t.getOrderDataList(),t.$apply()}),t.getDataForPayment=function(a,e){$(a.target).addClass("active").siblings().removeClass("active"),win.showLoading(),t.payState=e,t.getOrderDataList()},t.closeOrder=function(){if(!a.idList.length)return Layer.alert({width:300,height:150,type:"msg",title:"请选择需要关闭的订单！"}),!1;var e=getDataForKey({datas:t.orderDatas,id:"orderId",idList:a.idList});Layer.confirm({width:300,height:160,title:"确认关闭选中的订单吗？",header:"关闭订单"},function(){$.AJAX({url:config.basePath+"order/update-batch",data:{orderIds:a.idList.toString(),studentIds:getKeyArrFromData(e,"studentId").toString(),coachIds:getKeyArrFromData(e,"coachId").toString()},success:function(e){a.idList=[],Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getOrderDataList()}})})},t.studentPageSize=10,t.name="",t.getCoachDataList=function(){var a=o(t.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var e=getListData(a);t.studentDatas=e.dataList,t.$apply(),new Page({parent:$("#student-page"),nowPage:t.defaultPage,pageSize:t.studentPageSize,totalCount:e.total})}})},t.getCoachDataForPage=function(){win.showLoading(),t.getCoachDataList()},t.getStudentDataForSearch=function(){win.showLoading(),t.getCoachDataList()},t.studentDelete=function(a){$.AJAX({url:config.basePath+"bankDeposit/pass",data:{studentId:a},success:function(a){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getCoachDataList()}})},t.addNewStudLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.add-new-student").fadeOut("fast")})},t.addNewStudent=function(){$(".add-new-student").fadeIn("fast"),$("#add-new-student").css("marginTop",parseInt(($(win).height()-$("#add-new-student").height()-200)/2)+"px"),t.newStudentDatas=[],a.idList1=[],win.showLoading(),t.getNewStudentDataList()},t.newStudent="",t.newStudentPageSize=5,t.getNewStudentDataList=function(){var a=c(t.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var e=getListData(a);t.newStudentDatas=e.dataList,i.selects({datas:t.newStudentDatas,whichId:"studentId",num:"1"}),t.$apply(),new Page({parent:$("#new-student-page"),nowPage:t.defaultPage,pageSize:t.orderPageSize,totalCount:e.total,type:2,callback:function(a,e){var i=c(a);$.AJAX({type:"get",url:i.url,data:i.data,success:function(a){var e=getListData(a);t.newStudentDatas=e.dataList,t.$apply()}})}})}})},t.getDataForJid=function(a,e){$(a.target).addClass("active").siblings().removeClass("active"),win.showLoading(),t.payState=e,t.getNewStudentDataList()},t.getNewStudentDataForSearch=function(a){win.showLoading(),t.newStudent=a,t.getNewStudentDataList()},t.submitStudentMsg=function(e){return a.idList1.length?void Layer.confirm({width:300,height:170,title:"您确认分配已选择的 <i class='mainColor'>"+a.idList1.length+"</i> 位学员<br>到该教练吗？",header:"分配新学员"},function(){$.AJAX({url:config.basePath+"coach/new-stu",data:{coachId:t.coachId,studentIds:a.idList1.toString()},success:function(a){$(e.target).parents("div.add-new-student").fadeOut("fast"),Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getCoachDataList()}})}):(Layer.alert({type:"msg",title:"请选择需要绑定的学员！"}),!1)},t.getNewCarData=function(){$.AJAX({type:"get",url:config.basePath+"coach/car",data:{coachId:t.coachId},success:function(a){t.coachCarDatas=JSON.parse(a.result.coachCar),t.$apply()}})},t.addNewCarLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.add-new-car").fadeOut("fast"),t.isBindCarShow=!1})},t.isBindCarShow=!1,t.editType="分配",t.addNewCar=function(a){$(".add-new-car").fadeIn("fast"),$("#add-new-car").css("marginTop",parseInt(($(win).height()-$("#add-new-car").height()-200)/2)+"px"),t.isBindCarShow=!1,a+""&&(t.o_carId=a,t.editType="更换")},t.searchCarDetails=function(a){return a&&regCombination("*").test(a)?(t.isBindCarShow=!0,win.showLoading(),void $.AJAX({type:"get",url:config.basePath+"car/coach",data:{carNo:a,schoolNo:t.coachDetails.schoolId},success:function(a){t.bindCarDetails=JSON.parse(a.result.car),t.$apply()}})):(Layer.alert({type:"msg",title:"请输入车牌号!"}),!1)},t.submitBindCar=function(a){for(var e in t.coachCarDatas)if(t.bindCarDetails.carId==t.coachCarDatas[e].carId)return Layer.alert({width:300,height:160,title:"你绑定的教练车已存在！"}),!1;$.AJAX({url:config.basePath+"coach/new-car",data:{carNo:t.bindCarDetails.carNo,o_carId:t.o_carId,c_carId:t.bindCarDetails.carId,coachId:t.coachId},success:function(e){$(a.target).parents("div.edit-alert").fadeOut("fast"),Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getNewCarData()}})},t.deleteCoachBindCar=function(a){Layer.confirm({width:300,height:160,title:"确实取消绑定此车辆吗？",header:"删除绑定"},function(){$.AJAX({url:config.basePath+"coach/del-rel",data:{coachId:t.coachId,carId:a},success:function(a){Layer.miss({width:250,height:90,title:"操作成功",closeMask:!0}),t.getNewCarData()}})})},t.pageSize=10,t.indAccount=function(){var a=r(t.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){t.stuAccount=JSON.parse(a.result.CoachInfo),t.stuBankList=JSON.parse(a.result.bankList),t.stuMoneyList=JSON.parse(a.result.moneyList),t.$apply(),new Page({parent:$("#total-page"),nowPage:t.defaultPage,pageSize:t.pageSize,totalCount:t.stuMoneyList.total})}})},t.getDataForTime=function(a,i){switch($(a.target).addClass("active").siblings().removeClass("active"),i){case"1":t.startTime=e("date")((new Date).getTime()-6048e5,"yyyy-MM-dd"),t.endTime=e("date")((new Date).getTime(),"yyyy-MM-dd");break;case"2":t.startTime=e("date")((new Date).getTime()-1296e6,"yyyy-MM-dd"),t.endTime=e("date")((new Date).getTime(),"yyyy-MM-dd");break;case"3":t.startTime=e("date")((new Date).getTime()-31536e6,"yyyy-MM-dd"),t.endTime=e("date")((new Date).getTime(),"yyyy-MM-dd")}win.showLoading(),t.indAccount()},$("#coachAccount").daterangepicker({format:"YYYY/MM/DD"},function(a,i,n){t.startTime=t.endTime="",t.startTime=e("date")(a.toISOString(),"yyyy-MM-dd"),t.endTime=e("date")(i.toISOString(),"yyyy-MM-dd"),win.showLoading(),t.indAccount(),t.$apply()}),t.getDataForOperate=function(){win.showLoading(),t.defaultPage=1,t.operateType=t.operateType,t.indAccount()},t.logPageSize=10,t.getCoachLogs=function(){var a=g(t.defaultPage);$.AJAX({type:"get",url:a.url,data:a.data,success:function(a){var e=getListData(a);t.coachLogs=e.dataList,t.$apply(),new Page({parent:$("#log-page"),nowPage:t.defaultPage,pageSize:t.logPageSize,totalCount:e.total})}})}}]);