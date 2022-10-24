CREATE TABLE jugadores (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(50) DEFAULT NULL,
  `paterno` varchar(50) DEFAULT NULL,
  `materno` varchar(50) DEFAULT NULL,
  `altura` varchar(50) DEFAULT NULL,
  `peso` varchar(50) DEFAULT NULL,
  `posiciones_id` int(11) NOT NULL,
  `equipos_id` int(11) NOT NULL,
  FOREIGN KEY (`posiciones_id`) REFERENCES `posiciones`(`id`),
  FOREIGN KEY (`equipos_id`) REFERENCES `equipos`(`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
