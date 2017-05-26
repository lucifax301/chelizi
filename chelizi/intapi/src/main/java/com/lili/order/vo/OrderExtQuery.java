package com.lili.order.vo;

public class OrderExtQuery extends BaseQuery {
	/**开始mybatis查询变量**/
	private String orderId="order_id=#{param1.orderId}";
	private String coachId="coach_id=#{param1.coachId}";
	private String courseId="course_id=#{param1.courseId}";
	private String price="price=#{param1.price}";
	private String learnAddr="learn_addr=#{param1.learnAddr}";
	private String studentId="student_id=#{param1.studentId}";
	private String dltype="dltype=#{param1.dltype}";
	private String lge="lge=#{param1.lge}";
	private String lae="lae=#{param1.lae}";
	private String stuAddr="stu_addr=#{param1.stuAddr}";
	private String pstart="pstart=#{param1.pstart}";
	private String pend="pend=#{param1.pend}";
	private String clzNum="clz_num=#{param1.clzNum}";
	private String orderState="order_state=#{param1.orderState}";
	private String rstart="rstart=#{param1.rstart}";
	private String rend="rend=#{param1.rend}";
	private String cstart="cstart=#{param1.cstart}";
	private String cend="cend=#{param1.cend}";
	private String payState="pay_state=#{param1.payState}";
	private String needTrans="need_trans=#{param1.needTrans}";
	private String transState="trans_state=#{param1.transState}";
	private String payId="pay_id=#{param1.payId}";
	private String gtime="gtime=#{param1.gtime}";
	private String atime="atime=#{param1.atime}";
	private String otype="otype=#{param1.otype}";
	private String coorder="coorder=#{param1.coorder}";
	private String ccid="ccid=#{param1.ccid}";
	private String unitPrice="unit_price=#{param1.unitPrice}";
	private String transPrice="trans_price=#{param1.transPrice}";
	private String placeId="place_id=#{param1.placeId}";
	private String transName="trans_name=#{param1.transName}";
	private String carId="car_id=#{param1.carId}";
	private String carName="car_name=#{param1.carName}";
	private String carImg="car_img=#{param1.carImg}";
	private String carNo="car_no=#{param1.carNo}";
	private String insId="ins_id=#{param1.insId}";
	private String insPrice="ins_price=#{param1.insPrice}";
	private String insName="ins_name=#{param1.insName}";
	private String coachName="coach_name=#{param1.coachName}";
	private String coachImg="coach_img=#{param1.coachImg}";
	private String coachMobile="coach_mobile=#{param1.coachMobile}";
	private String coachStar="coach_star=#{param1.coachStar}";
	private String stuName="stu_name=#{param1.stuName}";
	private String stuImg="stu_img=#{param1.stuImg}";
	private String stuMobile="stu_mobile=#{param1.stuMobile}";
	private String courseName="course_name=#{param1.courseName}";
	/**结束mybatis查询变量**/

	private String sqlField="order_id as orderId,coach_id as coachId,course_id as courseId,price as price,learn_addr as learnAddr,student_id as studentId,dltype as dltype,lge as lge,lae as lae,stu_addr as stuAddr,pstart as pstart,pend as pend,clz_num as clzNum,order_state as orderState,rstart as rstart,rend as rend,cstart as cstart,cend as cend,pay_state as payState,need_trans as needTrans,trans_state as transState,pay_id as payId,gtime as gtime,atime as atime,otype as otype,coorder as coorder,ccid as ccid,unit_price as unitPrice,trans_price as transPrice,place_id as placeId,trans_name as transName,car_id as carId,car_name as carName,car_img as carImg,car_no as carNo,ins_id as insId,ins_price as insPrice,ins_name as insName,coach_name as coachName,coach_img as coachImg,coach_mobile as coachMobile,coach_star as coachStar,stu_name as stuName,stu_img as stuImg,stu_mobile as stuMobile,course_name as courseName";
	private String sqlPost;
	private String groupBy;
	private String orderBy;
	private boolean  paging=false;
	private int pageIndex=1;
	private int pageSize=10;
	private String queryExt;

	public boolean getPaging() {
		return this.paging;
	}

	public void setPageSize(int pageSize) {
		this.pageSize=pageSize;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex=pageIndex;
		this.paging=true;
	}
	public int getPageSize() {
		return this.pageSize;
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	public void setQueryExt(String queryExt) {
		this.queryExt=queryExt;
	}

	public String getQueryExt() {
		return this.queryExt;
	}
	public void setSqlField(String sqlField) {
		this.sqlField=sqlField;
	}

	public String getSqlField() {
		return  sqlField;
	}

	public String getSqlPost() {
		if(sqlPost==null || sqlPost.isEmpty()) {
			String sqlPostTemp="";
			if(getQueryExt()!=null) {
				sqlPostTemp+=getQueryExt();
			}
			if(groupBy!=null) {
				sqlPostTemp+=groupBy +" ";
			}
			if(orderBy!=null) {
				sqlPostTemp+=orderBy +" ";
			}
			if(getPaging()){
				sqlPostTemp+=" limit "+((getPageIndex()-1)*getPageSize())+","+getPageSize()+" ";
			}
			return sqlPostTemp;
		}
		return sqlPost;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy=groupBy;
	}

	public void setorderBy(String orderBy) {
		this.orderBy=orderBy;
	}

  public String getOrderId() {
    return this.orderId;
  }
  public void setOrderId(String orderId) {
    this.orderId=orderId;
  }
  public String getCoachId() {
    return this.coachId;
  }
  public void setCoachId(String coachId) {
    this.coachId=coachId;
  }
  public String getCourseId() {
    return this.courseId;
  }
  public void setCourseId(String courseId) {
    this.courseId=courseId;
  }
  public String getPrice() {
    return this.price;
  }
  public void setPrice(String price) {
    this.price=price;
  }
  public String getLearnAddr() {
    return this.learnAddr;
  }
  public void setLearnAddr(String learnAddr) {
    this.learnAddr=learnAddr;
  }
  public String getStudentId() {
    return this.studentId;
  }
  public void setStudentId(String studentId) {
    this.studentId=studentId;
  }
  public String getDltype() {
    return this.dltype;
  }
  public void setDltype(String dltype) {
    this.dltype=dltype;
  }
  public String getLge() {
    return this.lge;
  }
  public void setLge(String lge) {
    this.lge=lge;
  }
  public String getLae() {
    return this.lae;
  }
  public void setLae(String lae) {
    this.lae=lae;
  }
  public String getStuAddr() {
    return this.stuAddr;
  }
  public void setStuAddr(String stuAddr) {
    this.stuAddr=stuAddr;
  }
  public String getPstart() {
    return this.pstart;
  }
  public void setPstart(String pstart) {
    this.pstart=pstart;
  }
  public String getPend() {
    return this.pend;
  }
  public void setPend(String pend) {
    this.pend=pend;
  }
  public String getClzNum() {
    return this.clzNum;
  }
  public void setClzNum(String clzNum) {
    this.clzNum=clzNum;
  }
  public String getOrderState() {
    return this.orderState;
  }
  public void setOrderState(String orderState) {
    this.orderState=orderState;
  }
  public String getRstart() {
    return this.rstart;
  }
  public void setRstart(String rstart) {
    this.rstart=rstart;
  }
  public String getRend() {
    return this.rend;
  }
  public void setRend(String rend) {
    this.rend=rend;
  }
  public String getCstart() {
    return this.cstart;
  }
  public void setCstart(String cstart) {
    this.cstart=cstart;
  }
  public String getCend() {
    return this.cend;
  }
  public void setCend(String cend) {
    this.cend=cend;
  }
  public String getPayState() {
    return this.payState;
  }
  public void setPayState(String payState) {
    this.payState=payState;
  }
  public String getNeedTrans() {
    return this.needTrans;
  }
  public void setNeedTrans(String needTrans) {
    this.needTrans=needTrans;
  }
  public String getTransState() {
    return this.transState;
  }
  public void setTransState(String transState) {
    this.transState=transState;
  }
  public String getPayId() {
    return this.payId;
  }
  public void setPayId(String payId) {
    this.payId=payId;
  }
  public String getGtime() {
    return this.gtime;
  }
  public void setGtime(String gtime) {
    this.gtime=gtime;
  }
  public String getAtime() {
    return this.atime;
  }
  public void setAtime(String atime) {
    this.atime=atime;
  }
  public String getOtype() {
    return this.otype;
  }
  public void setOtype(String otype) {
    this.otype=otype;
  }
  public String getCoorder() {
    return this.coorder;
  }
  public void setCoorder(String coorder) {
    this.coorder=coorder;
  }
  public String getCcid() {
    return this.ccid;
  }
  public void setCcid(String ccid) {
    this.ccid=ccid;
  }
  public String getUnitPrice() {
    return this.unitPrice;
  }
  public void setUnitPrice(String unitPrice) {
    this.unitPrice=unitPrice;
  }
  public String getTransPrice() {
    return this.transPrice;
  }
  public void setTransPrice(String transPrice) {
    this.transPrice=transPrice;
  }
  public String getPlaceId() {
    return this.placeId;
  }
  public void setPlaceId(String placeId) {
    this.placeId=placeId;
  }
  public String getTransName() {
    return this.transName;
  }
  public void setTransName(String transName) {
    this.transName=transName;
  }
  public String getCarId() {
    return this.carId;
  }
  public void setCarId(String carId) {
    this.carId=carId;
  }
  public String getCarName() {
    return this.carName;
  }
  public void setCarName(String carName) {
    this.carName=carName;
  }
  public String getCarImg() {
    return this.carImg;
  }
  public void setCarImg(String carImg) {
    this.carImg=carImg;
  }
  public String getCarNo() {
    return this.carNo;
  }
  public void setCarNo(String carNo) {
    this.carNo=carNo;
  }
  public String getInsId() {
    return this.insId;
  }
  public void setInsId(String insId) {
    this.insId=insId;
  }
  public String getInsPrice() {
    return this.insPrice;
  }
  public void setInsPrice(String insPrice) {
    this.insPrice=insPrice;
  }
  public String getInsName() {
    return this.insName;
  }
  public void setInsName(String insName) {
    this.insName=insName;
  }
  public String getCoachName() {
    return this.coachName;
  }
  public void setCoachName(String coachName) {
    this.coachName=coachName;
  }
  public String getCoachImg() {
    return this.coachImg;
  }
  public void setCoachImg(String coachImg) {
    this.coachImg=coachImg;
  }
  public String getCoachMobile() {
    return this.coachMobile;
  }
  public void setCoachMobile(String coachMobile) {
    this.coachMobile=coachMobile;
  }
  public String getCoachStar() {
    return this.coachStar;
  }
  public void setCoachStar(String coachStar) {
    this.coachStar=coachStar;
  }
  public String getStuName() {
    return this.stuName;
  }
  public void setStuName(String stuName) {
    this.stuName=stuName;
  }
  public String getStuImg() {
    return this.stuImg;
  }
  public void setStuImg(String stuImg) {
    this.stuImg=stuImg;
  }
  public String getStuMobile() {
    return this.stuMobile;
  }
  public void setStuMobile(String stuMobile) {
    this.stuMobile=stuMobile;
  }
  public String getCourseName() {
    return this.courseName;
  }
  public void setCourseName(String courseName) {
    this.courseName=courseName;
  }
}
