package com.lili.access.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.FileHandle;
import com.lili.common.util.MD5Security;
import com.lili.common.util.Page;
import com.lili.finance.vo.SchDeposit;
import com.lili.finance.vo.UserMoneyVo;
import com.lili.school.dto.EnrollLongtrainStudent;
import com.lili.school.dto.EnrollTheoryStudent;
import com.lili.school.model.Region;
import com.lili.school.model.SchAccount;
import com.lili.school.model.SchoolAccountApply;
import com.lili.school.model.SchoolBDTO;
import com.lili.school.model.SchoolExtend;
import com.lili.school.model.WechatEnrollPackage;
import com.lili.school.model.WechatEnrollPackageBDTO;
import com.lili.school.service.CMSRegionService;
import com.lili.school.service.CMSSchoolService;
import com.lili.user.model.User;

/**
 * 驾校
 * 
 * @author lzb
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/school")
public class SchoolController extends BaseController {
	Logger logger = Logger.getLogger(SchoolController.class);

	@Autowired
	CMSSchoolService cmsSchoolService;

	@Autowired
	CMSRegionService cmsRegionService;

	/**
	 * 获取城市列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryCity", method = RequestMethod.GET)
	@ResponseBody
	public String queryCity(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Region dto = (Region) buildObject(request, Region.class);
			return cmsRegionService.findCityList(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/querySchool", method = RequestMethod.GET)
	@ResponseBody
	public String querySchool(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			SchoolBDTO dto = (SchoolBDTO) buildObject(request, SchoolBDTO.class);
			return cmsSchoolService.findSchoolList(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SchoolBDTO dto = (SchoolBDTO) buildObject(request, SchoolBDTO.class);
			return cmsSchoolService.findSchoolArray(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 申请开通驾校账户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/applyAccount", method = RequestMethod.POST)
	@ResponseBody
	public String applyAccount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String passwd = request.getParameter("passwd");

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			SchAccount dto = (SchAccount) buildObject(request, SchAccount.class);
			dto.setSchoolId(schoolId);
			dto.setPasswd(MD5Security.EncodeMD5Hex(passwd));

			SchoolExtend schoolExtend = (SchoolExtend) buildObject(request,
					SchoolExtend.class);
			schoolExtend.setSchoolId(schoolId);

			return cmsSchoolService.applyAccount(dto, schoolExtend).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	@ResponseBody
	public String apply(SchoolAccountApply apply, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {

			return cmsSchoolService.applySchoolAccount(apply).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 查询驾校账户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAccount", method = RequestMethod.GET)
	@ResponseBody
	public String queryAccount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String ymDate = request.getParameter("ymDate");
			SchoolExtend schoolExtend = (SchoolExtend) buildObject(request,
					SchoolExtend.class);
			schoolExtend.setSchoolId(schoolId);
			return cmsSchoolService.queryAccount(schoolExtend, ymDate);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 驾校提现申请
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	@ResponseBody
	public String deposit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();

			String money = request.getParameter("money");
			String passwdM = request.getParameter("passwd");
			String passwd = MD5Security.EncodeMD5Hex(passwdM);

			resp = cmsSchoolService.deposit(schoolId, money, passwd);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
		return resp;
	}

	/**
	 * 查询单笔提现
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDeposit", method = RequestMethod.GET)
	@ResponseBody
	public String queryDeposit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			SchoolExtend schoolExtend = (SchoolExtend) buildObject(request,
					SchoolExtend.class);
			schoolExtend.setSchoolId(schoolId);
			return cmsSchoolService.queryDeposit(schoolExtend);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 查询提现记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDepositHis", method = RequestMethod.GET)
	@ResponseBody
	public String queryDepositHis(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			SchoolExtend schoolExtend = (SchoolExtend) buildObject(request,
					SchoolExtend.class);
			schoolExtend.setSchoolId(schoolId);
			return cmsSchoolService.queryDepositList(schoolExtend);
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 找回密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPasswd", method = RequestMethod.POST)
	@ResponseBody
	public String queryPasswd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();

			String passwd = request.getParameter("passwd");

			SchAccount dto = (SchAccount) buildObject(request, SchAccount.class);
			dto.setPasswd(MD5Security.EncodeMD5Hex(passwd));
			dto.setSchoolId(schoolId);
			return cmsSchoolService.queryPasswd(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 申请提现校验
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/depositVal", method = RequestMethod.POST)
	@ResponseBody
	public String depositVal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			SchoolExtend schoolExtend = (SchoolExtend) buildObject(request,
					SchoolExtend.class);
			schoolExtend.setSchoolId(schoolId);

			return cmsSchoolService.depositVal(schoolExtend).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 关闭变更提示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/closeRemark", method = RequestMethod.POST)
	@ResponseBody
	public String closeRemark(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			SchoolExtend schoolExtend = (SchoolExtend) buildObject(request,
					SchoolExtend.class);
			schoolExtend.setSchoolId(schoolId);
			return cmsSchoolService.closeRemark(schoolExtend).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 修改驾校账户密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/alertPasswd", method = RequestMethod.POST)
	@ResponseBody
	public String alertPasswd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();

			String passwd = request.getParameter("passwd");
			String passwdOld = request.getParameter("passwdOld");
			SchAccount dto = (SchAccount) buildObject(request, SchAccount.class);
			dto.setPasswd(MD5Security.EncodeMD5Hex(passwd));
			dto.setPasswdOld(MD5Security.EncodeMD5Hex(passwdOld));
			dto.setSchoolId(schoolId);

			return cmsSchoolService.alertPasswd(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 修改驾校绑定手机
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/alertMobile", method = RequestMethod.POST)
	@ResponseBody
	public String alertMobile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();

			SchAccount dto = (SchAccount) buildObject(request, SchAccount.class);
			dto.setSchoolId(schoolId);

			return cmsSchoolService.alertMobile(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 下载EXCEL
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/downLoadExcel", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadExcel(HttpServletRequest request,
			HttpServletResponse response) {
		OutputStream out = null;
		try {
			SchDeposit schdeposit = (SchDeposit) buildObject(request,
					SchDeposit.class);
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmssSSS");
			String time = dateFormat.format(date);
			String fileName = new String(
					Constant.SHEET_SCHOOL_DEPOSIT.getBytes("UTF-8"),
					"ISO8859-1")
					+ time + ".xls";

			List<SchDeposit> batch = cmsSchoolService.downLoadExcel(schdeposit);
			Workbook wb = export(batch);
			out = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			response.setContentType("application/vnd.ms-excel");
			wb.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("********************************* error："
					+ e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("********************************* error："
						+ e.getMessage());
			}
		}
	}

	public static final String[] excelHeader = { "申请时间", "流水号", "提现金额", "账户余额",
			"开户银行", "对公账号", "提现状态", "备注" };

	public Workbook export(List<SchDeposit> list) {
		Workbook wb = null;
		try {
			wb = new SXSSFWorkbook(100); // 内存里一次只留 多少行,几十万行无压力，不怕OOM
			Sheet sheet = wb.createSheet(Constant.SHEET_BOUND_BANK_CARD); // 设置工作表标题
			Row row = sheet.createRow((int) 0);
			CellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中

			// 产生表格标题行
			for (int i = 0; i < excelHeader.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(excelHeader[i]);
				cell.setCellStyle(style);
			}
			// 第一个参数代表列id(从0开始),第2个参数代表宽度值
			sheet.setColumnWidth(0, 6000);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 3000);

			HashMap<Integer, String> map = new HashMap<Integer, String>();
			map.put(0, "审核中");
			map.put(1, "提现成功");
			map.put(2, "交易失败");
			map.put(3, "已确认");
			map.put(4, "银行处理中");
			map.put(5, "银行处理失败");

			// 遍历集合数据，产生数据行
			SchDeposit schDeposit = null;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 1);
				schDeposit = list.get(i);
				row.createCell(0).setCellValue(
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(schDeposit.getApplyTime()));
				row.createCell(1).setCellValue(schDeposit.getWaterId());
				row.createCell(2).setCellValue(schDeposit.getMoney() / 100);
				row.createCell(3).setCellValue(
						schDeposit.getAccountMoney() / 100);
				row.createCell(4).setCellValue(schDeposit.getBankName());
				row.createCell(5).setCellValue(schDeposit.getBankCard());
				row.createCell(6).setCellValue(
						map.get(schDeposit.getCheckStatus()));
				row.createCell(7).setCellValue(schDeposit.getRemark());
			}
		} catch (Exception e) {
			logger.error("************************************ error: "
					+ e.getMessage());
			e.printStackTrace();
		}
		return wb;
	}

	/**
	 * 余额明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/touchBalance", method = RequestMethod.GET)
	@ResponseBody
	public String touchBalance(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			UserMoneyVo userMoneyVo = (UserMoneyVo) buildObject(request,
					UserMoneyVo.class);
			String operateType = request.getParameter("operateType");
			logger.info("********************************** operateType"
					+ operateType);
			if (!"".equals(operateType) && operateType != null) {
				userMoneyVo.setOperateType(Integer.valueOf(operateType));
			}
			userMoneyVo.setUserType(3);
			userMoneyVo.setUserId(schoolId);
			return cmsSchoolService.touchBalance(userMoneyVo).build();
		} catch (Exception e) {
			logger.error("*********************************touchBalance error : "
					+ e.getMessage());
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 收入明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/accountBalance", method = RequestMethod.GET)
	@ResponseBody
	public String accountBalance(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			UserMoneyVo userMoneyVo = (UserMoneyVo) buildObject(request,
					UserMoneyVo.class);
			String operateType = request.getParameter("operateType");
			logger.info("********************************** operateType"
					+ operateType);
			if (!"".equals(operateType) && operateType != null) {
				userMoneyVo.setOperateType(Integer.valueOf(operateType));
			}
			userMoneyVo.setUserType(3);
			userMoneyVo.setUserId(schoolId);
			return cmsSchoolService.accountBalance(userMoneyVo).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 费用明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/costDetail", method = RequestMethod.GET)
	@ResponseBody
	public String costDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			UserMoneyVo userMoneyVo = (UserMoneyVo) buildObject(request,
					UserMoneyVo.class);
			String operateType = request.getParameter("operateType");
			logger.info("********************************** operateType"
					+ operateType);
			if (!"".equals(operateType) && operateType != null) {
				userMoneyVo.setOperateType(Integer.valueOf(operateType));
			}
			userMoneyVo.setUserType(3);
			userMoneyVo.setUserId(schoolId);
			return cmsSchoolService.costDetail(userMoneyVo).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 获取理论课列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory", method = RequestMethod.GET)
	@ResponseBody
	public String getTheory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String state = request.getParameter("state");
			String dateBegin = request.getParameter("dateBegin");
			String dateEnd = request.getParameter("dateEnd");
			String ctimeBegin = request.getParameter("ctimeBegin");
			String ctimeEnd = request.getParameter("ctimeEnd");
			logger.info("**********************************getTheory schoolId:"
					+ schoolId);

			return cmsSchoolService.getTheoryBySchoolId(schoolId, state,
					pageNo, pageSize, dateBegin, dateEnd, ctimeBegin, ctimeEnd)
					.build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 获取理论课 单个
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory/one", method = RequestMethod.GET)
	@ResponseBody
	public String getTheoryOne(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String theoryId = request.getParameter("theoryId");
			logger.info("**********************************getTheory schoolId:"
					+ schoolId);

			return cmsSchoolService.getTheoryByTheoryId(schoolId, theoryId)
					.build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 添加理论课
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory", method = RequestMethod.POST)
	@ResponseBody
	public String addTheory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String classDate = request.getParameter("classDate");
			String classTime = request.getParameter("classTime");
			String className = request.getParameter("className");
			String cardDesc = request.getParameter("cardDesc");
			String contactName = request.getParameter("contactName");
			String classPlace = request.getParameter("classPlace");
			String contactMobile = request.getParameter("contactMobile");
			logger.info("**********************************postTheory schoolId:"
					+ schoolId);

			return cmsSchoolService
					.addTheory(schoolId, classDate, classTime, className,
							cardDesc, contactName, contactMobile, classPlace)
					.build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 修改理论课
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory/modify", method = RequestMethod.POST)
	@ResponseBody
	public String modifyTheory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String theoryId = request.getParameter("theoryId");
			String classDate = request.getParameter("classDate");
			String classTime = request.getParameter("classTime");
			String className = request.getParameter("className");
			String cardDesc = request.getParameter("cardDesc");
			String contactName = request.getParameter("contactName");
			String classPlace = request.getParameter("classPlace");
			String contactMobile = request.getParameter("contactMobile");

			return cmsSchoolService.modifyTheory(theoryId, schoolId, classDate,
					classTime, className, cardDesc, contactName, contactMobile,
					classPlace).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 安排学员--导入学员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theoryStudent", method = RequestMethod.POST)
	@ResponseBody
	public String addTheoryStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String theoryId = request.getParameter("theoryId");
			String studentIds = request.getParameter("studentIds");

			return cmsSchoolService.addTheoryStudent(schoolId, theoryId,
					studentIds).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 安排学员--去除已导入学员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theoryStudent/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteTheoryStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String theoryId = request.getParameter("theoryId");
			String studentIds = request.getParameter("studentIds");

			return cmsSchoolService.deleteTheoryStudent(schoolId, theoryId,
					studentIds).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 安排学员--获取已导入学员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theoryStudent", method = RequestMethod.GET)
	@ResponseBody
	public String getTheoryStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String theoryId = request.getParameter("theoryId");
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String state = request.getParameter("state"); // 考勤状态：0-未记考勤；1-出勤；2-缺勤
			String isImport = request.getParameter("isImport"); // 0-喱喱学员，1-驾校学员

			return cmsSchoolService.getTheoryStudent(schoolId, theoryId,
					pageNo, pageSize, state, isImport).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 导出理论课学员
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/enroll/theoryStudent/export-excel", method = RequestMethod.GET)
	public void exportTheoryStudentExcel(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String theoryId = request.getParameter("theoryId");
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String state = request.getParameter("state"); // 考勤状态：0-未记考勤；1-出勤；2-缺勤
			String isImport = request.getParameter("isImport"); // 0-喱喱学员，1-驾校学员

			Page<EnrollTheoryStudent> theoryList = cmsSchoolService
					.getTheoryStudentPage(schoolId, theoryId, pageNo, pageSize,
							state, isImport);
			if (null == theoryList || theoryList.getDataList().size() == 0) {
				return;
			}
			Workbook wb = null;
			try {
				wb = new SXSSFWorkbook(100); // 内存里一次只留多少行,几十万行无压力，不怕OOM
				Sheet sheet = wb.createSheet("理论课培训学员"); // 设置工作表标题
				Row row = sheet.createRow((int) 0);
				CellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
				String[] excelHeader = { "姓名", "电话", "性别", "所学车型", "身份证",
						"流水号", "学员类型", "考勤状态" };

				// 产生表格标题行
				for (int i = 0; i < excelHeader.length; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(excelHeader[i]);
					cell.setCellStyle(style);
				}
				// 第一个参数代表列id(从0开始),第2个参数代表宽度值
				sheet.setColumnWidth(0, 6000);
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(7, 8000);

				// 遍历集合数据，产生数据行
				List<EnrollTheoryStudent> dataList = theoryList.getDataList();
				EnrollTheoryStudent field = null;
				for (int i = 0; i < dataList.size(); i++) {
					row = sheet.createRow(i + 1);
					row.setRowStyle(style);
					field = dataList.get(i);
					row.createCell(0).setCellValue(field.getName());
					row.createCell(1).setCellValue(field.getPhoneNum());
					row.createCell(2).setCellValue(
							field.getSex() == 0 ? "女" : "男");
					row.createCell(3).setCellValue(
							field.getDrType() == 1 ? "C1" : "C2");
					row.createCell(4).setCellValue(field.getIdNumber());
					row.createCell(5).setCellValue(field.getFlowNo());
					row.createCell(6).setCellValue(
							field.getIsImport() == 0 ? "喱喱学员" : "驾校学员");
					row.createCell(7).setCellValue(
							field.getState() == 0 ? "未记考勤"
									: (field.getState() == 1 ? "出勤" : "缺勤"));
				}
			} catch (Exception e) {
				logger.error("|||exception e :" + e.getMessage()
						+ " when export coach");
			}

			sendExcel(response, wb, "理论课培训" + theoryId);
		} catch (Exception e) {
			access.error(e.getMessage());
		}
	}

	/**
	 * 更改学员状态：考勤状态：0-未记考勤；1-出勤；2-缺勤
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theoryStudent/state", method = RequestMethod.POST)
	@ResponseBody
	public String changeTheoryStudentState(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String theoryId = request.getParameter("theoryId");
			String studentIds = request.getParameter("studentIds");
			String state = request.getParameter("state");

			return cmsSchoolService.changeTheoryStudentState(schoolId,
					theoryId, studentIds, state).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 安排学员--获取确认开课短信通知模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory/msgInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getTheoryMsgInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String state = request.getParameter("state"); // 1-待上课；3-已取消'
			return cmsSchoolService.getTheoryMsgInfo(state).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 更改课程状态--确认开课，取消开课--短信通知学员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/theory/class", method = RequestMethod.POST)
	@ResponseBody
	public String changeTheoryClass(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String theoryId = request.getParameter("theoryId");
			String state = request.getParameter("state");

			return cmsSchoolService
					.changeTheoryClass(schoolId, theoryId, state).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 长考课--新建--根据手机号获取教练信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain/coach", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrainCoach(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String contactMobile = request.getParameter("contactMobile");
			logger.info("**********************************postTheory schoolId:"
					+ schoolId);

			return cmsSchoolService.getLongtrainCoach(schoolId, contactMobile)
					.build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 长考课--新建--获取车辆信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain/car", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrainCar(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String carNo = getParamStr(request, "carNo");
			logger.info("**********************************postTheory schoolId:"
					+ schoolId);

			return cmsSchoolService.getLongtrainCar(schoolId, carNo).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 长考课--新建课程
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain", method = RequestMethod.POST)
	@ResponseBody
	public String addLongtrain(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String contactMobile = request.getParameter("contactMobile");
			String carNo = getParamStr(request, "carNo");
			String classDate = request.getParameter("classDate");
			String classTime = request.getParameter("classTime");
			String classPlace = request.getParameter("classPlace");
			String carrys = request.getParameter("carrys");

			logger.info("**********************************postTheory schoolId:"
					+ schoolId);

			return cmsSchoolService.addLongtrain(schoolId, contactMobile,
					carNo, classDate, classTime, classPlace, carrys).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 长考课--获取课程
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrain(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String state = request.getParameter("state");
			String dateBegin = request.getParameter("dateBegin");
			String dateEnd = request.getParameter("dateEnd");
			String ctimeBegin = request.getParameter("ctimeBegin");
			String ctimeEnd = request.getParameter("ctimeEnd");

			return cmsSchoolService.getLongtrain(schoolId, state, pageNo,
					pageSize, dateBegin, dateEnd, ctimeBegin, ctimeEnd).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 长考课--获取单个课程
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain/one", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrainOne(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String ltId = request.getParameter("ltId");

			return cmsSchoolService.getLongtrainOne(schoolId, ltId).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 长考课--修改课程
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain/modify", method = RequestMethod.POST)
	@ResponseBody
	public String modifyLongtrain(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String ltId = request.getParameter("ltId");
			String contactMobile = request.getParameter("contactMobile");
			String carNo = getParamStr(request, "carNo");
			String classDate = request.getParameter("classDate");
			String classTime = request.getParameter("classTime");
			String classPlace = request.getParameter("classPlace");
			String carrys = request.getParameter("carrys");

			return cmsSchoolService.modifyLongtrain(schoolId, ltId,
					contactMobile, carNo, classDate, classTime, classPlace,
					carrys).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 安排长考学员--导入学员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrainStudent", method = RequestMethod.POST)
	@ResponseBody
	public String addLongtrainStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String ltId = request.getParameter("ltId");
			String studentIds = request.getParameter("studentIds");

			return cmsSchoolService.addLongtrainStudent(schoolId, ltId,
					studentIds).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 安排长考学员--获取已导入学员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrainStudent", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrainStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String ltId = request.getParameter("ltId");
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String state = request.getParameter("state");
			String isImport = request.getParameter("isImport");

			return cmsSchoolService.getLongtrainStudent(schoolId, ltId, pageNo,
					pageSize, state, isImport).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 导出长考学员
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/enroll/longtrainStudent/export-excel", method = RequestMethod.GET)
	public void exportLongtrainStudentExcel(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String ltId = request.getParameter("ltId");
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String state = request.getParameter("state"); // 考勤状态：0-未记考勤；1-出勤；2-缺勤
			String isImport = request.getParameter("isImport"); // 0-喱喱学员，1-驾校学员

			Page<EnrollLongtrainStudent> data = cmsSchoolService
					.getLongtrainStudentPage(schoolId, ltId, pageNo, pageSize,
							state, isImport);
			if (null == data || data.getDataList().size() == 0) {
				return;
			}
			Workbook wb = null;
			try {
				wb = new SXSSFWorkbook(100); // 内存里一次只留多少行,几十万行无压力，不怕OOM
				Sheet sheet = wb.createSheet("长考培训学员"); // 设置工作表标题
				Row row = sheet.createRow((int) 0);
				CellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
				String[] excelHeader = { "姓名", "电话", "性别", "所学车型", "身份证",
						"流水号", "学员类型", "长考成绩" };

				// 产生表格标题行
				for (int i = 0; i < excelHeader.length; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(excelHeader[i]);
					cell.setCellStyle(style);
				}
				// 第一个参数代表列id(从0开始),第2个参数代表宽度值
				sheet.setColumnWidth(0, 6000);
				sheet.setColumnWidth(2, 6000);
				sheet.setColumnWidth(7, 8000);

				// 遍历集合数据，产生数据行
				List<EnrollLongtrainStudent> dataList = data.getDataList();
				EnrollLongtrainStudent field = null;
				for (int i = 0; i < dataList.size(); i++) {
					row = sheet.createRow(i + 1);
					row.setRowStyle(style);
					field = dataList.get(i);
					row.createCell(0).setCellValue(field.getName());
					row.createCell(1).setCellValue(field.getPhoneNum());
					row.createCell(2).setCellValue(
							field.getSex() == 0 ? "女" : "男");
					row.createCell(3).setCellValue(
							field.getDrType() == 1 ? "C1" : "C2");
					row.createCell(4).setCellValue(field.getIdNumber());
					row.createCell(5).setCellValue(field.getFlowNo());
					row.createCell(6).setCellValue(
							field.getIsImport() == 0 ? "喱喱学员" : "驾校学员");
					row.createCell(7).setCellValue(
							field.getState() == 0 ? "未记成绩"
									: (field.getState() == 1 ? "合格" : "不合格"));
				}
			} catch (Exception e) {
				logger.error("|||exception e :" + e.getMessage()
						+ " when export coach");
			}

			sendExcel(response, wb, "长考培训" + ltId);
		} catch (Exception e) {
			access.error(e.getMessage());
		}
	}

	/**
	 * 安排长考学员--删除学员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrainStudent/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteLongtrainStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String ltId = request.getParameter("ltId");
			String studentIds = request.getParameter("studentIds");

			return cmsSchoolService.deleteLongtrainStudent(schoolId, ltId,
					studentIds).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 安排长考学员--获取确认开课/取消开课短信通知模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain/msgInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getLongtrainMsgInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String state = request.getParameter("state"); // 1-待上课；3-已取消'
			return cmsSchoolService.getLongtrainMsgInfo(state).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 更改长考课程状态--确认开课，取消开课--短信通知学员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrain/class", method = RequestMethod.POST)
	@ResponseBody
	public String changeLongtrainClass(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String ltId = request.getParameter("ltId");
			String state = request.getParameter("state");

			return cmsSchoolService.changeLongtrainClass(schoolId, ltId, state)
					.build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 更改长考学员成绩状态 ：成绩状态：0-未记成绩；1-合格；2-不合格
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/longtrainStudent/state", method = RequestMethod.POST)
	@ResponseBody
	public String changeLongtrainStudentState(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String ltId = request.getParameter("ltId");
			String studentIds = request.getParameter("studentIds");
			String state = request.getParameter("state");

			return cmsSchoolService.changeLongtrainStudentState(schoolId, ltId,
					studentIds, state).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 导入约考情况、考试成绩文件 // 1-预约登记表 2-考试成绩表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/enroll/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object upload(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		ResponseMessage resp = new ResponseMessage(MessageCode.MSG_FAIL);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constant.USER_SESSION);
		Long schoolId = user.getSchoolId();
		String creator = user.getAccount();
		String type = request.getParameter("type"); // 1-预约登记表 2-考试成绩表

		// 上传文件处理
		String fileType = null;
		InputStream is = null;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获得文件
			MultipartFile multipartFile = multipartRequest.getFile("filename");
			// 获得文件名
			String fileName = multipartFile.getOriginalFilename();
			logger.info("***************************** FileName is : "
					+ fileName);
			// 文件存放地址
			String tagFileName = request.getSession().getServletContext()
					.getRealPath("WEB-INF");
			if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
				logger.info("***************************** FileName is Error!");
				return resp.build();
			}
			if (fileName.endsWith(".xls")) {
				fileType = ".xls";
			} else {
				fileType = ".xlsx";
			}

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmssSSS");
			String time = dateFormat.format(date);
			tagFileName = tagFileName + "/uploadExcel/" + fileName + "-" + time
					+ fileType;
			logger.info("************************ tagFileName :" + tagFileName);
			// 输入流
			is = multipartFile.getInputStream();
			File outfile = FileHandle.upLoadFile(is, tagFileName);
			if (outfile.exists()) {
				FileInputStream outfileis = new FileInputStream(outfile);

				ByteArrayOutputStream out = new ByteArrayOutputStream();

				byte[] buffer = new byte[outfileis.available()];
				outfileis.read(buffer);
				out.write(buffer);
				out.close();

				resp = cmsSchoolService.uploadNew(buffer, tagFileName,
						schoolId, creator, type);// 为避免超时，先返回uuid

				if (outfile.isFile()) { // 判断是否为文件
					outfile.delete();
				}

			}
			is.close();
		} catch (Exception e) {
			logger.error("************************************ error: "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resp.build();
	}

	/**
	 * 根据uuid获取上传处理结果
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/upload/uuid", method = RequestMethod.GET)
	@ResponseBody
	public String getUpdateResult(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String uuid = request.getParameter("uuid");

			return cmsSchoolService.getUpdateResult(schoolId, uuid).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 确认导入或取消导入
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/update/state", method = RequestMethod.POST)
	@ResponseBody
	public String changeUpdateState(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String uuid = request.getParameter("uuid");
			String state = request.getParameter("state"); // 1-确认导入；2-放弃导入
			String userName = user.getUserName();

			return cmsSchoolService.changeUpdateState(schoolId, uuid, state,
					userName).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 获取已导入的所有表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/tables", method = RequestMethod.GET)
	@ResponseBody
	public String getUpdateTables(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String dateBegin = request.getParameter("dateBegin");
			String dateEnd = request.getParameter("dateEnd");
			String type = request.getParameter("type"); // 1-预约登记表 2-考试成绩表

			return cmsSchoolService.getUpdateTables(schoolId, type, pageNo,
					pageSize, dateBegin, dateEnd).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 获取已导入的表详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/tables/one", method = RequestMethod.GET)
	@ResponseBody
	public String getUpdateTablesOne(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String tableNo = request.getParameter("tableNo");
			String type = request.getParameter("type"); // 1-预约登记表 2-考试成绩表

			return cmsSchoolService.getUpdateTablesOne(schoolId, type, pageNo,
					pageSize, tableNo).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 获取已导入的单个表简介
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enroll/tables/one/info", method = RequestMethod.GET)
	@ResponseBody
	public String getUpdateTablesOneInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();
			String tableNo = request.getParameter("tableNo");

			return cmsSchoolService.getUpdateTablesOneInfo(schoolId, tableNo)
					.build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 微信端驾校班级分页查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPackage", method = RequestMethod.GET)
	@ResponseBody
	public String queryPackage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resp = null;
		try {
			WechatEnrollPackageBDTO wBdto = (WechatEnrollPackageBDTO) buildObject(
					request, WechatEnrollPackageBDTO.class);
			resp = cmsSchoolService.findPackageList(wBdto).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
		return resp;
	}

	@RequestMapping(value = "/queryPackageById", method = RequestMethod.GET)
	@ResponseBody
	public String queryPackageById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resp = null;
		List<WechatEnrollPackage> list = new ArrayList<>();
		try {
			String ttids = request.getParameter("ttids");
			String arg[] = ttids.split(",");

			for (int i = 0; i < arg.length; i++) {
				String ttid = arg[i];
				if (ttid == null || ttid.equals("")) {
					continue;
				}
				WechatEnrollPackage wPackage = cmsSchoolService
						.findPackageById(ttid);
				if (wPackage != null) {
					list.add(wPackage);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
		resp = new ResponseMessage().addResult("pageData", list).build();
		return resp;
	}

	/**
	 * 微信端班级添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPackage", method = RequestMethod.GET)
	@ResponseBody
	public String addPackage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			WechatEnrollPackageBDTO dto = (WechatEnrollPackageBDTO) buildObject(
					request, WechatEnrollPackageBDTO.class);
			String cType = request.getParameter("cType") == null ? "1"
					: request.getParameter("cType");
			dto.setcType(Integer.parseInt(cType));
			// 非空字段 isdel是否删除 ctime创建时间 mtime修改时间
			dto.setIsdel((byte) 0);
			dto.setCtime(new Date());
			dto.setMtime(new Date());
			dto.setCstate(1);
			dto.setOstate(1);
			dto.setSchool_id(dto.getSchoolId().intValue());
			return cmsSchoolService.addPackage(dto).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 微信端班级修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePackage", method = RequestMethod.GET)
	@ResponseBody
	public String updatePackage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {

			WechatEnrollPackageBDTO dto = (WechatEnrollPackageBDTO) buildObject(
					request, WechatEnrollPackageBDTO.class);
			String ttNo = request.getParameter("ttNo");
			String cType = request.getParameter("cType") == null ? "1"
					: request.getParameter("cType");
			dto.setcType(Integer.parseInt(cType));
			dto.setTtid(Integer.parseInt(ttNo));
			// 非空字段 isdel是否删除 mtime修改时间
			dto.setIsdel((byte) 0);
			dto.setMtime(new Date());
			dto.setSchool_id(dto.getSchoolId().intValue());
			return cmsSchoolService.updatePackage(dto).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 微信端班级状态修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePackageState", method = RequestMethod.GET)
	@ResponseBody
	public String updatePackageState(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			WechatEnrollPackageBDTO dto = (WechatEnrollPackageBDTO) buildObject(
					request, WechatEnrollPackageBDTO.class);
			String ttids = request.getParameter("ttids");
			// mtime修改时间
			dto.setMtime(new Date());
			return cmsSchoolService.updatePackageState(dto, ttids).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 微信端单个驾校
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDriveSchoolById", method = RequestMethod.GET)
	@ResponseBody
	public String queryDriveSchoolById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resp = null;
		try {
			SchoolBDTO school = (SchoolBDTO) buildObject(
					request, SchoolBDTO.class);
			resp = cmsSchoolService.findWxSchoolById(
					Integer.parseInt(school.getSchoolId().toString())).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
		return resp;
	}

	/**
	 * 微信端驾校修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateWxSchool", method = RequestMethod.POST)
	@ResponseBody
	public String updateWxSchool(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			SchoolBDTO dto = (SchoolBDTO) buildObject(
					request, SchoolBDTO.class);
			String lge = request.getParameter("lge");
			String lae = request.getParameter("lae");
			if (lge != null && !lge.equals("") && lae != null
					&& !lae.equals("")) {
				BigDecimal lge2 = new BigDecimal(lge);
				BigDecimal lae2 = new BigDecimal(lae);
				dto.setLge(lge2);
				dto.setLae(lae2);
			}
			return cmsSchoolService.updateWxSchool(dto).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 班别导入学员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPackageStudent", method = RequestMethod.POST)
	@ResponseBody
	public String addPackageStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String studentIds = request.getParameter("studentIds");
			String ttid = request.getParameter("ttid");
			int tid = Integer.parseInt(ttid);
			String args[] = studentIds.split(",");
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("")) {
					continue;
				}
				int studentId = Integer.parseInt(args[i]);
				cmsSchoolService.addPackageStudent(studentId, tid);
			}
			return new ResponseMessage(0, MessageCode.MSG_SUCCESS).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 删除学员关联班别
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePackageStudent", method = RequestMethod.POST)
	@ResponseBody
	public String deletePackageStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String studentIdStr = request.getParameter("studentId");
			int studentId = Integer.parseInt(studentIdStr);
			cmsSchoolService.addPackageStudent(studentId, 0); // 删除关联关系
			return new ResponseMessage(0, MessageCode.MSG_SUCCESS).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
	}

}
