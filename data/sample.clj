(ns sample)

;; data structures
(defn test_vector [_]
  (= [1 [1 1] 1] [1 [1 1] 1]))

(defn test_hashmap [_]
  (= {:a 1 :b {:c 1}} {:a 1 :b {:c 1}}))

;; arithmetic
(defn test_add [_]
  (= 3 (+ 1 2)))

;; control flow
(defn test_if [_]
  (= [1 2] [(if true 1 2) (if false 1 2)]))

(defn test_case [_]
  (let [f (fn [x]
            (case x
              1 "one"
              2 "two"
              "default"))]
    (= ["one" "two" "default"] [(f 1) (f 2) (f 3)])))

;; str
(defn test_str [_]
  (= "1" (str 1)))

;; function calls
(defn- helper [a b]
  (+ a b))

(defn test_call [_]
  (= 3 (helper 1 2)))
