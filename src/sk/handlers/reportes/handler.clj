(ns sk.handlers.reportes.handler
  (:require
   [sk.handlers.reportes.view :refer [totales-view max-view]]
   [sk.layout :refer [application]]
   [sk.models.util :refer [get-session-id]]))

(defn totales-historial [_]
  (let [totales-title "Totales Historicos"
        promedios-title "Promedios Historicos"
        ok (get-session-id)
        js nil
        content (totales-view totales-title promedios-title)]
    (application totales-title ok js content)))

(defn max-historial [_]
  (let [max-title "Maximos Historicos"
        min-title "Minimos Historicos"
        ok (get-session-id)
        js nil
        content (max-view max-title min-title)]
    (application "Max/Min Totales" ok js content)))

(comment
  (totales-historial {}))
