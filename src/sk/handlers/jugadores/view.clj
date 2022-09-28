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
   [:td (:peso row)]
   [:td (:p_puntos row)]
   [:td (:p_assistencias row)]
   [:td (:p_bloqueos row)]
   [:td (:p_robos row)]
   [:td (:pe_2puntos row)]
   [:td (:pe_3puntos row)]])

(defn jugadores-view [title]
  (let [rows (get-rows "jugadores")]
    [:table.dg {:data-options "remoteSort:false,fit:true,rownumbers:true,fitColumns:true" :title title}
     [:thead
      [:tr
       [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
       [:th {:data-options "field:'paterno',sortable:true,width:100"} "PATERNO"]
       [:th {:data-options "field:'materno',sortable:true,width:100"} "MATERNO"]
       [:th {:data-options "field:'equipo',sortable:true,width:100"} "EQUIPO"]
       [:th {:data-options "field:'posicion',sortable:true,width:100"} "POSICION"]
       [:th {:data-options "field:'altura',sortable:true,width:100"} "ALTURA"]
       [:th {:data-options "field:'peso',sortable:true,width:100"} "PESO"]
       [:th {:data-options "field:'p_puntos',sortable:true,width:100"} "P PUNTOS"]
       [:th {:data-options "field:'p_assistencias',sortable:true,width:100"} "P ASSISTENCIAS"]
       [:th {:data-options "field:'p_bloqueos',sortable:true,width:100"} "P BLOQUEOS"]
       [:th {:data-options "field:'p_robos',sortable:true,width:100"} "P ROBOS"]
       [:th {:data-options "field:'pe_2puntos',sortable:true,width:100"} "% 2PUNTOS"]
       [:th {:data-options "field:'pe_3puntos',sortable:true,width:100"} "% 3PUNTOS"]]]
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
