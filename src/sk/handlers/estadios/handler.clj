(ns sk.handlers.estadios.handler
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as java-io]
   [ring.util.io :refer [piped-input-stream]]
   [hiccup.core :refer [html]]
   [pdfkit-clj.core :refer [as-stream gen-pdf]]
   [clj-pdf.core :refer [pdf template]]
   [sk.layout :refer [application]]
   [sk.models.util :refer [get-session-id user-level]]
   [sk.handlers.estadios.model :refer [get-rows]]
   [sk.handlers.estadios.view :refer [estadios-view estadios-scripts]]))

(defn estadios [_]
  (let [title "Estadios"
        ok (get-session-id)
        js (estadios-scripts)
        content (estadios-view title)]
    (application title ok js content)))

(defn estadios-reporte [_]
  (let [title "Estadios"
        ok (get-session-id)
        js nil
        content (html (estadios-view title))]
    (if
     (or
      (= (user-level) "U")
      (= (user-level) "A")
      (= (user-level) "S"))
      {:status 200
       :headers {"Content-Type" "application/pdf"
                 "Content-Disposition" "attachment;filename='estadios.pdf'"}
       :body (as-stream (gen-pdf content))}
      (application title ok js "Solo miembros pueden accessar esta opción!!!"))))

(def estadios-pdf-template
  (template
   (list
    [:cell {:align :left} (str $nombre)]
    [:cell {:align :left} (str $que_grande)]
    [:cell {:align :left} (str $donde_esta)])))

(defn generate-report-header []
  [{:background-color [233 233 233]}
   [:paragraph {:align :left} "NOMBRE"]
   [:paragraph {:align :left} "QUE_GRANDE"]
   [:paragraph {:align :left} "DONDE_ESTA"]])

(defn generate-report-body []
  (let [rows (get-rows "estadios")]
    (into
     [:table
      {:cell-border true
       :style :normal
       :size 10
       :border true
       :header (generate-report-header)}]
     (estadios-pdf-template rows))))

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

(defn estadios-pdf [_]
  (let [title "Estadios"
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
                 "Content-Disposition" "attachment;filename='estadios'"}
       :body (generate-report title)}
      (application title ok js content))))

(def estadios-csv-headers
  ["NOMBRE " "QUE_GRANDE " "DONDE_ESTA "])

(def estadios-csv-template
  (template
   [(str $nombre) (str $que_grande) (str $donde_esta)]))

(defn build-csv [filename]
  (let [rows (get-rows "estadios")]
    (with-open [writer (java-io/writer filename)]
      (csv/write-csv writer (cons (vec estadios-csv-headers) (estadios-csv-template rows))))))

(defn estadios-csv [_]
  (build-csv "estadios.csv")
  (let [filename "estadios.csv"
        my-file (slurp filename)]
    (java-io/delete-file filename)
    {:status 200
     :headers {"Content-Type" "text/csv"
               "Content-Disposition" "attachment;filename=estadios.csv"}
     :body my-file}))
