(ns monsoon.lib.authorization
  (require [cheshire.core :refer [generate-string]]
           [clj-http.client :as http]
           [compojure.response :as response]
           [environ.core :refer [env]]
           [ring.util.response :refer [header status]]))

(defn- make-url
  [product token]
  (clojure.string/join [(env :assembly-url)
                        "/products/"
                        product
                        "/authorization?token="
                        token]))

(defn- process-response-body
  [body]
  (body :authorized))

(defn- ask
  [product token]
  (let [response (http/get (make-url product token)
                           {:accept :json
                            :as :json})]
    (process-response-body (response :body))))

;; pletcher: This function should really be unnecessary;
;; I suspect I am doing something wrong when passing the
;; request through this middleware.
(defn- get-product-name-from-uri
  [uri]
  (second (clojure.string/split uri #"/")))

(defn- get-product
  [request]
  (get-product-name-from-uri (request :uri)))

(defn- get-token [params] (params :token))

(defn- authorized?
  [request]
  (let [product (get-product request)
        token (get-token (request :params)) ]
    (ask product token)))

(defn authorize
  [app]
  (fn [request]
    (if (authorized? request)
      (app request)
      (-> (response/render (generate-string {:status 401 :message "Unauthorized"})
                           request)
          (status 401)
          (header "Content-Type" "application/json")))))
