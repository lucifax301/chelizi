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
	
	/*验证button,链接，事件按钮等 权限过滤器（新版）*/
    app.filter('isAccessShow', ["$filter", function ($filter) {
        return function (id) {
            var butShow = false;
            for(var i = 0; i < window.btn.length; i++) {
                if(id == window.btn[i].id) { 
                    butShow = true
                }
            }
            return butShow;
        };
    }]);

    /*金钱过滤器| 保留两位小数点*/
    app.filter('priceTex', ["$filter", function ($filter) {
        return function (price) {
            return $filter('currency')(price / 100, '', 2);
        }
    }])

    /*时间过滤器*/
    app.filter('timeTex', ["$filter", function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "yyyy-MM-dd HH:mm:ss");
            }
        };
    }]);

    /*时间过滤器*/
    app.filter('timeTexYMD', ["$filter", function ($filter) {
        return function (time) {
            if (time) {
                return $filter('date')(new Date(time), "yyyy-MM-dd");
            }
        };
    }]);

    /*分钟转换为小时*/
    app.filter('timeHourMinute', ["$filter", function () {
        return function (minutes) {
            if (minutes) {
                return (Math.floor(minutes / 60) + "小时" + (minutes % 60) + "分" );
            }
        };
    }]);

    /*秒转换成天小时*/
    app.filter('timeHour', ["$filter", function () {
        return function (second) {
            if (second) {
                return (Math.floor(second / 86400) + "天" + Math.floor((second % 86400)/3600) + "小时" );
            }
        };
    }]);

    /*性别过滤器 (0女1男)*/
    app.filter('sexText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "2":
                    typeTex = "女"
                    break;
                case "1":
                    typeTex = "男"
                    break;
            }
            return typeTex;
        };
    }]);
	
	//报名方式  在线报名、现场报名、终端报名、其他报名方式
	app.filter('applytypeText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "1":
                    typeTex = "在线报名"
                    break;
                case "2":
                    typeTex = "现场报名"
                    break;
				case "3":
                    typeTex = "终端报名"
                    break;
				case "4":
                    typeTex = "其他报名方式"
                    break;
            }
            return typeTex;
        };
    }]);
	
	//付费方式  一次性付款、先学后付、先付后学
	app.filter('trainmethodText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "1":
                    typeTex = "服务全包"
                    break;
                case "2":
                    typeTex = "先学后付"
                    break;
				case "3":
                    typeTex = "先付后学"
                    break;
				
            }
            return typeTex;
        };
    }]);

    /*教练备案信息 0 未备案，1已备案*/
    app.filter('icpText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "0":
                    typeTex = "未备案"
                    break;
                case "1":
                    typeTex = "已备案"
                    break;
            }
            return typeTex;
        };
    }]);
	
	/**/
	   app.filter('workTypeText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "1":
                    typeTex = "直营"
                    break;
                case "2":
                    typeTex = "加盟"
                    break;
				case "3":
                    typeTex = "挂证"
                    break;
            }
            return typeTex;
        };
    }]);
	
	/*国籍*/
    app.filter('nationalityText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "1":
                    typeTex = "中国"
                    break;
                case "2":
                    typeTex = "其他国家"
                    break;
            }
            return typeTex;
        };
    }]);
	
	/*证件类型 身份证、护照、军官证、其他*/
    app.filter('cardtypeText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "1":
                    typeTex = "身份证"
                    break;
                case "2":
                    typeTex = "护照"
					break;
				case "3":
					typeTex = "军官证"
					break;
				case "4":
					typeTex = "其他"
					break;
            }
            return typeTex;
        };
    }]);

   // 业务类型
	 app.filter('busitypeText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "0":
                    typeTex = "初领"
                    break;
                case "1":
                    typeTex = "增领"
					break;
				case "9":
					typeTex = "其他"
					break;
            }
            return typeTex;
        };
    }]);

   // 培训状态
	 app.filter('applyexamText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "1":
                    typeTex = "未缴费"
                    break;
                case "2":
                    typeTex = "已缴费"
					break;
				case "3":
					typeTex = "投递资料"
					break;
				case "4":
					typeTex = "车管受理"
					break;
				case "5":
					typeTex = "未分配教练"
					break;
				case "6":
					typeTex = "科目1约课"
					break;
				case "7":
					typeTex = "科目1结业"
					break;
				case "8":
					typeTex = "科目1报考预约"
					break;
				case "9":
					typeTex = "科目1考试通过"
					break;	
				case "10":
					typeTex = "科目1重考预约"
					break;	
		
            }
            return typeTex;
        };
    }]);
	/*
	未缴费、已缴费、投递资料、车管受理、未分配教练、科目1约课、科目1结业、科目1报考预约、
	科目1考试通过、科目1重考预约、科目2约课、科目2结业、科目2报考预约、科目2考试通过、
	科目2重考预约、长训完成、科目3约课、科目3结业、科目3报考预约、科目3考试通过、科目3重考预约、
	科目4约课、结业、科目4报考预约、科目4考试通过、待领证、已领证、已注销 */
	
   // 职务
	 app.filter('postText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "1":
                    typeTex = "安全员"
                    break;
                case "2":
                    typeTex = "考核员"
					break;
            }
            return typeTex;
        };
    }]);

    // 职务
    app.filter('colorText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "1":
                    typeTex = "蓝色"
                    break;
                case "2":
                    typeTex = "黄色"
                    break;
                case "3":
                    typeTex = "黑色"
                    break;
                case "4":
                    typeTex = "白色"
                    break;
                case "5":
                    typeTex = "绿色"
                    break;
                case "9":
                    typeTex = "其他"
                    break;
            }
            return typeTex;
        };
    }]);

    // 车辆技术评定记录
    app.filter('statusText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "0":
                    typeTex = "未使用"
                    break;
                case "1":
                    typeTex = "使用中"
                    break;
                case "2":
                    typeTex = "维护中"
                    break;
            }
            return typeTex;
        };
    }]);
	
	    // 账号类型
    app.filter('accountType', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "子账户"
                    break;
                case 1:
                    typeTex = "根账户"
                    break;
            }
            return typeTex;
        };
    }]);




    // 车辆技术评定记录
    app.filter('skillText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "0":
                    typeTex = "未通过"
                    break;
                case "1":
                    typeTex = "通过"
                    break;

            }
            return typeTex;
        };
    }]);

    // 车辆检测记录
    app.filter('checkText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "0":
                    typeTex = "不合格"
                    break;
                case "1":
                    typeTex = "合格"
                    break;

            }
            return typeTex;
        };
    }]);

    // 车辆二次维保记录
    app.filter('secondText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "0":
                    typeTex = "未完成"
                    break;
                case "1":
                    typeTex = "完成"
                    break;

            }
            return typeTex;
        };
    }]);



    // 门店管理
    app.filter('storeText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 1:
                    typeTex = "建设筹备中"
                    break;
                case 2:
                    typeTex = "开业招生中"
                    break;
                case 3:
                    typeTex = "正常教学中"
                    break;
                case 4:
                    typeTex = "停业整顿中"
                    break;
            }
            return typeTex;
        };
    }]);


    // 教练状态状态
    app.filter('coachStatus', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "0":
                    typeTex = "在职"
                    break;
                case "1":
                    typeTex = "离职"
                    break;
            }
            return typeTex;
        };
    }]);

    // 班级类型
    app.filter('classType', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case "1":
                    typeTex = "服务全包"
                    break;
                case "2":
                    typeTex = "先学后付"
                    break;
                case "3":
                    typeTex = "先付后学"
                    break;
            }
            return typeTex;
        };
    }]);


		
    // 账号状态
    app.filter('accountStatus', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "正常"
                    break;
                case 1:
                    typeTex = "停用"
                    break;
            }
            return typeTex;
        };
    }]);


    // 用户申请注册状态
    app.filter('registerStatus', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "待审核"
                    break;
                case 1:
                    typeTex = "审核通过"
                    break;
                case 2:
                    typeTex = "审核拒绝"
                    break;
                case 3:
                    typeTex = "注册成功"
                break;
            }
            return typeTex;
        };
    }]);
	
	// 客户来源渠道
    app.filter('sourceText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case '1':
                    typeTex = "门店"
                    break;
                case '2':
                    typeTex = "微信"
                    break;
                case '3':
                    typeTex = "网页"
                    break;
				case '4':
                    typeTex = "微信二维码"
                    break;
				case '5':
                    typeTex = "大客户推荐"
                    break;
				case '6':
                    typeTex = "其他"
                    break;
            }
            return typeTex;
        };
    }]);
	
	// 客户跟踪进度
    app.filter('progressText', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case '1':
                    typeTex = "未跟踪"
                    break;
                case '2':
                    typeTex = "跟踪中"
                    break;
                case '3':
                    typeTex = "跟踪结束"
                    break;
				case '4':
                    typeTex = "已转化"
                    break;
				
            }
            return typeTex;
        };
    }]);


    // 客户跟踪进度
    app.filter('complainTex', [function () {
        return function (type) {
            var typeTex = "";
            switch (type) {
                case 0:
                    typeTex = "未处理"
                    break;
                case 1:
                    typeTex = "处理中"
                    break;
                case 2:
                    typeTex = "已处理"
                    break;
                case 3:
                    typeTex = "拒绝"
                    break;
            }
            return typeTex;
        };
    }]);

    //权限状态
    app.filter('roleState',function(){
        return function(type){
            var typeTex = "";
            switch(type){
                case 0:
                    typeTex="已启用"; break;
                case 1:
                    typeTex="未启用"; break;
                default:
                    typeTex = type;
                    break;
            }
            return typeTex;
        }
    })
	
	//充值状态
    app.filter('rstatusText',function(){
        return function(type){
            var typeTex = "";
            switch(type){
                case '1':
                    typeTex="成功"; break;
                case '2':
                    typeTex="失败"; break;
                default:
                    typeTex = type;
                    break;
            }
            return typeTex;
        }
    })
	//充值方式
	  app.filter('rtypeText',function(){
	return function(type){
		var typeTex = "";
		switch(type){
			case '1':
				typeTex="微信"; break;
			case '2':
				typeTex="支付宝"; break;
			default:
				typeTex = type;
				break;
		}
		return typeTex;
	}
    })
	
	//状态
	app.filter('statusfilter',function(){
		return function(type){
			var retText = "";
			switch(type){
				case 0:
					retText = "未启用";
					break;
				default:
					retText = "启用";
					break;
			}
			return retText;
		}
    })
	
		//id
	app.filter('idfilter',function(){
		return function(type){
			var retText = "000"+type;
			return retText.substring(retText.length-3);
		}
    })
	
			//null处理
	app.filter('nullfilter',function(){
		return function(type){
			
			if(type == null || type=='')
				return "-";
			
			return type;
		}
    })
	
})(window, window.angular);
