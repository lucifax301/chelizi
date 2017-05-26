/**
 * 
 */
package com.lili.pay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.pay.service.PayService;
import com.lili.pay.vo.PayWayType;
import com.lili.pay.vo.PerformanceVo;
import com.lili.pay.vo.UserMoneyVo;

/**
 * @author linbo
 *
 */
public class TestPay
{

    /**
     * @param args
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        @SuppressWarnings("resource")
        ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-init.xml");
        PayService payService = (PayService) ac.getBean("payServiceLocal");
        OrderService orderService = ac.getBean(OrderService.class);
        // 校验价格
        
        try
        {
            OrderVo orderVo = orderService.queryOrderById("5847887e4dc3476981a984b48dc756b0", new OrderQuery());
           
            while (true)
            {
                BufferedReader strin=new BufferedReader(new InputStreamReader(System.in)); 
                System.out.print("请输入一个字符串：");  
                
                strin.readLine();  
                
                payService.doPayAction(orderVo, 0, PayWayType.WX, 0, "143242423", new Date());
                
                System.out.println("============================================================");
                ReqResult r = payService.getMoneyLog(orderVo.getCoachId(), 1, 1, 15);
                List<UserMoneyVo> userMoneyVos = (List<UserMoneyVo>) r.getResult().get(ResultCode.RESULTKEY.DATA);
                for (UserMoneyVo userMoneyVo : userMoneyVos)
                {
                    System.err.println(userMoneyVo);
                }
                System.out.println("============================================================");
                r = payService.getMoneyLog(orderVo.getStudentId(), 2, 1, 15);
                userMoneyVos = (List<UserMoneyVo>) r.getResult().get(ResultCode.RESULTKEY.DATA);
                for (UserMoneyVo userMoneyVo : userMoneyVos)
                {
                    System.err.println(userMoneyVo);
                }
                System.out.println("============================================================");
                r = payService.getPerformanceLog(orderVo.getCoachId(), 1, 15);
                List<PerformanceVo> performanceVos = (List<PerformanceVo>) r.getResult().get(ResultCode.RESULTKEY.DATA);
                for (PerformanceVo performanceVo : performanceVos)
                {
                    System.err.println(performanceVo);
                }
                
                System.err.println("test over");
            }
             
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }

}
