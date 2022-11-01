(ns sk.handlers.reportes.view
  (:require [sk.handlers.reportes.model :refer [totales-historial promedios-historial max-historial min-historial]]))

(defn totales-view [title-totales title-promedios]
  (let [row (totales-historial)
        prow (promedios-historial)]
    [:div.container
     [:table.table.table-light.table-bordered.table-sm
      [:caption title-totales]
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
        [:td (:3puntos row)]]]]
     [:br]
     [:table.table.table-light.table-bordered.table-sm
      [:caption title-promedios]
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
        [:td (:puntos prow)]
        [:td (:asistencias prow)]
        [:td (:bloqueos prow)]
        [:td (:robos prow)]
        [:td (:2puntos prow)]
        [:td (:3puntos prow)]]]]]))

(defn max-view [title-max title-min]
  (let [row (max-historial)
        prow (min-historial)]
    [:div.container
     [:table.table.table-light.table-bordered.table-sm
      [:caption title-max]
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
        [:td (:3puntos row)]]]]
     [:br]
     [:table.table.table-light.table-bordered.table-sm
      [:caption title-min]
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
        [:td (:puntos prow)]
        [:td (:asistencias prow)]
        [:td (:bloqueos prow)]
        [:td (:robos prow)]
        [:td (:2puntos prow)]
        [:td (:3puntos prow)]]]]]))

(comment
  (totales-view "Totales"))
