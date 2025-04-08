(ns demo.flexlayoutuix
  (:require
   [reagent.core :as r]
   [uix.core :refer [$ defui defhook]]
   [uix.dom]
   ["flexlayout-react" :refer [Layout Model Actions  TabSetNode]]
   [layout.flexlayout.comp.option :refer [selected-id-a clj-option]]
   [layout.flexlayout.store :as store]
   [demo.comp.flowy :refer [server-counter-component server-fortune-component]]
   [demo.comp.demo :refer [button-component unknown-component url-component reagent-component reagent-clock
                           size-component
                           ]]))

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


(defn component-factory [^TabSetNode node]
  (let [component (.getComponent node)
        id (.getId node)
        config (.getConfig node)]
    (println "component factory component: " component " id: " id " config: " config)
    (case component
      "uixcounter" ($ button-component)
      "url" ($ url-component {:url config})
      "reagent-counter" ($ :div (r/as-element [reagent-component]))
      "reagent-clock" ($ :div (r/as-element [reagent-clock]))
      "server-counter" ($ :div (r/as-element [server-counter-component]))
      "server-fortune" ($ :div (r/as-element [server-fortune-component]))
      "clj-options" ($ :div (r/as-element [clj-option]))
      "size" ($ size-component)
      ($ unknown-component))))

(defn handle-action [^js action]
  (when (= Actions.SELECT_TAB (.-type action))
    (let [cell-id (-> action .-data .-tabNode)]
      (println "selected tab: " cell-id)
      (reset! selected-id-a cell-id)
      js/undefined))
                    ; (if (= FlexLayout.Actions.DELETE_TAB (.-type action))
                    ;   (let [cell-id (-> action .-data .-node)]
                    ;     (println "cell deleted: " cell-id)   
                    ;     js/undefined)
                    ;   action
  action)

(defonce state-a (r/atom nil))

(defui flex-layout []
  (let [model (Model.fromJson layout-json)]
    ($ :div
       ($ :link {:href ;"https://unpkg.com/flexlayout-react/style/dark.css"
                 "https://unpkg.com/flexlayout-react/style/light.css"
                 :rel "stylesheet"})
       ($ Layout
          {:model model
           :factory component-factory
           :onAction handle-action
           :ref (fn [el]
                  (reset! state-a {:layout el 
                                   :model model
                                   }))
           }))))

(defn mount []
  (let [root (uix.dom/create-root (js/document.getElementById "app"))]
    (uix.dom/render-root ($ flex-layout) root)))

(defn page-nomenu [_match]
  [:div ($ flex-layout)])

(defn save-layout []
  (println "save-layout..")
  (if @state-a
    (let [_ (println "layout found!")
          ^Model model (:model @state-a)
          model-clj (js->clj (.toJson model))]
      (println "model: " model-clj) 
      (store/save-layout "willie" model-clj))
    (println "no layout found. - not saving")))

(defn header []
  [:div {:style {:background "red"
                 :height "60px"}}
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
    [:div ($ flex-layout)]]])


