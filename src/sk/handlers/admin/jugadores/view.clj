(ns sk.handlers.admin.jugadores.view
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
     :prompt "Nombre del jugador..."
     :data-options "label:'Nombre:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "paterno"
     :name "paterno"
     :class "easyui-textbox"
     :prompt "Apellido paterno del jugador..."
     :data-options "label:'Paterno:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "materno"
     :name "materno"
     :class "easyui-textbox"
     :prompt "Apellido materno del jugador..."
     :data-options "label:'Materno:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "equipos_id"
     :name "equipos_id"
     :class "easyui-combobox"
     :prompt "Equipo del jugador..."
     :data-options "label:'Equipo:',
                 labelPosition:'top',
                 url:'/table_ref/get-equipos',
                 method:'GET',
                 required:true,
                 width:'100%'"})
   (build-field
    {:id "posiciones_id"
     :name "posiciones_id"
     :class "easyui-combobox"
     :prompt "Posicion del jugador..."
     :data-options "label:'Posicion:',
        labelPosition:'top',
        url: '/table_ref/get-posiciones',
        method:'GET',
        required:true,
        width:'100%'"})
   (build-field
    {:id "altura"
     :name "altura"
     :class "easyui-textbox"
     :prompt "Altura del jugador..."
     :data-options "label:'Altura:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "peso"
     :name "peso"
     :class "easyui-textbox"
     :prompt "Peso del jugador..."
     :data-options "label:'Peso:',
        labelPosition:'top',
        required:false,
        width:'100%'"})))

(defn jugadores-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/jugadores"
    (list
     [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
     [:th {:data-options "field:'paterno',sortable:true,width:100"} "PATERNO"]
     [:th {:data-options "field:'materno',sortable:true,width:100"} "MATERNO"]
     [:th {:data-options "field:'equipos_id',sortable:true,width:100"
           :formatter "get_equipo"} "EQUIPO"]
     [:th {:data-options "field:'posicion_id',sortable:true,width:100"
           :formatter "get_posicion"} "POSICION"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn jugadores-scripts []
  (list
   (include-js "/js/grid.js")
   [:script
    "
   function get_equipo(val,row,index) {
    var result = null;
    var scriptUrl = '/table_ref/get-item/equipos/nombre/id/' + val;
    $.ajax({
      url:scriptUrl,
      type:'get',
      dataType:'html',
      async:false,
      success:function(data) {
        result = data;
      }
    });
    return result;
   }

   function get_posicion(val,row,index) {
    var result = null;
    var scriptUrl = '/table_ref/get-item/posiciones/nombre/id/' + val;
    $.ajax({
      url:scriptUrl,
      type:'get',
      dataType:'html',
      async:false,
      success:function(data) {
        result = data;
      }
    });
    return result;
   }
   "]))
