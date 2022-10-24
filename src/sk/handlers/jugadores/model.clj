(ns sk.handlers.jugadores.model
  (:require [sk.models.crud :refer [Query db]]))

(defn get-jugadores-sql [tabla]
  (str
   "
    select 
    j.*,
    e.nombre as equipo,
    p.nombre as posicion
    from " tabla " j
    LEFT JOIN equipos e ON e.id = j.equipos_id
    LEFT JOIN posiciones p ON p.id = j.posiciones_id
    ORDER BY j.nombre,j.paterno,j.materno
    "))

(defn get-rows [tabla]
  (Query db [(get-jugadores-sql tabla)]))

(comment
  (get-rows "jugadores"))
