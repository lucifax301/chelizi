/**
 * 
 */
package com.lili.pay.vo;

/**
 * @author linbo
 *
 */
public class PayWayType
{
    public static final String AUTOPAY = "auto";    //老学员已付过款，自动支付
    public static final String BALANCE = "balance";  //余额支付 
    public static final String WX = "weixin";       //微信
    public static final String ZFB = "zhifubao";    //支付宝
    public static final String YL = "yinlian";      //银联
    public static final String COUPON = "coupon";   //课时券支付
    public static final String QQWALLET = "qqwallet"; //QQ钱包
    public static final String SYSTEMPAY = "system"; //系统调整：例如充送等
    
    //返回汉字名称
    public static String getChinaName(String name)
    {
        String chinaName = name;
        switch (name)
        {
        case AUTOPAY:
            chinaName =  "自动";
            break;
        case BALANCE:
            chinaName = "余额";
            break;
        case WX:
            chinaName = "微信";
            break;
        case ZFB:
            chinaName = "支付宝";
            break;
        case YL:
            chinaName = "银联";
            break;
        case COUPON:
            chinaName = "优惠券";
            break;
        case QQWALLET:
            chinaName = "QQ钱包";
            break;
        default:
            break;
        }
        return chinaName;
    }
}
