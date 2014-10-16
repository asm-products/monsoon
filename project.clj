(defproject monsoon "0.1.0"
  :description "Lightweight database wrapper and push mechanism for sundry activities"
  :url "https://assembly.com/monsoon"
  :min-lein-version "2.0.0"
  :license {:name "Affero General Public License"
            :url "https://gnu.org/licenses/agpl.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [cheshire "5.3.1"]
                 [clj-http "1.0.0"]
                 [clj-time "0.8.0"]
                 [compojure "1.1.9" :exclusions [ring/ring-core]]
                 [com.gfredericks/vcr-clj "0.4.0"]
                 [environ "1.0.0"]
                 [postgresql "9.3-1102.jdbc41"]
                 [ring "1.3.1"]
                 [ring-mock "0.1.5"]]
  :plugins [[clj-sql-up "0.3.3"]
            [lein-environ "1.0.0"]
            [lein-ring "0.8.12"]]
  :clj-sql-up {:database "jdbc:postgresql://127.0.0.1/monsoon_development"
               :database-test "jdbc:postgresql://127.0.0.1/monsoon_test"
               :deps [[postgresql "9.3-1102.jdbc41"]]}
  :ring {:handler monsoon.handler/app
         :auto-refresh? true
         :nrepl {:start? true}}
  :main ^:skip-aot monsoon.handler
  :uberjar-name "monsoon-standalone.jar"
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]]}
   :test {:dependencies [[javax.servlet/servlet-api "2.5"]
                         [ring-mock "0.1.5"]]}
   :uberjar {:main monsoon.handler :aot :all}})
