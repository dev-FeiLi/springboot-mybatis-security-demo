-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.18 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 authority.sys_authority 结构
CREATE TABLE IF NOT EXISTS `sys_authority` (
  `auth_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '表唯一标识',
  `auth_name` varchar(50) NOT NULL COMMENT '权限名称',
  `auth_url` varchar(500) NOT NULL COMMENT '权限路径，对应系统的url',
  `auth_parent` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '权限父类，对应本表的auth_id',
  `auth_sort` char(5) NOT NULL COMMENT '权限的页面排序，同个父类前缀一样比如：A001,A002,A011',
  `auth_description` varchar(100) NOT NULL DEFAULT '' COMMENT '权限的简单描述、说明',
  PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9010 DEFAULT CHARSET=utf8mb4 COMMENT='管理员权限表';

-- 正在导出表  authority.sys_authority 的数据：~10 rows (大约)
/*!40000 ALTER TABLE `sys_authority` DISABLE KEYS */;
INSERT INTO `sys_authority` (`auth_id`, `auth_name`, `auth_url`, `auth_parent`, `auth_sort`, `auth_description`) VALUES
	(1, '系统首页展示', '/index', 1, 'A0001', '系统首页的权限路径'),
	(2, '用户模块', '/userMange', 0, 'A1000', '用户模块父节点目录'),
	(3, '权限管理模块', '/authority', 0, 'A9000', '系统权限管理模块'),
	(9001, '权限列表数据get', '/authority/authsdata', 3, 'A9001', '权限数据获得'),
	(9002, '权限列表入口', '/authority/authlist', 3, 'A9002', '权限列表入口'),
	(9003, '权限保存操作', '/authority/authssave', 3, 'A9003', '权限保存操作'),
	(9004, '角色列表', '/authority/rolelist', 3, 'A9004', '角色列表'),
	(9005, '角色列表数据显示', '/authority/roledata', 3, 'A9005', '角色列表数据显示'),
	(9006, '角色保存操作', '/authority/rolesave', 3, 'A9006', '角色保存操作'),
	(9007, '管理员列表', '/authority/managerlist', 3, 'A9007', '管理员列表'),
	(9008, '管理员保存操作', '/authority/managesave', 3, 'A9008', '管理员保存操作'),
	(9009, '管理员数据显示', '/authority/managedata', 3, 'A9009', '管理员数据显示');
/*!40000 ALTER TABLE `sys_authority` ENABLE KEYS */;

-- 导出  表 authority.sys_manage 结构
CREATE TABLE IF NOT EXISTS `sys_manage` (
  `man_id` smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT '表唯一标识',
  `man_account` varchar(15) NOT NULL COMMENT '管理员帐号',
  `man_passwd` varchar(128) NOT NULL COMMENT '管理员密码',
  `man_name` varchar(50) NOT NULL COMMENT '管理员姓名',
  `man_add_time` datetime NOT NULL COMMENT '管理员添加时间',
  `man_login_time` datetime DEFAULT NULL COMMENT '管理员最新登录时间',
  `man_login_ip` varchar(32) DEFAULT NULL COMMENT '管理员最新登录IP',
  `man_last_time` datetime DEFAULT NULL COMMENT '管理员上次登录时间',
  `man_last_ip` varchar(32) DEFAULT NULL COMMENT '管理员上次登录IP',
  `man_status` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '管理员状态：Y正常，N禁用',
  `man_role` smallint(6) unsigned NOT NULL COMMENT '管理员角色，对应un_role表中的role_id',
  `man_version` bigint(20) unsigned NOT NULL COMMENT '表记录的版本，每次更新后修改为当前时间戳，乐观锁',
  `menu_pos` varchar(50) DEFAULT 'layout-top-nav' COMMENT '页面菜单位置，“layout-top-nav”顶部，“sidebar-mini sidebar-collapse”左边',
  PRIMARY KEY (`man_id`),
  UNIQUE KEY `man_account` (`man_account`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='联盟后台管理员表';

-- 正在导出表  authority.sys_manage 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `sys_manage` DISABLE KEYS */;
INSERT INTO `sys_manage` (`man_id`, `man_account`, `man_passwd`, `man_name`, `man_add_time`, `man_login_time`, `man_login_ip`, `man_last_time`, `man_last_ip`, `man_status`, `man_role`, `man_version`, `menu_pos`) VALUES
	(1, 'laowu', 'abfae1148760f94fbbf5b4839f5df66a881bdf6398014838a187856623727b2033a8771833a29ca9', '老吴', '2017-04-14 15:54:09', '2017-11-23 17:07:32', '192.168.2.101', '2017-11-23 17:02:51', '192.168.2.101', 'Y', 1, 490, 'layout-top-nav'),
	(2, 'lifei', '991010ca4f53e653384294bdd2eafa8d3802b02fddb8a431d6f2375461cbd5ab97c8ca53d52e7da3', '李飞', '2017-08-07 12:33:16', '2017-11-15 14:46:04', '0:0:0:0:0:0:0:1', '2017-11-15 14:44:28', '0:0:0:0:0:0:0:1', 'Y', 1, 1502080398018, 'layout-top-nav'),
	(4, 'admin', '6f2aac5ae32242882eea2795b0c634adf0cba725cb9a8a6a834cc30b83b2344ef7cf30a082d03111', '测试账号', '2017-11-27 10:14:29', NULL, NULL, NULL, NULL, 'Y', 1, 1511777668935, 'layout-top-nav');
/*!40000 ALTER TABLE `sys_manage` ENABLE KEYS */;

-- 导出  表 authority.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `role_id` smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT '表唯一标识',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_auths` varchar(1000) NOT NULL DEFAULT '' COMMENT '角色对应的权限ID串，用英文逗号隔开，ID对应un_authority表中的auth_id',
  `role_version` bigint(20) unsigned NOT NULL COMMENT '角色更新版本，每次更新后修改为当前的时间戳，乐观锁',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='管理员角色表';

-- 正在导出表  authority.sys_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_auths`, `role_version`) VALUES
	(1, '最高管理员', '1,2,3,9001,9002,9003,9004,9005,9006,9007,9008,9009', 56);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
