(ns main
  (:require [interpreter :as i])
  (:import [android.app Activity]
           [android.content Intent Context]
           [android.view View ViewGroup WindowInsets]
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

(defn- apply-insets [^View view]
  (.setOnApplyWindowInsetsListener
   view
   (reify android.view.View$OnApplyWindowInsetsListener
     (^android.view.WindowInsets onApplyWindowInsets [_ ^android.view.View the-view ^android.view.WindowInsets the-insets]
       (let [^int type-mask (WindowInsets/Type.systemBars)
             ^android.graphics.Insets system-bars (.getInsetsIgnoringVisibility the-insets type-mask)
             ^int left (.-left system-bars)
             ^int top (.-top system-bars)
             ^int right (.-right system-bars)
             ^int bottom (.-bottom system-bars)]
         (.setPadding the-view left top right bottom)
         the-insets))))
  nil)

(defn- _onCreate [^MainActivity self _]
  (let [state-atom (.-state self)
        engine (i/engine-create {:code-dir (str (.getExternalFilesDir self nil))})
        main-layout (LinearLayout. self)]

    (.setOrientation main-layout 1)
    (.setBackgroundColor main-layout -15198182)
    (swap! state-atom (fn [s] (assoc (assoc (assoc s :engine engine) :container main-layout) :self self)))

    (apply-insets main-layout)
    (refresh-ui state-atom)
    (.setContentView self main-layout))
  nil)
