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

(defn- test [id expected]
  (let [actual (->
                (make_env)
                (Interpreter/eval (checked! (Files/readAllLines (Path/of (str "test/samples/out/" id ".gen.lisp")))))
                first
                str)]
    (if (= actual expected)
      null
      (FIXME expected " <> " actual))))

(defn ^void main [^"String[]" args]
  (test 2 "6")
  (test 3 "[a1, b2]")
  (test 4 "1")
  (test 5 "foo3bar")
  (test 6 "a3b"))
