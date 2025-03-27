(ns pinkgorilla.input.button)

; for ideas for props for button look at:
; https://github.com/knipferrc/tails-ui/blob/master/src/components/Button.re

(defn ^{:category :control}
  button
  ([text]
   [button {} text])
  ([{:keys [on-click] :as options} text]
   [:button
    (merge
         ; opts that can be overwritten
     {:class "bg-blue-500 hover:bg-blue-700 text-white font-bold rounded" ; py-2 px-4
      :on-click (fn [_ & _] (when on-click (on-click)))}
     (dissoc options :on-click))
    text]))


