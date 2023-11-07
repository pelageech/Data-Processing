(defn perm [N args]
  (if (or (> N (count args)) (> 0 N))
    ()
    (if (= N 1)
      args
      (reduce
       (fn [acc, e] (reduce conj acc (map (fn [a] (cons e a)) (perm (- N 1) (remove (fn [a] (= a e)) args)))))
       ()
       args)
      )))
(def K 8)
(def arr '("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"))
(def N (count arr))
(def Perms (perm K arr))
;; (print Perms)
(defn ft [n] (reduce * 1 (range 1 (+ n 1))))

(print (count Perms) (/ (ft N) (ft (- N K))))