(ns build (:require [make :as m]))

(deps {:make "0.5.0"})

(m/build
 {:rules
  [{:target "dep"
    :name "prelude"
    :version "1.0.0/java"
    :namespace "y2k"
    :compile_target "java"
    :out-dir ".github/bin/y2k"}
   {:target "java"
    :root "src"
    :namespace "y2k"
    :extension "java"
    :out-dir ".github/bin/y2k"}
   {:target "java"
    :root "test"
    ;; :log true
    :namespace "y2k"
    :extension "java"
    :out-dir ".github/bin/y2k"}
  ;;  {:target "sexp"
  ;;   :root "samples"
  ;;   :namespace "interpreter"
  ;;   :out-dir ".github/bin/samples"}
   ]})
