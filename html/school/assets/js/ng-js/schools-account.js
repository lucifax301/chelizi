/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("accountSchool",["$scope",function($s) {
	//当前显示状态  1：驾校账户页面   2：变更商户信息提交的提示  3：变更商户信息提交"通过"审核的提示  4：变更商户信息提交"未通过"审核的提示
    $s.type=getQueryString('type') || 0
    $s.schoolId=getQueryString('schoolId')

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
    for(var i=2015;i<=thisYear;i++){monthpickerYears.push(i)}
    $('#monthpicker').monthpicker({
        years: monthpickerYears.reverse(),
        onMonthSelect: function(m, y) {
            var M = ("0" + (m+1)).slice(-2);
            var thisMonthDats = 32-(new Date(y,m,32)).getDate();//获取所选月的标准天数
            $s.startTime=y+"-"+M+"-01 00:00:00";//所选月份的1号零点开始
            $s.endTime=y+"-"+M+"-"+thisMonthDats+" 23:59:59";//所选月份的最后一天23：59：59结束
            $s.getAccountData();
        }
    });

    /*------------账户信息-----------*/
    $s.getAccountData = function(){
        $.AJAX({
        type:"get",
        url:config.basePath+"school/queryAccount",
        data:{
            schoolId:$s.schoolId,
            //startTime:$s.startTime,
            //endTime:$s.endTime,
            //本来应传以上时分秒，但后端已经作处理，只需要拿到年和月就OK
            ymDate:$s.startTime
        },
        success:function(data){
            $s.schoolDetails=getListData(data);   //获取返回数据
            $s.schoolMoneFree=JSON.parse(data.result.moneFree)

            $s.isChange=$s.schoolDetails.isChange;  //是否有变更: 1无，2已提交申请，3已审核申请资料，4已驳回申请，5关闭
            $s.checkStatus=$s.schoolDetails.checkStatus;  //审核状态: 1初始化，2审核通过，3审核不通过

            /*------------判断审核状态-----------*/
            if($s.checkStatus==1){
                window.location.href="schools-account-application.html?type=audit";
            }else if($s.checkStatus==3){
                window.location.href="schools-account-application.html?type=noPass";
            }

            /*------------"变更商户信息"申请提交后链接不可用-----------*/
            if($s.isChange==2){
                $(".ischangetype").attr("href","javascript:void(0);").addClass("btn-default");
            }

            /*------------判断是否申请开通-----------*/
            if($s.schoolDetails==""){
                window.location.href="schools-account-application.html";
            }

            /*--------------申请提现按钮禁用--------------*/
            $s.withdrawals=function(){
                if($s.isChange!=2){
                    window.location.href="schools-account-withdrawals.html?type=0";
                }
            }

            /*--------------收入流水统计--------------*/
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('mainA'));
            // 指定图表的配置项和数据
            optionA = {
                series: [
                    {
                        type:'pie',
                        radius: ['40%', '80%'],
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                        },
                        data:[
                            {value:$s.schoolMoneFree.enrollFree/100, name:'课时费收益'},
                            {value:$s.schoolMoneFree.courseFree/100, name:'报名费收益'}
                        ]
                    }
                ]
            };
            myChart.setOption(optionA);

            /*--------------费用流水统计--------------*/
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('mainB'));
            // 指定图表的配置项和数据
            optionB = {
                series: [
                    {
                        type:'pie',
                        radius: ['40%', '80%'],
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                        },
                        data:[
                            //comFree佣金  refundFree退款  fineFree罚款  expenseFree费用
                            {value:$s.schoolMoneFree.refundFree/100, name:'退款'},
                            {value:$s.schoolMoneFree.comFree/100, name:'佣金'},
                            {value:$s.schoolMoneFree.fineFree/100, name:'罚款'}
                        ]
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(optionB);
            $s.$apply();
        }
    });
    }
    $s.getAccountData();
    //关闭提示
    $s.passClose=function(){
        $.AJAX({
            url:config.basePath+"school/closeRemark",
            data:{
                schoolId:$s.schoolId,
            },
            success:function(data){
                console.log($s.isChange)
                $s.isChange=5
                $s.$apply();
            }
        })
    }



}]);