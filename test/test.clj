(ns test (:import [main Main]
                  [java.nio.file Files Path]
                  [java.util List]))

(defn ^void main [^"String[]" args]
  (let [[r1 env2] (->
                   (Main/make_env)
                   (Main/eval (checked! (Files/readAllLines (Path/of "test/sample.lisp")))))]
    (println "=== CALL function #2 ===")
    (->
     env2
     (Main/eval (checked! (Files/readAllLines (Path/of "test/sample.3.lisp"))))
     first
     println)))
