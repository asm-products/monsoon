(defproject monsoon "0.1.0"
  :description "Lightweight database wrapper and push mechanism for sundry activities"
  :url "https://assembly.com/monsoon"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [cheshire "5.3.1"]
                 [clj-http "1.0.0"]
                 [clj-time "0.8.0"]
                 [compojure "1.1.9" :exclusions [ring/ring-core]]
                 [environ "1.0.0"]
                 [postgresql "9.3-1102.jdbc41"]
                 [ragtime "0.3.7"]
                 [ring "1.3.1"]
                 [ring-mock "0.1.5"]]
  :plugins [[lein-environ "1.0.0"]
            [lein-ring "0.8.12"]
            [ragtime/ragtime.lein "0.3.7"]]
  :ragtime {:migrations ragtime.sql.files/migrations
            :database (System/getenv :database-url)}
  :ring {:handler monsoon.handler/app
         :auto-refresh? true
         :nrepl {:start? true}}
  :main ^:skip-aot monsoon.handler
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]]
         :ragtime {:database (env :database-url)}}
   :test {:dependencies [[javax.servlet/servlet-api "2.5"]
                         [ring-mock "0.1.5"]]
          :ragtime {:database (env :database-url)}}})
