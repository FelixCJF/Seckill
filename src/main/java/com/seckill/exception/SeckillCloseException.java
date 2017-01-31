package com.seckill.exception;
/**
 * 秒杀关闭异常
 * @author aspire
 *
 */
public class SeckillCloseException extends SeckillException {

	public SeckillCloseException(String meassage){
		super(meassage);
	}
	
	public SeckillCloseException(String meassage, Throwable cause){
		super(meassage, cause);
	}
}
