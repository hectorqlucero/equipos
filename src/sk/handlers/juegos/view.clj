(ns sk.handlers.juegos.view
  (:require [sk.handlers.juegos.model :refer [get-rows]]
            [sk.handlers.admin.juegos.model :refer
             [get-jugadores-por-equipo equipo-nombre historial]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [include-js]]))

(defn my-body [row]
  [:tr
   [:td (:id row)]
   [:td (:nombre row)]
   [:td (:fecha_formatted row)]
   [:td (:temporadas_id row)]
   [:td (:equipo_casa row)]
   [:td (:equipo_fuera row)]])

(defn juegos-view [title]
  (let [rows (get-rows "juegos")]
    [:table.dg {:data-options "remoteSort:false,fit:true,rownumbers:true,fitColumns:true" :title title}
     [:thead
      [:tr
       [:th {:data-options "field:'id',sortable:true,width:100"} "ID"]
       [:th {:data-options "field:'nombre',sortable:true,width:100"} "JUEGO"]
       [:th {:data-options "field:'fecha_formatted',sortable:true,width:100"} "FECHA"]
       [:th {:data-options "field:'temporadas_id',sortable:true,width:100"
             :formatter "get_temporada"} "TEMPORADA"]
       [:th {:data-options "field:'equipo_casa',sortable:true,width:100"
             :formatter "get_equipo_casa"} "EQUIPO CASA"]
       [:th {:data-options "field:'equipo_fuera',sortable:true,width:100"
             :formatter "get_equipo_fuera"} "EQUIPO VISITANTE"]]]
     [:tbody (map my-body rows)]]))

(defn juegos-scripts []
  (list
   (include-js "/js/juegosView.js")
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
       [:td (or (:p_puntos historial-row) " ")]
       [:td (or (:p_asistencias historial-row) " ")]
       [:td (or (:p_bloqueos historial-row) " ")]
       [:td (or (:p_robos historial-row) " ")]
       [:td (or (:pe_2puntos historial-row) " ")]
       [:td (or (:pe_3puntos historial-row) " ")]]))))

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
        (map historial-body fuera-rows)]]])))
;; End historial-view

(comment
  (get-jugadores-por-equipo 4 1)
  historial 8 4 1)
