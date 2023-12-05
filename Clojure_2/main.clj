(ns ru.nsu.ablaginin
  (:require [clojure.test :refer :all]))

(defn sieve [start]
  (cons (first start)
        (lazy-seq
          (sieve
            (filter #(not= 0 (mod % (first start)))
                                 (rest start))))
  )
)

(defn pseq [] (sieve (iterate inc 2)))

(println (take 10 (pseq)))

(deftest first-test
  (testing "First Prime"
    (is (== (nth (pseq) 0) 2))))

(deftest hundred-test
  (testing "100th Prime"
    (is (== (nth (pseq) 99) 541))))

(deftest thousand-test
  (testing "1000st Prime"
    (is (== (nth (pseq) 999) 7919))))


(test #'first-test)
(test #'hundred-test)
(test #'thousand-test)