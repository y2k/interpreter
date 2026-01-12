(ns sample)

;; data structures
(defn test-vector [_]
  (= [1 [1 1] 1] [1 [1 1] 1]))

(defn test-hashmap [_]
  (= {:a 1 :b {:c 1}} {:a 1 :b {:c 1}}))

;; arithmetic
(defn test-add [_]
  (= 3 (+ 1 2)))

;; control flow
(defn test-if [_]
  (= [1 2] [(if true 1 2) (if false 1 2)]))

(defn test-case [_]
  (let [f (fn [x]
            (case x
              1 "one"
              2 "two"
              "default"))]
    (= ["one" "two" "default"] [(f 1) (f 2) (f 3)])))

;; str
(defn test-str [_]
  (= "1" (str 1)))

;; function calls
(defn- helper-fun [a b]
  (+ a b))

(defn test-call [_]
  (= 3 (helper-fun 1 2)))
