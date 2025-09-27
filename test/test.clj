(ns _ (:import [java.nio.file Files Path]
               [java.util List])
    (:require ["./index" :as i]))

(gen-class :name App :methods [[^:static main ["String[]"] void]])

(defn- assert2 [input]
  (println
   input
   " -> "
   (first
    (i/list_to_tree 0 input))))

(defn _main [_]
  (assert2 ["(" "1" ")"])
  (assert2 ["(" "1" "2" ")"])
  (assert2 ["(" "1" "(" "3" "4" ")" "2" ")"])
  (assert2 ["(" "1" "(" "3" "4" ")" ")"])
  (assert2 ["(" "(" "3" "4" ")" "2" ")"])
  (assert2 ["(" "1" "(" "(" "31" "32" ")" "3" "4" ")" "2" ")"]))

;; (defn _main [_]
;;   (let [expected "3"
;;         code (Files/readAllLines (Path/of (str ".github/bin/samples/sample.sexp")))
;;         actual (i/eval2 code)]
;;     (if (not= expected actual)
;;       (FIXME expected " != " actual " in " code))))
