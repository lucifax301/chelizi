/**
 *
 */
package com.lili.coupon.condition;

import com.lili.coupon.dto.CCondition;
import com.lili.coupon.mapper.dao.CConditionMapper;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * @author linbo
 *         加载所有条件，并管理
 */
public class ConditionManager {
    private static Logger logger = LoggerFactory.getLogger(ConditionManager.class);

    @Autowired
    private Map<String, AbstractCCondition> conditionMap;

    @Autowired
    private CConditionMapper cConditionMapper;

    @PostConstruct
    public void init() {
    }

    public boolean isConditionTrue(int conditionId, String coupontmpid, Student student, OrderVo orderVo) {
        try {
            CCondition condition = cConditionMapper.selectByPrimaryKey(conditionId);
            if (condition == null) {
                logger.error("the condition is null when check it. id is:" + conditionId);
                return false;
            }

            condition.setCoupontmpid(coupontmpid);

            AbstractCCondition baseCondition = conditionMap.get(String.valueOf(condition.getType()));
            if (baseCondition == null) {
                logger.error("does not support " + condition.getType() + " type conditions");
                return false;
            }

            return baseCondition.checkCondition(condition, student, orderVo);
        } catch (Exception e) {
            logger.error("isConditionTrue:" + conditionId + "," + student + "," + orderVo, e);
        }
        return false;
    }

    public boolean addCondition(CCondition condition) {
        return cConditionMapper.insert(condition) > 0;
    }

    public boolean updateCondition(CCondition condition) {
        return cConditionMapper.updateByPrimaryKey(condition) > 0;
    }

    public String getConditionDesc(int conditionId) {
        String desc = "";
        CCondition condition = cConditionMapper.selectByPrimaryKey(conditionId);
        if (condition == null || condition.getDescri() == null || condition.getDescri().isEmpty()) {
            return desc;
        }
        String param1 = condition.getParam1();
        String param2 = condition.getParam2();
        desc = String.format(condition.getDescri(), param1, param2);
        return desc;
    }

    public Map<String, AbstractCCondition> getConditionMap() {
        return conditionMap;
    }

    public void setConditionMap(Map<String, AbstractCCondition> conditionMap) {
        this.conditionMap = conditionMap;
    }

    /**
     * 设置状态
     *
     * @param expression 格式: 表达式|参数1，参数2，参数3
     * @param student
     * @param orderVo
     * @return
     */
    public boolean checkExpressions(String expression, String coupontmpid, Student student, OrderVo orderVo) {
        //如果表达式为空，则认为是无条件为真
        if (expression == null || expression.isEmpty()) {
            return true;
        }
        try {
            String toggle[] = expression.split("\\|");
            String expressionOrigin = toggle[0];
            String conditions[] = toggle[1].split(",");
            String conResult[] = new String[conditions.length];
            for (int i = 0; i < conditions.length; i++) {
                Integer conId = Integer.parseInt(conditions[i]);
                boolean result = isConditionTrue(conId, coupontmpid, student, orderVo);
                conResult[i] = String.valueOf(result);
            }
            String expressionSpel = String.format(expressionOrigin, (Object[]) conResult);
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression(expressionSpel);
            Boolean res = exp.getValue(Boolean.class);
            return res;
        } catch (Exception e) {
            //表达式不合法
            logger.error(expression + " is not a valid expression.", e);
        }

        return false;
    }

    @PreDestroy
    public void destory() {
        conditionMap.clear();
    }

	public CCondition getConditionInfo(Integer conditionId) {
		return cConditionMapper.selectByPrimaryKey(conditionId);
	}
}
