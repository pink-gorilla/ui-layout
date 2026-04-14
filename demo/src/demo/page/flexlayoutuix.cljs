(ns demo.page.flexlayoutuix
  (:require
   [reagent.core :as r]
   [uix.core :refer [$ defui defhook]]
   [uix.dom]
   [nano-id.core :refer [nano-id]]
   [layout.flexlayout.core :refer [flex-layout save-layout flexlayout-page flexlayout-model-load
                                   add-node set-active-tabset move-node]]
   ; side effects
   [layout.flexlayout.comp.option] ; side effects
   [demo.comp.flowy] ; side effects
   [demo.comp.demo] ; side effects
   [demo.comp.header] ; side effects
   [demo.comp.algo] ; side effects
   ))

(def components
  {:wiki {:type "tab" :name "wikipedia" :component "url"
          :config "https://en.wikipedia.org/wiki/Main_Page"}
   :uix-counter {:type "tab" :name "uixcount" :component "uixcounter"}
   :size {:type "tab" :name "size" :component "size"}
   :reagent-counter {:type "tab" :name "rcount" :component "reagent-counter"}
   :reagent-clock {:type "tab"
                   :name "rclock"
                   :component "reagent-clock"
                   :state {:edit [{:type :select :path :background-color :name "background-color"
                                   :spec ["red" "green" "blue" "white" "yellow" "orange"]}]
                           :value {:background-color "green"}}}
   :server-fortune {:type "tab" :name "sfortune" :component "server-fortune"}
   :server-counter {:type "tab" :name "scounter" :component "server-counter"}
   :option {:type "tab" :name "option" :component "clj-options2"}
   :algo {:component "algo"
          :icon "/r/images/article.svg",
          :name "algo1"
          :state {:edit [{:type :select
                          :path [0 :asset],
                          :name "asset",
                          :spec
                          ["EUR/USD" "USD/CHF" "GBP/USD" "USD/SEK" "USD/NOK" "USD/CAD" "USD/JPY"
                           "AUD/USD" "NZD/USD" "USD/MXN" "USD/ZAR" "EUR/JPY" "EUR/CHF" "EUR/GBP" "GBP/JPY"]}
                         {:type :select :path [2 :trailing-n], :name "DailyLoad#", :spec [2 5 10 20 30 50 80 100 120 150]}
                         {:type :select :path [2 :atr-n], :name "dATR#", :spec [5 10 20 30]}
                         {:type :select :path [2 :percentile], :name "dPercentile", :spec [10 20 30 40 50 60 70 80 90]}
                         {:type :select :path [2 :step], :name "dStep", :spec [0.001 1.0E-4 4.0E-5]}
                         {:type :select :path [4 :max-open-close-over-low-high], :name "doji-co/lh max", :spec [0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9]}]
                  :value {[0 :asset] "USD/JPY",
                          [2 :trailing-n] 120,
                          [2 :atr-n] 10,
                          [2 :percentile] 70,
                          [2 :step] 1.0E-4,
                          [4 :max-open-close-over-low-high] 0.3}}}
   :data {:type "tab" :name "data" :component "data"}
   :sublayout  {:type "row"
                :children [{:type "tabset"
                            :weight 50
                            :selected 0
                            :children [{:type "tab"
                                        :name "ss"
                                        :component "rcount"
                                        :enableClose false}]}
                           {:type "tabset"
                            :weight 50
                            :selected 0
                            :children [{:type "tab"
                                        :name "ss2"
                                        :component "reagent-clock"
                                        :enableClose false}]}]}})

(defn add [component-key]
  (add-node (component-key components)))

;https://github.com/powerdragonfire/flexycakes/blob/master/src/model/Actions.ts

(defn add-sublayout []
  ; try to replicate what the user could do in the ui.
  ; however setting the active tabset to an id that does not exist will not create the tabset.
  ; so currently there is no way to create a tabset via code.
  (let [nodes [{:type "tab" :name "ss1" :component "reagent-clock"}
               {:type "tab" :name "ss2" :component "reagent-clock"}]
        container-id (nano-id 5)
        nodes (map (fn [node]
                     (assoc node :id (nano-id 5))) nodes)]
    (doall (map add-node nodes))
    (set-active-tabset container-id)
    (doall (map (fn [node]
                  (move-node (:id node) container-id :bottom)
                  ) nodes))
    nil))

(defn header []
  [:div {:style {:background "red"
                 :height "1.5em"}}
   [:button {:on-click #(save-layout)} "save"]
   [:button {:on-click #(add :wiki)} "wiki"]
   [:button {:on-click #(add :uix-counter)} "uix-counter"]
   [:button {:on-click #(add :size)} "size"]
   [:button {:on-click #(add :reagent-counter)} "reagent-counter"]
   [:button {:on-click #(add :reagent-clock)} "reagent-clock"]
   [:button {:on-click #(add :server-fortune)} "server-fortune"]
   [:button {:on-click #(add :server-counter)} "server-counter"]
   [:button {:on-click #(add :option)} "option"]
   [:button {:on-click #(add :algo)} "algo"]
   [:button {:on-click #(add :data)} "data"]
   [:button {:on-click #(add :sublayout)} "sublayout"]
   [:button {:on-click #(add-sublayout)} "sublayout2"]])

