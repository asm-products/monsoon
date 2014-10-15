(ns monsoon.lib.pusher
  (require [clj-http.client :as http]
           [cheshire.core :refer :all]
           [monsoon.models.subscriber :as subscriber]))

(defn- push-to-subscriber
  [headers payload]
  (fn [endpoint-map]
    (try
      (http/post (endpoint-map :endpoint)
                 {:body (generate-string {:headers headers
                                          :payload payload})
                  :content-type :json})
      (catch Exception e
        (println (clojure.string/join ["Error on " (endpoint-map :endpoint) ":\n" e]))))))

(defn push
  [headers source payload]
  (pmap (push-to-subscriber headers payload)
       (subscriber/get-all-expecting-source source)))
