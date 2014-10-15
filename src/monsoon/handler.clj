(ns monsoon.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [environ.core :refer [env]]
            [monsoon.controllers.activities :as activities]
            [monsoon.controllers.subscribers :as subscribers]))

(defroutes app-routes
  activities/activity-routes
  subscribers/subscriber-routes
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
