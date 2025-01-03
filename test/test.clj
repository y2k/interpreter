(ns _ (:import [java.nio.file Files Path]
               [java.util List])
    (:require ["../src/interpreter" :as i]))

(defn- make_env []
  (i/make_env
   {}))

(defn- test [id expected]
  (let [result (->
                (make_env)
                (i/eval (checked! (Files/readAllLines (Path/of (str "test/samples/out/" id ".gen.lisp")))))
                first)
        actual (-> result str)]
    (if (= actual expected)
      null
      (FIXME "TEST " id ": " expected " <> " actual))))

(defn ^void main [^"String[]" args]
  (test 2 "6")
  (test 3 "[a1, b2]")
  (test 4 "1")
  (test 5 "foo3bar")
  (test 6 "a3b")
  (test 7 "[1, 2, 3]")
  (test 8 "[1234, 12\"34]"))
