(ns sk.handlers.admin.estadios.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.estadios.view :refer [estadios-view estadios-scripts]]))

(defn estadios [_]
  (let [title "Estadios"
        ok (get-session-id)
        js (estadios-scripts)
        content (estadios-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn estadios-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "estadios"
        args {:sort-extra "nombre"}]
    (build-grid params table args)))

(defn estadios-form [id]
  (let [table "estadios"]
    (build-form-row table id)))

(defn estadios-save [{params :params}]
  (let [table "estadios"]
    (build-form-save params table)))

(defn estadios-delete [{params :params}]
  (let [table "estadios"]
    (build-form-delete params table)))
