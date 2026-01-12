(ns interpreter-test
  (:require [interpreter :as i]))

(gen-class :name App :methods [[^:static main ["String[]"] void]])

(defn- run-test [fname]
  (let [ng (i/engine-create {:code-dir "data"})
        result (i/engine-call ng fname [0])]
    (if (not= true result)
      (FIXME "test failed: " fname))))

(defn- test-file? [^String name]
  (if (string/starts-with? name "sample.test_")
    (.endsWith name ".txt")
    false))

(defn- to-test-name [^String name]
  (let [^int len (count name)]
    (subs name 0 (- len 4))))

(defn _main [_]
  (let [dir (java.io.File. "data")
        files (java.util.Arrays.asList (.listFiles dir))]
    (reduce
     (fn [_ ^java.io.File file]
       (let [^String name (.getName file)]
         (if (test-file? name)
           (run-test (to-test-name name)))))
     nil
     files)))
