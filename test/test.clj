(ns _ (:import [java.nio.file Files Path]
               [java.util List])
    (:require ["./index" :as i]))

(gen-class :name App :methods [[^:static main ["String[]"] void]])

(defn _main [_]
  (let [expected "3"
        code (Files/readAllLines (Path/of (str ".github/bin/samples/sample.sexp")))
        actual (i/eval2 code)]
    (if (not= expected actual)
      (FIXME expected " != " actual " in " code))))
