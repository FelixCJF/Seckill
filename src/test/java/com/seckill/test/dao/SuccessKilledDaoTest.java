package com.seckill.test.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * 配置Spring和Junit整合，Junit启动时加载SpringIOC容器
 *Spring-test Junit
 */



import com.seckill.dao.SuccessKilledDao;
import com.seckill.domain.SuccessKilled;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	@Resource
	private SuccessKilledDao successKilledDao;
	
	
	@Test
	public void insertSuccessKilledTest() throws Exception{
		/*
		 * 第一次返回1
		 * 第二次返回0
		 */
		long seckillId = 1000L;
		long userPhone = 15179237126L;
		int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
		System.out.println(insertCount);
	}
	
	@Test
	public void queryByIdWithSeckillTest() throws Exception{
		long seckillId = 1000L;
		long userPhone = 15179237126L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}
	

}
