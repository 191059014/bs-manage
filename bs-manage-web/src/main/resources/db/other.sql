CREATE TABLE `global_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cfg_group_id` varchar(32) NOT NULL COMMENT '配置分组的ID',
  `cfg_id` varchar(32) NOT NULL COMMENT '配置标识',
  `cfg_value` varchar(1024) NOT NULL COMMENT '配置内容',
  `cfg_remark` varchar(128) NOT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `record_status` tinyint NOT NULL DEFAULT '1' COMMENT '记录有效状态',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '多租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_cfg_group_id` (`cfg_group_id`),
  KEY `idx_cfg_id` (`cfg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='全局配置表';