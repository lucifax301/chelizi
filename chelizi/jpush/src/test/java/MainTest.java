import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

import com.lili.jpush.rmq.StuJPushListener;
import com.lili.common.vo.JpushMsg;


public class MainTest {
	private static final String stuMasterSecret="d45c091e6fd6d7d85e016524";
	private static final String stuAppKey="882ab536a72b77093a6f7529";
	
	private Logger log = Logger.getLogger(StuJPushListener.class);


	public static void main(String []args) {
		//待优化：去重复
		try {
			
			    JpushMsg jpushMsg=new JpushMsg();
			    jpushMsg.setAlias("18138201995");
			    
			    jpushMsg.setTitle("title");
			    jpushMsg.setAlter("content");
			    Map<String,String> extras=new HashMap<String,String>();
			    extras.put("extrasKey", "extrasValue");
			    extras.put("extrasKey2", "extrasValue2");
			    jpushMsg.setExtras(extras);
				// 1.消息推送
				JPushClient jpush = new JPushClient(stuMasterSecret, stuAppKey);

//				try {
//					jpush.sendAndroidNotificationWithAlias(jpushMsg.getTitle(),jpushMsg.getAlter(), jpushMsg.getExtras(), jpushMsg.getAlias());
//				} catch(Exception e){
//					jpush.sendIosNotificationWithAlias(jpushMsg.getAlter(), jpushMsg.getExtras(), jpushMsg.getAlias());
//				}
				//android
				jpushMsg.setAlias("100115064_2");
				try {
					PushPayload payload = PushPayload.newBuilder()
			                .setPlatform(Platform.android())
			                .setAudience(Audience.alias(jpushMsg.getAlias()))
			                .setMessage(Message.newBuilder()
			                        .setTitle(jpushMsg.getTitle())
			                        .setMsgContent(jpushMsg.getAlter())
			                        .addExtras(jpushMsg.getExtras())
			                        .build())
			                .build();
					jpush.sendPush(payload);
				} catch(Exception e){
					e.printStackTrace();
					jpush.sendIosNotificationWithAlias(jpushMsg.getAlter(), jpushMsg.getExtras(), jpushMsg.getAlias());
				}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
