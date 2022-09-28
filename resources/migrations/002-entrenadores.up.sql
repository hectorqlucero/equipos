CREATE TABLE entrenadores (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nombre` varchar(50) DEFAULT NULL,
  `paterno` varchar(50) DEFAULT NULL,
  `materno` varchar(50) DEFAULT NULL,
  `empezo` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
