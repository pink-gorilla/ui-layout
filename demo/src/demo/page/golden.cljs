(ns demo.page.golden
  (:require
   [reagent.core :as r]
   [reagent.dom.client :as dom]
   [ui.site.ipsum :refer [random-paragraph]]
   ["golden-layout" :refer [GoldenLayout ItemType LayoutConfig ComponentContainer LayoutManager ResolvedComponentItemConfig]]
   [layout.golden :as golden]
   [demo.lib.algo2 :refer [clock]]))

(defn panel-a [_]
  [:div.p-4 "Panel A Content"])

(defn panel-b [_]
  [:div.p-4 "Panel B Content"])

(defn panel-c [_]
  [:div.p-4 "Panel C Content"])

(defn say-hi [opts]
  (let [n (.-name opts)]
    [:div.p-4 "Hello, " n]))
  

(golden/register-reagent-component "clock" clock)
(golden/register-reagent-component "PanelA" panel-a)
(golden/register-reagent-component "sayHi" say-hi)

(def ^LayoutConfig
  myconfig2
  #js {:root #js{:type (.-row ItemType)
                 :content #js [#js {:type "component",
                                    :title "hi!",
                                    :header {:show "top"}
                                    :isClosable false
                                    :componentType "sayHi",
                                    :size "30%"
                                    :componentState #js {:name "Wolfram"}}
                               #js {:type "component"
                                    :componentType "sayHi"
                                    :componentState #js {:name "Isa"}}
                               #js {:type "component"
                                    :componentType "sayHi"
                                    :componentState #js {:name "Florian"}}

                               #js {:type "component"
                                    :componentType "PanelA"
                                    :title "Panel A"}
                               
                               #js {:type "component"
                                    :componentType "clock"
                                    :title "clock A"}
                               
                               #js {:type "component"
                                    :componentType "clock"
                                    :title "clock b"}
                               
                               ]}})


(defn add-hi []
  (let [cmp #js {:type "component" 
                 :componentType "clock" 
                 :componentState #js {:name "Wolfram"}}]
    (golden/add-component cmp)
    ))
 



(defn page [{:keys [route-params query-params handler] :as route}]
  [:div
   {:style {:display "flex"
            :flex-direction "column"
            :width "100vw"
            :height "100vh"
            :max-width "100vw"
            :max-height "100vh"
            :min-width "100vw"
            :min-height "100vh"}}

   [:div {:style {:height "50px"
                  :margin "2px"
                  :display "flex"
                  :flex-direction "row"
                  :align-items "center"}}
    [:button
     {:on-click #(add-hi)
      :style {:border-radius "5px"
              :border "1px solid lightgray"}}
     "add panel"]
    [:button
     {:on-click #(golden/save-layout)
      :style {:border-radius "5px"
              :border "1px solid lightgray"}}
     "save"]]

;
   ; main div
   [:div {:style {:flex-grow "1"
                  ;:position "relative"
                  ;:border "1px solid #ddd"
                  :background-color "lightgreen"
                  :overflow "clip"}}
    ;[:link {:rel "stylesheet" :href "/r/layout.css"}]
    ;[:link {:rel "stylesheet" :href "/r/golden-layout/dist/css/goldenlayout-base.css"}]
    ;[:link {:rel "stylesheet" :href "/r/golden-layout/dist/css/themes/goldenlayout-light-theme.css"}]
    [golden/golden-layout-component
     {:layout-config myconfig2
      :style {:height "100%" :width "100%"}}]]    

   ])
