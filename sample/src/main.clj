(ns main
  (:require [interpreter :as i])
  (:import [android.app Activity]
           [android.content Intent]
           [android.view View]
           [android.widget TextView]
           [android.os Bundle]
           [java.nio.file Files Path]))

(gen-class
 :name MainActivity
 :extends Activity
 :state state
 :init init
 :methods [[^Override onCreate [Bundle] void]])

(defn- _init [] (atom {}))

(defn- _onCreate [^MainActivity self _]
  (let [view (TextView. self)
        engine (i/engine_create {:code_dir (str (.getExternalFilesDir self nil))})
        result (i/engine_call engine "main.main" [0])]
    (.setText view (str "\n" result))
    (.setContentView self view))
  nil)
