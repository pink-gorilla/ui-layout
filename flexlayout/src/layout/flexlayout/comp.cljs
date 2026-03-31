(ns layout.flexlayout.comp
  (:require
   [uix.core :refer [$ defui defhook]]
   [uix.dom]))

(defmulti component-ui  (fn [e] (:component e)))

(defui unknown-component [{:keys [component id config]}]
  (println "unknown is rendered.")
  ($ :div (str "unknown component: " component)))

(defmethod component-ui :default [opts]
  ($ unknown-component opts))