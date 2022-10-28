(ns sk.handlers.reportes.view
  (:require [sk.handlers.reportes.model :refer [totales-historial]]))

(defn totales-view [title]
  (let [row (totales-historial)]
    [:div.container
     [:table.table.table-light.table-bordered.table-sm
      [:caption title]
      [:thead.thead-dark
       [:tr
        [:th "PUNTOS"]
        [:th "ASISTENCIAS"]
        [:th "BLOQUEOS"]
        [:th "ROBOS"]
        [:th "2 PUNTOS"]
        [:th "3 PUNTOS"]]]

      [:tbody
       [:tr
        [:td (:puntos row)]
        [:td (:asistencias row)]
        [:td (:bloqueos row)]
        [:td (:robos row)]
        [:td (:2puntos row)]
        [:td (:3puntos row)]]]]]))

(comment
  (totales-view "Totales"))
