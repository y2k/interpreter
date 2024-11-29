(ns test (:import [interpreter Interpreter]
                  [java.nio.file Files Path]
                  [java.util List]))

(defn ^void main [^"String[]" args]
  (let [[r1 env2] (->
                   (Interpreter/make_env)
                   (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/sample.1.gen.lisp")))))]
    (println "=== CALL function #2 ===")
    (->
     env2
     (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/sample.2.gen.lisp"))))
     first
     println)))
