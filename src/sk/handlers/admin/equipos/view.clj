(ns sk.handlers.admin.equipos.view
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
     :prompt "Nombre del equipo..."
     :data-options "label:'Nombre:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "fundado"
     :name "fundado"
     :class "easyui-datebox"
     :prompt "mm/dd/aaaa ex. 02/07/1957 es: Febreo 2 de 1957"
     :data-options "label:'Fundado:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "ciudad"
     :name "ciudad"
     :class "easyui-textbox"
     :prompt "Ciudad del equipo..."
     :data-options "label:'Ciudad:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "entrenador_id"
     :name "entrenador_id"
     :class "easyui-combobox"
     :prompt "Entrenador del equipo"
     :data-options "label:'Entrenador:',
        labelPosition:'top',
        url:'/table_ref/get-entrenadores',
        method:'GET',
        required:true,
        width:'100%'"})
   (build-field
    {:id "manager"
     :name "manager"
     :class "easyui-textbox"
     :prompt "Manager del equipo..."
     :data-options "label:'Manager:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "estadio_id"
     :name "estadio_id"
     :class "easyui-combobox"
     :prompt "Estadio del equipo..."
     :data-options "label:'Estadio:',
        labelPosition:'top',
        url:'/table_ref/get-estadios',
        method:'GET',
        required:true,
        width:'100%'"})))

(defn equipos-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/equipos"
    (list
     [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
     [:th {:data-options "field:'fundado_formatted',sortable:true,width:100"} "FUNDADO"]
     [:th {:data-options "field:'ciudad',sortable:true,width:100"} "CIUDAD"]
     [:th {:data-options "field:'entrenador_id',sortable:true,width:100"
           :formatter "get_entrenador"} "ENTRENADOR"]
     [:th {:data-options "field:'manager',sortable:true,width:100"} "MANAGER"]
     [:th {:data-options "field:'estadio_id',sortable:true,width:100"
           :formatter "get_estadio"} "ESTADIO"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn equipos-scripts []
  (list
   (include-js "/js/grid.js")
   [:script
    "
    function get_entrenador(val,row,index) {
      var result = null;
      var scriptUrl = '/table_ref/get-entrenador/' + val;
      $.ajax({
        url: scriptUrl,
        type: 'get',
        dataType: 'html',
        async: false,
        success: function(data) {
          result = data;
        }
      });
      return result
    }

    function get_estadio(val,row,index) {
      var result = null;
      var scriptUrl = '/table_ref/get-item/estadios/nombre/id/' + val;
      $.ajax({
        url: scriptUrl,
        type: 'get',
        dataType: 'html',
        async: false,
        success: function(data) {
          result = data;
        }
      });
      return result;
    }
     "]))
