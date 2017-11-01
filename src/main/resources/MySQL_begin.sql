CREATE TABLE `contact` (
  `idContact` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `surname` varchar(20) NOT NULL,
  `patronymic` varchar(20) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` enum('Male','Female','None') DEFAULT 'None',
  `nation` varchar(45) DEFAULT NULL,
  `familyState` enum('Married','Single','None') DEFAULT 'None',
  `webSite` varchar(100) DEFAULT NULL,
  `job` varchar(45) DEFAULT NULL,
  `Contactcol` varchar(45) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `streetHouseRoom` varchar(45) DEFAULT NULL,
  `index` varchar(15) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `photoUrl` varchar(100) DEFAULT NULL,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`idContact`),
  UNIQUE KEY `idContact_UNIQUE` (`idContact`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;


CREATE TABLE `attachment` (
  `idAttachment` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `path` tinytext NOT NULL,
  `fileName` tinytext NOT NULL,
  `loadDate` datetime NOT NULL,
  `idContact` int(10) unsigned NOT NULL,
  `comment` tinytext,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`idAttachment`),
  UNIQUE KEY `idAttachment_UNIQUE` (`idAttachment`),
  KEY `fk_attachment_contact_idx` (`idContact`),
  CONSTRAINT `fk_attachment_contact` FOREIGN KEY (`idContact`) REFERENCES `contact` (`idContact`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `phone` (
  `idPhone` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `countryCode` varchar(5) NOT NULL,
  `operatorCode` varchar(5) NOT NULL,
  `phoneNumber` varchar(10) NOT NULL,
  `phoneType` enum('Mobile','Home') NOT NULL,
  `idContact` int(11) NOT NULL,
  `comment` tinytext,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`idPhone`),
  UNIQUE KEY `idPhones_UNIQUE` (`idPhone`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


INSERT INTO contact (name, surname, patronymic, dob, gender, nation, familyState, webSite, job, country, city, streetHouseRoom, contact.index, email, photoUrl)
VALUES 
('Ilya','Pavlovsky','Valer\'evich','1998-04-19','Male','belarus','Single','google.com','ITechArt','Belarus','Minsk','Doroshevicha 3 - 227',null,'www.pavlovsky@gmail.com',null),
('Alexey','Pavlovsky','Valer\'evich','1996-10-12','Male','belarus','Single','yandex.ru','epam','Belarus','Minsk',null,'223710',null,null),
('Alexander','Volosevich','Sergeevich','1999-05-13','Male','belarus','Single',null,'ITechArt','Belarus','Soligorsk',null,null,'www.pavlovsky@gmail.com',null),
('Darya','Danilova',null,'1998-07-8','Female','belarus','Single','google.com','ITechArt','Belarus','Vitebsk',null,null,null,null),
('Nastya','Dlusskaya','Dmitrievna','1998-03-03','Female','belarus','Married',null,null,'Belarus','Minsk','Doroshevicha 3 - 223',null,null,null),
('Ilya1','Pavlovsky','Valer\'evich','1998-04-19','Male','belarus','Single','google.com','ITechArt','Belarus','Minsk','Doroshevicha 3 - 227',null,'www.pavlovsky@gmail.com',null),
('Alexey1','Pavlovsky','Valer\'evich','1996-10-12','Male','belarus','Single','yandex.ru','epam','Belarus','Minsk',null,'223710',null,null),
('Alexander1','Volosevich','Sergeevich','1999-05-13','Male','belarus','Single',null,'ITechArt','Belarus','Soligorsk',null,null,'www.pavlovsky@gmail.com',null),
('Darya1','Danilova',null,'1998-07-8','Female','belarus','Single','google.com','ITechArt','Belarus','Vitebsk',null,null,null,null),
('Nastya1','Dlusskaya','Dmitrievna','1998-03-03','Female','belarus','Married',null,null,'Belarus','Minsk','Doroshevicha 3 - 223',null,null,null),('Ilya','Pavlovsky','Valer\'evich','1998-04-19','Male','belarus','Single','google.com','ITechArt','Belarus','Minsk','Doroshevicha 3 - 227',null,'www.pavlovsky@gmail.com',null),
('Alexey2','Pavlovsky','Valer\'evich','1996-10-12','Male','belarus','Single','yandex.ru','epam','Belarus','Minsk',null,'223710',null,null),
('Alexander2','Volosevich','Sergeevich','1999-05-13','Male','belarus','Single',null,'ITechArt','Belarus','Soligorsk',null,null,'www.pavlovsky@gmail.com',null),
('Darya2','Danilova',null,'1998-07-8','Female','belarus','Single','google.com','ITechArt','Belarus','Vitebsk',null,null,null,null),
('Nastya2','Dlusskaya','Dmitrievna','1998-03-03','Female','belarus','Married',null,null,'Belarus','Minsk','Doroshevicha 3 - 223',null,null,null);