DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
                           `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                           `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
                           `default_status` int(1) DEFAULT NULL COMMENT '是否默认收货地址：0->否；1->是',
                           `receive_name` varchar(64) DEFAULT NULL COMMENT '收发货人姓名',
                           `phone` varchar(64) DEFAULT NULL COMMENT '收货人电话',
                           `province` varchar(64) DEFAULT NULL COMMENT '省/直辖市',
                           `city` varchar(64) DEFAULT NULL COMMENT '市',
                           `region` varchar(64) DEFAULT NULL COMMENT '区',
                           `detail_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
                           `create_time` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电商-公司收发货地址表';
