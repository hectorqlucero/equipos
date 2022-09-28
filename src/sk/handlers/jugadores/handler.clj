(ns sk.handlers.jugadores.handler
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as java-io]
   [ring.util.io :refer [piped-input-stream]]
   [hiccup.core :refer [html]]
   [pdfkit-clj.core :refer [as-stream gen-pdf]]
   [clj-pdf.core :refer [pdf template]]
   [sk.layout :refer [application]]
   [sk.models.util :refer [get-session-id user-level]]
   [sk.handlers.jugadores.model :refer [get-rows]]
   [sk.handlers.jugadores.view :refer [jugadores-view jugadores-scripts]]))

(defn jugadores [_]
  (let [title "Jugadores"
        ok (get-session-id)
        js (jugadores-scripts)
        content (jugadores-view title)]
    (application title ok js content)))

(defn jugadores-reporte [_]
  (let [title "Jugadores"
        ok (get-session-id)
        js nil
        content (html (jugadores-view title))]
    (if
     (or
      (= (user-level) "U")
      (= (user-level) "A")
      (= (user-level) "S"))
      {:status 200
       :headers {"Content-Type" "application/pdf"
                 "Content-Disposition" "attachment;filename='jugadores.pdf'"}
       :body (as-stream (gen-pdf content))}
      (application title ok js "Solo miembros pueden accessar esta opción!!!"))))

(def jugadores-pdf-template
  (template
   (list
    [:cell {:align :left} (str $nombre)]
    [:cell {:align :left} (str $paterno)]
    [:cell {:align :left} (str $materno)]
    [:cell {:align :left} (str $equipos_id)]
    [:cell {:align :left} (str $posicion)]
    [:cell {:align :left} (str $altura)]
    [:cell {:align :left} (str $peso)]
    [:cell {:align :left} (str $p_puntos)]
    [:cell {:align :left} (str $p_assistencias)]
    [:cell {:align :left} (str $p_bloqueos)]
    [:cell {:align :left} (str $p_robos)]
    [:cell {:align :left} (str $pe_2puntos)]
    [:cell {:align :left} (str $pe_3puntos)])))

(defn generate-report-header []
  [{:background-color [233 233 233]}
   [:paragraph {:align :left} "NOMBRE"]
   [:paragraph {:align :left} "PATERNO"]
   [:paragraph {:align :left} "MATERNO"]
   [:paragraph {:align :left} "EQUIPOS_ID"]
   [:paragraph {:align :left} "POSICION"]
   [:paragraph {:align :left} "ALTURA"]
   [:paragraph {:align :left} "PESO"]
   [:paragraph {:align :left} "P_PUNTOS"]
   [:paragraph {:align :left} "P_ASSISTENCIAS"]
   [:paragraph {:align :left} "P_BLOQUEOS"]
   [:paragraph {:align :left} "P_ROBOS"]
   [:paragraph {:align :left} "PE_2PUNTOS"]
   [:paragraph {:align :left} "PE_3PUNTOS"]])

(defn generate-report-body []
  (let [rows (get-rows "jugadores")]
    (into
     [:table
      {:cell-border true
       :style :normal
       :size 10
       :border true
       :header (generate-report-header)}]
     (jugadores-pdf-template rows))))

(defn generate-report-header-options [title]
  {:title title
   :header {:x 20
            :y 830
            :table
            [:pdf-table
             {:border false
              :width-percent 100}
             [100]
             [[:pdf-cell {:type :bold :size 16 :align :center} title]]]}
   :footer "page"
   :left-margin 10
   :right-margin 10
   :top-margin 10
   :bottom-margin 25
   :size :a4
   :orientation :portrait
   :font {:family :helvetica :size 10}
   :align :center
   :pages true})

(defn generate-report [title]
  (piped-input-stream
   (fn [output-stream]
     (pdf
      [(generate-report-header-options title)
       (generate-report-body)]
      output-stream))))

(defn jugadores-pdf [_]
  (let [title "Jugadores"
        ok (get-session-id)
        js nil
        content "Solo miembros pueden accessar esta opción!!!"]
    (if
     (or
      (= (user-level) "U")
      (= (user-level) "A")
      (= (user-level) "S"))
      {:status 200
       :headers {"Content-Type" "application/pdf"
                 "Content-Disposition" "attachment;filename='jugadores'"}
       :body (generate-report title)}
      (application title ok js content))))

(def jugadores-csv-headers
  ["NOMBRE " "PATERNO " "MATERNO " "EQUIPOS_ID " "POSICION " "ALTURA " "PESO " "P_PUNTOS " "P_ASSISTENCIAS " "P_BLOQUEOS " "P_ROBOS " "PE_2PUNTOS " "PE_3PUNTOS "])

(def jugadores-csv-template
  (template
   [(str $nombre) (str $paterno) (str $materno) (str $equipos_id) (str $posicion) (str $altura) (str $peso) (str $p_puntos) (str $p_assistencias) (str $p_bloqueos) (str $p_robos) (str $pe_2puntos) (str $pe_3puntos)]))

(defn build-csv [filename]
  (let [rows (get-rows "jugadores")]
    (with-open [writer (java-io/writer filename)]
      (csv/write-csv writer (cons (vec jugadores-csv-headers) (jugadores-csv-template rows))))))

(defn jugadores-csv [_]
  (build-csv "jugadores.csv")
  (let [filename "jugadores.csv"
        my-file (slurp filename)]
    (java-io/delete-file filename)
    {:status 200
     :headers {"Content-Type" "text/csv"
               "Content-Disposition" "attachment;filename=jugadores.csv"}
     :body my-file}))
