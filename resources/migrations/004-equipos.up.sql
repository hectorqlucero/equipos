CREATE TABLE equipos (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(100) DEFAULT NULL,
  `fundado` date DEFAULT NULL,
  `ciudad` varchar(50) DEFAULT NULL,
  `entrenador_id` int(11) NOT NULL,
  `manager` varchar(50) DEFAULT NULL,
  `estadios_id` int(11) NOT NULL,
  KEY `fk_equipos_entrenador` (`entrenador_id`),
  CONSTRAINT `fk_equipos_entrenador` FOREIGN KEY (`entrenador_id`) REFERENCES `entrenadores` (`id`) ON DELETE CASCADE,
  KEY `fk_equipos_estadios` (`estadios_id`),
  CONSTRAINT `fk_equipos_estadios` FOREIGN KEY (`estadios_id`) REFERENCES `estadios` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
  
