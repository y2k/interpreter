(ns test (:import [java.nio.file Files Path]
                  [java.util List])
    (:require ["./index" :as i]))

(gen-class :name App :methods [[^:static main ["String[]"] void]])

(defn- assert_ [fname args expected]
  (let [ng (i/engine_create {:code_dir "test/data"})
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
  ;;
  )
