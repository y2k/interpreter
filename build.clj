(ns _ (:require [".github/vendor/make/0.3.0/main" :as m]))

(m/build
 {:compile
  [{:target "java"
    :root "src"
    :namespace "interpreter"
    :out-dir ".github/bin/interpreter"}
   {:target "java"
    :root "test"
    :namespace "interpreter"
    :out-dir ".github/bin/interpreter"}
   {:target "sexp"
    :root "samples"
    :namespace "interpreter"
    :out-dir ".github/bin/samples"}]})
