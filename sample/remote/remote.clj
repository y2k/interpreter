(ns remote)

(def- WORDS
  [["Hello" "Привет"]
   ["World" "Мир"]])

(defn main [event]
  (case event
    :click-hello
    [:column
     (str "Hello [" event "]")
     [:button {:action :click-home}  "Дом"]]

    [:column
     (str "Home [" event "]")
     [:row
      [:button {:action :click-hello} "Привет"]
      [:button {:action :click-world} "Мир"]
      [:button {:action :click-home}  "Дом"]]]))
