(ns main)

(def try-take-fork-times (atom 0))

(defn fork []
  (ref {:in-use 0 :times-used 0}
       :validator (fn [state] (and (>= (state :in-use) 0) (<= (state :in-use) 1)))))

(defn take-fork [fork]
  (dosync
   (swap! try-take-fork-times inc)
   (alter fork (fn [state] (update (update state :in-use inc) :times-used inc)))))

(defn put-fork [fork]
  (dosync
   (alter fork (fn [state]  (update state :in-use dec)))))

(defn take-forks [left-fork right-fork]
  (if
   (nil?
    (try
      (take-fork left-fork)
      (catch IllegalStateException _ nil)))
    (recur left-fork right-fork)
    (if
     (nil?
      (try
        (take-fork right-fork)
        (catch IllegalStateException _ nil)))
      (do
        (put-fork left-fork)
        (recur left-fork right-fork))
      nil)))

(defn philosopher
  [thinking-time dining-time left-fork right-fork id times eaten]
  (new Thread
       (fn []
         (println (str "Philosopher " id " is thinking..."))
         (Thread/sleep thinking-time)
         (println (str "Philosopher " id " is trying to take forks..."))
         (take-forks left-fork right-fork)
         (println (str "Philosopher " id " is eating..."))
         (Thread/sleep dining-time)
         (swap! (nth eaten id) inc)
         (put-fork left-fork)
         (put-fork right-fork)
         (println (str "Philosopher " id " is putting forks..."))
         (if (>= times @(nth eaten id))
           (recur)
           nil))))

(defn start-lunch [phil-count thinking-time dining-time lunch-count]
  (let [forks (map (fn [_] (fork)) (range phil-count))
        lunch-eaten (map (fn [_] (atom 0)) (range phil-count))
        left-fork (fn [phil-id] (nth forks phil-id))
        right-fork (fn [phil-id] (nth forks (mod (inc phil-id) phil-count)))
        philosophers (map
                      (fn [phil-id]
                        (philosopher
                         thinking-time
                         dining-time
                         (left-fork phil-id)
                         (right-fork phil-id)
                         phil-id
                         lunch-count
                         lunch-eaten))
                      (range phil-count))]
    (run! #(.start %) philosophers)
    (time (run! #(.join %) philosophers))
    (println (str "Transaction restart times: " @try-take-fork-times))))

(start-lunch 4 100 100 10)