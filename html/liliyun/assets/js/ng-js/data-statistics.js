/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("DataStatistics",["$scope","$filter",function($s,$filter){

/**********************************************获取统计数据******************************************/
var indexTmp = 3;//三秒请求一次，最多12秒请求四次
$s.getDataStatisticsTmp = function(){
    indexTmp--;
    if(indexTmp>=0){
        $.AJAX({
            type:"get",
            url:config.basePath+"statistics-total/list",
            success:function(data){
                var timeTmp = setTimeout(function(){$s.getDataStatisticsTmp()},3000)
                if(data.msg=="正在处理数据"){
                    console.log("处理中")
                    console.log(timeTmp)
                    window.showLoading();
                }else{
                    console.log("处理完")
                    clearTimeout(timeTmp);
                    console.log(timeTmp)
                    $s.getDataStatistics();
                }
            }
        });
    }else{
        window.hideLoading();
        Layer.miss({width:400,height:90,title:"服务器努力计算中...稍后刷新即可",closeMask:true});
    }

}
$s.getDataStatistics=function(){
	$.AJAX({
		type:"get",
		url:config.basePath+"statistics-total/list",
		success:function(data){
			var result=data.result;
			$s.dataList=JSON.parse(data.result.dateList);
			//console.log(result)
			//总计数据
			$s.totalData = [
				{icon:"../assets/img/data/data-student.png",title:'学员总人数',dec:'平台全部注册学员的人数',totalNum:result.studentSum},
				{icon:"../assets/img/data/data-coach.png",title:'教练总人数',dec:'平台全部注册教练的人数',totalNum:result.coachSum},
				{icon:"../assets/img/data/data-order.png",title:'订单总数',dec:'平台全部订单的数据',totalNum:result.orderSum},
				{icon:"../assets/img/data/data-car.png",title:'教练车总数',dec:'平台全部教练车的数量',totalNum:result.carSum},
				{icon:"../assets/img/data/data-site.png",title:'训练场总数',dec:'平台全部训练场的数量',totalNum:result.fieldSum},
			];

			//统计table数据列表
			$s.tabDataList=[
				getDataFromJson(['流水统计','现约进账(元)','预约进账(元)','报名进账(元)','合计进账(元)'],['dateStr','instantOrderMoney','planOrderMoney','signupOrderMoney','totalMoney'],$s.dataList),
				getDataFromJson(['订单统计','预约单量(单)','现约单量(单)','预约成功付费单量','现约成功付费单量','付费预约课时','现约课时'],['dateStr','planOrderNum','instantOrderNum','planPayOrderNum','instantPayOrderNum','planPayClassTime','instantPayClassTime'],$s.dataList),
				getDataFromJson(['学员统计','登录量(人)','注册量(人)','报名人数(人)','活跃量(人)'],['dateStr','loginStuNum','registerStuNum','signupStuNum','activityStuNum'],$s.dataList),
				getDataFromJson(['教练统计','登录量(人)','排班人数(人)','上课人数(人)'],['dateStr','loginCoachNum','planClassCoachNum','hadPlanClassCoachNum'],$s.dataList),
				getDataFromJson(['教练课时','排班课时(小时)','上课课时(小时)','满课率'],['dateStr','planClassTime','rPlanClassTime','fullClass'],$s.dataList),
				getDataFromJson(['评价统计','<3星单量(单)','总评价单量(单)','<3星单占比'],['dateStr','lessThreeNum','commentOrderNum','lessThreeRatio'],$s.dataList),
			];
			//console.log($s.tabDataList)
			$s.$apply();
		}
	});
};	
$s.getDataStatisticsTmp();



	
}]);