(ns _ (:require ["vendor/make/0.1.0/main" :as b]))

(b/generate
 [(b/module
   {:lang "java"
    :root-ns "src"
    :src-dir "src"
    :target-dir ".github/bin/src"
    :items ["interpreter"]})
  (b/module
   {:lang "java"
    :root-ns "test"
    :src-dir "test"
    :target-dir ".github/bin/test"
    :items ["test"]})])
