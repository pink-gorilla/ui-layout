(ns layout.flexlayout.comp.option
  (:require 
   [reagent.core :as r]
   [options.flowy.core :refer [clj-option-ui]]))


(defonce selected-id-a (r/atom nil))

(defn clj-option [_]
  [:div
   [:hr]
   [:p "selected tab id:"]
   [:p (pr-str @selected-id-a)]
   
   [clj-option-ui {:id 66
                   :class "options-label-left"
                   :style {:background-color "yellow"
                           :max-height "400px"
                           :max-width "400px"
                                                            ;:min-height "400px"
                                                            ;:min-width "400px"
                           :height "400px"
                           :width "400px"}}]])

