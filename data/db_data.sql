-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- 主機： 172.17.0.2:3306
-- 產生時間： 2022 年 04 月 12 日 08:12
-- 伺服器版本： 10.5.7-MariaDB-1:10.5.7+maria~focal
-- PHP 版本： 7.4.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫: `nft_ex`
--

--
-- 傾印資料表的資料 `util_chain`
--

INSERT INTO `util_chain` (`id`, `name`, `main_unit`, `status`, `cold_wallet`, `system_wallet`) VALUES
('TRON', '波場', 'TRX', 'ENABLE', 'TJKrdQrc3zsxuZhBFm51cKXvWELABwz6rk', '');

--
-- 傾印資料表的資料 `util_chain_coin`
--

INSERT INTO `util_chain_coin` (`id`, `coin_id`, `chain_id`, `status`, `w_rate`, `w_fee`) VALUES
(1, 'USDT', 'TRON', 'ENABLE', '1.000', '0.00');

--
-- 傾印資料表的資料 `util_coin`
--

INSERT INTO `util_coin` (`id`, `unit`, `status`, `sort`) VALUES
('USDT', 'USDT', 'ENABLE', 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
