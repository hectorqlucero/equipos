(ns sk.handlers.admin.juegos.model
  (:require [sk.models.crud :refer [Query db]]))

(defn get-rows [tabla]
  (Query db [(str "select * from " tabla)]))

;; Start get-jugadores-por-equipo
(def get-jugadores-por-equipo-sql
  "
  SELECT
  jugadores.id,
  jugadores.equipos_id,
  jugadores.nombre,
  jugadores.paterno,
  jugadores.materno,
  posiciones.nombre as posicion
  FROM jugadores
  LEFT JOIN posiciones on posiciones.id = jugadores.posiciones_id
  WHERE jugadores.equipos_id = ?
  ORDER BY jugadores.nombre,jugadores.paterno,jugadores.materno
  ")

(defn get-jugadores-por-equipo [equipo-id juegos-id]
  (let [rows (Query db [get-jugadores-por-equipo-sql equipo-id])
        mod-rows (map #(assoc % :juegos_id juegos-id) rows)]
    mod-rows))
;; End get-jugadores-por-equipo

;; Start equipo-nombre
(defn equipo-nombre [id]
  (:nombre (first (Query db ["SELECT nombre FROM equipos WHERE id = ?" id]))))
;; End equipo-nombre

;; Start historial
(def historial-sql
  "
  SELECT *
  FROM historial
  WHERE
  jugadores_id = ?
  and equipos_id = ?
  and juegos_id = ?
  ")

(defn historial [jugadores-id equipos-id juegos-id]
  (let [row (Query db [historial-sql jugadores-id equipos-id juegos-id])]
    (first row)))
;; End historial

;; Start Crossjoin to calculate percentage
(def p-sql
  "
  SELECT
  p_puntos,
  p_puntos * 100 / t.s AS `percent of total`
  FROM historial
  CROSS JOIN (SELECT SUM(p_puntos) AS s FROM historial) t
  WHERE jugadores_id = 8;
  ")
;; End Crossjoin to calculate percentage
(comment
  (Query db "select SUM(p_puntos) as total from historial")
  (Query db p-sql)
  (Query db "select AVG(p_puntos) as promedio_puntos FROM historial where jugadores_id = 8")
  (historial 8 4 1)
  (equipo-nombre 4)
  (get-jugadores-por-equipo 4 1)
  (get-rows "juegos"))
