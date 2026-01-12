(ns sample)

(defn sample1 [x] [x [x x] x])
(defn sample2 [x] {:a x :b {:c x} :d x})

(defn foo [a b]
  (+ a b))

(defn bar [x]
  (foo x x))

(defn get1 [a b] a)
(defn get2 [a b] b)
(defn call_get1 [a b] (get1 a b))

(defn sum [a b] (+ a b))

(defn get_by_pos [is_first a b]
  (if is_first a b))

(defn to_string [x]
  (str x))

(defn test_case [x]
  (case x
    1 "one"
    2 "two"
    "default"))
