(ns monsoon.models.activity
  (:require [clojure.java.jdbc :as sql]
            [monsoon.db.config :as db]))

(defn create
  [headers source payload]
  (first (sql/insert! db/spec
               :activities
               {:headers headers
                :source source
                :payload payload})))
