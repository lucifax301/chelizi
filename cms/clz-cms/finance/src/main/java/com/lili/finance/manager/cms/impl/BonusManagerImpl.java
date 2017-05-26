package com.lili.finance.manager.cms.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanCopy;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.finance.manager.cms.IBonusManager;
import com.lili.finance.mapper.dao.cms.IBonusDao;
import com.lili.finance.mapper.dao.cms.IBonusDetailDao;
import com.lili.finance.mapper.dao.common.UserMoneyDao;
import com.lili.finance.model.cms.BonusDetail;
import com.lili.finance.model.common.UserMoney;
import com.lili.finance.vo.BonusDetailVo;
import com.lili.finance.vo.BonusVo;

@Service("bonusManager")
public class BonusManagerImpl implements IBonusManager{
	
	Logger logger = Logger.getLogger(BonusManagerImpl.class);

	@Autowired
	private IBonusDao bonusDao;
	
	@Autowired
	private IBonusDetailDao bonusDetailDao;
	
	@Autowired
	UserMoneyDao userMoneyDao;
	
	@Override
	public String uploadExcelDetail(File outfile, String fileName, String creator) {
		  String resp = null;
		  // 解析文件
		  //创建Excel工作簿对象	
		  Workbook wb = null; 
		  boolean isE2007 = false;
		  if (fileName .endsWith("xlsx"))  {
				isE2007  = true;  
		  }
		  FileInputStream fis = null;
          //根据文件格式(2003或者2007)来初始化  
          try {
        	 fis = new FileInputStream(outfile);
			if(isE2007)  {
			    wb = new XSSFWorkbook(fis);  
			  }
			  else  {
			  	wb = new HSSFWorkbook(fis);  
			  }
			  
			  BonusVo bonus = null;
			  int bonusId = 0 ;
			  DecimalFormat df = new DecimalFormat("0");
			  BonusDetail bonusDetail = null;
			  List<BonusDetail> bonusDetailList = new ArrayList<BonusDetail>();
			  Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
			  Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
			  while (rows.hasNext()) {  
			      Row row = rows.next();  //获得行数据  
			      if (row.getRowNum() == 1) {
			      	bonus = new BonusVo();
			      	bonus.setCreator(creator);
			      	bonus.setStatus(1);
			      }
			      else if (row.getRowNum() >= 3) {
			      	bonusDetail = new BonusDetail();
			      	bonusDetail.setBonusId(bonusId);
			      }
			      logger.info("Row #" + row.getRowNum());  //获得行号从0开始  
			      Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
			      while (cells.hasNext()) {  
			          Cell cell = cells.next();  
			          logger.info("Cell #" + cell.getColumnIndex());  
			          switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
			          case HSSFCell.CELL_TYPE_NUMERIC:  
			              logger.info(cell.getNumericCellValue());  
			              if(row.getRowNum() == 1 && cell.getColumnIndex() == 3) {
			          		bonus.setBonusNum((int)cell.getNumericCellValue());
			          	}
			              else if(row.getRowNum() == 1 && cell.getColumnIndex() == 5) {
			              	bonus.setBonusMoney((int)cell.getNumericCellValue());
			              }
			              else if (row.getRowNum() >= 3) {
			              	if(cell.getColumnIndex() == 1){
			          			bonusDetail.setPhoneNum(String.valueOf(df.format(cell.getNumericCellValue())));
			          		}
			              	else if (cell.getColumnIndex() == 5) {
			              		if ((int)cell.getNumericCellValue() > 200000 || (int)cell.getNumericCellValue() < 0) {
			                  		logger.error("******************* Money is over !");
			                  		resp = MessageCode.MSG_MONEY_OVER_LIMIT;
			                  		return resp;
			                  	}
			              		bonusDetail.setMoney((int)cell.getNumericCellValue());
			              	}
			          	}
			              break;  
			          case HSSFCell.CELL_TYPE_STRING:  
			          	if (row.getRowNum() == 1 &&  cell.getColumnIndex() == 0) {
			          		bonus.setBonusName(cell.getStringCellValue());
			          	}
			          	else if (row.getRowNum() >= 3) {
			          		if(cell.getColumnIndex() == 0){
			          			bonusDetail.setCoachName(cell.getStringCellValue());
			          		}
			          		else if(cell.getColumnIndex() == 2){
			              		bonusDetail.setRegion(cell.getStringCellValue());
			              	}
			          		else if(cell.getColumnIndex() == 3){
			              		bonusDetail.setBonusReason(cell.getStringCellValue());
			              	}
			          	}
			              logger.info(cell.getStringCellValue());  
			              break;  
			          case HSSFCell.CELL_TYPE_BOOLEAN:  
			              logger.info(cell.getBooleanCellValue());  
			              break;  
			          case HSSFCell.CELL_TYPE_FORMULA:  // 公式
			              logger.info(cell.getCellFormula());  
			              break;  
			          default:  
			              logger.info("unsuported sell type");  
			          break;  
			          }  
			      }
			      if (bonus != null) {
			      	logger.info("********************************* uploadExcel Start!");
			      	bonusDao.insertSelective(bonus);
			      	BonusVo bonusInfo = bonusDao.queryBonus(bonus);
			      	bonusId = bonusInfo.getId();
			      	bonus = null;
			      }
			      if(bonusDetail != null){
			    	  if (bonusDetail.getPhoneNum() != null && !"".equals(bonusDetail.getPhoneNum())) {
			    		  bonusDetailList.add(bonusDetail);
			    	  }
			      }
			}  
			logger.info("*********************************uploadExcelDetail Start!");
			bonusDetailDao.insertList(bonusDetailList);
			  
			 if (outfile.exists()) {  // 不存在返回 false  
			     if (outfile.isFile()) {   // 判断是否为文件  
			         outfile.delete();
			     } 
			 }
			 fis.close();
		} catch (FileNotFoundException e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
        finally {
        	try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
          
		return resp;  
	}
	
	@Override
	public String uploadExcelDetailNew(byte[] b, String fileName, String creator) {
		  String resp = null;
		  // 解析文件
		  //创建Excel工作簿对象	
		  Workbook wb = null; 
		  boolean isE2007 = false;
		  if (fileName .endsWith("xlsx"))  {
				isE2007  = true;  
		  }
		  InputStream fis = null;
          //根据文件格式(2003或者2007)来初始化  
          try {
        	 fis = new ByteArrayInputStream(b);
			if(isE2007)  {
			    wb = new XSSFWorkbook(fis);  
			  }
			  else  {
			  	wb = new HSSFWorkbook(fis);  
			  }
			  
			  BonusVo bonus = null;
			  int bonusId = 0 ;
			  DecimalFormat df = new DecimalFormat("0");
			  BonusDetail bonusDetail = null;
			  List<BonusDetail> bonusDetailList = new ArrayList<BonusDetail>();
			  Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
			  Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
			  while (rows.hasNext()) {  
			      Row row = rows.next();  //获得行数据  
			      if (row.getRowNum() == 1) {
			      	bonus = new BonusVo();
			      	bonus.setCreator(creator);
			      	bonus.setStatus(1);
			      }
			      else if (row.getRowNum() >= 3) {
			      	bonusDetail = new BonusDetail();
			      	bonusDetail.setBonusId(bonusId);
			      }
			      logger.info("Row #" + row.getRowNum());  //获得行号从0开始  
			      Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
			      while (cells.hasNext()) {  
			          Cell cell = cells.next();  
			          logger.info("Cell #" + cell.getColumnIndex());  
			          switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
			          case HSSFCell.CELL_TYPE_NUMERIC:  
			              logger.info(cell.getNumericCellValue());  
			              if(row.getRowNum() == 1 && cell.getColumnIndex() == 3) {
			          		bonus.setBonusNum((int)cell.getNumericCellValue());
			          	}
			              else if(row.getRowNum() == 1 && cell.getColumnIndex() == 5) {
			              	bonus.setBonusMoney((int)cell.getNumericCellValue());
			              }
			              else if (row.getRowNum() >= 3) {
			              	if(cell.getColumnIndex() == 1){
			          			bonusDetail.setPhoneNum(String.valueOf(df.format(cell.getNumericCellValue())));
			          		}
			              	else if (cell.getColumnIndex() == 5) {
			              		if ((int)cell.getNumericCellValue() > 200000 || (int)cell.getNumericCellValue() < 0) {
			                  		logger.error("******************* Money is over !");
			                  		resp = MessageCode.MSG_MONEY_OVER_LIMIT;
			                  		return resp;
			                  	}
			              		bonusDetail.setMoney((int)cell.getNumericCellValue());
			              	}
			          	}
			              break;  
			          case HSSFCell.CELL_TYPE_STRING:  
			          	if (row.getRowNum() == 1 &&  cell.getColumnIndex() == 0) {
			          		bonus.setBonusName(cell.getStringCellValue());
			          	}
			          	else if (row.getRowNum() >= 3) {
			          		if(cell.getColumnIndex() == 0){
			          			bonusDetail.setCoachName(cell.getStringCellValue());
			          		}
			          		else if(cell.getColumnIndex() == 2){
			              		bonusDetail.setRegion(cell.getStringCellValue());
			              	}
			          		else if(cell.getColumnIndex() == 3){
			              		bonusDetail.setBonusReason(cell.getStringCellValue());
			              	}
			          	}
			              logger.info(cell.getStringCellValue());  
			              break;  
			          case HSSFCell.CELL_TYPE_BOOLEAN:  
			              logger.info(cell.getBooleanCellValue());  
			              break;  
			          case HSSFCell.CELL_TYPE_FORMULA:  // 公式
			              logger.info(cell.getCellFormula());  
			              break;  
			          default:  
			              logger.info("unsuported sell type");  
			          break;  
			          }  
			      }
			      if (bonus != null) {
			      	logger.info("********************************* uploadExcel Start!");
			      	bonusDao.insertSelective(bonus);
			      	BonusVo bonusInfo = bonusDao.queryBonus(bonus);
			      	bonusId = bonusInfo.getId();
			      	bonus = null;
			      }
			      if(bonusDetail != null){
			    	  if (bonusDetail.getPhoneNum() != null && !"".equals(bonusDetail.getPhoneNum())) {
			    		  bonusDetailList.add(bonusDetail);
			    	  }
			      }
			}  
			logger.info("*********************************uploadExcelDetail Start!");
			bonusDetailDao.insertList(bonusDetailList);
			 
			 fis.close();
		} catch (FileNotFoundException e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
        finally {
        	try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
          
		return resp;  
	}

	@Override
	public PagedResult<BonusVo> queryBonusList(BonusVo bonusVo) {
		PageUtil.startPage(bonusVo.getPageNo(), bonusVo.getPageSize());
		return BeanUtil.toPagedResult(bonusDao.queryList(bonusVo));
	}

	@Override
	public PagedResult<BonusDetailVo> queryBonusDetailList(BonusDetailVo bonusDetailVo) {
		PageUtil.startPage(bonusDetailVo.getPageNo(), bonusDetailVo.getPageSize());
		return BeanUtil.toPagedResult(bonusDetailDao.queryList(bonusDetailVo));
	}

	@Override
	public void submitBonusStatus(Map<String, Object> params) {
		bonusDao.updateState(params);
	}

	@Override
	public void grantBonus(Map<String, Object> params) {
		bonusDao.updateState(params);
	}

	@Transactional
	@Override
	public void deleteBonusInfo(Integer id) {
		bonusDao.deleteByPrimaryKey(id);
		bonusDetailDao.deleteByBonusId(id);
	}

	@Override
	public List<BonusDetailVo> queryBonusDetailListInfo(Map<String, Object> params) {
		List<BonusDetailVo> bonusVoList = null;
		try {
			 bonusVoList = bonusDetailDao.queryRepeatList(params);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return bonusVoList;
	}

	@Override
	public void updateBonusDetail(Map<String, Object> params, Map<String, Object> paramsDetail) {
		bonusDetailDao.updateStatus(paramsDetail);
		bonusDao.updateState(params);
	}

	@Override
	public Integer queryRepeat(Map<String, Object> params) {
		return bonusDetailDao.queryRepeat(params);
	}

	@Override
	public BonusVo uploadExcel(BonusVo bonusVo) {
		BonusVo bonus =new BonusVo();
		try {
			bonusDao.insertSelective(bonusVo);
			bonus =  bonusDao.queryBonus(bonusVo);
			return BeanCopy.copyNotNull(bonus, BonusVo.class);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public void grantBonusDetail(Map<String, Object> params, Map<String, Object> paramsDetail, List<UserMoney> userMoneyList) {
		bonusDetailDao.updateStatus(paramsDetail); //更新所有明细为成功
		bonusDao.updateState(params); //更新主表为成功
		//userMoneyDao.insertUserMoneyList(userMoneyList); //批量插入money日志表
	}

	@Override
	public BonusVo queryBonusInfo(BonusVo bonusVo) {
		return bonusDao.queryBonusInfo(bonusVo);
	}

}
