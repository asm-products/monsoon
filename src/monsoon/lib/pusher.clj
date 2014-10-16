(ns monsoon.lib.pusher
  (require [clj-http.client :as http]
           [cheshire.core :refer :all]
           [monsoon.models.subscriber :as subscriber]))

(defn- push-to-subscriber
  [headers payload]
  (fn [endpoint]
    (try
      (http/post endpoint
                 {:body (generate-string {:headers headers
                                          :payload payload})
                  :content-type :json})
      (catch Exception e
        (println (clojure.string/join ["Error on " endpoint ":\n" e]))))))

(defn push
  [headers source payload]
  (pmap (push-to-subscriber headers payload)
       (subscriber/get-all-endpoints-expecting-source source)))
