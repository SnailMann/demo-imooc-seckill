# Host: localhost  (Version 8.0.12)
# Date: 2018-12-26 16:14:50
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "seckill_business_goods"
#

DROP TABLE IF EXISTS `seckill_business_goods`;
CREATE TABLE `seckill_business_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `seckill_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价格',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='要秒杀的商品表';

#
# Data for table "seckill_business_goods"
#

INSERT INTO `seckill_business_goods` VALUES (1,1,0.01,10,'2018-12-26 00:00:00','2018-12-27 00:00:00'),(2,2,1.00,10,'2018-12-26 00:00:00','2018-12-28 00:00:00');

#
# Structure for table "seckill_business_order"
#

DROP TABLE IF EXISTS `seckill_business_order`;
CREATE TABLE `seckill_business_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='秒杀订单表';

#
# Data for table "seckill_business_order"
#


#
# Structure for table "seckill_goods"
#

DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品的图片',
  `goods_detail` varchar(64) DEFAULT '' COMMENT '商品的详细介绍',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存，-1表示无穷，没有限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品信息表';

#
# Data for table "seckill_goods"
#

INSERT INTO `seckill_goods` VALUES (1,'iphoneX','Apple iPhone','/img/iphonex.png','苹果X 手机',8757.00,100),(2,'华为Meta9','华为 Mate 9 4GB+32GB','/img/meta9.png','华为9',3200.00,10);

#
# Structure for table "seckill_order"
#

DROP TABLE IF EXISTS `seckill_order`;
CREATE TABLE `seckill_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收货地址id',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) DEFAULT NULL COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品单价',
  `order_channel` tinyint(3) DEFAULT NULL COMMENT '订单渠道：1. pc 2. android 3. ios',
  `status` tinyint(3) DEFAULT NULL COMMENT '订单状态： 0 新建未支付 1 已支付 2 已发货 3 已收货 4 已退款 5 已完成',
  `create_date` datetime DEFAULT NULL COMMENT '下单时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品订单表';

#
# Data for table "seckill_order"
#


#
# Structure for table "seckill_user"
#

DROP TABLE IF EXISTS `seckill_user`;
CREATE TABLE `seckill_user` (
  `id` bigint(20) NOT NULL COMMENT '用户ID,手机号码',
  `nickname` varchar(255) NOT NULL,
  `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文 + 固定salt) + salt)',
  `salt` varchar(10) DEFAULT NULL,
  `head` varchar(128) DEFAULT NULL COMMENT '头像，云存储的ID',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

#
# Data for table "seckill_user"
#

INSERT INTO `seckill_user` VALUES (13750091178,'Jerry','6e0a7fe692684372437c9e508508990d','1a2b3c4d','123','2017-09-22 10:32:27','2017-09-22 10:32:29',1);
