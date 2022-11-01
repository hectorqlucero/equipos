(ns sk.proutes
  (:require [compojure.core :refer [defroutes GET POST]]
            [sk.handlers.admin.users.handler :as users]
            [sk.handlers.admin.entrenadores.handler :as entrenadores]
            [sk.handlers.admin.estadios.handler :as estadios]
            [sk.handlers.admin.equipos.handler :as equipos]
            [sk.handlers.admin.jugadores.handler :as jugadores]
            [sk.handlers.entrenadores.handler :as entrenadores-r]
            [sk.handlers.estadios.handler :as estadios-r]
            [sk.handlers.equipos.handler :as equipos-r]
            [sk.handlers.jugadores.handler :as jugadores-r]
            [sk.handlers.admin.posiciones.handler :as posiciones]
            [sk.handlers.admin.juegos.handler :as juegos]
            [sk.handlers.admin.temporadas.handler :as temporadas]
            [sk.handlers.juegos.handler :as juegos-r]
            [sk.handlers.reportes.handler :as reportes]))

(defroutes proutes
  ;; Start reportes
  (GET "/totales/historicos" req [] (reportes/totales-historial req))
  (GET "/max/historicos" req [] (reportes/max-historial req))
  ;; End reportes
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

  ;; Start estadios
  (GET "/admin/estadios"  req [] (estadios/estadios req))
  (POST "/admin/estadios" req [] (estadios/estadios-grid req))
  (GET "/admin/estadios/edit/:id" [id] (estadios/estadios-form id))
  (POST "/admin/estadios/save" req [] (estadios/estadios-save req))
  (POST "/admin/estadios/delete" req [] (estadios/estadios-delete req))
  ;; End estadios

  ;; Start equipos
  (GET "/admin/equipos"  req [] (equipos/equipos req))
  (POST "/admin/equipos" req [] (equipos/equipos-grid req))
  (GET "/admin/equipos/edit/:id" [id] (equipos/equipos-form id))
  (POST "/admin/equipos/save" req [] (equipos/equipos-save req))
  (POST "/admin/equipos/delete" req [] (equipos/equipos-delete req))
  ;; End equipos

  ;; Start posiciones
  (GET "/admin/posiciones"  req [] (posiciones/posiciones req))
  (POST "/admin/posiciones" req [] (posiciones/posiciones-grid req))
  (GET "/admin/posiciones/edit/:id" [id] (posiciones/posiciones-form id))
  (POST "/admin/posiciones/save" req [] (posiciones/posiciones-save req))
  (POST "/admin/posiciones/delete" req [] (posiciones/posiciones-delete req))
  ;; End posiciones

  ;; Start jugadores
  (GET "/admin/jugadores"  req [] (jugadores/jugadores req))
  (POST "/admin/jugadores" req [] (jugadores/jugadores-grid req))
  (GET "/admin/jugadores/edit/:id" [id] (jugadores/jugadores-form id))
  (POST "/admin/jugadores/save" req [] (jugadores/jugadores-save req))
  (POST "/admin/jugadores/delete" req [] (jugadores/jugadores-delete req))
  ;; End jugadores

  ;; Start entrenadores-r
  (GET "/entrenadores" req [] (entrenadores-r/entrenadores req))
  (GET "/entrenadores/csv" req [] (entrenadores-r/entrenadores-csv req))
  (GET "/entrenadores/pdf" req [] (entrenadores-r/entrenadores-pdf req))
  (GET "/entrenadores/html" req [] (entrenadores-r/entrenadores-reporte req))
  ;; End entrenadores-r

  ;; Start estadios-r
  (GET "/estadios" req [] (estadios-r/estadios req))
  (GET "/estadios/csv" req [] (estadios-r/estadios-csv req))
  (GET "/estadios/pdf" req [] (estadios-r/estadios-pdf req))
  (GET "/estadios/html" req [] (estadios-r/estadios-reporte req))
  ;; End estadios-r

  ;; Start equipos-r
  (GET "/equipos" req [] (equipos-r/equipos req))
  (GET "/equipos/csv" req [] (equipos-r/equipos-csv req))
  (GET "/equipos/pdf" req [] (equipos-r/equipos-pdf req))
  (GET "/equipos/html" req [] (equipos-r/equipos-reporte req))
  ;; End equipos-r

  ;; Start jugadores-r
  (GET "/jugadores" req [] (jugadores-r/jugadores req))
  (GET "/jugadores/csv" req [] (jugadores-r/jugadores-csv req))
  (GET "/jugadores/pdf" req [] (jugadores-r/jugadores-pdf req))
  (GET "/jugadores/html" req [] (jugadores-r/jugadores-reporte req))
  ;; End jugadores-r

  ;; Start temporadas
  (GET "/admin/temporadas"  req [] (temporadas/temporadas req))
  (POST "/admin/temporadas" req [] (temporadas/temporadas-grid req))
  (GET "/admin/temporadas/edit/:id" [id] (temporadas/temporadas-form id))
  (POST "/admin/temporadas/save" req [] (temporadas/temporadas-save req))
  (POST "/admin/temporadas/delete" req [] (temporadas/temporadas-delete req))
  ;; End temporadas

  ;; Start juegos
  (GET "/admin/juegos"  req [] (juegos/juegos req))
  (POST "/admin/juegos" req [] (juegos/juegos-grid req))
  (GET "/admin/juegos/edit/:id" [id] (juegos/juegos-form id))
  (POST "/admin/juegos/save" req [] (juegos/juegos-save req))
  (POST "/admin/juegos/delete" req [] (juegos/juegos-delete req))
  (GET "/admin/juegos/get-jugadores/:juego/:casa/:fuera" [juego casa fuera] (juegos/juegos-jugadores juego casa fuera))
  (GET "/update/historial/:jugadores_id/:equipos_id/:juegos_id/:field/:valor" [jugadores_id equipos_id juegos_id field valor] (juegos/historial jugadores_id equipos_id juegos_id field valor))
  ;; End juegos

  ;; Start juegos-r
  (GET "/juegos" req (juegos-r/juegos req))
  (GET "/juegos/get-jugadores/:juego/:casa/:fuera" [juego casa fuera] (juegos-r/juegos-jugadores juego casa fuera))
  ;; End juegos-r
  )
