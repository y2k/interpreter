(ns sample)

(defn foo [a b]
  (+ a b))

(defn bar [x]
  (foo x x))

(defn get1 [a b] a)
(defn get2 [a b] b)
(defn call_get1 [a b] (get1 a b))
