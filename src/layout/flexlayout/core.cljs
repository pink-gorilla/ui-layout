(ns layout.flexlayout.core
   (:require
    [taoensso.timbre :refer-macros [debug info error]]
    [reagent.core :as r]
    [uix.core :refer [$ defui defhook]]
    [uix.dom]
    ["flexlayout-react" :refer [Layout Model Actions  TabSetNode]]
    [layout.flexlayout.comp.option :refer [selected-id-a selected-node-a clj-option]]
    [layout.flexlayout.store :as store]
    ))

(defn handle-action [^js action]
  (when (= Actions.SELECT_TAB (.-type action))
    (let [cell-id (-> action .-data .-tabNode)]
      (println "selected tab: " cell-id)
      (reset! selected-id-a cell-id)
            js/undefined))
  (when (= Actions.DELETE_TAB (.-type action))
     (let [cell-id (-> action .-data .-node)] ; here it is called node, above tabnNode, but both get the id
       (println "cell deleted: " cell-id)   
       js/undefined))
  ;   action
  action)

(defonce state-a (r/atom nil))

(defui flex-layout [{:keys [layout-json component-factory model-name data]
                     :or {model-name "unknown"
                          data {}}}]
  (let [model (Model.fromJson layout-json)]
    ($ :div
       ($ :link {:href "/r/flexlayout-react/style/light.css"
                 :rel "stylesheet"})
       ($ Layout
          {:model model
           :factory component-factory
           :onAction handle-action
           :ref (fn [el]
                  (reset! state-a {:layout el
                                   :model model
                                   :model-name model-name
                                   :data-a (r/atom data)
                                   }))}))))

(defn save-layout []
  (println "save-layout..")
  (if @state-a
    (let [_ (println "layout found!")
          ^Model model (:model @state-a)
          model-name (:model-name @state-a)
          model-clj (js->clj (.toJson model))]
      (println "model: " model-clj)
      (store/save-layout model-name {:data @(:data-a @state-a)
                                     :model model-clj}))
    (println "no layout found. - not saving")))

;; page helper

(def layout-data-model-a (r/atom nil))

(defn flexlayout-model-load [opts]
  (info "flexlayout model load: " opts)
  (store/load-layout->atom layout-data-model-a (get-in opts [:path :model])))

(defn flexlayout-with-header [header flexlayout-opts]
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
    [:div ($ flex-layout flexlayout-opts)]]])

(defn flexlayout-only [flexlayout-opts]
  ($ flex-layout flexlayout-opts))

(defn create-flexlayout-page [{:keys [component-factory header]}]
 (fn [{:keys [parameters] :as match}]
   (if-let [{:keys [model data]} @layout-data-model-a] 
     (let [model-js (clj->js model)
           model-name (get-in parameters [:path :model])
           flexlayout-opts {:component-factory component-factory
                            :layout-json model-js
                            :model-name model-name
                            :data data
                            }]
       (println "model started: " model-name)
       (if header 
         [flexlayout-with-header header flexlayout-opts]
         [flexlayout-only flexlayout-opts]))
     [:div
      "loaded model is nil."
      ])))


