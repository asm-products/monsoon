(ns monsoon.controllers.home
  (:use compojure.core)
  (:require [ring.util.response :refer [header not-found response status]]
            [cheshire.core :refer [generate-string]]))

(defroutes home-routes
  (ANY "/" [] (-> (response (generate-string {:status 200
                                              :message "Monsoon"}))
                  (status 200)
                  (header "Content-Type" "application/json")))
  (ANY "/favicon.ico" [] (not-found "Not Found")))
