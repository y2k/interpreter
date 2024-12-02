(def count_state (atom 1))

(defn main []
  (reset! user/count_state (+ 1 (deref user/count_state)))
  (str "foo" (deref user/count_state) "bar"))

(main)
