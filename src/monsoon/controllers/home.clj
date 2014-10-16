(ns monsoon.controllers.home
  (:use compojure.core)
  (:require [ring.util.response :refer [header response status]]
            [cheshire.core :refer [generate-string]]))

(defroutes home-routes
  (ANY "/" [] (-> (response (generate-string {:status 200
                                              :message "Monsoon"}))
                  (status 200)
                  (header "Content-Type" "application/json"))))
