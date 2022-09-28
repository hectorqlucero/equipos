(ns sk.handlers.entrenadores.handler
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as java-io]
   [ring.util.io :refer [piped-input-stream]]
   [hiccup.core :refer [html]]
   [pdfkit-clj.core :refer [as-stream gen-pdf]]
   [clj-pdf.core :refer [pdf template]]
   [sk.layout :refer [application]]
   [sk.models.util :refer [get-session-id user-level]]
   [sk.handlers.entrenadores.model :refer [get-rows]]
   [sk.handlers.entrenadores.view :refer [entrenadores-view entrenadores-scripts]]))

(defn entrenadores [_]
  (let [title "Entrenadores"
        ok (get-session-id)
        js (entrenadores-scripts)
        content (entrenadores-view title)]
    (application title ok js content)))

(defn entrenadores-reporte [_]
  (let [title "Entrenadores"
        ok (get-session-id)
        js nil
        content (html (entrenadores-view title))]
    (if
     (or
      (= (user-level) "U")
      (= (user-level) "A")
      (= (user-level) "S"))
      {:status 200
       :headers {"Content-Type" "application/pdf"
                 "Content-Disposition" "attachment;filename='entrenadores.pdf'"}
       :body (as-stream (gen-pdf content))}
      (application title ok js "Solo miembros pueden accessar esta opción!!!"))))

(def entrenadores-pdf-template
  (template
   (list
    [:cell {:align :left} (str $nombre)]
    [:cell {:align :left} (str $paterno)]
    [:cell {:align :left} (str $materno)]
    [:cell {:align :left} (str $empezo)])))

(defn generate-report-header []
  [{:background-color [233 233 233]}
   [:paragraph {:align :left} "NOMBRE"]
   [:paragraph {:align :left} "PATERNO"]
   [:paragraph {:align :left} "MATERNO"]
   [:paragraph {:align :left} "EMPEZO"]])

(defn generate-report-body []
  (let [rows (get-rows "entrenadores")]
    (into
     [:table
      {:cell-border true
       :style :normal
       :size 10
       :border true
       :header (generate-report-header)}]
     (entrenadores-pdf-template rows))))

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

(defn entrenadores-pdf [_]
  (let [title "Entrenadores"
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
                 "Content-Disposition" "attachment;filename='entrenadores'"}
       :body (generate-report title)}
      (application title ok js content))))

(def entrenadores-csv-headers
  ["NOMBRE " "PATERNO " "MATERNO " "EMPEZO "])

(def entrenadores-csv-template
  (template
   [(str $nombre) (str $paterno) (str $materno) (str $empezo)]))

(defn build-csv [filename]
  (let [rows (get-rows "entrenadores")]
    (with-open [writer (java-io/writer filename)]
      (csv/write-csv writer (cons (vec entrenadores-csv-headers) (entrenadores-csv-template rows))))))

(defn entrenadores-csv [_]
  (build-csv "entrenadores.csv")
  (let [filename "entrenadores.csv"
        my-file (slurp filename)]
    (java-io/delete-file filename)
    {:status 200
     :headers {"Content-Type" "text/csv"
               "Content-Disposition" "attachment;filename=entrenadores.csv"}
     :body my-file}))
