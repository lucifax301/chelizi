package com.lili;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lili.common.util.BeanCopy;
import com.lili.common.util.TimeUtil;
import com.lili.order.vo.CommentTagVo;
import com.lili.order.vo.StuCommentVo;

public class MainTest {
	public static void main (String []args){
		String sss="aaa,";
		System.out.println(sss.substring(0,sss.length()-1));
		
		try{
		Date now=new Date();
		System.out.println(TimeUtil.getDateFormat(now));
		CommentTagVo ct1=new CommentTagVo();
		ct1.setCtid(1);
		ct1.setTag("tag1");
		CommentTagVo ct2=new CommentTagVo();
		ct2.setCtid(2);
		ct2.setTag("tag2");
		
		CommentTagVo ct3=new CommentTagVo();
		ct3.setCtid(3);
		ct3.setTag("tag3");
		
		List<Object> tagList=new ArrayList<Object>();
		tagList.add(ct1);
		tagList.add(ct2);
		tagList.add(ct3);
		
		StuCommentVo sc1=new StuCommentVo();
		sc1.setCtid(2);
		sc1.setScore(20);
		StuCommentVo sc2=new StuCommentVo();
		sc2.setCtid(1);
		sc2.setScore(10);
		StuCommentVo sc3=new StuCommentVo();
		sc3.setCtid(3);
		sc3.setScore(30);
		List<Object> stuList=new ArrayList<Object>();
		stuList.add(sc2);
		stuList.add(sc1);
//		stuList.add(sc3);
		
		List<Object> tagList1=BeanCopy.copyList(stuList, tagList, BeanCopy.COPY2NULL, "ctid");
		System.out.println(tagList);
		System.out.println("...........");
		System.out.println(tagList1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
