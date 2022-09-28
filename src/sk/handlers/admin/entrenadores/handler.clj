(ns sk.handlers.admin.entrenadores.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.entrenadores.view :refer [entrenadores-view entrenadores-scripts]]))

(defn entrenadores [_]
  (let [title "Entrenadores"
        ok (get-session-id)
        js (entrenadores-scripts)
        content (entrenadores-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn entrenadores-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "entrenadores"
        args {:sort-extra "nombre,paterno,materno"}]
    (build-grid params table args)))

(defn entrenadores-form [id]
  (let [table "entrenadores"]
    (build-form-row table id)))

(defn entrenadores-save [{params :params}]
  (let [table "entrenadores"]
    (build-form-save params table)))

(defn entrenadores-delete [{params :params}]
  (let [table "entrenadores"]
    (build-form-delete params table)))
