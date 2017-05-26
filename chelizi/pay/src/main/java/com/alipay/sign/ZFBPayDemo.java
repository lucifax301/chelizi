package com.alipay.sign;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ZFBPayDemo
{

    // 商户PID
    public static final String PARTNER = "2088111797257831";
    // 商户收款账号
    public static final String SELLER = "sz-chelizi@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE [] = {
              "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMW5c6U1CQp4dXsY"
            + "JMzCVbUTehRxh/D/M80WMbLRdwl87n5+kEMwYgyy8ZIEHInw4iGkXQ8vdX02xT6E"
            + "nvLpVExN6NPHwfhNaeFJLt2MAsXx6ipyDXS/f+/uCSjYYYvTA/iTMIoc50S6AJUl"
            + "aM5dWmye6E9K3Yfvq436031k//ZTAgMBAAECgYEAj3bAMl8I8E/27hnWmbtn9Lrt"
            + "GWhG+tYw/93asaTKelfrtFyg0B+LBSvRm+Df1Daz3TUlUJ9e4DudYn9X1Wj3gB9t"
            + "17RaHHRvXRQ/sZpSFMupRWhgZhAtDY5vCxkgLsIP6qmI+y5igV0njUUY9in3j0ee"
            + "jte2lxUBTXu7ms5epAECQQDm7kZ+PLP5BH263WOlg49Zn86hY0pv0ht7rDBlWvj1"
            + "sqjFvRYFjkQz7HMWgfMLz09ZLa6Lj9QGtu603nNYWd1LAkEA2zBZa7URmkahNfVz"
            + "NsHNWOdhk9IOJCm1HfeJHbBk2um0ymqkX+NA9M2d2wN61DnWTLfAJ1P/Tcb0vMcX"
            + "xxXOGQJBAJ4M+mYvuaV2825PU4HWVugqPcurHykFDhZItR7JcpcGXghn74iB5B/9"
            + "do8zBVtR2abZYxrVp3GpBhr4HM2dQ50CQHR+7gfg5MoqX+Rd4C5qMsndsG7wZbOy"
            + "tyCGP8oXxoLTU9OBenu8PPnfjtaoss+5Lbz4rJg6fKbJ56mt52VYoYECQCr2VWn0"
            + "Rb2PiS2q7biSAqf4rTn6UZGAuL0YDPhRJXlMJotE7Sc/qIVVTkVDHmUAFYQFskSw"
            + "KyU4dGL3fZWeolk=",
              "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAO8QZKik1VE7gMPa" 
            + "z8Zz7cnVoU9RtGktgKdmO/QHfQwr/vTcR39aEIwcKr0BaiuYqIKyXEoTDrSOU3i4"
            + "AQFpQOLwRuaGK5dqMlRYa/Y6rOzsYGd3PMRrwERlJL5JUedL+Oz0IOONt2mAYu6W"
            + "4NLL+YRbd+FH/hj+b+s85MeSZHYVAgMBAAECgYBwRWigctjjj2hHkqllb+hKk5Nw"
            + "JtTtWDtFYpPhZOrrHnZcFF9Xhbqjsk63+MQduVHaH12MRCrxzK2S0gdW3FFl2kD8"
            + "L5HLOLXuAxQRLg3juLkAoKffhH0mX+ct7hoeCG7nGT09Pcg86ToAra6WCeYYazga"
            + "vSljAPVBbDzk4hn6eQJBAPtuwFnHs9t3lfsFjsgW2kvfPXTbhowZA+ovCBxZmexa"
            + "2/Vug/WEGPBtmaWcUc5j5QJkWZ2tlw3Lqe6XliV+L88CQQDzaB+l0+d2brKlLjVC"
            + "DWH42S/I2diLlv32utdApif9T9nuV2ypTkqT/juCf+CfZP60+1N0DJngEhx2heBv"
            + "yXDbAkBfZvefWVIabTbAZ6x0qY8tCMjF5WIZGIjOvAfE0g7Aiv05FDUH3Jal68BI"
            + "AZdcy7SigrcqSCqvtl6FRIlZMpcrAkEAscp2g2v6/7jv6FOON0cLLveFPdJRp/SM"
            + "YLItFF3Ji+/NoZheGHTFaMAku7eoXQUqPwvs8zFZHCBehfxX6Y9cTQJABAk0p+ii"
            + "AeB6OUw2Etd5IR7ZmZvJriqAWUtorN33wKWWDELNHyp4uh31+nXJ+l7N9kW/La30"
            + "AKvj62Jc0XNpDw==",
              "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPrt8YTvB8XsdcZz"
            + "ZuoCuLU67j8t3C+ZZ+oG7WMEEjT8PAHllkGgknQSMbi4gwinmh79aeDVBw4k+cvH"
            + "F0G6Xiyid3vkc3bUS8Adcc92zCgQVBdIjh43t7WCKU/aBO7ut5n1X79c2OzelGeu"
            + "DNl8RVfE3RiCJANEHqHaRfBruocpAgMBAAECgYEAosZRousv/qt6ZwabXvfu+9lD"
            + "jYZhTBtWdlCSE2YjrW1mO9DD3MSd3F+lE2/VyHp4ltvKoAdwsTM5O4BlzMRpq3/P"
            + "p0DGyaQrG964gATGKrnsd6nm/ScTcWTy0SgX3OFqioxhVrMMpeHGegDVTaY1CL1K"
            + "Lv4TjmoJmAneMyAeF5UCQQD+5w5TdkGUEJKbuEBhTRRJvB0jCHq7UmGxjnzqdXeW"
            + "8MX+RovVBimdggQYZi5MOlb9V5N3fH4Y2Yblf/v3uwVXAkEA/AKCK4Rz3VtHdhOg"
            + "ep4S6gMWXjTNm3O8BhmlZnuL7emxmx+101O/Z14bZpb3nizvgdnhv/z4k716dp4S"
            + "w66HfwJBAKdg3X1cl688OqjaEhxSWtIYHiHU/ZJnhBLtd4UVYVPSApD3jfZxt33f"
            + "1ASZhCk13c05ZnH9Y2e53uRwZ+nB+r0CQAeIb6mrWOdJ/jXaWlav+PLDytxgCbFW"
            + "4UzxYU9R7BndfXCcnwvFGhiV6I3Ff3tb7oDcKXJatnd8F3gKO5db1TMCQCxli3aw"
            + "vUJGwQFqldlrNKPcligEF6tyam/bPytJ8plUjeuYs0SExwXv+j+5veyJG6pHSxRm"
            + "4Y2of7LY8le7ECQ=",
              "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANHSzHQEw5yZULA9"
            + "8wMGTACzMyz4z8qKVvIjyksBjFWbHf16+2J9e3HD7vBcEaPABivH9Z3D/x1C7Otz"
            + "UZjdZrHJ+VWjNH3e/IW7tH62Ff7bi3kKUvS1PA6a0HfwOgPBamxK2nl4cJs+tyxM"
            + "RnGLuhCWfzs8Vk0nPb2f4KB8XgPRAgMBAAECgYBFjtpUg9fNLE5Bfuf20rmTadTH"
            + "TdykymO0dgT0BJlfL8Mrav+3UN4BgvDNIdh/LoNdNDbKjFUggDVGgL5GLO+D2r+e"
            + "lCgL5otgcUJr57D6Evi6o51vmSk57SxrtgthECGz7l5HY+L2t9AR7EGrEZRp83uQ"
            + "eodMbxS9OV65H2NxgQJBAPug512EC3w2M+vYDBFwdDDpe1k9tVdD6OgRo+vczZ1W"
            + "whv+Jgs7jPuCezBAuvlx84mkIEhxkvsJCcl66Gt3YTkCQQDVd/hlhRWTNjlXu92V"
            + "3DfU8sHOTeLu6l8r6z14cEN2LCxEkAt6GAPWTAaIgKSBReFVqF4diaHlHcTZ3LkB"
            + "4e9ZAkBSi486m0WVQsa3mJEk62oCXaWMOfUSIoH2F0tZ5X5CHiLhQfdC+g8etsgo"
            + "pSNxDxOgVIoOmctotKdURBCYqHnpAkBDHkuNsACOntf8y+bp/anYXNOvCDI4KJzv"
            + "9DEJs1Kiz1TD4f6Zzv813AWZ2cjflJc7FYsyAlpS7b89OPc6rk1RAkA+ca7h7zaL"
            + "lW1cPbrPV76rg+9n/X+AB2dAM+IxYl22eg3zi7SzO5zjF4Ju3O35IVyJLgCKW7sB"
            + "JqK09jAU6V2b"};

    //private static String RSA_PUB = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    /**
     * call alipay sdk pay. 调用SDK支付
     * 
     */
    public static String pay(String orderId, String subject, String data, double price, String callback, int clientType)
    {
        // 订单
        String orderInfo = getOrderInfo(orderId, subject, data, String.valueOf(price), callback);

        // 对订单做RSA 签名,暂不做客户端版本验证
        String sign = sign(orderInfo, RSA_PRIVATE[0]);
        //String sign = sign(orderInfo, RSA_PRIVATE[clientType]);
        try
        {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();
        return payInfo;
    }

    /**
     * create the order info. 创建订单信息
     * @param callback 
     * 
     */
    public static String getOrderInfo(String orderId, String subject, String body, String price, String callback)
    {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + callback
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        // orderInfo += "&return_url=\"m.alipay.com\"";

        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     * 
     */
    public String getOutTradeNo()
    {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     * 
     * @param content
     *            待签名订单信息
     * @param rsaPrivate 
     */
    public static String sign(String content, String rsaPrivate)
    {
        return SignUtils.sign(content, rsaPrivate);
    }

    /**
     * get the sign type we use. 获取签名方式
     * 
     */
    public static String getSignType()
    {
        return "sign_type=\"RSA\"";
    }

    public static void Main()
    {

    }
}
