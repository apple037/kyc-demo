-- --------------------------------------------------------
-- 主機:                           127.0.0.1
-- 伺服器版本:                        5.7.37 - MySQL Community Server (GPL)
-- 伺服器作業系統:                      Linux
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 傾印 kyc_demo 的資料庫結構
CREATE DATABASE IF NOT EXISTS `kyc_demo` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `kyc_demo`;

-- 傾印  資料表 kyc_demo.admin 結構
CREATE TABLE IF NOT EXISTS `admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) NOT NULL COMMENT '使用者名稱',
  `password` varchar(255) NOT NULL COMMENT '密碼',
  `type` varchar(64) NOT NULL COMMENT '類型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 取消選取資料匯出。

-- 傾印  資料表 kyc_demo.kyc_apply 結構
CREATE TABLE IF NOT EXISTS `kyc_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '流水',
  `member_id` int(11) NOT NULL COMMENT '會員id',
  `real_name` varchar(128) NOT NULL COMMENT '真實姓名',
  `identity_code` varchar(64) NOT NULL COMMENT '身分證號碼',
  `birthdate` date NOT NULL COMMENT '出生日期',
  `nationality` varchar(64) NOT NULL COMMENT '國籍',
  `job` varchar(64) NOT NULL COMMENT '職業別',
  `title` varchar(64) NOT NULL COMMENT '職稱',
  `is_multi_nationality` tinyint(1) NOT NULL COMMENT '是否為多重國籍',
  `legal_compliance_reason` int(11) DEFAULT NULL COMMENT '法遵審核原因',
  `risk_reason` int(11) DEFAULT NULL COMMENT '風險審核原因',
  `status` varchar(32) NOT NULL COMMENT '審核狀態',
  `id_front` bigint(20) NOT NULL COMMENT '正面照',
  `id_back` bigint(20) NOT NULL COMMENT '反面照',
  `id_hand` bigint(20) NOT NULL COMMENT '手持照',
  `second_id` bigint(20) NOT NULL COMMENT '第二證件照',
  PRIMARY KEY (`id`),
  UNIQUE KEY `only_one_apply` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- 取消選取資料匯出。

-- 傾印  資料表 kyc_demo.kyc_audit_info 結構
CREATE TABLE IF NOT EXISTS `kyc_audit_info` (
  `id` bigint(14) NOT NULL,
  `apply_id` bigint(14) NOT NULL COMMENT '實名申請id',
  `type` varchar(32) NOT NULL COMMENT '資料類型',
  `image_ids` varchar(256) NOT NULL COMMENT '圖片ids',
  `customer_memo` varchar(256) NOT NULL COMMENT '客服註記',
  `legal_memo` varchar(256) NOT NULL COMMENT '法尊註記'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 取消選取資料匯出。

-- 傾印  資料表 kyc_demo.kyc_audit_risk 結構
CREATE TABLE IF NOT EXISTS `kyc_audit_risk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '編號',
  `member_id` int(11) NOT NULL COMMENT '會員id',
  `fact` int(11) NOT NULL COMMENT '因素',
  `point` int(11) NOT NULL COMMENT '分數',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 取消選取資料匯出。

-- 傾印  資料表 kyc_demo.kyc_reason 結構
CREATE TABLE IF NOT EXISTS `kyc_reason` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '編號',
  `type` varchar(64) NOT NULL COMMENT '類型',
  `description` varchar(255) NOT NULL COMMENT '原因描述',
  `point` int(11) NOT NULL DEFAULT '0' COMMENT '分數',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 取消選取資料匯出。

-- 傾印  資料表 kyc_demo.member 結構
CREATE TABLE IF NOT EXISTS `member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '編號',
  `member_id` varchar(64) NOT NULL COMMENT '會員id',
  `username` varchar(255) NOT NULL COMMENT '會員名稱 email',
  `password` varchar(255) NOT NULL COMMENT '密碼',
  `salt` varchar(255) DEFAULT NULL COMMENT 'salt',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真實姓名',
  `identity_code` varchar(64) DEFAULT NULL COMMENT '身分證字號',
  `register_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '註冊時間',
  `status` varchar(64) DEFAULT NULL COMMENT '會員狀態',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_unique` (`username`),
  UNIQUE KEY `identity_code_unique` (`identity_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- 取消選取資料匯出。

-- 傾印  資料表 kyc_demo.util_image 結構
CREATE TABLE IF NOT EXISTS `util_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '圖片id',
  `url` varchar(255) NOT NULL COMMENT '圖片url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 取消選取資料匯出。

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
