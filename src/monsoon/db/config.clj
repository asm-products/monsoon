(ns monsoon.db.config
  (:require [environ.core :refer [env]]))

(def spec (env :database-url))
