package com.lili.order.vo;

public class OrderQuery extends BaseQuery {
	private String sqlField="order_id as orderId,coach_id as coachId,course_id as courseId,price as price,learn_addr as learnAddr,student_id as studentId,dltype as dltype,lge as lge,lae as lae,stu_addr as stuAddr,pstart as pstart,pend as pend,clz_num as clzNum,order_state as orderState,rstart as rstart,rend as rend,cstart as cstart,cend as cend,pay_state as payState,need_trans as needTrans,trans_state as transState,pay_id as payId,gtime as gtime,atime as atime,otype as otype,coorder as coorder,ccid as ccid,unit_price as unitPrice,trans_price as transPrice,place_id as placeId,trans_name as transName,car_id as carId,car_name as carName,car_img as carImg,car_no as carNo,ins_id as insId,ins_price as insPrice,ins_name as insName,coach_name as coachName,coach_img as coachImg,coach_mobile as coachMobile,coach_star as coachStar,stu_name as stuName,stu_img as stuImg,stu_mobile as stuMobile,course_name as courseName,allowance as allowance,price_total as priceTotal,pay_total as payTotal,pay_time as payTime,place_lge as placeLge,place_lae as placeLae,city_id as cityId,coupon as coupon,coupon_total as couponTotal,coupon_name as couponName,course_type as courseType,school_Id as schoolId";
	private String sqlPost;
	private String groupBy;
	private String orderBy;
	private int pageIndex=1;
	private int pageSize=10;
	private boolean paging=false;
	public void setSqlField(String sqlField) {
		this.sqlField=sqlField;
	}

	public String getSqlField() {
		return  sqlField;
	}

	public void setSqlPost(String sqlPost) {
		this.sqlPost=sqlPost;
	}

	public String getSqlPost() {
		String sqlPostTemp=sqlPost;
		if(sqlPost==null || sqlPost.isEmpty()) {
			sqlPostTemp="";
			if(groupBy!=null) {
				sqlPostTemp+=groupBy +" ";
			}
			if(orderBy!=null) {
				sqlPostTemp+=orderBy +" ";
			}
			if(paging) {
				sqlPostTemp+=" limit "+((pageIndex-1)*pageSize)+","+pageSize+" ";
			}
		}
		if(sqlPostTemp.indexOf(";")>=0||sqlPostTemp.indexOf("select")>=0||sqlPostTemp.indexOf("from")>=0) {
			sqlPostTemp=" ";
		}
		return sqlPostTemp;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy=groupBy;
	}

	public void setorderBy(String orderBy) {
		this.orderBy=orderBy;
	}

	public void setPageSize(int pageSize) {
		this.pageSize=pageSize;
		this.paging=true;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex=pageIndex;
		this.paging=true;
	}

}
