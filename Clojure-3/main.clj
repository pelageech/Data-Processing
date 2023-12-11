(ns main)

(defn p-filter-eager
  ([pred coll]
   (let [chunk-size (int (Math/ceil (Math/sqrt (count coll)))),

         parts (partition-all chunk-size coll)]
     (->> parts
          (map (fn [coll1]

                 (future (vec (filter pred coll1)))))

          ;do not forget to cancel map’s laziness!
          (doall)
          (map deref)
          (reduce concat)))))

(defn p-filter-lazy
  ([pred coll]
   (let [large-chunk-size 16,
         parts (partition-all large-chunk-size coll)]
     (mapcat identity
             (->> parts
                  (map (fn [part]
                         (future (p-filter-eager pred part))))
                  (map deref))))))