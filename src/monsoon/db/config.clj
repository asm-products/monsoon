(ns monsoon.db.config
  (:require [environ.core :refer [env]]))

(def spec (or (env :database-url)
              (System/getenv "DATABASE_URL")))
