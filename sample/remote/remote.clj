(ns remote)

(def- WORDS
  [["Hello" "Привет"]
   ["World" "Мир"]])

(defn- foo [x] x)

(defn main [event]
  [:column
   (str "Hello [" event "]")
   [:row
    [:button {:action :click-hello} "Привет"]
    [:button {:action :click-world} "Мир"]
    [:button {:action :click-home}  "Дом"]]])
