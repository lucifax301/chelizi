package com.lili.httpaccess.controller;

import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.pay.service.ChargeService;
import com.lili.pay.service.PayService;
import com.lili.pay.service.PayServiceNew;
import com.lili.pay.vo.QQPayCallbackResData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@Controller
@RequestMapping("/v1/files")
public class FileController {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private PayService payService;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private PayServiceNew payServiceNew;

    /**
     * 获取请求参数中所有的信息
     *
     * @param request
     * @return
     */
    private static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                //System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

    /**
     * 第三方接口：微信回调接口
     *
     * @return
     */
    @RequestMapping(value = "/wxPayCallBack", produces = "text/html", method = RequestMethod.POST)
    public void wxPayCallBack(HttpServletRequest request, HttpServletResponse response) {
        ReqResult r = new ReqResult();
        try {
            response.setContentType("text/html;charset=UTF-8");

            InputStream in = request.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            // String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                // tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            String result = tempStr.toString();
            r = payServiceNew.wxPayCallback(result);

            PrintWriter out = response.getWriter();
            out.print(r.getResult().get(ResultCode.RESULTKEY.DATA));
        } catch (Exception e) {
            logger.error("wxPayCallBack:" + r.getResult(), e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return;
    }

    private Map<String, String> formatZFBMessyParam(Map<String, String[]> paramsMap) {
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator<String> iter = paramsMap.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = paramsMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 第三方接口：支付宝回调接口
     *
     * @return
     */
    @RequestMapping(value = "/zfbPayCallBack", produces = "text/html", method = RequestMethod.POST)
    public void zfbPayCallBack(HttpServletRequest request, HttpServletResponse response) {
        ReqResult r = new ReqResult();
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");

            Map<String, String> params = formatZFBMessyParam(request.getParameterMap());
            r = payServiceNew.zfbPayCallback(params);

            if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)) {
                logger.info("zfbPayCallBack result: success");
                PrintWriter out = response.getWriter();
                out.println("success");
                return;
            } else {
                PrintWriter out = response.getWriter();
                logger.info("zfbPayCallBack print: fail");
                out.println("fail");
            }
        } catch (Exception e) {
            logger.error("zfbPayCallBack error:" + r.getResult(), e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        logger.info("zfbPayCallBack result: fail");
        return;
    }

    @RequestMapping(value = "/zfbChargeCallBack", produces = "text/html", method = RequestMethod.POST)
    public void zfbChargeCallBack(HttpServletRequest request, HttpServletResponse response) {
        ReqResult r = new ReqResult();
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");

            Map<String, String> params = formatZFBMessyParam(request.getParameterMap());
            r = chargeService.zfbChargeCallBack(params);

            if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)) {
                PrintWriter out = response.getWriter();
                out.println("success");
                logger.info("zfbChargeCallBack result: success");
                return;
            } else {
                logger.info("zfbChargeCallBack print: fail");
                PrintWriter out = response.getWriter();
                out.println("fail");
            }
        } catch (Exception e) {
            logger.error("zfbChargeCallBack error:" + r.getResult(), e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        logger.info("zfbChargeCallBack result: fail");
        return;
    }

    /**
     * 第三方接口：微信回调接口
     *
     * @return
     */
    @RequestMapping(value = "/wxChargeCallBack", produces = "text/html", method = RequestMethod.POST)
    public void wxChargeCallBack(HttpServletRequest request, HttpServletResponse response) {
        ReqResult r = new ReqResult();
        try {
            response.setContentType("text/html;charset=UTF-8");

            InputStream in = request.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            // String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                // tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            String result = tempStr.toString();
            r = chargeService.wxChargeCallBack(result);

            PrintWriter out = response.getWriter();
            out.print(r.getResult().get(ResultCode.RESULTKEY.DATA));
        } catch (Exception e) {
            logger.error("wxChargeCallBack:" + r.getResult(), e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

        return;
    }

    /**
     * 第三方接口：QQ钱包回调接口
     *
     * @return
     */
    @RequestMapping(value = "/qqPayCallBack", produces = "text/html", method = RequestMethod.GET)
    public void qqPayCallBack(HttpServletRequest request, HttpServletResponse response) {
        ReqResult r = new ReqResult();
        try {
            response.setContentType("text/html;charset=UTF-8");
            String attach = request.getParameter("attach");
            String bank_type = request.getParameter("bank_type");
            String bargainor_id = request.getParameter("bargainor_id");
            String charset = request.getParameter("charset");
            String fee_type = request.getParameter("fee_type");
            String pay_result = request.getParameter("pay_result");
            String purchase_alias = request.getParameter("purchase_alias");
            String sign = request.getParameter("sign");
            String sp_billno = request.getParameter("sp_billno");
            String time_end = request.getParameter("time_end");
            String total_fee = request.getParameter("total_fee");
            String transaction_id = request.getParameter("transaction_id");
            String ver = request.getParameter("ver");

            QQPayCallbackResData qqPayCallbackResData = new QQPayCallbackResData();
            qqPayCallbackResData.setAttach(attach);
            qqPayCallbackResData.setBank_type(bank_type);
            qqPayCallbackResData.setBargainor_id(bargainor_id);
            qqPayCallbackResData.setCharset(charset);
            qqPayCallbackResData.setFee_type(Integer.parseInt(fee_type));
            qqPayCallbackResData.setPay_result(Integer.parseInt(pay_result));
            qqPayCallbackResData.setPurchase_alias(purchase_alias);
            qqPayCallbackResData.setSign(sign);
            qqPayCallbackResData.setSp_billno(sp_billno);
            qqPayCallbackResData.setTime_end(time_end);
            qqPayCallbackResData.setTotal_fee(Integer.parseInt(total_fee));
            qqPayCallbackResData.setTransaction_id(transaction_id);
            qqPayCallbackResData.setVer(ver);

            r = payServiceNew.qqPayCallback(qqPayCallbackResData);

            PrintWriter out = response.getWriter();
            out.print(r.getResult().get(ResultCode.RESULTKEY.DATA));
        } catch (Exception e) {
            logger.error("qqPayallBack:" + r.getResult(), e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
    }

    @RequestMapping(value = "/ylPayCallBack", produces = "text/html", method = RequestMethod.POST)
    public void ylPayCallBack(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        LogUtil.writeLog("BackRcvResponse接收后台通知开始");
        String encoding = "utf-8";
        try {
            request.setCharacterEncoding("ISO-8859-1");
            encoding = request.getParameter("encoding");
            // 获取银联通知服务器发送的后台通知参数
            Map<String, String> reqParam = getAllRequestParam(request);

            LogUtil.printRequestLog(reqParam);

            Map<String, String> valideData = null;
            if (null != reqParam && !reqParam.isEmpty()) {
                Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
                valideData = new HashMap<String, String>(reqParam.size());
                while (it.hasNext()) {
                    Entry<String, String> e = it.next();
                    String key = e.getKey();
                    String value = e.getValue();
                    value = new String(value.getBytes("ISO-8859-1"), encoding);
                    valideData.put(key, value);
                }
            }

            ReqResult reqResult = payServiceNew.ylPayCallback(valideData, encoding);

            if (reqResult.isSuccess()) {
                //返回给银联服务器http 200  状态码
                response.getWriter().print("ok");
            } else {
                response.getWriter().print("error");
            }
        } catch (IOException e) {
            logger.error("encoding: " + encoding, e);
        }

        LogUtil.writeLog("BackRcvResponse接收后台通知结束");
    }

    /**
     * 第三方接口：微信回调接口
     *
     * @param result
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Object test(@RequestParam String result) {
        return result;
    }
}
