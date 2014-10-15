(ns monsoon.controllers.activities
  (:use compojure.core)
  (:require [ring.util.response :as ring]
            [cheshire.core :refer :all]
            [monsoon.lib.pusher :as pusher]
            [monsoon.models.activity :as activity]))

(defn- create
  [headers source payload]
  ;; TODO: Verify that this is the best approach to running these tasks in parallel
  ;; NB: We don't actually care about the results from either task (for now)
  (future (pusher/push headers source payload))
  (future (activity/create headers source payload))
          (ring/created (clojure.string/join ["/" source "/activities"])
                        payload))

(defroutes activity-routes
  (context "/:source" [source]
           (POST "/activities"
                 {headers :headers body :body}
                 (create (generate-string headers) source (slurp body)))))
