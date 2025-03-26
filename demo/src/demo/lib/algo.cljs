(ns demo.lib.algo
  (:require
   [reagent.core :as r]
   [ui.flexlayout :refer [component-ui get-data]]
   [ui.frisk :refer [frisk]]))

(defmethod component-ui "panel" [{:keys [id]}]
  (fn [options]
    [:div {:style {:background-color "blue"
                   :width "100%"
                   :height "100%"}}
     "I am panel id: " (str id)
     "options: " (pr-str options)]))

(defmethod component-ui "algo" [{:keys [id]}]
  (fn [options]
    [:div
     "I am an algo"
     [:br]
     "options"
     [:br]
     (pr-str options)]))

(defmethod component-ui "data" [{:keys [id state]}]
  (fn [options]
    (let [data-a (r/atom nil)
          fetch (fn []
                  (println "fetching data..")
                  (js/setTimeout (fn []
                                   (println "data fetched successfully!")
                                   (reset! data-a (get-data state)))
                                 3000))]
      (fn [options]
        [:div
         "I can show the data of the layout:"
         [:br]
         [:button {:on-click #(fetch)} "get-data"]
         [:hr]
         "data"
         (if @data-a
           [frisk @data-a]
           [:p "no data loaded yet."])]))))

(defmethod component-ui "clock" [{:keys [id state]}]
  (println "counter is created.")
  (fn [options]
    (r/with-let [counter (r/atom 0)
                 interval-id (js/setInterval #(swap! counter inc) 1000)]  ; Side effect (runs once)
      [:div "Counter: " @counter]
      (finally 
        (println "counter is destroyed.")
        (js/clearInterval interval-id)))))