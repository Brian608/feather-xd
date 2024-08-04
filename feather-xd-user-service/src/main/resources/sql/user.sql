CREATE TABLE `user` (
                        `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                        `name` varchar(128) DEFAULT NULL COMMENT '昵称',
                        `pwd` varchar(124) DEFAULT NULL COMMENT '密码',
                        `head_img` varchar(524) DEFAULT NULL COMMENT '头像',
                        `slogan` varchar(524) DEFAULT NULL COMMENT '用户签名',
                        `sex` tinyint DEFAULT '1' COMMENT '0表示女，1表示男',
                        `points` int DEFAULT '0' COMMENT '积分',
                        `create_time` datetime DEFAULT NULL,
                        `mail` varchar(64) DEFAULT NULL COMMENT '邮箱',
                        `secret` varchar(12) DEFAULT NULL COMMENT '盐，用于个人敏感信息处理',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `mail_idx` (`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;