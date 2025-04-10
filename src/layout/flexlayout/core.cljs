(ns layout.flexlayout.core
  (:require
   [taoensso.timbre :refer-macros [debug info error]]
   [reagent.core :as r]
   [reagent.ratom :as ratom]
   [nano-id.core :refer [nano-id]]
   [uix.core :refer [$ defui defhook]]
   [uix.dom]
   ["flexlayout-react" :refer [Layout Model Actions  TabSetNode]]
   [layout.flexlayout.store :as store]
   [layout.flexlayout.comp :refer [component-ui]]))

(defonce state-a (r/atom {:data-a (r/atom {}) ; make sure subscrie-state always has defined value (being too careful here?)
                          }))

(defn subscribe-state [id]
  (let [data-a (:data-a @state-a)]
    (ratom/make-reaction
     (fn [] (get @data-a id)))))

(defn component-factory [^TabSetNode node]
  (let [component (.getComponent node)
        id (.getId node)
        config (.getConfig node)
        opts {:component component
              :id id
              :config config
              :state (subscribe-state id)}]
    ;(println "component factory component: " opts)
    (component-ui opts)))

(defonce selected-id-a (r/atom nil))

(defn handle-action [^js action]
  (when (= Actions.SELECT_TAB (.-type action))
    (let [cell-id (-> action .-data .-tabNode)]
      (println "selected tab: " cell-id)
      (reset! selected-id-a cell-id)
      js/undefined))
  (when (= Actions.DELETE_TAB (.-type action))
    (let [data-a (:data-a @state-a)
          cell-id (-> action .-data .-node)] ; here it is called node, above tabnNode, but both get the id
      (println "cell deleted: " cell-id)
      (swap! data-a dissoc cell-id)
      js/undefined))
  ;   action
  action)

(defui flex-layout [{:keys [layout-json category model-name data]
                     :or {category "default"
                          model-name "unknown"
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
                                   :category category
                                   :model-name model-name
                                   :data-a (r/atom data)}))}))))

(defn add-node [{:keys [id state]
                 :or {id (nano-id 5)}
                 :as node}]
  (let [model (:model @state-a)
        layout (:layout @state-a)
        data-a (:data-a @state-a)
        node (assoc node :id id)]
  ;  {:type "tab" :name "wikipedia" :component "url"
  ;                              :config "https://en.wikipedia.org/wiki/Main_Page"}
    (println "adding new node to layout..")
    (when state
      (swap! data-a assoc id state))
    (let [tabset (or (.getActiveTabset  ^Model model)
                     (.getFirstTabSet  ^Model model))]
      (.addTabToTabSet
       ^Model
       layout
       (.getId ^TabSetNode tabset)
       (clj->js node)))))

(defn save-layout []
  (println "save-layout..")
  (if @state-a
    (let [_ (println "layout found!")
          ^Model model (:model @state-a)
          category (:category @state-a)
          model-name (:model-name @state-a)
          model-clj (js->clj (.toJson model))]
      (println "model: " model-clj)
      (store/save-layout category model-name {:data @(:data-a @state-a)
                                              :model model-clj}))
    (println "no layout found. - not saving")))

;; page helper

(def layout-data-model-a (r/atom nil))

(defn flexlayout-model-load [opts]
  (let [model (get-in opts [:path :model])
        category (:category opts)]
    (info "flexlayout model load: category: " category " model: " model)
    (store/load-layout->atom layout-data-model-a category model)))

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

(defn flexlayout-page [{:keys [parameters] :as match}]
  (if-let [{:keys [model data]} @layout-data-model-a]
    (let [category (get-in match [:data :category])
          header (get-in match [:data :header])
          model-js (clj->js model)
          model-name (get-in parameters [:path :model])
          flexlayout-opts {;:component-factory component-factory
                           :layout-json model-js
                           :category category
                           :model-name model-name
                           :data data}]
      (println "model started: " model-name " category: " category)
      ;(println "flexlayout page match: " match)
      (if header
        [flexlayout-with-header header flexlayout-opts]
        [flexlayout-only flexlayout-opts]))
    [:div
     "loaded model is nil."]))

