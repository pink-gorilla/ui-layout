(ns demo.page.flexlayoutuix
  (:require
   [reagent.core :as r]
   [uix.core :refer [$ defui defhook]]
   [uix.dom]
   [layout.flexlayout.core :refer [flex-layout save-layout create-flexlayout-page flexlayout-model-load add-node]]
   [layout.flexlayout.comp.option] ; side effects
   [demo.comp.flowy] ; side effects
   [demo.comp.demo ] ; side effects
   ))

(def layout-json
  #js {:global
       #js {:tabEnableClose true}
       :layout
       #js {:type "row"
            :children #js [#js {:type "tabset"
                                :children #js [#js {:type "tab" :name "uixcount1" :component "uixcounter"}
                                               #js {:type "tab" :name "uixcount2" :component "uixcounter"}
                                               #js {:type "tab" :name "wikipedia" :component "url"
                                                    :config "https://en.wikipedia.org/wiki/Main_Page"}
                                               #js {:type "tab" :name "uixcount3" :component "uixcounter"}
                                               #js {:type "tab" :name "rcount1" :component "reagent-counter"}
                                               #js {:type "tab" :name "rcount2" :component "reagent-counter"}
                                               #js {:type "tab" :name "rclock1" :component "reagent-clock"}
                                               #js {:type "tab" :name "rclock2" :component "reagent-clock"}
                                               #js {:type "tab" :name "scounter1" :component "server-counter"}
                                               #js {:type "tab" :name "scounter2" :component "server-counter"}
                                               #js {:type "tab" :name "scounter3" :component "server-counter"}
                                               #js {:type "tab" :name "sfortune1" :component "server-fortune"}
                                               #js {:type "tab" :name "sfortune2" :component "server-fortune"}
                                               #js {:type "tab" :name "size1" :component "size"}
                                               #js {:type "tab" :name "size2" :component "size"}]}]}

       :borders
       #js [#js{:type "border"
                 ;:selected 13,
                :size 350
                :location "left"
                :children #js [#js {:type "tab"
                                    :id "options"
                                    :name "Options"
                                    :component "clj-options"
                              ;:icon "/r/quanta/adjustments-vertical.svg"
                                    :enableClose false}]}]})


(defn mount []
  (let [root (uix.dom/create-root (js/document.getElementById "app"))]
    (uix.dom/render-root ($ flex-layout {:layout-json layout-json}) root)))

(defn page-nomenu [_match]
  [:div ($ flex-layout)])


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
                           :value {:background-color "yellow"}}}
   :server-fortune {:type "tab" :name "sfortune" :component "server-fortune"}
   :server-counter {:type "tab" :name "scounter" :component "server-counter"}
   :option {:type "tab" :name "option" :component "clj-options2"}
   
   })

(defn add [component-key]
  (add-node (component-key components)))

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
   ])

(defn page [_match]
  [:div  {:style {:height "100vh"
                  :width "100vw"
                  :top "0"
                  :left "0"
                  :margin "0"
                  :padding "0"
                  :display "flex"
                  :flex-direction "column"
                  :flex-grow 1}}
   [:div {:dir "ltr"
          :style {:margin "2px"
                  :display "flex"
                  :align-items "center"}}
    [header]]
   [:div {:style {:display "flex"
                  :flex-grow "1"
                  :position "relative"
                  :border "1px solid #ddd"}}
    [:div ($ flex-layout {:layout-json layout-json})]]])


(def flexlayout-page
  (create-flexlayout-page {:header header}))

