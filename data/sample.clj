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

;; higher-order functions
(defn test-map [_]
  (= [2 4 6] (map (fn [x] (+ x x)) [1 2 3])))

(defn test-filter [_]
  (= [2 4] (filter (fn [x] (= 0 (mod x 2))) [1 2 3 4 5])))

(defn test-reduce [_]
  (= 10 (reduce (fn [acc x] (+ acc x)) 0 [1 2 3 4])))
