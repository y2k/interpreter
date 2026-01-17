(ns app
  (:require [interpreter :as i]
            [ui :as ui])
  (:import [android.app Activity]
           [android.content Context]
           [android.view View ViewGroup WindowInsets]
           [android.widget LinearLayout ScrollView]
           [android.os Bundle]))

(gen-class
 :name MainActivity
 :extends Activity
 :state state
 :init init
 :methods [[^Override onCreate [Bundle] void]])

(defn- _init [] (atom {:engine nil :container nil :self nil}))

(declare refresh-ui)

(defn- handle-click [state-atom ctx click]
  (let [state (deref state-atom)
        engine (:engine state)
        ^ViewGroup container (:container state)
        ui-tree (i/engine-call engine "script.main" [click])
        ^View root-view (ui/render-ui ctx ui-tree (fn [c] (handle-click state-atom ctx c)))]
    (.addView container root-view)
    nil))

(defn- refresh-ui [state-atom]
  (let [state (deref state-atom)
        engine (:engine state)
        ^ViewGroup container (:container state)
        ^Context self (:self state)
        ui-tree (i/engine-call engine "script.main" [{:click :home}])
        ^View root-view (ui/render-ui self ui-tree (fn [c] (handle-click state-atom self c)))]
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
        scroll-view (ScrollView. self)
        content-layout (LinearLayout. self)]

    (.setOrientation content-layout 1)
    (.addView scroll-view content-layout)
    (.setBackgroundColor scroll-view -15198182)
    (swap! state-atom (fn [s] (assoc (assoc (assoc s :engine engine) :container content-layout) :self self)))

    (apply-insets scroll-view)
    (refresh-ui state-atom)
    (.setContentView self scroll-view))
  nil)
