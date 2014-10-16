(ns monsoon.controllers.activities
  (:use compojure.core)
  (:require [ring.util.response :as ring]
            [cheshire.core :refer :all]
            [monsoon.lib.pusher :as pusher]
            [monsoon.models.activity :as activity]))

(defn- create
  [headers product source payload]
  ;; TODO: Verify that this is the best approach to running these tasks in parallel
  ;; NB: We don't actually care about the results from either task (for now)
  (future (pusher/push headers source payload))
  (future (activity/create headers
                           product
                           source
                           payload))
          (ring/created (clojure.string/join ["/"
                                              product
                                              "/"
                                              source
                                              "/activities"])
                        payload))

(defroutes activity-routes
  (context "/:product/:source" [product source]
           (POST "/activities"
                 {headers :headers body :body}
                 (create (generate-string headers)
                         product
                         source
                         (slurp body)))))
