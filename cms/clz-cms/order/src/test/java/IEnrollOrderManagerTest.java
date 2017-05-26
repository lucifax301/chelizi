

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.order.manager.CMSOrderManager;
import com.lili.order.model.InsuranceOrderBDTO;
import com.lili.order.service.CMSOrderService;
import com.lili.order.service.ICmsEnrollOrderService;
import com.lili.order.vo.EnrollOrderVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class IEnrollOrderManagerTest {
	
	@Autowired
	ICmsEnrollOrderService enrollOrderService;
	@Autowired
	CMSOrderService cmsOrderService;
	@Autowired
	CMSOrderManager cmsOrderManager;
	
	@Test
	public void testQueryEnrollOrderList() throws Exception{
//		fail("Not yet implemented");
//		EnrollOrderVo enrollOrderVo =new EnrollOrderVo();
//		 enrollOrderService.query(enrollOrderVo, null);
//		 fail("Not yet implemented");
		InsuranceOrderBDTO dto=new InsuranceOrderBDTO();
		PagedResult<InsuranceOrderBDTO> list = cmsOrderManager.findInsuranceList(dto);
	//	List<InsuranceOrderBDTO> list=(List<InsuranceOrderBDTO>)res.getResult().get("pageData");
		System.out.println("22");
	}

}
