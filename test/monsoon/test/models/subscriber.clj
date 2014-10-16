(ns monsoon.test.models.subscriber
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as sql]
            [monsoon.models.subscriber :as subscriber]
            [monsoon.db.config :as db]
            [environ.core :rever [env]]))

(defn teardown [f]
  (f)
  (sql/delete! db/spec
               :subscribers
               []))

(use-fixtures :each teardown)

(deftest create-read-subscriber
  (testing "Create subscriber"
    (let [resp (subscriber/create "test-product"
                                  "test"
                                  "test-endpoint")]
      (is (= (resp :endpoint) "test-endpoint")))))

(deftest read-subscriber
  (testing "Retrieve subscriber"
    (let [new-subscriber (subscriber/create "test-product"
                                            "test"
                                            "test-endpoint")
          found-subscriber (subscriber/retrieve (new-subscriber :id))]
      (is (= found-subscriber new-subscriber))))
  (testing "Retrieve nonexistent subscriber"
    (let [existent (subscriber/create "test-product"
                                      "test"
                                      "existent-endpoint")
          nonexistent (subscriber/retrieve (java.util.UUID/randomUUID))]
      (is (= nonexistent nil)))))

(deftest update-subscriber
  (testing "Update subscriber"
    (let [first-subscriber (subscriber/create "test-product"
                                              "test"
                                              "first-endpoint")
          found-first-subscriber (subscriber/retrieve (first-subscriber :id))]
      (is (= (first-subscriber :endpoint) "first-endpoint"))
      (let [updated-subscriber (subscriber/update (first-subscriber :id) "updated-endpoint")
            found-updated-subscriber (subscriber/retrieve (first-subscriber :id))]
        (is (= (found-updated-subscriber :endpoint) "updated-endpoint"))))))

(deftest destroy-subscriber
  (testing "Destroy subscriber"
    (let [created (subscriber/create "test-product"
                                     "test"
                                     "endpoint")
          destroyed (subscriber/destroy (created :id))
          timestamp (subscriber/retrieve (created :id))]
      (is (not (= (timestamp :deleted_at) nil))))))
