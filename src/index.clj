;; Version: 0.1.0

;; [(, do*, (, def*, G2m03foo, (, fn*, (, x, ), x, ), ), (, G2m03foo, 3, ), )]

;; (defn- make_stream [items]
;;   {:pos 0
;;    :items items})

;; (defn- stream_next [stream]
;;   (assoc stream :pos (+ 1 (cast int (:pos stream)))))

;; (defn- stream_value [stream]
;;   (get (:items stream) (:pos stream)))

;; (defn- match_sexp [stream on_atom on_list]
;;   (if (empty? stream)
;;     nil
;;     (case (first stream)
;;       "(" (on_list (first (rest stream)) (rest (rest stream)))
;;       ")" nil
;;       (on_atom (first stream)))))

;; (defn- call_until_nil [stream f]
;;   (FIXME __LOC__ " " stream))

(def- END "__END__")

(defn- get_args [ctx eval pos stream]
  (let [[form pos2] (eval ctx pos stream)]
    (if (= END form)
      [[] pos2]
      (let [[next_form pos3] (get_args ctx eval pos2 stream)]
        [(concat [form] next_form) pos3]))))

;; Context

(defn- ctx_make []
  {:scope {}
   :ns (atom {})})

(defn- ctx_add_locals [ctx locals]
  (assoc ctx :scope (merge (:scope ctx) locals)))

(defn- ctx_resolve [ctx ^String name]
  (cond
    (some? (re-find (re-pattern "^\\d+$") name)) (parse-int name)
    :else (FIXME __LOC__ name (str ctx))))

;;

(defn- make_locals_from_args [arg_names args]
  (FIXME __LOC__))

(defn eval [ctx ^int pos stream]
  (let [node (get stream pos)]
    ;; (eprintln "EVAL: " node " -> " (get stream (+ pos 1)))
    (cond
      (= "(" node)
      (case (get stream (+ pos 1))
        "fn*" (let [p_names (string/split (get stream (+ pos 2)) " ")
                    [body pos3] (eval ctx (+ pos 3) stream)]
                [(fn [p_values]
                   (let [ctx2 (ctx_add_locals ctx (make_locals_from_args p_names p_values))]
                     (eval ctx2 0 [body])))
                 pos3])
        "def*" (let [name (get stream (+ pos 2))
                     [value pos2] (eval ctx (+ pos 3) stream)]
                 ((FIXME __LOC__ name (str value))))
        "do*" (let [[args pos2] (get_args ctx eval (+ pos 2) stream)]
                [(last args) pos2])
        "if*" (let [[cond pos2] (eval ctx (+ pos 2) stream)]
                (if cond
                  (FIXME __LOC__)
                  ((FIXME __LOC__))))
        (let [[form pos2] (eval ctx (+ 1 pos) stream)
              [args pos3] (get_args ctx eval pos2 stream)]
          [(form args) pos]))

      (= ")" node) [END (+ 1 pos)]
      (= "nil" node) [nil (+ 1 pos)]

      :else [(ctx_resolve ctx node) (+ 1 pos)])))

(defn eval2 [stream]
  (let [[result _] (eval (ctx_make) 0 stream)]
    result))
