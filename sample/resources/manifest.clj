(ns _ (:require ["$LY2K_PACKAGES_DIR/xml/0.2.0/main" :as xml]))

(xml/to_string
 [:manifest {:xmlns:android "http://schemas.android.com/apk/res/android"}
  [:application {:android:icon "@drawable/ic_launcher"
                 :android:label "Interpreter"
                 :android:roundIcon "@drawable/ic_launcher"
                 :android:theme "@style/Theme.Default"}
   [:activity {:android:name "io.github.y2k.main$MainActivity"
               :android:configChanges "orientation|screenSize"
               :android:exported "true"}
    [:intent-filter
     [:action {:android:name "android.intent.action.MAIN"}]
     [:category {:android:name "android.intent.category.LAUNCHER"}]]]]])
