(ns sk.handlers.admin.equipos.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.equipos.view :refer [equipos-view equipos-scripts]]))

(defn equipos [_]
  (let [title "Equipos"
        ok (get-session-id)
        js (equipos-scripts)
        content (equipos-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn equipos-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "equipos"
        args {:sort-extra "nombre,ciudad"}]
    (build-grid params table args)))

(defn equipos-form [id]
  (let [table "equipos"]
    (build-form-row table id)))

(defn equipos-save [{params :params}]
  (let [table "equipos"]
    (build-form-save params table)))

(defn equipos-delete [{params :params}]
  (let [table "equipos"]
    (build-form-delete params table)))
