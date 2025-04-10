(ns layout.flexlayout.comp.option
  (:require
   [reagent.core :as r]
   [reagent.ratom :as ratom]
   [uix.core :refer [$ defui]]
   [options.core :refer [options-ui2]]
   [options.flowy.core :refer [clj-option-ui]]
   [layout.flexlayout.comp :refer [component-ui]]
   [layout.flexlayout.core :refer [selected-id-a state-a]]))

(defn subscribe-selected-state []
  (ratom/make-reaction
   (fn [] (get @(:data-a @state-a) @selected-id-a))))

(defn clj-option [_]
  (r/with-let [selected-state-a (subscribe-selected-state)
               value-a (ratom/make-reaction
                        (fn []
                          (if-let [value (:value @selected-state-a)]
                            value
                            {})))]
    (let [{:keys [edit value]} @selected-state-a
          set-fn (fn [path v]
                   (println "setting path: " path " to: " v)
                   (let [data-a (:data-a @state-a)
                         selected-id @selected-id-a]
                     (when (and data-a selected-id)
                       (swap! data-a assoc-in [selected-id :value path] v))))]
      [:div
       ;[:hr]
       ;[:p "selected tab id:"]
       ;[:p (pr-str @selected-id-a)]

       ;[:p "state:"]
       ;[:p (pr-str @selected-state-a)]

       (if (and edit value)
         [options-ui2 {:class "bg-blue-300 options-label-left"
                       :style {:width "400px"
                               ;:height "400px"
                               }
                       :edit edit
                       :state value-a
                       :set-fn set-fn}]
         [:div "no options for selected tab"])

       #_[clj-option-ui {:id 66
                         :class "options-label-left"
                         :style {:background-color "yellow"
                                 :max-height "400px"
                                 :max-width "400px"
                                                            ;:min-height "400px"
                                                            ;:min-width "400px"
                                 :height "400px"
                                 :width "400px"}}]])))

(defmethod component-ui "clj-options" [opts]
  ($ :div (r/as-element [clj-option])))

(defn clj-option2 [_]
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

(defmethod component-ui "clj-options2" [opts]
  ($ :div (r/as-element [clj-option2])))