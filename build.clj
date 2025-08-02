(ns _ (:require [".github/vendor/make/0.3.0/main" :as m]))

(m/build
 {:compile [{:target "java"
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

;; (b/generate
;;  [(b/module
;;    {:lang "java"
;;     :root-ns "src"
;;     :src-dir "src"
;;     :target-dir ".github/bin/src"
;;     :items ["interpreter"]})
;;   (b/module
;;    {:lang "java"
;;     :root-ns "test"
;;     :src-dir "test"
;;     :target-dir ".github/bin/test"
;;     :items ["test"]})
;;   (b/module
;;    {:lang "bytecode"
;;     :src-dir "test/samples"
;;     :target-dir "test/samples/out"
;;     :no_lint true
;;     :items ["1" "2" "3" "4" "5" "6" "7" "8" "9"]})])
