package com.lili.coupon.qqticket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;

/**
 * Created by Zlong on 2016/五月/4.
 */
public class SDKUtil {
    //用户卷消费接口
    private static String CONSUME_URL = "https://proxy.vac.qq.com/card/user/usecard";

    public static ReqResult useCard(
            final int appid,
            final String cardId,
            final String code
    ) throws Exception {
        ReqResult reqResult = ReqResult.getFailed();

        ObjectNode node = JsonMapper.createObjectNode();
        node.put("card_id", cardId);
        node.put("code", code);
        String data = node.toString();

        String requestUrl = getUserCardUrl(appid);
        HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
        int status = hc.send(data, "utf-8");

        if (200 == status) {
            String resultString = hc.getResult();
            JsonNode resultNode = JsonMapper.getInstance().readTree(resultString);
            int errcode = resultNode.get("errcode").asInt();
            String errmsg = resultNode.get("errmsg").asText();

            reqResult.setMsgKey(String.valueOf(errcode));
            reqResult.setMsgInfo(errmsg);

            switch (errcode) {
                case 0: {
                    reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
                    break;
                }
                default: {
                    //可以根据具体的状态码返回具体的结果
                    reqResult.setCode(ResultCode.ERRORCODE.COUPON_TICKET_INVALID);
                    reqResult.setMsgInfo(ResultCode.ERRORINFO.COUPON_TICKET_INVALID);
                }
            }
        } else {
            reqResult.setCode(ResultCode.ERRORCODE.FAILED);
            reqResult.setMsgInfo("http connect error! status:" + status);
        }

        return reqResult;
    }

    private static String getUserCardUrl(int appid) throws Exception {
        String accessToken = DES.getQQCardAccessToken(appid);
        return CONSUME_URL + "?token=" + accessToken + "&appid=" + appid;
    }

    public static void main(String[] args) {
        try {
            SDKUtil.useCard(1104953245, "V0GSEzYLxIEXmE4JlMBRKi6e10DrrZn_", "8410465035524");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
