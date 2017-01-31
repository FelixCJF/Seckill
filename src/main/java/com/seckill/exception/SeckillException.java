package com.seckill.exception;
/**
 * 秒杀业务相关异常基类
 * @author aspire
 * java异常分编译期异常和运行期异常，运行期异常不需要手工try-catch，
 * spring的的声明式事务只接收运行期异常回滚策略，
 * 非运行期异常不会帮我们回滚
 */
public class SeckillException extends RuntimeException {

	public SeckillException(String meassage){
		super(meassage);
	}
	
	public SeckillException(String meassage, Throwable cause){
		super(meassage, cause);
	}
}
