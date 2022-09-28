(ns sk.handlers.entrenadores.model
  (:require [sk.models.crud :refer [Query db]]))

(defn get-rows [tabla]
  (Query db [(str "select
                  nombre,
                  paterno,
                  materno,
                  empezo,
                  DATE_FORMAT(empezo,'%m/%d/%Y') as empezo_formatted
                  from " tabla)]))

(comment
  (get-rows "entrenadores"))
