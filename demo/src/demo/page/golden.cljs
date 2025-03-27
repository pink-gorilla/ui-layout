(ns demo.page.golden
  (:require
   [layout.golden :as golden]
   [demo.lib.algo2 :refer [clock]]))

(defn panel-a [_]
  [:div.p-4 "Panel A Content"])

(defn say-hi [opts]
  (let [n (.-name opts)]
    [:div.p-4 "Hello, " n]))

(golden/register-reagent-component "clock" clock)
(golden/register-reagent-component "PanelA" panel-a)
(golden/register-reagent-component "sayHi" say-hi)

(def myconfig3
  {:root {:type "row"
          :content [{:type "component" :componentType "sayHi",:componentState  {:name "Wolfram"}
                     :title "hi!",
                     :header {:show "top"}
                     :isClosable false
                     :size "30%"}
                    {:type "component" :componentType "sayHi" :componentState  {:name "Isa"}}
                    {:type "component" :componentType "sayHi" :componentState  {:name "Florian"}
                     :id "fred-feuerstein"}
                    {:type "column"
                     :id "master-yoda-column"
                     :content  [{:type "component" :componentType "PanelA"
                                 :title "Panel A"}
                                {:type "component" :componentType "clock"
                                 :title "clock A"}
                                {:type "component" :componentType "clock"
                                 :title "clock b"}]}]}})

(defn add-hi []
  (let [cmp {:type "component" :componentType "clock" :componentState {:name "Wolfram"}}]
    (golden/add-component cmp)))

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
     "save"]
    [:button
     {:on-click #(golden/load-layout myconfig3)
      :style {:border-radius "5px"
              :border "1px solid lightgray"}}
     "load layout"]]
   ; main div
   [golden/golden-layout-component
    {:layout-config myconfig3
     :style {:flex-grow "1"
             :background-color "lightgreen"
             :overflow "clip"
             :height "100%" :width "100%"}}]])
