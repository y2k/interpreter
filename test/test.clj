(ns test (:import [interpreter Interpreter]
                  [java.nio.file Files Path]
                  [java.util List]))

(defn- make_env []
  (Interpreter/make_env
   {}))

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
  (test 6 "a3b")
  (test 7 "[1, 2, 3]"))
