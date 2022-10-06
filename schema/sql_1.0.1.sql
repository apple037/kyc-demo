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
CREATE DATABASE IF NOT EXISTS `kyc_demo_local` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `kyc_demo_local`;

-- 傾印  資料表 kyc_demo.admin 結構
CREATE TABLE IF NOT EXISTS `admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) NOT NULL COMMENT '使用者名稱',
  `password` varchar(255) NOT NULL COMMENT '密碼',
  `type` varchar(64) NOT NULL COMMENT '類型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  kyc_demo.admin 的資料：~1 rows (近似值)
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` (`id`, `username`, `password`, `type`) VALUES
	(1, 'admin1', '1234', 'customer');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  kyc_demo.kyc_apply 的資料：~1 rows (近似值)
/*!40000 ALTER TABLE `kyc_apply` DISABLE KEYS */;
INSERT INTO `kyc_apply` (`id`, `member_id`, `real_name`, `identity_code`, `birthdate`, `nationality`, `job`, `title`, `is_multi_nationality`, `legal_compliance_reason`, `risk_reason`, `status`, `id_front`, `id_back`, `id_hand`, `second_id`) VALUES
	(9, 3, '123', '1234', '1995-07-31', 'taiwan', '654', '321', 0, NULL, NULL, 'ID_AUDIT', 1, 2, 3, 4);
/*!40000 ALTER TABLE `kyc_apply` ENABLE KEYS */;

-- 傾印  資料表 kyc_demo.kyc_audit_info 結構
CREATE TABLE IF NOT EXISTS `kyc_audit_info` (
  `id` bigint(14) NOT NULL AUTO_INCREMENT,
  `apply_id` bigint(14) NOT NULL COMMENT '實名申請id',
  `type` varchar(32) NOT NULL COMMENT '資料類型',
  `image_ids` varchar(256) DEFAULT NULL COMMENT '圖片ids',
  `customer_memo` varchar(256) DEFAULT NULL COMMENT '客服註記',
  `legal_memo` varchar(256) DEFAULT NULL COMMENT '法尊註記',
  PRIMARY KEY (`id`),
  KEY `apply_audit` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  kyc_demo.kyc_audit_info 的資料：~9 rows (近似值)
/*!40000 ALTER TABLE `kyc_audit_info` DISABLE KEYS */;
INSERT INTO `kyc_audit_info` (`id`, `apply_id`, `type`, `image_ids`, `customer_memo`, `legal_memo`) VALUES
	(1, 9, 'OVERALL', '1,2', 'test', 'test2'),
	(2, 9, 'RCA', '3,4', 'testrca', 'test2'),
	(3, 9, 'PEP', NULL, NULL, NULL),
	(4, 9, 'BLACKLIST', NULL, NULL, NULL),
	(5, 9, 'NEGATIVE', NULL, NULL, NULL),
	(6, 9, 'OTHER', NULL, NULL, NULL),
	(7, 9, 'TELEPHONE', NULL, NULL, NULL),
	(8, 9, 'STATEMENT', NULL, NULL, NULL),
	(9, 9, 'LEGAL', NULL, NULL, NULL);
/*!40000 ALTER TABLE `kyc_audit_info` ENABLE KEYS */;

-- 傾印  資料表 kyc_demo.kyc_audit_risk 結構
CREATE TABLE IF NOT EXISTS `kyc_audit_risk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '編號',
  `member_id` int(11) NOT NULL COMMENT '會員id',
  `fact` int(11) NOT NULL COMMENT '因素',
  `point` int(11) NOT NULL COMMENT '分數',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  kyc_demo.kyc_audit_risk 的資料：~0 rows (近似值)
/*!40000 ALTER TABLE `kyc_audit_risk` DISABLE KEYS */;
/*!40000 ALTER TABLE `kyc_audit_risk` ENABLE KEYS */;

-- 傾印  資料表 kyc_demo.kyc_reason 結構
CREATE TABLE IF NOT EXISTS `kyc_reason` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '編號',
  `doc_id` int(11) NOT NULL COMMENT '編號',
  `type` int(11) NOT NULL COMMENT '類別',
  `description` varchar(255) NOT NULL COMMENT '原因描述',
  `point` int(11) NOT NULL DEFAULT '0' COMMENT '分數',
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  CONSTRAINT `kyc_reason_ibfk_1` FOREIGN KEY (`type`) REFERENCES `kyc_reason_subtype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  kyc_demo.kyc_reason 的資料：~153 rows (近似值)
/*!40000 ALTER TABLE `kyc_reason` DISABLE KEYS */;
INSERT INTO `kyc_reason` (`id`, `doc_id`, `type`, `description`, `point`) VALUES
	(1, 1, 1, '姓名檢核命中制裁名單對象', 12),
	(2, 2, 1, '現任國內外PEPs', 8),
	(3, 3, 1, '現任國內外RCA', 8),
	(4, 4, 1, '離任國內外PEPs及RCA，並且未具較低風險因子者', 8),
	(5, 5, 1, '經NS程序命中負面新聞名單者，且確定為同一人者', 4),
	(6, 1, 2, '洗錢', 12),
	(7, 2, 2, '資恐犯罪', 12),
	(8, 3, 2, '人口販賣', 12),
	(9, 4, 2, '凌虐兒童', 12),
	(10, 5, 2, '貪汙賄賂', 12),
	(11, 6, 2, '毒品', 12),
	(12, 7, 2, '詐欺', 12),
	(13, 8, 2, '賭博', 12),
	(14, 9, 2, '組織犯罪', 8),
	(15, 10, 2, '走私', 8),
	(16, 11, 2, '稅務犯罪', 8),
	(17, 12, 2, '證券犯罪', 8),
	(18, 13, 2, '智慧財產犯罪', 8),
	(19, 1, 3, '經確認使用匿名、假名、人頭、虛設行號或虛設法人、團體建立業務關係', 12),
	(20, 2, 3, '經查證後，持用偽、變造身分證明文件', 12),
	(21, 3, 3, '不願提供佐證資料或確認客戶身分程序', 12),
	(22, 1, 4, '八大產業（包含舞廳業、舞場業、酒家業、酒吧業、特種咖啡茶室業、視聽歌唱業、夜店業及三溫暖業，但不包含一般KTV）', 12),
	(23, 2, 4, '毒品產業', 12),
	(24, 3, 4, '博弈產業', 12),
	(25, 4, 4, '國防武器或戰爭設備等軍火製造相關，不包含中科院、台船', 8),
	(26, 5, 4, '核子能源', 8),
	(27, 6, 4, '大使館或領事館', 8),
	(28, 7, 4, '非營利組織（包含基金會、紅十字會、人權團體、財團法人、教會等）', 4),
	(29, 8, 4, '金融、保險及VASP業者', 4),
	(30, 9, 4, '珠寶及貴金屬批發或零售業', 4),
	(31, 10, 4, '礦業及土石採取業', 4),
	(32, 11, 4, '旅行及相關代訂服務業', 4),
	(33, 12, 4, '汽車批發或零售業', 4),
	(34, 13, 4, '法律及會計服務業（包含代書）', 4),
	(35, 14, 4, '企業總管理機構及管理顧問業', 4),
	(36, 15, 4, '提供金融服務之非銀行機構', 4),
	(37, 16, 4, '不動產業', 4),
	(38, 17, 4, '國際貿易業', 4),
	(39, 1, 5, '公證人', 4),
	(40, 2, 5, '會計師', 4),
	(41, 3, 5, '律師', 4),
	(42, 4, 5, '地政士', 4),
	(43, 5, 5, '記帳士', 4),
	(44, 6, 5, '報稅代理人', 4),
	(45, 1, 6, '比對身分證件後，與已婉拒黑名單客戶有親戚關係', 12),
	(46, 2, 6, '與已婉拒黑名單客戶相同居住地址或註冊地址', 12),
	(47, 3, 6, '基本資料填寫錯誤達3次(含)以上', 8),
	(48, 4, 6, '65歲以上', 6),
	(49, 5, 6, '與其他客戶自拍照、背景、字跡相似或相同但無合理原因者', 6),
	(50, 6, 6, '不同使用者間，使用者名稱/E-mail雷同，但無合理原因者', 4),
	(51, 7, 6, '於室外或公共場所進行自拍或證件照拍攝', 4),
	(52, 8, 6, 'KYC文件經過壓縮', 4),
	(53, 9, 6, '地址證明提供國民年金徵收信件、法院刑事公文書等', 2),
	(54, 10, 6, 'KYC文件拍攝裝置與連線裝置不合，且無合理原因者', 2),
	(55, 11, 6, '22歲以下，年收入100萬以上', 2),
	(56, 12, 6, '惡意註冊亂填寫資料和上傳照片或是上傳過與KYC無關的相片', 2),
	(57, 13, 6, '重複上傳無效或相同的文件達兩次或是補件三次不成功', 2),
	(58, 14, 6, '非台灣常見email網域(常見email網域：gmail,yahoo,hotmail)', 2),
	(59, 1, 7, '核資錯誤-姓名、身分證字號、生日、生肖', 12),
	(60, 2, 7, '核資錯誤-戶籍地址、綁定銀行、使用者名稱', 8),
	(61, 3, 7, '照會時聽到有他人提示聲音', 8),
	(62, 4, 7, '核資流暢但照本宣科，異常多提供個人資訊（例如：地址鄰里、連續提供非需求資料）', 2),
	(63, 5, 7, '詢問資金來源時不願意提供或表達不願意透漏者', 2),
	(64, 6, 7, '回答內容與事實不符、回答內容前後矛盾或找理由搪塞解釋', 2),
	(65, 7, 7, '照會時聽到翻找資料聲音，且無合理原因者', 2),
	(66, 8, 7, '不願提供職業資訊、含糊其辭或對職業狀況不清楚者', 2),
	(67, 9, 7, '無投資經驗，且對投資標的不清楚', 2),
	(68, 10, 7, '跟隨朋友/家人投資，且對投資標的不清楚', 2),
	(69, 11, 7, '客戶資金來源來自於朋友，且無法進一步說明朋友的資金來源', 2),
	(70, 12, 7, '表示需要準備照會內容或是找理由晚點照會', 2),
	(71, 13, 7, '說話結巴、回答不出來、不願意回答、刻意跳過問題或回答吞吐、突然咬字模糊、變小聲、惱羞成怒等', 2),
	(72, 1, 8, '美國', 12),
	(73, 2, 8, '中國大陸', 12),
	(74, 3, 8, '日本', 12),
	(75, 4, 8, '韓國', 12),
	(76, 5, 8, '德國', 12),
	(77, 6, 8, '荷蘭', 12),
	(78, 7, 8, '比利時', 12),
	(79, 8, 8, '盧森堡', 12),
	(80, 9, 8, '法國', 12),
	(81, 10, 8, '義大利', 12),
	(82, 11, 8, '丹麥', 12),
	(83, 12, 8, '愛爾蘭', 12),
	(84, 13, 8, '希臘', 12),
	(85, 14, 8, '西班牙', 12),
	(86, 15, 8, '葡萄牙', 12),
	(87, 16, 8, '瑞典', 12),
	(88, 17, 8, '芬蘭', 12),
	(89, 18, 8, '奧地利', 12),
	(90, 19, 8, '賽普勒斯', 12),
	(91, 20, 8, '愛沙尼亞', 12),
	(92, 21, 8, '拉脫維亞', 12),
	(93, 22, 8, '立陶宛', 12),
	(94, 23, 8, '波蘭', 12),
	(95, 24, 8, '捷克', 12),
	(96, 25, 8, '斯洛伐克', 12),
	(97, 26, 8, '斯洛維尼亞', 12),
	(98, 27, 8, '匈牙利', 12),
	(99, 28, 8, '馬爾他', 12),
	(100, 29, 8, '羅馬尼亞', 12),
	(101, 30, 8, '保加利亞', 12),
	(102, 31, 8, '克羅埃西亞', 12),
	(103, 1, 9, '北韓', 12),
	(104, 2, 9, '伊朗', 12),
	(105, 3, 9, '阿爾巴尼亞', 8),
	(106, 4, 9, '巴貝多', 8),
	(107, 5, 9, '波札那', 8),
	(108, 6, 9, '布吉納法索', 8),
	(109, 7, 9, '柬埔寨', 8),
	(110, 8, 9, '開曼群島', 8),
	(111, 9, 9, '海地', 8),
	(112, 10, 9, '牙買加', 8),
	(113, 11, 9, '模里西斯', 8),
	(114, 12, 9, '摩洛哥', 8),
	(115, 13, 9, '緬甸', 8),
	(116, 14, 9, '尼加拉瓜', 8),
	(117, 15, 9, '巴基斯坦', 8),
	(118, 16, 9, '巴拿馬', 8),
	(119, 17, 9, '菲律賓', 8),
	(120, 18, 9, '塞內加爾', 8),
	(121, 19, 9, '南蘇丹', 8),
	(122, 20, 9, '敘利亞', 8),
	(123, 21, 9, '烏干達', 8),
	(124, 22, 9, '葉門', 8),
	(125, 23, 9, '辛巴威', 4),
	(126, 24, 9, '香港', 4),
	(127, 25, 9, '澳門', 4),
	(128, 26, 9, '越南', 4),
	(129, 27, 9, '印尼', 4),
	(130, 28, 9, '馬來西亞', 4),
	(131, 29, 9, '英屬維京群島', 4),
	(132, 30, 9, '薩摩亞', 4),
	(133, 31, 9, '貝里斯', 4),
	(134, 32, 9, '塞席爾', 4),
	(135, 33, 9, '杜拜', 4),
	(136, 34, 9, '加拿大', 4),
	(137, 35, 9, '澳洲', 4),
	(138, 36, 9, '泰國', 4),
	(139, 1, 10, '客戶具有雙重國籍 ', 2),
	(140, 1, 11, '客戶僅具有外國籍', 2),
	(141, 1, 12, '得使用台幣轉入與轉出', 2),
	(142, 1, 13, '使用信用卡買入虛擬通貨', 2),
	(143, 2, 13, '使用信用卡賣出虛擬通貨', 2),
	(144, 1, 14, '台幣、虛擬通貨累積金額超過USD$1,000,000元', 2),
	(145, 1, 15, '曾被銀行通報或圈存者', 12),
	(146, 2, 15, '與具禁止交易風險因素客戶於一日內，使用相同IP位址或相同GPS位置', 12),
	(147, 3, 15, '與具禁止交易風險因素客戶間，有2個以上IP位址相同', 12),
	(148, 4, 15, '轉出至具禁止交易風險因素客戶使用之相同錢包地址', 12),
	(149, 5, 15, '曾與黑名單客戶使用相同IP位址', 6),
	(150, 1, 16, '註冊或經通知補件者時於一小時內，與其他客戶使用相同IP位址', 8),
	(151, 2, 16, '使用相同IP位址，且姓氏、行業別不同者', 8),
	(152, 1, 17, '申報經3次者', 12),
	(153, 2, 17, '申報經1次者', 8);
/*!40000 ALTER TABLE `kyc_reason` ENABLE KEYS */;

-- 傾印  資料表 kyc_demo.kyc_reason_subtype 結構
CREATE TABLE IF NOT EXISTS `kyc_reason_subtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '編號',
  `code` varchar(32) NOT NULL COMMENT '代號',
  `description` varchar(64) NOT NULL COMMENT '說明',
  `main_type` int(11) NOT NULL COMMENT '主類別',
  PRIMARY KEY (`id`),
  KEY `main_type` (`main_type`),
  CONSTRAINT `kyc_reason_subtype_ibfk_1` FOREIGN KEY (`main_type`) REFERENCES `kyc_reason_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- 正在傾印表格  kyc_demo.kyc_reason_subtype 的資料：~17 rows (近似值)
/*!40000 ALTER TABLE `kyc_reason_subtype` DISABLE KEYS */;
INSERT INTO `kyc_reason_subtype` (`id`, `code`, `description`, `main_type`) VALUES
	(1, 'a', '姓名檢核', 1),
	(2, 'b', '客戶曾經因特定罪名，經起訴者', 1),
	(3, 'c', '法定文件與註冊異常', 1),
	(4, 'd', '高風險行業別', 1),
	(5, 'e', '高風險職業', 1),
	(6, 'f', '文件類異常', 1),
	(7, 'g', '電訪類異常', 1),
	(8, 'a', '法遵成本過高或禁止虛擬通貨國家或地區', 2),
	(9, 'b', '高風險國家或地區', 2),
	(10, 'c', '雙重國籍', 2),
	(11, 'c', '外國籍', 2),
	(12, 'a', '綁定銀行帳號以進行交易預備額之轉入與提領', 3),
	(13, 'b', '信用卡買賣虛擬通貨', 3),
	(14, 'c', '使用者服務累積額度', 3),
	(15, 'a', '黑名單客戶或相關者', 4),
	(16, 'b', '人頭戶嫌疑', 4),
	(17, 'c', '交易經多次申報者', 4);
/*!40000 ALTER TABLE `kyc_reason_subtype` ENABLE KEYS */;

-- 傾印  資料表 kyc_demo.kyc_reason_type 結構
CREATE TABLE IF NOT EXISTS `kyc_reason_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '編號',
  `code` varchar(32) NOT NULL COMMENT '代號',
  `description` varchar(64) NOT NULL COMMENT '說明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 正在傾印表格  kyc_demo.kyc_reason_type 的資料：~4 rows (近似值)
/*!40000 ALTER TABLE `kyc_reason_type` DISABLE KEYS */;
INSERT INTO `kyc_reason_type` (`id`, `code`, `description`) VALUES
	(1, 'A', '客戶因素'),
	(2, 'B', '國家或地區因素'),
	(3, 'C', '產品及服務因素'),
	(4, 'D', '交易或支付管道因素');
/*!40000 ALTER TABLE `kyc_reason_type` ENABLE KEYS */;

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

-- 正在傾印表格  kyc_demo.member 的資料：~2 rows (近似值)
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` (`id`, `member_id`, `username`, `password`, `salt`, `real_name`, `identity_code`, `register_at`, `status`) VALUES
	(3, 'M05YZQY9F', 'apple037037@gmail.com', '$2a$10$xIYMW4QzDojDcHTg/rQ4hOGhITpc9K18MlPOIQORIIX9BDFBJvrFK', 'u99pi99831kba6cg80py', NULL, '123', '2022-05-11 07:19:31', 'ENABLE'),
	(4, 'M06YZQY9F', 'apple037038@gmail.com', '$2a$10$xIYMW4QzDojDcHTg/rQ4hOGhITpc9K18MlPOIQORIIX9BDFBJvrFK', 'u99pi99831kba6cg80py', NULL, '321', '2022-05-11 07:19:31', 'NORMAL');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;

-- 傾印  資料表 kyc_demo.util_image 結構
CREATE TABLE IF NOT EXISTS `util_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '圖片id',
  `url` varchar(255) NOT NULL COMMENT '圖片url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在傾印表格  kyc_demo.util_image 的資料：~0 rows (近似值)
/*!40000 ALTER TABLE `util_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `util_image` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
