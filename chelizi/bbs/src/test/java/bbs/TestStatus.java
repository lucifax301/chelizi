package bbs;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.bbs.action.SensitiveWordFilter;
import com.lili.bbs.dto.BbsWord;
import com.lili.bbs.manager.IBBSManager;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;

@ContextConfiguration(locations = { "classpath:spring-init.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestStatus {

	@Autowired
	RedisUtil redisUtil;

	@Autowired
	IBBSManager bbsManager;
	
	public static int minMatchTYpe = 1;      //最小匹配规则
	
	public static int maxMatchType = 2;      //最大匹配规则
	
	@Autowired
	SensitiveWordFilter sensitiveWordFilter;
	
	@Test
	public void test() throws ParseException {
		//敏感词测试
		String content = "您是谁，您能女女女奥斯卡烦恼";
		System.out.println("********************************** content : " + content);
		List<BbsWord> bbsWordList = redisUtil.get(REDISKEY.BBS_FILTER_WORD_LIST);
		if (bbsWordList != null && bbsWordList.size() > 0) {
		}
		else {
			bbsWordList  = bbsManager.getBBSWordList(new BbsWord());
			if (bbsWordList != null && bbsWordList.size() > 0) {
				System.out.println("待检测语句字数：" + content.length());
				content = sensitiveWordFilter.replaceSensitiveWord(content, minMatchTYpe, "*");
				System.out.println("********************************** content : " + content);
			}
		}
		
		
		/**
		 * 缓存测试
		 */
		/*System.out.println("************************************** back");
		List<BbsMessage> bbsList = redisUtil.zRevRange(REDISKEY.BBS_STUDENT_LIST, 0, 140);
		System.out.println("************************** bbsList : " + bbsList.size());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		if (bbsList == null || bbsList.size() == 0) {
			bbsList = bbsManager.getBBSList(new BbsMessage(), 1, 10000);
			if (bbsList != null && bbsList.size() > 0) {
				for (BbsMessage bbsMsg : bbsList) {
					String ctime = formatter.format(bbsMsg.getCtime());
					System.out.println("************************** bbsMsg : " + bbsMsg.getId() + ", content : " + bbsMsg.getContent());
					redisUtil.zAdd(REDISKEY.BBS_STUDENT_LIST, bbsMsg, formatter.parse(ctime).getTime());
				}
			}
		} else {
			for (BbsMessage bbsMsg : bbsList) {
				System.out.println( "************************** bbsList : " + bbsMsg.getId() + ", content : " + bbsMsg.getContent());
				String ctime = formatter.format(bbsMsg.getCtime());
				
				redisUtil.zRemove(REDISKEY.BBS_STUDENT_LIST, bbsMsg);
				List<BbsMessage> bbsList2 = redisUtil.zRevRange(REDISKEY.BBS_STUDENT_LIST, 0, 140);
				System.out.println("************************** bbsList2 : " + bbsList2.size());
				
				redisUtil.zRemoveByScore(REDISKEY.BBS_STUDENT_LIST,  formatter.parse(ctime).getTime(),  formatter.parse(ctime).getTime());
				List<BbsMessage> bbsList3 = redisUtil.zRevRange(REDISKEY.BBS_STUDENT_LIST, 1, 140);
				System.out.println("************************** bbsList3 : " + bbsList3.size());
				
				redisUtil.zRemRange(REDISKEY.BBS_STUDENT_LIST,  formatter.parse(ctime).getTime(),  formatter.parse(ctime).getTime());
				List<BbsMessage> bbsList1 = redisUtil.zRevRange(REDISKEY.BBS_STUDENT_LIST, 1, 140);
				System.out.println("************************** bbsList1 : " + bbsList1.size());
			}
		}*/
	}

}
