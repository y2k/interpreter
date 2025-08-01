(defn main [x]
  (if (= x 0)
    x
    (+ x (main (+ x -1)))))

(main 10)
