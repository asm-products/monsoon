(ns monsoon.models.subscriber
  (:require [clj-time.coerce :as coerce]
            [clojure.java.jdbc :as sql]
            [monsoon.db.config :as db]))

;; NB: You must use the SQL column names as symbols,
;; e.g. `deleted_at`, not `deleted-at`.

(defn- now [] (coerce/to-sql-date (new java.util.Date)))

(defn- get-value-with-key
  [with-key]
  (fn [map-with-key] (get map-with-key with-key)))

(defn create
  [source endpoint]
  (println source endpoint)
  (first (sql/insert! db/spec
                      :subscribers
                      {:source source :endpoint endpoint})))

(defn retrieve
  [id]
  (first (sql/query db/spec
                    ["select * from subscribers where id = ? limit 1" id])))

(defn update
  [id endpoint]
  (first (sql/update! db/spec
                      :subscribers
                      {:endpoint endpoint :updated_at (now)}
                      ["id = ?" id])))

(defn destroy
  [id]
  (first (sql/update! db/spec
                      :subscribers
                      {:deleted_at (now)}
                      ["id = ?" id])))

(defn get-all-endpoints-expecting-source
  [source]
  (map (unravel :endpoint)
       (into [] (sql/query db/spec
                 ["select endpoint from subscribers where source = ?" source]))))
