(ns sk.layout
  (:require [clj-time.core :as t]
            [hiccup.page :refer [html5 include-css include-js]]
            [sk.models.util :refer [user-level user-name]]
            [sk.migrations :refer [config]]))

(defn build-reportes []
  (list
   [:a.dropdown-item {:href "/totales/historicos"} "Totales Historicos"]
   [:a.dropdown-item {:href "/promedios/historicos"} "Promedios Historicos"]
   [:a.dropdown-item {:href "/max/historicos"} "Max/Min Historicos"]
   (when (or
          (= (user-level) "A")
          (= (user-level) "S"))
     (list
      nil))
   (when (= (user-level) "S")
     nil)))

(defn build-admin []
  (list
   (when (or
          (= (user-level) "A")
          (= (user-level) "S"))
     (list
      [:a.dropdown-item {:href "/admin/entrenadores"} "Entrenadores"]
      [:a.dropdown-item {:href "/admin/estadios"} "Estadios"]
      [:a.dropdown-item {:href "/admin/equipos"} "Equipos"]
      [:a.dropdown-item {:href "/admin/posiciones"} "Posiciones"]
      [:a.dropdown-item {:href "/admin/jugadores"} "Jugadores"]
      [:a.dropdown-item {:href "/admin/temporadas"} "Temporadas"]
      [:a.dropdown-item {:href "/admin/juegos"} "Juegos"]))
   (when (= (user-level) "S")
     [:a.dropdown-item {:href "/admin/users"} "Usuarios"])))

(defn menus-private []
  (list
   [:nav.navbar.navbar-expand-md.navbar-dark.bg-dark.fixed-top
    [:a.navbar-brand {:href "/"}
     [:img.rounded-circle {:src "/images/logo.png"
                           :alt (:site-name config)
                           :style "width:40px;"}]]
    [:button.navbar-toggler {:type "button"
                             :data-toggle "collapse"
                             :data-target "#collapsibleNavbar"}
     [:span.navbar-toggler-icon]]
    [:div#collapsibleNavbar.collapse.navbar-collapse
     [:ul.navbar-nav
      [:li.nav-item [:a.nav-link {:href "/entrenadores"} "Entrenadores"]]
      [:li.nav-item [:a.nav-link {:href "/estadios"} "Estadios"]]
      [:li.nav-item [:a.nav-link {:href "/equipos"} "Equipos"]]
      [:li.nav-item [:a.nav-link {:href "/jugadores"} "Jugadores"]]
      [:li.nav-item [:a.nav-link {:href "/juegos"} "Juegos"]]
      (when
       (or
        (= (user-level) "U")
        (= (user-level) "A")
        (= (user-level) "S"))
        [:li.nav-item.dropdown
         [:a.nav-link.dropdown-toggle {:href "#"
                                       :id "navdrop"
                                       :data-toggle "dropdown"} "Reportes"]
         [:div.dropdown-menu
          (build-reportes)]])
      (when
       (or
        (= (user-level) "U")
        (= (user-level) "A")
        (= (user-level) "S"))
        [:li.nav-item.dropdown
         [:a.nav-link.dropdown-toggle {:href "#"
                                       :id "navdrop"
                                       :data-toggle "dropdown"} "Administrar"]
         [:div.dropdown-menu
          (build-admin)]])
      [:li.nav-item [:a.nav-link {:href "/home/logoff"} (str "Salir [" (user-name) "]")]]]]]))

(defn menus-public []
  (list
   [:nav.navbar.navbar-expand-md.navbar-dark.bg-dark.fixed-top
    [:a.navbar-brand {:href "/"}
     [:img.rounded-circle {:src "/images/logo.png"
                           :alt (:site-name config)
                           :style "width:40px;"}]]
    [:button.navbar-toggler {:type "button"
                             :data-toggle "collapse"
                             :data-target "#collapsibleNavbar"}
     [:span.navbar-toggler-icon]]
    [:div#collapsibleNavbar.collapse.navbar-collapse
     [:ul.navbar-nav
      [:li.nav-item [:a.nav-link {:href "/home/login"} "Conectar"]]]]]))

(defn menus-none []
  (list
   [:nav.navbar.navbar-expand-md.navbar-dark.bg-dark.fixed-top
    [:a.navbar-brand {:href "/"}
     [:img.rounded-circle {:src "/images/logo.png"
                           :alt (:site-name config)
                           :style "width:40px;"}]]
    [:button.navbar-toggler {:type "button"
                             :data-toggle "collapse"
                             :data-target "#collapsibleNavbar"}
     [:span.navbar-toggler-icon]]
    [:div#collapsibleNavbar.collapse.navbar-collapse]]))

(defn app-css []
  (list
   (include-css "/bootstrap/css/bootstrap.min.css")
   (include-css "/bootstrap/css/lumen.min.css")
   (include-css "https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css")
   (include-css "/bxslider/dist/jquery.bxslider.min.css")
   (include-css "/easyui/themes/gray/easyui.css")
   (include-css "/easyui/themes/icon.css")
   (include-css "/easyui/themes/color.css")
   (include-css "/css/main.css")
   (include-css "/RichText/src/richtext.min.css")))

(defn app-js []
  (list
   (include-js "/easyui/jquery.min.js")
   (include-js "/popper/popper.min.js")
   (include-js "/bxslider/dist/jquery.bxslider.min.js")
   (include-js "/bootstrap/js/bootstrap.min.js")
   (include-js "/easyui/jquery.easyui.min.js")
   (include-js "/easyui/jquery.edatagrid.js")
   (include-js "/easyui/datagrid-detailview.js")
   (include-js "/easyui/datagrid-groupview.js")
   (include-js "/easyui/datagrid-bufferview.js")
   (include-js "/easyui/datagrid-scrollview.js")
   (include-js "/easyui/datagrid-filter.js")
   (include-js "/easyui/locale/easyui-lang-es.js")
   (include-js "/RichText/src/jquery.richtext.min.js")
   (include-js "/js/main.js")))

(defn application [title ok js & content]
  (html5 {:ng-app (:site-name config) :lang "es"}
         [:head
          [:title (if title
                    title
                    (:site-name config))]
          [:meta {:charset "UTF-8"}]
          [:meta {:name "viewport"
                  :content "width=device-width, initial-scale=1"}]
          (app-css)
          [:link {:rel "shortcut icon"
                  :type "image/x-icon"
                  :href "data:image/x-icon;,"}]]
         [:body {:style "width:100vw;height:98vh;border:1px solid #000;"}
          [:div.container {:style "height:88vh;margin-top:75px;"}
           (cond
             (= ok -1) (menus-none)
             (= ok 0) (menus-public)
             (> ok 0) (menus-private))
           [:div.easyui-panel {:data-options "fit:true,border:false" :style "padding-left:14px;"} content]]
          (app-js)
          js]
         [:footer.bg-secondary.text-center.fixed-bottom
          [:span  "Copyright &copy" (t/year (t/now)) " Lucero Systems - All Rights Reserved"]]))

(defn error-404 [content return-url]
  (html5 {:ng-app (:site-name config) :lang "es"}
         [:head
          [:title "Mesaje"]
          [:meta {:charset "UTF-8"}]
          [:meta {:name "viewport"
                  :content "width=device-width, initial-scale=1"}]
          (app-css)
          [:link {:rel "shortcut iconcompojure"
                  :type "image/x-icon"
                  :href "data:image/x-icon;,"}]]
         [:body {:style "width:100vw;height:98vh;border:1px solid #000;"}
          [:div.container {:style "height:88vh;margin-top:75px;"}
           (menus-none)
           [:div.easyui-panel {:data-options "fit:true,border:false" :style "padding-left:14px;"}
            [:div
             [:p [:h3 [:b "Mensaje: "]] content]
             [:p [:h3 [:a {:href return-url} "Clic aqui para " [:strong "Continuar"]]]]]]]

          (app-js)
          nil]
         [:footer.bg-secondary.text-center.fixed-bottom
          [:span  "Copyright &copy" (t/year (t/now)) " Lucero Systems - All Rights Reserved"]]))
