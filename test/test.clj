(ns test (:import [interpreter Interpreter]
                  [java.nio.file Files Path]
                  [java.util List]))

(defn- test1 []
  (let [[r1 env2] (->
                   (Interpreter/make_env {})
                   (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/sample.1.gen.lisp")))))]
    (println "env:" env2)
    (println "=== CALL function #2 ===")
    (->
     env2
     (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/sample.2.gen.lisp"))))
     first
     println)))

(defn- test2 []
  ;; (println ["1" "2"])
  (->
   (Interpreter/make_env {:vector (function (fn [xs] xs))})
   (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/sample.3.gen.lisp"))))
   first
   println))

(defn- test3 []
  (->
   (Interpreter/make_env {:vector (function (fn [xs] xs))})
   (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/sample.4.gen.lisp"))))
   first
   println))

(defn ^void main [^"String[]" args]
  (test1)
  (test2)
  (test3)
  )
