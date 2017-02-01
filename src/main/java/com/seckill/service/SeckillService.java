package com.seckill.service;

import java.util.List;

import com.seckill.domain.Seckill;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.exception.RepeatkillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

/**
 * 秒杀接口
 *
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckills();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getSeckillById(long seckillId);
	/**
	 * 秒杀开启，暴露秒杀地址
	 * 秒杀微开启，输出系统时间和秒杀时间
	 * @param seckillId
	 * @return
	 */
	Exposer exportSeckillUrl(long seckillId);
	/**
	 * 秒杀成功
	 * @param seckillId
	 * @param userphone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws RepeatkillException
	 * @throws SeckillCloseException
	 */
	SeckillExecution excuteSeckill(long seckillId, long userphone, String md5) 
			throws SeckillException,RepeatkillException,SeckillCloseException;
}
