(ns sk.handlers.jugadores.view
  (:require [sk.handlers.jugadores.model :refer [get-rows]]))

(defn my-body [row]
  [:tr
   [:td (:nombre row)]
   [:td (:paterno row)]
   [:td (:materno row)]
   [:td (:equipo row)]
   [:td (:posicion row)]
   [:td (:altura row)]
   [:td (:peso row)]])

(defn jugadores-view [title]
  (let [rows (get-rows "jugadores")]
    [:table.dg {:data-options "remoteSort:false,fit:true,rownumbers:true,fitColumns:true" :title title}
     [:thead
      [:tr
       [:th {:colspan 3} [:a {:href "/jugadores/csv"} "Exportar a hoja electronica"]]
       [:th {:colspan 2} [:a {:href "/jugadores/pdf"} "Reporte PDF"]]
       [:th {:colspan 2} [:a {:href "/jugadores/html"} "Reporte HTML"]]]
      [:tr
       [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
       [:th {:data-options "field:'paterno',sortable:true,width:100"} "PATERNO"]
       [:th {:data-options "field:'materno',sortable:true,width:100"} "MATERNO"]
       [:th {:data-options "field:'equipo',sortable:true,width:100"} "EQUIPOS"]
       [:th {:data-options "field:'posicion',sortable:true,width:100"} "POSICION"]
       [:th {:data-options "field:'altura',sortable:true,width:100"} "ALTURA"]
       [:th {:data-options "field:'peso',sortable:true,width:100"} "PESO"]]]
     [:tbody (map my-body rows)]]))

(defn jugadores-scripts []
  [:script
   "
     var dg = $('.dg');
     $(document).ready(function() {
      dg.datagrid();
      dg.datagrid('enableFilter');
     });
     "])
