(ns sk.handlers.admin.entrenadores.view
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
     :prompt "Nombre del entrenador..."
     :data-options "label:'Nombre:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "paterno"
     :name "paterno"
     :class "easyui-textbox"
     :prompt "Apellido paterno del entrenador..."
     :data-options "label:'Paterno:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "materno"
     :name "materno"
     :class "easyui-textbox"
     :prompt "Apellido materno del entrenador..."
     :data-options "label:'Materno:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "empezo"
     :name "empezo"
     :class "easyui-datebox"
     :prompt "mm/dd/aaaa ex. 02/07/1957 es: Febreo 2 de 1957"
     :data-options "label:'Fecha de Inicio:',
        labelPosition:'top',
        required:false,
        width:'100%'"})))

(defn entrenadores-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/entrenadores"
    (list
     [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
     [:th {:data-options "field:'paterno',sortable:true,width:100"} "APELLIDO PATERNO"]
     [:th {:data-options "field:'materno',sortable:true,width:100"} "APELLIDO MATERNO"]
     [:th {:data-options "field:'empezo_formatted',sortable:true,width:100"} "INICIO"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn entrenadores-scripts []
  (include-js "/js/grid.js"))
