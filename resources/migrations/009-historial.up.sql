CREATE TABLE historial (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `p_puntos` int(11) DEFAULT 0,
  `p_asistencias` int(11) DEFAULT 0,
  `p_bloqueos` int(11) DEFAULT 0,
  `p_robos` int(11) DEFAULT 0,
  `pe_2puntos` int(11) DEFAULT 0,
  `pe_3puntos` int(11) DEFAULT 0,
  `jugadores_id` int(11) NOT NULL,
  `equipos_id` int(11) NOT NULL,
  `juegos_id` int(11) NOT NULL,
  FOREIGN KEY (`jugadores_id`) REFERENCES `jugadores`(`id`),
  FOREIGN KEY (`equipos_id`) REFERENCES `equipos`(`id`),
  FOREIGN KEY (`juegos_id`) REFERENCES `juegos`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET = utf8;
