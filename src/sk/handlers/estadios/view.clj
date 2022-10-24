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
       [:th [:a {:href "/estadios/csv"} "Exportar a hoja electronica"]]
       [:th [:a {:href "/estadios/pdf"} "Reporte PDF"]]
       [:th [:a {:href "/estadios/html"} "Reporte HTML"]]]
      [:tr
       [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
       [:th {:data-options "field:'que_grande',sortable:true,width:100"} "TAMAÑO"]
       [:th {:data-options "field:'donde_esta',sortable:true,width:100"} "CIUDAD"]]]
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
