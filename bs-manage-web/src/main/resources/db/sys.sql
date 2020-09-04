CREATE TABLE `sys_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '�û���ʶ',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '�û���',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '����',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '�ֻ���',
  `create_by` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�û���Ϣ';

CREATE TABLE `sys_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `role_id` varchar(32) NOT NULL COMMENT '��ɫid',
  `role_name` varchar(32) NOT NULL COMMENT '��ɫ����',
  `create_by` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='��ɫ��Ϣ��';

CREATE TABLE `sys_access` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `access_id` varchar(32) NOT NULL COMMENT 'Ȩ��ID',
  `access_name` varchar(32) NOT NULL COMMENT 'Ȩ������',
  `access_type` varchar(10) NOT NULL COMMENT 'Ȩ������',
  `access_value` varchar(32) NOT NULL COMMENT 'Ȩ��ֵ',
  `icon` varchar(64) NOT NULL COMMENT 'ͼ��',
  `url` varchar(255) NOT NULL COMMENT '����',
  `create_by` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_access_id` (`access_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Ȩ����Ϣ��';

CREATE TABLE `sys_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `user_id` varchar(32) NOT NULL COMMENT '�û�ID',
  `role_id` varchar(32) NOT NULL COMMENT '��ɫID',
  `create_by` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '���⻧ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�û���ɫ��ϵ��';

CREATE TABLE `sys_role_access` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `access_id` varchar(32) NOT NULL COMMENT 'Ȩ��ID',
  `role_id` varchar(32) NOT NULL COMMENT '��ɫID',
  `create_by` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '���⻧ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='��ɫȨ�޹�ϵ��';