(ns monsoon.controllers.activities
  (:use compojure.core)
  (:require [ring.util.response :as ring]
            [cheshire.core :refer :all]
            [monsoon.models.activity :as activity]))

(defn- create
  [headers source payload]
  (activity/create headers source payload)
  (ring/created (clojure.string/join ["/" source "/activities"])
                payload))

(defroutes activity-routes
  (context "/:source" [source]
           (POST "/activities"
                 {headers :headers body :body}
                 (create (generate-string headers) source (slurp body)))))
