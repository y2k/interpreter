(ns main)

(def- WORDS
  [["Hello" "Привет"]
   ["World" "Мир"]])

(defn- foo [x] x)

(defn main [event]
  [:column
   "Hello"
   [:row
    [:button "Привет"]
    [:button "Мир"]
    [:button "Дом"]]])
