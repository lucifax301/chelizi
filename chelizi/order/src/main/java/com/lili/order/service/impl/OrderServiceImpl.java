package com.lili.order.service.impl;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.coach.dto.Car;
import com.lili.coach.dto.Coach;
import com.lili.coach.dto.Trfield;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.TrfieldManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.*;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.service.CouponService;
import com.lili.file.manager.FileManager;
import com.lili.file.vo.CoursenewVo;
import com.lili.order.dao.mapper.InsuranceOrderMapper;
import com.lili.order.dao.mapper.OrderMapper;
import com.lili.order.dao.po.OrderPo;
import com.lili.order.dto.InsuranceOrder;
import com.lili.order.dto.InsuranceOrderExample;
import com.lili.order.service.*;
import com.lili.order.vo.*;
import com.lili.pay.manager.MoneyManager;
import com.lili.school.dto.WechatEnrollPackage;
import com.lili.school.manager.SchoolManager;
import com.lili.school.service.SchoolService;
import com.lili.student.dto.Student;
import com.lili.student.dto.StudentAccount;
import com.lili.student.manager.MycoachesManager;
import com.lili.student.manager.StudentManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.*;

public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    BasePriceService basePriceService;
    @Autowired
    CarLevelService carLevelService;
    @Autowired
    TimeRateService timeRateService;
    @Autowired
    OrderCancelService orderCancelService;
    @Autowired
    CoachClassService coachClassService;
    @Autowired
    CoachCommentService coachCommentService;
    @Autowired
    StuCommentService stuCommentService;
    @Autowired
    SkillRelationService skillRelationService;
    private Logger log = Logger.getLogger(OrderServiceImpl.class);
    @Resource(name = "orderProducer")
    private DefaultMQProducer orderProducer;
    @Autowired
    private PlantClassService plantClassService;
    @Autowired
    private StudentManager studentManager;
    @Autowired
    private CoachManager coachManager;
    @Autowired
    private CarManager carManager;
    @Autowired
    private FileManager fileManager;
    @Autowired
    private TrfieldManager trfieldManager;
    @Autowired
    private MoneyManager moneyManager;
    @Resource(name = "jpushProducer")
    private DefaultMQProducer jpushProducer;
    @Autowired
    private CouponService couponService;
	@Autowired
	private SchoolManager schoolManager;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
	SchoolService schoolService;
    @Autowired
    InsuranceOrderMapper insuranceOrderMapper;
    @Autowired
    MycoachesManager mycoachesManager;

    public void addOrder(OrderVo orderVo) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        orderMapper.addOrder(po);
    }

    public void addOrderList(List<OrderVo> orderVoList) throws Exception {
        List<OrderPo> poList = BeanCopy.copyList(orderVoList, OrderPo.class,
                BeanCopy.COPYALL);
        orderMapper.addOrderList(poList);
    }

    public void delOrderById(String orderId) throws Exception {
        orderMapper.delOrderById(orderId);
    }

    public void delOrderByIds(List<String> ids) throws Exception {
        orderMapper.delOrderByIds(ids);
    }

    public void delOrderByObj(OrderVo orderVo) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        orderMapper.delOrderByObj(po);
    }

    public void saveOrder(OrderVo orderVo) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        orderMapper.saveOrder(po);
    }

    public void saveOrderList(List<OrderVo> orderVoList) throws Exception {
        List<OrderPo> poList = BeanCopy.copyList(orderVoList, OrderPo.class,
                BeanCopy.COPYALL);
        orderMapper.saveOrderList(poList);
    }

    public int updateByOrderId(OrderVo orderVo, String orderId)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByOrderId(po, orderId);
    }

    public int updateByCoachId(OrderVo orderVo, Long coachId) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCoachId(po, coachId);
    }

    public int updateByCourseId(OrderVo orderVo, String courseId)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCourseId(po, courseId);
    }

    public int updateByPrice(OrderVo orderVo, Integer price) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByPrice(po, price);
    }

    public int updateByLearnAddr(OrderVo orderVo, String learnAddr)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByLearnAddr(po, learnAddr);
    }

    public int updateByStudentId(OrderVo orderVo, Long studentId)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByStudentId(po, studentId);
    }

    public int updateByDltype(OrderVo orderVo, Integer dltype) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByDltype(po, dltype);
    }

    public int updateByLge(OrderVo orderVo, Double lge) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByLge(po, lge);
    }

    public int updateByLae(OrderVo orderVo, Double lae) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByLae(po, lae);
    }

    public int updateByStuAddr(OrderVo orderVo, String stuAddr)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByStuAddr(po, stuAddr);
    }

    public int updateByPstart(OrderVo orderVo, Date pstart) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByPstart(po, pstart);
    }

    public int updateByPend(OrderVo orderVo, Date pend) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByPend(po, pend);
    }

    public int updateByClzNum(OrderVo orderVo, Integer clzNum) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByClzNum(po, clzNum);
    }

    public int updateByOrderState(OrderVo orderVo, Integer orderState)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByOrderState(po, orderState);
    }

    public int updateByRstart(OrderVo orderVo, Date rstart) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByRstart(po, rstart);
    }

    public int updateByRend(OrderVo orderVo, Date rend) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByRend(po, rend);
    }

    public int updateByCstart(OrderVo orderVo, Date cstart) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCstart(po, cstart);
    }

    public int updateByCend(OrderVo orderVo, Date cend) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCend(po, cend);
    }

    public int updateByPayState(OrderVo orderVo, Integer payState)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByPayState(po, payState);
    }

    public int updateByNeedTrans(OrderVo orderVo, Integer needTrans)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByNeedTrans(po, needTrans);
    }

    public int updateByTransState(OrderVo orderVo, Integer transState)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByTransState(po, transState);
    }

    public int updateByPayId(OrderVo orderVo, Integer payId) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByPayId(po, payId);
    }

    public int updateByGtime(OrderVo orderVo, Date gtime) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByGtime(po, gtime);
    }

    public int updateByAtime(OrderVo orderVo, Date atime) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByAtime(po, atime);
    }

    public int updateByOtype(OrderVo orderVo, Integer otype) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByOtype(po, otype);
    }

    public int updateByCoorder(OrderVo orderVo, String coorder)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCoorder(po, coorder);
    }

    public int updateByCcid(OrderVo orderVo, Integer ccid) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCcid(po, ccid);
    }

    public int updateByUnitPrice(OrderVo orderVo, Integer unitPrice) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByUnitPrice(po, unitPrice);
    }

    public int updateByTransPrice(OrderVo orderVo, Integer transPrice) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByTransPrice(po, transPrice);
    }

    public int updateByPlaceId(OrderVo orderVo, Integer placeId) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByPlaceId(po, placeId);
    }

    public int updateByTransName(OrderVo orderVo, String transName) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByTransName(po, transName);
    }

    public int updateByCarId(OrderVo orderVo, Integer carId) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCarId(po, carId);
    }

    public int updateByCarName(OrderVo orderVo, String carName) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCarName(po, carName);
    }

    public int updateByCarImg(OrderVo orderVo, String carImg) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCarImg(po, carImg);
    }

    public int updateByCarNo(OrderVo orderVo, String carNo) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCarNo(po, carNo);
    }

    public int updateByInsId(OrderVo orderVo, Integer insId) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByInsId(po, insId);
    }

    public int updateByInsPrice(OrderVo orderVo, Integer insPrice) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByInsPrice(po, insPrice);
    }

    public int updateByInsName(OrderVo orderVo, String insName) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByInsName(po, insName);
    }

    public int updateByCoachName(OrderVo orderVo, String coachName) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCoachName(po, coachName);
    }

    public int updateByCoachImg(OrderVo orderVo, String coachImg) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCoachImg(po, coachImg);
    }

    public int updateByCoachMobile(OrderVo orderVo, String coachMobile) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCoachMobile(po, coachMobile);
    }

    public int updateByCoachStar(OrderVo orderVo, Integer coachStar) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCoachStar(po, coachStar);
    }

    public int updateByStuName(OrderVo orderVo, String stuName) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByStuName(po, stuName);
    }

    public int updateByStuImg(OrderVo orderVo, String stuImg) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByStuImg(po, stuImg);
    }

    public int updateByStuMobile(OrderVo orderVo, String stuMobile) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByStuMobile(po, stuMobile);
    }

    public int updateByCourseName(OrderVo orderVo, String courseName) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        return orderMapper.updateByCourseName(po, courseName);
    }

    public int updateNotNullByObject(OrderVo orderVo, OrderVo search) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        OrderPo searchPo = BeanCopy.copyAll(search, OrderPo.class);
        return orderMapper.updateNotNullByObject(po, searchPo);
    }

    public int updateAllByObject(OrderVo orderVo, OrderVo search)
            throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        OrderPo searchPo = BeanCopy.copyAll(search, OrderPo.class);
        return orderMapper.updateAllByObject(po, searchPo);
    }

    public OrderVo queryOrderById(String orderId, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = orderMapper.queryOrderById(orderId, postSql, sqlFileld);
        OrderVo vo = null;
        if (po != null) {
        	 //查询上课人数
            CoachClassQuery coachclassQuery = new CoachClassQuery();
            coachclassQuery.setSqlPost(" limit 1");
            Integer bookSum = coachClassService.queryBookNumByOrderCcId(orderId, po.getCcid(), po.getOtype(), coachclassQuery);
            vo = BeanCopy.copyAll(po, OrderVo.class);
            vo.setBookSum(bookSum);
            this.setTimeLeft(vo);
        }
        return vo;
    }

    public List<OrderVo> queryOrderByIds(List<String> ids, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryOrderByIds(ids, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        for (OrderVo one : voList) {
            this.setTimeLeft(one);
        }
        return voList;
    }

    public List<OrderVo> queryByObjectAnd(OrderVo orderVo, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByObjectAnd(po, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        for (OrderVo one : voList) {
            this.setTimeLeft(one);
        }
        return voList;
    }
    
    public List<OrderVo> queryByObjectAndNew(OrderVo orderVo, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByObjectAndNew(po, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        for (OrderVo one : voList) {
            this.setTimeLeft(one);
        }
        return voList;
    }

    public List<OrderVo> queryByObjectOr(OrderVo orderVo, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByObjectOr(po, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByOrderId(String orderId, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByOrderId(orderId, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCoachId(Long coachId, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCoachId(coachId, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCourseId(String courseId, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCourseId(courseId, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByPrice(Integer price, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByPrice(price, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByLearnAddr(String learnAddr,
                                          OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByLearnAddr(learnAddr, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByStudentId(Long studentId, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByStudentId(studentId, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByDltype(Integer dltype, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByDltype(dltype, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByLge(Double lge, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByLge(lge, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByLae(Double lae, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByLae(lae, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByStuAddr(String stuAddr, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByStuAddr(stuAddr, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByPstart(Date pstart, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByPstart(pstart, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByPend(Date pend, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByPend(pend, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByClzNum(Integer clzNum, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByClzNum(clzNum, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByOrderState(Integer orderState,
                                           OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByOrderState(orderState, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByRstart(Date rstart, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByRstart(rstart, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByRend(Date rend, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByRend(rend, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCstart(Date cstart, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCstart(cstart, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCend(Date cend, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCend(cend, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByPayState(Integer payState, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByPayState(payState, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByNeedTrans(Integer needTrans,
                                          OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByNeedTrans(needTrans, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByTransState(Integer transState,
                                           OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByTransState(transState, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByPayId(Integer payId, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByPayId(payId, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByGtime(Date gtime, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByGtime(gtime, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByAtime(Date atime, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByAtime(atime, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByOtype(Integer otype, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByOtype(otype, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCoorder(String coorder, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCoorder(coorder, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCcid(Integer ccid, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCcid(ccid, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByUnitPrice(Integer unitPrice, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByUnitPrice(unitPrice, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByTransPrice(Integer transPrice, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByTransPrice(transPrice, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByPlaceId(Integer placeId, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByPlaceId(placeId, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByTransName(String transName, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByTransName(transName, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCarId(Integer carId, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCarId(carId, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCarName(String carName, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCarName(carName, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCarImg(String carImg, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCarImg(carImg, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCarNo(String carNo, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCarNo(carNo, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByInsId(Integer insId, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByInsId(insId, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByInsPrice(Integer insPrice, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByInsPrice(insPrice, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByInsName(String insName, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByInsName(insName, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCoachName(String coachName, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCoachName(coachName, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCoachImg(String coachImg, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCoachImg(coachImg, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCoachMobile(String coachMobile, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCoachMobile(coachMobile, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCoachStar(Integer coachStar, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCoachStar(coachStar, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByStuName(String stuName, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByStuName(stuName, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByStuImg(String stuImg, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByStuImg(stuImg, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByStuMobile(String stuMobile, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByStuMobile(stuMobile, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByCourseName(String courseName, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        List<OrderPo> poList = orderMapper.queryByCourseName(courseName, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByPstart(OrderVo orderVo, OrderQuery orderQuery) throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByNew0(po, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByRend(OrderVo orderVo, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByNew1(po, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByNew2(OrderVo orderVo, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByNew2(po, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByNew3(OrderVo orderVo, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByNew3(po, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByNew4(OrderVo orderVo, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByNew4(po, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByNew5(OrderVo orderVo, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByNew5(po, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByNew6(OrderVo orderVo, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByNew6(po, postSql, sqlFileld);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    public List<OrderVo> queryByNew7(OrderVo orderVo, OrderQuery orderQuery)
            throws Exception {
        String postSql = orderQuery.getSqlPost();
        String sqlFileld = orderQuery.getSqlField();
        if (postSql == null) {
            postSql = "";
        }
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByNew7(po, postSql, sqlFileld);
        List<OrderVo> voList = BeanCopy.copyList(poList, OrderVo.class,
                BeanCopy.COPYALL);
        return voList;
    }

    public int getCoachPrice(Date pstart, Date pend, int cityId, String courseId,
                             int colid, int calid) throws Exception {
        // 防止异常使得价格等于0
        int price = 999999;
        // 1。获取基础价格，
        BasePriceVo bp = new BasePriceVo();
        bp.setCityId(cityId);
        bp.setColid(colid);
        bp.setCourseId(Integer.valueOf(courseId));
        List<BasePriceVo> vo = basePriceService.queryByObjectAnd(bp,
                new BasePriceQuery());
        if (vo == null || vo.isEmpty()) {
            throw new Exception("base price is not exists when cityId=" + cityId + ",courseId=" + courseId + ",colid=" + colid + ",calid=" + calid);
        }
        price = vo.get(0).getPrice();
        // 2.获取时段价格
        TimeRateVo tr = new TimeRateVo();
        tr.setYint(DateUtil.getYear(pstart));
        tr.setMint(DateUtil.getMonth(pstart));
        tr.setDstart(DateUtil.getDay(pstart));
        tr.setDend(DateUtil.getDay(pend));
        tr.setWstart(DateUtil.getWeek(pstart));
        tr.setWend(DateUtil.getWeek(pend));
        tr.setHstart(DateUtil.getHour(pstart));
        tr.setHend(DateUtil.getHour(pend));
        List<TimeRateVo> timeRate = timeRateService.queryByDate(tr,
                new TimeRateQuery());
        if (timeRate == null || timeRate.isEmpty()) {
            timeRate = timeRateService.queryByWeek(tr, new TimeRateQuery());
        }
        if (timeRate == null || timeRate.isEmpty()) {
            throw new Exception("Time rate not eixts when cityId=" + cityId + ",courseId=" + courseId + ",colid=" + colid + ",calid=" + calid);
        }
        int trate = timeRate.get(0).getPrate();
        // 3.获取车的价格
        List<CarLevelVo> carlevel = carLevelService.queryByCalid(calid,
                new CarLevelQuery());
        if (carlevel == null || carlevel.isEmpty()) {
            throw new Exception("car rate not eixts when cityId=" + cityId + ",courseId=" + courseId + ",colid=" + colid + ",calid=" + calid);
        }
        int crate = carlevel.get(0).getPrate();
        price *= trate * crate / 100 / 100;
        return price;
    }

    /**
     * 订单取消接口
     *
     * @param orderId           ：订单id
     * @param retype            :取消原因类型：1.懒得等，2.订单错误，2.我时间冲突 3.对方态度不好, 4.对方有事
     * @param reseaon           ：原因描述
     * @param ucancel           ：取消发起人类型：1代表教练，2代表学员
     * @param tokenId
     * @param type:0代表据单，1代表取消单
     * @return
     */
    public String cancelOrder(String orderId, Integer retype, String reseaon,
                              Integer ucancel, String tokenId, int type) throws Exception {
    	return cancelOrder(orderId, retype, reseaon, ucancel, tokenId, type, false);
    }
    	
   public String cancelOrder(String orderId, Integer retype, String reseaon,
                Integer ucancel, String tokenId, int type,boolean isCMS) throws Exception {
        // 1.修改状态
        OrderVo orderVo = this.queryOrderById(orderId, new OrderQuery());
        if (orderVo == null) {
            log.error(orderId + " can't cancel because of order not eixst.");
            return ResultCode.ERRORCODE.ORDER_NOTEXIST;
        }
        if(isCMS){
        	log.info("NOT CHECK STATE because of CMS CANCEL="+orderVo);
        } else if (orderVo.getOrderState() != OrderConstant.ORDERSTATE.GIVEORDER && orderVo.getOrderState() != OrderConstant.ORDERSTATE.ACCEPTORDER) {
            log.error(orderId + " can't cancel because of order state error=" + orderVo.getOrderState());
            return ResultCode.ERRORCODE.ORDER_NO_CANCEL;
        }
        //拒单
        if (type == 0) {
            orderVo.setOrderState(OrderConstant.ORDERSTATE.REFUSEORDER);
        } else {
            orderVo.setOrderState(OrderConstant.ORDERSTATE.CANCELD);
            //取消预约定订单有时间限制
            if ((!isCMS)&&(orderVo.getOtype() == OrderConstant.OTYPE.BOOKORDER)) {
                //a、预约10：00以前的课程则当天凌晨0：00后不得取消
                long timeStart = TimeUtil.calcDistanceMillis(DateUtil.getDateStart(new Date()), orderVo.getPstart());
                //b、预约10：00后的课程距上课时间三小时内不得取消
                long time = TimeUtil.calcDistanceMillis(new Date(), orderVo.getPstart());
                if (time < 10800000 || timeStart < 36000000) {
                    log.info(orderVo + " can't cancel booked order beause time=" + time + ",timeStart=" + timeStart);
                    //20160823新增学员自主预约订单，用户取消后需要支付补偿费用
                    if(orderVo.getAllowance() != 0){
                    	return ResultCode.ERRORCODE.ORDER_TIMEOUT_CANCEL;
                    }else {
                    	return ResultCode.ERRORCODE.ORDER_TIMEOUT_CANCEL;
                    }
                }
            }
        }
        saveOrder(orderVo);

        // 2.支付处理
        int pstate = orderVo.getPayState();
        int restate = OrderConstant.PAYSTATE.WAITPAY;
        if (pstate == OrderConstant.PAYSTATE.HASPAY) {
            // 退款处理
            // 退款状态处理
//			restate = OrderConstant.PAYSTATE.HASPAY;
        }
        //2.1优惠券处理回退处理
        if (orderVo.getCoupon() != null) {
            couponService.recoverCoupon(orderVo);
        }

        // 3.添加取消记录
        OrderCancelVo oc = new OrderCancelVo();
        oc.setCltime(new Date());
        oc.setOrderId(orderId);
        oc.setPstate(restate);
        oc.setReseaon(reseaon);
        oc.setRetype(retype);
        oc.setUcancel(ucancel);
        orderCancelService.addOrderCancel(oc);
        //4.预约课程需要取消
        if (orderVo.getOtype() == OrderConstant.OTYPE.BOOKORDER && orderVo.getAllowance() ==0 ) { // 20160824 allowance用于区分自主预约订单
            //排班取消
            CoachClassVo cc = coachClassService.queryCoachClassById(orderVo.getCcid(), new CoachClassQuery());
            cc.setBookNum(cc.getBookNum() - 1);
            coachClassService.saveCoachClass(cc);
            //预约取消
            PlantClassVo pc = new PlantClassVo();
            pc.setIsdel(OrderConstant.ISDEL.DELETE);
            plantClassService.updateByOrderId(pc, orderId);
            
           // 20160805 我的教练增加缓存
           String keypcList =  RedisKeys.REDISKEY.COACH_CLASS_LIST + orderVo.getCoachId() + ".studentId." + orderVo.getStudentId(); 
    	   redisUtil.delete(keypcList);
    		
            //现约取消排班
        } else {
            List<CoachClassVo> cc = coachClassService.queryByOrderId(orderVo.getOrderId(), new CoachClassQuery());
            if (cc != null && !cc.isEmpty()) {
                BeanCopy.setListField(cc, "isdel", OrderConstant.ISDEL.DELETE);
                cc.get(0).setIsdel(OrderConstant.ISDEL.DELETE);
                coachClassService.saveCoachClassList(cc);
            }
        }

        //成功发送取消定订单消息
        Message msg = new Message();
        msg.setKeys(orderId);
        msg.setTopic(orderProducer.getCreateTopicKey());
        //拒单属于取消的一种,但是消息需要区分 type=0为拒绝
        if (type == 0) {
            msg.setTags(OrderConstant.RMQTAG.REFUSEORDER);
        } else {
            msg.setTags(OrderConstant.RMQTAG.CANCELORDER);
        }
        //临时存储取消用户类型,用于后面消息判断
        orderVo.setTimeLeft(ucancel);
        msg.setBody(SerializableUtil.serialize(orderVo));
        orderProducer.send(msg);
        //订单取消成功，则可能需要改变学员和教练的状态
		Student student=null;
		Coach coach=null;
			//2.取消成功，判断学员是否需要进行状态改变，如需则改变
			student = studentManager.getStudentInfo(orderVo.getStudentId());
			if(ReqConstants.STUDNET_PREPARE_START_CLASS == (int)student.getEventState() && orderId.equals(student.getEventDesc())){
				student.setEventState((byte)ReqConstants.STUDENT_STATUS_OFF_CLASS);
				long result=studentManager.updateStudent(student);
				log.debug(result+","+orderId+" all cancel with code=0, so change man state of student="+student);
			} else {
				log.debug(orderId+" all cancel with code=0, but not current ORDER SO NOT change man state of student="+student);
			}
			//3.取消成功，判断教练是否需要进行状态改变，如需则改变
			coach = coachManager.getCoachInfo(orderVo.getCoachId());
			//对于教练，只有现约会改状态
			if(ReqConstants.COACH_STATUS_PREPARE_CLASS == (int)coach.getWstate() && orderId.equals(coach.getEventDesc()) 
					&& orderVo.getOtype()==OrderConstant.OTYPE.NOWORDER) {
				coach.setWstate(ReqConstants.COACH_STATUS_ON_WORK);
				long result=coachManager.updateCoach(coach);
				log.debug(result+","+orderId+" all cancel with code=0, so change man state of coach="+coach);
			} else {
				log.debug(orderId+" cancel with code=0,but not current ORDER SO NOT change man state of coach="+coach);
			}
        return ResultCode.ERRORCODE.SUCCESS;
    }
   public String cmsCancelOrder(String orderId,long userId) throws Exception {
   		String code = cancelOrder(orderId, 99, "客服"+userId+"取消了该订单.",
				OrderConstant.USETYPE.MANGER, String.valueOf(userId),1,true);
		return code;
   }
   
    /**
     * 学生缺课
     *
     * @param orderId
     * @return 以下两种错误
     * ResultCode.ERRORCODE.ORDER_NOTEXIST
     * ResultCode.ERRORCODE.ORDER_NO_CLASS
     */
    public ReqResult missClass(String orderId) {
        ReqResult r = ReqResult.getSuccess();
        try {
            OrderVo order = this.queryOrderById(orderId, new OrderQuery());
            if (order == null) {
                r.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
                return r;
            }
            if (order.getOrderState() != OrderConstant.ORDERSTATE.INCLASS && order.getOrderState() != OrderConstant.ORDERSTATE.COMPLETE) {
                r.setCode(ResultCode.ERRORCODE.ORDER_NO_CLASS);
                return r;
            }
            //1.更新订单状态
            order.setOrderState(OrderConstant.ORDERSTATE.MISSCLASS);
            this.saveOrder(order);
            //2.更新计划课程表状态
            PlantClassVo search = new PlantClassVo();
            search.setOrderId(orderId);
            search.setStudentId(order.getStudentId());
            search.setStuName(order.getStuName());
            search.setStuImg(order.getStuImg());
            List<PlantClassVo> pList = plantClassService.queryByObjectAnd(search, new PlantClassQuery());
            if (pList != null && !pList.isEmpty()) {
                PlantClassVo pc = pList.get(0);
                pc.setIsdel(OrderConstant.ORDERSTATE.MISSCLASS);
                plantClassService.savePlantClass(pc);
            }
            //3.如果需要，可能需要退款
            //4.成功发送缺课推送
            JpushMsg jmsg = new JpushMsg();
            jmsg.setAlter("您缺课了");
            jmsg.setUserId(order.getStudentId());
            jmsg.setOperate(JpushConstant.OPERATE.STUMISSCLASS);
            jmsg.setOrderId(order.getOrderId());
            Message jpush = new Message();
            jpush.setKeys(order.getOrderId());
            jpush.setTopic(jpushProducer.getCreateTopicKey());
            jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
            jpush.setBody(SerializableUtil.serialize(jmsg));
            jpushProducer.send(jpush);
        } catch (Exception e) {
            log.error(orderId + " miss class Exception=" + e.getMessage(), e);
            r = ReqResult.getFailed();
        }
        return r;
    }

    /*
     * 恢复被抢占的预约排班
     */
    private boolean recouveryCoachClass(OrderVo orderVo) throws Exception{
    	CoachClassVo cc = coachClassService.queryCoachClassById(orderVo.getCcid(), new CoachClassQuery());
    	if (cc != null && cc.getCtype() == OrderConstant.OTYPE.BOOKORDER) {
    		CoachClassVo newcc = new CoachClassVo();
            newcc.setBookNum(cc.getBookNum() - 1);
            newcc.setCcid(cc.getCcid());
            if (cc.getOrderId() != null && cc.getOrderId().equals(orderVo.getOrderId()))
            	newcc.setOrderId("");
            int num = coachClassService.updateByCcid(newcc, cc.getCcid());
            if (num > 0)
            	return true;
    	}
    	return false;
    }
    
    public String bookOrder(OrderVo orderVo) throws Exception {
    	
        //已约查询
        PlantClassVo search = new PlantClassVo();
        search.setCcid(orderVo.getCcid());
        search.setStudentId(orderVo.getStudentId());
        List<PlantClassVo> haspc = plantClassService.queryByObjectAnd(search, new PlantClassQuery());
        if (haspc != null && !haspc.isEmpty()) {
            log.error(orderVo.getOrderId() + " can't book because of class has aready booked=" + haspc.get(0));
            return ResultCode.ERRORCODE.ORDER_NOT_SECOND;
        }
        //1.判断教练是否空闲够
        CoachClassVo cc = coachClassService.queryCoachClassById(orderVo.getCcid(), new CoachClassQuery());
        if (cc == null || cc.getCtype() != OrderConstant.OTYPE.BOOKORDER || cc.getBookNum() >= cc.getMaxNum()) {
            log.error("can't book because of coach class is full=" + cc);
            return ResultCode.ERRORCODE.ORDER_FULL;
        }

        //2.排班获取订单信息
        orderVo.setPstart(cc.getRstart());
        orderVo.setPend(cc.getRend());
        orderVo.setRstart(cc.getRstart());
        orderVo.setRend(cc.getRend());
        orderVo.setClzNum(1);
        orderVo.setGtime(new Date());
        orderVo.setCoachId(cc.getCoachId());
        //科目三的地点
        orderVo.setPlaceLae(cc.getLae());
        orderVo.setPlaceLge(cc.getLge());
        orderVo.setLearnAddr(cc.getPlaceName());

        //判断学生是否空闲
        OrderVo stuBuz = coachClassService.isStudentIdle(orderVo.getStudentId(), null, orderVo.getPstart(), orderVo.getPend(), true);
        if (stuBuz != null) {
            log.error(stuBuz + " student is buz while place a book order:" + orderVo);
            return ResultCode.ERRORCODE.ORDER_STU_BUZ;
        }
        long start = TimeUtil.calcDistanceMillis(new Date(), cc.getRstart());
        if (start < 0) {
            log.error("can't book because of class is start=" + cc);
            return ResultCode.ERRORCODE.ORDER_NO_BOOK;
        }
        //1.添加教练信息
        Coach c = coachManager.getCoachInfo(orderVo.getCoachId());
        if (c != null) {
            orderVo.setCoachImg(c.getHeadIcon());
            orderVo.setCoachMobile(c.getPhoneNum());
            orderVo.setCoachStar(c.getStarLevel());
            orderVo.setCoachName(c.getName());
        }
        //2.添加学生信息
        Student s = studentManager.getStudentInfo(orderVo.getStudentId());
        if (s != null) {
            orderVo.setStuImg(s.getHeadIcon());
            orderVo.setStuMobile(s.getPhoneNum());
            orderVo.setStuName(s.getName());
        }
        //3.添加汽车信息
        Integer carId = orderVo.getCarId();
        if (carId == null) {
            carId = cc.getCarId();
        }
        Car car = carManager.getCarInfo(carId);
        if (car != null) {
            //前端没有传取出后需要保存
            orderVo.setCarId(car.getCarId());
            orderVo.setCarName(car.getCarType());
            orderVo.setCarImg(String.valueOf(car.getCarLevel()));
            orderVo.setCarNo(car.getCarNo());
        }
        //4.添加课程信息
        CoursenewVo course = fileManager.getCoursenewBycourseid(Integer.valueOf(orderVo.getCourseId()));
        if (course != null) {
            orderVo.setCourseName(course.getCoursenewname());
        }
        //5.添加训练场信息
        Integer placeId = orderVo.getPlaceId();
        if (placeId == null) {
            placeId = cc.getPlaceId();
            orderVo.setPlaceId(placeId);
        }
        if (placeId != null) {
            Trfield tr = trfieldManager.getTrfieldInfo(placeId);
            if (tr != null) {
                orderVo.setLearnAddr(tr.getName());
                //这连个字段暂时被学生使用了，所以不能使用
//						orderVo.setLge(tr.getLge());
//						orderVo.setLae(tr.getLae());
            }
        }

        //抢占班次
        CoachClassVo newcc = new CoachClassVo();
        newcc.setBookNum(cc.getBookNum() + 1);
        newcc.setCcid(cc.getCcid());
        newcc.setOrderId(orderVo.getOrderId());
        int num = coachClassService.updateByCcid(newcc, cc.getCcid());
        //这个结果不准确，需要再看
        if (num < 1) {
            return ResultCode.ERRORCODE.ORDER_FULL;
        }
        
        String oneday=TimeUtil.getDateFormat(cc.getCstart(),"yyyy-MM-dd");
        String onekey=this.getClass().getSimpleName()+"_"+oneday+"_"+orderVo.getCoachId()+"_"+OrderConstant.ISDEL.NOTDELETE+"_"+OrderConstant.OTYPE.BOOKORDER+"_withprice";
        redisUtil.delete(onekey); //删除原缓存
        //不需要接单
        orderVo.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
        orderVo.setOtype(OrderConstant.OTYPE.BOOKORDER);
        orderVo.setAtime(orderVo.getGtime());
        //老学员自动支付：即不需要支付
        try {
            if (1 == s.getIsImport()) {
            	int mycoachNum = mycoachesManager.countById(orderVo.getStudentId().intValue(), orderVo.getCoachId().intValue());
            	//add by devil 20161105
            	if(s.getPackageId()!=null && s.getPackageId()!=0 && mycoachNum > 0){
        			
        			int ttid=s.getPackageId();
        			ReqResult reqResult=schoolService.getPackageById(String.valueOf(ttid));
                    WechatEnrollPackage enrollPackage=(WechatEnrollPackage)reqResult.getResult().get("data");
                    if(enrollPackage.getCstate()==2){ // cstate=2 bm班 班别审核通过
                    	List<OrderVo> list = searchBmClass(s.getStudentId().intValue(),cc.getCoachId().intValue());
        				int userdHours=0;
        				for(int i=0;i<list.size();i++){
        					OrderVo vo=list.get(i);
        					Date pstart=vo.getPstart();
        					Date pend=vo.getPend();
        					long courseHours = ((pend.getTime() - pstart.getTime()) / 1000) % (24 * 3600) / 3600;
        					int maxNum=vo.getClzNum(); 
        					userdHours+=courseHours/maxNum;
        				}
        				
        				int hours = enrollPackage.getHours();
        				int bmHour=s.getBmHour();
        				int left = hours - userdHours-bmHour;
        				long usednum=((cc.getRend().getTime()-cc.getRstart().getTime())/1000)%(24*3600)/3600;//	
        				if(left<=0 || left<usednum){
	            			if(left>0 && left<usednum){
	            				orderVo.setPayState(OrderConstant.PAYSTATE.WAITPAY);
	            			}else{
	            				orderVo.setPayState(OrderConstant.PAYSTATE.WAITPAY);
	            			}
	            			
	            		}else{//还有剩免费课时
	            			orderVo.setPayState(OrderConstant.PAYSTATE.AUTOPAYE);
	            		}
        				
        	            	
                    }else{//不是bm班
                    	orderVo.setPayState(OrderConstant.PAYSTATE.AUTOPAYE);
                    }
                   
        		} else if (mycoachNum > 0) {//不是bm班
        			orderVo.setPayState(OrderConstant.PAYSTATE.AUTOPAYE);
        		} else {
        			orderVo.setPayState(OrderConstant.PAYSTATE.WAITPAY);
        		}
            	
                
                //理论上目前都是自动支付，所以非自动支付，记录错误日志。后续如规格有变化，去掉即可
            } else {
                log.error(orderVo + " student is not import,so DONOT auto pay!" + s);
            }
        } catch (Exception e) {
            log.error("student auto pay but type get error:" + e.getMessage(), e);
        }
        //5.计算总价钱
        if (orderVo.getInsPrice() == null) {
            orderVo.setInsPrice(0);
        }
        if (orderVo.getTransPrice() == null) {
            orderVo.setTransPrice(0);
        }
        int total = orderVo.getPrice() + orderVo.getInsPrice() + orderVo.getTransPrice();
        orderVo.setPriceTotal(total);
        orderVo.setPayTotal(total);
        
        // 7. 优惠券使用
        if (orderVo.getCoupon() != null && orderVo.getCoupon() > 0) {
            int money = couponService.useCoupon(orderVo.getCoupon(), orderVo,  true);
            // 优惠券无法使用
            if (money < 0) {
            	// 20161219 恢复抢占班次
            	recouveryCoachClass(orderVo);
                return ResultCode.ERRORCODE.ORDER_COUPON_CANNOTUSE;
            } else {
                //还需要部分支付
                if (total > money) {
                    orderVo.setPayTotal(total - money);
                    orderVo.setCouponTotal(money);
                    // 无需付钱则设置为已经支付
                } else {
                    orderVo.setPayTotal(0);
                    orderVo.setCouponTotal(total);
                    orderVo.setPayState(OrderConstant.PAYSTATE.HASPAY);
                    orderVo.setPayTime(new Date());
                    //20160719全额抵扣券的时候，也需要增加资金结算记录。//20160720 不能在这里增加，因为学员还有可能在教练上课前取消订单。应该在下课时增加资金结算记录。
                    //moneyManager.handleOrderHasPayedMoneyFlow(orderVo);
                }
            }
        }
      
    	// 20161116如果余额不足，不能约课
        // 20161219	若校验失败，则恢复之前抢占的排班，并兼容优惠券抵扣之后的价格
		Student student = studentManager.getStudentInfo(orderVo.getStudentId());
		if(student.getIsImport() == (byte)0) {
			StudentAccount sa = studentManager.getStudentMoney(orderVo.getStudentId());
			int money = 0;
			if (null != sa) {
				money = sa.getMoney();
			}
			if (money <  orderVo.getPayTotal()) {
				recouveryCoachClass(orderVo);
				couponService.recoverCoupon(orderVo);
				return ResultCode.ERRORCODE.BALANCE_IS_INSUFFICIENT;
			}
		}
		
        //3.添加教练学员关系
        PlantClassVo pc = new PlantClassVo();
        pc.setCcid(orderVo.getCcid());
        pc.setCoachId(orderVo.getCoachId());
        pc.setGtime(orderVo.getGtime());
        pc.setOrderId(orderVo.getOrderId());
        pc.setStudentId(orderVo.getStudentId());
        pc.setStuImg(s.getHeadIcon());
        pc.setStuName(s.getName());
        pc.setStuMobile(s.getPhoneNum());
        if(orderVo.getPayState() == null || orderVo.getPayState().intValue() != OrderConstant.PAYSTATE.AUTOPAYE){ //只有订单设置为老学员自动支付的才不需要设置价格
        	pc.setPrice(total);	//约单时把价格保存便于教练查询排版使用
        }
        
        //20160929新增bm班价格
        if(s.getPackageId()!=null && s.getPackageId()!=0){  //关联了班别的bm班
        	int packageId=s.getPackageId();
        	
        	 ReqResult reqResult=schoolManager.getPackageById(String.valueOf(packageId));
	         WechatEnrollPackage enrollPackage=(WechatEnrollPackage)reqResult.getResult().get("data");
	         if(enrollPackage!=null){
	        	 
	            List<OrderPo> list = orderMapper.searchBmClass(s.getStudentId().intValue(),orderVo.getCoachId().intValue());
	 			int userdHours=0;
	 			for(int i=0;i<list.size();i++){
	 				OrderPo vo=list.get(i);
	 				Date pstart=vo.getPstart();
					Date pend=vo.getPend();
					long courseHours = ((pend.getTime() - pstart.getTime()) / 1000) % (24 * 3600) / 3600;
					int maxNum=vo.getClzNum();
					userdHours+=courseHours/maxNum;
	 			}
	        	 int cstate=enrollPackage.getCstate();
		         int hours=enrollPackage.getHours()==null?0:enrollPackage.getHours();
		         int bmHour=s.getBmHour();
		         int left=hours-userdHours-bmHour;
		         Date cstart=orderVo.getPstart();
          		 Date cend=orderVo.getPend();
          		 long num2=((cend.getTime()-cstart.getTime())/1000)%(24*3600)/3600;
		         if(cstate==2 && (left<=0 || left<num2)){ //cstate=2 审核通过
		        	 pc.setPrice(total);  	//约单时把价格保存便于教练查询排版使用
		         }
	         }
        }
        
        plantClassService.addPlantClass(pc);
        saveOrder(orderVo);
        // 20160805 我的教练增加缓存
        String keypcList =  RedisKeys.REDISKEY.COACH_CLASS_LIST + orderVo.getCoachId() + ".studentId." + orderVo.getStudentId(); 
        List<PlantClassVo> pcList = new ArrayList<PlantClassVo>();
        pcList.add(pc);
		redisUtil.set(keypcList, pcList);
        
        //成功发送预定单消息
        Message msg = new Message();
        msg.setKeys(orderVo.getOrderId());
        msg.setTopic(orderProducer.getCreateTopicKey());
        msg.setTags(OrderConstant.RMQTAG.COMMITORDER);
        msg.setBody(SerializableUtil.serialize(orderVo));
        orderProducer.send(msg);
        return ResultCode.ERRORCODE.SUCCESS;
    }
    
    @Override
	public ReqResult bookStudentOrder(OrderVo orderVo) throws Exception {
    	ReqResult r = ReqResult.getSuccess();
    	saveOrder(orderVo);
        //3.添加教练学员关系
        PlantClassVo pc = new PlantClassVo();
        pc.setCcid(orderVo.getCcid());
        pc.setCoachId(orderVo.getCoachId());
        pc.setGtime(orderVo.getGtime());
        pc.setOrderId(orderVo.getOrderId());
        pc.setStudentId(orderVo.getStudentId());
        pc.setStuImg(orderVo.getStuImg());
        pc.setStuName(orderVo.getStuName());
        pc.setStuMobile(orderVo.getStuMobile());
        pc.setPrice(orderVo.getPriceTotal());
        plantClassService.addPlantClass(pc);
        //成功发送预定单消息
        Message msg = new Message();
        msg.setKeys(orderVo.getOrderId());
        msg.setTopic(orderProducer.getCreateTopicKey());
        msg.setTags(OrderConstant.RMQTAG.COMMITORDER);
        msg.setBody(SerializableUtil.serialize(orderVo));
        orderProducer.send(msg);
		return r;
	}

	public long getTimeleft(String orderId) throws Exception {
        OrderVo order = this.queryOrderById(orderId, new OrderQuery());
        long left = 0;
        if (order.getOrderState() == OrderConstant.ORDERSTATE.INCLASS) {
            left = TimeUtil.calcDistanceMillis(order.getPend(), new Date());
            if (left < 0) {
                left = 0;
            }
        }
        return left;
    }

    private void setTimeLeft(OrderVo vo) {
        long left = 0;
        try {
            if (vo.getOrderState() == OrderConstant.ORDERSTATE.ACCEPTORDER || vo.getOrderState() == OrderConstant.ORDERSTATE.INCLASS) {

                if (vo.getRend() != null) {
                    //为了容忍时序可能带来的问题，剩余时间增加2s，实际影响不大
                    left = TimeUtil.calcDistanceMillis(new Date(), vo.getRend()) + 2000;
                } else if (vo.getPend() != null) {
                    //为了容忍时序可能带来的问题，剩余时间增加2s，实际影响不大
                    left = TimeUtil.calcDistanceMillis(new Date(), vo.getPend()) + 2000;
                }
                if (left < 0) {
                    left = 0;
                }
            }
        } catch (Exception e) {
            log.error(vo + " setTimeLeft Exception:" + e.getMessage(), e);
        }
        vo.setTimeLeft(left);
    }

    public List<OrderVo> queryByObject(OrderVo orderVo, OrderExtQuery orderQuery) throws Exception {
        OrderPo po = BeanCopy.copyAll(orderVo, OrderPo.class);
        List<OrderPo> poList = orderMapper.queryByObject(po, orderQuery);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }

    /**
     * 返回给前台的教练必须完成的订单
     *
     * @param coachId
     * @param normalOrder:注意正常订单号全部放在list里面即可
     * @return
     */
    public WaitOrderVo getCoachWait(long coachId, WaitOrderVo normalOrder) throws Exception {
        WaitOrderVo wait = new WaitOrderVo();
        OrderExtQuery oequery = new OrderExtQuery();
        oequery.setQueryExt("and order_state in (4,6)");
        oequery.setorderBy("order by rstart desc");
        OrderVo search = new OrderVo();
        search.setCoachId(coachId);
        List<OrderVo> waitComment = queryByObject(search, oequery);
        if (waitComment != null && !waitComment.isEmpty()) {
            List<String> normalList = new ArrayList<String>();
            List<String> idList = new ArrayList<String>();
            if (normalOrder != null && normalOrder.getWaitCommentId() != null) {
                normalList.addAll(normalOrder.getWaitCommentId());
            }
            for (OrderVo one : waitComment) {
                if (!normalList.contains(one.getOrderId())) {
                    if (wait.getWaitComment() == null) {
                        wait.setWaitComment(one);
                    } else {
                        idList.add(one.getOrderId());
                    }
                }
            }
            if (!idList.isEmpty()) {
                wait.setWaitCommentId(idList);
            }
        }
        return wait;
    }

    /**
     * 返回给前台的学生必须完成的订单
     *
     * @param studentId
     * @param normalOrder:注意正常订单号全部放在list里面即可，对象字段请全部置空
     * @return
     */
    public WaitOrderVo getStudentWait(long studentId, WaitOrderVo normalOrder) throws Exception {
        WaitOrderVo wait = new WaitOrderVo();
        OrderExtQuery oequery = new OrderExtQuery();
        oequery.setQueryExt("and pay_state in (0,2,10) and order_state in (4,5,6,7,10)");
        OrderVo search = new OrderVo();
        search.setStudentId(studentId);
        List<OrderVo> waitPay = queryByObject(search, oequery);
        if (waitPay != null && !waitPay.isEmpty()) {
            List<String> normalList = new ArrayList<String>();
            List<String> idList = new ArrayList<String>();
            if (normalOrder != null && normalOrder.getWaitPayId() != null) {
                normalList.addAll(normalOrder.getWaitPayId());
            }
            for (OrderVo one : waitPay) {
                if (!normalList.contains(one.getOrderId())) {
                    if (wait.getWaitPay() == null) {
                        wait.setWaitPay(one);
                    } else {
                        idList.add(one.getOrderId());
                    }
                }
            }
            if (idList.isEmpty()) {
                wait.setWaitPayId(idList);
            }
        }
        return wait;
    }

    public OrderVo getStuLastBooked(long studentId) throws Exception {
        OrderQuery query = new OrderQuery();
        query.setGroupBy(" and UNIX_TIMESTAMP(pend) >= " + (System.currentTimeMillis() / 1000) + " and order_state in (2,3) ");
        query.setorderBy("order by pstart asc");
        query.setPageSize(1);
        OrderVo search = new OrderVo();
        search.setStudentId(studentId);
        search.setOtype(OrderConstant.OTYPE.BOOKORDER);
        //	search.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
        List<OrderVo> list = queryByObjectAnd(search, query);
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public OrderVo getCoachLastBooked(long coachId) throws Exception {
        OrderQuery query = new OrderQuery();
        //query.setGroupBy("and pstart > "+System.currentTimeMillis());
        query.setGroupBy(" and UNIX_TIMESTAMP(pend) >= " + (System.currentTimeMillis() / 1000) + " and order_state in (2,3) ");
        query.setorderBy("order by pstart asc");
        query.setPageSize(1);
        OrderVo search = new OrderVo();
        search.setCoachId(coachId);
        search.setOtype(OrderConstant.OTYPE.BOOKORDER);
        //search.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
        List<OrderVo> list = queryByObjectAnd(search, query);
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public String handlePlaceNow(OrderVo orderVo) throws Exception {
        String orderId = orderVo.getOrderId();
        
        //支付限制
        OrderExtQuery oequery = new OrderExtQuery();
        oequery.setQueryExt("and pay_state in (0,2) and order_state in (1,2,3,4,5,6,7)");
        oequery.setPageIndex(1);
        OrderVo search = new OrderVo();
        search.setStudentId(orderVo.getStudentId());
        List<OrderVo> waitPay = queryByObject(search, oequery);
        if (waitPay != null && !waitPay.isEmpty()) {
            log.error(waitPay.get(0).getOrderId() + " is not pay, so can't place a new order.");
            return ResultCode.ERRORCODE.ORDER_NO_GIVE;
        }
        
        //检查教练是否有为评价单
        WaitOrderVo waitOrderVo = getCoachWait(orderVo.getCoachId(), null);
        if(waitOrderVo != null && waitOrderVo.getWaitComment() != null){
        	log.error("coach "+orderVo.getCoachId()+" has no commnet order.");
        	return ResultCode.ERRORCODE.ORDER_COACH_BUZ;
        }
        
        //最大课时数时间判断
        if (orderVo.getClzNum() > 4) {
            log.error("can't set because maxNum>4:"
                    + orderVo);
            return ResultCode.ERRORCODE.ORDER_CLASSTIMEOUT;
        }
        // 如果没有时间默认为现在。
        if (orderVo.getPstart() == null) {
            orderVo.setPstart(new Date());
        }
        // 结束时间
        orderVo.setPend(DateUtil.dateAfterMinute(orderVo.getPstart(),
                OrderConstant.clztime * orderVo.getClzNum()));
        orderVo.setGtime(new Date());
        // 班次冲突查询
        //获取教练所有排班，不管是否有价格
        List<CoachClassVo> list = coachClassService.queryByCoachDateWithNoPrice(orderVo.getPstart(), orderVo.getCoachId(), OrderConstant.ISDEL.NOTDELETE, null, 1, 30);
        CoachClassVo buz = coachClassService.isCoachIdle(list, null, null, orderVo.getPstart(), orderVo.getPend(), false);
        if (buz != null) {
            log.error(orderVo.getCoachId() + " is not idle, so can't place a new order=" + buz);
            return ResultCode.ERRORCODE.ORDER_COACH_FULL;
        }
        //判断学生是否空闲
        OrderVo stuBuz = coachClassService.isStudentIdle(orderVo.getStudentId(), orderId, orderVo.getPstart(), orderVo.getPend(), false);
        if (stuBuz != null) {
            log.error(stuBuz + " student is buz while place a new order:" + orderVo);
            return ResultCode.ERRORCODE.ORDER_STU_BUZ;
        }
        //1.添加教练信息
        Coach c = coachManager.getCoachInfo(orderVo.getCoachId());
        String courses = c.getCourses();
        //订单逻辑:接单教练工作状态和出车课程判断
        if (c.getWstate() == ReqConstants.COACH_STATUS_ON_WORK && StringUtil.isNotNullAndNotEmpty(courses)) {
            List<String> clist = Arrays.asList(courses.split(","));
            if (!clist.contains(orderVo.getCourseId())) {
                log.debug(orderVo + " order courses not equal=" + courses);
                return ResultCode.ERRORCODE.ORDER_COACH_FULL;
            }
        } else {
            log.debug(orderVo + " order courses not equal empty=" + c);
            return ResultCode.ERRORCODE.ORDER_COACH_FULL;
        }
        orderVo.setCoachImg(c.getHeadIcon());
        orderVo.setCoachMobile(c.getPhoneNum());
        orderVo.setCoachStar(c.getStarLevel());
        orderVo.setCoachName(c.getName());
        //2.添加学生信息
        Student s = studentManager.getStudentInfo(orderVo.getStudentId());
        if (s != null) {
            orderVo.setStuImg(s.getHeadIcon());
            orderVo.setStuMobile(s.getPhoneNum());
            orderVo.setStuName(s.getName());
        }
        //3.添加汽车信息
        Car car = carManager.getCarInfo(orderVo.getCarId());
        if (car != null) {
            orderVo.setCarId(car.getCarId());
            orderVo.setCarName(car.getCarType());
            orderVo.setCarImg(String.valueOf(car.getCarLevel()));
            orderVo.setCarNo(car.getCarNo());
        }
        //4.添加课程信息
        CoursenewVo course = fileManager.getCoursenewBycourseid(Integer.valueOf(orderVo.getCourseId()));
        if (course != null) {
            orderVo.setCourseName(course.getCoursenewname());
            orderVo.setCourseType(course.getRemark());
        }
        //5.计算总价钱
        if (orderVo.getInsPrice() == null) {
            orderVo.setInsPrice(0);
        }
        if (orderVo.getTransPrice() == null) {
            orderVo.setTransPrice(0);
        }
        int total = orderVo.getPrice() + orderVo.getInsPrice() + orderVo.getTransPrice();
        orderVo.setPriceTotal(total);
        orderVo.setPayTotal(total);

        // 6.系统自动接单
        orderVo.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
        // 7. 优惠券使用
        if (orderVo.getCoupon() != null && orderVo.getCoupon() > 0) {
            int money = couponService.useCoupon(orderVo.getCoupon(), orderVo,
                    true);
            // 优惠券无法使用
            if (money < 0) {
                return ResultCode.ERRORCODE.ORDER_COUPON_CANNOTUSE;
            } else {
                //还需要部分支付
                if (total > money) {
                    orderVo.setPayTotal(total - money);
                    orderVo.setCouponTotal(money);
                    // 无需付钱则设置为已经支付
                } else {
                    orderVo.setPayTotal(0);
                    orderVo.setCouponTotal(total);
                    orderVo.setPayState(OrderConstant.PAYSTATE.HASPAY);
                    orderVo.setPayTime(new Date());
                    //20160719全额抵扣券的时候，也需要增加资金结算记录。
                    //moneyManager.handleOrderHasPayedMoneyFlow(orderVo);
                }
            }
        }
        
        // 20161116如果余额不足，不能约课
		Student student = studentManager.getStudentInfo(orderVo.getStudentId());
		if(student.getIsImport() == (byte)0) {
			StudentAccount sa = studentManager.getStudentMoney(orderVo.getStudentId());
			int money = 0;
			if (null != sa) {
				money = sa.getMoney();
			}
			if (money <  orderVo.getPayTotal()) {
				//20161219	因为余额不足导致优惠券被使用，订单无法生成则恢复优惠券，不需要前置判断，这个恢复服务自带前置校验
				couponService.recoverCoupon(orderVo);
				return ResultCode.ERRORCODE.BALANCE_IS_INSUFFICIENT;
			}
		}
        
        addOrder(orderVo);
        //添加排班信息
        CoachClassVo cc = new CoachClassVo();
        cc.setCtype(OrderConstant.OTYPE.NOWORDER);
        cc.setCoachId(orderVo.getCoachId());
        cc.setOrderId(orderId);
        cc.setCstart(orderVo.getPstart());
        cc.setCend(orderVo.getPend());
        cc.setRstart(cc.getCstart());
        cc.setRend(cc.getCend());
        coachClassService.addCoachClass(cc);
        
        //成功发送提交定订单消息
        Message msg = new Message();
        msg.setKeys(orderId);
        msg.setTopic(orderProducer.getCreateTopicKey());
        msg.setTags(OrderConstant.RMQTAG.COMMITORDER);
        msg.setBody(SerializableUtil.serialize(orderVo));
        orderProducer.send(msg);

        return ResultCode.ERRORCODE.SUCCESS;
    }

    /**
     * 因为已经自动接单，所以只处理时间，作为是否确认的标记
     */
    public String handleAccept(OrderVo orderVo) throws Exception {
        //判断订单情况：包括存在，状态等
        if (orderVo == null || orderVo.getOrderId() == null) {
            log.error("new order is not exists while accept:" + orderVo);
            return ResultCode.ERRORCODE.ORDER_NOTEXIST;
        }
        //更新数据
        orderVo.setAtime(new Date());
        orderVo.setOrderState(OrderConstant.ORDERSTATE.ACCEPTORDER);
        //回存方式接单，后续如抢单需要考虑并发
        saveOrder(orderVo);
        return ResultCode.ERRORCODE.SUCCESS;
    }

    public String stuCommentCoach(String coachId, String studentId,
                                  String orderId, String score, String tagId, String oneWord, String anonymity) throws Exception {
        String result = ResultCode.ERRORCODE.SUCCESS;
        CoachCommentVo scc = new CoachCommentVo();
        //订单判断
        OrderVo order = queryOrderById(orderId, new OrderQuery());
        if (order == null) {
            log.error(orderId + " stuCommentCoach not exist.");
            return ResultCode.ERRORCODE.ORDER_NOTEXIST;
        }
        if (!String.valueOf(order.getCoachId()).equals(coachId) || !String.valueOf(order.getStudentId()).equals(studentId)) {
            log.error(orderId + " stuCommentCoach not equal coach=" + coachId + ", or student=" + studentId);
            return ResultCode.ERRORCODE.ORDER_NOTEXIST;
        }
        //订单状态更新
        if (order.getOrderState() == OrderConstant.ORDERSTATE.COMPLETE || order.getOrderState() == OrderConstant.ORDERSTATE.COACHCOMMENT) {
            order.setOrderState(order.getOrderState() + 2);
        } else {
            if (order.getOrderState() != OrderConstant.ORDERSTATE.MISSCLASS) {
                log.error(orderId + " coachCommentStu state error coach=" + coachId + ", or student=" + studentId);
                return ResultCode.ERRORCODE.ORDER_NOT_COMMENT;
                //产品规格：缺课可以接受评价
            } else {
                log.error(orderId + " coachCommentStu state error but can comment coach=" + coachId + ", or student=" + studentId);
            }
        }
        saveOrder(order);
        scc.setCoachId(Long.valueOf(coachId));
        scc.setOrderId(orderId);
        scc.setStudentId(Long.valueOf(studentId));
        scc.setScore(Integer.valueOf(score));
        scc.setCtid(tagId);
        scc.setCotime(new Date());
        scc.setOneWord(oneWord);
        if(anonymity != null && !"".equals(anonymity)) {
        	scc.setAnonymity(Integer.valueOf(anonymity));
        }
        coachCommentService.addCoachComment(scc);
        //评价成功后，发送消息
        Message msg = new Message();
        msg.setKeys(orderId + OrderConstant.RMQTAG.COMMENTCOACH);
        msg.setTopic(orderProducer.getCreateTopicKey());
        msg.setTags(OrderConstant.RMQTAG.COMMENTCOACH);
        msg.setBody(SerializableUtil.serialize(scc));
        orderProducer.send(msg);

        return result;
    }

    public String coachCommentStu(String coachId, String studentId, String orderId, Map<Integer, Integer> scores, String oneWord) throws Exception {
        String result = ResultCode.ERRORCODE.SUCCESS;
        //订单判断
        OrderVo order = queryOrderById(orderId, new OrderQuery());
        if (order == null) {
            log.error(orderId + " coachCommentStu not exist.");
            result = ResultCode.ERRORCODE.ORDER_NOTEXIST;
            return result;
        }
        if (!String.valueOf(order.getCoachId()).equals(coachId) || !String.valueOf(order.getStudentId()).equals(studentId)) {
            log.error(orderId + " coachCommentStu not equal coach=" + coachId + ", or student=" + studentId);
            result = ResultCode.ERRORCODE.ORDER_NOTEXIST;
            return result;
        }
        //订单状态更新
        if (order.getOrderState() == OrderConstant.ORDERSTATE.COMPLETE || order.getOrderState() == OrderConstant.ORDERSTATE.STUCOMMENT) {
            order.setOrderState(order.getOrderState() + 1);
        } else {
            log.error(orderId + " coachCommentStu not equal coach=" + coachId + ", or student=" + studentId);
            result = ResultCode.ERRORCODE.ORDER_NOT_COMMENT;
            return result;
        }
        saveOrder(order);
        //获取技能点所属的科目
        SkillRelationVo search = new SkillRelationVo();
        search.setCourseId(Integer.valueOf(order.getCourseId()));
        SkillRelationQuery query = new SkillRelationQuery();
        query.setPageSize(500);
        List<SkillRelationVo> sr = skillRelationService.queryByObjectAnd(search, query);
        Map<Integer, SkillRelationVo> srmap = new HashMap<Integer, SkillRelationVo>();
        if (sr != null && !sr.isEmpty()) {
            srmap = BeanCopy.getFromList(sr, "ctid");
        }
        List<StuCommentVo> list = new ArrayList<StuCommentVo>();
        Iterator<Integer> it = scores.keySet().iterator();
        Date cotime = new Date();
        while (it.hasNext()) {
            Integer ctid = it.next();
            StuCommentVo ccs = new StuCommentVo();
            ccs.setCoachId(Long.valueOf(coachId));
            ccs.setOrderId(orderId);
            ccs.setStudentId(Long.valueOf(studentId));
            ccs.setCtid(ctid);
            ccs.setScore(scores.get(ctid));
            SkillRelationVo myskill = srmap.get(ctid);
            //分数统计的口径是科目
            if (myskill != null) {
                ccs.setCourseId(myskill.getCourseId());
                ccs.setSubjectId(myskill.getSubjectId());
            }
            ccs.setCotime(cotime);
            ccs.setOneWord(oneWord);
            list.add(ccs);
        }
        stuCommentService.addStuCommentList(list);
        //评价成功后，发送消息
        Message msg = new Message();
        msg.setKeys(orderId + OrderConstant.RMQTAG.COMMENTSTU);
        msg.setTopic(orderProducer.getCreateTopicKey());
        msg.setTags(OrderConstant.RMQTAG.COMMENTSTU);
        msg.setBody(SerializableUtil.serialize(list));
        orderProducer.send(msg);
        return result;
    }

    public ReqResult handUpOrder(String orderId) {
        ReqResult result = ReqResult.getSuccess();

        try {
            OrderVo orderVo = this.queryOrderById(orderId, new OrderQuery());
            if (orderVo == null) {
                result.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
                result.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOTEXIST);
                return result;
            }
            if (orderVo.getPayState() != OrderConstant.PAYSTATE.WAITPAY) {
                result.setCode(ResultCode.ERRORCODE.ORDER_PAY_SIGN_INVALID);
                result.setMsgInfo(ResultCode.ERRORINFO.ORDER_PAY_SIGN_INVALID);
                return result;
            }
            //orderVo.setPayState(OrderConstant.PAYSTATE.HAND_UP);
            //orderVo.setCheckOutState(OrderConstant.CHECKOUTSTATE.HAS_CHECKOUT); //为统一支付状态，在学员支付后才设置已结算。

//            if (updateByOrderId(orderVo, orderId) > 0) {
//                result = ReqResult.getSuccess();
//            }

            moneyManager.handleOrderHandUpMoneyFlow(orderVo);

        } catch (Exception e) {
            log.error("handUpOrder fail!", e);
            result.setCode(ResultCode.ERRORCODE.EXCEPTION);
            result.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return result;
    }
    
    public List<OrderVo> searchBmClass(int studentId, Integer coachId) throws Exception {
       
        List<OrderPo> poList = orderMapper.searchBmClass(studentId, coachId);
        List<OrderVo> voList = new ArrayList<OrderVo>();
        if (poList != null && !poList.isEmpty()) {
            voList = BeanCopy.copyList(poList, OrderVo.class, BeanCopy.COPYALL);
        }
        return voList;
    }
    
   public ReqResult saveInsuranceOrder(InsuranceOrder insuranceOrder){
	   ReqResult result = ReqResult.getSuccess();
	   Student student=studentManager.getStudentInfo(insuranceOrder.getStudentId());
		if(student==null){
			result.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
			result.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
			return result;
		}
		
		InsuranceOrderExample example=new InsuranceOrderExample();
	    example.createCriteria().andStudentIdEqualTo(insuranceOrder.getStudentId());
		List<InsuranceOrder> orders=insuranceOrderMapper.selectByExample(example);
		 String insuranceId=StringUtil.getOrderId();
         insuranceOrder.setInsuranceId(insuranceId);
         insuranceOrder.setPayState(0);
		if(orders.size()>0){
			InsuranceOrder order=orders.get(0);
			int payState=order.getPayState()==null?0:order.getPayState();
			if(payState==OrderConstant.PAYSTATE.HASPAY){
				result.setCode(ResultCode.ERRORCODE.PAY_ORDER_HAVE_PAY);
				result.setMsgInfo(ResultCode.ERRORINFO.PAY_ORDER_HAVE_PAY);
				return result;
			}else{
				insuranceId=order.getInsuranceId();
				insuranceOrder.setInsuranceId(insuranceId);
				insuranceOrderMapper.updateByPrimaryKeySelective(order);
			}
		}else{
			 insuranceOrderMapper.insertSelective(insuranceOrder);
		}
		Map<String,String> map=new HashMap<>();
		map.put("insuranceId", insuranceId);
	   result.setData(map);
	   return result;
   }
   
   public ReqResult searchInsuranceById(String userId){
	   ReqResult result = ReqResult.getSuccess();
	   InsuranceOrderExample example=new InsuranceOrderExample();
	   example.createCriteria().andStudentIdEqualTo(Long.parseLong(userId)).andPayStateEqualTo((int)ReqConstants.STAGE_STATE_SUCC);
	   List<InsuranceOrder> orders=insuranceOrderMapper.selectByExample(example);
	   result.setData(orders);
	   return result;
   }
   
   public InsuranceOrder searchInsuranceByOrderId(String insuranceId){
	   return insuranceOrderMapper.selectByPrimaryKey(insuranceId);
   }
   
   public void updatePayState(String insuranceId,byte payState,String payWay){
	   InsuranceOrder order=new InsuranceOrder();
	   order.setInsuranceId(insuranceId);
	   order.setPayState((int)payState);
	   order.setPayTime(new Date());
	   order.setPayWay(payWay);
	   insuranceOrderMapper.updateByPrimaryKeySelective(order);
   }
   
   
}
