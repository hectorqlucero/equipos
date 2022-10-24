(ns sk.handlers.juegos.model
  (:require [sk.models.crud :refer [Query db]]))

(defn get-rows [tabla]
  (Query db [(str "SELECT juegos.*,DATE_FORMAT(juegos.fecha,'%m/%d/%Y') AS fecha_formatted FROM " tabla)]))

(comment
  (get-rows "juegos"))
