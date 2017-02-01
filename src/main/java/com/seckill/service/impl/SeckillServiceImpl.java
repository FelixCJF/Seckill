package com.seckill.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.domain.Seckill;
import com.seckill.domain.SuccessKilled;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.enums.SeckillStateEnum;
import com.seckill.exception.RepeatkillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;
@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
			
	
	@Autowired//spring提供
	private SeckillDao seckillDao;
	
	@Resource//j2ee提供的注解
	private SuccessKilledDao successKilledDao;
	
	private String slat = "sjklsndflsadfklsagk&**(";//盐值，混淆md5
	
	@Override
	public List<Seckill> getSeckills() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getSeckillById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if (seckill == null) {
			return new Exposer(false, seckillId);
		}
		Date start_time = seckill.getStartTime();
		Date end_time = seckill.getEndTime();
		//当前系统时间
		Date now_time = new Date();
		if (start_time.getTime() > now_time.getTime() 
				|| end_time.getTime() < now_time.getTime()) {
			return new Exposer(false, seckillId, now_time.getTime(), start_time.getTime(), end_time.getTime());
		}
		//转化特定字符串过程，不可逆
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" +slat;
		//Spring提供的md5生成工具
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Override
	@Transactional
	public SeckillExecution excuteSeckill(long seckillId, long userphone,
			String md5) throws SeckillException, RepeatkillException,
			SeckillCloseException {
		try {
			if (md5 == null || !md5.equals(getMD5(seckillId))) {
				throw new SeckillException("seckill data rewrite");
			}
			//秒杀逻辑：减库存，记录购买行为
			Date killTime = new Date();
			int updateCount = seckillDao.reduceNumber(seckillId, killTime);
			if (updateCount <= 0) {
				throw new SeckillCloseException("秒杀已经关闭");
			}else {
				//记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userphone);
				//seckillId,userphone联合主键，唯一性
				if (insertCount <= 0) {
					throw new RepeatkillException("重复秒杀");
				}else {
					//秒杀成功
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userphone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
				}
			}
		} catch(SeckillCloseException e1){
			throw e1;
		} catch(RepeatkillException e2){
			throw e2;
		}catch (Exception e) {
			//所有编译期异常转化成运行期异常,一旦发生异常，Spring事务就会回滚
			throw new SeckillException("异常是" + e.getMessage());
		}
	}

}
