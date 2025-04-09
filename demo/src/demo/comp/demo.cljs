(ns demo.comp.demo
  (:require
   [uix.core :refer [$ defui]]
   [uix.dom]
   [reagent.core :as r]
   [layout.flexlayout.hooks.size :refer [use-size]]
   [layout.flexlayout.comp :refer [component-ui]]))

(defui button [{:keys [on-click children]}]
  ($ :button.btn {:on-click on-click}
     children))

(defui button-component []
  (let [[state set-state!] (uix.core/use-state 0)]
    ;(println "button-component is rendered. state: " state)
    ($ :<>
       ($ button {:on-click #(set-state! dec)} "-")
       ($ :span state)
       ($ button {:on-click #(set-state! inc)} "+"))))

(defmethod component-ui "uixcounter" [opts]
  ($ button-component opts))

(defui unknown-component []
  (println "unknown is rendered.")
  ($ :div "unknown component"))

(defui url-component [{:keys [url]}]
  ;(println "url-component: " url)
  ($ :iframe {:title "url-component"
              :src url
              :style {:display "block"
                      :border "none"
                      :boxSizing "border-box"}
              :width "100%"
              :height "100%"}))

(defmethod component-ui "url" [opts]
  ;(println "URL OPTS: " opts)
  ($ url-component {:url (:config opts)}))

(defn reagent-component []
  (r/with-let [c (r/atom 0)]
    ;(println "reagent-counter gets rendered.")
    [:div
     [:p "I am a reagent counter"]
     [:button {:on-click #(swap! c dec)} "-"]
     [:span @c]
     [:button {:on-click #(swap! c inc)} "+"]]))

(defmethod component-ui "reagent-counter" [opts]
  ($ :div (r/as-element [reagent-component])))
  
(defn reagent-clock [{:keys [state]}]
  (r/with-let [_ (println "reagent-clock is created.")
               counter (r/atom 0)
               interval-id (js/setInterval #(swap! counter inc) 1000)]  ; Side effect (runs once)
    (let [c (or (get-in @state [:value :background-color]) "black")]
      [:div {:style {:background-color c}}
       "Clock: " @counter])
    (finally
      (println "clock is destroyed.")
      (js/clearInterval interval-id))))

(defmethod component-ui "reagent-clock" [opts]
  ($ :div (r/as-element [reagent-clock opts])))
 

(defui size-component []
    (let [[ref size] (use-size)]
      ($ :div {:ref ref
               :style {:width "100%" :height "100%"}}
         ($ :p (str "Parent size: " (pr-str size))))
      ))

(defmethod component-ui "size" [opts]
 ($ size-component))

 