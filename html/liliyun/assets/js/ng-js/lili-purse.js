/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("purse",["$scope",function($s) {
    /*******************月度统计*****************/
    //列举从2016年1月至今的所有月份
    var monthpickerYears = [];
    var thisDate = new Date();
    var thisYear = thisDate.getFullYear();
    var thisMonth = ("0" + (thisDate.getMonth() + 1)).slice(-2);//当前月份处理，两位数，从1开始
    var thisDay = ("0" + (thisDate.getDate() + 1)).slice(-2);//当前日期处理，两位数，从1开始
    $s.startTime=thisYear+"-"+thisMonth+"-01 00:00:00";
    $s.endTime=thisYear+"-"+thisMonth+"-"+thisDay+" 23:59:59";
    $s.thismonth = thisMonth;
    $s.month = thisYear+thisMonth;
    //console.log($s.month);
    for(var i=2015;i<=thisYear;i++){monthpickerYears.push(i)}
    $('#monthpicker').monthpicker({
        years: monthpickerYears.reverse(),
        onMonthSelect: function(m, y) {
            var M = ("0" + (m+1)).slice(-2);
            var Y = y;
            $s.month = Y+M;
            //var thisMonthDats = 32-(new Date(y,m,32)).getDate();//获取所选月的标准天数
            //$s.startTime=y+"-"+M+"-01 00:00:00";//所选月份的1号零点开始
            //$s.endTime=y+"-"+M+"-"+thisMonthDats+" 23:59:59";//所选月份的最后一天23：59：59结束
            $s.getAccountData();
        }
    });



    /*------------月度盈亏统计-----------*/
    $s.getAccountData =function(){
        $.AJAX({
            type:"get",
            url:config.basePath+"liliWallet/fundCount",
            data:{
                month:$s.month,
            },
            success:function(data){
                $s.income=JSON.parse(data.result.income);   //获取喱喱收入
                $s.expense=JSON.parse(data.result.expense);   //获取喱喱费用
                $s.profitLoss=JSON.parse(data.result.profitLoss);   //获取月盈亏
                $s.$apply();
            }
        });
    }
    $s.getAccountData();

    /*------------账户余额-----------*/
    $.AJAX({
        type:"get",
        url:config.basePath+"liliWallet/balance",
        success:function(data){
            console.log(data)
            $s.liliMoney=JSON.parse(data.result.liliMoney);  //获取喱喱余额
            $s.liliPer=data.result.liliPer;  //获取喱喱余额所占比例

            $s.studentMoney=JSON.parse(data.result.studentMoney);  //获取学员余额
            $s.studentPer=data.result.studentPer;  //获取学员余额所占比例

            $s.schoolMoney=JSON.parse(data.result.schoolMoney);  //获取驾校余额
            $s.schoolPer=data.result.schoolPer;  //获取学员余额所占比例

            $s.coachMoney=JSON.parse(data.result.coachMoney);  //获取教练余额
            $s.coachPer=data.result.coachPer;  //获取教练余额所占比例

            $s.totalMoney=JSON.parse(data.result.totalMoney);  //获取平台总额

            /*--------------余额账户统计--------------*/
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('mainA'));
            // 指定图表的配置项和数据
            option = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                series: [
                    {
                        name:'所占比例',
                        type:'pie',
                        radius: ['70%', '90%'],
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                        },
                        data:[
                            {value:$s.liliMoney/100, name:'喱喱余额'},
                            {value:$s.studentMoney/100, name:'学员总余额'},
                            {value:$s.schoolMoney/100, name:'驾校总余额'},
                            {value:$s.coachMoney/100, name:'教练总余额'}
                        ]
                    }
                ]
            };
            myChart.setOption(option);
            $s.$apply();
        }
    });
}]);