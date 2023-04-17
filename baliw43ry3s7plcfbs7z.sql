-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: baliw43ry3s7plcfbs7z-mysql.services.clever-cloud.com:3306
-- Generation Time: Feb 12, 2022 at 10:34 PM
-- Server version: 8.0.22-13
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `baliw43ry3s7plcfbs7z`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `id` int NOT NULL,
  `date` timestamp NOT NULL,
  `doctorID` int NOT NULL,
  `userID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE `doctor` (
  `id` int NOT NULL,
  `address` varchar(64) NOT NULL,
  `plz` int NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `specializationId` int NOT NULL,
  `city` varchar(155) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`id`, `address`, `plz`, `name`, `specializationId`, `city`) VALUES
(1, 'Dreikönigsstraße 19', 60594, 'Dipl.-Psych. Florian Kaiser', 1, 'Frankfurt'),
(2, 'Hansaring 88', 50670, 'Christof Niehues Psychologe', 1, 'Köln'),
(3, 'Herrnstraße 28', 63065, 'Frau Dipl.-Psych. Soheila Kiani-Dorff', 1, 'Offenbach'),
(4, 'Lindenstraße 5', 60325, 'Praxis für Neurologie Dr. Grabowski', 2, 'Frankfurt'),
(5, 'Kaiserstraße 39', 63065, 'Gemeinschaftspraxis für Neurologie', 2, 'Offenbach'),
(6, 'Neusser Straße 14', 50670, 'Dr.med. Wim Richter', 2, 'Köln'),
(7, 'Mainzer Landstraße 65', 60329, 'Hausarzt Westend', 3, 'Frankfurt'),
(8, 'Große Bockenheimer Straße 44', 60313, 'Stefan Martin', 3, 'Frankfurt'),
(9, 'Friedrichstraße 43', 63065, 'Praxis Evangelos Zinonidis', 3, 'Offenbach'),
(10, 'Langstraße 30', 63450, 'Dr. med. Georg Kroner', 3, 'Hanau'),
(11, 'Hohenstaufenring 42', 50674, 'Dr. Alexandra Bujak', 3, 'Köln'),
(12, 'Kaiserstraße 1', 60311, 'Augenland - Augenarzt und Augenlasern Frankfurt', 4, 'Frankfurt'),
(13, 'Große Bockenheimer Straße 35', 60313, 'Augenarzt an der Alten Oper', 4, 'Frankfurt'),
(14, 'Kölner Straße 2', 51645, 'Schröter D. Dr.med.', 4, 'Gummersbach'),
(15, 'Große Bockenheimer Straße 41', 60313, 'HNO Praxis Dr. Süssmann', 5, 'Frankfurt'),
(16, 'Usinger Straße 5', 60389, 'Chirurgisches Zentrum am Bethanien', 6, 'Frankfurt'),
(17, 'Zeil 57', 60313, 'Op-Zentrum • Praxis • Konstablerwache', 6, 'Frankfurt');

-- --------------------------------------------------------

--
-- Table structure for table `healthInformation`
--

CREATE TABLE `healthInformation` (
  `userID` int NOT NULL,
  `medication` varchar(100) NOT NULL,
  `allergies` varchar(100) NOT NULL,
  `other` varchar(100) NOT NULL,
  `preIllness` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `healthproblem`
--

CREATE TABLE `healthproblem` (
  `id` int NOT NULL,
  `name` varchar(64) NOT NULL,
  `doctorType` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `specialization` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `healthproblem`
--

INSERT INTO `healthproblem` (`id`, `name`, `doctorType`, `specialization`) VALUES
(1, 'Depression', 'Psychologe', 1),
(2, 'Migräne', 'Neurologe', 2),
(3, 'Epilepsie', 'Neurologe', 2),
(4, 'Schädel-Hirn-Trauma', 'Neurologe', 2),
(5, 'Erkältung', 'Hausarzt', 3),
(6, 'Bluthochdruck', 'Hausarzt', 3),
(7, 'Mandelentzündung', 'Hausarzt', 3),
(8, 'Augenprobleme', 'Augenarzt', 4),
(9, 'Ohrenprobleme', 'Ohrenarzt', 5),
(10, 'Gebrochenes Bein', 'Chirurg', 6),
(11, 'Gebrochener Arm', 'Chirurg', 6);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int NOT NULL,
  `firstname` varchar(64) NOT NULL,
  `lastname` varchar(64) NOT NULL,
  `dateOfBirth` varchar(64) NOT NULL,
  `address` varchar(64) NOT NULL,
  `plz` int NOT NULL,
  `city` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `insuranceName` varchar(64) NOT NULL,
  `password` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salt` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  `privateInsurance` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `doctorId` (`doctorID`),
  ADD KEY `appointment_ibfk_2` (`userID`);

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `doctor_ibfk_1` (`specializationId`);

--
-- Indexes for table `healthInformation`
--
ALTER TABLE `healthInformation`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `healthproblem`
--
ALTER TABLE `healthproblem`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `doctor`
--
ALTER TABLE `doctor`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `healthproblem`
--
ALTER TABLE `healthproblem`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`doctorID`) REFERENCES `doctor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `healthInformation`
--
ALTER TABLE `healthInformation`
  ADD CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
