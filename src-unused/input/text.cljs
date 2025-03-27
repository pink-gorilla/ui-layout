(ns pinkgorilla.input.text
  (:require
   [taoensso.timbre :as timbre :refer-macros [trace debug debugf info infof warn error]]))

(defn ^{:category :control} textbox [{:keys [on-change value] :as opts}]
  [:input
   (merge
      ; opts that can be overwritten
    {:placeholder "Placeholder"
     :class "px-2 py-1 placeholder-gray-400 text-gray-700 relative bg-white bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
     ; add props from opts 
    opts
     ; opts that cannot be overwritten
    {:type "text"
     :value (if (nil? value) "" value)
     :on-change (fn [e]
                  (let [v (-> e .-target .-value)]
                     ;(info "textbox value: " v)
                    (on-change v)))})])

