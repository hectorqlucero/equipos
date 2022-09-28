(ns sk.handlers.jugadores.model
  (:require [sk.models.crud :refer [Query db]]))

(defn get-rows [tabla]
  (Query db [(str "SELECT
                  j.nombre,
                  j.paterno,
                  j.materno,
                  j.equipos_id,
                  e.nombre as equipo,
                  j.posicion,
                  j.altura,
                  j.peso,
                  j.p_puntos,
                  j.p_assistencias,
                  j.p_bloqueos,
                  j.p_robos,
                  j.pe_2puntos,
                  j.pe_3puntos
                  FROM " tabla " j
                  LEFT JOIN equipos e ON e.id = j.equipos_id
                  ORDER BY 
                  e.nombre,j.nombre,j.paterno,j.materno
                  ")]))

(comment
  (get-rows "jugadores"))
