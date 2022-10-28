(ns sk.handlers.reportes.handler
  (:require
   [sk.handlers.reportes.view :refer [totales-view]]
   [sk.layout :refer [application]]
   [sk.models.util :refer [get-session-id]]))

(defn totales-historial [_]
  (let [title "Totales Historicos"
        ok (get-session-id)
        js nil
        content (totales-view title)]
    (application title ok js content)))

(comment
  (totales-historial {}))
