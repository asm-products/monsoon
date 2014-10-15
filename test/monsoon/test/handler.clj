(ns monsoon.test.handler
  (:require [clojure.test :refer :all]
            [monsoon.handler :refer :all]
            [clojure.java.jdbc :as sql]
            [monsoon.db.config :as db]
            [ring.mock.request :as mock]
            [cheshire.core :refer :all]))

(defn teardown-activities [f]
  (f)
  (sql/delete! db/spec
               :activities
               []))

(defn teardown-subscribers [f]
  (f)
  (sql/delete! db/spec
               :subscribers
               []))

(use-fixtures :each teardown-activities teardown-subscribers)

(defn test-create
  [url body]
  (app (-> (mock/request :post
                    url
                    body)
           (mock/content-type "text/plain"))))

(defn test-create-subscriber []
  (test-create "/test/subscribers" "http://test-endpoint.com/webhook"))

(deftest create-activity
  (testing "Create activity request"
    (let [response (test-create "/test/activities"
                                (generate-string {:body "body"}))]
      (is (= (:status response) 201))
      (is (= (:body response) (generate-string {:body "body"}))))))

(deftest create-subscriber
  (testing "Create subscriber request"
    (let [response (test-create-subscriber)]
      (is (= (:status response) 201)))))

(deftest retrieve-subscriber
  (testing "Retrieve subscriber request"
    (let [response (test-create-subscriber)
          retrieved (app (mock/request :get
                                       (clojure.string/join ["/test/subscribers/"
                                                             (response :body)])))]
      (is (= (:status retrieved) 200))
      (is (= ((:body retrieved) :endpoint) "http://test-endpoint.com/webhook")))))

(deftest update-subscriber
  (testing "Update subscriber request"
    (let [response (test-create-subscriber)
          updated (app (-> (mock/request :put
                                         (clojure.string/join ["/test/subscribers/"
                                                               (response :body)])
                                         "http://updated-endpoint.com/webhook")
                           (mock/content-type "text/plain")))]
      (is (= (:status updated) 200))
      (is (= ((:body updated) :endpoint) "http://updated-endpoint.com/webhook")))))

(deftest destroy-subscriber
  (testing "Destroy subscriber request"
    (let [response (test-create-subscriber)
          destroyed (app (mock/request :delete
                                           (clojure.string/join ["/test/subscribers/"
                                                                 (response :body)])))]
      (is (= (:status destroyed) 200))
      (is (not (= ((:body destroyed) :deleted_at) nil))))))
