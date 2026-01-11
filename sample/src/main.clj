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

(declare refresh_ui)
(declare render_ui)

(defn- render_column [^Context ctx ui_tree state_atom]
  (let [layout (LinearLayout. ctx)]
    (.setOrientation layout 1)
    (reduce
     (fn [^LinearLayout l child]
       (let [^View v (render_ui ctx child state_atom)]
         (.addView l v)
         l))
     layout
     (rest ui_tree))))

(defn- render_row [^Context ctx ui_tree state_atom]
  (let [layout (LinearLayout. ctx)]
    (.setOrientation layout 0)
    (reduce
     (fn [^LinearLayout l child]
       (let [^View v (render_ui ctx child state_atom)]
         (.addView l v)
         l))
     layout
     (rest ui_tree))))

(defn- render_button [^Context ctx ui_tree state_atom]
  (let [opts (second ui_tree)
        ^String label (get ui_tree 2)
        action (:action opts)
        btn (Button. ctx)]
    (.setText btn label)
    (.setOnClickListener
     btn
     (reify android.view.View$OnClickListener
       (^void onClick [_ ^android.view.View v]
         (let [state (deref state_atom)
               engine (:engine state)]
           (i/engine_call engine "remote.main" [action])
           (refresh_ui state_atom)))))
    btn))

(defn- render_text [^Context ctx ^String text]
  (let [tv (TextView. ctx)]
    (.setText tv text)
    tv))

(defn- ^View render_ui [^Context ctx ui_tree state_atom]
  (if (string? ui_tree)
    (render_text ctx ui_tree)
    (let [tag (first ui_tree)]
      (cond
        (= tag :column) (render_column ctx ui_tree state_atom)
        (= tag :row) (render_row ctx ui_tree state_atom)
        (= tag :button) (render_button ctx ui_tree state_atom)
        :else (render_text ctx (str "Unknown: " tag))))))

(defn- refresh_ui [state_atom]
  (let [state (deref state_atom)
        engine (:engine state)
        ^ViewGroup container (:container state)
        ^Context self (:self state)
        ui_tree (i/engine_call engine "remote.main" [0])
        ^View root_view (render_ui self ui_tree state_atom)]
    (.removeAllViews container)
    (.addView container root_view)
    nil))

(defn- _onCreate [^MainActivity self _]
  (let [state_atom (.-state self)
        engine (i/engine_create {:code_dir (str (.getExternalFilesDir self nil))})
        main_layout (LinearLayout. self)]

    (.setOrientation main_layout 1)
    (swap! state_atom (fn [s] (assoc (assoc (assoc s :engine engine) :container main_layout) :self self)))

    (refresh_ui state_atom)
    (.setContentView self main_layout))
  nil)
