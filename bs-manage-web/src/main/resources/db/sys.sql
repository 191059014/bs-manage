CREATE TABLE `sys_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户标识',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '加密后的密码',
  `mobile` varchar(11) NOT NULL DEFAULT '-1' COMMENT '手机号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '记录更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '修改人',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据记录逻辑删除标识：1-正常；0-已删除',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '多租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id` (`user_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

CREATE TABLE `sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '记录更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '修改人',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据记录逻辑删除标识：1-正常；0-已删除',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '多租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_id` (`role_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

CREATE TABLE `sys_access` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `access_id` varchar(32) NOT NULL COMMENT '权限ID',
  `access_name` varchar(32) NOT NULL COMMENT '权限名称',
  `access_type` varchar(10) NOT NULL COMMENT '权限类型',
  `access_value` varchar(32) NOT NULL COMMENT '权限值',
  `icon` varchar(64) NOT NULL COMMENT '图标',
  `url` varchar(255) NOT NULL COMMENT '链接',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '记录更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '修改人',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据记录逻辑删除标识：1-正常；0-已删除',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '多租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_access_id` (`access_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限信息表';

CREATE TABLE `sys_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '记录更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '修改人',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据记录逻辑删除标识：1-正常；0-已删除',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '多租户ID',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

CREATE TABLE `sys_role_access` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `access_id` varchar(32) NOT NULL COMMENT '权限ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `create_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '创建人',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '记录更新时间',
  `update_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '修改人',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据记录逻辑删除标识：1-正常；0-已删除',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '多租户ID',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系表';