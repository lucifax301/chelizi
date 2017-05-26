/**
 * 
 */
package com.lili.pay.bill.qq;

import java.util.ArrayList;
import java.util.List;



/**
 * @author linbo
 * QQ支付账单
 */
public class QQBillData {
	private int totalBill;
	private int totalFee;
	
	private List<QQBillDetail> billDetails = new ArrayList<QQBillDetail>();

	/**
	 * @return the totalBill
	 */
	public int getTotalBill() {
		return totalBill;
	}

	/**
	 * @param totalBill the totalBill to set
	 */
	public void setTotalBill(int totalBill) {
		this.totalBill = totalBill;
	}

	/**
	 * @return the totalFee
	 */
	public int getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return the billDetails
	 */
	public List<QQBillDetail> getBillDetails() {
		return billDetails;
	}

	/**
	 * @param billDetails the billDetails to set
	 */
	public void setBillDetails(List<QQBillDetail> billDetails) {
		this.billDetails = billDetails;
	}
	
	public void addBillDetail(QQBillDetail qqBillDetail)
	{
		this.billDetails.add(qqBillDetail);
	}
}
