(ns demo.comp.demo
   (:require
    [uix.core :refer [$ defui]]
    [uix.dom]
    [reagent.core :as r]))


(defui button [{:keys [on-click children]}]
  ($ :button.btn {:on-click on-click}
     children))

(defui button-component []
  (let [[state set-state!] (uix.core/use-state 0)]
    (println "button-component is rendered. state: " state)
    ($ :<>
       ($ button {:on-click #(set-state! dec)} "-")
       ($ :span state)
       ($ button {:on-click #(set-state! inc)} "+"))))

(defui unknown-component []
  (println "unknown is rendered.")
  ($ :div "unknown component"))

(defui url-component [{:keys [url]}]
  (println "url-component: " url)
  ($ :iframe {:title "url-component"
              :src url
              :style {:display "block"
                      :border "none"
                      :boxSizing "border-box"}
              :width "100%"
              :height "100%"}))

(defn reagent-component []
  (r/with-let [c (r/atom 0)]
    (println "reagent-counter gets rendered.")
    [:div
     [:p "I am a reagent counter"]
     [:button {:on-click #(swap! c dec)} "-"]
     [:span @c]
     [:button {:on-click #(swap! c inc)} "+"]]))

(defn reagent-clock []
  (r/with-let [_ (println "reagent-clock is created.")
               counter (r/atom 0)
               interval-id (js/setInterval #(swap! counter inc) 1000)]  ; Side effect (runs once)
    [:div "Clock: " @counter]
    (finally
      (println "clock is destroyed.")
      (js/clearInterval interval-id))))
