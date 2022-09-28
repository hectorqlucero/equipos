CREATE TABLE jugadores (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(50) DEFAULT NULL,
  `paterno` varchar(50) DEFAULT NULL,
  `materno` varchar(50) DEFAULT NULL,
  `equipos_id` int(11) NOT NULL,
  `posicion` varchar(100) DEFAULT NULL,
  `altura` varchar(50) DEFAULT NULL,
  `peso` varchar(50) DEFAULT NULL,
  `p_puntos` int DEFAULT 0,
  `p_assistencias` int DEFAULT 0,
  `p_bloqueos` int DEFAULT 0,
  `p_robos` int DEFAULT 0,
  `pe_2puntos` int DEFAULT 0,
  `pe_3puntos` int DEFAULT 0,
  KEY `fk_jugadores_equipos` (`equipos_id`),
  CONSTRAINT `fk_jugadores_equipos` FOREIGN KEY (`equipos_id`) REFERENCES `equipos` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
