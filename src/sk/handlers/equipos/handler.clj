(ns sk.handlers.equipos.handler
  (:require
   [clojure.data.csv :as csv]
   [clojure.java.io :as java-io]
   [ring.util.io :refer [piped-input-stream]]
   [hiccup.core :refer [html]]
   [pdfkit-clj.core :refer [as-stream gen-pdf]]
   [clj-pdf.core :refer [pdf template]]
   [sk.layout :refer [application]]
   [sk.models.util :refer [get-session-id user-level]]
   [sk.handlers.equipos.model :refer [get-rows]]
   [sk.handlers.equipos.view :refer [equipos-view equipos-scripts]]))

(defn equipos [_]
  (let [title "Equipos"
        ok (get-session-id)
        js (equipos-scripts)
        content (equipos-view title)]
    (application title ok js content)))

(defn equipos-reporte [_]
  (let [title "Equipos"
        ok (get-session-id)
        js nil
        content (html (equipos-view title))]
    (if
     (or
      (= (user-level) "U")
      (= (user-level) "A")
      (= (user-level) "S"))
      {:status 200
       :headers {"Content-Type" "application/pdf"
                 "Content-Disposition" "attachment;filename='equipos.pdf'"}
       :body (as-stream (gen-pdf content))}
      (application title ok js "Solo miembros pueden accessar esta opción!!!"))))

(def equipos-pdf-template
  (template
   (list
    [:cell {:align :left} (str $nombre)]
    [:cell {:align :left} (str $fundado)]
    [:cell {:align :left} (str $ciudad)]
    [:cell {:align :left} (str $entrenador_id)]
    [:cell {:align :left} (str $manager)]
    [:cell {:align :left} (str $estadios_id)])))

(defn generate-report-header []
  [{:background-color [233 233 233]}
   [:paragraph {:align :left} "NOMBRE"]
   [:paragraph {:align :left} "FUNDADO"]
   [:paragraph {:align :left} "CIUDAD"]
   [:paragraph {:align :left} "ENTRENADOR_ID"]
   [:paragraph {:align :left} "MANAGER"]
   [:paragraph {:align :left} "ESTADIOS_ID"]])

(defn generate-report-body []
  (let [rows (get-rows "equipos")]
    (into
     [:table
      {:cell-border true
       :style :normal
       :size 10
       :border true
       :header (generate-report-header)}]
     (equipos-pdf-template rows))))

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

(defn equipos-pdf [_]
  (let [title "Equipos"
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
                 "Content-Disposition" "attachment;filename='equipos'"}
       :body (generate-report title)}
      (application title ok js content))))

(def equipos-csv-headers
  ["NOMBRE " "FUNDADO " "CIUDAD " "ENTRENADOR_ID " "MANAGER " "ESTADIOS_ID "])

(def equipos-csv-template
  (template
   [(str $nombre) (str $fundado) (str $ciudad) (str $entrenador_id) (str $manager) (str $estadios_id)]))

(defn build-csv [filename]
  (let [rows (get-rows "equipos")]
    (with-open [writer (java-io/writer filename)]
      (csv/write-csv writer (cons (vec equipos-csv-headers) (equipos-csv-template rows))))))

(defn equipos-csv [_]
  (build-csv "equipos.csv")
  (let [filename "equipos.csv"
        my-file (slurp filename)]
    (java-io/delete-file filename)
    {:status 200
     :headers {"Content-Type" "text/csv"
               "Content-Disposition" "attachment;filename=equipos.csv"}
     :body my-file}))
