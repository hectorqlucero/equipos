(ns sk.proutes
  (:require [compojure.core :refer [defroutes GET POST]]
            [sk.handlers.admin.users.handler :as users]
            [sk.handlers.admin.entrenadores.handler :as entrenadores]
            [sk.handlers.entrenadores.handler :as entrenadores-r]
            [sk.handlers.admin.estadios.handler :as estadios]
            [sk.handlers.estadios.handler :as estadios-r]
            [sk.handlers.admin.equipos.handler :as equipos]
            [sk.handlers.equipos.handler :as equipos-r]
            [sk.handlers.admin.jugadores.handler :as jugadores]
            [sk.handlers.jugadores.handler :as jugadores-r]))

(defroutes proutes
  ;; Start users
  (GET "/admin/users"  req [] (users/users req))
  (POST "/admin/users" req [] (users/users-grid req))
  (GET "/admin/users/edit/:id" [id] (users/users-form id))
  (POST "/admin/users/save" req [] (users/users-save req))
  (POST "/admin/users/delete" req [] (users/users-delete req))
  ;; End users

  ;; Start entrenadores
  (GET "/admin/entrenadores"  req [] (entrenadores/entrenadores req))
  (POST "/admin/entrenadores" req [] (entrenadores/entrenadores-grid req))
  (GET "/admin/entrenadores/edit/:id" [id] (entrenadores/entrenadores-form id))
  (POST "/admin/entrenadores/save" req [] (entrenadores/entrenadores-save req))
  (POST "/admin/entrenadores/delete" req [] (entrenadores/entrenadores-delete req))
  ;; End entrenadores

  ;; Start entrenadores-r
  (GET "/entrenadores" req [] (entrenadores-r/entrenadores req))
  ;; End entrenadores-r

  ;; Start estadios
  (GET "/admin/estadios"  req [] (estadios/estadios req))
  (POST "/admin/estadios" req [] (estadios/estadios-grid req))
  (GET "/admin/estadios/edit/:id" [id] (estadios/estadios-form id))
  (POST "/admin/estadios/save" req [] (estadios/estadios-save req))
  (POST "/admin/estadios/delete" req [] (estadios/estadios-delete req))
  ;; End estadios

  ;; Start estadios-r
  (GET "/estadios" req [] (estadios-r/estadios req))
  ;; End estadios-r

  ;; Start equipos
  (GET "/admin/equipos"  req [] (equipos/equipos req))
  (POST "/admin/equipos" req [] (equipos/equipos-grid req))
  (GET "/admin/equipos/edit/:id" [id] (equipos/equipos-form id))
  (POST "/admin/equipos/save" req [] (equipos/equipos-save req))
  (POST "/admin/equipos/delete" req [] (equipos/equipos-delete req))
  ;; End equipos

  ;; Start equipos-r
  (GET "/equipos" req [] (equipos-r/equipos req))
  ;; Stop equipos-r

  ;; Start jugadores
  (GET "/admin/jugadores"  req [] (jugadores/jugadores req))
  (POST "/admin/jugadores" req [] (jugadores/jugadores-grid req))
  (GET "/admin/jugadores/edit/:id" [id] (jugadores/jugadores-form id))
  (POST "/admin/jugadores/save" req [] (jugadores/jugadores-save req))
  (POST "/admin/jugadores/delete" req [] (jugadores/jugadores-delete req))
  ;; End jugadores

  ;; Start jugadores-r
  (GET "/jugadores" req (jugadores-r/jugadores req))
  ;; End jugadores-r
  )
