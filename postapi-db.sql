DROP TABLE IF EXISTS `posts`;
CREATE TABLE IF NOT EXISTS `posts` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` TEXT,
  `description` TEXT,
  `url` TEXT,
  `location` varchar(250),
  `jaime` int NOT NULL DEFAULT 0,
  `date` DATE
  ) ENGINE=InnoDB;
 