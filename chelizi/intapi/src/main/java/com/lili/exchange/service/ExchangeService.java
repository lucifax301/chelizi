package com.lili.exchange.service;

import com.lili.exchange.dto.ExEvalDTO;
import com.lili.exchange.dto.ExPayDTO;
import com.lili.exchange.dto.ExSubDTO;
import com.lili.exchange.dto.ExTrainDTO;
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

public interface ExchangeService {

	//预约信息
	public ExResult<ExSubDTO> postBookInfo(ExSubPostVo espVo);
	public ExResult<ExData<ExSubDTO>> getBookInfoList(ExSubQueryVo esqVo);
	public ExResult<ExSubDTO> putBookInfoCancel(String platnum, String recnum, String cancelTime,
			String cancelUserCode, String cancelUserName, String remark);
	public ExResult<ExSubDTO> getBookInfoByID(String id);
	public ExResult<ExSubDTO> getBookInfoByNum(String platnum, String recnum);
	//培训信息
	public ExResult<ExTrainDTO> postTrainInfo(ExTrainPostVo etpVo);
	public ExResult<ExData<ExTrainDTO>> getTrainInfoList(ExTrainQueryVo etqVo);
	public ExResult<ExTrainDTO> getTrainInfoByID(String id);
	public ExResult<ExTrainDTO> getTrainInfoByNum(String platnum, String recnum);
	//评价信息
	public ExResult<ExEvalDTO> postEvaluate(ExEvalPostVo eepVo);
	public ExResult<ExData<ExEvalDTO>> getEvaluateList(ExEvalQueryVo eeqVo);
	public ExResult<ExEvalDTO> getEvaluateById(String id);
	public ExResult<ExEvalDTO> getEvaluateByNum(String platnum, String recnum);
	//支付信息
	public ExResult<ExPayDTO> postPayInfo(ExPayPostVo eppVo);
	public ExResult<ExData<ExPayDTO>> getPayInfoList(ExPayQueryVo epqVo);
	public ExResult<ExPayDTO> getPayInfoById(String id);
	public ExResult<ExPayDTO> getPayInfoByNum(String platnum, String recnum);
}
