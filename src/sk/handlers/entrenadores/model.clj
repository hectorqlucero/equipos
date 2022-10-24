(ns sk.handlers.entrenadores.model
  (:require [sk.models.crud :refer [Query db]]))

(defn get-entrenadores-sql [tabla]
  (str
   "
  SELECT
  nombre,
  paterno,
  materno,
  empezo,
  DATE_FORMAT(empezo,'%m/%d/%Y') as empezo_formatted
  FROM " tabla "
  "))

(defn get-rows [tabla]
  (let [sql (get-entrenadores-sql tabla)]
    (Query db [sql])))

(comment
  (get-entrenadores-sql "entrenadores")
  (get-rows "entrenadores"))
