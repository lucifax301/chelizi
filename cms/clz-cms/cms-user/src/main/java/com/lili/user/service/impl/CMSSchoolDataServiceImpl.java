package com.lili.user.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.HttpConstant;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.StringUtil;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.model.Coach;
import com.lili.coach.service.CMSCoachService;
import com.lili.coach.service.CoachService;
import com.lili.school.model.Car;
import com.lili.school.model.CarNBDTO;
import com.lili.school.model.Field;
import com.lili.school.model.FieldBDTO;
import com.lili.school.model.Region;
import com.lili.school.service.CMSCarService;
import com.lili.school.service.CMSFieldService;
import com.lili.school.service.CMSRegionService;
import com.lili.school.service.CMSSchoolService;
import com.lili.student.manager.StudentManager;
import com.lili.student.model.Student;
import com.lili.student.model.StudentNBDTO;
import com.lili.student.service.CMSStudentService;
import com.lili.student.service.StudentService;
import com.lili.user.manager.CMSSchoolDataManager;
import com.lili.user.model.CarFile;
import com.lili.user.model.CarFileBDTO;
import com.lili.user.model.CoachFile;
import com.lili.user.model.CoachFileBDTO;
import com.lili.user.model.FieldFile;
import com.lili.user.model.FieldFileBDTO;
import com.lili.user.model.SchoolDataFile;
import com.lili.user.model.SchoolDataFileBDTO;
import com.lili.user.model.StudentFile;
import com.lili.user.model.StudentFileBDTO;
import com.lili.user.model.User;
import com.lili.user.service.CMSSchoolDataService;

public class CMSSchoolDataServiceImpl implements CMSSchoolDataService{

	protected static final Logger access = Logger.getLogger("CMSSchoolDataServiceImpl");

	@Autowired
	CMSCarService cmsCarService;
	@Autowired
	CMSSchoolService cmsSchoolService;
	@Autowired
	CMSSchoolDataManager cmsSchoolDataManager;
	@Autowired
	CMSRegionService cmsRegionService;
	@Autowired
	CMSCoachService cmsCoachService;
	@Autowired
	CMSStudentService cmsStudentService;
	@Autowired
	CMSFieldService cmsFieldService;
	@Autowired
	StudentService studentService;
	@Autowired
	CoachService coachService;
    @Autowired
    StudentManager studentManager;
    @Autowired
    CoachManager coachManager;

	@Override
	public ResponseMessage importFile(File file,String fileName, User user) throws Exception {

		// 解析文件
		//创建Excel工作簿对象	
		access.info("||| upload now");
		Workbook wb = null; 
		boolean isE2007 = false;
		if (fileName.endsWith("xlsx"))  {
			isE2007  = true;  
		}
		FileInputStream fis = null;
		//根据文件格式(2003或者2007)来初始化  
		try {
			fis = new FileInputStream(file);
			if(isE2007)  {
				wb = new XSSFWorkbook(fis);  
			}
			else  {
				wb = new HSSFWorkbook(fis);  
			}
		}catch (Exception e) {
		      return new ResponseMessage("错误:" + e.getMessage());
		}
		
		importField(wb,file, user);
		importCoach(wb,file, user);
		ArrayList<CarNBDTO> importCar = importCar(wb,file, user);
		rebuiltCoachCar(importCar);
		ArrayList<StudentNBDTO> dtos = importStudent(wb,file, user);
		rebuiltStudentCoach(dtos);

		return new ResponseMessage();
	}
	
	/**
	 * 重建学员和教练的关系
	 * @param dtos 
	 */
	private void rebuiltStudentCoach(ArrayList<StudentNBDTO> dtos) {
		if(dtos != null && dtos.size() > 0){
			for(StudentNBDTO dto : dtos){
				try {
					cmsCoachService.updateSC(dto);
					cmsCoachService.delSC(dto);
					cmsCoachService.insertSC(dto);
				} catch (Exception e) {
					access.error("||| exception happends when rebuiltStudentCoach : " + e.getMessage() + " ||| student phoneNum :" + dto.getPhoneNum()
							+ ",coachPhoneNum:" + dto.getCoachPhoneNum());
				}
			}
		}
	}

	/**
	 * 重新创建车和教练的关系<br>
	 * 先删除,再插入
	 * @param importCar
	 * @throws Exception 
	 */
	private void rebuiltCoachCar(ArrayList<CarNBDTO> carNBDTOs) throws Exception {
		if(carNBDTOs != null && carNBDTOs.size() > 0){
			for(CarNBDTO dto : carNBDTOs){
				try {
					//cmsCarService.updateCoachCarId(dto);
					cmsCarService.delCoachCar(dto);
					cmsCarService.insertCoachCar(dto);
				} catch (Exception e) {
					access.error("||| exception happends when rebuiltCoachCar : " + e.getMessage() + " ||| carNo :" + dto.getCarNo() + ",coachPhoneNum:" + dto.getCarOwner());
				}
			}
		}
	}


	private ArrayList<CarNBDTO> importCar(Workbook wb,File file, User user){

		ArrayList<CarNBDTO> carNBDTOs=new ArrayList<CarNBDTO>();
		List<CarFile> list=new ArrayList<CarFile>();
		String userName = user!=null?user.getUserName():null;
		int successCount = 0;
		int faildCount = 0;
		DecimalFormat df = new DecimalFormat("0");
		Long schoolId = null;
		String billNo = "JXCAR-" +  DateUtil.currentDateTime();
		
		
		Sheet sheet = wb.getSheetAt(2);     //获得第一个表单  
		Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
		while (rows.hasNext()) {  
			Row row = rows.next();  //获得行数据 
			if (row.getRowNum() >= 3) {

				try {
					access.info("Row #" + row.getRowNum());  //获得行号从0开始  
					Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
					
					
					
					String carType=null;
					String carNo=null;
					String carPower=null;
					String buyTime=null;
					String driveType=null;
					String carEngineNo=null;
					String carVin=null;
					String coachPhoneNum=null;
					String coachName=null;
					String schoolName=null;
					String city = null;
					CarFile carFile = new CarFile();
					while (cells.hasNext()) {
						Cell cell = cells.next();  
						access.info("Cell #" + cell.getColumnIndex());  
						

						switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                		case HSSFCell.CELL_TYPE_NUMERIC:  	
    						if(cell.getColumnIndex() == 0){
    							carType = String.valueOf(df.format(cell.getNumericCellValue())).trim();;
    						}else if(cell.getColumnIndex() == 1){
    							carNo = String.valueOf(df.format(cell.getNumericCellValue())).trim();;
    						}else if(cell.getColumnIndex() == 2){
    							carPower = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 3){
    							buyTime = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 4){
    							driveType = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 5){
    							carEngineNo = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 6){
    							carVin = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 7){
    							coachPhoneNum =  String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 8){
    							coachName = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 9){
    							schoolName = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 10){
    							city =  String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}
                			break;  
                		case HSSFCell.CELL_TYPE_STRING: 
    						if(cell.getColumnIndex() == 0){
    							carType = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 1){
    							carNo = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 2){
    							carPower = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 3){
    							buyTime = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 4){
    							driveType = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 5){
    							carEngineNo = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 6){
    							carVin = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 7){
    							coachPhoneNum = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 8){
    							coachName = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 9){
    							schoolName = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 10){
    							city =  cell.getStringCellValue().trim();
    						}
                			break;  
                		case HSSFCell.CELL_TYPE_BOOLEAN:  //boolean
                			access.info("************************ Boolean Value, Not Set value : " + cell.getBooleanCellValue());  
                			break;  
                		case HSSFCell.CELL_TYPE_FORMULA:  // 公式
                			access.info("************************ Formula Value, Not Set value : " +cell.getCellFormula());  
                			break;  
                		default:  
                			access.info("************************ Default Value, Not Set value : " + "unsuported sell type");  
                			break;  
						}
						
						
					}
					
					if (StringUtil.isNull(carType) && 
						StringUtil.isNull(carNo) && 
						StringUtil.isNull(carPower) && 
						StringUtil.isNull(buyTime) && 
						StringUtil.isNull(driveType) && 
						StringUtil.isNull(carEngineNo) && 
						StringUtil.isNull(carVin) && 
						StringUtil.isNull(coachPhoneNum) && 
						StringUtil.isNull(coachName) && 
						StringUtil.isNull(schoolName) && 
						StringUtil.isNull(city))
						continue;

					carFile.setBillNo(billNo);
					carFile.setCartype(carType);
					carFile.setCarno(carNo);
					carFile.setCarengineno(carEngineNo);
					carFile.setCarvin(carVin);
					carFile.setCoachphonenum(coachPhoneNum);
					carFile.setCoachname(coachName);
					carFile.setCreatorName(userName);
					carFile.setBuyTime(buyTime);
					carFile.setSchoolName(schoolName);
					carFile.setCityName(city);
					schoolId = cmsSchoolService.findSchoolIdByName(schoolName);
					Region region = cmsRegionService.findByName(city);
					if(StringUtil.isNull(coachPhoneNum)){
						carFile.setExtra("绑定教练: 手机号码为空");
						list.add(carFile);
						faildCount ++;
						continue;
					}
					Coach coach = cmsCoachService.findByPhone(coachPhoneNum);

					if(!StringUtil.isNull(carPower))
						carFile.setCarpower((byte)(carPower.contains("汽油")?1:2));
					if(!StringUtil.isNull(driveType))
						carFile.setDrivetype((byte)(driveType.equals("C1")?1:2));
					

					
					
					if(schoolId == null){
						carFile.setExtra("驾校名:" + schoolName + " 未能在系统中找到对应驾校,请检查");
						list.add(carFile);
						faildCount ++;
						continue;
					}
					carFile.setSchoolid(schoolId.intValue());

					
					if(region == null){
						carFile.setExtra("车牌号:" + carNo + " 更新时未找到对应城市:" + city);
						list.add(carFile);
						faildCount ++;
						continue;
					}
					
					if (coach == null){
						carFile.setExtra("绑定教练:" + coachPhoneNum + " 更新时未找到对应教练:" + coachName);
						list.add(carFile);
						faildCount ++;
						continue;
					}

					/**
					 * 非空字段判断
					 */
					if(StringUtil.isNullString(carNo) || StringUtil.isNullString(coachPhoneNum)
							 || StringUtil.isNullString(schoolName) || StringUtil.isNullString(city)){
						carFile.setExtra("车牌号|绑定教练手机号|所属驾校|所属城市 有一条为空");
						list.add(carFile);
						faildCount ++;
						continue;
					}

					/**
					 * 根据CarNo判断是否已存在
					 */
					CarNBDTO carBDTO = new CarNBDTO();
					carBDTO.setCarNo(carNo);
					List<Car> cars = cmsCarService.findCar(carBDTO);
					if(cars != null && cars.size() > 0){
						/**
						 * 存在,则更新
						 */
						Car car = cars.get(0);
						if(!StringUtil.isNull(carType))
							car.setCarType(carType);
						if(!StringUtil.isNull(carNo))
							car.setCarNo(carNo);
						if(!StringUtil.isNull(carPower))
							car.setCarPower((byte)(carPower.contains("汽油")?1:2));
						if(!StringUtil.isNull(driveType))
							car.setDriveType((byte)(driveType.equals("C1")?1:2));
						if(!StringUtil.isNull(carEngineNo))
							car.setCarEngineNo(carEngineNo);
						if(!StringUtil.isNull(carVin))
							car.setCarVin(carVin);
						if(!StringUtil.isNull(coachPhoneNum))
							car.setCarOwner(coachPhoneNum);
						if(!StringUtil.isNull(schoolId))
							car.setSchoolId(schoolId);
						if(!StringUtil.isNull(region))
							car.setCityId(region.getRid());
						ResponseMessage resp = null;
						try {
							resp = cmsCarService.updateOne(null,car);
						} catch (Exception e) {
							access.error("车牌:" + carNo + " 更新遇未知错误:" + e.getMessage());
							carFile.setExtra("车牌:" + carNo + " 更新遇未知错误");
							list.add(carFile);
							faildCount ++;
							continue;
						}
						if(resp.getCode() != HttpConstant.SUCCESS_CODE){
							carFile.setExtra("车牌:" + carNo + " 更新遇未知错误");
							list.add(carFile);
							faildCount ++;
							continue;
						}
					}else {
						Car car = new Car();
						car.setCarType(carType);
						car.setCarNo(carNo);
						if(!StringUtil.isNull(carPower))
							car.setCarPower((byte)(carPower.contains("汽油")?1:2));
						if(!StringUtil.isNull(driveType))
							car.setDriveType((byte)(driveType.equals("C1")?1:2));
						car.setCarEngineNo(carEngineNo);
						car.setCarVin(carVin);
						car.setCarOwner(coachPhoneNum);
						if(schoolId != null){
							car.setSchoolId(schoolId);
						}
						car.setCityId(region.getRid());
						ResponseMessage resp = null;
						try {
							resp = cmsCarService.insertOne(null,car);
						} catch (Exception e) {
							access.error("车牌:" + carNo + " 插入遇未知错误:" + e.getMessage());
							carFile.setExtra("车牌:" + carNo + " 插入遇未知错误");
							list.add(carFile);
							faildCount ++;
							continue;
						}
						if(resp.getCode() != HttpConstant.SUCCESS_CODE){
							carFile.setExtra("车牌:" + carNo + " 插入遇未知错误");
							list.add(carFile);
							faildCount ++;
							continue;
						}
					}
					

					/**
					 * 这个实体序列需要在教练插入后,用来删除教练和对应车的关系再插入,因为一辆车和一个教练只能在关系表中存在一条数据
					 */
					carBDTO.setCarOwner(coachPhoneNum);
					carNBDTOs.add(carBDTO);
					successCount ++;
				} catch (Exception e) {
					access.error("||| exception happends when import car : " + e.getMessage());
				}
				
				
			}
		}
		
		
		int insertCount = 0;
		if(list.size() > 0){
			for(CarFile _file : list){
				try {
					cmsSchoolDataManager.insertCarFile(_file);
					insertCount ++;
				} catch (Exception e) {
					access.error("||| error msg : " + e.getMessage());
				}
			}
		}
			
			if(insertCount > 0 || successCount > 0){
				SchoolDataFile schoolDataFile = new SchoolDataFile();
				schoolDataFile.setBillNo(billNo);
				schoolDataFile.setCreatorName(userName);
				schoolDataFile.setFailsum(faildCount);
				schoolDataFile.setSucsum(successCount);
				schoolDataFile.setSum(faildCount + successCount);
				if(schoolId != null){
					schoolDataFile.setSchoolid(schoolId.intValue());
				}
				schoolDataFile.setFiletype((byte)4);
				if(successCount == 0){
					schoolDataFile.setStatus((byte)3);
				}else {
					schoolDataFile.setStatus((byte)4);
				}
				try {
					cmsSchoolDataManager.insertSchoolDataFile(schoolDataFile);
				} catch (Exception e) {
					access.error("||| error : " + e.getMessage());
				}
			}

		

		return carNBDTOs;
	}

	private void importCoach(Workbook wb,File file, User user){
		List<CoachFile> list=new ArrayList<CoachFile>();
		int successCount = 0;
		int faildCount = 0;
		DecimalFormat df = new DecimalFormat("0");
		String userName = user!=null?user.getUserName():null;
		Long schoolId = null;
		String billNo = "JXCOACH-" + DateUtil.currentDateTime();
		
		
		Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
		Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
		while (rows.hasNext()) {  
			Row row = rows.next();  //获得行数据  
			if (row.getRowNum() >= 3) {

				try {
					access.info("Row #" + row.getRowNum());  //获得行号从0开始  
					Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  


					//第一个是列数，第二个是行数
					String name=null;
					String sex=null;
					String idNumber=null;
					String hometown=null;
					String phoneNum=null;
					String coachCard=null;
					String coachCardDate=null;
					String city=null;
					String schoolName=null;
					while (cells.hasNext()) {
						Cell cell = cells.next();  
						access.info("Cell #" + cell.getColumnIndex());  

						switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
						case HSSFCell.CELL_TYPE_NUMERIC:  	
							if(cell.getColumnIndex() == 0){
								name = String.valueOf(df.format(cell.getNumericCellValue())).trim();
							}else if(cell.getColumnIndex() == 1){
								sex = String.valueOf(df.format(cell.getNumericCellValue())).trim();
							}else if(cell.getColumnIndex() == 2){
								idNumber = String.valueOf(df.format(cell.getNumericCellValue())).trim();
							}else if(cell.getColumnIndex() == 3){
								hometown = String.valueOf(df.format(cell.getNumericCellValue())).trim();
							}else if(cell.getColumnIndex() == 4){
								phoneNum = String.valueOf(df.format(cell.getNumericCellValue())).trim();
							}else if(cell.getColumnIndex() == 5){
								coachCard = String.valueOf(df.format(cell.getNumericCellValue())).trim();
							}else if(cell.getColumnIndex() == 6){
								coachCardDate = String.valueOf(df.format(cell.getNumericCellValue())).trim();
							}else if(cell.getColumnIndex() == 7){
								city =  String.valueOf(df.format(cell.getNumericCellValue())).trim();
							}else if(cell.getColumnIndex() == 9){
								schoolName = String.valueOf(df.format(cell.getNumericCellValue())).trim();
							}
							break;  
						case HSSFCell.CELL_TYPE_STRING:  	
							if(cell.getColumnIndex() == 0){
								name = cell.getStringCellValue().trim();
							}else if(cell.getColumnIndex() == 1){
								sex = cell.getStringCellValue().trim();
							}else if(cell.getColumnIndex() == 2){
								idNumber = cell.getStringCellValue().trim();
							}else if(cell.getColumnIndex() == 3){
								hometown = cell.getStringCellValue().trim();
							}else if(cell.getColumnIndex() == 4){
								phoneNum = cell.getStringCellValue().trim();
							}else if(cell.getColumnIndex() == 5){
								coachCard = cell.getStringCellValue().trim();
							}else if(cell.getColumnIndex() == 6){
								coachCardDate = cell.getStringCellValue().trim();
							}else if(cell.getColumnIndex() == 7){
								city =  cell.getStringCellValue().trim();
							}else if(cell.getColumnIndex() == 9){
								schoolName = cell.getStringCellValue().trim();
							}
							break;  
						case HSSFCell.CELL_TYPE_BOOLEAN:  //boolean
						access.info("************************ Boolean Value, Not Set value : " + cell.getBooleanCellValue());  
						break;  
						case HSSFCell.CELL_TYPE_FORMULA:  // 公式
							access.info("************************ Formula Value, Not Set value : " +cell.getCellFormula());  
							break;  
						default:  
							access.info("************************ Default Value, Not Set value : " + "unsuported sell type");  
							break;  
						}


					}
					
					if (StringUtil.isNull(name) && 
						StringUtil.isNull(sex) && 
						StringUtil.isNull(idNumber) && 
						StringUtil.isNull(hometown) && 
						StringUtil.isNull(phoneNum) && 
						StringUtil.isNull(coachCard) && 
						StringUtil.isNull(coachCardDate) && 
						StringUtil.isNull(city) && 
						StringUtil.isNull(schoolName))
						continue;

					schoolId = cmsSchoolService.findSchoolIdByName(schoolName);
					Region region = cmsRegionService.findByName(city);

					CoachFile coachFile = new CoachFile();
					coachFile.setName(name);
					coachFile.setBillNo(billNo);
					coachFile.setSex((byte)("女".equals(sex)?0:1));
					coachFile.setIdnumber(idNumber);
					coachFile.setHometown(hometown);
					coachFile.setPhonenum(phoneNum);
					coachFile.setCoachcard(coachCard);
					coachFile.setSchoolName(schoolName);
					coachFile.setCityName(city);
					coachFile.setCoachcarddate(coachCardDate);
					
					if(region == null){
						coachFile.setExtra("未找到城市:" + city);
						list.add(coachFile);
						faildCount ++;
						continue;
					}else {
						coachFile.setCityid(region.getRid());
					}

					if(schoolId == null){
						coachFile.setExtra("驾校名:" + schoolName + " 未能在系统中找到对应驾校,请检查");
						list.add(coachFile);
						faildCount ++;
						continue;
					}
					coachFile.setSchoolid(schoolId.intValue());



					/**
					 * 非空字段判断
					 */
					if(StringUtil.isNullString(name) || StringUtil.isNullString(idNumber) || StringUtil.isNullString(schoolName)
							|| StringUtil.isNullString(phoneNum)){
						coachFile.setExtra("手机号|姓名|所属驾校|身份证号 至少有一条数据为空");
						list.add(coachFile);
						faildCount ++;
						continue;
					}

					String validateNum = validateNumber(phoneNum, idNumber);
					if(!StringUtil.isNull(validateNum)){
						coachFile.setExtra(validateNum);
						list.add(coachFile);
						faildCount ++;
						continue;
					}


					/**
					 * 判断教练手机号是否存在,存在则覆盖
					 */
					Coach coach = new Coach();
					coach.setPhoneNum(phoneNum);
					coach.setIdNumber(idNumber);
					List<Coach> coaches = cmsCoachService.findByNums(coach);
					if (coaches != null && coaches.size() > 1){
						coachFile.setExtra("身份信息 : " + name + "的手机身份证同时存在且对应不同的人 ");
						list.add(coachFile);
						faildCount ++;
						continue;
					}
					
					if(coaches != null && coaches.size() == 1){
						coach = coaches.get(0);
						com.lili.coach.dto.Coach _coach = coachManager.getCoachInfo(coach.getCoachId());
						_coach.setName(name);
						if(!StringUtil.isNull(sex))
							_coach.setSex(("女".equals(sex)?0:1));
						if(!StringUtil.isNull(idNumber))
							_coach.setIdNumber(idNumber);
						if(!StringUtil.isNull(hometown))
							_coach.setHometown(hometown);
						if(!StringUtil.isNull(phoneNum))
							_coach.setPhoneNum(phoneNum);
						if(!StringUtil.isNull(coachCard))
							_coach.setCoachCard(coachCard);
						if(!StringUtil.isNull(region))
							_coach.setCityId(region.getRid());
						_coach.setSchoolId(schoolId.intValue());
						_coach.setIsImport(1);
						_coach.setImportSrc(schoolName);
						
						long coach_count = 0;
						try {
							 coach_count = coachManager.updateCoach(_coach);
							 access.info("**********************************************coach_count: " + coach_count);
						} catch (Exception e) {
							if(e.getLocalizedMessage().contains("DuplicateKeyException")){
								coachFile.setExtra("教练身份证号可能已存在 ");
							}else {
								coachFile.setExtra("教练 : " + phoneNum + "更新出现错误 ");
							}
							access.error("教练 : " + phoneNum + "更新出现错误 :" + e.getMessage());
							list.add(coachFile);
							faildCount ++;
							continue;
						}
						
						if(coach_count <= 0){
							coachFile.setExtra("教练 : " + phoneNum + "更新出现错误 ");
							list.add(coachFile);
							faildCount ++;
							continue;
						}

					}else {	
						com.lili.coach.dto.Coach _coach = new com.lili.coach.dto.Coach();
						_coach.setName(name);
						if(!StringUtil.isNull(sex))
							_coach.setSex(("女".equals(sex)?0:1));
						_coach.setIdNumber(idNumber);
						_coach.setHometown(hometown);
						_coach.setPhoneNum(phoneNum);
						_coach.setCoachCard(coachCard);
						_coach.setCoachCardDate(coachCardDate);
						_coach.setSchoolId(schoolId.intValue());
						_coach.setCheckDriveIdState(2);
						_coach.setCheckIdState(2);
						_coach.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
						_coach.setCityId(region.getRid());
						_coach.setIsImport(1);
						_coach.setImportSrc(schoolName);

						long coach_count = 0;
						try {
							coach_count = coachManager.addCoach(_coach);
						} catch (Exception e) {
							if(e.getLocalizedMessage().contains("DuplicateKeyException")){
								coachFile.setExtra("教练身份证号可能已存在 ");
							}else {
								coachFile.setExtra("教练 : " + phoneNum + "更新出现错误 ");
							}
							access.error("教练 : " + phoneNum + "插入出现错误 :" + e.getMessage());
							list.add(coachFile);
							faildCount ++;
							continue;
						}
						if(coach_count <= 0){
							coachFile.setExtra("教练 : " + phoneNum + "插入出现错误 ");
							list.add(coachFile);
							faildCount ++;
							continue;
						}

					}

					successCount ++;
				} catch (Exception e) {
					e.printStackTrace();
					access.error("||| exception happends when import CoachFile : " + e.getMessage());
				}
			}
		}

		int insertCount = 0;
		if(list.size() > 0){
			for(CoachFile _file : list){
				try {
					cmsSchoolDataManager.insertCoachFile(_file);
					insertCount++;
				} catch (Exception e) {
					access.error("||| error msg : " + e.getMessage());
				}
			}
		}
			
			if(insertCount > 0 || successCount > 0){
			SchoolDataFile schoolDataFile = new SchoolDataFile();
			schoolDataFile.setBillNo(billNo);
			schoolDataFile.setCreatorName(userName);
			schoolDataFile.setFailsum(faildCount);
			schoolDataFile.setSucsum(successCount);
			schoolDataFile.setSum(faildCount + successCount);
			if(schoolId != null){
				schoolDataFile.setSchoolid(schoolId.intValue());
			}
			schoolDataFile.setFiletype((byte)1);
			if(successCount == 0){
				schoolDataFile.setStatus((byte)3);
			}else {
				schoolDataFile.setStatus((byte)4);
			}
			
			try {
				cmsSchoolDataManager.insertSchoolDataFile(schoolDataFile);
			} catch (Exception e) {
				access.error("||| error : " + e.getMessage());
			}
			}
	}
	
	/**
	 * 校验身份证号和手机号
	 * @return
	 */
	private String validateNumber(String phoneNum,String idNumber){
		if(StringUtil.isNull(phoneNum) || StringUtil.isNull(idNumber)){
			return "手机号或身份证号为空";
		}

		if(phoneNum.length() != 11 && idNumber.length() != 15 && idNumber.length() != 18){
			return "手机号长度应为11位,身份证号长度应为15或18位";
		}
		
		return null;
	}
	
	private ArrayList<StudentNBDTO> importStudent(Workbook wb,File file, User user){
		List<StudentFile> list=new ArrayList<StudentFile>();
		ArrayList<StudentNBDTO> dtos = new ArrayList<StudentNBDTO>();
		int successCount = 0;
		int faildCount = 0;
		Long schoolId = null;
		String billNo = "JXSTUDENT-" +  DateUtil.currentDateTime();
		DecimalFormat df = new DecimalFormat("0");
		String userName = user!=null?user.getUserName():null;
		
		
		Sheet sheet = wb.getSheetAt(1);     //获得第一个表单  
		Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
		while (rows.hasNext()) {  
			Row row = rows.next();  //获得行数据  
			if (row.getRowNum() >= 3) {

				try {
					access.info("Row #" + row.getRowNum());  //获得行号从0开始  
					Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
					
					
					//第一个是列数，第二个是行数
					String name=null;
					String carType=null;
					String idNumber=null;
					String sex=null;
					String age=null;
					String hometown=null;
					String phoneNum=null;
					String entryTime=null;
					String coachPhoneNum =null;
					String coachName=null;
					String flowNo=null;
					String course1=null;
					String course2=null;
					String course3=null;
					String course4=null;
					String city =null;
					String schoolName=null;
					while (cells.hasNext()) {
						Cell cell = cells.next();  
						access.info("Cell #" + cell.getColumnIndex()); 

						switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                		case HSSFCell.CELL_TYPE_NUMERIC:  
                			if(cell.getColumnIndex() == 0){
    							name = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 1){
    							carType = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 2){
    							idNumber = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 3){
    							sex = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 4){
    							age = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 5){
    							hometown = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 6){
    							phoneNum = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 7){
    							entryTime =  String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 8){
    							coachPhoneNum = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 9){
    							coachName = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 10){
    							flowNo = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 11){
    							course1 = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 12){
    							course2 = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 13){
    							course3 = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 14){
    							course4 = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 15){
    							city = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 16){
    							schoolName = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}
                			break;  
                		case HSSFCell.CELL_TYPE_STRING:  
                			if(cell.getColumnIndex() == 0){
    							name = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 1){
    							carType = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 2){
    							idNumber = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 3){
    							sex = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 4){
    							age = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 5){
    							hometown = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 6){
    							phoneNum = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 7){
    							entryTime =  cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 8){
    							coachPhoneNum = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 9){
    							coachName = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 10){
    							flowNo = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 11){
    							course1 = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 12){
    							course2 = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 13){
    							course3 = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 14){
    							course4 = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 15){
    							city = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 16){
    							schoolName = cell.getStringCellValue().trim();
    						}
                			break;  
                		case HSSFCell.CELL_TYPE_BOOLEAN:  //boolean
                			access.info("************************ Boolean Value, Not Set value : " + cell.getBooleanCellValue());  
                			break;  
                		case HSSFCell.CELL_TYPE_FORMULA:  // 公式
                			access.info("************************ Formula Value, Not Set value : " +cell.getCellFormula());  
                			break;  
                		default:  
                			access.info("************************ Default Value, Not Set value : " + "unsuported sell type");  
                			break;  
						}
						
						
						
					}
					
					if (StringUtil.isNull(name) && 
						StringUtil.isNull(carType) && 
						StringUtil.isNull(idNumber) && 
						StringUtil.isNull(sex) && 
						StringUtil.isNull(age) && 
						StringUtil.isNull(hometown) && 
						StringUtil.isNull(phoneNum) && 
						StringUtil.isNull(entryTime) && 
						StringUtil.isNull(coachPhoneNum ) && 
						StringUtil.isNull(coachName) && 
						StringUtil.isNull(flowNo) && 
						StringUtil.isNull(course1) && 
						StringUtil.isNull(course2) && 
						StringUtil.isNull(course3) && 
						StringUtil.isNull(course4) && 
						StringUtil.isNull(city ) && 
						StringUtil.isNull(schoolName))
						continue;


					schoolId = cmsSchoolService.findSchoolIdByName(schoolName);
					Region region = cmsRegionService.findByName(city);
					
					StudentFile studentFile = new StudentFile();
					studentFile.setBillNo(billNo);
					studentFile.setName(name);
					studentFile.setApplycartype(carType);
					studentFile.setSchoolName(schoolName);
					studentFile.setCityName(city);
					studentFile.setIdnumber(idNumber);
					if(!StringUtil.isNull(age))
						studentFile.setAge(Short.valueOf(age));
					studentFile.setHometown(hometown);
					studentFile.setPhonenum(phoneNum);
					studentFile.setCreatetime(DateUtil.now());
					if(!StringUtil.isNull(sex))
						studentFile.setSex((byte)("女".equals(sex)?0:1));
					studentFile.setStucoachname(coachName);
					studentFile.setFlowno(flowNo);
					studentFile.setCourse1(StringUtil.isNull(course1)?null:Short.valueOf(course1));
					studentFile.setCourse2(StringUtil.isNull(course2)?null:Short.valueOf(course2));
					studentFile.setCourse3(StringUtil.isNull(course3)?null:Short.valueOf(course3));
					studentFile.setCourse4(StringUtil.isNull(course4)?null:Short.valueOf(course4));
					if(region == null){
						studentFile.setExtra("未找到城市:" + city);
						list.add(studentFile);
						faildCount ++;
						continue;
					}else {
						studentFile.setCityid(region.getRid());
					}
					

					if(schoolId == null){
						studentFile.setExtra("驾校名:" + schoolName + " 未能在系统中找到对应驾校,请检查");
						list.add(studentFile);
						faildCount ++;
						continue;
					}
					studentFile.setSchoolid(schoolId.intValue());
					

					/**
					 * 非空字段判断
					 */
					if(StringUtil.isNullString(name) || StringUtil.isNullString(idNumber) || StringUtil.isNullString(schoolName)
							 || StringUtil.isNullString(phoneNum)){
						studentFile.setExtra("手机号|姓名|所属驾校|身份证号 有一条为空");
						list.add(studentFile);
						faildCount ++;
						continue;
					}
					String validateNum = validateNumber(phoneNum, idNumber);
					if(!StringUtil.isNull(validateNum)){
						studentFile.setExtra(validateNum);
						list.add(studentFile);
						faildCount ++;
						continue;
					}
					
					
					Student student = new Student();
					student.setPhoneNum(phoneNum);
					student.setIdNumber(idNumber);
					List<Student> students = cmsStudentService.findByNums(student);
					if (students != null && students.size() > 1){
						studentFile.setExtra("身份信息: " + name + "手机身份证同时存在且对应不同的人 ");
						list.add(studentFile);
						faildCount ++;
						continue;
					}
					
					if(students != null && students.size() == 1){
						student = students.get(0);
						com.lili.student.dto.Student _student = studentManager.getStudentInfo(student.getStudentId());
						_student.setName(name);
						if(!StringUtil.isNull(carType)){
							_student.setApplyCarType((carType.equals("C2")?"2":"1"));
							_student.setDrType((byte)(carType.equals("C2")?2:1));
						}
						if(!StringUtil.isNull(idNumber))
							_student.setIdNumber(idNumber);
						if(!StringUtil.isNull(age))
							_student.setAge(Short.valueOf(age));
						if(!StringUtil.isNull(hometown))
							_student.setHometown(hometown);
						if(!StringUtil.isNull(phoneNum))
							_student.setPhoneNum(phoneNum);
						if(!StringUtil.isNull(sex))
							_student.setSex((byte)("女".equals(sex)?0:1));
						_student.setImportSrc(schoolName);
//						_student.setRegisterTime(DateUtil.now());
						
						Coach coach = cmsCoachService.findByPhone(coachPhoneNum);
						if(coach != null){
							_student.setStuCoachEmpID(Integer.valueOf(coach.getCoachId()+""));
						} else if (!StringUtil.isNull(coachPhoneNum)){
							studentFile.setExtra("绑定教练：" + coachPhoneNum + "手机号不存在");
							list.add(studentFile);
							faildCount ++;
							continue;
						}
						
						if(!StringUtil.isNull(flowNo))
							_student.setFlowNo(flowNo);
						_student.setCourse1(StringUtil.isNull(course1)?null:Short.valueOf(course1));
						_student.setCourse2(StringUtil.isNull(course2)?null:Short.valueOf(course2));
						_student.setCourse3(StringUtil.isNull(course3)?null:Short.valueOf(course3));
						_student.setCourse4(StringUtil.isNull(course4)?null:Short.valueOf(course4));
						if(!StringUtil.isNull(region))
							_student.setCityId(region.getRid());
						if(!StringUtil.isNull(schoolId))
							_student.setSchoolId(schoolId.byteValue());
						_student.setIsImport((byte)1);
						long count = 0;
						try {
							count = studentManager.updateStudent(_student);
							access.info("**********************************************student_count: " + count);
						} catch (Exception e) {
							if(e.getLocalizedMessage().contains("DuplicateKeyException")){
								studentFile.setExtra("学员身份证可能已存在");
							}else {
								studentFile.setExtra("学员更新失败");
							}
							list.add(studentFile);
							faildCount ++;
							continue;
						}
						if(count <= 0){
							studentFile.setExtra("学员更新失败");
							list.add(studentFile);
							faildCount ++;
							continue;
						}
					}else {
						com.lili.student.dto.Student _student = new com.lili.student.dto.Student();
						_student.setName(name);
						if(!StringUtil.isNull(carType)){
							_student.setApplyCarType((carType.equals("C2")?"2":"1"));
							_student.setDrType((byte)(carType.equals("C2")?2:1));
						}
						_student.setIdNumber(idNumber);
						_student.setAge(StringUtil.isNull(age)?null:Short.valueOf(age));
						_student.setHometown(hometown);
						_student.setPhoneNum(phoneNum);
//						_student.setRegisterTime(DateUtil.now());
						if(!StringUtil.isNull(sex))
							_student.setSex((byte)("女".equals(sex)?0:1));
						_student.setApplyexam(5);
						_student.setApplystate(0);
						_student.setIsImport((byte)1);
						_student.setImportSrc(schoolName);
						
						if (!StringUtil.isNull(coachPhoneNum)){
							Coach coach = cmsCoachService.findByPhone(coachPhoneNum);
							if(coach != null){
								_student.setStuCoachEmpID(Integer.valueOf(coach.getCoachId()+""));
							} else {
								studentFile.setExtra("绑定教练：" + coachPhoneNum + "手机号不存在");
								list.add(studentFile);
								faildCount ++;
								continue;
							}
						}
						
						_student.setFlowNo(flowNo);
						_student.setCourse1(StringUtil.isNull(course1)?null:Short.valueOf(course1));
						_student.setCourse2(StringUtil.isNull(course2)?null:Short.valueOf(course2));
						_student.setCourse3(StringUtil.isNull(course3)?null:Short.valueOf(course3));
						_student.setCourse4(StringUtil.isNull(course4)?null:Short.valueOf(course4));
						_student.setCityId(region.getRid());
						_student.setSchoolId(schoolId.byteValue());
						
						long student_count = 0;
						try {
							student_count = studentManager.addStudent(_student);
						} catch (Exception e) {
							if(e.getLocalizedMessage().contains("DuplicateKeyException")){
								studentFile.setExtra("学员身份证可能已存在");
							}else {
								studentFile.setExtra("学员更新失败");
							}
							
							list.add(studentFile);
							faildCount ++;
							continue;
						}
						if(student_count <= 0){
							studentFile.setExtra("未知错误");
							list.add(studentFile);
							faildCount ++;
							continue;
						}
					}
					
					if (!StringUtil.isNull(coachPhoneNum)){
						StudentNBDTO dto = new StudentNBDTO();
						dto.setPhoneNum(phoneNum);
						dto.setCoachPhoneNum(coachPhoneNum);
						dtos.add(dto);
					}
					successCount ++;
				} catch (Exception e) {
					access.error("||| exception happends when import student : " + e.getMessage());
				}
				
				
			}
		}
		

		int insertCount = 0;
		if(list.size() > 0){
			for(StudentFile _file : list){
				try {
					cmsSchoolDataManager.insertStudentFile(_file);
					insertCount++;
				} catch (Exception e) {
					access.error("||| error msg : " + e.getMessage());
				}
			}
		}
			
			if(insertCount > 0 || successCount > 0){
				SchoolDataFile schoolDataFile = new SchoolDataFile();
				schoolDataFile.setBillNo(billNo);
				schoolDataFile.setCreatorName(userName);
				schoolDataFile.setFailsum(faildCount);
				schoolDataFile.setSucsum(successCount);
				schoolDataFile.setSum(faildCount + successCount);

				if(schoolId != null){
					schoolDataFile.setSchoolid(schoolId.intValue());
				}
				schoolDataFile.setFiletype((byte)2);
				if(successCount == 0){
					schoolDataFile.setStatus((byte)3);
				}else {
					schoolDataFile.setStatus((byte)4);
				}
				
				try {
					cmsSchoolDataManager.insertSchoolDataFile(schoolDataFile);
				} catch (Exception e) {
					access.error("||| error : " + e.getMessage());
				}
			}

	
		return dtos;
		
	}

	/**
	 * 导入场地
	 * @param wb
	 * @param file
	 * @param user
	 */
	private void importField(Workbook wb,File file, User user){
		List<FieldFile> list=new ArrayList<FieldFile>();
		int successCount = 0;
		int faildCount = 0;
		Long schoolId = null;
		String billNo = "JXFIELD-" +  DateUtil.currentDateTime();
		DecimalFormat df = new DecimalFormat("0");
		String userName = user!=null?user.getUserName():null;
		
		
		Sheet sheet = wb.getSheetAt(3);     //获得第一个表单  
		Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
		while (rows.hasNext()) {  
			Row row = rows.next();  //获得行数据  
			if (row.getRowNum() >= 3) {

				try {
					access.info("Row #" + row.getRowNum());  //获得行号从0开始  
					Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
					
					
					//第一个是列数，第二个是行数
					String schoolName=null;
					Double lge=null;
					Double lae=null;
					String city=null;
					String area=null;
					String name=null;
					String posDesc=null;
					String reverseLim=null;
					String phoneNum=null;
					while (cells.hasNext()) {
						Cell cell = cells.next();  
						access.info("Cell #" + cell.getColumnIndex());  
						
						switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                		case HSSFCell.CELL_TYPE_NUMERIC:  
    						if(cell.getColumnIndex() == 0){
    							schoolName = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 1){
    							lge = cell.getNumericCellValue();
    						}else if(cell.getColumnIndex() == 2){
    							lae = cell.getNumericCellValue();
    						}else if(cell.getColumnIndex() == 3){
    							city = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 4){
    							area = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 5){
    							name = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 6){
    							posDesc = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 7){
    							reverseLim  = String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}else if(cell.getColumnIndex() == 8){
    							phoneNum =  String.valueOf(df.format(cell.getNumericCellValue())).trim();
    						}
                			break;  
                		case HSSFCell.CELL_TYPE_STRING:  
    						if(cell.getColumnIndex() == 0){
    							schoolName = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 1){
    							lge = Double.valueOf(cell.getStringCellValue());
    						}else if(cell.getColumnIndex() == 2){
    							lae = Double.valueOf(cell.getStringCellValue());
    						}else if(cell.getColumnIndex() == 3){
    							city = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 4){
    							area = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 5){
    							name = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 6){
    							posDesc = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 7){
    							reverseLim  = cell.getStringCellValue().trim();
    						}else if(cell.getColumnIndex() == 8){
    							phoneNum = cell.getStringCellValue().trim();
    						}	
                			break;  
                		case HSSFCell.CELL_TYPE_BOOLEAN:  //boolean
                			access.info("************************ Boolean Value, Not Set value : " + cell.getBooleanCellValue());  
                			break;  
                		case HSSFCell.CELL_TYPE_FORMULA:  // 公式
                			access.info("************************ Formula Value, Not Set value : " +cell.getCellFormula());  
                			break;  
                		default:  
                			access.info("************************ Default Value, Not Set value : " + "unsuported sell type");  
                			break;  
						}
					}

					if (StringUtil.isNull(schoolName) &&
						StringUtil.isNull(lge) &&
						StringUtil.isNull(lae) &&
						StringUtil.isNull(city) &&
						StringUtil.isNull(area) &&
						StringUtil.isNull(name) &&
						StringUtil.isNull(posDesc) &&
						StringUtil.isNull(reverseLim) &&
						StringUtil.isNull(phoneNum))
						continue;
					
					schoolId = cmsSchoolService.findSchoolIdByName(schoolName);
					Region cityRegion = cmsRegionService.findByName(city);
					Region regionDto = new Region();
					regionDto.setPid(cityRegion.getRid());
					regionDto.setRegion(area);
					List<Region> regions = cmsRegionService.findRegionList(regionDto);
					Region region = null;
					if (regions != null && regions.size() == 1)
						region = regions.get(0);
					
					FieldFile fieldFile = new FieldFile();
					fieldFile.setSchoolName(schoolName);
					fieldFile.setCityName(city + area);
					if(!StringUtil.isNull(lae))
						fieldFile.setLae(new BigDecimal(lae));
					if(!StringUtil.isNull(lge))
						fieldFile.setLge(new BigDecimal(lge));
					fieldFile.setName(name);
					fieldFile.setPosdesc(posDesc);
					fieldFile.setBillNo(billNo);
					if(!StringUtil.isNull(reverseLim))
						fieldFile.setReverselim(Integer.valueOf(reverseLim));
					fieldFile.setPhonenum(phoneNum);
					if(region == null){
						fieldFile.setExtra("未找到城市/区域:" + city + area);
						list.add(fieldFile);
						faildCount ++;
						continue;
					}else {
						fieldFile.setRegion(region.getRid());
					}
					
					if(schoolId == null){
						fieldFile.setExtra("驾校名:" + schoolName + " 未能在系统中找到对应驾校,请检查");
						list.add(fieldFile);
						faildCount ++;
						continue;
					}

					fieldFile.setSchoolid(Integer.valueOf(schoolId+""));

					/**
					 * 非空字段判断
					 */
					if(StringUtil.isNullString(name) || StringUtil.isNullString(schoolName)
							 || StringUtil.isNullString(phoneNum)){
						fieldFile.setExtra("联系方式|训练场名|所属驾校 有一条为空");
						list.add(fieldFile);
						faildCount ++;
						continue;
					}
					
					FieldBDTO dto = new FieldBDTO();
					dto.setName(name);
					Field _field = cmsFieldService.findOne(dto);
					if(_field != null){

						if(!StringUtil.isNull(schoolId))
							_field.setSchoolId(schoolId);
						if(!StringUtil.isNull(lae))
							_field.setLae(lae);
						if(!StringUtil.isNull(lge))
							_field.setLge(lge);
						if(!StringUtil.isNull(region))
							_field.setRegion(region.getRid().longValue());
						if(!StringUtil.isNull(name))
							_field.setName(name);
						if(!StringUtil.isNull(posDesc))
							_field.setPosDesc(posDesc);
						if(!StringUtil.isNull(reverseLim))
							_field.setReverseLim(Integer.valueOf(reverseLim));
						if(!StringUtil.isNull(phoneNum))
							_field.setPhoneNum(phoneNum);
						ResponseMessage resp = null;
						try {
							resp = cmsFieldService.updateOne(null, _field);
						} catch (Exception e) {
							fieldFile.setExtra("更新已有训练场失败" + resp.getCode());
							list.add(fieldFile);
							faildCount ++;
							continue;
						}

						if(resp.getCode() != HttpConstant.SUCCESS_CODE){
							fieldFile.setExtra("更新已有训练场失败" + resp.getCode());
							list.add(fieldFile);
							faildCount ++;
							continue;
						}
					}else {
						
						Field field = new Field();
						field.setSchoolId(schoolId);
						field.setLae(lae);
						field.setLge(lge);
						field.setRegion(region.getRid().longValue());
						field.setName(name);
						field.setPosDesc(posDesc);
						if(!StringUtil.isNull(reverseLim))
							field.setReverseLim(Integer.valueOf(reverseLim));
						field.setPhoneNum(phoneNum);
						
						ResponseMessage resp = null;
						try {
							resp = cmsFieldService.insertOne(null, field);
						} catch (Exception e) {
							fieldFile.setExtra("未知错误");
							list.add(fieldFile);
							faildCount ++;
							continue;
						}
						if(resp.getCode() != HttpConstant.SUCCESS_CODE){
							fieldFile.setExtra("未知错误");
							list.add(fieldFile);
							faildCount ++;
							continue;
						}
						
					}
					
					
					successCount ++;
				} catch (Exception e) {
					access.error("||| exception happends when import car : " + e.getMessage());
				}
				
				
			}
		}
		
		int insertCount = 0;
		if(list.size() > 0){
			for(FieldFile _file : list){
				try {
					cmsSchoolDataManager.insertFieldFile(_file);
					insertCount++;
				} catch (Exception e) {
					access.error("||| error msg : " + e.getMessage());
				}
			}
		}

			
			if(insertCount > 0 || successCount > 0){
				SchoolDataFile schoolDataFile = new SchoolDataFile();
				schoolDataFile.setBillNo(billNo);
				schoolDataFile.setCreatorName(userName);
				schoolDataFile.setFailsum(faildCount);
				schoolDataFile.setSucsum(successCount);
				schoolDataFile.setSum(faildCount + successCount);
				if(schoolId != null){
					schoolDataFile.setSchoolid(schoolId.intValue());
				}
				schoolDataFile.setFiletype((byte)3);
				if(successCount == 0){
					schoolDataFile.setStatus((byte)3);
				}else {
					schoolDataFile.setStatus((byte)4);
				}
				
				try {
					cmsSchoolDataManager.insertSchoolDataFile(schoolDataFile);
				} catch (Exception e) {
					access.error("||| error : " + e.getMessage());
				}
			}
			
		
		
	}

	@Override
	public ResponseMessage findStudentBatch(StudentFileBDTO dto) {
		PagedResult<StudentFile> batch = cmsSchoolDataManager.findStudentBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findSchoolDataBatch(SchoolDataFileBDTO dto) {
		PagedResult<SchoolDataFile> batch = cmsSchoolDataManager.findSchoolDataBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findCarBatch(CarFileBDTO dto) {
		PagedResult<CarFile> batch = cmsSchoolDataManager.findCarBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findFieldBatch(FieldFileBDTO dto) {
		PagedResult<FieldFile> batch = cmsSchoolDataManager.findFieldBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage findCoachBatch(CoachFileBDTO dto) {
		PagedResult<CoachFile> batch = cmsSchoolDataManager.findCoachBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}
	
}
