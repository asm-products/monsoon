(ns monsoon.controllers.subscribers
  (:use compojure.core)
  (:require [ring.util.response :as ring]
            [cheshire.core :refer :all]
            [monsoon.models.subscriber :as subscriber]))

(defn- create
  [source endpoint]
  (let [created (subscriber/create source endpoint)]
    (ring/created (clojure.string/join ["/" source "/subscribers"])
                  (created :id))))

(defn- retrieve
  [id]
  (let [retrieved-subscriber (subscriber/retrieve id)]
    (if (empty? retrieved-subscriber)
      (ring/not-found id)
      (ring/response retrieved-subscriber))))

(defn- update
  [id endpoint]
  (let [updated-subscriber (subscriber/update id endpoint)]
    (retrieve id)))

(defn- destroy
  [id]
  (let [destroyed-subscriber (subscriber/destroy id)]
    (retrieve id)))

(defroutes subscriber-routes
  (context "/:source/:subscribers" [source]
           (POST "/"
                 {endpoint :body}
                 (create source (slurp endpoint)))
           (context "/:id" [id]
                    (GET "/" [] (retrieve (java.util.UUID/fromString id)))
                    (PUT "/"
                         {endpoint :body}
                         (update (java.util.UUID/fromString id) (slurp endpoint)))
                    (DELETE "/" [] (destroy (java.util.UUID/fromString id))))))
