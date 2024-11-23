(ns main)

(defn- ^java.util.function.Function function [^java.util.function.Function f] f)

(defn make_env []
  {:scope
   {:+ (function (fn [[^int a ^int b]] (+ a b)))}})

(defn eval [env node]
  (cond
    (list? node)
    (let [name (first node)
          args (rest node)
          ^java.util.function.Function f (get (:scope env) name)]
      (.apply f args))

    (vector? node) (FIXME)

    (map? node) (FIXME)

    :else node))
