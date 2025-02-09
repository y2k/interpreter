(ns _ (:require ["vendor/make/0.2.0/main" :as b]))

(b/generate
 [(b/module
   {:lang "java"
    :root-ns "src"
    :src-dir "src"
    :target-dir ".github/bin/src"
    :items ["interpreter" "client"]})
  (b/module
   {:lang "java"
    :root-ns "test"
    :src-dir "test"
    :target-dir ".github/bin/test"
    :items ["test"]})
  (b/module
   {:lang "bytecode"
    :src-dir "test/samples"
    :target-dir "test/samples/out"
    :no_lint true
    :items ["1" "2" "3" "4" "5" "6" "7" "8" "9"]})])
