(ns sk.handlers.entrenadores.view
  (:require [sk.handlers.entrenadores.model :refer [get-rows]]))

(defn my-body [row]
  [:tr
   [:td (:nombre row)]
   [:td (:paterno row)]
   [:td (:materno row)]
   [:td (:empezo_formatted row)]])

(defn entrenadores-view [title]
  (let [rows (get-rows "entrenadores")]
    [:table.dg {:data-options "remoteSort:false,fit:true,rownumbers:true,fitColumns:true" :title title}
     [:thead
      [:tr
       [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
       [:th {:data-options "field:'paterno',sortable:true,width:100"} "PATERNO"]
       [:th {:data-options "field:'materno',sortable:true,width:100"} "MATERNO"]
       [:th {:data-options "field:'empezo_formatted',sortable:true,width:100"} "EMPEZO"]]]
     [:tbody (map my-body rows)]]))

(defn entrenadores-scripts []
  [:script
   "
     var dg = $('.dg');
     $(document).ready(function() {
      dg.datagrid();
      dg.datagrid('enableFilter');
     });
     "])
