(defn foo [a b]
  (+ a b))

(defn main [x]
  (foo (foo 1 2) x))
