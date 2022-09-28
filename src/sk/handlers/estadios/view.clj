(ns sk.handlers.estadios.view
  (:require [sk.handlers.estadios.model :refer [get-rows]]))

(defn my-body [row]
  [:tr
   [:td (:nombre row)]
   [:td (:que_grande row)]
   [:td (:donde_esta row)]])

(defn estadios-view [title]
  (let [rows (get-rows "estadios")]
    [:table.dg {:data-options "remoteSort:false,fit:true,rownumbers:true,fitColumns:true" :title title}
     [:thead
      [:tr
       [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
       [:th {:data-options "field:'que_grande',sortable:true,width:100"} "QUE_GRANDE"]
       [:th {:data-options "field:'donde_esta',sortable:true,width:100"} "DONDE_ESTA"]]]
     [:tbody (map my-body rows)]]))

(defn estadios-scripts []
  [:script
   "
     var dg = $('.dg');
     $(document).ready(function() {
      dg.datagrid();
      dg.datagrid('enableFilter');
     });
     "])
