!function(e,r,a){var t=r.module("ngFilter",[]);t.filter("limitToSec",[function(){return function(e,r){var a=e;return e.length>r&&(a=e.substring(0,r)+"···"),a}}]),t.filter("isButtonShow",["$filter",function(e){return function(e){var r=!1,a=localStorage.getItem("lili-btnList");if(a)for(var t=a.split(","),c=0,n=t.length;n>c;c++)e==t[c]&&(r=!0);return r}}]),t.filter("isAccessShow",["$filter",function(r){return function(r){for(var a=!1,t=e.menuPer.btn,c=0;c<t.length;c++)r==t[c].id&&(a=!0);return a}}]),t.filter("priceTex",["$filter",function(e){return function(r){return e("currency")(r/100,"",2)}}]),t.filter("timeTex",["$filter",function(e){return function(r){return r?e("date")(new Date(r),"yyyy-MM-dd HH:mm:ss"):void 0}}]),t.filter("timeTexYMD",["$filter",function(e){return function(r){return r?e("date")(new Date(r),"yyyy-MM-dd"):void 0}}]),t.filter("timeHourMinute",["$filter",function(){return function(e){return e?Math.floor(e/60)+"小时"+e%60+"分":void 0}}]),t.filter("timeDayHourMinute",["$filter",function(){return function(e){return e?Math.floor(e/1440)+"天"+Math.floor(e%1440/60)+"小时"+e%60+"分":void 0}}]),t.filter("timeHour",["$filter",function(){return function(e){return e?Math.floor(e/86400)+"天"+Math.floor(e%86400/3600)+"小时":void 0}}]),t.filter("sexText",[function(){return function(e){var r="";switch(e){case 0:r="女";break;case 1:r="男"}return r}}]),t.filter("accountStateText",[function(){return function(e){var r="";switch(e){case 0:r="正常";break;case 1:r="临时封号";break;case 2:r="永久封号"}return r}}]),t.filter("payStateTex",[function(){return function(e){var r="";switch(e){case 0:r="未付款";break;case 1:r="已付款";break;case 2:r="付款失败";break;case 9:r="老学员自动付款";break;case 10:r="已挂起";break;case 11:r="退款中";break;case 12:r="已退款"}return r}}]),t.filter("orderStateTex",[function(){return function(e){var r="";switch(e){case 0:r="已取消";break;case 1:r="已下单";break;case 2:r="已接单";break;case 3:r="上课中";break;case 4:r="已下课";break;case 5:r="教练已评";break;case 6:r="学员已评";break;case 7:r="双方已评";break;case 9:r="已拒单";break;case 10:r="缺课"}return r}}]),t.filter("isdelText",[function(){return function(e){var r="";switch(e){case 0:r="未结款";break;case 1:r="已结款"}return r}}]),t.filter("orderStateText",[function(){return function(e){var r="";switch(e){case 0:r="已取消";break;case 1:r="已下单";break;case 2:r="未结款";break;case 3:r="已结款";break;case 4:r="退款中";break;case 5:r="已退款";break;case 6:r="退款失败"}return r}}]),t.filter("isCommonText",[function(){return function(e){var r="";switch(e){case 0:r="启用";break;case 1:r="停用"}return r}}]),t.filter("payWayType",[function(){return function(e){var r="";switch(e){case"weixin":r="微信";break;case"zhifubao":r="支付宝";break;case"yinlian":r="银联";break;case"balance":r="余额支付";break;case"coupon":r="优惠券";break;case"qqwallet":r="QQ钱包";break;case"system":r="系统"}return r}}]),t.filter("refundType",[function(){return function(e){var r="";switch(e){case 0:r="退款中";break;case 1:r="已退款"}return r}}]),t.filter("payStateType",[function(){return function(e){var r="";switch(e){case 0:r="未开始";break;case 1:r="已提交";break;case 100:r="支付成功";break;case 101:r="支付失败";break;case 103:r="退款中";break;case 104:r="退款失败";break;case 105:r="已退款"}return r}}]),t.filter("studentState",[function(){return function(e){var r="";switch(e){case 0:r="未认证";break;case 1:r="认证中";break;case 2:r="已认证";break;case 3:r="认证失败";break;case 4:r="已过期";break;case 5:r="已吊销"}return r}}]),t.filter("accountStatusTex",[function(){return function(e){var r="";switch(e){case 0:r="正常";break;case 1:r="已封号"}return r}}]),t.filter("drLicenseTex",[function(){return function(e){var r="";switch(e){case 0:r="未认证";break;case 1:r="认证中";break;case 2:r="已认证";break;case 3:r="认证失败";break;case 4:r="已过期";break;case 5:r="已吊销"}return r}}]),t.filter("applyCarTypeTex",[function(){return function(e){var r="";switch(parseInt(e)){case 1:r="C1";break;case 2:r="C2";break;case 3:r="其他"}return r}}]),t.filter("idNumberLast4",[function(){return function(e){return e.slice(-4,25)}}]),t.filter("applystateText",[function(){return function(e){var r="";switch(e){case 0:r="未开始";break;case 1:r="已提交";break;case 100:r="已成功";break;case 101:r="已失败"}return r}}]),t.filter("studentStateText",[function(){return function(e){var r="";switch(e){case"-1,0":r="暂不报名";break;case"1,0":r="尚未报名";break;case"0,0":r="尚未报名";break;case"1,100":r="已报名";break;case"2,0":r="尚未支付";break;case"2,100":r="已支付";break;case"2,101":r="支付失败";break;case"3,0":r="尚未填写个人信息";break;case"3,100":r="已填写个人信息";break;case"4,0":r="未邮寄资料";break;case"4,1":r="资料已邮寄";break;case"4,100":r="资料齐全";break;case"4,101":r="资料不全";break;case"5,0":r="未交表";break;case"5,1":r="表已寄出";break;case"5,100":r="收表成功";break;case"6,0":r="已收表";break;case"6,1":r="受理中";break;case"6,100":r="受理成功";break;case"6,101":r="受理失败";break;case"101,0":r="报名成功";break;case"101,1":r="已约理论课";break;case"101,100":r="已上理论课";break;case"101,101":r="缺理论课";break;case"201,0":r="未模拟考试";break;case"201,100":r="模拟考试达标";break;case"201,101":r="模拟考试未达标";break;case"301,0":r="未约考科一";break;case"301,1":r="科一排队中";break;case"301,100":r="科一排队成功";break;case"301,101":r="科一排队失败";break;case"302,0":r="已约考科一";break;case"302,100":r="科一合格";break;case"302,101":r="科一不合格";break;case"401,0":r="未约科考二";break;case"401,1":r="科二排队中";break;case"401,100":r="科二排队成功";break;case"401,101":r="科二排队失败";break;case"402,0":r="已约考科二";break;case"402,100":r="科二合格";break;case"402,101":r="科二不合格";break;case"501,0":r="未约长考";break;case"501,1":r="已约长考";break;case"501,100":r="长训合格";break;case"501,101":r="长训不合格";break;case"601,0":r="未约考科三";break;case"601,1":r="科三排队中";break;case"601,100":r="科三排队成功";break;case"601,101":r="科三排队失败";break;case"602,0":r="已约考科三";break;case"602,100":r="科三合格";break;case"602,101":r="科三不合格";break;case"701,0":r="未约考科四";break;case"701,1":r="科四排队中";break;case"701,100":r="科四排队成功";break;case"701,101":r="科四排队失败";break;case"702,0":r="已约考科四";break;case"702,100":r="科四合格";break;case"702,101":r="科四不合格";break;case"801,0":r="已拿证";break;default:r="未报名"}return r}}]),t.filter("operateTypeText",[function(){return function(e){var r="";switch(e){case 0:r="充值";break;case 1:r="提现";break;case 2:r="奖金";break;case 3:r="补贴";break;case 5:r="课时费支付";break;case 7:r="报名费支付";break;case 8:r="课时费佣金";break;case 9:r="报名费佣金";break;case 10:r="报名费退款";break;case 11:r="报名费佣金退款";break;case 12:r="罚款";break;case 13:r="报名费结算";break;case 14:r="优惠券使用";break;case 15:r="挂起订单";break;case 16:r="支付挂起订单";break;case 17:r="报名违约金";break;case 18:r="充值送现金"}return r}}]),t.filter("carLevelText",[function(){return function(e){var r="";switch(e){case 1:r="普通";break;case 2:r="舒适";break;case 3:r="豪华"}return r}}]),t.filter("checkIdStateText",[function(){return function(e){var r="";switch(e){case 0:r="未认证";break;case 1:r="认证中";break;case 2:r="已认证";break;case 3:r="认证失败"}return r}}]),t.filter("typeTex",[function(){return function(e){var r="";switch(e){case"bank":r="银行卡";break;case"wexin":r="微信";break;case"zhifubao":r="支付宝";break;case"qqwallet":r="QQ钱包"}return r}}]),t.filter("checkStatusTex",[function(){return function(e){var r="";switch(e){case 0:r="审核中";break;case 1:r="提现成功";break;case 2:r="提现失败";break;case 3:r="财务确认";break;case 4:r="银行处理中";break;case 5:r="银行处理失败"}return r}}]),t.filter("statusTex",[function(){return function(e){var r="";switch(e){case 0:r="待反馈";break;case 1:r="有效卡号";break;case 2:r="无效卡号"}return r}}]),t.filter("postStateTex",[function(){return function(e){var r="";switch(e){case 0:r="资料未邮寄";break;case 1:r="资料已邮寄";break;case 2:r="资料已收到"}return r}}]),t.filter("signUpPayStateTex",[function(){return function(e){var r="";switch(e){case 0:r="未支付";break;case 1:r="已提交";break;case 100:r="支付成功";break;case 101:r="支付失败"}return r}}]),t.filter("bonusStatusText",[function(){return function(e){var r="";switch(e){case 1:r="未确认";break;case 2:r="待发奖";break;case 3:r="发放成功";break;case 4:r="发放失败";break;case 5:r="财务拒绝"}return r}}]),t.filter("bonusStatusDetailsText",[function(){return function(e){var r="";switch(e){case 0:r="待发放";break;case 1:r="发放成功";break;case 2:r="发放失败"}return r}}]),t.filter("accBalaStateText",[function(){return function(e){var r=1;return e.match(/\+/)?r=2:e.match(/\-/)&&(r=3),r}}]),t.filter("tranObjectText",[function(){return function(e){var r="";switch(e){case 1:r="教练";break;case 2:r="学员";break;case 3:r="驾校";break;default:r="无"}return r}}]),t.filter("couponType",[function(){return function(e){var r="";switch(e){case 0:r="代金券";break;case 1:r="课时券";break;case 2:r="折扣券";break;default:r="无"}return r}}]),t.filter("verifyType",[function(){return function(e){var r="";switch(e){case 0:r="未审核";break;case 1:r="已审核";break;case 2:r="审核不过";break;case 8:r="未生效";break;case 9:r="已过期";break;default:r="无"}return r}}]),t.filter("uType",[function(){return function(e){var r="";switch(e){case 1:r="学员";break;case 2:r="教练"}return r}}]),t.filter("typeStateText",[function(){return function(e){var r="";switch(e){case 1:r="未使用";break;case 2:r="已使用";break;case 3:r="已作废";break;case 4:r="已过期";break;default:r="无"}return r}}]),t.filter("activeTex",[function(){return function(e){var r="";switch(e){case 0:r="未激活";break;case 1:r="已启用"}return r}}]),t.filter("checkStatusText",[function(){return function(e){var r="";switch(e){case 1:r="未处理";break;case 2:r="已审核";break;case 3:r="审核不过";break;case 4:r="同意变更";break;case 5:r="拒绝变更";break;default:r="无"}return r}}]),t.filter("planActive",[function(){return function(e){var r="";switch(e){case 0:r="未激活";break;case 1:r="已激活"}return r}}]),t.filter("enrollTex",function(){return function(e){var r="";switch(e){case 0:r="无";break;case 1:r="已报名"}return r}}),t.filter("indepentTex",function(){return function(e){var r="";switch(e){case 1:r="独立使用";break;case 2:r="混合使用"}return r}}),t.filter("planAutoTex",function(){return function(e){var r="";switch(e){case 0:r="需财务审核";break;case 1:r="自动到帐"}return r}}),t.filter("activeTex",function(){return function(e){var r="";switch(e){case 0:r="未激活";break;case 1:r="已激活"}return r}}),t.filter("schoolTex",function(){return function(e){var r="";switch(e){case"0":r="喱喱";break;default:r=e}return r}}),t.filter("importSchoolFileType",function(){return function(e){var r="";switch(e){case 1:r="教练信息";break;case 2:r="学员信息";break;case 3:r="训练场信息";break;case 4:r="车辆信息"}return r}}),t.filter("importSchoolStatus",function(){return function(e){var r="";switch(e){case 1:r="待处理";break;case 2:r="处理中";break;case 3:r="处理成功";break;case 4:r="处理失败"}return r}}),t.filter("couponCondiTex",function(){return function(e){var r="";switch(e){case 0:r="指定时间";break;case 1:r="指定区域";break;case 2:r="指定科目";break;case 3:r="限额次数";break;case 4:r="分享可得"}return r}}),t.filter("planVtypeTex",function(){return function(e){var r="";switch(e){case 1:r="大客户即扫码";break;case 2:r="普惠"}return r}}),t.filter("stuStateforPlan",function(){return function(e){var r="";switch(e){case 0:r="未生效";break;case 1:r="已生效";break;default:r="未生效"}return r}}),t.filter("importStuStatus",function(){return function(e){var r="";switch(e){case 0:r="未确认";break;case 1:r="成功";break;case 2:r="失败";break;case 3:r="放弃";break;default:r=e}return r}}),t.filter("userText",function(){return function(e){var r="";switch(e){case 0:r="全体用户";break;case 1:r="(全体/指定)教练";break;case 2:r="(全体/指定)学员";break;case 3:r="驾校教练";break;case 4:r="驾校学员";break;case 5:r="城市教练";break;case 6:r="城市学员";break;case 7:r="特约教练";break;default:r=e}return r}}),t.filter("text",function(){return function(e){var r="";switch(e){case 0:r="今日重点播报";break;case 1:r="优惠活动";break;case 2:r="订单消息";break;default:r=e}return r}}),t.filter("cState",[function(){return function(e){var r="";switch(e){case 1:r="未审核";break;case 2:r="审核通过";break;case 3:r="审核不通过"}return r}}]),t.filter("oState",[function(){return function(e){var r="";switch(e){case 1:r="未开通";break;case 2:r="已开通"}return r}}]),t.filter("roleState",function(){return function(e){var r="";switch(e){case 0:r="已启用";break;case 1:r="未启用";break;default:r=e}return r}}),t.filter("nStatus",[function(){return function(e){var r="";switch(e){case 0:r="未审核";break;case 1:r="审核通过";break;case 2:r="审核不过"}return r}}])}(window,window.angular);