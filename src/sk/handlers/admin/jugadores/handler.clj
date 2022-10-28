(ns sk.handlers.admin.jugadores.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.jugadores.view :refer [jugadores-view jugadores-scripts]]))

(defn jugadores [_]
  (let [title "Jugadores"
        ok (get-session-id)
        js (jugadores-scripts)
        content (jugadores-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn jugadores-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "jugadores"
        args {:sort-extra "equipos_id,nombre,paterno,materno"}]
    (build-grid params table args)))

(defn jugadores-form [id]
  (let [table "jugadores"]
    (build-form-row table id)))

(defn jugadores-save [{params :params}]
  (let [table "jugadores"]
    (build-form-save params table)))

(defn jugadores-delete [{params :params}]
  (let [table "jugadores"]
    (build-form-delete params table)))
