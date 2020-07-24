CREATE TABLE `sys_user` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '记录更新时间',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据记录逻辑删除标识：1-正常；0-已删除',
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户标识 ',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '加密后的密码',
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id` (`user_id`) USING BTREE,
  UNIQUE KEY `uniq_mobile` (`mobile`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表'