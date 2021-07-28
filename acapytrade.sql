-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 26, 2021 at 11:56 PM
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

--
-- Dumping data for table `cli_clients`
--

INSERT INTO `cli_clients` (`id`, `name`, `organization`, `location`, `account_id`, `email`, `tele1`, `tele2`) VALUES
(1, 'تجربة 1', 'المؤسسة', 'طنطا', '', '', '', '');

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
  `member_id` int(11) NOT NULL,
  `amount` varchar(700) NOT NULL,
  `date_of_doc` date NOT NULL,
  `date_of_cash` date DEFAULT NULL,
  `pay_type` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

--
-- Dumping data for table `cli_contracts`
--

INSERT INTO `cli_contracts` (`id`, `client_id`, `date_from`, `date_to`, `num_of_visits`, `cost`, `due_after`) VALUES
(1, 1, '2020-07-02', '2021-07-02', '12', '1250', 'نص سنوي');

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
-- Dumping data for table `cli_maintaince`
--

INSERT INTO `cli_maintaince` (`id`, `client_id`, `member_id`, `date`, `problem`, `cost`, `pay_type`) VALUES
(1, 1, 1, '2021-07-13', 'sd', '50', 'كاش');

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
-- Dumping data for table `cli_operation`
--

INSERT INTO `cli_operation` (`id`, `client_id`, `sales_id`, `date`, `total_cost`, `pay_type`, `doc`, `doc_ext`) VALUES
(1, 1, 1, '2021-07-30', '150', 'كاش', 0xffd8ffe000104a46494600010100000100010000ffdb0043000302020302020303030304030304050805050404050a070706080c0a0c0c0b0a0b0b0d0e12100d0e110e0b0b1016101113141515150c0f171816141812141514ffdb00430103040405040509050509140d0b0d1414141414141414141414141414141414141414141414141414141414141414141414141414141414141414141414141414ffc000110800aa008c03012200021101031101ffc4001f0000010501010101010100000000000000000102030405060708090a0bffc400b5100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9faffc4001f0100030101010101010101010000000000000102030405060708090a0bffc400b51100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a737475767778797a82838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae2e3e4e5e6e7e8e9eaf2f3f4f5f6f7f8f9faffda000c03010002110311003f00fd53a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28aa7a86af67a4c5e6de5cc76e9fde738a00f14fda8ff006a583f668d053529b404d7032e7cb7d43ecbfaf96ffcabe326ff0082dedb2c857fe150a11fdeff0084a7ff00b8ebddff006e9f0ef84be3378421b11abdbc92a290763d7e6eb7ec67a4f9840d64633c7cf401f5537fc16fad80c8f84287dbfe129ffee3af46f823ff000560b4f8c9e2eb6d0bfe15cc7a43ccc17cdff84844c467fd9fb32ff3af838fec65a501ff002191ff007dd7b1fecc5fb337873c07f112c756bbd591bca7539693a73401fb116b3fdaada19b1b7cc40f8ce71919a96b91b4f899e128ad618d75db30aa8aa01939000a9bfe1687853fe83d67ff007f2803a8a2b97ff85a1e14ff00a0f59ffdfca3fe1687853fe83d67ff007f2803a8a2b97ff85a1e14ff00a0f59ffdfca3fe1687853fe83d67ff007f2803a8a2b97ff85a1e14ff00a0f59ffdfca3fe1687853fe83d67ff007f2803a8a2b963f143c263fe63d65ff7f2add878efc3fa9ca23b5d5ed6790f4547e68037a8a40430c83914b40057e407edcffb706ad67e33f12f8434b9da2934cd5af6c4946c63ca9dd3ff0065afd7fafe6e3f6c2666fda9be2d64e71e2cd5f1ff0081d350073d75f1d7c617b2b3cbab4ee09ce0b9a87fe172f897fe7fe5ff00beab85a00cd00775ff000b97c4a7fe5fe5ff00be8d2afc69f14c6731ea5321f5de6b47e1afecfbe2ff00897790ae9da5ccf6ee46640a4f15f757c27ff8253ffc24d630cbac33dbb3005b76462803e08ff85f1e36ff00a0e5c7fdf67fc697fe17c78dbfe83971ff007d9ff1afd5683fe0903e0e5876bdea16f520d721e38ff8245e93a758cb3e97726665190149eb401f9b1ff0be3c6dff0041cb8ffbecff008d1ff0be3c6dff0041cb8ffbecff008d7a57c65fd8cbc65f0e2f677834d9a7b3427e6da6be7bbcb39ac2e5e0b88da2950e1958608a00eebfe17c78dbfe83971ff7d9ff001a3fe17c78dbfe83971ff7d9ff001af3fa2803d1ad7e34f8fb529961b6d62ea595b80a8c727f5aec2d93e39dec2b342bacbc6dc861d2babfd84bc19a178b3e23449ac98f6ac8b80fdebf76bc37f0f7c33a76876705b69366d0ac4b8631039e3d6803f9fb9edbe3ac1133c89ad2a0e493567e1efed33e3df859e29b79357bcba211c164998e4735fd03def807c39796b243368f67e53a90d8880e2bf14bfe0a79e05f0ff82fe24dba684b1aac921de23c71c1a00fd52fd8bbe371f8f7f09af3c42cdb8dbea8f624e7fbb040ff00fb52bdf2be08ff008230c8d27ecb3e23dc738f175c81f4fb0d8d7def40057f36ff00b617fc9d3fc5bffb1b357ffd2e9abfa48afe6dff006c2ff93a7f8b7ff6366aff00fa5d35007900059800324f615f557ec77fb226a3f1bbc4114d796ceb648e0e48e315e15f093c0777e3df1969b636f1192369943e076cd7f419fb31fc1ad33e167c3dd316dedd52ee5814bb639a00d2f833fb3ff873e116850da59d942f32a805d901c1af5248d225da8aa83d14629598229663803a935e19f1aff6b4f077c2082586f2fe337983852c383401ee948402304647a57e696a5ff0541b54d55921b9530eec039ed5f447c0efdbabc1bf125e0b09efe34be7c01f30a00fa1bc55e07d23c5fa5cd657f650c8920c6e28322bf2bff6ebfd8163f0ddbddf893c3b6e5f396c46bf8d7eb55add457b024d0b89237190c0d66f8afc3165e2dd1a7d3efa259629148c30a00fe5e350d3e7d2eee4b6b98cc534670ca7b556afabbf6eff0080d3fc35f891a85ec16e63b1924382071d6be51a00e9bc05e3dd47e1feb316a3a748c92a306f94e2bec2f0effc1573e21787f4982c5226758976825abe17a2803ef0d4ff00e0acff0010f52b196dcc2ca2418c86af927e2c7c5bd5fe2deb6752d5a46794b16018e715c2d1401fb67ff04645dbfb2c7887dfc5b707ff00246c6bef5af82ffe08cbff0026b1e20ffb1b2e3ff486c6bef4a002bf9b7fdb0bfe4e9fe2dffd8d9abffe974d5fd2457f36ff00b617fc9d3fc5bffb1b357ffd2e9a803e8eff00825bfc3f83c65e3d9e49a212792e0f22bf6d2ced52cad628231848d42815f921ff000472f2bfe127d5338dff0037f2afd75a00f20fda53e2d41f0bfe1eea774240977e51d9cfb57f3f9f1abe2feb5f157c5d7b7ba8dd48f18998221638c66bf5dffe0a58b7a7c26fe46ff2fcae76f4e95f8937b9fb64f9ebbce7f3a008324f735b9e0ff18ea5e0ad72df53d3ae1e19e16078623358745007ef77ec11fb448f8a3f0e6d2df519b7df2a803279afaeabf233fe097497ff00da56c46ffb3e47d2bf5ce803e00ff82ab7802d6ebe171d4e18479e41cb639afc56236920f5afdebff8292987fe14cc9e6e3186eb5f8357db7ed936dfbbbce28021a2b63c39e14bff00144e62b188cae3b019aebee3e02f8aedac9ee5ec6411a8c93b0d0079c5152dddb3d9dc3c320dae8704545401fb69ff000465ff009358f107fd8d971ffa43635f7a57c17ff0465ff9358f107fd8d971ff00a43635f7a50015fcdbfed85ff274ff0016ff00ec6cd5ff00f4ba6afe922bf9b7fdb0bfe4e9fe2dff00d8d9abff00e974d401f45ffc12efe21dbf833c7b34734a23f39f0327af4afdb6b2ba4beb48ae233949143035fcc87c28f1bdd7817c67a6dfc12b4689329700e38cd7f417fb2f7c69d37e29fc3fd33c89d5eee18155c6793c50068fed1ff0a20f89ff000f752b3f283dcf94db38f6afe7fbe37fc17d73e14f8baf2cef6ce510b4ac51c21c75afe9699432904641ea0d78b7c65fd973c25f162d657bad3e2fb61070e5475a00fe70cc6e0e0ab03e98ae8bc13e01d5bc75aedbe99a75acb2492b01b821c0afd5dd57fe096f05c6aed3431a2c25b2063b57d23f037f62af097c318e1b9b8b08a5bd4c60ed140185fb077ecf0bf0a3e1d5ac97f06dbd650791cf4afacea2b6b68ace158a1411c6a30140e9595e2ef14d9784344b8d46fa558a28d49cb1ef401f0c7fc155fe20dadb7c303a5c330fb40072b9e735f8b04ee249ea6beadfdb87e38dc7c54f8ab75a6dbdc192cc4e1061b23938af64f851ff0004c2d4fe207802c35b5207db1372e7f0a00f3dff00826df84b4df15f8fe5835189248c381f30afd6ef883f027c1f6ff0ef5931e9b12b25a332be075c57cf3fb28fec137df02b5e37f70c0e4e4f35f6c789f463ad785afb4c1d6780c42803f99ef8b56b1d9fc45d7608942c6972c001e95c8d7ead78d7fe0955a9f897c5f7faa2b0f2ee242fd477af937f6b4fd8eef7f678b74967e9c1fa8a00fd0cff008232ff00c9ac7883fec6cb8ffd21b1afbd2be0bff8232ffc9acf883fec6cb8ff00d21b1afbd2800afe6dff006c2ff93a7f8b7ff6366aff00fa5d357f4915fcdc7ed84acbfb537c5ac8ebe2cd5c8ffc0e9a803c78120820e08ef5f527ec7ffb5d6a3f033c43145733c925948e072dc015f2dd00e2803fa4ef833fb44f86be2ce8105e417f04570ca098d9c0cf15eaf14c93a078dc3a1e8ca722bf99ff00871f1d7c53f0e350864b1d4e716e8c331ef3d2bee4f861ff000565bbf0bd8436ba8db3dc6d0012ea48a00fd7fa42715f9bd6bff0572d1ee2d7cd367129c74c571fe30ff82bfc571672dbd858ed66046f8d6803f4b7c55f10b43f0869d3dddfea1046225276171935f957fb75fedfbff094c377e17f0f4a5146577c6dc57ca7f19ff6c2f177c4fbc97cabf9edada4272a18f4af01baba9af6779a791a595ce59d8e49a00b96fa8cd77aedb5ddcc86494ce8ccedd4fcc2bf733f66efdabfc31a27c18d0ecef2ee213dac01482c076afc2204a90470477adab7f19eb76900861d4a78e21c6d56e2803fa37f007ed0fe1ef88375e4584d1bbf4c2b66bd3afaf92c6c25ba90811c69bcfd2bf15ffe0993e23d6b56f8893453ddcb3c5bc637b57ec37c46f323f871ad797912ad93631eb8a00f33bffdae7c23a7ea92d94b730ac91920e5ebf3cffe0a69f1d74af897a6c7069d3a4aab85010e6be27f8b9e36f1041f12b5f5fed19e3db72c0057ed5c0ea3af6a1ab0c5e5dc971fef9cd007ed2ffc1197fe4d67c41ff6365c7fe90d8d7de95f067fc119d193f659f106e18cf8b2e08cfa7d86cabef3a002bf1b7f6fefd9520b7f17f89bc5b69751a5cdfead7b78ea73d649ddc8ff00c7abf641dd63467760a8a32598e001eb4c9ae61b78c3cb2a4484e033b000fe34d2bec07f2df71e1ebab795a363192a7190d51ff62dc7fb1f9d7f52d14c93c61e375910f4643907f1a7d2d80fe59bfb16e3fd8fce8fec5b8ff63f3afea668a00fe597fb1ee3fd9fce8fec5b83fdcfcebfa9aa2803f966fec5b8ff0063f3a3fb16e3fd8fcebfa99a2803f966fec5b8ff0063f3a3fb16e3fd8fcebfa99a2803f9c5fd9d3e2f7883e06788db52d3446e1883b49ef5f5d788bfe0a79e38d6fc2577a5358c4a6788c664e33835fb0145007f2f3e26fed0f116bd7ba95c94335cc8646c1e39aeb3e107c1cb8f887e28b5b296e228616700f24e7f4afe96a8a00f00fd8a3e11db7c18f84779a25abac91cfaa3de12bead6f027fed315eff004514019b71e1ad22eef1eee7d2aca6bb785adda792dd1a4313162c8588ced259b23a7cc7d6a31e12d0c42211a369fe52aaa88fecb1ed0173b4631d06e6c7a6e3eb5ad45004369676fa7dba5bdac11db4099db14281157272700703926a6a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a00fffd900d21b1afbd2800afe6dff006c2ff93a7f8b7ff636, 'jpg'),
(2, 1, 1, '2021-07-13', '4850', 'كاش', NULL, NULL);

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

--
-- Dumping data for table `mem_acapy_members`
--

INSERT INTO `mem_acapy_members` (`id`, `name`, `acc_num`, `app_token`) VALUES
(1, 'mohamed', '', '');

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
  `product` varchar(700) NOT NULL,
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

--
-- Dumping data for table `priviliges_name`
--

INSERT INTO `priviliges_name` (`name`) VALUES
('Accounts'),
('ClientScreen'),
('ClientScreenClients'),
('ClientScreenContract'),
('ClientScreenMaintainces'),
('ClientScreenOperations'),
('Hr'),
('Invoice'),
('MainData'),
('MainDataScreenClients'),
('MainDataScreenProducts'),
('MainDataScreenProvider'),
('maintaince'),
('members'),
('Sales'),
('SalesScreenCalendar'),
('SalesScreenCalls'),
('SalesScreenClients'),
('SalesScreenOffers'),
('Store'),
('StoreScreenProducts'),
('StoreScreenProvider');

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
-- Dumping data for table `sl_calender`
--

INSERT INTO `sl_calender` (`id`, `client_id`, `sales_id`, `date`, `time`, `details`) VALUES
(1, 1, 1, '2021-07-25', '13:00:00', NULL),
(2, 1, 1, '2021-07-25', '11:00:00', NULL),
(3, 1, 1, '2021-07-27', '10:00:00', NULL),
(4, 1, 1, '2021-07-25', '09:00:00', NULL),
(5, 1, 1, '2021-07-26', '20:54:00', '');

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

--
-- Dumping data for table `sl_calls`
--

INSERT INTO `sl_calls` (`id`, `client_id`, `date`, `time`, `details`, `sales_id`) VALUES
(1, 1, '2021-07-07', '19:44:00', '', 1);

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

--
-- Dumping data for table `sl_client`
--

INSERT INTO `sl_client` (`id`, `name`, `organization`, `relation`, `location`, `email`, `tele1`, `tele2`, `sales_id`) VALUES
(1, 'TEST', 'a', 'a', 'a', 'a', 'a', 'a', 1),
(2, 'scccc', 'ccc', 'ccc', 'ccc', 'ccc', 'c', '', 1);

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

--
-- Dumping data for table `sl_offers`
--

INSERT INTO `sl_offers` (`id`, `client_id`, `date`, `cost`, `discount`, `discount_percent`, `total_cost`, `doc`, `doc_ext`, `sales_id`, `notes`) VALUES
(1, 1, '2021-07-01', '2750.0', '0', '0', '2750.0', NULL, NULL, 1, 'لايوجد');

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
-- Dumping data for table `sl_offers_condition`
--

INSERT INTO `sl_offers_condition` (`id`, `offer_id`, `attribute`, `value`) VALUES
(1, 1, 'الارتباط بالسعر', '3 شهور');

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

--
-- Dumping data for table `sl_offers_condition_temp`
--

INSERT INTO `sl_offers_condition_temp` (`id`, `offer_id`, `attribute`, `value`) VALUES
(3, 2, 's', 's');

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
-- Dumping data for table `sl_offers_details`
--

INSERT INTO `sl_offers_details` (`id`, `offer_id`, `product_id`, `cost`, `amount`, `total_cost`) VALUES
(1, 1, 1, '275', '10', '2750');

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

--
-- Dumping data for table `sl_offers_details_temp`
--

INSERT INTO `sl_offers_details_temp` (`id`, `offer_id`, `product_id`, `cost`, `amount`, `total_cost`) VALUES
(5, 2, 1, '125', '12', '1500');

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

--
-- Dumping data for table `sl_offers_temp`
--

INSERT INTO `sl_offers_temp` (`id`, `client_id`, `date`, `cost`, `discount`, `discount_percent`, `total_cost`, `doc`, `doc_ext`, `sales_id`, `notes`) VALUES
(2, 1, '2021-07-14', '1500.0', '0', '0', '1500.0', NULL, NULL, 0, 'لايوجد');

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
-- Dumping data for table `sl_sales_members`
--

INSERT INTO `sl_sales_members` (`id`, `name`, `num_of_success`, `app_token`) VALUES
(1, 'ahmed', '0', NULL);

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
  `account_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

--
-- Dumping data for table `st_products`
--

INSERT INTO `st_products` (`id`, `cat_id`, `name`, `model`, `details`) VALUES
(1, 1, 'hikvision', 'HDT-0626232312hs', '');

-- --------------------------------------------------------

--
-- Table structure for table `st_products_category`
--

CREATE TABLE `st_products_category` (
  `id` int(11) NOT NULL,
  `name` varchar(700) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `st_products_category`
--

INSERT INTO `st_products_category` (`id`, `name`) VALUES
(1, 'cctv');

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
  `name` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `user_name`, `password`, `role`) VALUES
(1, 'a', 'a', 'super_admin'),
(2, 's', 'a', 'user');

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
-- Dumping data for table `users_permissions`
--

INSERT INTO `users_permissions` (`user_id`, `privileges`, `value`) VALUES
(2, 'Accounts', 'true'),
(2, 'ClientScreen', 'true'),
(2, 'ClientScreenClients', 'true'),
(2, 'ClientScreenContract', 'false'),
(2, 'ClientScreenMaintainces', 'false'),
(2, 'ClientScreenOperations', 'false'),
(2, 'Hr', 'true'),
(2, 'Invoice', 'false'),
(2, 'MainData', 'false'),
(2, 'MainDataScreenClients', 'false'),
(2, 'MainDataScreenProducts', 'false'),
(2, 'MainDataScreenProvider', 'false'),
(2, 'maintaince', 'true'),
(2, 'members', 'true'),
(2, 'Sales', 'true'),
(2, 'SalesScreenCalendar', 'false'),
(2, 'SalesScreenCalls', 'false'),
(2, 'SalesScreenClients', 'false'),
(2, 'SalesScreenOffers', 'false'),
(2, 'Store', 'true'),
(2, 'StoreScreenProducts', 'false'),
(2, 'StoreScreenProvider', 'false');

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
-- Indexes for table `sl_offers_condition_temp`
--
ALTER TABLE `sl_offers_condition_temp`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sl_offers_details`
--
ALTER TABLE `sl_offers_details`
  ADD PRIMARY KEY (`id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cli_maintaince_details`
--
ALTER TABLE `cli_maintaince_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cli_operation`
--
ALTER TABLE `cli_operation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `sl_offers_details`
--
ALTER TABLE `sl_offers_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `sl_offers_details_temp`
--
ALTER TABLE `sl_offers_details_temp`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

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
