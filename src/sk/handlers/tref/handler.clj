(ns sk.handlers.tref.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [current_year get-image parse-int zpl]]))

;; Start get-pais
(def get-pais-sql
  "
  SELECT
   id AS value,
   descripcion AS text
   FROM pais
   ORDER BY
   descripcion
   ")

(defn get-pais []
  (Query db [get-pais-sql]))
;; End get-pais

;; Start get-pais-id
(def get-pais-id-sql
  "
  SELECT
   descripcion
   FROM pais
   WHERE
   id = ?
   ")

(defn get-pais-id [id]
  (:descripcion (first (Query db [get-pais-id-sql id]))))
;; End get-pais-id

;; Start get-users
(def get-users-sql
  "SELECT
  id AS value,
  CONCAT(firstname,' ',lastname) AS text
  FROM users
  ORDER BY
  firstname,lastname")

(defn get-users
  "Gets all users from database :ex: (get-users)"
  []
  (Query db [get-users-sql]))
;; End get-users

;; Start get-titulos
(def get-titulos-sql
  "SELECT
   id AS value,
   descripcion AS text
   FROM titulos
   ORDER BY
   descripcion")

(defn get-titulos
  "Gets all titulos from database ex: (get-titulos)"
  []
  (Query db [get-titulos-sql]))
;; End get-titulos

;; Start get-titulo
(def get-titulo-sql
  "
  SELECT
   descripcion
   FROM titulos
   WHERE
   id = ?
   ")

(defn get-titulo
  "Gets a titulo from database ex: (get-titulo 1)"
  [id]
  (:descripcion (first (Query db [get-titulo-sql id]))))
;; End get-titulo

;; Start get-users-email
(def get-users-email-sql
  "SELECT
  LOWER(email) as email
  FROM users
  WHERE email = ?")

(defn get-users-email
  "Returns user email or nil"
  [email]
  (first (Query db [get-users-email-sql email])))
;; End get-users-email

(defn months
  "Returns months name ex: (months)"
  []
  (list
   {:value 1 :text "Enero"}
   {:value 2 :text "Febrero"}
   {:value 3 :text "Marzo"}
   {:value 4 :text "Abril"}
   {:value 5 :text "Mayo"}
   {:value 6 :text "Junio"}
   {:value 7 :text "Julio"}
   {:value 8 :text "Agosto"}
   {:value 9 :text "Septiembre"}
   {:value 10 :text "Octubre"}
   {:value 11 :text "Noviembre"}
   {:value 12 :text "Diciembre"}))

(defn level-options []
  (list
   {:value "U" :text "Usuarios"}
   {:value "A" :text "Administrador"}
   {:value "S" :text "Systema"}))

(defn years
  "Genera listado para dropdown dependiendo de p=anterioriores de este año, n=despues de este año,
  ex: (years 5 4)"
  [p n]
  (let [year   (parse-int (current_year))
        pyears (for [n (range (parse-int p) 0 -1)] {:value (- year n) :text (- year n)})
        nyears (for [n (range 0 (+ (parse-int n) 1))] {:value (+ year n) :text (+ year n)})
        years  (concat pyears nyears)]
    years))

(defn build-time
  "Builds tipical time dropdown"
  []
  (let [items (flatten
               (for [x (range 5 21)]
                 (list
                  {:value (str (zpl x 2) ":00")
                   :text (if (< x 12)
                           (str (zpl x 2) ":00 AM")
                           (str (if (> x 12) (zpl (- x 12) 2) (zpl x 2)) ":00 PM"))}
                  {:value (str (zpl x 2) ":30")
                   :text (if (< x 12)
                           (str (zpl x 2) ":30 AM")
                           (str (if (> x 12) (zpl (- x 12) 2) (zpl x 2)) ":30 PM"))})))]
    items))

(defn imagen [table field idname value & extra-folder]
  (get-image table field idname value (first extra-folder)))

(defn get-item
  "Generic get field value from table"
  [table field idname idvalue]
  (let [sql (str "SELECT " field " FROM " table " WHERE " idname "='" idvalue "'")
        row (first (Query db sql))]
    ((keyword field) row)))

;; Start get-entrenadores
(def get-entrenadores-sql
  "
  SELECT
  id AS value,
  CONCAT(nombre,' ',paterno,' ',materno) AS text
  FROM entrenadores
  ORDER BY nombre,paterno,materno
  ")

(defn get-entrenadores
  "Regresa los entrenadores en formato amigable a esyui-combobox"
  []
  (Query db [get-entrenadores-sql]))
;; End get-entrenadores

;; Start get-estadios
(def get-estadios-sql
  "
  SELECT
  id AS value,
  nombre AS text
  FROM estadios
  ORDER BY nombre,donde_esta
  ")

(defn get-estadios
  "Regresa los estadios en formato amigable para easyui-combobox"
  []
  (Query db [get-estadios-sql]))
;; End get-estadios

;; Start get-entrenador-nombre
(def get-entrenador-nombre-sql
  "
  SELECT
  CONCAT(nombre,' ',paterno,' ',materno) as nombre
  FROM entrenadores
  WHERE id = ?
  ")

(defn get-entrenador-nombre
  "Regresa el nombre completo del entrenador"
  [id]
  (let [row (first (Query db [get-entrenador-nombre-sql id]))]
    (:nombre row)))
;; Stop get-entrenador-nombre

;; Start get-equipos
(def get-equipos-sql
  "
  SELECT
  id AS value,
  nombre AS text
  FROM equipos
  ORDER BY nombre,ciudad
  ")

(defn get-equipos
  "Regresa los equipos en formato amigable para eeasyui-combobox"
  []
  (Query db [get-equipos-sql]))
;; End get-equipos

;; Start get-posiciones
(def get-posiciones-sql
  "
  SELECT
  id AS value,
  nombre AS text
  FROM posiciones
  ORDER BY nombre
  ")

(defn get-posiciones
  "Regresa las posiciones de los jugadores en formato amigable para easyui-combobox"
  []
  (Query db [get-posiciones-sql]))
;; End get-posiciones

;; Start get-temporadas
(def get-temporadas-sql
  "
  SELECT
  id AS value,
  nombre AS text
  FROM temporadas
  ORDER BY nombre
  ")

(defn get-temporadas
  "Regresa las temporadas de los jugadores en formato amigable para easyui-combobox"
  []
  (Query db [get-temporadas-sql]))
;; End get-temporadas

;; Start get-juegos
(def get-juegos-sql
  "
  SELECT
  id AS value,
  DATE_FORMAT(fecha,'%m/%d/%Y') AS text
  FROM juegos
  ORDER BY fecha
  ")

(defn get-juegos
  "Regresa las juegos de los jugadores en formato amigable para easyui-combobox"
  []
  (Query db [get-juegos-sql]))
;; End get-juegos
(comment
  (get-juegos)
  (get-temporadas)
  (get-posiciones)
  (get-entrenadores)
  (get-estadios)
  (get-entrenador-nombre 2)
  (get-equipos)
  (get-pais-id 1)
  (get-pais))
