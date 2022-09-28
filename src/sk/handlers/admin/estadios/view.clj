(ns sk.handlers.admin.estadios.view
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
     :prompt "Nombre del estadio..."
     :data-options "label:'Nombre:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "que_grande"
     :name "que_grande"
     :class "easyui-textbox"
     :prompt "Que tan grande esta?"
     :data-options "label:'Grande:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "donde_esta"
     :name "donde_esta"
     :class "easyui-textbox"
     :prompt "Donde esta el estadio..."
     :data-options "label:'Donde Esta:',
        labelPosition:'top',
        required:true,
        width:'100%'"})))

(defn estadios-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/estadios"
    (list
     [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
     [:th {:data-options "field:'que_grande',sortable:true,width:100"} "QUE_GRANDE"]
     [:th {:data-options "field:'donde_esta',sortable:true,width:100"} "DONDE_ESTA"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn estadios-scripts []
  (include-js "/js/grid.js"))
