package com.lili.exchange.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lili.exchange.dto.ExEvalDTO;
import com.lili.exchange.dto.ExPayDTO;
import com.lili.exchange.dto.ExSubDTO;
import com.lili.exchange.dto.ExTrainDTO;
import com.lili.exchange.jst.ExchangeEvaluations;
import com.lili.exchange.jst.ExchangePayments;
import com.lili.exchange.jst.ExchangeSubscribes;
import com.lili.exchange.jst.ExchangeTrainInfos;
import com.lili.exchange.service.ExchangeService;
import com.lili.exchange.vo.ExData;
import com.lili.exchange.vo.ExEvalPostVo;
import com.lili.exchange.vo.ExEvalQueryVo;
import com.lili.exchange.vo.ExPayPostVo;
import com.lili.exchange.vo.ExPayQueryVo;
import com.lili.exchange.vo.ExResult;
import com.lili.exchange.vo.ExSubPostVo;
import com.lili.exchange.vo.ExSubQueryVo;
import com.lili.exchange.vo.ExTrainPostVo;
import com.lili.exchange.vo.ExTrainQueryVo;


public class ExchangeServiceImpl implements ExchangeService {
	
	private Logger log = Logger.getLogger(ExchangeServiceImpl.class);
	@Autowired
	private ExchangeSubscribes exchangeSubscribes;
	@Autowired
	private ExchangeTrainInfos exchangeTrainInfos;
	@Autowired
	private ExchangeEvaluations exchangeEvaluations;
	@Autowired
	private ExchangePayments exchangePayments;
	
	private <T> ExResult<T> parseJson(String json){
		try {
			Gson gson = new GsonBuilder().create();
			ExResult<T> result = new ExResult<>();
			ExResult<String> r = gson.fromJson(json, new TypeToken<ExResult<String>>(){}.getType());
			T data = null;
			if (r.getData() != null && !r.getData().equals("") && !r.getData().equals("null"))
				data = gson.fromJson(r.getData(), new TypeToken<T>(){}.getType());
			result.setErrorCode(r.getErrorCode());
			result.setMessage(r.getMessage());
			result.setData(data);
			return result;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ExResult<ExSubDTO> postBookInfo(ExSubPostVo espVo) {
		ExResult<ExSubDTO> result = null;
		try {
			String json = exchangeSubscribes.postBookInfo(espVo);
			if (json != null && !"".equals(json)){
				result = parseJson(json);
			}
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExData<ExSubDTO>> getBookInfoList(ExSubQueryVo esqVo) {
		ExResult<ExData<ExSubDTO>> result = null;
		try {
			String json = exchangeSubscribes.getBookInfoList(esqVo);
			if (json != null && !"".equals(json)){
				result = parseJson(json);
			}
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExSubDTO> putBookInfoCancel(String platnum, String recnum, String cancelTime, String cancelUserCode,
			String cancelUserName, String remark) {
		ExResult<ExSubDTO> result = null;
		try {
			String json = exchangeSubscribes.putBookInfoCancel(platnum, recnum, cancelTime, cancelUserCode, cancelUserName, remark);
			if (json != null && !"".equals(json)){
				result = parseJson(json);
			}
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExSubDTO> getBookInfoByID(String id) {
		ExResult<ExSubDTO> result = null;
		try {
			String json = exchangeSubscribes.getBookInfoByID(id);
			if (json != null && !"".equals(json)){
				result = parseJson(json);
			}
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExSubDTO> getBookInfoByNum(String platnum, String recnum) {
		ExResult<ExSubDTO> result = null;
		try {
			String json = exchangeSubscribes.getBookInfoByNum(platnum, recnum);
			if (json != null && !"".equals(json)){
				result = parseJson(json);
			}
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExTrainDTO> postTrainInfo(ExTrainPostVo etpVo) {
		ExResult<ExTrainDTO> result = null;
		try {
			String json = exchangeTrainInfos.postTrainInfo(etpVo);
			if (json != null && !"".equals(json)){
				result = parseJson(json);
			}
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExData<ExTrainDTO>> getTrainInfoList(ExTrainQueryVo etqVo) {
		ExResult<ExData<ExTrainDTO>> result = null;
		try {
			String json = exchangeTrainInfos.getTrainInfoList(etqVo);
			if (json != null && !"".equals(json)){
				result = parseJson(json);
			}
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExTrainDTO> getTrainInfoByID(String id) {
		ExResult<ExTrainDTO> result = null;
		try {
			String json = exchangeTrainInfos.getTrainInfoByID(id);
			if (json != null && !"".equals(json)){
				result = parseJson(json);
			}
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExTrainDTO> getTrainInfoByNum(String platnum, String recnum) {
		ExResult<ExTrainDTO> result = null;
		try {
			String json = exchangeTrainInfos.getTrainInfoByNum(platnum, recnum);
			if (json != null && !"".equals(json)){
				result = parseJson(json);
			}
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExEvalDTO> postEvaluate(ExEvalPostVo eepVo) {
		ExResult<ExEvalDTO> result = null;
		try {
			String json = exchangeEvaluations.postEvaluate(eepVo);
			if (json != null && !"".equals(json))
				result = parseJson(json);
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExData<ExEvalDTO>> getEvaluateList(ExEvalQueryVo eeqVo) {
		ExResult<ExData<ExEvalDTO>> result = null;
		try {
			String json = exchangeEvaluations.getEvaluateList(eeqVo);
			if (json != null && !"".equals(json))
				result = parseJson(json);
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExEvalDTO> getEvaluateById(String id) {
		ExResult<ExEvalDTO> result = null;
		try {
			String json = exchangeEvaluations.getEvaluateById(id);
			if (json != null && !"".equals(json))
				result = parseJson(json);
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExEvalDTO> getEvaluateByNum(String platnum, String recnum) {
		ExResult<ExEvalDTO> result = null;
		try {
			String json = exchangeEvaluations.getEvaluateByNum(platnum, recnum);
			if (json != null && !"".equals(json))
				result = parseJson(json);
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExPayDTO> postPayInfo(ExPayPostVo eppVo) {
		ExResult<ExPayDTO> result = null;
		try {
			String json = exchangePayments.postPayInfo(eppVo);
			if (json != null && !"".equals(json))
				result = parseJson(json);
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExData<ExPayDTO>> getPayInfoList(ExPayQueryVo epqVo) {
		ExResult<ExData<ExPayDTO>> result = null;
		try {
			String json = exchangePayments.getPayInfoList(epqVo);
			if (json != null && !"".equals(json))
				result = parseJson(json);
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExPayDTO> getPayInfoById(String id) {
		ExResult<ExPayDTO> result = null;
		try {
			String json = exchangePayments.getPayInfoById(id);
			if (json != null && !"".equals(json))
				result = parseJson(json);
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}
	
	@Override
	public ExResult<ExPayDTO> getPayInfoByNum(String platnum, String recnum) {
		ExResult<ExPayDTO> result = null;
		try {
			String json = exchangePayments.getPayInfoByNum(platnum, recnum);
			if (json != null && !"".equals(json))
				result = parseJson(json);
		} catch (Exception e) {
			log.error("ExchangeServiceImpl Exception : ", e);
		}
		return result;
	}

}
