/*
 Navicat Premium Data Transfer

 Source Server         : 天翼云-120.39.217.37
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 120.39.217.37:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 12/04/2022 13:26:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_admin_img
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_img`;
CREATE TABLE `tb_admin_img`  (
  `admin_img_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员头像id',
  `admin_img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员头像url',
  `create_time` datetime(0) NOT NULL COMMENT '添加时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `is_deleted` int(11) NOT NULL COMMENT '是否删除 0 未删除 1 已删除 ',
  `admin_id` int(11) NOT NULL COMMENT '管理员id 外键',
  PRIMARY KEY (`admin_img_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_admin_img
-- ----------------------------
INSERT INTO `tb_admin_img` VALUES (1, 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJRbHCAIcuRAADy8FarNgw089.jpg', '2021-11-26 21:36:41', '2022-04-09 19:22:22', 0, 1);

-- ----------------------------
-- Table structure for tb_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_user`;
CREATE TABLE `tb_admin_user`  (
  `admin_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint(4) NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台管理员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_admin_user
-- ----------------------------
INSERT INTO `tb_admin_user` VALUES (1, 'admin', '670b14728ad9902aecba32e22fa4f6bd', '任人子', 0);

-- ----------------------------
-- Table structure for tb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_category`;
CREATE TABLE `tb_blog_category`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类表主键',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类的名称',
  `category_icon` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类的图标',
  `category_rank` int(11) NOT NULL DEFAULT 1 COMMENT '分类的排序值 被使用的越多数值越大',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_category
-- ----------------------------
INSERT INTO `tb_blog_category` VALUES (1, '默认分类', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJUL5yAeM4yAAAA1FywuJc512.png', 1, 0, '2019-08-30 15:07:02');
INSERT INTO `tb_blog_category` VALUES (20, 'About', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJULuWAeOAIAAABPznvm_s386.png', 9, 0, '2018-11-12 00:28:49');
INSERT INTO `tb_blog_category` VALUES (22, 'Java进阶', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJUONGAAwPIAAABu6bWeGc700.png', 22, 0, '2018-11-12 10:42:25');
INSERT INTO `tb_blog_category` VALUES (24, '日常随笔', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJUL1GARzJlAAAA_DsdbTY082.png', 23, 0, '2018-11-12 10:43:21');
INSERT INTO `tb_blog_category` VALUES (25, 'About2', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJULuWAeOAIAAABPznvm_s386.png', 100, 0, '2019-09-02 01:58:53');
INSERT INTO `tb_blog_category` VALUES (27, 'Python', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJULzyAbktOAAABzWC9Sv8939.png', 6, 0, '2021-11-18 17:44:15');
INSERT INTO `tb_blog_category` VALUES (28, 'Centos7', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJUOPCAPOGqAAABTyVS-EI644.png', 88, 0, '2021-11-18 18:35:42');
INSERT INTO `tb_blog_category` VALUES (29, 'SpringCloud', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJULvaAQSKUAAACDyCdRZg669.png', 17, 0, '2022-03-06 13:13:52');
INSERT INTO `tb_blog_category` VALUES (30, '中间件', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJUL2CAeHjVAAABxfwR8ZA404.png', 11, 0, '2022-04-08 18:40:20');
INSERT INTO `tb_blog_category` VALUES (31, '对象存储', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJULymALfilAAABoKhORAQ607.png', 1, 0, '2022-04-09 19:01:06');
INSERT INTO `tb_blog_category` VALUES (34, 'Mysql', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJULRyAfl5eAAABeIrRCJY158.png', 2, 0, '2022-04-11 21:30:03');

-- ----------------------------
-- Table structure for tb_blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_comment`;
CREATE TABLE `tb_blog_comment`  (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `blog_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '关联的blog主键',
  `commentator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '评论者名称',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '评论人的邮箱',
  `website_url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '网址',
  `comment_body` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '评论内容',
  `comment_create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '评论提交时间',
  `commentator_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '评论时的ip地址',
  `reply_body` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '回复内容',
  `reply_create_time` timestamp(0) NULL DEFAULT NULL COMMENT '回复时间',
  `comment_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否审核通过 0-未审核 1-审核通过',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评论信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_comment
-- ----------------------------
INSERT INTO `tb_blog_comment` VALUES (1, 7, '测试评论', '1320291471@qq.com', '', '感觉还可以', '2019-08-06 20:39:41', '', '谢谢夸奖', '2019-08-06 12:40:10', 1, 0);
INSERT INTO `tb_blog_comment` VALUES (2, 7, '测试评论2', '1320291471@qq.com', '', '还可以啊', '2019-08-06 20:40:40', '', 'nihao', '2019-08-30 02:19:50', 1, 0);
INSERT INTO `tb_blog_comment` VALUES (3, 7, '测试评论31', '1320291471@qq.com', '', '还可以啊', '2019-08-06 20:40:40', '', 'wetw', '2022-03-26 08:09:34', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (4, 7, '测试评论32', '1320291471@qq.com', '', '还可以啊', '2019-08-06 20:40:40', '', 'oko', '2021-11-14 04:18:27', 1, 0);
INSERT INTO `tb_blog_comment` VALUES (5, 7, '测试评论33', '1320291471@qq.com', '', '还可以啊', '2019-08-06 20:40:40', '', 'okok', '2019-09-04 00:38:56', 1, 0);
INSERT INTO `tb_blog_comment` VALUES (6, 7, '测试评论34', '1320291471@qq.com', '', '还可以啊', '2019-08-06 20:40:40', '', 'okok', '2019-09-04 00:38:56', 1, 0);
INSERT INTO `tb_blog_comment` VALUES (7, 7, '测试评论35', '1320291471@qq.com', '', '还可以啊', '2019-08-06 20:40:40', '', 'okok', '2019-09-04 00:38:56', 1, 0);
INSERT INTO `tb_blog_comment` VALUES (10, 1, '测试着', '1320291471@qq.com', '', '11111121312412', '2019-09-06 09:45:32', '', '你好啊，谢谢你的评论2', '2019-09-06 09:46:26', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (15, 37, 'Ip为: 127.0.0.1的用户', '', '', '这是一条测试评论', '2022-03-18 21:27:37', '127.0.0.1', '似懂非懂', '2022-03-19 20:54:35', 1, 0);
INSERT INTO `tb_blog_comment` VALUES (27, 37, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 07:29:46', '183.253.127.71', '你好啊1312', '2022-03-19 20:54:11', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (28, 36, 'IP : 183.253.127.71的游客', '', '', 'hello', '2022-03-19 07:44:11', '183.253.127.71', '123', '2022-03-28 10:48:21', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (29, 35, 'IP : 183.253.127.71的游客', '', '', '不错', '2022-03-19 08:30:35', '183.253.127.71', '123', '2022-03-27 11:06:04', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (41, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (42, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (43, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (44, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (45, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 1);
INSERT INTO `tb_blog_comment` VALUES (46, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (47, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (48, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (49, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (50, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (51, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (52, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (53, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (54, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (55, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (56, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (57, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (58, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (59, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (60, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (61, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (62, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (63, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (64, 0, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-03-19 12:58:26', '183.253.127.71', '', NULL, 0, 0);
INSERT INTO `tb_blog_comment` VALUES (71, 37, 'IP : 183.253.127.71的游客', '', '', 'testse', '2022-03-20 09:13:31', '183.253.127.71', '哈哈', '2022-03-22 12:20:03', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (74, 35, 'IP : 183.253.127.71的游客', '', '', 'etw', '2022-03-26 08:09:20', '183.253.127.71', '123', '2022-03-27 11:06:08', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (75, 34, 'IP : 106.122.207.178的游客', '', '', 'hello', '2022-03-28 10:43:21', '106.122.207.178', '123123', '2022-04-08 20:20:18', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (76, 36, 'IP : 183.253.127.71的游客', '', '', '测试', '2022-04-05 12:49:04', '183.253.127.71', '123', '2022-04-05 12:49:08', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (85, 33, 'IP : 183.253.127.71的游客', '', '', '你好', '2022-04-09 11:43:49', '183.253.127.71', '你好', '2022-04-09 11:43:55', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (86, 33, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-04-09 11:44:28', '183.253.127.71', 'hh', '2022-04-09 11:44:34', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (87, 33, 'IP : 27.149.80.65的游客', '', '', '你好啊!', '2022-04-09 11:56:35', '27.149.80.65', '123', '2022-04-09 11:56:41', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (88, 33, 'IP : 183.253.127.71的游客', '', '', 'test', '2022-04-10 02:05:40', '183.253.127.71', '1341414', '2022-04-10 14:06:01', 0, 0);
INSERT INTO `tb_blog_comment` VALUES (89, 39, 'IP : 0:0:0:0:0:0:0:1的游客', '', '', '123456', '2022-04-11 20:36:22', '0:0:0:0:0:0:0:1', '2231', '2022-04-11 20:36:28', 0, 0);

-- ----------------------------
-- Table structure for tb_blog_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_config`;
CREATE TABLE `tb_blog_config`  (
  `config_field` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '字段名',
  `config_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置名',
  `config_value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '配置项的值',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`config_field`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_config
-- ----------------------------
INSERT INTO `tb_blog_config` VALUES ('sysAuthorImg', '开发者头像', 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWGg996AV9XDAAmDNXwTEpw205.png', '2019-08-24 20:33:14', '2019-08-24 21:56:23');
INSERT INTO `tb_blog_config` VALUES ('sysCopyRight', '版权所有', 'renrenzi(renrenzi) 访问官网', '2019-08-24 20:33:31', '2019-08-24 11:58:06');
INSERT INTO `tb_blog_config` VALUES ('SysDeveleper', '开发者', '任人子', '2021-11-18 23:57:51', '2021-11-18 23:57:51');
INSERT INTO `tb_blog_config` VALUES ('sysEmail', '开发者邮箱', '486084544@qq.com', '2019-08-24 14:06:48', '2019-08-24 14:06:51');
INSERT INTO `tb_blog_config` VALUES ('sysUpdateTime', '最后修改时间', '2021-11-25 20:33:23', '2019-08-24 20:33:20', '2021-11-25 20:23:19');
INSERT INTO `tb_blog_config` VALUES ('sysUrl', '服务器url', 'localhost:2235', '2019-08-24 14:03:23', '2022-01-19 03:18:25');
INSERT INTO `tb_blog_config` VALUES ('sysVersion', '当前版本号', '1.0.0', '2019-08-24 20:33:23', '2022-01-19 03:17:27');
INSERT INTO `tb_blog_config` VALUES ('websiteName', '微极客', '微极客JBlog', '2018-11-11 20:33:01', '2022-03-18 07:02:48');

-- ----------------------------
-- Table structure for tb_blog_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_info`;
CREATE TABLE `tb_blog_info`  (
  `blog_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客表主键id',
  `blog_title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客标题',
  `blog_sub_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客自定义路径url',
  `blog_preface` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客前言',
  `blog_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客内容',
  `blog_category_id` int(11) NOT NULL COMMENT '博客分类id',
  `blog_category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客分类(冗余字段)',
  `blog_tags` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客标签(冗余字段)',
  `blog_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-草稿 1-发布',
  `blog_views` bigint(20) NOT NULL DEFAULT 0 COMMENT '阅读量',
  `blog_likes` bigint(20) NULL DEFAULT NULL COMMENT '点赞数',
  `enable_comment` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-允许评论 1-不允许评论',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` datetime(0) NOT NULL COMMENT '添加时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`blog_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_info
-- ----------------------------
INSERT INTO `tb_blog_info` VALUES (1, '我是南街', 'about', '关于自己的简短介绍', '## About me\n\n我是南街:tw-1f471:，一名很普通的大三学生，写文章算是随心而欲的，基本上我想写什么就写什么，也不会为了谁谁谁而写。我弄这个博客写文章的初衷是为了记录自己的成长和锻炼自己。\n\n成长的路总是跌跌撞撞的，从大二到现在，差不多有两年了。在两年里，有过被人赞赏、被人肯定的喜悦；有过被人喷、被人怼的心酸；有过质疑自己的时候，甚至一度想就这样放弃……这个世界从不曾温情脉脉，也没有什么岁月静好，你我必须要非常努力才能争取到自己的一席之地。\n\n梦想永在，你不努力，谁也给不了你想要的生活。反问自己没有梦想，何必远方？\n\n相信浏览这段话的你也知道，学习是一件极其枯燥而无聊的过程，甚至有时候显得很无助，我也想告诉你，成长就是这样一件残酷的事情，任何成功都不是一蹴而就，需要坚持、需要付出、需要你的毅力，短期可能看不到收获，因为破茧成蝶所耗费的时间不是一天。\n\n最后，2020无畏年少青春，迎风潇洒前行！！\n## Contact\n\n我的邮箱：1320291471@qq.com\nQQ：1320291471\n欢迎加我交流', 24, '日常随笔', '默认标签', 1, 289, 10, 1, 1, '2019-09-06 10:01:50', '2022-01-19 03:16:26');
INSERT INTO `tb_blog_info` VALUES (7, 'SSM整合Redis做缓存', 'redis', '测试介绍', '前面我已经写过一篇博客简单介绍Redis的Java客户端和Redis的一整合操作，这里我们深入将Redis配置SpringCache注解来实现缓存，这样效率更高更快捷\n\n话不多说，直接晒代码\n\n一、redis配置文件\n```java\nredis.host=192.168.3.143\nredis.port=6379\nredis.dbIndex=1\nredis.expiration=3000\nredis.maxIdle=300\nredis.maxTotal=600\nredis.maxWaitMillis=1000\nredis.testOnBorrow=true\nredis.password=有密码就配，没有就不用\n```\n\n二、Spring-redis.xml文件\n\n\n```xml\n    <?xml version=\"1.0\" encoding=\"UTF-8\"?>\n    <beans xmlns=\"http://www.springframework.org/schema/beans\"\n           xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n           xmlns:context=\"http://www.springframework.org/schema/context\"\n           xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd\">\n    \n        <context:property-placeholder location=\"classpath:redis.properties\" ignore-unresolvable=\"true\" order=\"2\"></context:property-placeholder>\n        <!-- 扫描RedisCacheConfig  -->\n        <context:component-scan base-package=\"com.zl.config\" ></context:component-scan>\n    \n        <bean id=\"jedisPoolConfig\" class=\"redis.clients.jedis.JedisPoolConfig\">\n            <property name=\"maxIdle\" value=\"${redis.maxIdle}\"></property>\n            <property name=\"maxTotal\" value=\"${redis.maxTotal}\"></property>\n            <property name=\"maxWaitMillis\" value=\"${redis.maxWaitMillis}\"></property>\n            <property name=\"testOnBorrow\" value=\"${redis.testOnBorrow}\"></property>\n        </bean>\n    \n        <bean id=\"jedisConnectionFactory\" class=\"org.springframework.data.redis.connection.jedis.JedisConnectionFactory\">\n            <property name=\"hostName\" value=\"${redis.host}\"></property>\n            <property name=\"port\" value=\"${redis.port}\"></property>\n            <property name=\"database\" value=\"${redis.dbIndex}\"></property>\n            <property name=\"password\" value=\"${redis.password}\"></property>\n            <property name=\"poolConfig\" ref=\"jedisPoolConfig\"></property>\n        </bean>\n    \n        <bean id=\"redisTemplate\" class=\"org.springframework.data.redis.core.RedisTemplate\">\n            <property name=\"connectionFactory\" ref=\"jedisConnectionFactory\"></property>\n            <!--   key进行序列化配置，默认JDK改为String     -->\n            <property name=\"keySerializer\">\n                <bean class=\"org.springframework.data.redis.serializer.StringRedisSerializer\"></bean>\n            </property>\n            <property name=\"valueSerializer\">\n                <bean class=\"org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer\"></bean>\n            </property>\n            <property name=\"hashKeySerializer\">\n                <bean class=\"org.springframework.data.redis.serializer.StringRedisSerializer\"></bean>\n            </property>\n        </bean>\n    \n        <!-- 配置RedisCacheManager -->\n        <bean id=\"redisCacheManager\" class=\"org.springframework.data.redis.cache.RedisCacheManager\">\n            <constructor-arg name=\"redisOperations\" ref=\"redisTemplate\"></constructor-arg>\n            <!-- 配置过期时间-->\n            <property name=\"defaultExpiration\" value=\"3000\"></property>\n        </bean>\n    \n        <!-- 配置RedisCacheConfig -->\n        <bean id=\"redisCacheConfig\" class=\"com.zl.config.RedisCacheConfig\">\n            <constructor-arg ref=\"jedisConnectionFactory\"></constructor-arg>\n            <constructor-arg ref=\"redisTemplate\"></constructor-arg>\n            <constructor-arg ref=\"redisCacheManager\"></constructor-arg>\n        </bean>\n    </beans>\n```\n\n这里要注意valueSerializer的序列化方式，建议使用GenericJackson2JsonRedisSerializer，因为使用StringRedisSerializer在存入对象时会报类型转换异常，且使用GenericJackson2JsonRedisSerializer在Redis可视化工具RedisDesktopManager也可以看到不是乱码而是Json格式。效果如下：\n\n\n\n三、RedisCacheConfig配置类\n```java\npackage com.zl.config;\n\nimport org.springframework.cache.annotation.CachingConfigurerSupport;\nimport org.springframework.cache.annotation.EnableCaching;\nimport org.springframework.cache.interceptor.KeyGenerator;\nimport org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;\nimport org.springframework.data.redis.cache.RedisCacheManager;\nimport org.springframework.data.redis.connection.jedis.JedisConnectionFactory;\nimport org.springframework.data.redis.core.RedisTemplate;\n\nimport java.lang.reflect.Method;\n\n/**\n * @program: FruitSales\n * @classname: RedisCacheConfig\n * @description: Redis缓存配置\n * @create: 2019-07-01 18:56\n **/\n@Configuration\n@EnableCaching\npublic class RedisCacheConfig extends CachingConfigurerSupport {\n\n    private volatile JedisConnectionFactory jedisConnectionFactory;\n    private volatile RedisTemplate<String, String> redisTemplate;\n    private volatile RedisCacheManager redisCacheManager;\n\n    public RedisCacheConfig() {\n        super();\n    }\n\n    /**\n     * 带参数的构造方法 初始化所有的成员变量\n     *\n     * @param jedisConnectionFactory\n     * @param redisTemplate\n     * @param redisCacheManager\n     */\n    public RedisCacheConfig(JedisConnectionFactory jedisConnectionFactory, RedisTemplate<String, String> redisTemplate,\n                            RedisCacheManager redisCacheManager) {\n        this.jedisConnectionFactory = jedisConnectionFactory;\n        this.redisTemplate = redisTemplate;\n        this.redisCacheManager = redisCacheManager;\n    }\n\n    public JedisConnectionFactory getJedisConnecionFactory() {\n        return jedisConnectionFactory;\n    }\n\n    public RedisTemplate<String, String> getRedisTemplate() {\n        return redisTemplate;\n    }\n\n    public RedisCacheManager getRedisCacheManager() {\n        return redisCacheManager;\n    }\n\n    @Bean\n    public KeyGenerator keyGenerator() {\n        return new KeyGenerator() {\n            @Override\n            public Object generate(Object target, Method method,\n                                   Object... params) {\n                //规定  本类名+方法名+参数名 为key\n                StringBuilder sb = new StringBuilder();\n                sb.append(target.getClass().getName()+\"_\");\n                sb.append(method.getName()+\"_\");\n                for (Object obj : params) {\n                    sb.append(obj.toString()+\",\");\n                }\n                return sb.toString();\n            }\n        };\n    }\n}\n\n```\n四、接下来只要在需要缓存的方式上加注释啦\n如这样:在service方法上加@Cacheable注解\n\n\n\n常用注解：\n\n@Cacheable、@CachePut、@CacheEvict 注释介绍\n通过上面的例子，我们可以看到 spring cache 主要使用两个注释标签，即 @Cacheable、@CachePut 和 @CacheEvict，我们总结一下其作用和配置方法。', 22, 'Java进阶', '默认标签', 1, 39, 20, 1, 0, '2019-09-07 03:25:28', '2021-11-16 22:46:44');
INSERT INTO `tb_blog_info` VALUES (8, '一条Sql语句在数据库框架的执行过程', '', '撒旦', '阿萨', 1, '默认分类', '默认标签', 0, 1, 30, 1, 0, '2021-11-09 11:03:10', '2022-03-25 20:18:18');
INSERT INTO `tb_blog_info` VALUES (27, 'springboot', '/sping', '这是springboot', '***Springboot集成redis***', 20, 'About', '默认标签', 1, 0, 40, 1, 1, '2021-11-13 23:18:14', '2021-11-27 22:16:06');
INSERT INTO `tb_blog_info` VALUES (29, 'Linux安装Nginx', '/linux/nginx', 'Linux如何快速安装Nginx', '# Linux 安装 Nginx\n> 第一步:上传文件到Linux', 1, '默认分类', '默认标题', 1, 9, 50, 1, 0, '2021-11-18 18:35:14', '2021-11-27 22:16:03');
INSERT INTO `tb_blog_info` VALUES (30, '证明泛型的类型擦除', '/test', '123', '# 证明泛型的类型擦除\n``` java\n   public static void main(String[] args) throws Exception {\n        List<Integer> list = new ArrayList<Integer>();\n        list.add(1);\n        list.getClass().getMethod(\"add\", Object.class).invoke(list, \"任人子\");\n        for(int i = 0; i < list.size(); i++){\n            System.out.println(list.get(i));\n        }\n    }\n```\n![image.png](http://120.39.217.37:2234/group1/M00/00/00/rB8AYWI9sBaARVTHAAJmG-QqmsY190.png)\n\n![image 1.png](http://120.39.217.37:2234/group1/M00/00/00/rB8AYWI9sB6AUfdHAAEvQcPHQsU482.png)', 22, 'Java进阶', '默认标题', 1, 11, 60, 1, 0, '2021-11-18 18:36:39', '2022-03-25 20:17:01');
INSERT INTO `tb_blog_info` VALUES (31, '关于J-Blog如何集成FastDfs？', '', '集成FastDfs中遇到问题及解决？', '\n## 首先云服务器部署FastDFS\n> 如何部署不在赘述了，先查看服务端是否开启\n\n![image.png](http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJRaO-AbIyMAAEglsZV2g0460.png) \n- 我这里采用的是Nginx反向代理启动Fastdfs\n## 接下来看SpringBoot连接Fastdfs\n- 首先在resouces目录下添加fastdfs.conf配置文件，并添加一下内容\n![image.png](http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJRalyAFPI4AABbAcihfis235.png)\n- 创建FastDfsUtil工具类\n```java\npublic class FastDfsUtil {\n\n    private static TrackerServer trackerServer = null;\n    private static StorageServer storageServer = null;\n\n\n    public static StorageClient getStorageClient() throws IOException, MyException {\n        //1.加载配置文件，默认去classpath下加载\n        ClientGlobal.init(\"fastdfs.conf\");\n        //2.创建TrackerClient对象\n        TrackerClient trackerClient = new TrackerClient();\n        //3.创建TrackerServer对象\n        trackerServer = trackerClient.getConnection();\n        //4.创建StorageServler对象\n        storageServer = trackerClient.getStoreStorage(trackerServer);\n        //5.创建StorageClient对象，这个对象完成对文件的操作\n        StorageClient storageClient = new StorageClient(trackerServer,storageServer);\n        return storageClient;\n    }\n\n    public static void closeFastDFS() {\n        if (storageServer != null) {\n            try {\n                storageServer.close();\n            } catch (IOException e) {\n                e.printStackTrace();\n            }\n        }\n        if (trackerServer != null) {\n            try {\n                trackerServer.close();\n            } catch (IOException e) {\n                e.printStackTrace();\n            }\n        }\n    }\n\n   \n}\n```\n> 接下来就可以使用该工具类来上传文件了\n \n> 用写好的接口进行测试\n![image.png](http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJRa9iAGv28AAC9z3FIJ_M115.png)\n![image.png](http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJRbKKASKP2AAAncDfNeaU053.png)\n![91919258_p0.jpg](http://120.39.217.37:2234/group1/M00/00/00/rB8AYWGnjGqAZcZRAAPb51QDpG0349.jpg)', 31, '对象存储', '默认标题', 1, 13, 71, 1, 0, '2021-12-01 22:54:41', '2022-04-12 03:16:31');
INSERT INTO `tb_blog_info` VALUES (33, '我是任人子', 'about', '关于自己的简短介绍', '## About me\n\n我是任人子，一名很普通的大三学生，写文章算是随心而欲的，基本上我想写什么就写什么，也不会为了谁谁谁而写。我弄这个博客写文章的初衷是为了记录自己的成长和锻炼自己。\n\n成长的路总是跌跌撞撞的，从大二到现在，差不多有两年了。在两年里，有过被人赞赏、被人肯定的喜悦；有过被人喷、被人怼的心酸；有过质疑自己的时候，甚至一度想就这样放弃……这个世界从不曾温情脉脉，也没有什么岁月静好，你我必须要非常努力才能争取到自己的一席之地。\n\n梦想永在，你不努力，谁也给不了你想要的生活。反问自己没有梦想，何必远方？\n\n最后，2022无畏年少青春，迎风潇洒前行！！\n## Contact\n\n我的邮箱：486084544@qq.com\nQQ：486084544\n欢迎加我交流', 24, '日常随笔', '默认标题', 1, 305, 80, 1, 0, '2021-12-02 19:28:22', '2022-04-07 13:19:31');
INSERT INTO `tb_blog_info` VALUES (34, '如何使用Linux命令', '', '使用Linux命令', '# Mysql 相关命令\n> 查看进程 ，使用\n``` \nps -ef | grep mysql\n```\n> 启动mysql服务\n``` \n./mysqld_safe &\n```\n> 关闭mysql服务\n``` \n./mysqladmin -uroot -p shutdown\n```\n> 登录\n```\n./mysql -uroot -p\n```\n> 远程改密码\n```\ngrant all privileges on *.* to root@\'%\' identified by \'123456\';\n```\n\n\n\n', 1, '默认分类', 'Linux', 1, 22, 92, 1, 0, '2022-01-19 03:11:01', '2022-01-19 03:11:01');
INSERT INTO `tb_blog_info` VALUES (35, '关于 well-mall 实现 SpringCloud GateWay + Oauth2 完成统一认证授权', '/oauth', 'well-mall 集成SpringCloud GateWay', '# 关于 well-mall 实现 SpringCloud GateWay + Oauth2 完成统一认证授权(mall-auth端的配置，关于配置过程和出现的主要问题)(1)\n\n> 首先搭建 mall-auth 统一token 认证和获取\n\n## Oauth2认证服务搭建\n\n>  在Pom文件中添加依赖,主要有SpringSecurity、Oauth2、 redis、 jwt\n\n```Java\n<!-- SpringSecurity -->\n<dependency>\n    <groupId>org.springframework.boot</groupId>\n    <artifactId>spring-boot-starter-security</artifactId>\n</dependency>\n<!-- Oauth2 -->\n<dependency>\n    <groupId>org.springframework.cloud</groupId>\n    <artifactId>spring-cloud-starter-oauth2</artifactId>\n</dependency>\n<!-- JWT -->\n<dependency>\n    <groupId>com.nimbusds</groupId>\n    <artifactId>nimbus-jose-jwt</artifactId>\n</dependency>\n```\n\n> 集成SpringSecurity 需要实现 UserDetailsService 接口\n\n```java\n@Service\npublic class UserServiceImpl implements UserDetailsService {\n\n    @Resource\n    private UmsAdminService adminService;\n    @Resource\n    private HttpServletRequest request;\n\n    @Override\n    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {\n\n        String clientId = request.getParameter(\"client_id\");\n        UserDto userDto = null;\n        if(AuthConstant.ADMIN_CLIENT_ID.equals(clientId)){\n            // 调用微服务查询用户信息\n            userDto = adminService.loadUserByUsername(username);\n        }\n        if (userDto==null) {\n            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);\n        }\n        userDto.setClientId(clientId);\n        SecurityUser securityUser = new SecurityUser(userDto);\n        if (!securityUser.isEnabled()) {\n            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);\n        } else if (!securityUser.isAccountNonLocked()) {\n            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);\n        } else if (!securityUser.isAccountNonExpired()) {\n            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);\n        } else if (!securityUser.isCredentialsNonExpired()) {\n            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);\n        }\n        return securityUser;\n    }\n```\n\n> 接下来时Oauth2服务端的配置\n\n```java\n@AllArgsConstructor\n@Configuration\n@EnableAuthorizationServer\npublic class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {\n\n    private final PasswordEncoder passwordEncoder;\n    private final UserServiceImpl userDetailsService;\n    private final AuthenticationManager authenticationManager;\n    private final JwtTokenEnhancer jwtTokenEnhancer;\n\n    @Override\n    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {\n        clients.inMemory()\n                .withClient(\"admin-app\")\n                .secret(passwordEncoder.encode(\"123456\"))\n                .scopes(\"all\")\n                .authorizedGrantTypes(\"password\", \"refresh_token\")\n                .accessTokenValiditySeconds(3600*24)\n                .refreshTokenValiditySeconds(3600*24*7)\n                .and()\n                .withClient(\"portal-app\")\n                .secret(passwordEncoder.encode(\"123456\"))\n                .scopes(\"all\")\n                .authorizedGrantTypes(\"password\", \"refresh_token\")\n                .accessTokenValiditySeconds(3600*24)\n                .refreshTokenValiditySeconds(3600*24*7);\n    }\n\n    @Override\n    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {\n        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();\n        List<TokenEnhancer> delegates = new ArrayList<>();\n        delegates.add(jwtTokenEnhancer);\n        delegates.add(accessTokenConverter());\n        enhancerChain.setTokenEnhancers(delegates); //配置JWT的内容增强器\n        endpoints.authenticationManager(authenticationManager)\n                .userDetailsService(userDetailsService) //配置加载用户信息的服务\n                .accessTokenConverter(accessTokenConverter())\n                .tokenEnhancer(enhancerChain);\n    }\n      @Override\n    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {\n        security.allowFormAuthenticationForClients();\n    }\n\n    @Bean\n    public JwtAccessTokenConverter accessTokenConverter() {\n        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();\n        jwtAccessTokenConverter.setKeyPair(keyPair());\n        return jwtAccessTokenConverter;\n    }\n\n    @Bean\n    public KeyPair keyPair() {\n        //从classpath下的证书中获取秘钥对\n        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(\"jwt.jks\"), \"123456\".toCharArray());\n        return keyStoreKeyFactory.getKeyPair(\"jwt\", \"123456\".toCharArray());\n    }\n}\n```\n\n***jwt.jks匹配文件必须放在resouces目录下***\n\n> 关于如何获取秘钥对\n\n```\nkeytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks\n```\n\n> 编写获取Jwt令牌接口\n\n```java\n@RestController\n@Api(tags = \"AuthController\", description = \"认证中心登录认证\")\n@RequestMapping(\"/oauth\")\npublic class AuthController {\n\n    @Autowired\n    private TokenEndpoint tokenEndpoint;\n\n    @ApiOperation(\"Oauth2获取token\")\n    @ApiImplicitParams({\n            @ApiImplicitParam(name = \"grant_type\", value = \"授权模式\", required = true),\n            @ApiImplicitParam(name = \"client_id\", value = \"Oauth2客户端ID\", required = true),\n            @ApiImplicitParam(name = \"client_secret\", value = \"Oauth2客户端秘钥\", required = true),\n            @ApiImplicitParam(name = \"refresh_token\", value = \"刷新token\"),\n            @ApiImplicitParam(name = \"username\", value = \"登录用户名\"),\n            @ApiImplicitParam(name = \"password\", value = \"登录密码\")\n    })\n    @RequestMapping(value = \"/token\", method = RequestMethod.POST)\n    public CommonResult<Oauth2TokenDto> postAccessToken(@ApiIgnore Principal principal, @ApiIgnore @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {\n        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();\n        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()\n                .token(oAuth2AccessToken.getValue())\n                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())\n                .expiresIn(oAuth2AccessToken.getExpiresIn())\n                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX).build();\n\n        return CommonResult.success(oauth2TokenDto);\n    }\n}\n```\n\n> 编写获取RSA公匙接口(暴露给mall-gateway获取)\n\n```java\n@RestController\npublic class KeyPairController {\n\n    @Resource\n    private KeyPair keyPair;\n\n    @GetMapping(\"/rsa/publicKey\")\n    public Map<String, Object> getKey() {\n        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();\n        RSAKey key = new RSAKey.Builder(publicKey).build();\n        return new JWKSet(key).toJSONObject();\n    }\n\n}\n```\n\n> 最后不要忘了Sercurity配置\n\n```java\n@Configuration\n@EnableWebSecurity\npublic class WebSecurityConfig extends WebSecurityConfigurerAdapter {\n\n    @Override\n    protected void configure(HttpSecurity http) throws Exception {\n        http.csrf().disable()\n                .authorizeRequests()\n                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()\n                // 暴露无需认证的接口\n            	.antMatchers(\"/rsa/publicKey\").permitAll()\n                .anyRequest().authenticated();\n    }\n\n    @Bean\n    @Override\n    public AuthenticationManager authenticationManagerBean() throws Exception {\n        return super.authenticationManagerBean();\n    }\n\n    @Bean\n    public PasswordEncoder passwordEncoder() {\n        return new BCryptPasswordEncoder();\n    }\n\n}\n```\n\n', 22, 'Java进阶', '权限认证', 1, 34, 102, 1, 0, '2022-03-06 13:15:29', '2022-03-22 12:18:43');
INSERT INTO `tb_blog_info` VALUES (36, '关于 well-mall 实现 SpringCloud GateWay + Oauth2 完成统一认证授权（2）', '/oauth', 'mall-gateway 服务搭建', '# 关于 well-mall 实现 SpringCloud GateWay + Oauth2 完成统一认证授权（2）\n\n> mall-gateway 服务搭建\n\n> 首先Pom文件依赖和application.yml的配置\n\n```xml\n<!-- SpringCloud Gateway -->\n<dependency>\n    <groupId>org.springframework.cloud</groupId>\n    <artifactId>spring-cloud-starter-gateway</artifactId>\n</dependency>\n<dependency>\n    <groupId>com.github.xiaoymin</groupId>\n    <artifactId>knife4j-spring-boot-starter</artifactId>\n</dependency>\n<dependency>\n    <groupId>org.springframework.security</groupId>\n    <artifactId>spring-security-config</artifactId>\n</dependency>\n<dependency>\n    <groupId>org.springframework.security</groupId>\n    <artifactId>spring-security-oauth2-resource-server</artifactId>\n</dependency>\n<dependency>\n    <groupId>org.springframework.security</groupId>\n    <artifactId>spring-security-oauth2-client</artifactId>\n</dependency>\n<dependency>\n    <groupId>org.springframework.security</groupId>\n    <artifactId>spring-security-oauth2-jose</artifactId>\n</dependency>\n<dependency>\n    <groupId>com.nimbusds</groupId>\n    <artifactId>nimbus-jose-jwt</artifactId>\n</dependency>\n<dependency>\n    <groupId>org.springframework.boot</groupId>\n    <artifactId>spring-boot-starter-data-redis</artifactId>\n</dependency>\n```\n\n```shell\nspring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true # 开启从注册中心动态创建路由的功能\n          lower-case-service-id: true #使用小写service-id\n      routes: #配置路由路径\n        - id: mall-auth\n          uri: lb://mall-auth\n          predicates:\n            - Path=/mall-auth/**\n          filters:\n            - StripPrefix=1\n        - id: mall-admin\n          uri: lb://mall-admin\n          predicates:\n            - Path=/mall-admin/**\n          filters:\n            - StripPrefix=1\n  security:\n    oauth2:\n      resourceserver:\n        jwt:\n          jwk-set-uri: \'http://localhost:8201/mall-auth/rsa/publicKey\' #配置RSA的公钥访问地址\n```\n\n> 首先是资源服务器的配置\n\n```java\n@AllArgsConstructor\n@Configuration\n@EnableWebFluxSecurity\npublic class ResourceServerConfig {\n    private final AuthorizationManager authorizationManager;\n    private final IgnoreUrlsConfig ignoreUrlsConfig;\n    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;\n    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;\n    private final IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;\n\n    @Bean\n    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {\n        http.oauth2ResourceServer().jwt()\n                .jwtAuthenticationConverter(jwtAuthenticationConverter());\n        //自定义处理JWT请求头过期或签名错误的结果\n      http.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);\n        //对白名单路径，直接移除JWT请求头\n        http.addFilterBefore(ignoreUrlsRemoveJwtFilter,SecurityWebFiltersOrder.AUTHENTICATION);\n        http.authorizeExchange()\n                .pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(),String.class)).permitAll()//白名单配置\n                .anyExchange().access(authorizationManager)//鉴权管理器配置\n                .and().exceptionHandling()\n                .accessDeniedHandler(restfulAccessDeniedHandler)//处理未授权\n                .authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证\n                .and().csrf().disable();\n        return http.build();\n    }\n\n}\n```\n\n> 自定义鉴权管理器要实现 ReactiveAuthorizationManager 接口\n\n```java\n@Component\npublic class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {\n\n@Autowired\nprivate RedisTemplate<String, Object> redisTemplate;\n@Autowired\nprivate IgnoreUrlsConfig ignoreUrlsConfig;\n\n@Override\npublic Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {\n    ServerHttpRequest request = authorizationContext.getExchange().getRequest();\n    URI uri = request.getURI();\n    authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());\n    //认证通过且角色匹配的用户可访问当前路径\n    return mono\n            .filter(Authentication::isAuthenticated)\n            .flatMapIterable(Authentication::getAuthorities)\n            .map(GrantedAuthority::getAuthority)\n            .any(authorities::contains)\n            .map(AuthorizationDecision::new)\n            .defaultIfEmpty(new AuthorizationDecision(false));\n}\n}\n```\n\n> 鉴权成功使用 全局过滤器 将登录用户的JWT转化成用户信息, 需要实现 GlobalFilter, Ordered 这两个接口\n\n```java\n@Component\npublic class AuthGlobalFilter implements GlobalFilter, Ordered {\n\n    private static Logger LOGGER = LoggerFactory.getLogger(AuthGlobalFilter.class);\n\n    @Override\n    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {\n        String token = exchange.getRequest().getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);\n        if (StrUtil.isEmpty(token)) {\n            return chain.filter(exchange);\n        }\n        try {\n            //从token中解析用户信息并设置到Header中去\n            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, \"\");\n            JWSObject jwsObject = JWSObject.parse(realToken);\n            String userStr = jwsObject.getPayload().toString();\n            ServerHttpRequest request = exchange.getRequest().mutate().header(AuthConstant.USER_TOKEN_HEADER, userStr).build();\n            exchange = exchange.mutate().request(request).build();\n        } catch (ParseException e) {\n            e.printStackTrace();\n        }\n        return chain.filter(exchange);\n    }\n    @Override\n    public int getOrder() {\n        return 0;\n    }\n}\n```\n\n> 自定义返回结果 ServerAuthenticationEntryPoint 接口(处理授权未成功)，ServerAccessDeniedHandler 接口(处理权限不足) 可以分别实现两个接口来实现自定义返回接口，并在ResourceServerConfig配置。\n\n```java\n@Component\npublic class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {\n    @Override\n    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {\n        ServerHttpResponse response = exchange.getResponse();\n        response.setStatusCode(HttpStatus.OK);\n        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);\n        response.getHeaders().set(\"Access-Control-Allow-Origin\",\"*\");\n        response.getHeaders().set(\"Cache-Control\",\"no-cache\");\n        String body= JSONUtil.toJsonStr(CommonResult.forbidden(denied.getMessage()));\n        DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(Charset.forName(\"UTF-8\")));\n        return response.writeWith(Mono.just(buffer));\n    }\n}\n```\n\n```java\n@Component\npublic class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {\n    @Override\n    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {\n        ServerHttpResponse response = exchange.getResponse();\n        response.setStatusCode(HttpStatus.OK);\n        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);\n        response.getHeaders().set(\"Access-Control-Allow-Origin\",\"*\");\n        response.getHeaders().set(\"Cache-Control\",\"no-cache\");\n        String body= JSONUtil.toJsonStr(CommonResult.unauthorized(e.getMessage()));\n        DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(Charset.forName(\"UTF-8\")));\n        return response.writeWith(Mono.just(buffer));\n    }\n}\n\n```\n\n***自我总结：在认证授权中，其本质上是在于对拦截器、过滤器的使用！***', 29, 'SpringCloud', '权限认证', 1, 3939, 124, 1, 0, '2022-03-07 15:11:15', '2022-03-07 15:11:15');
INSERT INTO `tb_blog_info` VALUES (37, 'CentOS安装Mysql', '/centos', '快速安装Mysql并运行', '# CentOS 7.8  Docker 安装 Mysql并运行\n\n> 查看镜像\n\n```shell\ndocker search mysql\n```\n\n![image20220308184350143.png](http://120.39.217.37:2234/group1/M00/00/00/rB8AYWIsSWyAaOdWAANvT7FSQXU238.png)\n> 拉取镜像\n\n```shell\ndocker pull mysql\n```\n\n> 运行镜像\n\n```\ndocker run --name mysql-doc -p 3306:3306 -v /opt/mysql/data:/var/lib/mysql -v /opt/mysql/data:/var/lib/mysql  -e MYSQL_ROOT_PASSWORD=123456 -d mysql\n```\n\n- -name : 指定镜像名\n- -p : 指定映射(主机端口:容器端口)\n- -v : 将主机路径/opt/mysql/data挂载到容器内:/var/lib/mysql下\n\n![9eb1300e27224a8dc3ab36f9ce68f9ba335ff4e8.jpg](http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJRdj6ABQBhAACjCk1_OqA655.jpg)', 28, 'Centos7', 'Linux', 1, 5216, 127, 1, 0, '2022-03-08 10:53:47', '2022-04-09 12:04:18');
INSERT INTO `tb_blog_info` VALUES (39, 'Redis 的三种缓存读写策略', '', '三种缓存读写策略', '## 旁路缓存模式(Cache Aside Pattern)\n写： 先更新DB,再删除缓存Cache\n读：先查看Cache缓存中是否存在，如果有直接返回。如果没有，则从DB中取数据返回，然后再写入缓存中。\n## 读写穿透模式(Read/Write Through Pattern)\n写 : 先查看Cache缓存中是否有，如果存在就更新缓存Cache,然后由缓存更新DB数据库。\n读：先查看缓存是否存在，存在直接返回，不存在查询DB然后更新缓存后返回。\n## Write Behind Pattern (异步缓存写入)\n写: 查询缓存是否存在，存在直接更新缓存。设置一个时间段(定时任务)来进行数据同步。\n读：查询缓存是否存在，存在直接读取缓存。不存在查询数据库\n（点赞量，浏览量等更新频率高的）\n\n![300a58773d6d71f31d1efac46642f838a4b094f7.jpg](http://120.39.217.37:2234/group1/M00/00/00/rB8AYWJRclGAWXZbAACA2EAvAYQ503.jpg)', 20, 'About', 'Redis', 1, 257, 199, 1, 0, '2022-04-09 11:47:33', '2022-04-09 11:47:33');

-- ----------------------------
-- Table structure for tb_blog_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_link`;
CREATE TABLE `tb_blog_link`  (
  `link_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '友链表主键id',
  `link_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '友链类别 0-友链 1-推荐 2-个人网站',
  `link_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站名称',
  `link_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站链接',
  `link_description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站描述',
  `link_rank` int(11) NOT NULL DEFAULT 0 COMMENT '用于列表排序',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '友情链接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_link
-- ----------------------------
INSERT INTO `tb_blog_link` VALUES (2, 0, '任人子的GITHUB', 'https://github.com/Zinner2/J-BLOG-web', '哈哈哈', 0, 0, '2019-09-02 21:24:44');
INSERT INTO `tb_blog_link` VALUES (27, 1, 'LeetCode', 'https://leetcode-cn.com/leetbook/', '面试刷题网站', 123, 0, '2022-03-13 14:19:33');
INSERT INTO `tb_blog_link` VALUES (29, 1, 'stackoverflow', 'https://stackoverflow.com/questions/tagged/java', 'bug解决网站', 123, 1, '2022-03-13 14:21:14');
INSERT INTO `tb_blog_link` VALUES (32, 1, '哔哩哔哩', 'https://www.bilibili.com/', '知名学习网站', 100, 0, '2022-03-19 07:46:14');
INSERT INTO `tb_blog_link` VALUES (34, 1, 'CODEIF', 'https://unbug.github.io/codelf/', '变量命名网站', 100, 0, '2022-03-19 07:47:55');
INSERT INTO `tb_blog_link` VALUES (35, 2, '微极客', 'http://120.39.217.37:2233/', '这是一个个人博客网站', 123, 0, '2022-03-19 13:04:11');
INSERT INTO `tb_blog_link` VALUES (36, 0, '语雀', 'https://www.yuque.com/dashboard', '笔记小Kip', 0, 0, '2022-03-25 20:19:12');

-- ----------------------------
-- Table structure for tb_blog_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag`;
CREATE TABLE `tb_blog_tag`  (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tag_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 146 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_tag
-- ----------------------------
INSERT INTO `tb_blog_tag` VALUES (1, '默认标题', 0, '2019-09-01 11:19:47');
INSERT INTO `tb_blog_tag` VALUES (57, '世界上有一个很可爱的人', 1, '2018-11-12 00:31:15');
INSERT INTO `tb_blog_tag` VALUES (58, '现在这个人就在看这句话', 1, '2018-11-12 00:31:15');
INSERT INTO `tb_blog_tag` VALUES (139, 'NoSQL', 0, '2019-08-06 21:23:38');
INSERT INTO `tb_blog_tag` VALUES (140, 'Redis', 0, '2021-11-18 01:48:50');
INSERT INTO `tb_blog_tag` VALUES (142, 'RabbitMq', 0, '2021-11-18 18:32:56');
INSERT INTO `tb_blog_tag` VALUES (143, 'Linux', 0, '2021-11-18 18:35:32');
INSERT INTO `tb_blog_tag` VALUES (144, 'Spring', 0, '2022-03-06 12:50:02');
INSERT INTO `tb_blog_tag` VALUES (145, '权限认证', 0, '2022-03-06 13:13:36');

-- ----------------------------
-- Table structure for tb_blog_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tag_relation`;
CREATE TABLE `tb_blog_tag_relation`  (
  `relation_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系表id',
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 377 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '博客跟标签的关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_tag_relation
-- ----------------------------
INSERT INTO `tb_blog_tag_relation` VALUES (355, 1, 1, '2021-11-18 10:42:26');
INSERT INTO `tb_blog_tag_relation` VALUES (356, 1, 1, '2021-11-18 10:42:26');
INSERT INTO `tb_blog_tag_relation` VALUES (357, 7, 1, '2021-11-18 10:42:26');
INSERT INTO `tb_blog_tag_relation` VALUES (358, 8, 1, '2021-11-18 10:42:26');
INSERT INTO `tb_blog_tag_relation` VALUES (359, 8, 1, '2021-11-18 10:42:26');
INSERT INTO `tb_blog_tag_relation` VALUES (360, 27, 1, '2021-11-18 10:42:26');
INSERT INTO `tb_blog_tag_relation` VALUES (361, 29, 1, '2021-11-18 18:35:14');
INSERT INTO `tb_blog_tag_relation` VALUES (362, 30, 142, '2021-11-18 18:36:39');
INSERT INTO `tb_blog_tag_relation` VALUES (363, 31, 1, '2021-12-01 22:54:41');
INSERT INTO `tb_blog_tag_relation` VALUES (365, 33, 1, '2021-12-02 19:28:22');
INSERT INTO `tb_blog_tag_relation` VALUES (366, 34, 143, '2022-01-19 11:11:01');
INSERT INTO `tb_blog_tag_relation` VALUES (367, 35, 145, '2022-03-06 21:15:28');
INSERT INTO `tb_blog_tag_relation` VALUES (368, 36, 145, '2022-03-07 23:11:15');
INSERT INTO `tb_blog_tag_relation` VALUES (369, 37, 143, '2022-03-08 18:53:47');
INSERT INTO `tb_blog_tag_relation` VALUES (371, 39, 140, '2022-04-09 19:47:33');

-- ----------------------------
-- Table structure for tb_login_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_log`;
CREATE TABLE `tb_login_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `login_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录用户名',
  `login_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '登录时间',
  `login_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录用户Ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_login_log
-- ----------------------------
INSERT INTO `tb_login_log` VALUES (2, 'admin', '2022-04-10 21:35:29', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (3, 'admin', '2022-04-10 21:35:45', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (4, 'admin', '2022-04-10 21:35:48', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (5, 'admin', '2022-04-10 21:35:50', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (6, 'admin', '2022-04-10 22:19:08', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (7, 'admin', '2022-04-10 22:24:59', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (8, 'admin', '2022-04-10 22:25:09', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (9, 'admin', '2022-04-10 22:46:56', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (10, 'admin', '2022-04-10 22:53:20', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (11, 'admin', '2022-04-10 22:53:28', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (12, 'admin', '2022-04-10 22:56:27', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (13, 'admin', '2022-04-10 23:04:52', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (14, 'admin', '2022-04-10 23:13:28', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (15, 'admin', '2022-04-11 19:34:07', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (16, 'admin', '2022-04-11 19:55:52', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (17, 'admin', '2022-04-11 20:03:31', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (18, 'admin', '2022-04-11 20:10:36', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (19, 'admin', '2022-04-11 20:10:52', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (20, 'admin', '2022-04-11 20:13:01', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (21, 'admin', '2022-04-11 20:17:13', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (22, 'admin', '2022-04-11 20:17:46', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (23, 'admin', '2022-04-11 20:48:28', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (24, 'admin', '2022-04-11 20:54:10', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (25, 'admin', '2022-04-11 21:10:59', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (26, 'admin', '2022-04-11 21:11:09', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (27, 'admin', '2022-04-11 21:12:42', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (28, 'admin', '2022-04-11 21:13:02', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (29, 'admin', '2022-04-11 21:13:29', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (30, 'admin', '2022-04-11 21:26:15', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (31, 'admin', '2022-04-11 22:22:04', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (32, 'admin', '2022-04-11 22:36:49', '0:0:0:0:0:0:0:1');
INSERT INTO `tb_login_log` VALUES (33, 'admin', '2022-04-12 03:16:04', '218.83.127.168');

SET FOREIGN_KEY_CHECKS = 1;
