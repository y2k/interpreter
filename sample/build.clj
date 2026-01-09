(ns build (:require ["$LY2K_PACKAGES_DIR/make/0.5.0/make" :as m]))

(m/build
 {:rules
  [{:target "java_v2"
    :root "src"
    :namespace "io.github.y2k"
    :extension "java"
    :out-dir "bin/android/app/src/main/java/io/github/y2k"}
   {:target "java_v2"
    :root "../src"
    :namespace "io.github.y2k"
    :extension "java"
    :out-dir "bin/android/app/src/main/java/io/github/y2k"}
   {:target "eval"
    :src "resources/manifest.clj"
    :out "bin/android/app/src/main/AndroidManifest.xml"}]})
