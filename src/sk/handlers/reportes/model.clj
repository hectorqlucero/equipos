(ns sk.handlers.reportes.model
  (:require [sk.models.crud :refer [Query db]]))

(defn get-rows [tabla]
  (Query db [(str "SELECT * FROM " tabla)]))

;; Start Crossjoin to calculate percentage
(def p-sql
  "
  SELECT
  p_puntos,
  p_puntos * 100 / t.s AS `percent of total`
  FROM historial
  CROSS JOIN (SELECT SUM(p_puntos) AS s FROM historial) t
  WHERE jugadores_id = 21;
  ")
;; End Crossjoin to calculate percentage

;; Start totales-historial
(def totales-historial-sql
  "
  SELECT
  CAST(SUM(p_puntos) as UNSIGNED) as puntos,
  CAST(SUM(p_asistencias) as UNSIGNED) as asistencias,
  CAST(SUM(p_bloqueos) as UNSIGNED) as bloqueos,
  CAST(SUM(p_robos) as UNSIGNED) as robos,
  CAST(SUM(pe_2puntos) as UNSIGNED) as 2puntos,
  CAST(SUM(pe_3puntos) as UNSIGNED) as 3puntos
  FROM historial
  ")

(defn totales-historial []
  (let [row (first (Query db totales-historial-sql))]
    row))
;; End totales-historial

;; Start totales por jugador
(def totales-jugador-historial-sql
  "
  SELECT
  CAST(SUM(p_puntos) as UNSIGNED) as puntos,
  CAST(SUM(p_asistencias) as UNSIGNED) as asistencias,
  CAST(SUM(p_bloqueos) as UNSIGNED) as bloqueos,
  CAST(SUM(p_robos) as UNSIGNED) as robos,
  CAST(SUM(pe_2puntos) as UNSIGNED) as 2puntos,
  CAST(SUM(pe_3puntos) as UNSIGNED) as 3puntos
  FROM historial
  WHERE
  jugadores_id = ?
  ")

(defn jugador-totales-historial [jugador-id]
  (let [row (first (Query db [totales-jugador-historial-sql jugador-id]))]
    row))
;; End totales por jugador

;; Start promedios-historial
(def promedios-historial-sql
  "
  SELECT
  CAST(AVG(p_puntos) as UNSIGNED) as puntos,
  CAST(AVG(p_asistencias) as UNSIGNED) as asistencias,
  CAST(AVG(p_bloqueos) as UNSIGNED) as bloqueos,
  CAST(AVG(p_robos) as UNSIGNED) as robos,
  CAST(AVG(pe_2puntos) as UNSIGNED) as 2puntos,
  CAST(AVG(pe_3puntos) as UNSIGNED) as 3puntos
  FROM historial
  ")

(defn promedios-historial []
  (let [row (first (Query db promedios-historial-sql))]
    row))
;; End promedios-historial

;; Start promedio puntos por jugador
(def promedios-jugador-historial-sql
  "
  SELECT
  CAST(AVG(p_puntos) as UNSIGNED) as puntos,
  CAST(AVG(p_asistencias) as UNSIGNED) as asistencias,
  CAST(AVG(p_bloqueos) as UNSIGNED) as bloqueos,
  CAST(AVG(p_robos) as UNSIGNED) as robos,
  CAST(AVG(pe_2puntos) as UNSIGNED) as 2puntos,
  CAST(AVG(pe_3puntos) as UNSIGNED) as 3puntos
  FROM historial
  WHERE
  jugadores_id = ?
  ")

(defn jugador-promedios-historial [jugador-id]
  (let [row (first (Query db [promedios-jugador-historial-sql jugador-id]))]
    row))
;; End promedio puntos por jugador

;; Start max puntos generales
(def max-historial-sql
  "
  SELECT
  CAST(MAX(p_puntos) as UNSIGNED) as puntos,
  CAST(MAX(p_asistencias) as UNSIGNED) as asistencias,
  CAST(MAX(p_bloqueos) as UNSIGNED) as bloqueos,
  CAST(MAX(p_robos) as UNSIGNED) as robos,
  CAST(MAX(pe_2puntos) as UNSIGNED) as 2puntos,
  CAST(MAX(pe_3puntos) as UNSIGNED) as 3puntos
  FROM historial
  ")

(defn max-historial []
  (let [row (first (Query db max-historial-sql))]
    row))
;; End max puntos generales

;; Start min puntos generales
(def min-historial-sql
  "
  SELECT
  CAST(MIN(CASE p_puntos WHEN 0 THEN 1 END) as UNSIGNED) as puntos,
  CAST(MIN(CASE p_asistencias WHEN 0 THEN 1 END) as UNSIGNED) as asistencias,
  CAST(MIN(CASE p_bloqueos WHEN 0 THEN 1 END) as UNSIGNED) as bloqueos,
  CAST(MIN(CASE p_robos WHEN 0 THEN 1 END) as UNSIGNED) as robos,
  CAST(MIN(CASE pe_2puntos WHEN 0 THEN 1 END) as UNSIGNED) as 2puntos,
  CAST(MIN(CASE pe_3puntos WHEN 0 THEN 1 END) as UNSIGNED) as 3puntos
  FROM historial
  ")

(defn min-historial []
  (let [row (first (Query db min-historial-sql))]
    row))
;; End min puntos generales

(comment
  (min-historial)
  (max-historial)
  (promedios-historial)
  (get-rows "historial")
  (totales-historial)
  (jugador-totales-historial 11)
  (jugador-promedios-historial 11))
