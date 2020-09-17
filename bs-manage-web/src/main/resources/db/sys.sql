CREATE TABLE `sys_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '�û���ʶ',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '�û���',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '����',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '�ֻ���',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�û���Ϣ';

CREATE TABLE `sys_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `role_id` varchar(32) NOT NULL COMMENT '��ɫid',
  `role_name` varchar(32) NOT NULL COMMENT '��ɫ����',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='��ɫ��Ϣ��';

CREATE TABLE `sys_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `permission_id` varchar(32) NOT NULL COMMENT 'Ȩ��ID',
  `permission_name` varchar(32) NOT NULL COMMENT 'Ȩ������',
  `resource_type` varchar(10) NOT NULL COMMENT 'Ȩ������',
  `value` varchar(32) NOT NULL DEFAULT '' COMMENT 'Ȩ��ֵ',
  `icon` varchar(64) NOT NULL DEFAULT '' COMMENT 'ͼ��',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '����',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_permission_id` (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Ȩ����Ϣ��';

CREATE TABLE `sys_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `user_id` varchar(32) NOT NULL COMMENT '�û�ID',
  `role_id` varchar(32) NOT NULL COMMENT '��ɫID',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '���⻧ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�û���ɫ��ϵ��';

CREATE TABLE `sys_role_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `role_id` varchar(32) NOT NULL COMMENT '��ɫID',
  `permission_id` varchar(32) NOT NULL COMMENT 'Ȩ��ID',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '���⻧ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='��ɫȨ�޹�ϵ��';

CREATE TABLE `sys_merchant` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `merchant_id` varchar(32) NOT NULL COMMENT '�̻�ID',
  `merchant_name` varchar(256) NOT NULL COMMENT '�̻�����',
  `parent_id_path` varchar(1024) NOT NULL DEFAULT '' COMMENT '����id·��',
  `create_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '������',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `record_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '��¼��Ч״̬',
  `parent_id` varchar(32) NOT NULL DEFAULT '' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '' COMMENT '���⻧ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�̻���';