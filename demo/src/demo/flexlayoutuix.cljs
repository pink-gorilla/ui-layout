(ns demo.flexlayoutuix
  (:require
   [reagent.core :as r]
   [uix.core :refer [$ defui defhook]]
   [uix.dom]
   [layout.flexlayout.core :refer [flex-layout save-layout create-flexlayout-page flexlayout-model-load]]
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



(defn header []
  [:div {:style {:background "red"
                 :height "1.5em"}}
   [:button {:on-click #(save-layout)} "save"]])

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

