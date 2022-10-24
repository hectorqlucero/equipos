(ns sk.handlers.admin.posiciones.view
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
     :prompt "Posicion del jugador..."
     :data-options "label:'Posicion:',
        labelPosition:'top',
        required:true,
        width:'100%'"})))

(defn posiciones-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/posiciones"
    (list
     [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn posiciones-scripts []
  (include-js "/js/grid.js"))
