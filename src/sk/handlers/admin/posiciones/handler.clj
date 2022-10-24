(ns sk.handlers.admin.posiciones.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.posiciones.view :refer [posiciones-view posiciones-scripts]]))

(defn posiciones [_]
  (let [title "Posiciones de jugadores"
        ok (get-session-id)
        js (posiciones-scripts)
        content (posiciones-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn posiciones-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "posiciones"
        args {:sort-extra "nombre"}]
    (build-grid params table args)))

(defn posiciones-form [id]
  (let [table "posiciones"]
    (build-form-row table id)))

(defn posiciones-save [{params :params}]
  (let [table "posiciones"]
    (build-form-save params table)))

(defn posiciones-delete [{params :params}]
  (let [table "posiciones"]
    (build-form-delete params table)))
