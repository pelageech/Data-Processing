(ns ru.nsu.ablaginin)

(defn add-letter-to-word [letter word]
    (str letter word) ;; добавка буквы к слову
)

(defn add-letter-to-list [letter letters-list]
(map add-letter-to-word ;; добавляем букву ко всем словам
    (repeat (count letters-list)
        letter ;; все буквы такие что
    )
     ;; fn x
    (filter #(not= (str (last letter)) (str (last %))) ;; не совпадают с последней буквой в слове
        letters-list
        )
    )
)

(defn reduce-letters [result letters]
    (reduce concat (map #(add-letter-to-list % letters) result)) ;;
)

(defn lab1 [letters n]
    (reduce reduce-letters (repeat n letters))
)
(println (count (lab1 (list "a", "b", "c", "d") 4)))
(println (lab1 (list "a", "b", "c", "d") 4))