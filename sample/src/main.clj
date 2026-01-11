(ns main
  (:require [interpreter :as i])
  (:import [android.app Activity]
           [android.content Intent Context]
           [android.view View ViewGroup]
           [android.widget TextView Button LinearLayout]
           [android.os Bundle]
           [java.nio.file Files Path]))

(gen-class
 :name MainActivity
 :extends Activity
 :state state
 :init init
 :methods [[^Override onCreate [Bundle] void]])

(defn- _init [] (atom {:engine nil :container nil :self nil}))

(declare refresh-ui)
(declare render-ui)

(defn- render-column [^Context ctx ui-tree state-atom]
  (let [layout (LinearLayout. ctx)]
    (.setOrientation layout 1)
    (reduce
     (fn [^LinearLayout l child]
       (let [^View v (render-ui ctx child state-atom)]
         (.addView l v)
         l))
     layout
     (rest ui-tree))))

(defn- render-row [^Context ctx ui-tree state-atom]
  (let [layout (LinearLayout. ctx)]
    (.setOrientation layout 0)
    (reduce
     (fn [^LinearLayout l child]
       (let [^View v (render-ui ctx child state-atom)]
         (.addView l v)
         l))
     layout
     (rest ui-tree))))

(defn- render-button [^Context ctx ui-tree state-atom]
  (let [opts (second ui-tree)
        ^String label (get ui-tree 2)
        action (:action opts)
        btn (Button. ctx)]
    (.setText btn label)
    (.setOnClickListener
     btn
     (reify android.view.View$OnClickListener
       (^void onClick [_ ^android.view.View v]
         (let [state (deref state-atom)
               engine (:engine state)]
           (i/engine-call engine "remote.main" [action])
           (refresh-ui state-atom)))))
    btn))

(defn- render-text [^Context ctx ^String text]
  (let [tv (TextView. ctx)]
    (.setText tv text)
    tv))

(defn- ^View render-ui [^Context ctx ui-tree state-atom]
  (if (string? ui-tree)
    (render-text ctx ui-tree)
    (let [tag (first ui-tree)]
      (cond
        (= tag :column) (render-column ctx ui-tree state-atom)
        (= tag :row) (render-row ctx ui-tree state-atom)
        (= tag :button) (render-button ctx ui-tree state-atom)
        :else (render-text ctx (str "Unknown: " tag))))))

(defn- refresh-ui [state-atom]
  (let [state (deref state-atom)
        engine (:engine state)
        ^ViewGroup container (:container state)
        ^Context self (:self state)
        ui-tree (i/engine-call engine "remote.main" [0])
        ^View root-view (render-ui self ui-tree state-atom)]
    (.removeAllViews container)
    (.addView container root-view)
    nil))

(defn- apply-insets [^View view ^Activity activity]
  (let [^android.content.res.Resources res (.getResources activity)
        ^int status-bar-id (.getIdentifier res "status_bar_height" "dimen" "android")
        ^int nav-bar-id (.getIdentifier res "navigation_bar_height" "dimen" "android")
        ^int status-height (if (> status-bar-id 0) (.getDimensionPixelSize res status-bar-id) 0)
        ^int nav-height (if (> nav-bar-id 0) (.getDimensionPixelSize res nav-bar-id) 0)]
    (.setPadding view 0 status-height 0 nav-height)
    nil))

(defn- _onCreate [^MainActivity self _]
  (let [state-atom (.-state self)
        engine (i/engine-create {:code-dir (str (.getExternalFilesDir self nil))})
        main-layout (LinearLayout. self)]

    (.setOrientation main-layout 1)
    (swap! state-atom (fn [s] (assoc (assoc (assoc s :engine engine) :container main-layout) :self self)))

    (apply-insets main-layout self)
    (refresh-ui state-atom)
    (.setContentView self main-layout))
  nil)
