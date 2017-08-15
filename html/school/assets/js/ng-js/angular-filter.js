/*自定义 $filter 公共过虐器*/
(function (window, angular, undefined) {

    var app = angular.module('ngFilter', []);

    /*-------------------------------公共过滤器---------------------------------------*/

    /*字符串太长时剪切 并加上...号*/
    app.filter('limitToSec', [function () {
        return function (type, num) {
            var typeTex = type;
            if (type.length > num) {
                typeTex = type.substring(0, num) + '···';
            }
            return typeTex;
        };
    }]);

    /*验证button 权限滤器*/
    app.filter('isButtonShow', ["$filter",function ($filter) {
        return function (id) {
            var butShow = false;
            var butList = localStorage.getItem("school-btnList");
            if (butList) {
                var butListData = butList.split(',')
                for (var i = 0, len = butListData.length; i < len; i++) {
                    if (id == butListData[i]) {
                        butShow = true;
                    }
                    ;
                }
            }
            return butShow;
        };
    }]);

    /*金钱过滤器| 保留两位小数点*/
    app.filter('priceTex', ["$filter",function ($filter) {
        return function (price) {
            return $filter('currency')(price / 100, '', 2);
        }
    }])

    /*时间过滤器*/
    app.filter('timeTex', ["$filter",function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "yyyy-MM-dd HH:mm:ss");
            }
        };
    }]);

    /*时间过滤器*/
    app.filter('timeTexYMD', ["$filter",function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "yyyy-MM-dd");
            }
        };
    }]);

    /*时间过滤器*/
    app.filter('timeTexMD', ["$filter",function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "MM-dd");
            }
        };
    }]);

    /*时间过滤器*/
    app.filter('timeTexHM', ["$filter",function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "HH:mm");
            }
        };
    }]);

    /*时间过滤器*/
    app.filter('timeTexHI', ["$filter", function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "HH:mm");
            }
        };
    }]);

    /*时间过滤器*/
    app.filter('timeTexYMDHI', ["$filter",function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "yyyy-MM-dd HH:mm");
            }
        };
    }]);

    /*分钟转换为小时*/
    app.filter('timeHourMinute', ["$filter",function () {
        return function (minutes) {
            if (minutes) {
                return (Math.floor(minutes/60) + "小时" + (minutes%60) + "分" );
            }
        };
    }]);

    /*性别过滤器 (0女1男)*/
    app.filter('sexText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "女"
                    break;
                case 1:
                    typeTex = "男"
                    break;
            }
            return typeTex;
        };
    }]);

    /*多为数 变*号*/
    app.filter('secretNumber', [function () {
        return function (type) {
            var strxh="";
            if(type){
                for(var i=0;i<type.length;i++){
                    strxh+='*';
                }   
                return type.replace(type.substr(0,type.length-4),strxh);
            }
        };
    }]);

    /*-------------------------------Order 订单---------------------------------------*/

    /*付款类型过滤器 0未付款 1已付款 2付款失败 9老学员自动付款*/
    app.filter('payStateTex', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未付款"
                    break;
                case 1:
                    typeTex = "已付款"
                    break;
                case 2:
                    typeTex = "付款失败"
                    break;
                case 9:
                    typeTex = "老学员自动付款"
                    break;
            }
            return typeTex;
        };
    }]);

    /*订单状态类型过滤器*/
    app.filter('orderStateTex', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "已取消"
                    break;
                case 1:
                    typeTex = "已下单"
                    break;
                case 2:
                    typeTex = "已接单"
                    break;
                case 3:
                    typeTex = "上课中"
                    break;
                case 4:
                    typeTex = "已下课"
                    break;
                case 5:
                    typeTex = "教练已评"
                    break;
                case 6:
                    typeTex = "学员已评"
                    break;
                case 7:
                    typeTex = "双方已评"
                    break;
                case 9:
                    typeTex = "已拒单"
                    break;
                case 10:
                    typeTex = "缺课"
                    break;
            }
            return typeTex;
        };
    }]);

    /*报名订单状态*/
    app.filter('isdelText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未结款"
                    break;
                case 1:
                    typeTex = "已结款"
                    break;
            }
            return typeTex;
        };
    }]);


    /*-------------------------------student 学员---------------------------------------*/
    /* 认证状态(0---未审核 1---审核中 2---审核未通过 3---已认证 4---已过期 5---已吊销) */
    app.filter('studentState', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未审核"
                    break;
                case 1:
                    typeTex = "审核中"
                    break;
                case 2:
                    typeTex = "审核未通过"
                    break;
                case 3:
                    typeTex = "已认证"
                    break;
                case 4:
                    typeTex = "已过期"
                    break;
                case 5:
                    typeTex = "已吊销"
                    break;
            }
            return typeTex;
        }
    }])



    /*账号状态过滤器*/
    app.filter('accountStatusTex', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "正常"
                    break;
                case 1:
                    typeTex = "已封号"
                    break;
            }
            return typeTex;
        };
    }]);

    /*车型过滤器 (1代表C1,2代表C2,3代表其它)  */
    app.filter('applyCarTypeTex', [function () {
        return function (type) {
            var typeTex = "";
            switch (parseInt(type)) {
                case 1:
                    typeTex = "C1"
                    break;
                case 2:
                    typeTex = "C2"
                    break;
                case 3:
                    typeTex = "其他"
                    break;
            }
            return typeTex;
        };
    }]);

    /*身份证保留后4位过滤器*/
    app.filter('idNumberLast4', [function () {
        return function (idNumber) {
            return idNumber.slice(-4, 25);
        }
    }])

    /*报名状态过滤器*/
    app.filter('applystateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未开始"
                    break;
                case 1:
                    typeTex = "已提交"
                    break;
                case 100:
                    typeTex = "已成功"
                    break;
                case 101:
                    typeTex = "已失败"
                    break;
            }
            return typeTex;
        };
    }])

    /*学员状态过滤器*/
    app.filter('studentStateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case '-1,0':
                    typeTex = "暂不报名"
                    break;
                case '1,0':
                    typeTex = "尚未报名"
                    break;
                case '1,100':
                    typeTex = "已报名"
                    break;
                case '2,0':
                    typeTex = "尚未支付"
                    break;
                case '2,100':
                    typeTex = "已支付"
                    break;
                case '2,101':
                    typeTex = "支付失败"
                    break;
                case '3,0':
                    typeTex = "尚未填写个人信息"
                    break;
                case '3,100':
                    typeTex = "已填写个人信息"
                    break;
                case '4,0':
                    typeTex = "未邮寄资料"
                    break;
                case '4,1':
                    typeTex = "资料已邮寄"
                    break;
                case '4,100':
                    typeTex = "资料齐全"
                    break;
                case '4,101':
                    typeTex = "资料不全"
                    break;
                case '5,0':
                    typeTex = "未交表"
                    break;
                case '5,1':
                    typeTex = "表已寄出"
                    break;
                case '5,100':
                    typeTex = "收表成功"
                    break;
                case '6,0':
                    typeTex = "已收表"
                    break;
                case '6,1':
                    typeTex = "受理中"
                    break;
                case '6,100':
                    typeTex = "受理成功"
                    break;
                case '6,101':
                    typeTex = "受理失败"
                    break;
                case '101,0':
                    typeTex = "报名成功"
                    break;
                case '101,1':
                    typeTex = "已约理论课"
                    break;
                case '101,100':
                    typeTex = "已上理论课"
                    break;
                case '101,101':
                    typeTex = "缺理论课"
                    break;
                case '201,0':
                    typeTex = "未模拟考试"
                    break;
                case '201,100':
                    typeTex = "模拟考试达标"
                    break;
                case '201,101':
                    typeTex = "模拟考试未达标"
                    break;
                case '301,0':
                    typeTex = "未约考科一"
                    break;
                case '301,1':
                    typeTex = "科一排队中"
                    break;
                case '301,100':
                    typeTex = "科一排队成功"
                    break;
                case '301,101':
                    typeTex = "科一排队失败"
                    break;
                case '302,0':
                    typeTex = "已约考科一"
                    break;
                case '302,100':
                    typeTex = "科一合格"
                    break;
                case '302,101':
                    typeTex = "科一不合格"
                    break;
                case '401,0':
                    typeTex = "未约科考二"
                    break;
                case '401,1':
                    typeTex = "科二排队中"
                    break;
                case '401,100':
                    typeTex = "科二排队成功"
                    break;
                case '401,101':
                    typeTex = "科二排队失败"
                    break;
                case '402,0':
                    typeTex = "已约考科二"
                    break;
                case '402,100':
                    typeTex = "科二合格"
                    break;
                case '402,101':
                    typeTex = "科二不合格"
                    break;
                case '501,0':
                    typeTex = "未约长考"
                    break;
                case '501,1':
                    typeTex = "已约长考"
                    break;
                case '501,100':
                    typeTex = "长训合格"
                    break;
                case '501,101':
                    typeTex = "长训不合格"
                    break;
                case '601,0':
                    typeTex = "未约考科三"
                    break;
                case '601,1':
                    typeTex = "科三排队中"
                    break;
                case '601,100':
                    typeTex = "科三排队成功"
                    break;
                case '601,101':
                    typeTex = "科三排队失败"
                    break;
                case '602,0':
                    typeTex = "已约考科三"
                    break;
                case '602,100':
                    typeTex = "科三合格"
                    break;
                case '602,101':
                    typeTex = "科三不合格"
                    break;
                case '701,0':
                    typeTex = "未约考科四"
                    break;
                case '701,1':
                    typeTex = "科四排队中"
                    break;
                case '701,100':
                    typeTex = "科四排队成功"
                    break;
                case '701,101':
                    typeTex = "科四排队失败"
                    break;
                case '702,0':
                    typeTex = "已约考科四"
                    break;
                case '702,100':
                    typeTex = "科四合格"
                    break;
                case '702,101':
                    typeTex = "科四不合格"
                    break;
                case '801,0':
                    typeTex = "已拿证"
                    break;
                default:
                    typeTex = "未报名"
                    break;
            }
            return typeTex;
        };
    }])

    /*学员具体修改信息*/
    app.filter('studentUpdateText', ["$filter",function ($filter) {
        return function (type) {
            var text="";
            var utjson=JSON.parse(type);
            //console.log(utjson)
            if(utjson){
                if(utjson.name)
                    text=text+"将'姓名'由'"+utjson.name.from+"'修改为'"+utjson.name.to+"'; ";
                if(utjson.idNumber)
                    text=text+"将'身份证'由'"+utjson.idNumber.from+"'修改为'"+utjson.idNumber.to+"';  ";
                if(utjson.phoneNum)
                    text=text+"将'电话'由'"+utjson.phoneNum.from+"'修改为'"+utjson.phoneNum.to+"';  ";
                if(utjson.applyCarType){
                    var dfrom="";
                    var dto="";
                    if(utjson.applyCarType.from==1)dfrom="C1";
                    if(utjson.applyCarType.from==2)dfrom="C2";
                    if(utjson.applyCarType.to==1)dto="C1";
                    if(utjson.applyCarType.to==2)dto="C2";
                    text=text+"将'驾考类别'由'"+dfrom+"'修改为'"+dto+"';  ";
                }
                if(utjson.applyexam){
                    var dfrom = $filter('studentStateText')(utjson.applyexam.from);
                    var dto=$filter('studentStateText')(utjson.applyexam.to);

                    text=text+"将'学员状态'由'"+dfrom+"'修改为'"+dto+"';  ";
                }
                if(utjson.sex)
                    text=text+"将'性别'由'"+ $filter('sexText')(utjson.sex.from)+"'修改为'"+$filter('sexText')(utjson.sex.to)+"'; ";
            }

            return text;
        }
    }])

    /*-------------------------------car 汽车---------------------------------------*/

    /*汽车等级过滤器 (1代表普通,2代表舒适,3代表豪华)  */
    app.filter('carLevelText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 1:
                    typeTex = "普通"
                    break;
                case 2:
                    typeTex = "舒适"
                    break;
                case 3:
                    typeTex = "豪华"
                    break;
            }
            return typeTex;
        };
    }]);

    /*汽车认证状态过滤器*/
    app.filter('checkIdStateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未认证"
                    break;
                case 1:
                    typeTex = "认证中"
                    break;
                case 2:
                    typeTex = "已认证"
                    break;
                case 3:
                    typeTex = "认证失败"
                    break;
            }
            return typeTex;
        }
    }])


    /*-------------------------------提现---------------------------------------*/
    /*提现类型过滤器*/
    app.filter('typeTex', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "bank":
                    typeTex = "银行卡"
                    break;
                case "wexin":
                    typeTex = "微信"
                    break;
                case "zhifubao":
                    typeTex = "支付宝"
                    break;
                case "qqwallet":
                    typeTex = "QQ钱包"
                    break;
            }
            return typeTex;
        };
    }]);

    /*财务反馈类型过滤器*/
    app.filter('checkStatusTex', [function () {
        return function (status) {
            var typeTex = "";
            switch (status) {
                case 0:
                    typeTex = "审核中";
                    break;
                case 1:
                    typeTex = "提现成功";
                    break;
                case 2:
                    typeTex = "提现失败";
                    break;
                case 3:
                    typeTex = "财务确认";
                    break;
                case 4:
                    typeTex = "银行处理中";
                    break;
                case 5:
                    typeTex = "银行处理失败";
                    break;
            }
            return typeTex;
        };
    }]);

    /*-------------------------------银行卡---------------------------------------*/
    /*财务反馈类型过滤器*/
    app.filter('statusTex', [function () {
        return function (status) {
            var typeTex = "";
            switch (status) {
                case 0:
                    typeTex = "待反馈";
                    break;
                case 1:
                    typeTex = "有效卡号";
                    break;
                case 2:
                    typeTex = "无效卡号";
                    break;
            }
            return typeTex;
        };
    }]);

    /*-------------------------------报名订单---------------------------------------*/
    /*资料邮寄状态过滤器*/
    app.filter('postStateTex', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "资料未邮寄";
                    break;
                case 1:
                    typeTex = "资料已邮寄";
                    break;
                case 2:
                    typeTex = "资料已收到";
                    break;
            }
            return typeTex;
        };
    }]);

    /*报名订单支付状态过滤器*/
    app.filter('signUpPayStateTex', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未开始";
                    break;
                case 1:
                    typeTex = "已提交";
                    break;
                case 100:
                    typeTex = "成功";
                    break;
                case 101:
                    typeTex = "失败";
                    break;
            }
            return typeTex;
        };
    }]);

    /*-------------------------------奖金---------------------------------------*/
    /*奖金列表发放状态*/
    app.filter('bonusStatusText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 1:
                    typeTex = "未确认";
                    break;
                case 2:
                    typeTex = "待发奖";
                    break;
                case 3:
                    typeTex = "发放成功";
                    break;
                case 4:
                    typeTex = "发放失败";
                    break;
                case 5:
                    typeTex = "财务拒绝";
                    break;
            }
            return typeTex;
        };
    }]);

    /*奖金明细 奖金发放状态*/
    app.filter('bonusStatusDetailsText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "待发放";
                    break;
                case 1:
                    typeTex = "发放成功";
                    break;
                case 2:
                    typeTex = "发放失败";
                    break;
            }
            return typeTex;
        };
    }]);

    /*携带的证件*/
    app.filter('papersText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "身份证";
                    break;
                case 1:
                    typeTex = "居住证";
                    break;
            }
            return typeTex;
        }
    }])

    /*理论课程状态*/
    app.filter('lessonStateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未确认";
                    break;
                case 1:
                    typeTex = "待上课";
                    break;
                case 2:
                    typeTex = "已上课";
                    break;
                case 3:
                    typeTex = "已取消";
                    break;
            }
            return typeTex;
        }
    }])

    /*长考课程状态*/
    app.filter('longtrainStateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未确认";
                    break;
                case 1:
                    typeTex = "待长考";
                    break;
                case 2:
                    typeTex = "已长考";
                    break;
                case 3:
                    typeTex = "已取消";
                    break;
            }
            return typeTex;
        }
    }])

   /*学员类型*/
    app.filter('studentTypeText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "喱喱学员";
                    break;
                case 1:
                    typeTex = "驾校学员";
                    break;
            }
            return typeTex;
        }
    }])

    /*出勤状态*/
    app.filter('attendStateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未记考勤";
                    break;
                case 1:
                    typeTex = "出勤";
                    break;
                case 2:
                    typeTex = "缺勤";
                    break;
                   
            }
            return typeTex;
        }
    }])


    /*长考成绩*/
    app.filter('ltResultStateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未记成绩";
                    break;
                case 1:
                    typeTex = "合格";
                    break;
                case 2:
                    typeTex = "不合格";
                    break;
                   
            }
            return typeTex;
        }
    }])


    /*-------------------------------财务收支统计---------------------------------------*/
    app.filter('accBalaStateText', [function () {
        return function (type) {
            var typeTex = 1;        //表示平
            if (type.match(/\+/)) {
                typeTex = 2;     //表示+
            } else if (type.match(/\-/)) {
                typeTex = 3;     //表示-
            }
            return typeTex;
        };
    }]);


    /*驾校提现状态过滤器 状态,0-审核中，1-提现成功，2-提现失败，3-财务确认,4银行处理中，5银行处理失败*/
    app.filter('recoreStatusText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "审核中";
                    break;
                case 1:
                    typeTex = "提现成功";
                    break;
                case 2:
                    typeTex = "提现失败";
                    break;
                case 3:
                    typeTex = "财务确认";
                    break;
                case 4:
                    typeTex = "银行处理中";
                    break;
                case 5:
                    typeTex = "银行处理失败";
                    break;            
            }
            return typeTex;
        }
    }])

    /*----------------------------------驾校钱包 > 账单明细------------------------------------*/
    /*余额账单-交易类型*/
    app.filter('operateTypeText',[function(){
        return function (type){
            var typeTex="";
            switch (type){
                case 0:
                    typeTex="充值"
                    break;
                case 1:
                    typeTex="提现"
                    break;
                case 2:
                    typeTex="奖金"
                    break;
                case 3:
                    typeTex="补贴"
                    break;
                case 5:
                    typeTex="课时费支付"
                    break;
                case 7:
                    typeTex="报名费支付"
                    break;
                case 8:
                    typeTex="课时费佣金"
                    break;
                case 9:
                    typeTex="报名费佣金"
                    break;
                case 10:
                    typeTex="报名费退款"
                    break;
                case 11:
                    typeTex="报名费佣金退款"
                    break;
                case 12:
                    typeTex="罚款"
                    break;
                case 13:
                    typeTex="报名费结算"
                    break;
                case 14:
                    typeTex="优惠券使用"
                    break;
                case 15:
                    typeTex="挂起订单"
                    break;
                case 16:
                    typeTex="支付挂起订单"
                    break;
                case 17:
                    typeTex="报名违约金"
                    break;
                case 18:
                    typeTex="充值送现金"
                    break;
            }
            return typeTex;
        };
    }]);

    /*账单明细-交易对象*/
    app.filter('tranObjectText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 1:
                    typeTex = "教练";
                    break;
                case 2:
                    typeTex = "学员";
                    break;
                case 3:
                    typeTex = "驾校";
                    break;
                default:
                    typeTex = "无";
                    break;
            }
            return typeTex;
        };
    }]);

    /*登记状态*/
    app.filter('registStateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "正常排队";
                    break;
                case 1:
                    typeTex = "滚动登记";
                    break;
                case 2:
                    typeTex = "已取消";
                    break;
            }
            return typeTex;
        };
    }]);

    /*预约结果*/
    app.filter('bookResultText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "等待排队";
                    break;
                case 1:
                    typeTex = "已排到队";
                    break;
                case 2:
                    typeTex = "没排到队";
                    break;
            }
            return typeTex;
        };
    }]);

    /*导入状态*/
    app.filter('importStateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未开始";
                    break;
                case 1:
                    typeTex = "已导入";
                    break;
            }
            return typeTex;
        };
    }]);

    /*导入状态*/
    app.filter('bookApplystateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未约考";
                    break;
                case 1:
                    typeTex = "排队中";
                    break;
                case 100:
                    typeTex = "排队成功";
                break;
                 case 101:
                    typeTex = "排队失败";
                break;
            }
            return typeTex;
        };
    }]);


    app.filter('studentApplystateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 100:
                    typeTex = "合格";
                    break;
                case 101:
                    typeTex = "不合格";
                    break;   
            }
            return typeTex;
        };
    }]);

    /*考试结果状态*/
    app.filter('resultApplystateText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 100:
                    typeTex = "合格";
                break;
                 case 101:
                    typeTex = "不合格";
                break;
            }
            return typeTex;
        };
    }]);


    /*是否有效*/
    app.filter('validText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "有效";
                break;
                 case 1:
                    typeTex = "无效";
                break;
            }
            return typeTex;
        };
    }]);
    // 班别审核通过 1未审核  2审核通过 3审核未通过
    app.filter('cState', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 1:
                    typeTex = "未审核"
                    break;
                case 2:
                    typeTex = "审核通过"
                    break;
                case 3:
                    typeTex = "审核不通过"
                    break;
            }
            return typeTex;
        };
    }]);
    // 班别开通上线  1未开通  2 已开通
    app.filter('oState', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 1:
                    typeTex = "未开通"
                    break;
                case 2:
                    typeTex = "已开通"
                    break;
            }
            return typeTex;
        };
    }]);
    // 学车学时  0为不限学时
    app.filter('xcHours', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "不限学时"
                    break;
                default:
                    typeTex = type;
                    break;
            }
            return typeTex;
        };
    }]);
    /*字符串简单截取*/
    app.filter('substrString', [function () {
        return function (type, from,to) {
            var typeTex = type;
            if((from<to)&&(typeTex.length>=to)){
                typeTex = type.substring(from,to);
            }
            return typeTex;
        };
    }]);

    /*星期转化 为中文*/
    app.filter('weekDay', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 1:
                    typeTex = "周一"
                    break;
                case 2:
                    typeTex = "周二"
                    break;
                case 3:
                    typeTex = "周三"
                    break;
                case 4:
                    typeTex = "周四"
                    break;
                case 5:
                    typeTex = "周五"
                    break;
                case 6:
                    typeTex = "周六"
                    break;
                case 0:
                    typeTex = "周日"
                    break;
            }
            return typeTex;
        };
    }]);

    /****考场预约订单状态 0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭',*********/
    app.filter('examOrderState',function(){
        return function(type){
            var typeTex = "";
            switch(type){
                case 0:
                    typeTex="未支付"; break;
                case 1:
                    typeTex="已支付"; break;
                case 2:
                    typeTex="练考中"; break;
                case 3:
                    typeTex="已完成"; break;
                case 4:
                    typeTex="已取消"; break;
                case 5:
                    typeTex="已关闭"; break;
                default:
                    typeTex = type;
                    break;
            }
            return typeTex;
        }
    })

    /*订单支付方式*/
    app.filter('payWayType',[function(){
        return function (type){
            var typeTex="";
            switch (type){
                case 'weixin':
                    typeTex="微信"
                    break;
                case 'zhifubao':
                    typeTex="支付宝"
                    break;
                case 'yinlian':
                    typeTex="银联"
                    break;
                case 'balance':
                    typeTex="余额支付"
                    break;
                case 'coupon':
                    typeTex="优惠券"
                    break;
                case 'qqwallet':
                    typeTex="QQ钱包"
                    break;
                case 'system':
                    typeTex="系统"
                    break;
            }
            return typeTex;
        };
    }]);


    /*验证button,链接，事件按钮等 权限过滤器（新版）*/
    app.filter('isAccessShow', ["$filter", function ($filter) {
        return function (id) {
            var butShow = false;
            var butList = window.menuPer.btn;
            for(var i=0; i<butList.length;i++){
                //console.log("允许的ID为："+butList[i].id)
                if(id == butList[i].id){butShow=true}
            }
            return butShow;
        };
    }]);

    /****证件类型：1：科目一，2：科目二*********/
    app.filter('cTypeTex',function(){
        return function(type){
            var typeTex = "";
            switch(type){
                case 1:
                    typeTex="科目一"; break;
                case 2:
                    typeTex="科目二"; break;
                default:
                    typeTex = "未知";
            }
            return typeTex;
        }
    })
	/****支付订单状态 *********/
    app.filter('payOrderState',function(){
        return function(type){
            var typeTex = "";
            switch(type){
                case 0:
                    typeTex="未支付"; break;
                case 1:
                    typeTex="已支付"; break;
                case 2:
                    typeTex="过期取消"; break;
                case 3:
                    typeTex="退款取消"; break;    
                default:
                    typeTex = "未知";
            }
            return typeTex;
        }
    })
	
})(window, window.angular);
