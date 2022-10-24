(ns sk.handlers.juegos.handler
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as java-io]
   [ring.util.io :refer [piped-input-stream]]
   [hiccup.core :refer [html]]
   [pdfkit-clj.core :refer [as-stream gen-pdf]]
   [clj-pdf.core :refer [pdf template]]
   [sk.layout :refer [application]]
   [sk.models.util :refer [get-session-id user-level]]
   [sk.handlers.juegos.model :refer [get-rows]]
   [sk.handlers.juegos.view :refer [juegos-view juegos-scripts historial-view]]))

(defn juegos [_]
  (let [title "Juegos"
        ok (get-session-id)
        js (juegos-scripts)
        content (juegos-view title)]
    (application title ok js content)))

(defn juegos-reporte [_]
  (let [title "Juegos"
        ok (get-session-id)
        js nil
        content (html (juegos-view title))]
    (if
     (or
      (= (user-level) "U")
      (= (user-level) "A")
      (= (user-level) "S"))
      {:status 200
       :headers {"Content-Type" "application/pdf"
                 "Content-Disposition" "attachment;filename='juegos.pdf'"}
       :body (as-stream (gen-pdf content))}
      (application title ok js "Solo miembros pueden accessar esta opción!!!"))))

(def juegos-pdf-template
  (template
   (list
    [:cell {:align :left} (str $nombre)]
    [:cell {:align :left} (str $fecha)]
    [:cell {:align :left} (str $temporadas_id)]
    [:cell {:align :left} (str $equipo_casa)]
    [:cell {:align :left} (str $equipo_fuera)])))

(defn generate-report-header []
  [{:background-color [233 233 233]}
   [:paragraph {:align :left} "NOMBRE"]
   [:paragraph {:align :left} "FECHA"]
   [:paragraph {:align :left} "TEMPORADAS_ID"]
   [:paragraph {:align :left} "EQUIPO_CASA"]
   [:paragraph {:align :left} "EQUIPO_FUERA"]])

(defn generate-report-body []
  (let [rows (get-rows "juegos")]
    (into
     [:table
      {:cell-border true
       :style :normal
       :size 10
       :border true
       :header (generate-report-header)}]
     (juegos-pdf-template rows))))

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

(defn juegos-pdf [_]
  (let [title "Juegos"
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
                 "Content-Disposition" "attachment;filename='juegos'"}
       :body (generate-report title)}
      (application title ok js content))))

(def juegos-csv-headers
  ["NOMBRE " "FECHA " "TEMPORADAS_ID " "EQUIPO_CASA " "EQUIPO_FUERA "])

(def juegos-csv-template
  (template
   [(str $nombre) (str $fecha) (str $temporadas_id) (str $equipo_casa) (str $equipo_fuera)]))

(defn build-csv [filename]
  (let [rows (get-rows "juegos")]
    (with-open [writer (java-io/writer filename)]
      (csv/write-csv writer (cons (vec juegos-csv-headers) (juegos-csv-template rows))))))

(defn juegos-csv [_]
  (build-csv "juegos.csv")
  (let [filename "juegos.csv"
        my-file (slurp filename)]
    (java-io/delete-file filename)
    {:status 200
     :headers {"Content-Type" "text/csv"
               "Content-Disposition" "attachment;filename=juegos.csv"}
     :body my-file}))

(defn juegos-jugadores [juegos-id equipo-casa equipo-fuera]
  (let [content (historial-view juegos-id equipo-casa equipo-fuera)]
    content))
