(ns _ (:import [java.nio.file Files Path]
               [java.util List])
    (:require ["./interpreter" :as i]))

;; (defn- make_env []
;;   (i/make_env
;;    {}))

;; (defn- test [id expected]
;;   (let [result (->
;;                 (i/eval
;;                  {:interpreter:save (fn [_ _] nil)}
;;                  (make_env)
;;                  (Files/readAllLines (Path/of (str "test/samples/out/" id ".bytecode"))))
;;                 first)
;;         actual (-> result str)]
;;     (if (not= actual expected)
;;       (FIXME "TEST " id ": " expected " <> " actual))))

(gen-class :name App
           :methods [[^:static main ["String[]"] void]])

(defn _main [_]
  (eprintln "Hello World!"))
