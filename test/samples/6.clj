(defonce count_state (atom 2))

(defn main []
  (reset! user/count_state (+ 1 (deref user/count_state)))
  (str "a" (deref user/count_state) "b"))

(main)