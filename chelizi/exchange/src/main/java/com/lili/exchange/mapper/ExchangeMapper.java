package com.lili.exchange.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.lili.exchange.vo.ExEvalPostVo;
import com.lili.exchange.vo.ExPayPostVo;
import com.lili.exchange.vo.ExSubPostVo;
import com.lili.exchange.vo.ExTrainPostVo;

public interface ExchangeMapper {

	public List<ExSubPostVo> selectBookInfo(String start, String end);
	public List<ExSubPostVo> selectBookCancelInfo(String start, String end);
	public List<ExTrainPostVo> selectTrainInfo(String start, String end);
	public List<ExPayPostVo> selectPayInfo(String start, String end);
	public List<ExPayPostVo> selectJXPayInfo(String start, String end);
	public List<ExEvalPostVo> selectCoachEvalInfo(String start, String end);
	public List<String> selectStuEvalOrder(String start, String end);
	public List<ExEvalPostVo> selectStuEvalInfo(String orderId);
	public List<String> selectTag(@Param("ctids") String ctids);
}
										