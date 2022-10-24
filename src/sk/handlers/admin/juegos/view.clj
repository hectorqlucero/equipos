(ns sk.handlers.admin.juegos.view
  (:require
   [hiccup.core :refer [html]]
   [sk.handlers.admin.juegos.model :refer
    [get-jugadores-por-equipo equipo-nombre historial]]
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
     :prompt "Descripcion o nombre del juego..."
     :data-options "label:'Descripcion:',
                 labelPosition:'top',
                 required:true,
                 width:'100%'"})
   (build-field
    {:id "temporadas_id"
     :name "temporadas_id"
     :class "easyui-combobox"
     :prompt "Aqui va la temporada ex. 2021 o Temporada Invierno 2021 etc..."
     :data-options "label:'Temporada:',
        labelPosition:'top',
        url:'/table_ref/get-temporadas',
        method:'GET',
        required:true,
        width:'100%'"})
   (build-field
    {:id "equipo_casa"
     :name "equipo_casa"
     :class "easyui-combobox"
     :data-options "label:'Equipo(Casa):',
        labelPosition:'top',
        url:'/table_ref/get-equipos',
        method:'GET',
        required:true,
        width:'100%'"})
   (build-field
    {:id "equipo_fuera"
     :name "equipo_fuera"
     :class "easyui-combobox"
     :data-options "label:'Equipo(Fuera):',
        labelPosition:'top',
        url:'/table_ref/get-equipos',
        method:'GET',
        required:true,
        width:'100%'"})
   (build-field
    {:id "fecha"
     :name "fecha"
     :class "easyui-datebox"
     :prompt "mm/dd/aaaa ex. 02/07/1957 es: Febreo 2 de 1957"
     :data-options "label:'Fecha:',
        labelPosition:'top',
        required:true,
        width:'100%'"})))

(defn juegos-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/juegos"
    (list
     [:th {:data-options "field:'id',sortable:true,width:100"} "ID"]
     [:th {:data-options "field:'nombre',sortable:true,width:100"} "DESC"]
     [:th {:data-options "field:'temporadas_id',sortable:true,width:100"
           :formatter "get_temporada"} "TEMPORADA"]
     [:th {:data-options "field:'equipo_casa',sortable:true,width:100"
           :formatter "get_equipo_casa"} "EQUIPO CASA"]
     [:th {:data-options "field:'equipo_fuera',sortable:true,width:100"
           :formatter "get_equipo_fuera"} "EQUIPO FUERA"]
     [:th {:data-options "field:'fecha_formatted',sortable:true,width:100"} "FECHA"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn juegos-scripts []
  (list
   (include-js "/js/juegosGrid.js")
   [:script
    "
   function get_temporada(val,row,index) {
    var result = null;
    var scriptUrl = '/table_ref/get-item/temporadas/nombre/id/' + val;
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

   function get_equipo_casa(val,row,index) {
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

   function get_equipo_fuera(val,row,index) {
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

;; Start historial-view
(defn historial-body [row]
  (let [jugadores-id (:id row)
        equipos-id (:equipos_id row)
        juegos-id (:juegos_id row)
        historial-row (historial jugadores-id equipos-id juegos-id)]
    (html
     (list
      [:tr {:align "center"}
       [:td (:nombre row)]
       [:td (:paterno row)]
       [:td (:materno row)]
       [:td (:posicion row)]
       [:td [:input {:name  "p_puntos"
                     :id "p_puntos"
                     :size "4"
                     :maxlength "4"
                     :value (str (:p_puntos historial-row))
                     :onblur (str "postValue(" jugadores-id "," equipos-id "," juegos-id ",'p_puntos',this.value)")}]]
       [:td [:input {:name "p_asistencias"
                     :id "p_asistencias"
                     :size "4"
                     :maxlength "4"
                     :value (:p_asistencias historial-row)
                     :onblur (str "postValue(" jugadores-id "," equipos-id "," juegos-id ",'p_asistencias',this.value)")}]]
       [:td [:input {:name "p_bloqueos"
                     :id "p_bloqueos"
                     :size "4"
                     :maxlength "4"
                     :value (:p_bloqueos historial-row)
                     :onblur (str "postValue(" jugadores-id "," equipos-id "," juegos-id ",'p_bloqueos',this.value)")}]]
       [:td [:input {:name "p_robos"
                     :id "p_robos"
                     :size "4"
                     :maxlength "4"
                     :value (:p_robos historial-row)
                     :onblur (str "postValue(" jugadores-id "," equipos-id "," juegos-id ",'p_robos',this.value)")}]]
       [:td [:input {:name "pe_2puntos"
                     :id "pe_2puntos"
                     :size "4"
                     :maxlength "4"
                     :value (:pe_2puntos historial-row)
                     :onblur (str "postValue(" jugadores-id "," equipos-id "," juegos-id ",'pe_2puntos',this.value)")}]]
       [:td [:input {:name "pe_3puntos"
                     :id "pe_3puntos"
                     :size "4"
                     :maxlength "4"
                     :value (:pe_3puntos historial-row)
                     :onblur (str "postValue(" jugadores-id "," equipos-id "," juegos-id ",'pe_3puntos',this.value)")}]]]))))

(defn historial-view [juegos-id equipo-casa equipo-fuera]
  (let [casa-rows (get-jugadores-por-equipo equipo-casa juegos-id)
        fuera-rows (get-jugadores-por-equipo equipo-fuera juegos-id)
        casa (equipo-nombre equipo-casa)
        fuera (equipo-nombre equipo-fuera)]
    (html
     [:div.container
      [:table.table.table-bordered.table-sm.table-striped
       [:thead
        [:tr
         [:th {:colspan 10
               :style "text-align:center;"} casa]]
        [:tr {:align "center"}
         [:th "Nombre"]
         [:th "Paterno"]
         [:th "Materno"]
         [:th "Posicion"]
         [:th "Puntos"]
         [:th "Asistencias"]
         [:th "Bloqueos"]
         [:th "Robos"]
         [:th "2 Puntos"]
         [:th "3 Puntos"]]]

       [:tbody
        (map historial-body casa-rows)]]
      [:br]
      [:table.table.table-bordered.table-sm.table-striped
       [:thead
        [:tr
         [:th {:colspan 10
               :style "text-align:center;"} fuera]]
        [:tr {:align "center"}
         [:th "Nombre"]
         [:th "Paterno"]
         [:th "Materno"]
         [:th "Posicion"]
         [:th "Puntos"]
         [:th "Asistencias"]
         [:th "Bloqueos"]
         [:th "Robos"]
         [:th "2 Puntos"]
         [:th "3 Puntos"]]]

       [:tbody
        (map historial-body fuera-rows)]]]
     [:br]
     [:script
      (str
       "
        function postValue(jugadores_id,equipos_id,juegos_id,field,valor) {
          $.get('/update/historial/'+jugadores_id+'/'+equipos_id+'/'+juegos_id+'/'+field+'/'+valor, function(data) {
            var dta = JSON.parse(data);
            $.messager.show({
              title: 'Estatus',
              msg: dta.message
            })
          })
        }
        ")])))
;; End historial-view

(comment
  (get-jugadores-por-equipo 3 1))
