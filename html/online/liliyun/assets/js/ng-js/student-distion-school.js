var app=angular.module("app",["ngFilter","ngSelects"]);app.controller("Student",["$scope","$rootScope","$filter","Selects",function(t,e,a,i){function o(e){var a={url:config.basePath+"student/school-batch",data:{pageNo:e,pageSize:parseInt(t.pageSize),startTime:t.startTime,endTime:t.endTime,cityId:t.cityNo}};return a.data[t.searchType]=t.search,a}t.defaultPage=location.hash.substring(2)||1,t.pageSize=10,t.startTime="",t.endTime="",t.search="",t.searchType="name",t.cityNo="",t.data={pages:10,total:100,pageSize:10,pageNo:1,dataList:[{studentId:0,name:"小张",sex:0,phoneNum:"13476002586",applyCarType:1,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},{studentId:2,name:"小张",sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0}]},t.datas=t.data.dataList,i.selects({datas:t.datas,whichId:"studentId"}),t.getDataList=function(){var e=o(t.defaultPage);$.AJAX({type:"get",url:e.url,data:e.data,success:function(e){var a=getListData(e);t.total=a.total,t.datas=a.dataList,i.selects({datas:t.datas,whichId:"studentId"}),t.$apply(),new Page({parent:$("#copot-page"),nowPage:t.defaultPage,pageSize:t.pageSize,totalCount:a.total})}})},t.getDataList(),window.onhashchange=function(){win.showLoading(),t.defaultPage=location.hash.substring(2)||1,t.getDataList()},t.getDataForTime=function(e,i){switch($(e.target).addClass("active").siblings().removeClass("active"),i){case"all":t.startTime=t.endTime="";break;case"0":t.startTime=a("date")((new Date).getTime()-6048e5,"yyyy-MM-dd"),t.endTime=a("date")((new Date).getTime(),"yyyy-MM-dd");break;case"1":t.startTime=a("date")((new Date).getTime()-1296e6,"yyyy-MM-dd"),t.endTime=a("date")((new Date).getTime(),"yyyy-MM-dd")}win.showLoading(),t.getDataList()},$("#reservation").daterangepicker({format:"YYYY/MM/DD"},function(e,i,o){t.startTime=t.endTime="",t.startTime=a("date")(e.toISOString(),"yyyy-MM-dd"),t.endTime=a("date")(i.toISOString(),"yyyy-MM-dd"),win.showLoading(),t.getDataList(),t.$apply()}),queryCity({callback:function(e){console.log(e),t.citys=e,t.$apply()}}),t.getDataForCity=function(){win.showLoading(),t.getDataList()},t.getDataForPage=function(){win.showLoading(),t.getDataList()},t.getDataForSearch=function(){win.showLoading(),t.getDataList()},t.cityError=!1,t.schoolError=!1,t.distionSchoolLoad=function(){$(".closeAlert").click(function(){$(this).parents("div.distion-school").fadeOut("fast")})},t.distionSchool=function(){if(!e.idList.length)return Layer.alert({width:300,height:150,type:"msg",title:"请选择需要分配的学员！"}),!1;var a=getDataForKey({datas:t.datas,id:"studentId",idList:e.idList});t.applyttid=a[0].applyttid;for(var i=[],o=0;o<a.length;o++){i.push(a[o].region);var s=i.indexOf(i[o]);console.log(s)}return 0!=s?(Layer.alert({width:330,height:175,type:"msg",title:"您只能选择同一城市的学员，<br>请重新选择学员！"}),!1):($(".distion-school").fadeIn("fast"),void $("#distion-school").css("marginTop",parseInt(($(win).height()-$("#distion-school").height()-100)/2)+"px"))},t.haveCityId=function(e){t.cityError=!e},t.getSchools=function(e){e?(t.cityError=!e,querySchool({cityId:e,callback:function(e){t.schools=e,t.$apply()}})):($schoolId="",t.schools=null)},t.submitDistionSchool=function(a,i,o){var s=o.split(",")[0];return console.log(s),regCombination("*").test(i)&&i?(t.cityError=!1,regCombination("*").test(s)&&s?(t.schoolError=!1,void $.AJAX({url:config.basePath+"student/allot",data:{studentIdList:e.idList.toString(),region:i,schoolId:s,schoolName:o.split(",")[1]},code:!0,success:function(e){$(a.target).parents("div.distion-school").fadeOut("fast"),Layer.miss({width:250,height:90,title:"分配驾校成功",closeMask:!0}),t.getDataList()},error:function(t){console.log(t.code),601==t.code&&Layer.alert({width:300,height:165,type:"msg",title:"学员报考城市与分配驾校不<br>是同一个城市!",header:"信息"})}})):(t.schoolError=!0,!1)):(t.cityError=!0,!1)}}]);