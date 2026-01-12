(ns interpreter-test
  (:require [interpreter :as i]))

(gen-class :name App :methods [[^:static main ["String[]"] void]])

(defn- run_test [fname]
  (let [ng (i/engine_create {:code_dir "data"})
        result (i/engine_call ng fname [0])]
    (if (not= true result)
      (FIXME "test failed: " fname))))

(defn _main [_]
  (run_test "sample.test_vector")
  (run_test "sample.test_hashmap")
  (run_test "sample.test_add")
  (run_test "sample.test_if")
  (run_test "sample.test_case")
  (run_test "sample.test_str")
  (run_test "sample.test_call"))
