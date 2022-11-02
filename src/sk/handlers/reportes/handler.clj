(ns sk.handlers.reportes.handler
  (:require
   [sk.handlers.reportes.view :refer [totales-view promedios-view max-view]]
   [sk.layout :refer [application]]
   [sk.models.util :refer [get-session-id]]))

(defn totales-historial [_]
  (let [title "Totales Historicos"
        ok (get-session-id)
        js nil
        content (totales-view title)]
    (application title ok js content)))

(defn promedios-historial [_]
  (let [title "Promedios Historicos"
        ok (get-session-id)
        js nil
        content (promedios-view title)]
    (application title ok js content)))

(defn max-historial [_]
  (let [max-title "Maximos Historicos"
        min-title "Minimos Historicos"
        ok (get-session-id)
        js nil
        content (max-view max-title min-title)]
    (application "Max/Min Totales" ok js content)))

(comment
  (totales-historial {}))
