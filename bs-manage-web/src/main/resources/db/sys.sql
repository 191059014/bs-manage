CREATE TABLE `sys_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户标识',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '记录有效状态',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '多租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

CREATE TABLE `sys_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '记录有效状态',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '多租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

CREATE TABLE `sys_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission_id` varchar(32) NOT NULL COMMENT '权限ID',
  `permission_name` varchar(32) NOT NULL COMMENT '权限名称',
  `resource_type` varchar(10) NOT NULL COMMENT '权限类型',
  `value` varchar(32) NOT NULL DEFAULT '' COMMENT '权限值',
  `icon` varchar(64) NOT NULL DEFAULT '' COMMENT '图标',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '链接',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '记录有效状态',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '多租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_permission_id` (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限信息表';

CREATE TABLE `sys_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '记录有效状态',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '多租户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

CREATE TABLE `sys_role_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `permission_id` varchar(32) NOT NULL COMMENT '权限ID',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '记录有效状态',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '多租户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系表';

CREATE TABLE `sys_merchant` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` varchar(32) NOT NULL COMMENT '商户ID',
  `merchant_name` varchar(256) NOT NULL COMMENT '商户名称',
  `parent_id_path` varchar(1024) NOT NULL DEFAULT '' COMMENT '父级id路径',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '记录有效状态',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '父级ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '多租户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户表';