-- MariaDB dump 10.19  Distrib 10.5.10-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: db_school
-- ------------------------------------------------------
-- Server version	10.5.9-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course`
(
    `course_id`   bigint(20)                             NOT NULL AUTO_INCREMENT,
    `course_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    `type`        int(1)  DEFAULT NULL,
    `score`       int(5)  DEFAULT NULL,
    `time`        int(5)                                 NOT NULL,
    `major_id`    int(11) DEFAULT NULL,
    `term`        int(2)                                 NOT NULL,
    `teacher_id`  int(8)                                 NOT NULL,
    PRIMARY KEY (`course_id`),
    UNIQUE KEY `course_cid_uindex` (`course_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course`
    DISABLE KEYS */;
INSERT INTO `course`
VALUES (1, '高数（一）', 0, 5, 100, 1, 1, 17100101),
       (2, '英语（一）', 0, 4, 100, 1, 1, 17100102),
       (3, '计算机导论', 0, 3, 100, 1, 1, 17100103),
       (4, '操作系统', 0, 4, 100, 1, 1, 17100104),
       (5, '计算机网络', 0, 4, 100, 1, 1, 17100105);
/*!40000 ALTER TABLE `course`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department`
(
    `department_id`   int(2)                                 NOT NULL AUTO_INCREMENT,
    `department_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`department_name`),
    UNIQUE KEY `department_department_name_uindex` (`department_name`),
    UNIQUE KEY `department_department_id_uindex` (`department_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `department`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grade`
--

DROP TABLE IF EXISTS `grade`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grade`
(
    `grade_id`  int(11) NOT NULL,
    `major_id`  int(11) NOT NULL,
    `class_num` int(11) NOT NULL,
    `grade`     int(4)  NOT NULL,
    PRIMARY KEY (`grade_id`),
    UNIQUE KEY `grade_grade_id_uindex` (`grade_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade`
--

LOCK TABLES `grade` WRITE;
/*!40000 ALTER TABLE `grade`
    DISABLE KEYS */;
INSERT INTO `grade`
VALUES (22017, 2, 4, 2017);
/*!40000 ALTER TABLE `grade`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `major`
(
    `major_id`   int(2)                                 NOT NULL,
    `major_name` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`major_id`),
    UNIQUE KEY `major_major_id_uindex` (`major_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major`
    DISABLE KEYS */;
INSERT INTO `major`
VALUES (1, '计算机'),
       (2, '软件工程');
/*!40000 ALTER TABLE `major`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room`
(
    `room_id` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL,
    `type`    int(11) DEFAULT NULL,
    PRIMARY KEY (`room_id`),
    UNIQUE KEY `room_room_id_uindex` (`room_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room`
    DISABLE KEYS */;
INSERT INTO `room`
VALUES ('b101', 1),
       ('b102', 1),
       ('b103', 1),
       ('b104', 1),
       ('c101', 0),
       ('c102', 0),
       ('c103', 0),
       ('c104', 0),
       ('c105', 0),
       ('c106', 0),
       ('c107', 0),
       ('c108', 0),
       ('c201', 0),
       ('c202', 0),
       ('c203', 0),
       ('d101', 0);
/*!40000 ALTER TABLE `room`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule`
(
    `major_id`   int(11)                                DEFAULT NULL,
    `term`       int(11)                                DEFAULT NULL,
    `classnum`   int(11)                                DEFAULT NULL,
    `course_id`  bigint(20) NOT NULL,
    `time_part`  int(5)     NOT NULL,
    `teacher_id` bigint(20)                             DEFAULT NULL,
    `room_id`    varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule`
    DISABLE KEYS */;
INSERT INTO `schedule`
VALUES (1, 1, 0, 2, 0, 17100102, 'c101'),
       (1, 1, 0, 4, 1, 17100104, 'c101'),
       (1, 1, 0, 5, 3, 17100105, 'c101'),
       (1, 1, 0, 3, 4, 17100103, 'c101'),
       (1, 1, 0, 5, 5, 17100105, 'c101'),
       (1, 1, 0, 2, 6, 17100102, 'c101'),
       (1, 1, 0, 4, 7, 17100104, 'c101'),
       (1, 1, 0, 3, 8, 17100103, 'c101'),
       (1, 1, 0, 2, 12, 17100102, 'c101'),
       (1, 1, 0, 1, 13, 17100101, 'c101'),
       (1, 1, 0, 5, 14, 17100105, 'c101'),
       (1, 1, 0, 5, 17, 17100105, 'c101'),
       (1, 1, 0, 4, 18, 17100104, 'c101'),
       (1, 1, 0, 1, 19, 17100101, 'c101'),
       (1, 1, 0, 1, 20, 17100101, 'c101'),
       (1, 1, 0, 4, 23, 17100104, 'c101'),
       (1, 1, 0, 3, 24, 17100103, 'c101'),
       (1, 1, 1, 5, 0, 17100105, 'c102'),
       (1, 1, 1, 3, 2, 17100103, 'c101'),
       (1, 1, 1, 4, 4, 17100104, 'c102'),
       (1, 1, 1, 5, 6, 17100105, 'c102'),
       (1, 1, 1, 3, 7, 17100103, 'c102'),
       (1, 1, 1, 2, 10, 17100102, 'c101'),
       (1, 1, 1, 4, 11, 17100104, 'c101'),
       (1, 1, 1, 3, 14, 17100103, 'c102'),
       (1, 1, 1, 2, 15, 17100102, 'c101'),
       (1, 1, 1, 4, 16, 17100104, 'c101'),
       (1, 1, 1, 3, 18, 17100103, 'c102'),
       (1, 1, 1, 2, 21, 17100102, 'c101'),
       (1, 1, 1, 5, 22, 17100105, 'c101'),
       (1, 1, 1, 5, 24, 17100105, 'c102'),
       (1, 1, 2, 5, 1, 17100105, 'c102'),
       (1, 1, 2, 1, 2, 17100101, 'c102'),
       (1, 1, 2, 5, 4, 17100105, 'c103'),
       (1, 1, 2, 3, 5, 17100103, 'c102'),
       (1, 1, 2, 1, 8, 17100101, 'c102'),
       (1, 1, 2, 4, 9, 17100104, 'c101'),
       (1, 1, 2, 3, 10, 17100103, 'c102'),
       (1, 1, 2, 2, 11, 17100102, 'c102'),
       (1, 1, 2, 5, 12, 17100105, 'c102'),
       (1, 1, 2, 4, 13, 17100104, 'c102'),
       (1, 1, 2, 3, 16, 17100103, 'c102'),
       (1, 1, 2, 2, 18, 17100102, 'c103'),
       (1, 1, 2, 5, 19, 17100105, 'c102'),
       (1, 1, 2, 3, 20, 17100103, 'c102'),
       (1, 1, 2, 4, 21, 17100104, 'c102'),
       (1, 1, 2, 4, 22, 17100104, 'c102'),
       (1, 1, 2, 1, 23, 17100101, 'c102'),
       (1, 1, 3, 4, 0, 17100104, 'c103'),
       (1, 1, 3, 1, 1, 17100101, 'c103'),
       (1, 1, 3, 2, 2, 17100102, 'c103'),
       (1, 1, 3, 2, 3, 17100102, 'c102'),
       (1, 1, 3, 4, 6, 17100104, 'c103'),
       (1, 1, 3, 5, 8, 17100105, 'c103'),
       (1, 1, 3, 2, 9, 17100102, 'c102'),
       (1, 1, 3, 1, 10, 17100101, 'c103'),
       (1, 1, 3, 3, 11, 17100103, 'c103'),
       (1, 1, 3, 4, 15, 17100104, 'c102'),
       (1, 1, 3, 5, 16, 17100105, 'c103'),
       (1, 1, 3, 5, 18, 17100105, 'c104'),
       (1, 1, 3, 3, 19, 17100103, 'c103'),
       (1, 1, 3, 5, 20, 17100105, 'c103'),
       (1, 1, 3, 3, 21, 17100103, 'c103');
/*!40000 ALTER TABLE `schedule`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student`
(
    `student_id`       varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL,
    `student_name`     varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    `student_password` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    `grade`            int(11)                                NOT NULL,
    `class`            int(11)                                NOT NULL,
    PRIMARY KEY (`student_id`),
    UNIQUE KEY `student_sno_uindex` (`student_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student`
    DISABLE KEYS */;
INSERT INTO `student`
VALUES ('1712120441', '张三', 'admin', 1, 1);
/*!40000 ALTER TABLE `student`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher`
(
    `teacher_id`       int(8)                                 NOT NULL AUTO_INCREMENT,
    `teacher_name`     varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    `telephone`        varchar(20) COLLATE utf8mb4_unicode_ci          DEFAULT NULL,
    `teacher_password` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '123456',
    `course_id`        bigint(20)                                      DEFAULT NULL,
    PRIMARY KEY (`teacher_id`),
    UNIQUE KEY `teacher_tno_uindex` (`teacher_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 17100104
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher`
    DISABLE KEYS */;
INSERT INTO `teacher`
VALUES (17100101, '张老师', '13123388888', '123456', 1),
       (17100103, '李老师', '1312487677', '123456', 3);
/*!40000 ALTER TABLE `teacher`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2021-05-23 23:05:02
