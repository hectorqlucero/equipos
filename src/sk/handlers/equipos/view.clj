(ns sk.handlers.equipos.view
  (:require [sk.handlers.equipos.model :refer [get-rows]]))

(defn my-body [row]
  [:tr
   [:td (:nombre row)]
   [:td (:fundado_formatted row)]
   [:td (:ciudad row)]
   [:td (:entrenador row)]
   [:td (:manager row)]
   [:td (:estadio row)]])

(defn equipos-view [title]
  (let [rows (get-rows "equipos")]
    [:table.dg {:data-options "remoteSort:false,fit:true,rownumbers:true,fitColumns:true" :title title}
     [:thead
      [:tr
       [:th {:colspan 2} [:a {:href "/equipos/csv"} "Exportar a hoja electronica"]]
       [:th {:colspan 2} [:a {:href "/equipos/pdf"} "Reporte PDF"]]
       [:th {:colspan 2} [:a {:href "/equipos/html"} "Reporte HTML"]]]
      [:tr
       [:th {:data-options "field:'nombre',sortable:true,width:100"} "NOMBRE"]
       [:th {:data-options "field:'fundado_formatted',sortable:true,width:100"} "FUNDADO"]
       [:th {:data-options "field:'ciudad',sortable:true,width:100"} "CIUDAD"]
       [:th {:data-options "field:'entrenador',sortable:true,width:100"} "ENTRENADOR"]
       [:th {:data-options "field:'manager',sortable:true,width:100"} "MANAGER"]
       [:th {:data-options "field:'estadio',sortable:true,width:100"} "ESTADIO"]]]
     [:tbody (map my-body rows)]]))

(defn equipos-scripts []
  [:script
   "
     var dg = $('.dg');
     $(document).ready(function() {
      dg.datagrid();
      dg.datagrid('enableFilter');
     });
     "])
