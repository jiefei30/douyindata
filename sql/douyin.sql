/*
 Source Schema         : douyin
 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 30/09/2021 15:14:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `room_id` bigint UNSIGNED NOT NULL COMMENT '直播间id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '直播间名称',
  `display_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户抖音号',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `user_count` int NULL DEFAULT NULL COMMENT '观看人数',
  `sec_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '直播人加密id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint UNSIGNED NULL DEFAULT 1 COMMENT '1有效，0无效',
  PRIMARY KEY (`room_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for rooms_users
-- ----------------------------
DROP TABLE IF EXISTS `rooms_users`;
CREATE TABLE `rooms_users`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint UNSIGNED NOT NULL COMMENT '直播间id',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
  `scor` int NOT NULL DEFAULT 0 COMMENT '用户本直播间刷了多少礼物',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint UNSIGNED NULL DEFAULT 1 COMMENT '保留字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `room_user_id`(`room_id`, `user_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 124567348 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL COMMENT '用户id',
  `sec_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '加密用户id',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '男' COMMENT '性别（全部是男）',
  `level` int NULL DEFAULT NULL COMMENT '用户礼物等级',
  `display_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户抖音号',
  `province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市',
  `school_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学校',
  `signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `follower_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '粉丝数量',
  `following_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '关注数量',
  `forward_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '分析数量',
  `aweme_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '发布抖音数',
  `total_favorited` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '总获得点赞数',
  `is_secret` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '1私密  0公开',
  `last_pay_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次打赏时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '保留字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
