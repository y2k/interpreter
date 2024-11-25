(ns test
  (:import [main Main]))

(defn ^void main [^"String[]" args]
  (->
   (Main/make_env)
   (Main/eval (list "+" 2 2))
   (println)))
