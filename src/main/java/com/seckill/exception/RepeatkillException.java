package com.seckill.exception;
/**
 * 重复秒杀异常
 * @author aspire
 *
 */
public class RepeatkillException extends SeckillException {

	public RepeatkillException(String meassage){
		super(meassage);
	}
	
	public RepeatkillException(String meassage, Throwable cause){
		super(meassage, cause);
	}

}
