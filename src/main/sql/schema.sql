--数据库初始化脚本

--创建数据库
CREATE DATABASE seckill;
--使用数据库
USE seckill;
--创建秒杀库存表
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '商品库存数量',
`start_time` timestamp NOT NULL COMMENT '秒杀开始时间',
`end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 	DEFAULT CHARSET=utf8 COMMENT '秒杀库存表';
--初始化数据
INSERT INTO 
	seckill(name,number,start_time,end_time)
VALUES
	('1000元秒杀iPhone6s',100,'2017-01-28 00:00:00','2017-01-29 00:00:00'),
	('500元秒杀iPad',200,'2017-01-28 00:00:00','2017-01-29 00:00:00'),
	('300元秒杀小米5',300,'2017-01-28 00:00:00','2017-01-29 00:00:00'),
	('100元秒杀红米3s',400,'2017-01-28 00:00:00','2017-01-29 00:00:00');

--创建秒杀明细表
--用户登录认证的相关信息
create table success_killed(
`seckill_id` bigint not null comment '秒杀商品id',
`user_phone` bigint not null comment '用户手机号',
`state` tinyint not null default -1 comment '状态标识：-1：无效     0：成功   1：已付款   2：已发货',
`create_time` timestamp not null comment '创建时间',
primary key (seckill_id, user_phone),/*联合主键*/
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表';



