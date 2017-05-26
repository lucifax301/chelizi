var app=angular.module("app",["ngFilter"]);app.controller("DataStatistics",["$scope","$filter",function(t,a){t.getDataStatistics=function(){$.AJAX({type:"get",url:config.basePath+"statistics-total/list",success:function(a){var s=a.result;t.dataList=JSON.parse(a.result.dateList),console.log(s),t.totalData=[{icon:"../assets/img/data/data-student.png",title:"学员总人数",dec:"平台全部注册学员的人数",totalNum:s.studentSum},{icon:"../assets/img/data/data-coach.png",title:"教练总人数",dec:"平台全部注册教练的人数",totalNum:s.coachSum},{icon:"../assets/img/data/data-order.png",title:"订单总数",dec:"平台全部订单的数据",totalNum:s.orderSum},{icon:"../assets/img/data/data-car.png",title:"教练车总数",dec:"平台全部教练车的数量",totalNum:s.carSum},{icon:"../assets/img/data/data-site.png",title:"训练场总数",dec:"平台全部训练场的数量",totalNum:s.fieldSum}],t.tabDataList=[getDataFromJson(["流水统计","现约进账(元)","报名进账(元)","合计进账(元)"],["dateStr","instantOrderMoney","signupOrderMoney","totalMoney"],t.dataList),getDataFromJson(["订单统计","预约单量(单)","现约单量(单)","现约成功付费单量"],["dateStr","planOrderNum","instantOrderNum","instantPayOrderNum"],t.dataList),getDataFromJson(["学员统计","登录量(人)","注册量(人)","报名人数(人)","活跃量(人)"],["dateStr","loginStuNum","registerStuNum","signupStuNum","activityStuNum"],t.dataList),getDataFromJson(["教练统计","登录量(人)","排班人数(人)","上课人数(人)"],["dateStr","loginCoachNum","planClassCoachNum","hadPlanClassCoachNum"],t.dataList),getDataFromJson(["教练课时","排班课时(小时)","上课课时(小时)","满课率"],["dateStr","planClassTime","rPlanClassTime","fullClass"],t.dataList),getDataFromJson(["评价统计","<3星单量(单)","总评价单量(单)","<3星单占比"],["dateStr","lessThreeNum","commentOrderNum","lessThreeRatio"],t.dataList)],console.log(t.tabDataList),t.$apply()}})},t.getDataStatistics()}]);