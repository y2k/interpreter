(ns interpreter)

;; Version: 0.5.0

(defn- handle-children [^int i1 nodes]
  (let [[n1 ^int i2] (list-to-tree i1 nodes)]
    (if (= n1 nil)
      [[] i2]
      (let [[n2 i3] (handle-children i2 nodes)]
        [(concat [n1] n2) i3]))))

(defn- list-to-tree [^int i nodes]
  (let [hd (get nodes i)]
    (cond
      (= hd "(") (handle-children (+ i 1) nodes)
      (= hd ")") [nil (+ i 1)]
      :else [hd (+ i 1)])))

;;
;;
;;

(defn resolve-value [engine ctx name]
  (if (contains? ctx name)
    (get ctx name)
    (resolve-name engine name)))

(defn- zipmap-merge [keys values dic]
  (if (empty? keys)
    dic
    (zipmap-merge
     (rest keys)
     (rest values)
     (assoc dic (first keys) (first values)))))

(defn- string-node? [s]
  (string/starts-with? s "\""))

(defn- number-node? [s]
  (re-find (re-pattern "^\\d+$") s))

;; engine * local-scope * lines -> value * local-scope
(defn- eval- [engine ctx sexp]
  (if (vector? sexp)
    (case (first sexp)
      "fn*" [(fn [arg-values]
               (let [arg-names (get sexp 1)
                     ctx2 (zipmap-merge arg-names arg-values ctx)]
                 (first
                  (eval- engine ctx2 (get sexp 2)))))
             ctx]
      "if*" [(let [[cond _] (eval- engine ctx (get sexp 1))]
               (first
                (if cond
                  (eval- engine ctx (get sexp 2))
                  (eval- engine ctx (get sexp 3)))))
             ctx]
      "do*" (reduce
             (fn [[_ ctx2] n]
               (eval- engine ctx2 n))
             [nil ctx]
             (rest sexp))
      "let*" (let [name (get sexp 1)
                   ctx2 (assoc ctx name (first (eval- engine ctx (get sexp 2))))]
               [nil ctx2])
      (let [f (resolve-value engine ctx (first sexp))]
        [(apply f [(map
                    (fn [n] (first (eval- engine ctx n)))
                    (rest sexp))])
         ctx]))
    (cond
      (string-node? sexp) (let [^int len (count sexp)]
                            [(subs sexp 1 (- len 1)) ctx])
      (number-node? sexp) [(parse-int sexp) ctx]
      :else [(resolve-value engine ctx sexp) ctx])))

(defn- read-all-lines [^String path]
  (java.nio.file.Files.readAllLines (java.nio.file.Path.of path)))

(defn- load-code [engine name]
  (let [path (str (:code-dir engine) "/" name ".txt")]
    (->>
     (read-all-lines path)
     (list-to-tree 0)
     (first)
     (eval- engine (:ctx engine))
     (first))))

(defn- resolve-name [engine name]
  (let [ns (deref (:ns engine))
        value (get ns name)]
    (if (some? value)
      value
      (let [value2 (load-code engine name)]
        (if (some? value2)
          (do
            (reset! (:ns engine) (assoc ns name value2))
            value2)
          (FIXME "Could not find " name " in " (:code-dir engine)))))))

(defn engine-call [engine name args]
  (let [fun (resolve-name engine name)]
    (apply fun [args])))

;;
;;
;;

;; { :code-dir "" } -> engine
(defn engine-create [opts]
  {:code-dir (:code-dir opts)
   :ns (atom {"str" (fn [xs] (string/join "" (map (fn [x] (str x)) xs)))
              "true" true
              "false" false
              "vec" (fn [[coll]] (vec coll))
              "vector" (fn [xs] (vec xs))
              "concat" (fn [[a b]] (vec (concat a b)))
              "take" (fn [[n coll]] (vec (take n coll)))
              "drop" (fn [[n coll]] (vec (drop n coll)))
              "hash_map" (fn [key-values]
                           (hash-map-from key-values))
              "get" (fn [[m k]] (get m k))
              "map" (fn [[f coll]] (map (fn [x] (apply f [[x]])) coll))
              "filter" (fn [[f coll]] (filter (fn [x] (apply f [[x]])) coll))
              "reduce" (fn [[f init coll]] (reduce (fn [acc x] (apply f [[acc x]])) init coll))
              "mod" (fn [[^int a ^int b]] (mod a b))
              "_PLUS_" (fn [[^int a ^int b]] (+ a b))
              "=" (fn [[a b]] (= a b))})
   :ctx {}})
