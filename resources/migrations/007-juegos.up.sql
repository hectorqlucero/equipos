CREATE TABLE juegos (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(100) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `temporadas_id` int(11) NOT NULL,
  `equipo_casa` int(11) NOT NULL,
  `equipo_fuera` int(11) NOT NULL,
  FOREIGN KEY (`temporadas_id`) REFERENCES `temporadas`(`id`),
  FOREIGN KEY (`equipo_casa`) REFERENCES `equipos`(`id`),
  FOREIGN KEY (`equipo_fuera`) REFERENCES `equipos`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
