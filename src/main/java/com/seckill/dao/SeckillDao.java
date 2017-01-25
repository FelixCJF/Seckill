package com.seckill.dao;

import java.util.Date;
import java.util.List;

import com.seckill.domain.Seckill;

public interface SeckillDao {

	/**
	 * 减库存
	 * @param skillId
	 * @param killTime
	 * @return
	 */
	int reduceNumber(long skillId, Date killTime);
	
	/**
	 * 根据id查询秒杀对象
	 * @param skillId
	 * @return
	 */
	Seckill queryById(long skillId);
	
	/**
	 * 根据偏移量查询
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(int offset , int limit);
}
