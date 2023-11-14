(ns ru.nsu.ablaginin)

(defn addLetterToWord [letter word]
    (str letter word) ;; добавка буквы к слову
)

(defn addLetterToList [letter lettersList]
(map addLetterToWord ;; добавляем букву ко всем словам
    (repeat (count lettersList)
        letter ;; все буквы такие что
    )
     ;; fn x
    (filter #(not= (str (last letter)) (str (last %))) ;; не совпадают с последней буквой в слове
        lettersList
        )
    )
)

(defn reduceLetters [result letters]
    (reduce concat (map #(addLetterToList % letters) result)) ;;
)

(defn lab1 [letters n]
    (reduce reduceLetters (repeat n letters))
)
(println (count (lab1 (list "a", "b", "c", "d") 4)))
(println (lab1 (list "a", "b", "c", "d") 4))