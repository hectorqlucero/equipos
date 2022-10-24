(ns sk.handlers.admin.juegos.handler
  (:require [sk.models.crud :refer [Save db build-form-row build-form-save build-form-delete]]
            [hiccup.core :refer [html]]
            [cheshire.core :refer [generate-string]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.juegos.model :refer [get-jugadores-por-equipo equipo-nombre]]
            [sk.handlers.admin.juegos.view :refer [juegos-view juegos-scripts historial-view]]))

(defn juegos [_]
  (let [title "Juegos de Temporada"
        ok (get-session-id)
        js (juegos-scripts)
        content (juegos-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn juegos-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "juegos"
        args {:sort-extra "fecha"}]
    (build-grid params table args)))

(defn juegos-form [id]
  (let [table "juegos"]
    (build-form-row table id)))

(defn juegos-save [{params :params}]
  (let [table "juegos"]
    (build-form-save params table)))

(defn juegos-delete [{params :params}]
  (let [table "juegos"]
    (build-form-delete params table)))

(defn juegos-jugadores [juegos-id equipo-casa equipo-fuera]
  (let [content (historial-view juegos-id equipo-casa equipo-fuera)]
    content))

(defn historial [jugadores_id equipos_id juegos_id field valor]
  (let [postvars {:jugadores_id jugadores_id
                  :equipos_id equipos_id
                  :juegos_id juegos_id
                  (keyword field) valor}
        result (Save db :historial postvars ["jugadores_id = ? and equipos_id = ? and juegos_id = ?" jugadores_id equipos_id juegos_id])]
    (if (seq result)
      (generate-string {:message "Procesado correctamente!"})
      (generate-string {:message "Error: no se pudo procesar!"}))))
