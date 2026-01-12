(ns interpreter-test
  (:require [interpreter :as i])
  (:import [java.nio.file Files Path]
           [java.util List]))

(gen-class :name App :methods [[^:static main ["String[]"] void]])

(defn- assert_ [fname args expected]
  (let [ng (i/engine_create {:code_dir "data"})
        actual (i/engine_call ng fname args)]
    (if (not= expected actual)
      (FIXME "expected: " expected " actual: " actual))))

(defn _main [_]
  (assert_ "sample.sample2" [1] {:a 1 :b {:c 1} :d 1})
  (assert_ "sample.sample1" [1] [1 [1 1] 1])
  (assert_ "sample.to_string" [1] "1")
  (assert_ "sample.get_by_pos" [true 1 2] 1)
  (assert_ "sample.get_by_pos" [false 1 2] 2)
  (assert_ "sample.sum" [1 2] 3)
  (assert_ "sample.call_get1" [1 2] 1)
  (assert_ "sample.get1" [1 2] 1)
  (assert_ "sample.get2" [1 2] 2)
  (assert_ "sample.test_case" [1] "one")
  (assert_ "sample.test_case" [2] "two")
  (assert_ "sample.test_case" [3] "default")
  ;;
  )
