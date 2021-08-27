-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 27, 2021 at 09:34 PM
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
-- Triggers `acc_expenses`
--
DELIMITER $$
CREATE TRIGGER `after_delete_expenses` AFTER DELETE ON `acc_expenses` FOR EACH ROW BEGIN	
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(OLD.amount as UNSIGNED) WHERE id = OLD.acc_id;
		END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_insert_expenses` AFTER INSERT ON `acc_expenses` FOR EACH ROW BEGIN	
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(NEW.amount as UNSIGNED) WHERE id = NEW.acc_id;
		END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_update_expenses` AFTER UPDATE ON `acc_expenses` FOR EACH ROW BEGIN	
            UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(OLD.amount as UNSIGNED) WHERE id = OLD.acc_id;
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(NEW.amount as UNSIGNED) WHERE id = NEW.acc_id;
		END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_expenses_category`
--

CREATE TABLE `acc_expenses_category` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
-- Triggers `acc_transactions`
--
DELIMITER $$
CREATE TRIGGER `after_delete_transaction` AFTER DELETE ON `acc_transactions` FOR EACH ROW BEGIN	
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(OLD.amount as UNSIGNED) WHERE id = OLD.acc_to;
            UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(OLD.amount as UNSIGNED) WHERE id = OLD.acc_from;
		END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_insert_transaction` AFTER INSERT ON `acc_transactions` FOR EACH ROW BEGIN	
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(NEW.amount as UNSIGNED) WHERE id = NEW.acc_to;
            UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(NEW.amount as UNSIGNED) WHERE id = NEW.acc_from;
		END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_update_transaction` AFTER UPDATE ON `acc_transactions` FOR EACH ROW BEGIN	
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(OLD.amount as UNSIGNED) WHERE id = OLD.acc_to;
            UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(OLD.amount as UNSIGNED) WHERE id = OLD.acc_from;
            
            UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(NEW.amount as UNSIGNED) WHERE id = NEW.acc_to;
            UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(NEW.amount as UNSIGNED) WHERE id = NEW.acc_from;
		END
$$
DELIMITER ;

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
-- Triggers `acc_yields`
--
DELIMITER $$
CREATE TRIGGER `after_delete_yields` AFTER DELETE ON `acc_yields` FOR EACH ROW BEGIN	
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(OLD.amount as UNSIGNED) WHERE id = OLD.acc_id;
		END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_insert_yields` AFTER INSERT ON `acc_yields` FOR EACH ROW BEGIN	
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(NEW.amount as UNSIGNED) WHERE id = NEW.acc_id;
		END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_update_yields` AFTER UPDATE ON `acc_yields` FOR EACH ROW BEGIN	
            UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(OLD.amount as UNSIGNED) WHERE id = OLD.acc_id;
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(NEW.amount as UNSIGNED) WHERE id = NEW.acc_id;
		END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_yields_category`
--

CREATE TABLE `acc_yields_category` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- Table structure for table `cli_client_pays`
--

CREATE TABLE `cli_client_pays` (
  `id` int(11) NOT NULL,
  `client_acc_id` int(11) NOT NULL,
  `member_id` int(11) DEFAULT NULL,
  `amount` varchar(700) NOT NULL,
  `date_of_doc` date NOT NULL,
  `date_of_cash` date DEFAULT NULL,
  `pay_type` varchar(700) DEFAULT NULL,
  `acc_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Triggers `cli_client_pays`
--
DELIMITER $$
CREATE TRIGGER `after_delete_clients_pays` AFTER DELETE ON `cli_client_pays` FOR EACH ROW BEGIN 
        	 
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(OLD.amount as UNSIGNED) WHERE id = OLD.acc_id;
 		END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_insert_clients_pays` AFTER INSERT ON `cli_client_pays` FOR EACH ROW BEGIN 
        	 
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(NEW.amount as UNSIGNED) WHERE id = NEW.acc_id;
 		END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_update_clients_pays` AFTER UPDATE ON `cli_client_pays` FOR EACH ROW BEGIN 
        	 
            UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(OLD.amount as UNSIGNED) WHERE id = OLD.acc_id;
        	UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(NEW.amount as UNSIGNED) WHERE id = NEW.acc_id;
 		END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `cli_contracts`
--

CREATE TABLE `cli_contracts` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `date_from` date NOT NULL,
  `date_to` date DEFAULT NULL,
  `num_of_visits` varchar(700) NOT NULL,
  `cost` varchar(700) NOT NULL,
  `due_after` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- Table structure for table `cli_maintaince`
--

CREATE TABLE `cli_maintaince` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `problem` varchar(700) DEFAULT NULL,
  `cost` varchar(700) DEFAULT NULL,
  `pay_type` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Triggers `cli_maintaince`
--
DELIMITER $$
CREATE TRIGGER `after_Update_maintaince` AFTER UPDATE ON `cli_maintaince` FOR EACH ROW BEGIN 
     DELETE from cli_client_account where `client_id` =OLD.client_id AND `source_id` = OLD.id AND  `source_type` ='صيانة' AND  `amount` =OLD.cost AND `date` = 			      	OLD.date ;
     set @cash  := 'كاش'; 
     set @warrenty  = 'فى الضمان'; 
     set @later  = 'بالاجل'; 
     IF(NEW.pay_type =  @later) THEN
    	INSERT INTO cli_client_account ( `client_id`, `source_id`, `source_type`, `amount`, `date`) VALUES (NEW.client_id,New.id,'صيانة',NEW.cost,New.date);
     ELSEIF (NEW.pay_type =  @cash) THEN
      	INSERT INTO cli_client_account ( `client_id`, `source_id`, `source_type`, `amount`, `date`) VALUES (NEW.client_id,New.id,'صيانة',NEW.cost,New.date);
        set @insertedID := (SELECT max(`id`) FROM `cli_client_account` WHERE client_id = NEW.client_id);
         set @AccID := (SELECT `value` FROM `static_values` WHERE `attribute`='MAIN_ACC_ID');
        INSERT INTO `cli_client_pays`(`client_acc_id`, `member_id`, `amount`, `date_of_doc`, `date_of_cash`, `pay_type`,`acc_id`) 
        VALUES (@insertedID,NEW.member_id, NEW.cost,New.date,New.date,@Type,@AccID);
      ELSEIF  (NEW.pay_type =  @warrenty) THEN
      	INSERT INTO cli_client_account ( `client_id`, `source_id`, `source_type`, `amount`, `date`) VALUES (NEW.client_id,New.id,'صيانة',NEW.cost,New.date);
      end if;
     END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_insert_maintaince` AFTER INSERT ON `cli_maintaince` FOR EACH ROW BEGIN 
     set @cash  := 'كاش'; 
     set @warrenty  = 'فى الضمان'; 
     set @later  = 'بالاجل'; 
     IF(NEW.pay_type =  @later) THEN
    	INSERT INTO cli_client_account ( `client_id`, `source_id`, `source_type`, `amount`, `date`) VALUES (NEW.client_id,New.id,'صيانة',NEW.cost,New.date);
     ELSEIF (NEW.pay_type =  @cash) THEN
      	INSERT INTO cli_client_account ( `client_id`, `source_id`, `source_type`, `amount`, `date`) VALUES (NEW.client_id,New.id,'صيانة',NEW.cost,New.date);
        set @insertedID := (SELECT max(`id`) FROM `cli_client_account` WHERE client_id = NEW.client_id);
        set @AccID := (SELECT `value` FROM `static_values` WHERE `attribute`='MAIN_ACC_ID');
        INSERT INTO `cli_client_pays`(`client_acc_id`, `member_id`, `amount`, `date_of_doc`, `date_of_cash`, `pay_type`,`acc_id`) 
        VALUES (@insertedID,NEW.member_id, NEW.cost,New.date,New.date,@Type,@AccID);
      ELSEIF  (NEW.pay_type =  @warrenty) THEN
      	INSERT INTO cli_client_account ( `client_id`, `source_id`, `source_type`, `amount`, `date`) VALUES (NEW.client_id,New.id,'صيانة',NEW.cost,New.date);
      end if;
     END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_Delete_maintaince` BEFORE DELETE ON `cli_maintaince` FOR EACH ROW BEGIN 
     DELETE from cli_client_account where `client_id` =OLD.client_id AND `source_id` = OLD.id AND  `source_type` ='صيانة' AND  `amount` =OLD.cost AND `date` = 			      	OLD.date ;
      
     END
$$
DELIMITER ;

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
-- Triggers `cli_operation`
--
DELIMITER $$
CREATE TRIGGER `after_Update_opertaion` AFTER UPDATE ON `cli_operation` FOR EACH ROW BEGIN 
     DELETE from cli_client_account where `client_id` =OLD.client_id AND `source_id` = OLD.id AND  `source_type` ='عملية' AND  `amount` =OLD.total_cost AND `date` =OLD.date ;
     set @cash  := 'كاش'; 
     set @later  = 'دفعات'; 
     IF(NEW.pay_type =  @later) THEN
    	INSERT INTO cli_client_account ( `client_id`, `source_id`, `source_type`, `amount`, `date`) VALUES (NEW.client_id,New.id,'عملية',NEW.total_cost,New.date);
     ELSEIF (NEW.pay_type =  @cash) THEN
      	INSERT INTO cli_client_account ( `client_id`, `source_id`, `source_type`, `amount`, `date`) VALUES (NEW.client_id,New.id,'عملية',NEW.total_cost,New.date);
        set @insertedID := (SELECT max(`id`) FROM `cli_client_account` WHERE client_id = NEW.client_id);
        set @AccID := (SELECT `value` FROM `static_values` WHERE `attribute`='MAIN_ACC_ID');
        INSERT INTO `cli_client_pays`(`client_acc_id`,   `amount`, `date_of_doc`, `date_of_cash`, `pay_type`,`acc_id`) 
        VALUES (@insertedID, NEW.total_cost,New.date,New.date,@Type,@AccID);
     end if;
     END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_insert_operation` AFTER INSERT ON `cli_operation` FOR EACH ROW BEGIN 
     set @cash  := 'كاش'; 
     set @later  = 'دفعات'; 
     IF(NEW.pay_type =  @later) THEN
    	INSERT INTO cli_client_account ( `client_id`, `source_id`, `source_type`, `amount`, `date`) VALUES (NEW.client_id,New.id,'عملية',NEW.total_cost,New.date);
     ELSEIF (NEW.pay_type =  @cash) THEN
      	INSERT INTO cli_client_account ( `client_id`, `source_id`, `source_type`, `amount`, `date`) VALUES (NEW.client_id,New.id,'عملية',NEW.total_cost,New.date);
        set @insertedID := (SELECT max(`id`) FROM `cli_client_account` WHERE client_id = NEW.client_id);
        set @AccID := (SELECT `value` FROM `static_values` WHERE `attribute`='MAIN_ACC_ID');
        INSERT INTO `cli_client_pays`(`client_acc_id`,   `amount`, `date_of_doc`, `date_of_cash`, `pay_type`,`acc_id`) 
        VALUES (@insertedID, NEW.total_cost,New.date,New.date,@Type,@AccID);
     end if;
     END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_DELETE_opertaion` BEFORE DELETE ON `cli_operation` FOR EACH ROW BEGIN 
     DELETE from cli_client_account where `client_id` =OLD.client_id AND `source_id` = OLD.id AND  `source_type` ='عملية' AND  `amount` =OLD.total_cost AND `date` =OLD.date ;
      
     END
$$
DELIMITER ;

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

-- --------------------------------------------------------

--
-- Table structure for table `cli_operation_members`
--

CREATE TABLE `cli_operation_members` (
  `id` int(11) NOT NULL,
  `operation_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- Table structure for table `mem_member_daily_cost_details`
--

CREATE TABLE `mem_member_daily_cost_details` (
  `id` int(11) NOT NULL,
  `daily_cost_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `cost` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- Table structure for table `priviliges_name`
--

CREATE TABLE `priviliges_name` (
  `name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- Table structure for table `sl_calls`
--

CREATE TABLE `sl_calls` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` time DEFAULT NULL,
  `details` varchar(700) DEFAULT NULL,
  `sales_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `tele2` varchar(700) DEFAULT NULL,
  `sales_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sl_offers`
--

CREATE TABLE `sl_offers` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `cost` varchar(700) NOT NULL,
  `discount` varchar(700) DEFAULT NULL,
  `discount_percent` varchar(700) DEFAULT NULL,
  `total_cost` varchar(700) DEFAULT NULL,
  `doc` longblob,
  `doc_ext` varchar(700) DEFAULT NULL,
  `sales_id` int(11) NOT NULL,
  `notes` varchar(1400) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- Table structure for table `sl_offers_condition_temp`
--

CREATE TABLE `sl_offers_condition_temp` (
  `id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  `attribute` varchar(700) NOT NULL,
  `value` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- Table structure for table `sl_offers_details_temp`
--

CREATE TABLE `sl_offers_details_temp` (
  `id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `cost` varchar(700) DEFAULT NULL,
  `amount` varchar(700) DEFAULT NULL,
  `total_cost` varchar(700) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sl_offers_temp`
--

CREATE TABLE `sl_offers_temp` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `cost` varchar(700) NOT NULL,
  `discount` varchar(700) DEFAULT NULL,
  `discount_percent` varchar(700) DEFAULT NULL,
  `total_cost` varchar(700) DEFAULT NULL,
  `doc` longblob,
  `doc_ext` varchar(700) DEFAULT NULL,
  `sales_id` int(11) NOT NULL,
  `notes` varchar(1400) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- Table structure for table `static_values`
--

CREATE TABLE `static_values` (
  `attribute` varchar(250) NOT NULL,
  `value` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- Table structure for table `st_exit_per_details`
--

CREATE TABLE `st_exit_per_details` (
  `exit_id` int(11) NOT NULL,
  `store_product_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `account_id` int(11) DEFAULT NULL,
  `notes` varchar(1400) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Triggers `st_invoices`
--
DELIMITER $$
CREATE TRIGGER `acc_after_insert` AFTER INSERT ON `st_invoices` FOR EACH ROW BEGIN

INSERT INTO `st_provider_accounts`(`provider_id`, `invoice_id`, `date`, `amount`) VALUES (NEW.provider_id,NEW.id,NEW.date,NEW.total_cost);
 
 
 SET @nnb := 'كاش';
 set @lastID := (SELECT max(id) from st_provider_accounts);
 
   IF(NEW.pay_type =  @nnb) THEN
		 INSERT INTO `st_provider_accounts_pays`(`provider_id`, 				`provider_acc_id`, `amount`, 			`date`, `account_id`) VALUES  	(NEW.provider_id,@lastID,NEW.total_cost,NEW.date,NEW.account_id);
        
	END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `acc_after_update` AFTER UPDATE ON `st_invoices` FOR EACH ROW BEGIN
-- delete ol
DELETE from st_provider_accounts_pays WHERE provider_acc_id IN (SELECT id FROM st_provider_accounts  WHERE invoice_id =OLD.id);
DELETE FROM `st_provider_accounts` WHERE invoice_id =OLD.id;

-- add new
INSERT INTO `st_provider_accounts`(`provider_id`, `invoice_id`, `date`, `amount`) VALUES (NEW.provider_id,NEW.id,NEW.date,NEW.total_cost);
 
 
 SET @nnb := 'كاش';
 set @lastID := (SELECT max(id) from st_provider_accounts);
 
   IF(NEW.pay_type =  @nnb) THEN
		 INSERT INTO `st_provider_accounts_pays`(`provider_id`, 				`provider_acc_id`, `amount`, 			`date`, `account_id`) VALUES  	(NEW.provider_id,@lastID,NEW.total_cost,NEW.date,NEW.account_id);
        
	END IF;

end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `acc_before_delete` BEFORE DELETE ON `st_invoices` FOR EACH ROW BEGIN
DELETE from st_provider_accounts_pays WHERE provider_acc_id IN (SELECT id FROM st_provider_accounts  WHERE invoice_id =OLD.id);
DELETE FROM `st_provider_accounts` WHERE invoice_id =OLD.id;
end
$$
DELIMITER ;

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

-- --------------------------------------------------------

--
-- Table structure for table `st_products_category`
--

CREATE TABLE `st_products_category` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
-- Triggers `st_provider_accounts`
--
DELIMITER $$
CREATE TRIGGER `provider_acc_after_delete` AFTER DELETE ON `st_provider_accounts` FOR EACH ROW DELETE FROM st_provider_accounts_pays WHERE provider_acc_id = OLD.id
$$
DELIMITER ;

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
-- Triggers `st_provider_accounts_pays`
--
DELIMITER $$
CREATE TRIGGER `provider_acc_pay_after_insert` AFTER INSERT ON `st_provider_accounts_pays` FOR EACH ROW UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) - CAST(NEW.amount as UNSIGNED) WHERE id = NEW.account_id
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `provider_acc_pay_before_delete` BEFORE DELETE ON `st_provider_accounts_pays` FOR EACH ROW UPDATE acc_accounts set credite = CAST(credite as UNSIGNED) + CAST(OLD.amount as UNSIGNED) WHERE id = OLD.account_id
$$
DELIMITER ;

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

-- --------------------------------------------------------

--
-- Table structure for table `st_returned_per_details`
--

CREATE TABLE `st_returned_per_details` (
  `returned_id` int(11) NOT NULL,
  `store_product_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `st_stores`
--

CREATE TABLE `st_stores` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `st_store_products`
--

CREATE TABLE `st_store_products` (
  `id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `cost_of_buy` varchar(700) NOT NULL,
  `cost_for_sell` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  ADD PRIMARY KEY (`id`),
  ADD KEY `acc_expenses_ibfk_1` (`acc_id`),
  ADD KEY `acc_expenses_ibfk_2` (`cat_id`);

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
  ADD PRIMARY KEY (`id`),
  ADD KEY `acc_yields_ibfk_1` (`acc_id`);

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
  ADD PRIMARY KEY (`id`),
  ADD KEY `cli_client_account_ibfk_1` (`client_id`);

--
-- Indexes for table `cli_client_pays`
--
ALTER TABLE `cli_client_pays`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cli_client_pays_ibfk_1` (`client_acc_id`),
  ADD KEY `cli_client_pays_ibfk_2` (`member_id`),
  ADD KEY `acc_id` (`acc_id`);

--
-- Indexes for table `cli_contracts`
--
ALTER TABLE `cli_contracts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `cli_contract_visits`
--
ALTER TABLE `cli_contract_visits`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cli_contract_visits_ibfk_1` (`contract_id`),
  ADD KEY `cli_contract_visits_ibfk_2` (`member_id`);

--
-- Indexes for table `cli_maintaince`
--
ALTER TABLE `cli_maintaince`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cli_maintaince_ibfk_1` (`client_id`),
  ADD KEY `cli_maintaince_ibfk_2` (`member_id`);

--
-- Indexes for table `cli_maintaince_details`
--
ALTER TABLE `cli_maintaince_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cli_maintaince_details_ibfk_1` (`maintaince_id`),
  ADD KEY `cli_maintaince_details_ibfk_2` (`product_id`);

--
-- Indexes for table `cli_operation`
--
ALTER TABLE `cli_operation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cli_operation_ibfk_2` (`client_id`),
  ADD KEY `cli_operation_ibfk_3` (`sales_id`);

--
-- Indexes for table `cli_operation_details`
--
ALTER TABLE `cli_operation_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cli_operation_details_ibfk_1` (`operation_id`),
  ADD KEY `cli_operation_details_ibfk_2` (`store_product_id`);

--
-- Indexes for table `cli_operation_members`
--
ALTER TABLE `cli_operation_members`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cli_operation_members_ibfk_1` (`member_id`),
  ADD KEY `cli_operation_members_ibfk_2` (`operation_id`);

--
-- Indexes for table `mem_acapy_members`
--
ALTER TABLE `mem_acapy_members`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mem_member_daily_cost`
--
ALTER TABLE `mem_member_daily_cost`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mem_member_daily_cost_ibfk_1` (`account_id`);

--
-- Indexes for table `mem_member_daily_cost_details`
--
ALTER TABLE `mem_member_daily_cost_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mem_member_daily_cost_details_ibfk_1` (`daily_cost_id`),
  ADD KEY `mem_member_daily_cost_details_ibfk_2` (`product_id`);

--
-- Indexes for table `mem_member_orders`
--
ALTER TABLE `mem_member_orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mem_member_orders_ibfk_1` (`member_id`);

--
-- Indexes for table `mem_member_orders_progress`
--
ALTER TABLE `mem_member_orders_progress`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mem_member_orders_progress_ibfk_1` (`order_id`);

--
-- Indexes for table `mem_member_reward`
--
ALTER TABLE `mem_member_reward`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mem_member_reward_ibfk_1` (`member_id`),
  ADD KEY `mem_member_reward_ibfk_2` (`operation_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Indexes for table `mem_member_solfa`
--
ALTER TABLE `mem_member_solfa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mem_member_solfa_ibfk_1` (`member_id`),
  ADD KEY `mem_member_solfa_ibfk_2` (`account_id`);

--
-- Indexes for table `mem_member_transactions`
--
ALTER TABLE `mem_member_transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mem_member_transactions_ibfk_1` (`order_id`),
  ADD KEY `mem_member_transactions_ibfk_2` (`account_id`);

--
-- Indexes for table `mem_member_transactions_details`
--
ALTER TABLE `mem_member_transactions_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mem_member_transactions_details_ibfk_1` (`transaction_id`);

--
-- Indexes for table `priviliges_name`
--
ALTER TABLE `priviliges_name`
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `sl_calender`
--
ALTER TABLE `sl_calender`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `sales_id` (`sales_id`);

--
-- Indexes for table `sl_calls`
--
ALTER TABLE `sl_calls`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `sales_id` (`sales_id`);

--
-- Indexes for table `sl_client`
--
ALTER TABLE `sl_client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sales_id` (`sales_id`);

--
-- Indexes for table `sl_offers`
--
ALTER TABLE `sl_offers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `sales_id` (`sales_id`);

--
-- Indexes for table `sl_offers_condition`
--
ALTER TABLE `sl_offers_condition`
  ADD PRIMARY KEY (`id`),
  ADD KEY `offer_id` (`offer_id`);

--
-- Indexes for table `sl_offers_condition_temp`
--
ALTER TABLE `sl_offers_condition_temp`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sl_offers_details`
--
ALTER TABLE `sl_offers_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `offer_id` (`offer_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `sl_offers_details_temp`
--
ALTER TABLE `sl_offers_details_temp`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sl_offers_temp`
--
ALTER TABLE `sl_offers_temp`
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
  ADD PRIMARY KEY (`id`),
  ADD KEY `offer_id` (`offer_id`),
  ADD KEY `sales_id` (`sales_id`);

--
-- Indexes for table `static_values`
--
ALTER TABLE `static_values`
  ADD UNIQUE KEY `attribute` (`attribute`);

--
-- Indexes for table `st_enterance_per`
--
ALTER TABLE `st_enterance_per`
  ADD PRIMARY KEY (`id`),
  ADD KEY `store_id` (`store_id`),
  ADD KEY `invoice_id` (`invoice_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `st_exit_per`
--
ALTER TABLE `st_exit_per`
  ADD PRIMARY KEY (`id`),
  ADD KEY `store_id` (`store_id`),
  ADD KEY `member_id` (`member_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `st_exit_per_details`
--
ALTER TABLE `st_exit_per_details`
  ADD KEY `exit_id` (`exit_id`),
  ADD KEY `store_product_id` (`store_product_id`);

--
-- Indexes for table `st_invoices`
--
ALTER TABLE `st_invoices`
  ADD PRIMARY KEY (`id`),
  ADD KEY `provider_id` (`provider_id`);

--
-- Indexes for table `st_invoices_details`
--
ALTER TABLE `st_invoices_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `invoice_id` (`invoice_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `st_products`
--
ALTER TABLE `st_products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cat_id` (`cat_id`);

--
-- Indexes for table `st_products_category`
--
ALTER TABLE `st_products_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_provider`
--
ALTER TABLE `st_provider`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cat_id` (`cat_id`);

--
-- Indexes for table `st_provider_accounts`
--
ALTER TABLE `st_provider_accounts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `provider_id` (`provider_id`),
  ADD KEY `invoice_id` (`invoice_id`);

--
-- Indexes for table `st_provider_accounts_pays`
--
ALTER TABLE `st_provider_accounts_pays`
  ADD PRIMARY KEY (`id`),
  ADD KEY `provider_id` (`provider_id`),
  ADD KEY `provider_acc_id` (`provider_acc_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Indexes for table `st_returned_per`
--
ALTER TABLE `st_returned_per`
  ADD PRIMARY KEY (`id`),
  ADD KEY `store_id` (`store_id`),
  ADD KEY `member_id` (`member_id`);

--
-- Indexes for table `st_returned_per_details`
--
ALTER TABLE `st_returned_per_details`
  ADD KEY `returned_id` (`returned_id`),
  ADD KEY `store_product_id` (`store_product_id`);

--
-- Indexes for table `st_stores`
--
ALTER TABLE `st_stores`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `st_store_products`
--
ALTER TABLE `st_store_products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `store_id` (`store_id`),
  ADD KEY `invoice_id` (`invoice_id`),
  ADD KEY `product_id` (`product_id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `acc_expenses`
--
ALTER TABLE `acc_expenses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `acc_expenses_category`
--
ALTER TABLE `acc_expenses_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `acc_transactions`
--
ALTER TABLE `acc_transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `acc_yields`
--
ALTER TABLE `acc_yields`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `acc_yields_category`
--
ALTER TABLE `acc_yields_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cli_clients`
--
ALTER TABLE `cli_clients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cli_client_account`
--
ALTER TABLE `cli_client_account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `cli_client_pays`
--
ALTER TABLE `cli_client_pays`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `cli_contracts`
--
ALTER TABLE `cli_contracts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cli_contract_visits`
--
ALTER TABLE `cli_contract_visits`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_maintaince`
--
ALTER TABLE `cli_maintaince`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `cli_maintaince_details`
--
ALTER TABLE `cli_maintaince_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_operation`
--
ALTER TABLE `cli_operation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `cli_operation_details`
--
ALTER TABLE `cli_operation_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `cli_operation_members`
--
ALTER TABLE `cli_operation_members`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `mem_acapy_members`
--
ALTER TABLE `mem_acapy_members`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `mem_member_daily_cost`
--
ALTER TABLE `mem_member_daily_cost`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `mem_member_daily_cost_details`
--
ALTER TABLE `mem_member_daily_cost_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `mem_member_orders`
--
ALTER TABLE `mem_member_orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `mem_member_transactions_details`
--
ALTER TABLE `mem_member_transactions_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sl_calender`
--
ALTER TABLE `sl_calender`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `sl_calls`
--
ALTER TABLE `sl_calls`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `sl_client`
--
ALTER TABLE `sl_client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `sl_offers`
--
ALTER TABLE `sl_offers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `sl_offers_condition`
--
ALTER TABLE `sl_offers_condition`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `sl_offers_condition_temp`
--
ALTER TABLE `sl_offers_condition_temp`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `sl_offers_details`
--
ALTER TABLE `sl_offers_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `sl_offers_details_temp`
--
ALTER TABLE `sl_offers_details_temp`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `sl_offers_temp`
--
ALTER TABLE `sl_offers_temp`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `sl_sales_members`
--
ALTER TABLE `sl_sales_members`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `st_invoices_details`
--
ALTER TABLE `st_invoices_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `st_products`
--
ALTER TABLE `st_products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `st_products_category`
--
ALTER TABLE `st_products_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `st_provider`
--
ALTER TABLE `st_provider`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `st_provider_accounts`
--
ALTER TABLE `st_provider_accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `st_provider_accounts_pays`
--
ALTER TABLE `st_provider_accounts_pays`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `st_returned_per`
--
ALTER TABLE `st_returned_per`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `st_stores`
--
ALTER TABLE `st_stores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `st_store_products`
--
ALTER TABLE `st_store_products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `acc_expenses`
--
ALTER TABLE `acc_expenses`
  ADD CONSTRAINT `acc_expenses_ibfk_1` FOREIGN KEY (`acc_id`) REFERENCES `acc_accounts` (`id`),
  ADD CONSTRAINT `acc_expenses_ibfk_2` FOREIGN KEY (`cat_id`) REFERENCES `acc_yields_category` (`id`);

--
-- Constraints for table `acc_yields`
--
ALTER TABLE `acc_yields`
  ADD CONSTRAINT `acc_yields_ibfk_1` FOREIGN KEY (`acc_id`) REFERENCES `acc_accounts` (`id`);

--
-- Constraints for table `cli_client_account`
--
ALTER TABLE `cli_client_account`
  ADD CONSTRAINT `cli_client_account_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `cli_clients` (`id`);

--
-- Constraints for table `cli_client_pays`
--
ALTER TABLE `cli_client_pays`
  ADD CONSTRAINT `cli_client_pays_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `mem_acapy_members` (`id`),
  ADD CONSTRAINT `cli_client_pays_ibfk_3` FOREIGN KEY (`acc_id`) REFERENCES `acc_accounts` (`id`);

--
-- Constraints for table `cli_contracts`
--
ALTER TABLE `cli_contracts`
  ADD CONSTRAINT `cli_contracts_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `cli_clients` (`id`);

--
-- Constraints for table `cli_contract_visits`
--
ALTER TABLE `cli_contract_visits`
  ADD CONSTRAINT `cli_contract_visits_ibfk_1` FOREIGN KEY (`contract_id`) REFERENCES `cli_contracts` (`id`),
  ADD CONSTRAINT `cli_contract_visits_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `mem_acapy_members` (`id`);

--
-- Constraints for table `cli_maintaince`
--
ALTER TABLE `cli_maintaince`
  ADD CONSTRAINT `cli_maintaince_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `cli_clients` (`id`),
  ADD CONSTRAINT `cli_maintaince_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `mem_acapy_members` (`id`);

--
-- Constraints for table `cli_maintaince_details`
--
ALTER TABLE `cli_maintaince_details`
  ADD CONSTRAINT `cli_maintaince_details_ibfk_1` FOREIGN KEY (`maintaince_id`) REFERENCES `cli_maintaince` (`id`),
  ADD CONSTRAINT `cli_maintaince_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `st_products` (`id`);

--
-- Constraints for table `cli_operation`
--
ALTER TABLE `cli_operation`
  ADD CONSTRAINT `cli_operation_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `cli_clients` (`id`),
  ADD CONSTRAINT `cli_operation_ibfk_2` FOREIGN KEY (`client_id`) REFERENCES `cli_clients` (`id`),
  ADD CONSTRAINT `cli_operation_ibfk_3` FOREIGN KEY (`sales_id`) REFERENCES `sl_sales_members` (`id`);

--
-- Constraints for table `cli_operation_details`
--
ALTER TABLE `cli_operation_details`
  ADD CONSTRAINT `cli_operation_details_ibfk_1` FOREIGN KEY (`operation_id`) REFERENCES `cli_operation` (`id`);

--
-- Constraints for table `cli_operation_members`
--
ALTER TABLE `cli_operation_members`
  ADD CONSTRAINT `cli_operation_members_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `mem_acapy_members` (`id`),
  ADD CONSTRAINT `cli_operation_members_ibfk_2` FOREIGN KEY (`operation_id`) REFERENCES `cli_operation` (`id`);

--
-- Constraints for table `mem_member_daily_cost`
--
ALTER TABLE `mem_member_daily_cost`
  ADD CONSTRAINT `mem_member_daily_cost_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `acc_accounts` (`id`);

--
-- Constraints for table `mem_member_daily_cost_details`
--
ALTER TABLE `mem_member_daily_cost_details`
  ADD CONSTRAINT `mem_member_daily_cost_details_ibfk_1` FOREIGN KEY (`daily_cost_id`) REFERENCES `mem_member_daily_cost` (`id`),
  ADD CONSTRAINT `mem_member_daily_cost_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `st_products` (`id`);

--
-- Constraints for table `mem_member_orders`
--
ALTER TABLE `mem_member_orders`
  ADD CONSTRAINT `mem_member_orders_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `mem_acapy_members` (`id`);

--
-- Constraints for table `mem_member_orders_progress`
--
ALTER TABLE `mem_member_orders_progress`
  ADD CONSTRAINT `mem_member_orders_progress_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `mem_member_orders` (`id`);

--
-- Constraints for table `mem_member_reward`
--
ALTER TABLE `mem_member_reward`
  ADD CONSTRAINT `mem_member_reward_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `mem_acapy_members` (`id`),
  ADD CONSTRAINT `mem_member_reward_ibfk_2` FOREIGN KEY (`operation_id`) REFERENCES `cli_operation` (`id`),
  ADD CONSTRAINT `mem_member_reward_ibfk_3` FOREIGN KEY (`account_id`) REFERENCES `acc_accounts` (`id`);

--
-- Constraints for table `mem_member_solfa`
--
ALTER TABLE `mem_member_solfa`
  ADD CONSTRAINT `mem_member_solfa_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `mem_acapy_members` (`id`),
  ADD CONSTRAINT `mem_member_solfa_ibfk_2` FOREIGN KEY (`account_id`) REFERENCES `acc_accounts` (`id`);

--
-- Constraints for table `mem_member_transactions`
--
ALTER TABLE `mem_member_transactions`
  ADD CONSTRAINT `mem_member_transactions_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `mem_member_orders` (`id`),
  ADD CONSTRAINT `mem_member_transactions_ibfk_2` FOREIGN KEY (`account_id`) REFERENCES `acc_accounts` (`id`);

--
-- Constraints for table `mem_member_transactions_details`
--
ALTER TABLE `mem_member_transactions_details`
  ADD CONSTRAINT `mem_member_transactions_details_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `mem_member_transactions` (`id`);

--
-- Constraints for table `sl_calender`
--
ALTER TABLE `sl_calender`
  ADD CONSTRAINT `sl_calender_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `sl_client` (`id`),
  ADD CONSTRAINT `sl_calender_ibfk_2` FOREIGN KEY (`sales_id`) REFERENCES `sl_sales_members` (`id`);

--
-- Constraints for table `sl_calls`
--
ALTER TABLE `sl_calls`
  ADD CONSTRAINT `sl_calls_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `sl_client` (`id`),
  ADD CONSTRAINT `sl_calls_ibfk_2` FOREIGN KEY (`sales_id`) REFERENCES `sl_sales_members` (`id`);

--
-- Constraints for table `sl_client`
--
ALTER TABLE `sl_client`
  ADD CONSTRAINT `sl_client_ibfk_1` FOREIGN KEY (`sales_id`) REFERENCES `sl_sales_members` (`id`);

--
-- Constraints for table `sl_offers`
--
ALTER TABLE `sl_offers`
  ADD CONSTRAINT `sl_offers_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `sl_client` (`id`),
  ADD CONSTRAINT `sl_offers_ibfk_2` FOREIGN KEY (`sales_id`) REFERENCES `sl_sales_members` (`id`);

--
-- Constraints for table `sl_offers_condition`
--
ALTER TABLE `sl_offers_condition`
  ADD CONSTRAINT `sl_offers_condition_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `sl_offers` (`id`);

--
-- Constraints for table `sl_offers_details`
--
ALTER TABLE `sl_offers_details`
  ADD CONSTRAINT `sl_offers_details_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `sl_offers` (`id`),
  ADD CONSTRAINT `sl_offers_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `st_products` (`id`);

--
-- Constraints for table `sl_succeded_offers`
--
ALTER TABLE `sl_succeded_offers`
  ADD CONSTRAINT `sl_succeded_offers_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `sl_offers` (`id`),
  ADD CONSTRAINT `sl_succeded_offers_ibfk_2` FOREIGN KEY (`sales_id`) REFERENCES `sl_sales_members` (`id`);

--
-- Constraints for table `st_enterance_per`
--
ALTER TABLE `st_enterance_per`
  ADD CONSTRAINT `st_enterance_per_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `st_stores` (`id`),
  ADD CONSTRAINT `st_enterance_per_ibfk_2` FOREIGN KEY (`store_id`) REFERENCES `st_stores` (`id`),
  ADD CONSTRAINT `st_enterance_per_ibfk_3` FOREIGN KEY (`invoice_id`) REFERENCES `st_invoices` (`id`),
  ADD CONSTRAINT `st_enterance_per_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `st_exit_per`
--
ALTER TABLE `st_exit_per`
  ADD CONSTRAINT `st_exit_per_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `st_stores` (`id`),
  ADD CONSTRAINT `st_exit_per_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `mem_acapy_members` (`id`),
  ADD CONSTRAINT `st_exit_per_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `st_exit_per_details`
--
ALTER TABLE `st_exit_per_details`
  ADD CONSTRAINT `st_exit_per_details_ibfk_1` FOREIGN KEY (`exit_id`) REFERENCES `st_exit_per` (`id`),
  ADD CONSTRAINT `st_exit_per_details_ibfk_2` FOREIGN KEY (`exit_id`) REFERENCES `st_exit_per` (`id`),
  ADD CONSTRAINT `st_exit_per_details_ibfk_3` FOREIGN KEY (`store_product_id`) REFERENCES `st_invoices_details` (`id`);

--
-- Constraints for table `st_invoices`
--
ALTER TABLE `st_invoices`
  ADD CONSTRAINT `st_invoices_ibfk_1` FOREIGN KEY (`provider_id`) REFERENCES `st_provider` (`id`),
  ADD CONSTRAINT `st_invoices_ibfk_2` FOREIGN KEY (`provider_id`) REFERENCES `st_provider` (`id`),
  ADD CONSTRAINT `st_invoices_ibfk_3` FOREIGN KEY (`provider_id`) REFERENCES `st_provider` (`id`);

--
-- Constraints for table `st_invoices_details`
--
ALTER TABLE `st_invoices_details`
  ADD CONSTRAINT `st_invoices_details_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `st_invoices` (`id`),
  ADD CONSTRAINT `st_invoices_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `st_products` (`id`);

--
-- Constraints for table `st_products`
--
ALTER TABLE `st_products`
  ADD CONSTRAINT `st_products_ibfk_1` FOREIGN KEY (`cat_id`) REFERENCES `st_products_category` (`id`);

--
-- Constraints for table `st_provider`
--
ALTER TABLE `st_provider`
  ADD CONSTRAINT `st_provider_ibfk_1` FOREIGN KEY (`cat_id`) REFERENCES `st_products_category` (`id`);

--
-- Constraints for table `st_provider_accounts`
--
ALTER TABLE `st_provider_accounts`
  ADD CONSTRAINT `st_provider_accounts_ibfk_1` FOREIGN KEY (`provider_id`) REFERENCES `st_provider` (`id`),
  ADD CONSTRAINT `st_provider_accounts_ibfk_2` FOREIGN KEY (`invoice_id`) REFERENCES `st_invoices` (`id`);

--
-- Constraints for table `st_provider_accounts_pays`
--
ALTER TABLE `st_provider_accounts_pays`
  ADD CONSTRAINT `st_provider_accounts_pays_ibfk_1` FOREIGN KEY (`provider_id`) REFERENCES `st_provider` (`id`),
  ADD CONSTRAINT `st_provider_accounts_pays_ibfk_2` FOREIGN KEY (`provider_acc_id`) REFERENCES `st_provider_accounts` (`id`),
  ADD CONSTRAINT `st_provider_accounts_pays_ibfk_3` FOREIGN KEY (`provider_id`) REFERENCES `st_provider` (`id`),
  ADD CONSTRAINT `st_provider_accounts_pays_ibfk_4` FOREIGN KEY (`provider_acc_id`) REFERENCES `st_provider_accounts` (`id`),
  ADD CONSTRAINT `st_provider_accounts_pays_ibfk_5` FOREIGN KEY (`account_id`) REFERENCES `acc_accounts` (`id`);

--
-- Constraints for table `st_returned_per`
--
ALTER TABLE `st_returned_per`
  ADD CONSTRAINT `st_returned_per_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `st_stores` (`id`),
  ADD CONSTRAINT `st_returned_per_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `mem_acapy_members` (`id`);

--
-- Constraints for table `st_returned_per_details`
--
ALTER TABLE `st_returned_per_details`
  ADD CONSTRAINT `st_returned_per_details_ibfk_1` FOREIGN KEY (`returned_id`) REFERENCES `st_returned_per` (`id`),
  ADD CONSTRAINT `st_returned_per_details_ibfk_2` FOREIGN KEY (`store_product_id`) REFERENCES `st_invoices_details` (`id`);

--
-- Constraints for table `st_store_products`
--
ALTER TABLE `st_store_products`
  ADD CONSTRAINT `st_store_products_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `st_invoices` (`id`),
  ADD CONSTRAINT `st_store_products_ibfk_2` FOREIGN KEY (`store_id`) REFERENCES `st_stores` (`id`),
  ADD CONSTRAINT `st_store_products_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `st_products` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
