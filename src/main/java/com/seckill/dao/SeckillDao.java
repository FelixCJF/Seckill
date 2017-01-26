package com.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seckill.domain.Seckill;

public interface SeckillDao {

	/**
	 * 减库存
	 * @param skillId
	 * @param killTime
	 * @return
	 */
	int reduceNumber(@Param("seckillId") long skecillId,@Param("killTime") Date killTime);
	
	/**
	 * 根据id查询秒杀对象
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * 根据偏移量查询
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offset") int offset ,@Param("limit") int limit);
}
