package com.lili;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.exchange.dto.ExEvalDTO;
import com.lili.exchange.dto.ExPayDTO;
import com.lili.exchange.dto.ExSubDTO;
import com.lili.exchange.dto.ExTrainDTO;
import com.lili.exchange.mapper.ExchangeMapper;
import com.lili.exchange.service.ExchangeService;
import com.lili.exchange.vo.ExEvalPostVo;
import com.lili.exchange.vo.ExPayPostVo;
import com.lili.exchange.vo.ExResult;
import com.lili.exchange.vo.ExSubPostVo;
import com.lili.exchange.vo.ExTrainPostVo;

@ContextConfiguration(locations = {"classpath:spring-init.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PostDataQuartz {

	private final Logger log = Logger.getLogger(PostDataQuartz.class);
	@Autowired
	private ExchangeService exchangeService;
	@Autowired
	private ExchangeMapper exchangeMapper;
	
	private final String coachTags = "想学的和所教差别很大,经常让我独自一人练车,跟预约课程有点不一样,经常让我独自一人练车,和预约课程内容一样,很少离车教学,有目标，效果好,教得很耐心,教学有目标，效果好，比较适合,"
			+ "教学很有耐心,很凶、态度恶劣,教练不理我,总玩手机，长时间听电话,用语不文明,有问才有答,长时间听电话,对我挺好,耐心指导，有问必答,接打电话也很快,很温柔，好喜欢,耐心指导，有问必答,偶尔接打电话，不影响教学,"
			+ "教练温柔，经常有鼓励,鼓励我不懂就问,偶尔接打电话会先征得我同意,行为举止不检点,车有刮痕、有难闻的异味,有故意肢体接触，很反感,教练有点随意、吃零食,有点邋遢、有烟味,偶有挂挡时碰我身体,教练看着还行,"
			+ "挺爱干净，车内无异味,偶有抓手帮忙打方向,教练好帅,爱干净、车很新,有职业素养，还很绅士,形象好，又爱干净,课程含接送但未接送,要求我提前点击下课,有接送，但偶有迟到情况,把握教学时间很合理,接送准时,"
			+ "把握教学时间很合理,接送准时,根据我掌握情况适时延长教学时间,提前到达约定地点,根据我掌握情况适时延长教学时间,提醒我想学车要送礼物,吓我，不交钱过不了,偶有暗示我送礼,建议我交钱才能过,从没提送礼的事,"
			+ "提醒不需要交过考费,教练倡议廉政教学,提醒我，注意包过的骗子,教练专门为学员备有饮用水";
	private final String coachTagIds = "010101,010102,020101,020102,030101,030102,040101,040102,050101,050102,010201,010202,010203,020201,020202,020203,"
			+ "030201,030202,030203,040201,040202,040203,050201,050202,050203,010301,010302,010303,020301,020302,020303,030301,030302,030303,040301,"
			+ "040302,050301,050302,010401,010402,020401,020402,030401,030402,040401,040402,050401,050402,010501,010502,020501,020502,030501,030502,"
			+ "040501,040502,050501";
	private final String studentExtraTags = ",虚心请教,主动好学,积极互动,心态好,很快掌握,学得特快,淡定,比较放松,独立上路,比较紧张,自尊心强,理解偏差,非常紧张,不太认真,不熟练,手脚发抖,主动放弃,易激动,情绪失控"; 
	private final String studentExtraTagIds = ",71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89";
	private final String student2Tags = "倒车入库,倒车入库,侧方停车,侧方停车,坡道定点,坡道定点,直角转弯,直角转弯,曲线行驶,曲线行驶,科目二应试训练,科目二应试训练,科目二考场模拟,科目二考场模拟" + studentExtraTags;
	private final String student2TagIds = "26,60,27,61,28,62,29,63,30,64,50,65,51,66" + studentExtraTagIds;
	private final String student3Tags = "科目三基础训练,科目三基础训练,科目三应试训练,科目三应试训练,科目三考场模拟,科目三考场模拟" + studentExtraTags;
	private final String student3TagIds = "52,67,53,68,54,69" + studentExtraTagIds;

	@Test
	public void sendData(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String end = sdf.format(calendar.getTime());
		calendar.add(Calendar.DATE, -1);
		end = sdf.format(calendar.getTime());
		calendar.add(Calendar.DATE, -1);
		String start = sdf.format(calendar.getTime());
			//添加预约信息
			ExResult<ExSubDTO> erSub;
			erSub = new ExResult<>();
			try {
				List<ExSubPostVo> espVos = exchangeMapper.selectBookInfo(start, end);
				if (espVos != null) {
					for (ExSubPostVo espVo : espVos) {
						espVo.setStartTime(espVo.getStartTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						espVo.setEndTime(espVo.getEndTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						espVo.setInputTime(espVo.getInputTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						erSub = exchangeService.postBookInfo(espVo);
						if (erSub == null)
							log.error("*********** postBookInfo : response body is empty " + "#info: " + espVo.getRecnum());
						else if (erSub.getErrorCode() != 0)
							log.error("*********** postBookInfo : " + erSub.getMessage() + "#info: " + espVo.getRecnum());
						else
							log.info("*********** postBookInfo : " + erSub.getMessage() + "#info: " + espVo.getRecnum());
					}
				}
			} catch (Exception e) {
				log.error("*********** postBookInfo : Exception! #info: " + e.getMessage());
				e.printStackTrace();
			}
			
			//添加预约取消信息
			try {
				List<ExSubPostVo> espCancels = exchangeMapper.selectBookCancelInfo(start, end);
				if (espCancels != null) {
					for (ExSubPostVo espCancel : espCancels) {
						espCancel.setInputTime(espCancel.getInputTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						erSub = exchangeService.putBookInfoCancel("chelizi", espCancel.getRecnum(), espCancel.getInputTime(), 
								"chelizi", "车厘子", espCancel.getRemark());
						if (erSub == null)
							log.error("*********** putBookInfoCancel : response body is empty " + "#info: " + espCancel.getRecnum());
						else if (erSub.getErrorCode() != 0)
							log.error("*********** putBookInfoCancel : " + erSub.getMessage() + "#info: " + espCancel.getRecnum());
						else
							log.info("*********** putBookInfoCancel : " + erSub.getMessage() + "#info: " + espCancel.getRecnum());
					}
				}
			} catch (Exception e) {
				log.error("*********** putBookInfoCancel : Exception! #info: " + e.getMessage());
				e.printStackTrace();
			}
			
			//添加培训时长
			ExResult<ExTrainDTO> erTrain = new ExResult<>();
			try {
				List<ExTrainPostVo> etpVos = exchangeMapper.selectTrainInfo(start, end);
				if (etpVos != null) {
					for (ExTrainPostVo etpVo : etpVos) {
						etpVo.setStartTime(etpVo.getStartTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						etpVo.setEndTime(etpVo.getEndTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						etpVo.setInputTime(etpVo.getInputTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						erTrain = exchangeService.postTrainInfo(etpVo);
						if (erTrain == null)
							log.error("*********** postTrainInfo : response body is empty " + "#info: " + etpVo.getRecnum());
						else if (erTrain.getErrorCode() != 0)
							log.error("*********** postTrainInfo : " + erTrain.getMessage() + "#info: " + etpVo.getRecnum());
						else
							log.info("*********** postTrainInfo : " + erTrain.getMessage() + "#info: " + etpVo.getRecnum());
					}
				}
			} catch (Exception e) {
				log.error("*********** postTrainInfo : Exception! #info: " + e.getMessage());
				e.printStackTrace();
			}
			
			//添加喱喱学员支付信息
			ExResult<ExPayDTO> erPay = new ExResult<>();
			try {
				List<ExPayPostVo> eppVos = exchangeMapper.selectPayInfo(start, end);
				if (eppVos != null) {
					for (ExPayPostVo eppVo : eppVos) {
						eppVo.setInputTime(eppVo.getInputTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						eppVo.setTrainBeginTime(eppVo.getTrainBeginTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						eppVo.setTrainEndTime(eppVo.getTrainEndTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						if ("weixin".equals(eppVo.getPayType()))
							eppVo.setPayType("01");
						else if ("zhifubao".equals(eppVo.getPayType()))
							eppVo.setPayType("02");
						else if ("yinlian".equals(eppVo.getPayType()))
							eppVo.setPayType("03");
						else 
							eppVo.setPayType("05");
						erPay = exchangeService.postPayInfo(eppVo);
						if (erPay == null)
							log.error("*********** postPayInfo : response body is empty " + "#info: " + eppVo.getRecnum());
						else if (erPay.getErrorCode() != 0)
							log.error("*********** postPayInfo : " + erPay.getMessage() + "#info: " + eppVo.getRecnum());
						else
							log.info("*********** postPayInfo : " + erPay.getMessage() + "#info: " + eppVo.getRecnum());
					}
				}
			} catch (Exception e) {
				log.error("*********** postPayInfo : Exception! #info: " + e.getMessage());
				e.printStackTrace();
			}
			
			//添加驾校学员支付信息
			try {
				List<ExPayPostVo> eppJXVos = exchangeMapper.selectJXPayInfo(start, end);
				if (eppJXVos != null) {
					for (ExPayPostVo eppJXVo : eppJXVos) {
						eppJXVo.setInputTime(eppJXVo.getInputTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						eppJXVo.setTrainBeginTime(eppJXVo.getTrainBeginTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						eppJXVo.setTrainEndTime(eppJXVo.getTrainEndTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						eppJXVo.setPayType("05");
						erPay = exchangeService.postPayInfo(eppJXVo);
						if (erPay == null)
							log.error("*********** postPayInfo : response body is empty " + "#info: " + eppJXVo.getRecnum());
						else if (erPay.getErrorCode() != 0)
							log.error("*********** postPayInfo : " + erPay.getMessage() + "#info: " + eppJXVo.getRecnum());
						else
							log.info("*********** postPayInfo : " + erPay.getMessage() + "#info: " + eppJXVo.getRecnum());
					}
				}
			} catch (Exception e) {
				log.error("*********** postPayInfo : Exception! #info: " + e.getMessage());
				e.printStackTrace();
			}
			
			//添加教练评价信息
			ExResult<ExEvalDTO> erEval = new ExResult<>();
			List<String> names = null;
			String nameString = null;
			List<ExEvalPostVo> eepVos;
			try {
				eepVos = exchangeMapper.selectCoachEvalInfo(start, end);
				if (eepVos != null) {
					for (ExEvalPostVo eepVo : eepVos) {
						eepVo.setInputTime(eepVo.getInputTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						eepVo.setEvaluation(eepVo.getEvaluation().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						eepVo.setEvaCodes(coachTagIds);
						eepVo.setEvaNames(coachTags);
						names = exchangeMapper.selectTag(eepVo.getCodes());
						if (names != null && names.size() > 0){
							nameString = names.toString().replace(" ", "");
							eepVo.setNames(nameString.substring(1, nameString.length()-1));
						}
						erEval = exchangeService.postEvaluate(eepVo);
						if (erEval == null)
							log.error("*********** postEvaluate : response body is empty " + "#info: " + eepVo.getRecnum());
						else if (erEval.getErrorCode() != 0)
							log.error("*********** postEvaluate : " + erEval.getMessage() + "#info: " + eepVo.getRecnum());
						else
							log.info("*********** postEvaluate : " + erEval.getMessage() + "#info: " + eepVo.getRecnum());
					}
				}
			} catch (Exception e) {
				log.error("*********** postEvaluate : Exception! #info: " + e.getMessage());
				e.printStackTrace();
			}
			
			//添加学员评价信息
			try {
				List<String> orders = exchangeMapper.selectStuEvalOrder(start, end);
				ExEvalPostVo eepVo = null;
				if (orders != null) {
					for (String order : orders) {
						eepVos = exchangeMapper.selectStuEvalInfo(order);
						eepVo = eepVos.get(0);
						eepVos.remove(0);
						if (eepVo.getSubject() == 2) {
							eepVo.setEvaCodes(student2TagIds);
							eepVo.setEvaNames(student2Tags);
						} else if (eepVo.getSubject() == 3) {
							eepVo.setEvaCodes(student3TagIds);
							eepVo.setEvaNames(student3Tags);
						}
						for (ExEvalPostVo eep : eepVos){
							eepVo.setCodes(eepVo.getCodes() + "," + eep.getCodes());
						}
						names = exchangeMapper.selectTag(eepVo.getCodes());
						if (names != null && names.size() > 0){
							nameString = names.toString().replace(" ", "");
							eepVo.setNames(nameString.substring(1, nameString.length()-1));
						}
						eepVo.setInputTime(eepVo.getInputTime().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						eepVo.setEvaluation(eepVo.getEvaluation().replace(" ", "").replace(":", "").replace("-", "").trim().substring(0, 14));
						erEval = exchangeService.postEvaluate(eepVo);
						if (erEval == null)
							log.error("*********** postEvaluate : response body is empty " + "#info: " + eepVo.getRecnum());
						else if (erEval.getErrorCode() != 0)
							log.error("*********** postEvaluate : " + erEval.getMessage() + "#info: " + eepVo.getRecnum());
						else
							log.info("*********** postEvaluate : " + erEval.getMessage() + "#info: " + eepVo.getRecnum());
					}
				}
			} catch (Exception e) {
				log.error("*********** postEvaluate : Exception! #info: " + e.getMessage());
				e.printStackTrace();
			}
			
		}
	
}
