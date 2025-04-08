(ns demo.flexlayoutuix
  (:require
   [reagent.core :as r]
   [uix.core :refer [$ defui defhook]]
   [uix.dom]
   ["flexlayout-react" :refer [Layout Model Actions  TabSetNode]]
   [layout.flexlayout.comp.option :refer [selected-id-a clj-option]]
   [demo.comp.flowy :refer [server-counter-component server-fortune-component
                            ;size-component
                            ]]
   [demo.comp.demo :refer [button-component unknown-component url-component reagent-component reagent-clock]]))

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

(def layout-model
  ;(.fromJson Model layout-json)
  (Model.fromJson layout-json))

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
      ;"size" ($ size-component)
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

(defui app []
  ($ :div
     ($ :link {:href ;"https://unpkg.com/flexlayout-react/style/dark.css"
               "https://unpkg.com/flexlayout-react/style/light.css"
               :rel "stylesheet"})
     ($ Layout
        {:model layout-model
         :factory component-factory
         :onAction handle-action})))

(defn mount []
  (let [root (uix.dom/create-root (js/document.getElementById "app"))]
    (uix.dom/render-root ($ app) root)))

(defn page [_match]
  [:div ($ app)])





