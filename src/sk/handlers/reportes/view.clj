(ns sk.handlers.reportes.view
  (:require [sk.handlers.reportes.model :refer [totales-historial
                                                totales-equipos
                                                totales-jugadores
                                                promedios-historial
                                                promedios-equipos
                                                promedios-jugadores
                                                max-historial
                                                min-historial]]))

;; Start totales-view
(defn get-jugadores-body [equipos-id]
  (let [rows (totales-jugadores equipos-id)]
    (list
     (map (fn [row]
            (list
             [:tr
              [:td (:jugador row)]
              [:td (:puntos row)]
              [:td (:asistencias row)]
              [:td (:bloqueos row)]
              [:td (:robos row)]
              [:td (:2puntos row)]
              [:td (:3puntos row)]])) rows))))

(defn get-equipos-body [row]
  (list
   [:tr.table-primary
    [:td (:equipo row)]
    [:td (:puntos row)]
    [:td (:asistencias row)]
    [:td (:bloqueos row)]
    [:td (:robos row)]
    [:td (:2puntos row)]
    [:td (:3puntos row)]]
   [:tr
    [:td {:colspan 7}
     [:table.table.table-bordered.table-sm
      [:thead
       [:tr.table-active
        [:th "JUGADOR"]
        [:th "PUNTOS"]
        [:th "ASISTENCIAS"]
        [:th "BLOQUEOS"]
        [:th "ROBOS"]
        [:th "2 PUNTOS"]
        [:th "3 PUNTOs"]]]
      [:tbody
       (get-jugadores-body (:id row))]]]]))

(defn totales-view [title]
  (let [row (totales-historial)
        erows (totales-equipos)]
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
        [:td (:3puntos row)]]]]
     [:br]
     [:table.table.table-light.table-bordered.table-sm
      [:caption "Totales por equipo"]
      [:thead.thead-dark
       [:tr
        [:th "EQUIPO"]
        [:th "PUNTOS"]
        [:th "ASISTENCIAS"]
        [:th "BLOQUEOS"]
        [:th "ROBOS"]
        [:th "2 PUNTOS"]
        [:th "3 PUNTOS"]]]

      [:tbody
       [:tr
        (map get-equipos-body erows)]]]]))
;; End totales-view

;; Start promedios-view
(defn get-pjugadores-body [equipos-id]
  (let [rows (promedios-jugadores equipos-id)]
    (list
     (map (fn [row]
            (list
             [:tr
              [:td (:jugador row)]
              [:td (:puntos row)]
              [:td (:asistencias row)]
              [:td (:bloqueos row)]
              [:td (:robos row)]
              [:td (:2puntos row)]
              [:td (:3puntos row)]])) rows))))

(defn get-pequipos-body [row]
  (list
   [:tr.table-primary
    [:td (:equipo row)]
    [:td (:puntos row)]
    [:td (:asistencias row)]
    [:td (:bloqueos row)]
    [:td (:robos row)]
    [:td (:2puntos row)]
    [:td (:3puntos row)]]
   [:tr
    [:td {:colspan 7}
     [:table.table.table-bordered.table-sm
      [:thead
       [:tr.table-active
        [:th "JUGADOR"]
        [:th "PUNTOS"]
        [:th "ASISTENCIAS"]
        [:th "BLOQUEOS"]
        [:th "ROBOS"]
        [:th "2 PUNTOS"]
        [:th "3 PUNTOs"]]]
      [:tbody
       (get-pjugadores-body (:id row))]]]]))

(defn promedios-view [title]
  (let [row (promedios-historial)
        erows (promedios-equipos)]
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
        [:td (:3puntos row)]]]]
     [:br]
     [:table.table.table-light.table-bordered.table-sm
      [:caption "Promedios por equipo"]
      [:thead.thead-dark
       [:tr
        [:th "EQUIPO"]
        [:th "PUNTOS"]
        [:th "ASISTENCIAS"]
        [:th "BLOQUEOS"]
        [:th "ROBOS"]
        [:th "2 PUNTOS"]
        [:th "3 PUNTOS"]]]

      [:tbody
       [:tr
        (map get-pequipos-body erows)]]]]))
;; End promedios-view

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
