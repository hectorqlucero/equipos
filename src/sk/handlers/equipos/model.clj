(ns sk.handlers.equipos.model
  (:require [sk.models.crud :refer [Query db]]))

(def get-rows-sql
  (str
   "
    SELECT
    e.nombre,
    e.fundado,
    DATE_FORMAT(e.fundado,'%m/%d/%Y') as fundado_formatted,
    e.ciudad,
    e.entrenador_id,
    CONCAT(n.nombre,' ',n.paterno,' ',n.materno) as entrenador,
    e.manager,
    e.estadios_id,
    s.nombre as estadio
    FROM equipos e
    LEFT JOIN entrenadores n ON n.id = e.entrenador_id
    LEFT JOIN estadios s on s.id = e.estadios_id
    ORDER BY e.nombre
    "))

(defn get-rows [tabla]
  (Query db [get-rows-sql]))

(comment
  (get-rows "equipos"))
