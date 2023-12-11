(ns test
  (:require [clojure.test :refer :all]) 
  (:require main)
  )

(defn loong-func [number]
  (Thread/sleep 1)
  (even? number))

(time (count (filter loong-func (range 1 1000))))
(time (count (main/p-filter-lazy loong-func (range 1 1000))))

(def A (filter even? (range 1 1000)))
(def B (main/p-filter-lazy even? (range 1 1000)))
(def C (take 10 (filter even? (range))))
(def D (take 10 (main/p-filter-lazy even? (range))))

(deftest finite-test
  (testing "Finite Sequence Filtering"
    (is (= A B))))

(deftest lazy-test
  (testing "Laziness"
    (is (= C D))))

(test #'finite-test)
(println "--- finite-test: PASS")

(test #'lazy-test)
(println "--- lazy-test: PASS")

(shutdown-agents)