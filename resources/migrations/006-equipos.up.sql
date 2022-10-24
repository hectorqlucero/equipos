CREATE TABLE equipos (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(100) DEFAULT NULL,
  `fundado` date DEFAULT NULL,
  `ciudad` varchar(50) DEFAULT NULL,
  `manager` varchar(50) DEFAULT NULL,
  `estadio_id` int(11) NOT NULL,
  `entrenador_id` int(11) NOT NULL,
  FOREIGN KEY (`estadio_id`) REFERENCES `estadios`(`id`),
  FOREIGN KEY (`entrenador_id`) REFERENCES `entrenadores`(`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
  
