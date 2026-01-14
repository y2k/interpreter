(ns ui
  (:import [android.content Context]
           [android.view View ViewGroup]
           [android.widget TextView Button LinearLayout]))

(declare render-ui)

(defn- render-column [^Context ctx ui-tree on-click]
  (let [layout (LinearLayout. ctx)]
    (.setOrientation layout 1)
    (reduce
     (fn [^LinearLayout l child]
       (let [^View v (render-ui ctx child on-click)]
         (.addView l v)
         l))
     layout
     (rest ui-tree))))

(defn- render-row [^Context ctx ui-tree on-click]
  (let [layout (LinearLayout. ctx)]
    (.setOrientation layout 0)
    (reduce
     (fn [^LinearLayout l child]
       (let [^View v (render-ui ctx child on-click)]
         (.addView l v)
         l))
     layout
     (rest ui-tree))))

(defn- render-button [^Context ctx ui-tree on-click]
  (let [opts (second ui-tree)
        ^String label (get ui-tree 2)
        click (get opts :click)
        btn (Button. ctx)]
    (.setText btn label)
    (.setOnClickListener
     btn
     (reify android.view.View$OnClickListener
       (^void onClick [_ ^android.view.View v]
         (apply on-click [click]))))
    btn))

(defn- render-text [^Context ctx ^String text]
  (let [tv (TextView. ctx)]
    (.setText tv text)
    (.setTextSize tv 24f)
    tv))

(defn ^View render-ui [^Context ctx ui-tree on-click]
  (if (string? ui-tree)
    (render-text ctx ui-tree)
    (let [tag (first ui-tree)]
      (cond
        (= tag :column) (render-column ctx ui-tree on-click)
        (= tag :row) (render-row ctx ui-tree on-click)
        (= tag :button) (render-button ctx ui-tree on-click)
        (= tag :text) (render-text ctx (second ui-tree))
        :else (render-text ctx (str "Unknown: " tag))))))
