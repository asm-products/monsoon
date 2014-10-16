(ns monsoon.test.models.activity
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as sql]
            [monsoon.models.activity :as activity]
            [monsoon.db.config :as db]
            [environ.core :rever [env]]))

(defn teardown [f]
  (f)
  (sql/delete! db/spec
               :activities
               []))

(use-fixtures :each teardown)

(deftest create-activity
  (testing "Create activity"
    (let [resp (activity/create "{\"test\":\"headers\"}"
                                "test-product"
                                "test-source"
                                "test-payload")]
      (is (= (resp :payload) "test-payload"))
      (is (= (resp :source) "test-source"))
      (is (= (resp :headers) "{\"test\":\"headers\"}")))))
