(ns build (:require ["$LY2K_PACKAGES_DIR/make/0.5.0/make" :as m]))

(m/build
 {:rules
  [{:target "java_v2"
    :root "src"
    :namespace "y2k"
    :extension "java"
    :out-dir ".github/bin/y2k"}
   {:target "java_v2"
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
