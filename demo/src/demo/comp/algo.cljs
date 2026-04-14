(ns demo.comp.algo
  (:require
   [reagent.core :as r]
   [uix.core :refer [$ defui memo]]
   [uix.dom]
   [layout.flexlayout.comp :refer [component-ui]]
   [layout.flexlayout.core :refer [state-a]]
   [ui.frisk :refer [frisk]]))

(defn algo-ui [options]
  [:div
   "I am an algo"
   [:br]
   "options"
   [:br]
   (pr-str options)])

(defmethod component-ui "algo" [options]
  ($ :div (r/as-element [algo-ui options])))

(defn data-ui [options]
  [:div
   "I can show the data of the layout:"
   [:br]
   (if (:data-a @state-a)
     [frisk @(:data-a @state-a)]
     [:p "no data loaded yet."])])

(defmethod component-ui "data" [options]
  ($ :div (r/as-element [data-ui options])))
