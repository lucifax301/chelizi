package exam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class TestSMS {

	 //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIO2GWU3zgUrqK";
    static final String accessKeySecret = "gX7xPLpu8WC8Zn8efHyWf6iXZIcorq";

    public static SendSmsResponse sendSms() throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers("13310862381");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("微驾");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_94740013");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        Date date=new Date();
        request.setTemplateParam("{\"name\":\"陈斌\", \"old\":\""+"234"+"\",\"time\":\"123\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }
    
    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("13310862381");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
    
    public static void main(String args[]) throws Exception{
//    	SendSmsResponse res=sendSms();
//    	System.out.println(res.getCode());
//    	System.out.println(res.getMessage());
//    	 System.out.println("RequestId=" + res.getRequestId());
//         System.out.println("BizId=" + res.getBizId());
//         
//         Thread.sleep(3000L);
////    	String bizid="231410904796955912";
////    	QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(bizid);
////        System.out.println("短信明细查询接口返回数据----------------");
////        System.out.println("Code=" + querySendDetailsResponse.getCode());
////        System.out.println("Message=" + querySendDetailsResponse.getMessage());
////        int i = 0;
////        for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
////        {
////            System.out.println("SmsSendDetailDTO["+i+"]:");
////            System.out.println("Content=" + smsSendDetailDTO.getContent());
////            System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
////            System.out.println("OutId=" + smsSendDetailDTO.getOutId());
////            System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
////            System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
////            System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
////            System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
////            System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
////        }
////        System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
////        System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
//
//         //查明细
//         if(res.getCode() != null && res.getCode().equals("OK")) {
//             QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(res.getBizId());
//             System.out.println("短信明细查询接口返回数据----------------");
//             System.out.println("Code=" + querySendDetailsResponse.getCode());
//             System.out.println("Message=" + querySendDetailsResponse.getMessage());
//             int i = 0;
//             for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
//             {
//                 System.out.println("SmsSendDetailDTO["+i+"]:");
//                 System.out.println("Content=" + smsSendDetailDTO.getContent());
//                 System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
//                 System.out.println("OutId=" + smsSendDetailDTO.getOutId());
//                 System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
//                 System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
//                 System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
//                 System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
//                 System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
//             }
//             System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
//             System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
//         }
    	Runtime run = Runtime.getRuntime();
    	TestM mm=new TestM();
    	TestM mm2=new TestM();
    	System.out.println(run.totalMemory());
    	System.out.println(run.freeMemory());
    	List list=new ArrayList();
    	list.add(new TestM());
System.out.print(System.currentTimeMillis());
//    	byte[] bb=new byte[1024*1024*10];
//    	System.out.println(bb.length);
//    	while(true){
//    		System.out.println("111");
//    		list.add(new byte[1024*1024*10]);
//    		Thread.currentThread().sleep(1000);
//    		
//    	}
    }
    
    //static TestM mm=new TestM();
}

class TestM{
	static byte[] data=new byte[1024*1024*10];
}
