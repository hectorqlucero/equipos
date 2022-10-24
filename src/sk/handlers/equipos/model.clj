(ns sk.handlers.equipos.model
  (:require [sk.models.crud :refer [Query db]]))

(defn get-equipos-sql [tabla]
  (str
   "
    SELECT
    equipos.nombre,
    equipos.fundado,
    DATE_FORMAT(equipos.fundado,'%m/%d/%Y') as fundado_formatted,
    equipos.ciudad,
    equipos.entrenador_id,
    CONCAT(entrenadores.nombre,' ',entrenadores.paterno,' ',entrenadores.materno) as entrenador,
    equipos.manager,
    equipos.estadio_id,
    estadios.nombre as estadio
    FROM " tabla "
    LEFT JOIN entrenadores ON entrenadores.id = equipos.entrenador_id
    LEFT JOIN estadios ON estadios.id = equipos.estadio_id
    ORDER BY nombre
    "))

(defn get-rows [tabla]
  (Query db [(get-equipos-sql tabla)]))

(comment
  (get-rows "equipos"))
