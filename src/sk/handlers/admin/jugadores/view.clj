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
     :prompt "Equipo al que pertenece el jugador..."
     :data-options "label:'Equipo:',
        labelPosition:'top',
        url:'/table_ref/get-equipos',
        method:'GET',
        required:true,
        width:'100%'"})
   (build-field
    {:id "posicion"
     :name "posicion"
     :class "easyui-textbox"
     :prompt "Posicion en la que juega..."
     :data-options "label:'Posicion:',
        labelPosition:'top',
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
        width:'100%'"})
   (build-field
    {:id "p_puntos"
     :name "p_puntos"
     :class "easyui-textbox"
     :prompt "Promedio de puntos del jugador..."
     :data-options "label:'Promedio de puntos:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "p_assistencias"
     :name "p_assistencias"
     :class "easyui-textbox"
     :prompt "Promedio de asistencias del jugador..."
     :data-options "label:'Promedio de asistencias:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "p_bloqueos"
     :name "p_bloqueos"
     :class "easyui-textbox"
     :prompt "Promedio de bloqueos del jugador..."
     :data-options "label:'Promedio de bloqueos:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "p_robos"
     :name "p_robos"
     :class "easyui-textbox"
     :prompt "Promedio de robos del jugador..."
     :data-options "label:'Promedio de robos:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "pe_2puntos"
     :name "pe_2puntos"
     :class "easyui-textbox"
     :prompt "Porcentage de 2 puntos del jugador..."
     :data-options "label:'Porcentage 2puntos:',
        labelPosition:'top',
        required:false,
        width:'100%'"})
   (build-field
    {:id "pe_3puntos"
     :name "pe_3puntos"
     :class "easyui-textbox"
     :prompt "Porcentage de 3puntos del jugador..."
     :data-options "label:'Porcentage 3puntos:',
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
     [:th {:data-options "field:'posicion',sortable:true,width:100"} "POSICION"]
     [:th {:data-options "field:'altura',sortable:true,width:100"} "ALTURA"]
     [:th {:data-options "field:'peso',sortable:true,width:100"} "PESO"]
     [:th {:data-options "field:'p_puntos',sortable:true,width:100"} "P PUNTOS"]
     [:th {:data-options "field:'p_assistencias',sortable:true,width:100"} "P ASSISTENCIAS"]
     [:th {:data-options "field:'p_bloqueos',sortable:true,width:100"} "P BLOQUEOS"]
     [:th {:data-options "field:'p_robos',sortable:true,width:100"} "P ROBOS"]
     [:th {:data-options "field:'pe_2puntos',sortable:true,width:100"} "% 2PUNTOS"]
     [:th {:data-options "field:'pe_3puntos',sortable:true,width:100"} "% 3PUNTOS"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn jugadores-scripts []
  (list
   (include-js "/js/grid.js")
   [:script
    "
   function get_equipo(val, row, index) {
    var result = null;
    var scriptUrl = '/table_ref/get-item/equipos/nombre/id/' + val;
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
