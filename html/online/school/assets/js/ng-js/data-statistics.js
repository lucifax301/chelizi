var app=angular.module("app",[]);app.controller("DataStatistics",["$scope",function(t){t.getDataStatistics=function(){$.AJAX({type:"get",url:config.basePath+"statistics-total/listJx",success:function(a){var s=a.result;t.totalData=[{icon:"../assets/img/data/data-student.png",title:"学员总人数",dec:"平台全部注册学员的人数",totalNum:s.studentSum},{icon:"../assets/img/data/data-coach.png",title:"教练总人数",dec:"平台全部注册教练的人数",totalNum:s.coachSum},{icon:"../assets/img/data/data-order.png",title:"订单总数",dec:"平台全部订单的数据",totalNum:s.orderSum},{icon:"../assets/img/data/data-car.png",title:"教练车总数",dec:"平台全部教练车的数量",totalNum:s.carSum},{icon:"../assets/img/data/data-site.png",title:"训练场总数",dec:"平台全部训练场的数量",totalNum:s.fieldSum}],t.$apply()}})},t.getDataStatistics()}]);