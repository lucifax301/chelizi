package com.lili.cms.constant;

/**
 * 日志表常量
 * 
 * @author Administrator
 *
 */
public class LogConstant {
	
	/**
	 * 日志实体在Redis中过期时长
	 */
	public static final Integer LOG_EXPIRE_SECONDS = 180;
	
	
	//渠道
	public static final Integer CHANNEL_LILI = 1; //喱喱端
	public static final Integer CHANNEL_COACH = 2;//教练端
	
	//状态
	public static final Integer STATUS_SUCCESS = 1;//成功
	public static final Integer STATUS_FAIL = 2;//失败
	
	// 按钮 
	public static final Integer ACTION_ADD = 1;	//新增
	public static final Integer ACTION_UPDATE = 2; //修改
	public static final Integer ACTION_DELETE = 3;//删除
	public static final Integer ACTION_PASS = 4;//成功、有效
	public static final Integer ACTION_REJECT = 5;//失败、无效
	public static final Integer ACTION_CLOSE = 6;//关闭
	
	// 菜单
	public static final String MENE_STUDENT = "学员";
	public static final String MENE_COACH = "教练";
	public static final String MENE_ORDER = "订单";
	public static final String MENE_CAR = "教练车";
	public static final String MENE_FIELD = "训练场";
	public static final String MENE_DEPOSIT = "提现";
	public static final String MENE_BOUND_BANK = "银行卡";
	public static final String MENE_COUPON = "优惠券";
	
	//菜单 ID
	public static final Integer MENU_ID_STUDENT = 1;
	public static final Integer MENU_ID_COACH = 2;
	public static final Integer MENU_ID_ORDER = 3;
	public static final Integer MENU_ID_CAR = 4;
	public static final Integer MENU_ID_FIELD = 5;
	public static final Integer MENU_ID_DEPOSIT = 6;
	public static final Integer MENU_ID_BOUND_BANK = 7;
	public static final Integer MENU_ID_COUPON = 8;
	public static final Integer MENU_ID_CONDITION= 9;
	
	// 关联表名 
	public static final String TABLE_U_STUDENT= "t_u_student";
	public static final String TABLE_U_COACH = "t_u_coach";
	public static final String TABLE_ORDER = "t_order";
	public static final String TABLE_P_CAR= "t_p_car";
	public static final String TABLE_S_TRFIELD = "t_s_trfield";
	public static final String TABLE_U_DEPOSIT = "t_u_deposit";
	public static final String TABLE_U_BANKCARD_VERIFY = "t_u_bankcard_verify";
	public static final String TABLE_S_COUPON = "t_s_coupon";
	public static final String TABLE_U_COUPON = "t_u_coupon";
	public static final String TABLE_S_CCONDITION= "t_s_ccondition";
	//public static final String TABLE_S_SCHOOL= "t_s_school";
	
	// 关联表ID
	public static final Integer TABLE_ID_U_STUDENT= 1;
	public static final Integer TABLE_ID_U_COACH = 2;
	public static final Integer TABLE_ID_ORDER = 3;
	public static final Integer TABLE_ID_P_CAR= 4;
	public static final Integer TABLE_ID_S_TRFIELD = 5;
	public static final Integer TABLE_ID_U_DEPOSIT = 6;
	public static final Integer TABLE_ID_U_BANKCARD_VERIFY = 7;
	public static final Integer TABLE_ID_U_COUPON = 8;
	public static final Integer TABLE_ID_S_COUPON = 9;
	public static final Integer TABLE_ID_S_CCONDITION = 10;
	//public static final Integer TABLE_ID_S_SCHOOL= 8;
	
	//备注组合
	public static final String  REMARK_BANK_PASS = "更新银行卡状态为有效";
	public static final String  REMARK_BANK_REJECT ="更新银行卡状态无效";
	public static final String  REMARK_DEPOSIT_PASS ="更新提现状态为成功";
	public static final String  REMARK_DEPOSIT_REJECT ="更新提现状态为失败";
	
	

	/**
	 * 渠道
	 * @author Administrator
	 *
	 */
	public enum Channel {
		LILI("喱喱", 1), COACH("教练端", 2);

		private String name;
		private int index;

		// 构造方法
		private Channel(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 普通方法
		public static String getName(int index) {
			for (Channel c : Channel.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/**
	 * 动作
	 * @author Administrator
	 *
	 */
	public enum Action {
		 ADD("增加", 1), UPDATE("更新", 2), DELETE("删除",3), CANLE("关闭",4), BIND("绑定",5), DISTRIBUTE("分配",6);

		private String name;
		private int index;

		// 构造方法
		private Action(String name, int index) {
            this.name = name;
            this.index = index;
        }

		// 普通方法
		public static String getName(int index) {
			for (Action c : Action.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}
	
	/**
	 * 菜单
	 * @author lzb
	 *
	 */
	public enum Menu {
		RESOURCE("数据统计", 1), 
		ORDER("订单", 2), 
		STUDENT("学员", 3), 
		COACH("教练", 4), 
		CAR("教练车", 5), 
		FIELD("训练场", 6), 
		DEPOSIT("提现", 7), 
		BANKCARD("银行卡", 8);

		private String name;
		private int index;

		// 构造方法
		private Menu(String name, int index) {
            this.name = name;
            this.index = index;
        }

		// 普通方法
		public static String getName(int index) {
			for (Menu c : Menu.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}
	
	public static void main(String args[]){
		System.out.println(Menu.getName(11));
	}

}
