(ns interpreter-test
  (:require [interpreter :as i]))

(gen-class :name App :methods [[^:static main ["String[]"] void]])

(defn- assert_ [fname args expected]
  (let [ng (i/engine_create {:code_dir "data"})
        actual (i/engine_call ng fname args)]
    (if (not= expected actual)
      (FIXME "expected: " expected " actual: " actual))))

(defn _main [_]
  ;; data structures
  (assert_ "sample.test_vector" [1] [1 [1 1] 1])
  (assert_ "sample.test_hashmap" [1] {:a 1 :b {:c 1}})
  ;; arithmetic
  (assert_ "sample.test_add" [1 2] 3)
  ;; control flow
  (assert_ "sample.test_if" [true 1 2] 1)
  (assert_ "sample.test_if" [false 1 2] 2)
  (assert_ "sample.test_case" [1] "one")
  (assert_ "sample.test_case" [2] "two")
  (assert_ "sample.test_case" [3] "default")
  ;; str
  (assert_ "sample.test_str" [1] "1")
  ;; function calls
  (assert_ "sample.test_call" [1 2] 3))
