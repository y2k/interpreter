;; Version: 0.1.0

(defn- handle_children [list_to_tree ^int i1 nodes]
  (let [[n1 ^int i2] (list_to_tree i1 nodes)]
    (if (= n1 nil)
      [[] i2]
      (let [[n2 i3] (handle_children list_to_tree i2 nodes)]
        [(concat [n1] n2) i3]))))

(defn- list_to_tree [^int i nodes]
  (let [hd (get nodes i)]
    (cond
      (= hd "(") (handle_children list_to_tree (+ i 1) nodes)
      (= hd ")") [nil (+ i 1)]
      :else [hd (+ i 1)])))

;;
;;
;;

;; { :code_dir "" } -> engine
(defn engine_create [opts]
  {:code_dir (:code_dir opts)
   :ns (atom {})
   :ctx {"true" true
         "false" false}})

(defn resolve_value [engine ctx name]
  (let [value (get ctx name)]
    (if (some? value)
      value
      (get (deref (:ns engine)) name))))

;; engine * ctx * lines -> value * ctx
(defn- interpret [engine ctx lines]
  (case (first lines)
    "nil" [nil ctx]
    "(" (let [fn_name (second lines)]
          (case fn_name
            "fn" (let [arg_names (string/split (get lines 2) " ")
                       body_line_cnt (parse-int (get lines 3))]
                   (fn [arg_values]
                     (FIXME)))
            "let" (let [name (get lines 2)]
                    [nil (assoc ctx
                                name
                                (first (interpret engine ctx (drop 3 lines))))])
            "do" (FIXME)
            "if" (let [[cond _] (interpret engine ctx (FIXME))]
                   (FIXME))
            (FIXME)))
    [(resolve_value engine ctx (first lines)) ctx]))

(defn- read_lines [path]
  (string/split (slurp path) "\n"))

(defn- load_code [engine name]
  (let [path (str (:code_dir engine) "/" name ".bin")]
    (->>
     (string/split (slurp path) "\n")
     (list_to_tree 0)
     (interpret engine)
     (first))))

(defn- resolve_name [engine name]
  (let [ns (deref (:ns engine))
        value (get ns name)]
    (if (some? value)
      value
      (let [value2 (load_code engine name)]
        (if (some? value2)
          (do
            (reset! (:ns engine) (assoc ns name value2))
            value2)
          (FIXME "Could not find " name " in " (:code_dir engine)))))))

(defn engine_call [engine name args]
  (let [value (resolve_name engine name)]
    (value args)))
