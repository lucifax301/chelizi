package com.lili;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.vo.ReqResult;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.service.BasePriceService;
import com.lili.order.service.CarLevelService;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.CoachCommentService;
import com.lili.order.service.CoachLevelService;
import com.lili.order.service.CoachScoreService;
import com.lili.order.service.CoachStatisticService;
import com.lili.order.service.CommentTagService;
import com.lili.order.service.OrderService;
import com.lili.order.service.StuCommentService;
import com.lili.order.service.TimeRateService;
import com.lili.order.service.UnitPriceService;
import com.lili.order.vo.BasePriceVo;
import com.lili.order.vo.CarLevelVo;
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.CoachCommentQuery;
import com.lili.order.vo.CoachLevelVo;
import com.lili.order.vo.CoachScoreQuery;
import com.lili.order.vo.CoachScoreVo;
import com.lili.order.vo.CoachStatisticVo;
import com.lili.order.vo.CommentTagQuery;
import com.lili.order.vo.CommentTagVo;
import com.lili.order.vo.OrderExtQuery;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.order.vo.StuCommentVo;
import com.lili.order.vo.TimeRateVo;
import com.lili.order.vo.UnitPriceVo;
import com.lili.pay.service.PayServiceNew;
import com.lili.pay.vo.PayVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class OrderTest
{
	private Logger log = Logger.getLogger(OrderTest.class);
    @Autowired
    private OrderService orderService;
    @Autowired
	private PayServiceNew payServiceNew;
//    @Autowired
//    private CoachClassService coachClassService;
//    @Autowired
//    private CoachLevelService coachLevelService;
//    @Autowired
//    private CarLevelService carLevelService;
//    @Autowired
//    private TimeRateService timeRateService;
//    @Autowired
//    private BasePriceService basePriceService;
//    @Autowired
//    private CommentTagService commentTagService;
//    @Autowired
//    private CoachScoreService coachScoreService;
//    @Autowired
//    private UnitPriceService unitPriceService;
//    @Autowired
//    private CoachStatisticService coachStatisticService;
//	@Autowired
//	StuCommentService stuCommentService;
//	@Autowired
//	CoachCommentService coachCommentService;
//	@Autowired
//	CommentTagService commentTageService;
//    @Value("${datasource.url}")
//    private String url; 
    
    @Before
    public void before() {
    	System.out.println("start>>>>");
    }
//    @After
//    public void after(){
//    	System.out.println("<<<<affter");
//    	try {
//			Thread.sleep(1000000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//    @Test
//    public void addPrice() {
//    	UnitPriceVo u=new UnitPriceVo();
//    	u.setAllowance(20);
//    	u.setCalid(1);
//    	u.setCityId(100);
//    	u.setColid(1);
//    	u.setCourseId(1);
//    	u.setDftype(1);
//    	u.setPrice(20000);
//    	u.setTstart(new Date());
//    	u.setTend(TimeUtil.parseDate("2015-10-31 17:55:50"));
//    	try {
//			unitPriceService.addUnitPrice(u);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//    @Test
//    public void getPrice() throws Exception {
//    	try {
//    	long now=System.currentTimeMillis();
//    	
//    	int price=unitPriceService.getCoachPrice(new Date(now), 100, 1, 1, 1,1);
//    	System.out.println(">>>>>>>>>"+price);
//    	}catch(Exception e){
//    		e.printStackTrace();
//    		
//    	}
//    }
//    @Test
//    public void getOrder() throws Exception {
//    	try {
//    	long now=System.currentTimeMillis();
//    	
//    	OrderVo o=orderService.queryOrderById("75114c58990547fb8bc3415c1fa9201e", new OrderQuery());
//    	System.out.println(o.getTimeLeft()+">>>>>>>>>"+o);
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//    }
//    @Test
//    public void testAc() throws Exception {
//	    Date atime = new Date();
//		String date = TimeUtil.getDateFormat(atime, "yyyyMMdd");
//		long coachId = 12345;
//		CoachStatisticVo vo = coachStatisticService.getCoachStatistc(coachId, date);
//		vo.setOrderAccept(vo.getOrderAccept() + 1);
//		vo.setOrderMoney(vo.getOrderMoney() + 18000);
//		// 回存
//		coachStatisticService.saveCoachStatistic(vo);
//    }
//    @Test 
//    public void quartzTest() {
//    	OrderQuery orderQuery=new OrderQuery();
//    	orderQuery.setPageIndex(1);
//		orderQuery.setPageSize(100);
//		OrderVo search = new OrderVo();
//		Date now=new Date();
//		search.setRstart(DateUtil.dateAfterMilliSecond(now, -70000));
//		// 多加10秒防止遗漏
//		search.setRend(now);
//		//已下课的不推送
//		search.setOrderState(OrderConstant.ORDERSTATE.INCLASS);
//		List<OrderVo> olist = null;
//		try {
//			olist = orderService.queryByPstart(search, orderQuery);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(olist.size()+"=="+olist);
//    }
//    
//    @Test
//    public void addclass() throws Exception {
//    	for(int i=1000004510;i<1000004517;i++){
//	    	CoachClassVo cc=new CoachClassVo();
//	    	long now=System.currentTimeMillis();
//	    	cc.setCstart(new Date(now));
//	    	cc.setCend(new Date(now+60000*60*10));
//	    	cc.setCtype(1);
//	    	cc.setRstart(new Date(now));
//	    	cc.setCoachId((long)(i));
//	    	
//	    	CoachClassVo ccb=new CoachClassVo();
//	    	long tom=System.currentTimeMillis()+60000*60*24;
//	    	ccb.setCstart(new Date(tom));
//	    	ccb.setCend(new Date(tom+60000*60*10));
//	    	ccb.setCtype(3);
//	    	ccb.setRstart(new Date(tom));
//	    	ccb.setCoachId((long)(i));
//	    	ccb.setBookNum(0);
//	    	ccb.setMaxNum(3);
//	    	
//	    	if(i%2==0){
//	    		coachClassService.addCoachClass(cc);
//	    		coachClassService.addCoachClass(ccb);
//	    	}
//	    }
//    }
//    
//    @Test
//    public void getclass()  {
//    	
//    	try {
//			coachClassService.queryBookClassByCoachDate("2015-10-30", 123l, 0, 1, 30);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    }
//    @Test
//    public void isCoachIdle() throws Exception  {
//    	long coachId=1000000032;
//    	Date start=TimeUtil.parseDate("2015-10-23 12:49:50");
//    	Date end=TimeUtil.parseDate("2015-10-23 14:49:45");
//		List<CoachClassVo> list=coachClassService.queryByCoachDateWithNoPrice(start, coachId,OrderConstant.ISDEL.NOTDELETE ,null,null, null);
//		boolean idle=true;
//		if(list==null||list.isEmpty()){
//			System.out.println(">>>>>"+idle);
//			return ;
//		} else {
//			for(CoachClassVo one:list){
//				long woda=TimeUtil.calcDistanceMillis(one.getCend(),start);
//				long tada=TimeUtil.calcDistanceMillis(end,one.getCstart());
//				if(woda<=0&&tada<=0){
//					idle=false;
//					break;
//				}
//			}
//		}
//		System.out.println(">>"+idle);
//	}
//    
//    
//    @Test
//    public void addBasePrice() throws Exception {
//    	for(int i=1;i<10;i++) {
//    		BasePriceVo bp=new BasePriceVo();
//    		bp.setCityId(100);
//    		bp.setColid(1);
//    		bp.setCourseId(i);
//    		bp.setPrice(150);
//    		basePriceService.addBasePrice(bp);
//    		bp=new BasePriceVo();
//    		bp.setCityId(100);
//    		bp.setColid(2);
//    		bp.setCourseId(i);
//    		bp.setPrice(100);
//    		basePriceService.addBasePrice(bp);
//    		bp=new BasePriceVo();
//    		bp.setCityId(100);
//    		bp.setColid(3);
//    		bp.setCourseId(i);
//    		bp.setPrice(80);
//    		basePriceService.addBasePrice(bp);
//    	}
//    }
//    @Test
//    public void addCarLevel() throws Exception {
//    	CarLevelVo cl=new CarLevelVo();
//    	cl.setName("gaoji");
//    	cl.setPrate(150);
//    	carLevelService.addCarLevel(cl);
//    	cl.setName("zhongji");
//    	cl.setPrate(100);
//    	carLevelService.addCarLevel(cl);
//    	cl.setName("chuji");
//    	cl.setPrate(80);
//    	carLevelService.addCarLevel(cl);
//    }
//    @Test
//    public void addCoachLevel() throws Exception {
//    	CoachLevelVo cl=new CoachLevelVo();
//    	cl.setName("gaoji");
//    	cl.setPrate(150);
//    	coachLevelService.addCoachLevel(cl);
//    	cl.setName("zhongji");
//    	cl.setPrate(100);
//    	coachLevelService.addCoachLevel(cl);
//    	cl.setName("chuji");
//    	cl.setPrate(80);
//    	coachLevelService.addCoachLevel(cl);
//    }
//    @Test
//    public void addTimeRate() throws Exception {
//    	TimeRateVo tr=new TimeRateVo();
//    	tr.setPtype(1);
//    	tr.setWstart(1);
//    	tr.setWend(5);
//    	tr.setHstart(0);
//    	tr.setHend(18);
//    	tr.setPrate(100);
//    	timeRateService.addTimeRate(tr);
//    	tr.setPtype(1);
//    	tr.setWstart(1);
//    	tr.setWend(5);
//    	tr.setHstart(18);
//    	tr.setHend(24);
//    	tr.setPrate(120);
//    	timeRateService.addTimeRate(tr);
//    	tr.setPtype(1);
//    	tr.setWstart(6);
//    	tr.setWend(7);
//    	tr.setHstart(0);
//    	tr.setHend(24);
//    	tr.setPrate(120);
//    	timeRateService.addTimeRate(tr);
//    	tr.setPtype(3);
//    	tr.setYint(2015);
//    	tr.setMint(10);
//    	tr.setDstart(16);
//    	tr.setDend(30);
//    	tr.setHstart(0);
//    	tr.setHend(24);
//    	tr.setPrate(120);
//    	timeRateService.addTimeRate(tr);
//    }
//    @Test
//    public void findclass() throws Exception {
//    	CoachClassVo coachClassVo = new CoachClassVo();
//		coachClassVo.setCoachId(2l);
//		// 默认现在
//		long now = System.currentTimeMillis();
//		coachClassVo.setCstart(new Date(now));
//		// 默认一节课
//		coachClassVo.setCend(new Date(now + 60000
//				* OrderConstant.clztime));
//		CoachClassQuery coachClassQuery = new CoachClassQuery();
//		coachClassQuery.setPageSize(1);
//		List<CoachClassVo> coachClass = coachClassService
//				.queryBetween(coachClassVo, coachClassQuery);
//		System.out.println(coachClass);
//    }
//    
//    @Test
//    public void findBookclass() throws Exception {
//    	try {
//		
//		List<CoachClassVo> coachClass = coachClassService.queryBookClassByCoachDate("2015-10-23 12:12:12",1000000038L,  OrderConstant.ISDEL.NOTDELETE, 1, 10);
//		System.out.println(coachClass);
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//    }
//    @Test
//    public void bookOrder() throws Exception {
//    	try {
//        	System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+url);
//        	OrderVo order=new OrderVo();
//        	order.setCoachId(1000004716L);
//        	order.setCourseId("1");
//        	order.setCstart(new Date());
//        	order.setCend(new Date());
//        	order.setLearnAddr("learnaddr");
//        	order.setOrderState(1);
//        	order.setOrderId(StringUtil.getOrderId());
//        	order.setPstart(new Date());
//        	order.setPend(new Date());
//        	order.setStudentId(100000033l);
//        	order.setStuAddr("stuaddr");
//        	order.setPrice(3);
//        	order.setPayState(1);
//        	order.setLge(114.3);
//        	order.setLae(22.2);
//        	order.setOtype(3);
//        	order.setOrderState(1);
//        	order.setClzNum(1);
//        	order.setCcid(1);
//        	order.setOrderId("3214321666");
//    		String result=orderService.bookOrder(order);
//        	System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+result);
//        	}catch(Exception e) {
//        		System.out.println("Exception");
//        		e.printStackTrace();
//        	}
//    }
//    
//    @Test
//    public void testCoach5() throws Exception {
////    	List<Map<String,Long>> result=coachClassService.getCoach5Date(1000004716);
//    	List<CoachClassVo> result=coachClassService.queryByCoachDateAllWithNoPrice(TimeUtil.parseDate("2015-12-11 14:00:00"), 1000004567l, OrderConstant.ISDEL.NOTDELETE, 3, 1, 100);
//    	System.out.println(result.size()+"<<<<"+result);
//    }
//    @Test
//    public void testStuComment() throws Exception {
//    	try{
////    	Object result=stuCommentService.getStuCommentList(100000017, 1, 10,1);
//    		CoachCommentQuery query=new CoachCommentQuery();
//    		Object result=coachCommentService.queryByOrderId("647a836da85b465bbc2da46906857c37",query);
//    		System.out.println(result);
//    		query.setPageSize(20);
//    		result=coachCommentService.queryByOrderId("647a836da85b465bbc2da46906857c37",query);
//    		System.out.println(result);
//    	}catch(Exception e){
//    		System.out.println("Exception");
//    		e.printStackTrace();
//    	}
//    }
//    @Test
//    public void testCancel1() throws Exception {
//    	String orderId="16f70d201259476a9b71c7126fe2035e";
//    	
//    	try{
//    		orderService.cancelOrder(orderId, 1, "reseaon", 2, "tokenId", 1);
//    	}catch(Exception e){
//    		System.out.println("Exception");
//    		e.printStackTrace();
//    	}
//    }
//    
//    @Test
//    public void testLog() throws Exception {
//    	int init=0;
//    	if(log.isDebugEnabled()){
//    		log.debug("im in debug="+(++init));
//    	}
//    	if(log.isInfoEnabled()){
//    		log.info("im in info="+(++init));
//    	}
//    	log.warn("im in warn="+(++init));
//    	log.error("im in error="+(++init));
//    	System.out.println("last="+init);
//    }
//    @Test
//    public void addOrder() throws Exception {
//    	try {
//    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+url);
//    	OrderVo order=new OrderVo();
//    	order.setCoachId(1000005275l);
//    	order.setCourseId("1");
//    	order.setCstart(new Date());
//    	order.setCend(new Date());
//    	order.setLearnAddr("learnaddr");
//    	order.setOrderState(1);
//    	order.setOrderId(StringUtil.getOrderId());
//    	order.setPstart(new Date());
//    	order.setPend(new Date());
//    	order.setStudentId(115074l);
//    	order.setStuAddr("stuaddr");
//    	order.setPrice(3);
//    	order.setPayState(1);
//    	order.setLge(114.3);
//    	order.setLae(22.2);
//    	order.setOtype(1);
//    	order.setOrderState(1);
//    	order.setClzNum(2);
//    	order.setCarId(123);
//    	order.setCoupon(123L);
//    	
//    	order.setOrderId("321432143333311");
//		String result=orderService.handlePlaceNow(order);
//    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+result);
//    	}catch(Exception e) {
//    		System.out.println("Exception");
//    		e.printStackTrace();
//    	}
//    }
//    @Test
//    public void testStudentSorce() throws Exception {
//    	try {
//    		System.out.println(url+"<<<");
//    		CommentTagVo tag=new CommentTagVo();
//    		tag.setCourseId(Integer.valueOf(1));
//    		tag.setIsdel(OrderConstant.ISDEL.NOTDELETE);
//    		tag.setType(OrderConstant.USETYPE.STUDENT);
//    		List<CommentTagVo> list=commentTageService.queryByObjectAnd(tag, new CommentTagQuery());
//    		List<Integer> taglist=BeanCopy.getFieldList(list, "ctid");
//    		List<StuCommentVo> scoreList=stuCommentService.queryByTags(taglist, 115081L);
//    		BeanCopy.copyList(scoreList, list, BeanCopy.COPY2NULL,"ctid");
//    		System.out.println(list+"<<<");
//    		} catch (Exception e) {
//    			 ReqResult.getFailed();
//    		}
//    	
//    }
//    @Test
//    public void testCha() throws Exception {
//    	
//   
//    	for(int i=1;i<3;i++) {
//    	Date now=new Date();
//    	OrderQuery orderQuery = new OrderQuery();
//		orderQuery.setPageIndex(i);
//		orderQuery.setPageSize(100);
//		OrderVo search = new OrderVo();
//		search.setOtype(OrderConstant.OTYPE.BOOKORDER);
//		search.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
//		search.setPstart(DateUtil.dateAfterMinute(now, -1));
//		// 多加0.1秒防止遗漏
//		search.setPend(DateUtil.dateAfterMilliSecond(search.getPstart(),
//				60100));
//		List<OrderVo> olist = null;
//		try {
//			olist = orderService.queryByPstart(search, orderQuery);
//		} catch (Exception e) {
//			log.error(TimeUtil.getDateFormat(now)+"jpush to 3,4chadan:"+e.getMessage(),e);
//		}
//    	}
//    }
//    
//    @Test
//    public void testCancel() throws Exception {
//    	String orderId="fa0402b20cf54d8bbfcb2ad2928b1ff4";
//    	Integer retype=1;
//    	String reseaon="aa";
//    	Integer ucancel=2;
//    	String tokenId="tokenId";
//    	int type=1;
//    	orderService.cancelOrder(orderId, retype, reseaon, ucancel, tokenId, type);
//    }
//    @Test
//    public void addCommentTag() throws Exception {
//    	CommentTagVo ct=null;
//    	for(int i=1;i<6;i++){
//	    	ct=new CommentTagVo();
//	    	ct.setType(1);
//	    	ct.setCourseId(i);
//	    	ct.setTag(i+"分教练便签"+1);
//	    	commentTagService.addCommentTag(ct);
//	    	ct=new CommentTagVo();
//	    	ct.setType(1);
//	    	ct.setCourseId(i);
//	    	ct.setTag(i+"分教练便签"+2);
//	    	commentTagService.addCommentTag(ct);
//    	}
//    	for(int i=1;i<10;i++){
//	    	ct=new CommentTagVo();
//	    	ct.setType(2);
//	    	ct.setCourseId(i);
//	    	ct.setTag(i+"科学生便签"+1);
//	    	commentTagService.addCommentTag(ct);
//	    	ct=new CommentTagVo();
//	    	ct.setType(2);
//	    	ct.setCourseId(i);
//	    	ct.setTag(i+"科学生便签"+2);
//	    	commentTagService.addCommentTag(ct);
//    	}
//    }
//    @Test
//    public void addCoachScoreTag() throws Exception {
//    	CoachScoreVo cs=coachScoreService.queryCoachScoreById(1000030l, new CoachScoreQuery());
//		if(cs==null){
//			cs=new CoachScoreVo();
//		}
//		cs.setScoreTotal(cs.getScoreTotal()+5);
//		cs.setOrderTotal(cs.getOrderTotal()+1);
//		cs.setCoachId(100030l);
//		//回存
//		coachScoreService.saveCoachScore(cs);
//    }
    @Test
    public void test()  {
    	
    	InsuranceOrder order=new InsuranceOrder();
    //	order.setInsuranceId("sdfdsfsd");
    	order.setName("test");
    	order.setStudentId((long) 123);
    	order.setCityId(12345);
    	order.setPrice(15000);
    	order.setYear(3);
    	try {
    		orderService.saveInsuranceOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}

    	
    }
    public static void main(String []args)  throws Exception {
        @SuppressWarnings("resource")
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-init.xml");
        OrderService os=ac.getBean(OrderService.class);
        long now=System.currentTimeMillis(); 	
    	int price=os.getCoachPrice(new Date(now),new Date(now+60000*45), 100, "1", 1, 1);
    	System.out.println(">>>>>>>>>"+price);
//        System.out.print(tl);
    }
    
}
