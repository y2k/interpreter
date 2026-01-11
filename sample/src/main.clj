(ns main
  (:require [interpreter :as i])
  (:import [android.app Activity]
           [android.content Intent Context]
           [android.view View]
           [android.widget TextView Button LinearLayout]
           [android.os Bundle]
           [java.nio.file Files Path]))

(gen-class
 :name MainActivity
 :extends Activity
 :state state
 :init init
 :methods [[^Override onCreate [Bundle] void]])

(defn- _init [] (atom {:engine nil :history ""}))

(defn- ^Button make_sample_button [^Context self ^TextView chat_view state_atom ^String fn_name ^String args_str]
  (let [btn (Button. self)]
    (.setText btn (str fn_name " " args_str))
    (.setOnClickListener
     btn
     (reify android.view.View$OnClickListener
       (^void onClick [_ ^android.view.View v]
         (do
           (let [state (deref state_atom)
                 args (if (= args_str "")
                        []
                        [(Integer/parseInt args_str)])
                 result (i/engine_call (:engine state) "remote.main" [0])
                 old_history (:history state)
                 new_line (str "> " fn_name " " args_str "\n" result "\n\n")
                 new_history (str old_history new_line)]
             (swap! state_atom (fn [s] (assoc s :history new_history)))
             (.setText chat_view new_history))))))
    btn))

(defn- _onCreate [^MainActivity self _]
  (let [state_atom (.-state self)
        engine (i/engine_create {:code_dir (str (.getExternalFilesDir self nil))})
        main_layout (LinearLayout. self)
        chat_view (TextView. self)
        ^Button btn1 (make_sample_button self chat_view state_atom "remote.main" "0")
        ^Button btn2 (make_sample_button self chat_view state_atom "remote.main" "1")
        ^Button btn3 (make_sample_button self chat_view state_atom "remote.main" "2")]

    (.setOrientation main_layout 1)
    (.setText chat_view "Ready\n\n")

    (.addView main_layout chat_view)
    (.addView main_layout btn1)
    (.addView main_layout btn2)
    (.addView main_layout btn3)

    (swap! state_atom (fn [s] (assoc s :engine engine)))
    (.setContentView self main_layout))
  nil)
