package com.lili.order.model;

import java.util.Date;

import com.lili.cms.entity.BaseEntity;
import com.lili.cms.util.StringUtil;

public class Order  extends BaseEntity{

	private static final long serialVersionUID = -238216512959989155L;
	
	private String orderId;
	private String carNo;
	private String learnAddr;
	private String gtimeStr;
	private String studentName;
	private String coachName;
	private String coorder;
	private String stuAddr;
	private String courseName;

	private String coachPhoneNum;
	private String studentPhoneNum;
	
	private Long coachId;
	private Long studentId;
	
	private Integer courseId;
	private Integer carId;
	private Integer price;
	private Integer otype;
	private Integer ccid;
	private Integer clzNum;
	private Integer orderState;
	/**
	 * 支付状态：0代表未支付，1代表已支付,2代表支付失败，9代表老学员自动支付
	 */
	private Integer payState;
	private Integer needTrans;
	private Integer transState;
	private Integer payId;
	private Integer dltype;

	private Double lge;
	private Double lae;
	private Double placeLge;
	private Double placeLae;
	
	private Date rstart;
	private Date rend;
	private Date cstart;
	private Date cend;
	private Date pstart;
	private Date pend;
	private Date gtime;
	private Date atime;
	private Date payTime;
	
	private Integer payTotal;
	private String payWay;
	private Integer sustainTime;
	private Integer couponTotal;
	private Integer coupon;
	private Integer score;
	private String oneWord;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getOneWord() {
		return oneWord;
	}

	public void setOneWord(String oneWord) {
		this.oneWord = oneWord;
	}

	public Integer getCoupon() {
		return coupon;
	}

	public void setCoupon(Integer coupon) {
		this.coupon = coupon;
	}

	public enum PayStateEnum{
		ALL("全部",99), UNPAY("未支付",0), PAYED("已支付",1), FAIL_PAYED("支付失败",2),OLD_AUTO_PAY("老学员自动支付",9);
		
		private PayStateEnum(String name, int num) {
			this.name = name;
			this.num = num;
		}
		private String name;
		private int num;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		
		@Override
		public String toString() {
			return "name = " + name + ",num = " + num;
		}
	}
	
	public String getPayStateEnum(Integer num){
		PayStateEnum[] values = PayStateEnum.values();
		for(int i = 0;i < values.length;i ++){
			if(num == values[i].num){
				return values[i].getName();
			}
		}
		return StringUtil.BLANK_STR;
	}

	public String getOrderStateEnum(Integer num){
		OrderStateEnum[] values = OrderStateEnum.values();
		for(int i = 0;i < values.length;i ++){
			if(num == values[i].num){
				return values[i].getName();
			}
		}
		return StringUtil.BLANK_STR;
	}
	
	public enum OrderStateEnum{
		ALL("全部",99), CANCLE("已取消",0), ORDERED("已下单",1), GET_ORDER("已接单",2),
		CLASS("上课中",3), FINISHED("已完成",4), COACH_EVALUATE("教练已评价",5), STUDENT_EVALUATE("学生已评价",6),
		EVALUATE("双方已评价",7), UNKNOWN("未知",8),REFUSE("拒单",9),ABSENT("缺课",10);
		
		private OrderStateEnum(String name, int num) {
			this.name = name;
			this.num = num;
		}
		private String name;
		private int num;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		
		@Override
		public String toString() {
			return "name = " + name + ",num = " + num;
		}
	}

	public String getCoachPhoneNum() {
		return coachPhoneNum;
	}

	public void setCoachPhoneNum(String coachPhoneNum) {
		this.coachPhoneNum = coachPhoneNum;
	}

	public String getStudentPhoneNum() {
		return studentPhoneNum;
	}

	public void setStudentPhoneNum(String studentPhoneNum) {
		this.studentPhoneNum = studentPhoneNum;
	}

	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCoachName() {
		return coachName;
	}
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	public String getGtimeStr() {
		return gtimeStr;
	}
	public void setGtimeStr(String gtimeStr) {
		this.gtimeStr = gtimeStr;
	}
	public String getOrderId() {
		return this.orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId=orderId;
	}
	public Long getCoachId() {
		return this.coachId;
	}
	public void setCoachId(Long coachId) {
		this.coachId=coachId;
	}
	public Integer getCourseId() {
		return this.courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId=courseId;
	}
	public Integer getPrice() {
		return this.price;
	}
	public void setPrice(Integer price) {
		this.price=price;
	}
	public String getLearnAddr() {
		return this.learnAddr;
	}
	public void setLearnAddr(String learnAddr) {
		this.learnAddr=learnAddr;
	}
	public Long getStudentId() {
		return this.studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId=studentId;
	}
	public Integer getDltype() {
		return this.dltype;
	}
	public void setDltype(Integer dltype) {
		this.dltype=dltype;
	}
	public Double getLge() {
		return this.lge;
	}
	public void setLge(Double lge) {
		this.lge=lge;
	}
	public Double getLae() {
		return this.lae;
	}
	public void setLae(Double lae) {
		this.lae=lae;
	}
	public String getStuAddr() {
		return this.stuAddr;
	}
	public void setStuAddr(String stuAddr) {
		this.stuAddr=stuAddr;
	}
	public Date getPstart() {
		return this.pstart;
	}
	public void setPstart(Date pstart) {
		this.pstart=pstart;
	}
	public Date getPend() {
		return this.pend;
	}
	public void setPend(Date pend) {
		this.pend=pend;
	}
	public Integer getClzNum() {
		return this.clzNum;
	}
	public void setClzNum(Integer clzNum) {
		this.clzNum=clzNum;
	}
	public Integer getOrderState() {
		return this.orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState=orderState;
	}
	public Date getRstart() {
		return this.rstart;
	}
	public void setRstart(Date rstart) {
		this.rstart=rstart;
	}
	public Date getRend() {
		return this.rend;
	}
	public void setRend(Date rend) {
		this.rend=rend;
	}
	public Date getCstart() {
		return this.cstart;
	}
	public void setCstart(Date cstart) {
		this.cstart=cstart;
	}
	public Date getCend() {
		return this.cend;
	}
	public void setCend(Date cend) {
		this.cend=cend;
	}
	public Integer getPayState() {
		return this.payState;
	}
	public void setPayState(Integer payState) {
		this.payState=payState;
	}
	public Integer getNeedTrans() {
		return this.needTrans;
	}
	public void setNeedTrans(Integer needTrans) {
		this.needTrans=needTrans;
	}
	public Integer getTransState() {
		return this.transState;
	}
	public void setTransState(Integer transState) {
		this.transState=transState;
	}
	public Integer getPayId() {
		return this.payId;
	}
	public void setPayId(Integer payId) {
		this.payId=payId;
	}
	public Date getGtime() {
		return this.gtime;
	}
	public void setGtime(Date gtime) {
		this.gtime=gtime;
	}
	public Date getAtime() {
		return this.atime;
	}
	public void setAtime(Date atime) {
		this.atime=atime;
	}
	public Integer getOtype() {
		return this.otype;
	}
	public void setOtype(Integer otype) {
		this.otype=otype;
	}
	public String getCoorder() {
		return this.coorder;
	}
	public void setCoorder(String coorder) {
		this.coorder=coorder;
	}
	public Integer getCcid() {
		return this.ccid;
	}
	public void setCcid(Integer ccid) {
		this.ccid=ccid;
	}

	public Integer getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(Integer payTotal) {
		this.payTotal = payTotal;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public Integer getCouponTotal() {
		return couponTotal;
	}

	public void setCouponTotal(Integer couponTotal) {
		this.couponTotal = couponTotal;
	}

	public Double getPlaceLge() {
		return placeLge;
	}

	public void setPlaceLge(Double placeLge) {
		this.placeLge = placeLge;
	}

	public Double getPlaceLae() {
		return placeLae;
	}

	public void setPlaceLae(Double placeLae) {
		this.placeLae = placeLae;
	}

	public Integer getSustainTime() {
		return sustainTime;
	}

	public void setSustainTime(Integer sustainTime) {
		this.sustainTime = sustainTime;
	}
	
	
	
}

