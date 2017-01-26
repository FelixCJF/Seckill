package com.seckill.test.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dao.SeckillDao;
import com.seckill.domain.Seckill;

/**
 * 配置Spring和Junit整合，Junit启动时加载SpringIOC容器
 *Spring-test Junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit Spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	//注入Dao实现依赖
	@Autowired
	private SeckillDao seckillDao;

	@Test
	public void queryByIdTest() throws Exception{
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	
	@Test
	public void queryAllTest() throws Exception{
		List<Seckill> list = seckillDao.queryAll(0, 100);
		for (Seckill seckill : list) {
			System.out.println(seckill.getName());
			System.out.println(seckill);
		}
	}
	
	@Test
	public void reduceNumberTest(){
		Date date = new Date();
		int number = seckillDao.reduceNumber(1000L, date);
		System.out.println(number);
	}
	

}
