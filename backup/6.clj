(defonce count_state (atom 2))

(defn main []
  (reset! count_state (+ 1 (deref count_state)))
  (str "a" (deref count_state) "b"))

(main)