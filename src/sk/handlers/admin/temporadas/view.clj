(ns sk.handlers.admin.temporadas.view
  (:require
   [hiccup.page :refer [include-js]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   [sk.models.util :refer
    [build-dialog build-dialog-buttons build-field build-table build-toolbar]]))

(defn dialog-fields []
  (list
   (build-field
    {:id "id"
     :name "id"
     :type "hidden"})
   (build-field
    {:id "nombre"
     :name "nombre"
     :class "easyui-textbox"
     :prompt "Temporada ej. 2021 etc..."
     :data-options "label:'Temporada:',
        labelPosition:'top',
        required:true,
        width:'100%'"})))

(defn temporadas-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/temporadas"
    (list
     [:th {:data-options "field:'id',sortable:true,width:100"} "ID"]
     [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn temporadas-scripts []
  (include-js "/js/grid.js"))
