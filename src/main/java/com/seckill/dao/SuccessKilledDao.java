package com.seckill.dao;

import com.seckill.domain.SuccessKilled;

public interface SuccessKilledDao {
	
	/**
	 * 插入购买明细，可过滤重复
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	int insertSuccessKilled(long seckillId, long userPhone);

	/**
	 * 根据id查询SuccessKilled并携带seckill实体信息
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(long seckillId);
}
