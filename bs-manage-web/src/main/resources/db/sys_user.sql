CREATE TABLE `sys_user` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '��¼����ʱ��',
  `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '��¼����ʱ��',
  `record_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '���ݼ�¼�߼�ɾ����ʶ��1-������0-��ɾ��',
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '����',
  `user_id` varchar(32) NOT NULL COMMENT '�û���ʶ ',
  `user_name` varchar(32) NOT NULL COMMENT '�û���',
  `password` varchar(32) NOT NULL COMMENT '���ܺ������',
  `mobile` varchar(11) NOT NULL COMMENT '�ֻ���',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id` (`user_id`) USING BTREE,
  UNIQUE KEY `uniq_mobile` (`mobile`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�û���Ϣ��'