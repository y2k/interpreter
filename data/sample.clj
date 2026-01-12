(ns sample)

(defn test_vector [x]
  [x [x x] x])

(defn test_hashmap [x]
  {:a x :b {:c x}})

(defn test_add [a b]
  (+ a b))

(defn test_if [cond a b]
  (if cond a b))

(defn test_case [x]
  (case x
    1 "one"
    2 "two"
    "default"))

(defn test_str [x]
  (str x))

(defn test_call [a b]
  (test_add a b))
