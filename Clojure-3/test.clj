(ns test
  (:require [clojure.test :refer :all]) 
  (:require main)
  )

(defn loong-func [number]
  (Thread/sleep 1)
  (even? number))

(time (count (filter loong-func (range 1 1000))))
(time (count (main/p-filter-lazy loong-func (range 1 1000))))

(deftest finite-test
  (testing "Finite Sequence Filtering"
    (is (= (filter even? (range 1 1000)) (main/p-filter-lazy even? (range 1 1000))))))

(deftest lazy-test
  (testing "Laziness"
    (is (= (take 10 (filter even? (range))) (take 10 (main/p-filter-lazy even? (range)))))))

(test #'finite-test)
(println "--- finite-test: PASS")

(test #'lazy-test)
(println "--- lazy-test: PASS")

(shutdown-agents)