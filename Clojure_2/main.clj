(ns ru.nsu.ablaginin)

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


