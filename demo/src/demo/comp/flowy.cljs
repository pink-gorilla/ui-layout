(ns demo.comp.flowy
  (:require
   [reagent.core :as r]
   [flowy.reflower :refer [task flow]]
   [flowy.reagent :refer [flow->ratom]]
   ;[demo.comp.size :refer [use-parent-size]]
   ))

(defn server-counter-component []
  (r/with-let [f (flow 'demo.counter/counter-fn)
               [counter-a dispose!] (flow->ratom f "waiting ..")]
    [:div
     [:p "counter: " (str @counter-a)]]
    (finally
      (println "Cleanup: stopping flow!")
      (dispose!))))

(defn server-fortune-component []
  (r/with-let [state-a (r/atom "please press the button to get a fortune cookie (multiple presses are ok)")]
    (let [cookie-t (task 'demo.fortune-cookie/get-cookie)
          get-cookie (fn []
                       (cookie-t (fn [c]
                                   (reset! state-a c))
                                 (fn [err]
                                   (println "could not get cookie. error: " err)
                                   (reset! state-a (str "error: " (ex-message err))))))]
      [:div
       [:button {:on-click #(get-cookie)} "get fortune cookie"]
       [:p (str "cookie: " @state-a)]])))


#_(defn size-component []
  (let [[ref {:keys [width height]}] (use-parent-size)]
    [:div {:ref ref
           :style {:width "100%" :height "100%"}}
     [:p (str "Parent size: " width "x" height)]]))