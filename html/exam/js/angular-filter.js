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

    /*金钱过滤器| 保留两位小数点*/
    app.filter('priceTex', ['$filter', function ($filter) {
        return function (price) {
        	if(price>=0){
        		return $filter('currency')(price / 100, '', 2);
        	}else{
        		var text1 = 0 - price;
            	var text2 = $filter('currency')(text1 / 100, '', 2);
            	return '-'+text2
        	}
        }
    }])

    /*时间过滤器*/
    app.filter('timeTex', ['$filter', function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "yyyy-MM-dd HH:mm:ss");
            }
        };
    }]);

    /*时间过滤器*/
    app.filter('timeTexYMD', ['$filter', function ($filter) {
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
    app.filter('timeTexD', ["$filter",function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "dd");
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

    /*时间中文过滤器*/
    app.filter('timeTexZhCn', ['$filter', function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "yyyy年MM月dd日");
            }
        };
    }]);

    /*时间只显示月和日过滤器*/
    app.filter('timeTexMMDD', ['$filter', function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "MM月dd日");
            }
        };
    }]);

    /*时间只显示时和分过滤器*/
    app.filter('timeTexHM', ['$filter', function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "HH:mm");
            }
        };
    }]);

    /*时间只显示日过滤器*/
    app.filter('timeTexDD', ['$filter', function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "dd");
            }
        };
    }]);

    /*时间只显示小时过滤器*/
    app.filter('timeTexHH', ['$filter', function ($filter) {
        return function (time) {
            if (time) {
                return (Math.floor(time / 3600000));
            }
        };
    }]);

    /*时间转换成星期过滤器*/
    app.filter('timeWeekDay', ['$filter', function ($filter) {
        return function (time) {
            if (time) {
                var weekday = new Date(time).getDay();  //定义 星期
                var week = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
                return $filter('date')(week[weekday]);
            }
        };
    }]);

    /*星期转化 为中文*/
    app.filter('weekDay', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 1:
                    typeTex = "一"
                    break;
                case 2:
                    typeTex = "二"
                    break;
                case 3:
                    typeTex = "三"
                    break;
                case 4:
                    typeTex = "四"
                    break;
                case 5:
                    typeTex = "五"
                    break;
                case 6:
                    typeTex = "六"
                    break;
                case 0:
                    typeTex = "日"
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

    /*车型过滤器 (1代表C1,2代表C2,3代表其它)  */
    app.filter('applyCarTypeTex', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
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

    /*城市过滤器 */
    app.filter('cityIdText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 1:
                    typeTex = "深圳"
                    break;
                case 2:
                    typeTex = "广州"
                    break;
                case 3:
                    typeTex = "东莞"
                    break;
                case 4:
                    typeTex = "佛山"
                    break;
            }
            return typeTex;
        };
    }]);

	/*支付方式 */
    app.filter('payway', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 'zhifubao':
                    typeTex = "支付宝"
                    break;
                case 'weixin':
                    typeTex = "微信"
                    break;
                case 'system':
                    typeTex = "系统"
                    break;
                case 'balance':
                    typeTex = "余额"
                    break;
                case 'yinlian':
                    typeTex = "银联"
                    break;
            }
            return typeTex;
        };
    }]);
    /*数字转中文 */
    app.filter('goChines', [function () {
        return function (num) {
            var typeTex = "";
            switch (num) {
                case 1:
                    typeTex = "一"
                    break;
                case 2:
                    typeTex = "二"
                    break;
                case 3:
                    typeTex = "三"
                    break;
                case 4:
                    typeTex = "四"
                    break;
                case 5:
                    typeTex = "五"
                    break;
            }
            return typeTex;
        };
    }]);
    //隐藏手机号码
    app.filter('hidephone', [function () {
        return function (str) {
            var typeTex = "";
            console.log(str)
            var newnum = str.split('');
            for(var i = 0 in newnum){
            	if(2<i && i<7){
            		typeTex = typeTex + '*';
            	}else{
            		typeTex = typeTex + newnum[i];
            	}
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
    /*订单验证状态*/
    app.filter('codeValidTex',[function(){
        return function (type){
            var typeTex="";
            switch (type){
                case 0:
                    typeTex="未验证"
                    break;
                case 1:
                    typeTex="已验证"
                    break;
            }
            return typeTex;
        };
    }]);
})(window, window.angular);
