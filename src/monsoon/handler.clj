(ns monsoon.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [monsoon.controllers.activities :as activities]
            [monsoon.controllers.home :as home]
            [monsoon.controllers.subscribers :as subscribers])
  (:gen-class))

(defroutes app-routes
  home/home-routes
  subscribers/subscriber-routes
  activities/activity-routes
  (route/not-found "Not Found"))

(def app (handler/api app-routes))
