/**
 * 
 */
package com.lili.pay.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.service.EmailService;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.pay.vo.PayMessage;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PurposeType;
import com.lili.student.vo.RechargeRecordVo;
import com.lili.ylpay.sdk.CertUtil;
import com.lili.ylpay.sdk.DemoBase;
import com.lili.ylpay.sdk.LogUtil;
import com.lili.ylpay.sdk.SDKConfig;
import com.lili.ylpay.sdk.SDKUtil;

/**
 * @author linbo
 *         银联支付
 */
public class YLPayAction extends PayAction
{
    @Autowired
    private EmailService emailService;
    
    @Resource(name="payRehandleProducer")
    DefaultMQProducer payRehandleProducer;
    
	Logger logger = LoggerFactory.getLogger(YLPayAction.class);
    @PostConstruct
    private void initYLConfig()
    {
        SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
    }

    @Override
    public void doPayAction(PayVo payVo, final ReqResult reqResult) throws Exception
    {
        try {
			// 预处理
			Object discountAdv = getPayPurpose(payVo.getPayPurpose()).purposeAdvance(payVo, reqResult);
			if (discountAdv == null)
			{
			    return;
			}

			String txnAmt = String.valueOf(payVo.getPayValue());
			String orderId = payVo.getPayOrderId();
			String txnTime = TimeUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");

			Map<String, String> contentData = new HashMap<String, String>();

			/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
			contentData.put("version", SDKUtil.version);            // 版本号 全渠道默认值
			contentData.put("encoding", SDKUtil.encoding_UTF8);     // 字符集编码 可以使用UTF-8,GBK两种方式
			contentData.put("signMethod", "01");                    // 签名方法 目前只支持01：RSA方式证书加密
			contentData.put("txnType", "01");                       // 交易类型 01:消费
			contentData.put("txnSubType", "01");                    // 交易子类 01：消费
			contentData.put("bizType", "000201");                   // 填写000201
			contentData.put("channelType", "07");                   // 渠道类型

			/*** 商户接入参数 ***/
			contentData.put("merId", SDKConfig.getConfig().getMerId());                        // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			contentData.put("accessType", "0");                     // 接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
			contentData.put("orderId", orderId);                    // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
			contentData.put("txnTime", txnTime);                    // 订单发送时间，取系统时间，格式为YYYYMMddhhmmss，必须取当前时间，否则会报txnTime无效
			contentData.put("accType", "01");                       // 账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)

			// testaccNo:6216261000000000018
			// testCertifId:341126197709218366
			// ////////如果在控件回显卡号【需开通 接收商户共享信息】，商户号开通了商户对敏感信息加密的权限那么，需要对 卡号accNo加密使用：
			contentData.put("encryptCertId", CertUtil.getEncryptCertId());      // 上送敏感信息加密域的加密证书序列号
			String accNo = SDKUtil.encryptPan(SDKConfig.getConfig().getAccNo(), "UTF-8"); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
			contentData.put("accNo", accNo);
			// ////////

			// ///////如果在控件回显卡号【需开通 接收商户共享信息】，商户未开通敏感信息加密的权限那么不对敏感信息加密使用：
			// contentData.put("accNo", "6216261000000000018"); //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
			// //////

			// 代收交易的上送的卡验证要素为：姓名或者证件类型+证件号码
			Map<String, String> customerInfoMap = new HashMap<String, String>();
			customerInfoMap.put("certifTp", "01");                          // 证件类型
			customerInfoMap.put("certifId", SDKConfig.getConfig().getCertifId());         // 证件号码
			// customerInfoMap.put("customerNm", "全渠道"); //姓名
			String customerInfoStr = SDKUtil.getCustomerInfoWithEncrypt(customerInfoMap, SDKConfig.getConfig().getAccNo(),
			        SDKUtil.encoding_UTF8);

			contentData.put("customerInfo", customerInfoStr);
			contentData.put("txnAmt", txnAmt);                          // 交易金额 单位为分，不能带小数点
			contentData.put("currencyCode", "156");                     // 境内商户固定 156 人民币
			contentData.put("reqReserved", "透传字段");                    // 商户自定义保留域，交易应答时会原样返回

			// 后台通知地址（需设置为外网能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
			// 后台通知参数详见open.unionpay.com帮助中心 下载 产品接口规范 网关支付产品接口规范 消费交易 商户通知
			// 注意:1.需设置为外网能访问，否则收不到通知 2.http https均可 3.收单后台通知后需要10秒内返回http200或302状态码
			// 4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5 分钟后会再次通知。
			// 5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
			contentData.put("backUrl", SDKConfig.getConfig().getCallback());

			/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
			Map<String, String> submitFromData = SDKUtil.signData(contentData, SDKUtil.encoding_UTF8);            // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();                                 // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
			                                                                 // acpsdk.backTransUrl
			// 如果这里通讯读超时（30秒），需发起交易状态查询交易
			Map<String, String> resmap = SDKUtil.submitUrl(submitFromData, requestAppUrl, SDKUtil.encoding_UTF8);  // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

			/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
			// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
			String respCode = resmap.get("respCode");
			if (("00").equals(respCode))
			{
			    // 成功,获取tn号
			    String tn = resmap.get("tn");
			    reqResult.setCode(ResultCode.ERRORCODE.SUCCESS);
			    reqResult.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			    reqResult.setData(tn);

			    // 记录一个缓存到redis
			    redisUtil.set(REDISKEY.PRE_PAY_ORDER + payVo.getPayOrderId(), payVo, 24 * 60 * 60);
			}
			else
			{
			    // 其他应答码为失败请排查原因
			    String respMsg = resmap.get("respMsg");
			    reqResult.setCode(ResultCode.ERRORCODE.FAILED);
			    reqResult.setMsgInfo(respMsg);
			}

			String reqMessage = DemoBase.genHtmlResult(submitFromData);
			String rspMessage = DemoBase.genHtmlResult(resmap);
			logger.debug("获取tn交易请求报文:\n" + reqMessage + "\n" + "应答报文:\n" + rspMessage + "");
		} catch (Exception e) {
            logger.error("payVo:" + payVo, e);
            emailService.send("【系统】[用户支付异常啦，请抓紧时间处理！！]", "YLPayAction-->doPayAction|"+payVo+"|Exception:"+e);
		}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lili.pay.action.IPayAction#payCallBack(java.lang.Object)
     */
    @Override
    public ReqResult payCallBack(Object... callbackParam)
    {
        @SuppressWarnings("unchecked")
        Map<String, String> valideData = (Map<String, String>) callbackParam[0];
        String encoding = (String) callbackParam[1];
        // 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!SDKUtil.validate(valideData, encoding))
        {
            LogUtil.writeLog("验证签名结果[失败].");
            // 验签失败，需解决验签问题
            ReqResult reqResult = ReqResult.getFailed();
            return reqResult;
        }
        else
        {
            ReqResult reqResult = ReqResult.getSuccess();
            LogUtil.writeLog("验证签名结果[成功].");
            // 【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
            String orderId = valideData.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取
            
            if(redisUtil.isExist(REDISKEY.PRE_PAY_ORDER + orderId))
            {
                PayVo payVo = redisUtil.get(REDISKEY.PRE_PAY_ORDER + orderId);
                //20160629	此处传递的traceTime无法解析！！！已更改为txnTime 商户发送交易时间
                logger.info("YLPayAction valideData -------------->" + valideData.toString());
                reqResult = getPayPurpose(payVo.getPayPurpose()).doPurpose(payVo,
                        // TimeUtil.parseDate(valideData.get("traceTime"), "MMDDHHmmss"),
                        TimeUtil.parseDate(valideData.get("txnTime"), "yyyyMMddhhmmss"),
                        valideData.get("queryId"), Integer.parseInt(valideData.get("txnAmt")));
                //只有成功充值的学员才充值送
            	if(reqResult.isSuccess() && payVo.getUserType()==OrderConstant.USETYPE.STUDENT && payVo.getPayPurpose() == PurposeType.CHARGE){
            	  try {
            		RechargeRecordVo record=new RechargeRecordVo();
            		record.setCharge(payVo.getPayValue());
            		record.setStudentId(payVo.getUserId());
            		record.setCuid(payVo.getUserId());
            		record.setPayTime(new Date());
            		record.setPayWay(payVo.getPayWay());
            		record.setWaterId(valideData.get("queryId"));
            		rechargeService.recharge(record);
            	  }catch(Exception e){
            		  emailService.send("【系统】[用户银联支付异常啦，记录充值送异常，请抓紧时间处理！！]", "YLPayAction-->doPayAction-->rechargeService Fail! payVo: " + payVo);
            		  logger.error(reqResult+" with "+payVo+" Exception:"+e.getMessage(),e);
            	  }
            	}else {
            		logger.error(reqResult+" with "+payVo+" is not incorrect,so do nothing.");
            		//20160905报名回调失败处理
            		/*if (!reqResult.isSuccess() && payVo.getPayPurpose() == PurposeType.SIGNUP) {
            			try {
            				Message msg=new Message();
            				msg.setTopic(payRehandleProducer.getCreateTopicKey());
            				msg.setTags(OrderConstant.RMQTAG.STUDENT_ENROLL_PAY);
            				PayMessage pmsg = new PayMessage();
            				pmsg = BeanCopy.copyNotNull(payVo, pmsg);
            				
            				pmsg.setEndTime(TimeUtil.parseDate(valideData.get("txnTime"), "yyyyMMddhhmmss"));;
            				pmsg.setWaterNum(valideData.get("queryId"));
            				pmsg.setTotalFee(Integer.parseInt(valideData.get("txnAmt")));
            				msg.setBody(SerializableUtil.serialize(pmsg));
            				payRehandleProducer.send(msg);
            				logger.debug("payErrorProducer has sent a message. MSGTYPE_STUDENT_REGISTER. payVo: "+ payVo);
            			} 
            			catch (Exception e) {
            				emailService.send("【系统】[用户银联支付异常啦，发送MQ处理失败，请抓紧时间处理！！]", "YLPayAction-->doPayAction-->payRehandleProducer Fail! payVo : " + payVo);
            				logger.error("payErrorProducer error "+e);
            				e.printStackTrace();
            			} 
            		}*/
            	}
            }
            else
            {
                reqResult = ReqResult.getFailed();
            }
            //String respCode = valideData.get("respCode"); // 获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
            return reqResult;
        }
    }
}
