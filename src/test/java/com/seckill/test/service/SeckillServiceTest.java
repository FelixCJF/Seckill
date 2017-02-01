package com.seckill.test.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.domain.Seckill;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.exception.RepeatkillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.service.SeckillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void getSeckills(){
		List<Seckill> list = seckillService.getSeckills();
		logger.info("list={}",list);
	}
	
	@Test
	public void getSeckillByIdTest(){
		long id = 1001;
		Seckill seckill = seckillService.getSeckillById(id);
		logger.info("seckill={}", seckill);
	}
	
	//集成测试完整代码，注意可重复执行
	@Test
	public void exportSeckillLogic(){
		long seckillId = 1001;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()) {
			//秒杀开启
			try{
				long userphone = 15179437128L;
				SeckillExecution execution = seckillService.excuteSeckill(seckillId, userphone, exposer.getMd5());
				logger.info("result={}", execution);
			} catch(RepeatkillException e){
				logger.error(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			}
		} else {
			//秒杀未开启
			logger.info("exposer={}", exposer);
		}
	}
}
