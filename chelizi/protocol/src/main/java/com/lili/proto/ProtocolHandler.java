/**   
 * @Title: ProtocolHandler.java 
 * @Package com.lili.proto 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author A18ccms A18ccms_gmail_com   
 * @date 2015-9-22 下午8:28:16 
 * @version V1.0   
 */
package com.lili.proto;

/**
 * @ClassName: ProtocolHandler
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author A18ccms a18ccms_gmail_com
 * @date 2015-9-22 下午8:28:16
 * 
 */
public enum ProtocolHandler {
    COACH_LOGIN_ACK(0x0001, "com.clz.lili.client.command.ClientLoginAckCmd"),
    COACH_REPORT_ACK(0x0002, "com.clz.lili.client.command.ClientReportAckCmd"),
    //USER_SEARCH_ACK(0x0003, "com.lili.client.command.ClientSearchAckCmd"),
    USER_PING_ACK(0x0004, "com.clz.lili.client.command.ClientPingAckCmd");
    
	private short cmd;
	private String className;

	ProtocolHandler(int cmd, String className) {
		this.cmd = (short) cmd;
		this.className = className;
	}

	/**
	 * @return the cmd
	 */
	public short getCmd() {
		return cmd;
	}

	/**
	 * @param cmd
	 *            the cmd to set
	 */
	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */

}
