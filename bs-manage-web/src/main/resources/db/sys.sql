CREATE TABLE `sys_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `user_id` varchar(32) NOT NULL COMMENT '�û���ʶ',
  `user_name` varchar(32) NOT NULL COMMENT '�û���',
  `password` varchar(32) NOT NULL COMMENT '���ܺ������',
  `mobile` varchar(11) NOT NULL DEFAULT '-1' COMMENT '�ֻ���',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '��¼����ʱ��',
  `create_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '��¼����ʱ��',
  `update_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '�޸���',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '���ݼ�¼�߼�ɾ����ʶ��1-������0-��ɾ��',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id` (`user_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�û���Ϣ��';

CREATE TABLE `sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `role_id` varchar(32) NOT NULL COMMENT '��ɫid',
  `role_name` varchar(32) NOT NULL COMMENT '��ɫ����',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '��¼����ʱ��',
  `create_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '��¼����ʱ��',
  `update_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '�޸���',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '���ݼ�¼�߼�ɾ����ʶ��1-������0-��ɾ��',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_id` (`role_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='��ɫ��Ϣ��';

CREATE TABLE `sys_access` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `access_id` varchar(32) NOT NULL COMMENT 'Ȩ��ID',
  `access_name` varchar(32) NOT NULL COMMENT 'Ȩ������',
  `access_type` varchar(10) NOT NULL COMMENT 'Ȩ������',
  `access_value` varchar(32) NOT NULL COMMENT 'Ȩ��ֵ',
  `icon` varchar(64) NOT NULL COMMENT 'ͼ��',
  `url` varchar(255) NOT NULL COMMENT '����',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '��¼����ʱ��',
  `create_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '��¼����ʱ��',
  `update_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '�޸���',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '���ݼ�¼�߼�ɾ����ʶ��1-������0-��ɾ��',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_access_id` (`access_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Ȩ����Ϣ��';

CREATE TABLE `sys_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `user_id` varchar(32) NOT NULL COMMENT '�û�ID',
  `role_id` varchar(32) NOT NULL COMMENT '��ɫID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '��¼����ʱ��',
  `create_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '��¼����ʱ��',
  `update_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '�޸���',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '���ݼ�¼�߼�ɾ����ʶ��1-������0-��ɾ��',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�û���ɫ��ϵ��';

CREATE TABLE `sys_role_access` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `access_id` varchar(32) NOT NULL COMMENT 'Ȩ��ID',
  `role_id` varchar(32) NOT NULL COMMENT '��ɫID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '��¼����ʱ��',
  `create_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '������',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '��¼����ʱ��',
  `update_user_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '�޸���',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '���ݼ�¼�߼�ɾ����ʶ��1-������0-��ɾ��',
  `parent_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '����ID',
  `tenant_id` varchar(32) NOT NULL DEFAULT '-1' COMMENT '���⻧ID',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='��ɫȨ�޹�ϵ��';