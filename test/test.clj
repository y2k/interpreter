(ns test (:import [interpreter Interpreter]
                  [java.nio.file Files Path]
                  [java.util List]))

(defn- make_env []
  (Interpreter/make_env
   {:vector (function (fn [xs] xs))
    :atom (function (fn [[x]] (atom x)))
    :deref (function (fn [[x]] (deref x)))
    :reset! (function (fn [[a x]]
                        ;; (println "reset!" a x)
                        (reset! a x) x))
    :str (function (fn [xs] (str (into-array2 (.-class Object) xs))))}))

(defn- test1 []
  (let [[r1 env2] (->
                   (Interpreter/make_env {})
                   (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/samples/out/1.gen.lisp")))))]
    ;; (println "env:" env2)
    (println "=== CALL function #2 ===")
    (->
     env2
     (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/samples/out/2.gen.lisp"))))
     first
     println)))

(defn- test2 []
  (->
   (make_env)
   (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/samples/out/3.gen.lisp"))))
   first
   println))

(defn- test3 []
  (->
   (make_env)
   (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/samples/out/4.gen.lisp"))))
   first
   println))

(defn- test4 []
  (->
   (make_env)
   (Interpreter/eval (checked! (Files/readAllLines (Path/of "test/samples/out/5.gen.lisp"))))
   first
   println))

(defn ^void main [^"String[]" args]
  ;; (test1)
  ;; (test2)
  ;; (test3)
  (test4))
