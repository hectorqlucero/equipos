(ns sk.handlers.admin.temporadas.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.temporadas.view :refer [temporadas-view temporadas-scripts]]))

(defn temporadas [_]
  (let [title "Temporadas"
        ok (get-session-id)
        js (temporadas-scripts)
        content (temporadas-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn temporadas-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "temporadas"
        args {:sort-extra "nombre"}]
    (build-grid params table args)))

(defn temporadas-form [id]
  (let [table "temporadas"]
    (build-form-row table id)))

(defn temporadas-save [{params :params}]
  (let [table "temporadas"]
    (build-form-save params table)))

(defn temporadas-delete [{params :params}]
  (let [table "temporadas"]
    (build-form-delete params table)))
