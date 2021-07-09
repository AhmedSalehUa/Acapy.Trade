-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 10, 2021 at 12:12 AM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `acapytrade`
--
CREATE DATABASE IF NOT EXISTS `acapytrade` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `acapytrade`;

-- --------------------------------------------------------

--
-- Table structure for table `acc_accounts`
--

CREATE TABLE `acc_accounts` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL,
  `acc_num` varchar(700) DEFAULT NULL,
  `bank` varchar(700) DEFAULT NULL,
  `credite` varchar(700) DEFAULT NULL,
  `type` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `acc_accounts`
--

TRUNCATE TABLE `acc_accounts`;
-- --------------------------------------------------------

--
-- Table structure for table `acc_expenses`
--

CREATE TABLE `acc_expenses` (
  `id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `date` date NOT NULL,
  `acc_id` int(11) NOT NULL,
  `cat_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `acc_expenses`
--

TRUNCATE TABLE `acc_expenses`;
-- --------------------------------------------------------

--
-- Table structure for table `acc_expenses_category`
--

CREATE TABLE `acc_expenses_category` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `acc_expenses_category`
--

TRUNCATE TABLE `acc_expenses_category`;
-- --------------------------------------------------------

--
-- Table structure for table `acc_transactions`
--

CREATE TABLE `acc_transactions` (
  `id` int(11) NOT NULL,
  `acc_from` int(11) NOT NULL,
  `acc_to` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `acc_transactions`
--

TRUNCATE TABLE `acc_transactions`;
-- --------------------------------------------------------

--
-- Table structure for table `acc_yields`
--

CREATE TABLE `acc_yields` (
  `id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `date` date NOT NULL,
  `acc_id` int(11) NOT NULL,
  `acc_cat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `acc_yields`
--

TRUNCATE TABLE `acc_yields`;
-- --------------------------------------------------------

--
-- Table structure for table `acc_yields_category`
--

CREATE TABLE `acc_yields_category` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `acc_yields_category`
--

TRUNCATE TABLE `acc_yields_category`;
-- --------------------------------------------------------

--
-- Table structure for table `cli_clients`
--

CREATE TABLE `cli_clients` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL,
  `organization` varchar(700) NOT NULL,
  `location` varchar(700) DEFAULT NULL,
  `account_id` varchar(700) DEFAULT NULL,
  `email` varchar(700) DEFAULT NULL,
  `tele1` varchar(700) DEFAULT NULL,
  `tele2` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `cli_clients`
--

TRUNCATE TABLE `cli_clients`;
-- --------------------------------------------------------

--
-- Table structure for table `cli_client_account`
--

CREATE TABLE `cli_client_account` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `source_id` int(11) NOT NULL,
  `source_type` varchar(700) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `cli_client_account`
--

TRUNCATE TABLE `cli_client_account`;
-- --------------------------------------------------------

--
-- Table structure for table `cli_client_pays`
--

CREATE TABLE `cli_client_pays` (
  `id` int(11) NOT NULL,
  `client_acc_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `date_of_doc` date NOT NULL,
  `date_of_cash` date DEFAULT NULL,
  `pay_type` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `cli_client_pays`
--

TRUNCATE TABLE `cli_client_pays`;
-- --------------------------------------------------------

--
-- Table structure for table `cli_contracts`
--

CREATE TABLE `cli_contracts` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `date_from` date NOT NULL,
  `date_to` date DEFAULT NULL,
  `num_of_visits` int(11) NOT NULL,
  `cost` int(11) NOT NULL,
  `due_after` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `cli_contracts`
--

TRUNCATE TABLE `cli_contracts`;
-- --------------------------------------------------------

--
-- Table structure for table `cli_contract_visits`
--

CREATE TABLE `cli_contract_visits` (
  `id` int(11) NOT NULL,
  `contract_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `report` varchar(1400) DEFAULT NULL,
  `doc` longblob,
  `doc_ext` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `cli_contract_visits`
--

TRUNCATE TABLE `cli_contract_visits`;
-- --------------------------------------------------------

--
-- Table structure for table `cli_maintaince`
--

CREATE TABLE `cli_maintaince` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `date` int(11) NOT NULL,
  `problem` varchar(700) DEFAULT NULL,
  `cost` varchar(700) DEFAULT NULL,
  `pay_type` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `cli_maintaince`
--

TRUNCATE TABLE `cli_maintaince`;
-- --------------------------------------------------------

--
-- Table structure for table `cli_maintaince_details`
--

CREATE TABLE `cli_maintaince_details` (
  `id` int(11) NOT NULL,
  `maintaince_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `cost` varchar(700) NOT NULL,
  `total_cost` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `cli_maintaince_details`
--

TRUNCATE TABLE `cli_maintaince_details`;
-- --------------------------------------------------------

--
-- Table structure for table `cli_operation`
--

CREATE TABLE `cli_operation` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `sales_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `total_cost` varchar(700) NOT NULL,
  `pay_type` varchar(700) NOT NULL,
  `doc` longblob,
  `doc_ext` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `cli_operation`
--

TRUNCATE TABLE `cli_operation`;
-- --------------------------------------------------------

--
-- Table structure for table `cli_operation_details`
--

CREATE TABLE `cli_operation_details` (
  `id` int(11) NOT NULL,
  `operation_id` int(11) NOT NULL,
  `store_product_id` int(11) NOT NULL,
  `cost` varchar(700) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `total_cost` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `cli_operation_details`
--

TRUNCATE TABLE `cli_operation_details`;
-- --------------------------------------------------------

--
-- Table structure for table `cli_operation_members`
--

CREATE TABLE `cli_operation_members` (
  `id` int(11) NOT NULL,
  `operation_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `cli_operation_members`
--

TRUNCATE TABLE `cli_operation_members`;
-- --------------------------------------------------------

--
-- Table structure for table `mem_acapy_members`
--

CREATE TABLE `mem_acapy_members` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL,
  `acc_num` varchar(700) DEFAULT NULL,
  `app_token` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `mem_acapy_members`
--

TRUNCATE TABLE `mem_acapy_members`;
-- --------------------------------------------------------

--
-- Table structure for table `mem_member_daily_cost`
--

CREATE TABLE `mem_member_daily_cost` (
  `id` int(11) NOT NULL,
  `destination` varchar(700) NOT NULL,
  `cost` varchar(700) NOT NULL,
  `date` date NOT NULL,
  `statue` varchar(700) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `mem_member_daily_cost`
--

TRUNCATE TABLE `mem_member_daily_cost`;
-- --------------------------------------------------------

--
-- Table structure for table `mem_member_daily_cost_details`
--

CREATE TABLE `mem_member_daily_cost_details` (
  `id` int(11) NOT NULL,
  `daily_cost_id` int(11) NOT NULL,
  `product` varchar(700) NOT NULL,
  `cost` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `mem_member_daily_cost_details`
--

TRUNCATE TABLE `mem_member_daily_cost_details`;
-- --------------------------------------------------------

--
-- Table structure for table `mem_member_orders`
--

CREATE TABLE `mem_member_orders` (
  `id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `app_token` varchar(700) NOT NULL,
  `place` varchar(700) DEFAULT NULL,
  `location` varchar(700) DEFAULT NULL,
  `visit_type` varchar(700) DEFAULT NULL,
  `amount_to_collect` varchar(700) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `matters` varchar(700) DEFAULT NULL,
  `note` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `mem_member_orders`
--

TRUNCATE TABLE `mem_member_orders`;
-- --------------------------------------------------------

--
-- Table structure for table `mem_member_orders_progress`
--

CREATE TABLE `mem_member_orders_progress` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `progress` varchar(700) NOT NULL,
  `statue` varchar(700) NOT NULL DEFAULT 'false'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `mem_member_orders_progress`
--

TRUNCATE TABLE `mem_member_orders_progress`;
-- --------------------------------------------------------

--
-- Table structure for table `mem_member_reward`
--

CREATE TABLE `mem_member_reward` (
  `id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `operation_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `date` date NOT NULL,
  `account_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `mem_member_reward`
--

TRUNCATE TABLE `mem_member_reward`;
-- --------------------------------------------------------

--
-- Table structure for table `mem_member_solfa`
--

CREATE TABLE `mem_member_solfa` (
  `id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `date` date NOT NULL,
  `account_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `mem_member_solfa`
--

TRUNCATE TABLE `mem_member_solfa`;
-- --------------------------------------------------------

--
-- Table structure for table `mem_member_transactions`
--

CREATE TABLE `mem_member_transactions` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `total_cost` varchar(700) NOT NULL,
  `statue` varchar(700) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `mem_member_transactions`
--

TRUNCATE TABLE `mem_member_transactions`;
-- --------------------------------------------------------

--
-- Table structure for table `mem_member_transactions_details`
--

CREATE TABLE `mem_member_transactions_details` (
  `id` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL,
  `place_from` varchar(700) NOT NULL,
  `place_to` varchar(700) NOT NULL,
  `amount` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `mem_member_transactions_details`
--

TRUNCATE TABLE `mem_member_transactions_details`;
-- --------------------------------------------------------

--
-- Table structure for table `priviliges_name`
--

CREATE TABLE `priviliges_name` (
  `name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `priviliges_name`
--

TRUNCATE TABLE `priviliges_name`;
--
-- Dumping data for table `priviliges_name`
--

INSERT INTO `priviliges_name` (`name`) VALUES
('MainData');

-- --------------------------------------------------------

--
-- Table structure for table `sl_calender`
--

CREATE TABLE `sl_calender` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `sales_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` time DEFAULT NULL,
  `details` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `sl_calender`
--

TRUNCATE TABLE `sl_calender`;
-- --------------------------------------------------------

--
-- Table structure for table `sl_calls`
--

CREATE TABLE `sl_calls` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` time DEFAULT NULL,
  `details` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `sl_calls`
--

TRUNCATE TABLE `sl_calls`;
-- --------------------------------------------------------

--
-- Table structure for table `sl_client`
--

CREATE TABLE `sl_client` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL,
  `organization` varchar(700) DEFAULT NULL,
  `relation` varchar(700) DEFAULT NULL,
  `location` varchar(700) DEFAULT NULL,
  `email` varchar(700) DEFAULT NULL,
  `tele1` varchar(700) DEFAULT NULL,
  `tele2` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `sl_client`
--

TRUNCATE TABLE `sl_client`;
-- --------------------------------------------------------

--
-- Table structure for table `sl_offers`
--

CREATE TABLE `sl_offers` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `total_cost` varchar(700) DEFAULT NULL,
  `doc` longblob NOT NULL,
  `doc_ext` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `sl_offers`
--

TRUNCATE TABLE `sl_offers`;
-- --------------------------------------------------------

--
-- Table structure for table `sl_offers_condition`
--

CREATE TABLE `sl_offers_condition` (
  `id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  `attribute` varchar(700) NOT NULL,
  `value` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `sl_offers_condition`
--

TRUNCATE TABLE `sl_offers_condition`;
-- --------------------------------------------------------

--
-- Table structure for table `sl_offers_details`
--

CREATE TABLE `sl_offers_details` (
  `id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `cost` varchar(700) DEFAULT NULL,
  `amount` varchar(700) DEFAULT NULL,
  `total_cost` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `sl_offers_details`
--

TRUNCATE TABLE `sl_offers_details`;
-- --------------------------------------------------------

--
-- Table structure for table `sl_sales_members`
--

CREATE TABLE `sl_sales_members` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL,
  `num_of_success` varchar(700) DEFAULT '0',
  `app_token` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `sl_sales_members`
--

TRUNCATE TABLE `sl_sales_members`;
-- --------------------------------------------------------

--
-- Table structure for table `sl_succeded_offers`
--

CREATE TABLE `sl_succeded_offers` (
  `id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  `sales_id` int(11) NOT NULL,
  `date_of_operation` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `sl_succeded_offers`
--

TRUNCATE TABLE `sl_succeded_offers`;
-- --------------------------------------------------------

--
-- Table structure for table `static_values`
--

CREATE TABLE `static_values` (
  `attribute` varchar(250) NOT NULL,
  `value` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `static_values`
--

TRUNCATE TABLE `static_values`;
--
-- Dumping data for table `static_values`
--

INSERT INTO `static_values` (`attribute`, `value`) VALUES
('DRUGS_ACCOUNT_ID', '3'),
('DRUGS_BRANCH_ACCOUNT_ID', '4'),
('MAIN_ACC_ID', '1'),
('RECEPTION_ACC_ID', '2');

-- --------------------------------------------------------

--
-- Table structure for table `st_enterance_per`
--

CREATE TABLE `st_enterance_per` (
  `id` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_enterance_per`
--

TRUNCATE TABLE `st_enterance_per`;
-- --------------------------------------------------------

--
-- Table structure for table `st_exit_per`
--

CREATE TABLE `st_exit_per` (
  `id` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_exit_per`
--

TRUNCATE TABLE `st_exit_per`;
-- --------------------------------------------------------

--
-- Table structure for table `st_exit_per_details`
--

CREATE TABLE `st_exit_per_details` (
  `exit_id` int(11) NOT NULL,
  `store_product_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_exit_per_details`
--

TRUNCATE TABLE `st_exit_per_details`;
-- --------------------------------------------------------

--
-- Table structure for table `st_invoices`
--

CREATE TABLE `st_invoices` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `provider_id` int(11) NOT NULL,
  `cost` varchar(700) NOT NULL,
  `discount` varchar(700) DEFAULT NULL,
  `total_cost` varchar(700) NOT NULL,
  `pay_type` varchar(700) DEFAULT NULL,
  `doc` varchar(700) DEFAULT NULL,
  `doc_ext` varchar(700) DEFAULT NULL,
  `account_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_invoices`
--

TRUNCATE TABLE `st_invoices`;
-- --------------------------------------------------------

--
-- Table structure for table `st_invoices_details`
--

CREATE TABLE `st_invoices_details` (
  `id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `cost` varchar(700) NOT NULL,
  `total_cost` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_invoices_details`
--

TRUNCATE TABLE `st_invoices_details`;
-- --------------------------------------------------------

--
-- Table structure for table `st_products`
--

CREATE TABLE `st_products` (
  `id` int(11) NOT NULL,
  `cat_id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL,
  `model` varchar(700) DEFAULT NULL,
  `details` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_products`
--

TRUNCATE TABLE `st_products`;
-- --------------------------------------------------------

--
-- Table structure for table `st_products_category`
--

CREATE TABLE `st_products_category` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_products_category`
--

TRUNCATE TABLE `st_products_category`;
-- --------------------------------------------------------

--
-- Table structure for table `st_provider`
--

CREATE TABLE `st_provider` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL,
  `adress` varchar(700) DEFAULT NULL,
  `credite` varchar(700) DEFAULT NULL,
  `account_number` int(11) DEFAULT NULL,
  `cat_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_provider`
--

TRUNCATE TABLE `st_provider`;
-- --------------------------------------------------------

--
-- Table structure for table `st_provider_accounts`
--

CREATE TABLE `st_provider_accounts` (
  `id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `amount` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_provider_accounts`
--

TRUNCATE TABLE `st_provider_accounts`;
-- --------------------------------------------------------

--
-- Table structure for table `st_provider_accounts_pays`
--

CREATE TABLE `st_provider_accounts_pays` (
  `id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `provider_acc_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `date` date NOT NULL,
  `member_id` int(11) DEFAULT NULL,
  `account_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_provider_accounts_pays`
--

TRUNCATE TABLE `st_provider_accounts_pays`;
-- --------------------------------------------------------

--
-- Table structure for table `st_returned_per`
--

CREATE TABLE `st_returned_per` (
  `id` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `source_id` varchar(700) NOT NULL,
  `source_type` varchar(700) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_returned_per`
--

TRUNCATE TABLE `st_returned_per`;
-- --------------------------------------------------------

--
-- Table structure for table `st_returned_per_details`
--

CREATE TABLE `st_returned_per_details` (
  `returned_id` int(11) NOT NULL,
  `store_product_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_returned_per_details`
--

TRUNCATE TABLE `st_returned_per_details`;
-- --------------------------------------------------------

--
-- Table structure for table `st_stores`
--

CREATE TABLE `st_stores` (
  `id` int(11) NOT NULL,
  `name` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_stores`
--

TRUNCATE TABLE `st_stores`;
-- --------------------------------------------------------

--
-- Table structure for table `st_store_products`
--

CREATE TABLE `st_store_products` (
  `id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `cost_of_buy` varchar(700) NOT NULL,
  `cost_for_sell` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `st_store_products`
--

TRUNCATE TABLE `st_store_products`;
-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `user_name` varchar(700) NOT NULL,
  `password` varchar(700) NOT NULL,
  `role` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `users`
--

TRUNCATE TABLE `users`;
--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `user_name`, `password`, `role`) VALUES
(1, 'a', 'a', 'super_admin'),
(2, 'saaa', 'a', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `users_permissions`
--

CREATE TABLE `users_permissions` (
  `user_id` int(11) NOT NULL,
  `privileges` varchar(700) NOT NULL,
  `value` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Truncate table before insert `users_permissions`
--

TRUNCATE TABLE `users_permissions`;
--
-- Indexes for dumped tables
--

--
-- Indexes for table `acc_accounts`
--
ALTER TABLE `acc_accounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `acc_expenses`
--
ALTER TABLE `acc_expenses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `acc_expenses_category`
--
ALTER TABLE `acc_expenses_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `acc_transactions`
--
ALTER TABLE `acc_transactions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `acc_yields`
--
ALTER TABLE `acc_yields`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `acc_yields_category`
--
ALTER TABLE `acc_yields_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cli_clients`
--
ALTER TABLE `cli_clients`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cli_client_account`
--
ALTER TABLE `cli_client_account`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cli_client_pays`
--
ALTER TABLE `cli_client_pays`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cli_contracts`
--
ALTER TABLE `cli_contracts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cli_contract_visits`
--
ALTER TABLE `cli_contract_visits`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cli_maintaince`
--
ALTER TABLE `cli_maintaince`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cli_maintaince_details`
--
ALTER TABLE `cli_maintaince_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cli_operation`
--
ALTER TABLE `cli_operation`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cli_operation_details`
--
ALTER TABLE `cli_operation_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cli_operation_members`
--
ALTER TABLE `cli_operation_members`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mem_acapy_members`
--
ALTER TABLE `mem_acapy_members`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mem_member_daily_cost`
--
ALTER TABLE `mem_member_daily_cost`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mem_member_daily_cost_details`
--
ALTER TABLE `mem_member_daily_cost_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mem_member_orders`
--
ALTER TABLE `mem_member_orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mem_member_orders_progress`
--
ALTER TABLE `mem_member_orders_progress`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mem_member_reward`
--
ALTER TABLE `mem_member_reward`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mem_member_solfa`
--
ALTER TABLE `mem_member_solfa`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mem_member_transactions`
--
ALTER TABLE `mem_member_transactions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mem_member_transactions_details`
--
ALTER TABLE `mem_member_transactions_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `priviliges_name`
--
ALTER TABLE `priviliges_name`
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `sl_calender`
--
ALTER TABLE `sl_calender`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sl_calls`
--
ALTER TABLE `sl_calls`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sl_client`
--
ALTER TABLE `sl_client`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sl_offers`
--
ALTER TABLE `sl_offers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sl_offers_condition`
--
ALTER TABLE `sl_offers_condition`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sl_offers_details`
--
ALTER TABLE `sl_offers_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sl_sales_members`
--
ALTER TABLE `sl_sales_members`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sl_succeded_offers`
--
ALTER TABLE `sl_succeded_offers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `static_values`
--
ALTER TABLE `static_values`
  ADD UNIQUE KEY `attribute` (`attribute`);

--
-- Indexes for table `st_enterance_per`
--
ALTER TABLE `st_enterance_per`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_exit_per`
--
ALTER TABLE `st_exit_per`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_invoices`
--
ALTER TABLE `st_invoices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_invoices_details`
--
ALTER TABLE `st_invoices_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_products`
--
ALTER TABLE `st_products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_products_category`
--
ALTER TABLE `st_products_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_provider`
--
ALTER TABLE `st_provider`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_provider_accounts`
--
ALTER TABLE `st_provider_accounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_provider_accounts_pays`
--
ALTER TABLE `st_provider_accounts_pays`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_returned_per`
--
ALTER TABLE `st_returned_per`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_stores`
--
ALTER TABLE `st_stores`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_store_products`
--
ALTER TABLE `st_store_products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users_permissions`
--
ALTER TABLE `users_permissions`
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `acc_accounts`
--
ALTER TABLE `acc_accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `acc_expenses`
--
ALTER TABLE `acc_expenses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `acc_expenses_category`
--
ALTER TABLE `acc_expenses_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `acc_transactions`
--
ALTER TABLE `acc_transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `acc_yields`
--
ALTER TABLE `acc_yields`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `acc_yields_category`
--
ALTER TABLE `acc_yields_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_clients`
--
ALTER TABLE `cli_clients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_client_account`
--
ALTER TABLE `cli_client_account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_client_pays`
--
ALTER TABLE `cli_client_pays`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_contracts`
--
ALTER TABLE `cli_contracts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_contract_visits`
--
ALTER TABLE `cli_contract_visits`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_maintaince`
--
ALTER TABLE `cli_maintaince`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_maintaince_details`
--
ALTER TABLE `cli_maintaince_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_operation`
--
ALTER TABLE `cli_operation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_operation_details`
--
ALTER TABLE `cli_operation_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_operation_members`
--
ALTER TABLE `cli_operation_members`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mem_acapy_members`
--
ALTER TABLE `mem_acapy_members`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mem_member_daily_cost`
--
ALTER TABLE `mem_member_daily_cost`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mem_member_daily_cost_details`
--
ALTER TABLE `mem_member_daily_cost_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mem_member_orders`
--
ALTER TABLE `mem_member_orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mem_member_orders_progress`
--
ALTER TABLE `mem_member_orders_progress`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mem_member_reward`
--
ALTER TABLE `mem_member_reward`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mem_member_solfa`
--
ALTER TABLE `mem_member_solfa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mem_member_transactions`
--
ALTER TABLE `mem_member_transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mem_member_transactions_details`
--
ALTER TABLE `mem_member_transactions_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sl_calender`
--
ALTER TABLE `sl_calender`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sl_calls`
--
ALTER TABLE `sl_calls`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sl_client`
--
ALTER TABLE `sl_client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sl_offers`
--
ALTER TABLE `sl_offers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sl_offers_condition`
--
ALTER TABLE `sl_offers_condition`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sl_offers_details`
--
ALTER TABLE `sl_offers_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sl_sales_members`
--
ALTER TABLE `sl_sales_members`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sl_succeded_offers`
--
ALTER TABLE `sl_succeded_offers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_enterance_per`
--
ALTER TABLE `st_enterance_per`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_exit_per`
--
ALTER TABLE `st_exit_per`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_invoices`
--
ALTER TABLE `st_invoices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_invoices_details`
--
ALTER TABLE `st_invoices_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_products`
--
ALTER TABLE `st_products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_products_category`
--
ALTER TABLE `st_products_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_provider`
--
ALTER TABLE `st_provider`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_provider_accounts`
--
ALTER TABLE `st_provider_accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_provider_accounts_pays`
--
ALTER TABLE `st_provider_accounts_pays`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_returned_per`
--
ALTER TABLE `st_returned_per`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_stores`
--
ALTER TABLE `st_stores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_store_products`
--
ALTER TABLE `st_store_products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
