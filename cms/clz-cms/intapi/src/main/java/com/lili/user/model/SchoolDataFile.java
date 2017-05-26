package com.lili.user.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 导入驾校数据时的导表记录
 * @author hughes
 *
 */
public class SchoolDataFile implements Serializable{

	private static final long serialVersionUID = 1L;

	 private Integer id;


	    private String schoolname;

	    private Integer schoolid;

	    /**
	     * 文件类型：1-教练信息，2-学员信息，3-训练场信息，4-车辆信息
	     */
	    private Byte filetype;

	    private Integer sum;

	    private Integer sucsum;

	    private Integer failsum;

	    private Byte status;

	    private Date createtime;

	    private String creatorName;

	    private String extra;


		private String billNo;

		public String getBillNo() {
			return billNo;
		}

		public void setBillNo(String billNo) {
			this.billNo = billNo;
		}
		
	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }


	    public String getSchoolname() {
	        return schoolname;
	    }

	    public void setSchoolname(String schoolname) {
	        this.schoolname = schoolname == null ? null : schoolname.trim();
	    }

	    public Integer getSchoolid() {
	        return schoolid;
	    }

	    public void setSchoolid(Integer schoolid) {
	        this.schoolid = schoolid;
	    }

	    public Byte getFiletype() {
	        return filetype;
	    }

	    public void setFiletype(Byte filetype) {
	        this.filetype = filetype;
	    }

	    public Integer getSum() {
	        return sum;
	    }

	    public void setSum(Integer sum) {
	        this.sum = sum;
	    }

	    public Integer getSucsum() {
	        return sucsum;
	    }

	    public void setSucsum(Integer sucsum) {
	        this.sucsum = sucsum;
	    }

	    public Integer getFailsum() {
	        return failsum;
	    }

	    public void setFailsum(Integer failsum) {
	        this.failsum = failsum;
	    }

	    public Byte getStatus() {
	        return status;
	    }

	    public void setStatus(Byte status) {
	        this.status = status;
	    }

	    public Date getCreatetime() {
	        return createtime;
	    }

	    public void setCreatetime(Date createtime) {
	        this.createtime = createtime;
	    }


	    public String getCreatorName() {
			return creatorName;
		}

		public void setCreatorName(String creatorName) {
			this.creatorName = creatorName;
		}

		public String getExtra() {
	        return extra;
	    }

	    public void setExtra(String extra) {
	        this.extra = extra == null ? null : extra.trim();
	    }
	
}
