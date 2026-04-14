(ns layout.flexlayout.core
  (:require
   [taoensso.timbre :refer-macros [debug info error]]
   [reagent.core :as r]
   [reagent.ratom :as ratom]
   [nano-id.core :refer [nano-id]]
   [uix.core :refer [$ defui defhook]]
   [uix.dom]
   ["flexlayout-react" :refer [Layout Model Action Actions TabSetNode DockLocation]]
   [layout.flexlayout.store :as store]
   [layout.flexlayout.comp :refer [component-ui]]))

;; https://www.npmjs.com/package/flexlayout-react 20k weekly downloads from npm
;; official? demo https://github.com/caplin/FlexLayout/blob/master/examples/demo/App.tsx

;; clojurescript example
;; https://github.com/dundalek/daba/blob/master/components/core/src/io/github/dundalek/daba/ui/viewers/root_docking.cljs

;; https://github.com/openworm/geppetto-client/tree/development/geppetto-ui/src/flex-layout/src

; more up to date fork: 
; https://github.com/powerdragonfire/flexycakes/blob/master/src/model/Actions.ts

;(let [^js tabset-node
  ;        (tabset-with-most-children model)
  ;                  ; (.getActiveTabset ^js model)
  ;                  ; (.getFirstTabSet ^js model)
 ;
 ;         node #js {:id cell-id
 ;                   :type "tab"}
 ;         add-node-action (FlexLayout.Actions.addNode node (.getId tabset-node) (.-CENTER FlexLayout.DockLocation) -1)]
 ;     (.doAction ^js model add-node-action)))))

; cell-id (.getId node)

;  attributeDefinitions.add("config", undefined).setType(Attribute.JSON);

;; actions
; ADD_NODE
; ADJUST_BORDER_SPLIT
; ADJUST_WEIGHTS
; CLOSE_WINDOW
; CREATE_WINDOW
; DELETE_TAB
; DELETE_TABSET
; MAXIMIZE_TOGGLE
; MOVE_NODE
; POPOUT_TAB
; POPOUT_TABSET
; RENAME_TAB
; SELECT_TAB
; SET_ACTIVE_TABSET
; UPDATE_MODEL_ATTRIBUTES

(defonce state-a (r/atom {:data-a (r/atom {}) ; make sure subscrie-state always has defined value (being too careful here?)
                          }))
(defn subscribe-state [id]
  (let [data-a (:data-a @state-a)]
    (ratom/make-reaction
     (fn [] (get @data-a id)))))

(defonce selected-id-a (r/atom nil))

(defn subscribe-selected-state []
  (ratom/make-reaction
   (fn [] (get @(:data-a @state-a) @selected-id-a))))

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

#_(defn tab-button-title [{:keys [cell-id]}]
    [:span (str cell-id)])

#_(def title-factory (fn [^js node]
                       (let [cell-id (.getId node)]
                         (r/as-element
                          [tab-button-title {;:!value (ratom/make-reaction
                                       ;         (fn [] cell-id))
                                             :cell-id cell-id}]))))

(defn handle-action [^js action]
  (js/console.log "handle-action: " action)
  (when (= Actions.SELECT_TAB (.-type action))
    (let [cell-id (-> action .-data .-tabNode)]
      (info "selected tab: " cell-id)
      (reset! selected-id-a cell-id)
      js/undefined))
  (when (= Actions.DELETE_TAB (.-type action))
    (let [data-a (:data-a @state-a)
          cell-id (-> action .-data .-node)] ; here it is called node, above tabnNode, but both get the id
      (info "cell deleted: " cell-id)
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
           ;:titleFactory title-factory
           :ref (fn [el]
                  (reset! state-a {:layout el
                                   :model model
                                   :category category
                                   :model-name model-name
                                   :data-a (r/atom data)}))}))))

(defn add-node
  ([opts]
   (let [model (:model @state-a)
         tabset ^TabSetNode (or (.getActiveTabset  ^Model model)
                                (.getFirstTabSet  ^Model model))
         tab-id (.getId ^TabSetNode tabset)]
     (add-node opts tab-id)))
  ([{:keys [id state]
     :or {id (nano-id 5)}
     :as node}
    tab-id]
   (let [layout ^Model (:layout @state-a)
         data-a (:data-a @state-a)
         node (assoc node :id id)]
  ;  {:type "tab" :name "wikipedia" :component "url"
  ;                              :config "https://en.wikipedia.org/wiki/Main_Page"}
     (info "adding new node to tab: " tab-id)
     (when state
       (swap! data-a assoc id state))
     (.addTabToTabSet layout tab-id (clj->js node)))))

(defn- dock-location-kw->enum [dock-kw]
  (case dock-kw
    :center (.-CENTER DockLocation)
    :right (.-RIGHT DockLocation)
    :left (.-LEFT DockLocation)
    :top (.-TOP DockLocation)
    :bottom (.-BOTTOM DockLocation)
    (.-CENTER DockLocation)))

(defn set-active-tabset [tabset-id]
  (js/console.log "set-active-tabset: " tabset-id)
  (let [;action (.setActiveTabSet Actions tabset-id "__main_window_id__")
        action (Action. Actions.SET_ACTIVE_TABSET (clj->js {:tabsetNode tabset-id :windowId "__main_window_id__"}));
        _ (js/console.log "set-active-tabset: " action)
        model ^Model (:model @state-a)]
    (.doAction model action)
    (js/console.log "set-active-tabset: " tabset-id "DONE.")))

(defn move-node
  [tab-id container-id dock-kw]
  (info "move-tab " tab-id " to container" container-id)
  (let [model ^Model (:model @state-a)
        dock-location (.getName ^DockLocation (dock-location-kw->enum dock-kw))
        _ (js/console.log "dock location: " dock-location)
        ;action (Actions.moveNode tab-id container-id  dock-location -1)
        action (Action. Actions.MOVE_NODE (clj->js {:fromNode tab-id
                                                    :toNode container-id
                                                    :location dock-location
                                                    :index -1}));
        _ (js/console.log "move-node: " action)]
    (.doAction  model action)
    (info "move-tab.. done")))

(defn save-layout []
  (info "save-layout..")
  (if @state-a
    (let [^Model model (:model @state-a)
          category (:category @state-a)
          model-name (:model-name @state-a)
          model-clj (js->clj (.toJson model))]
      (info "model: " model-clj)
      (store/save-layout category model-name {:data @(:data-a @state-a)
                                              :model model-clj}))
    (info "no layout found. - not saving")))

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
      (info "model started: " model-name " category: " category)
      ;(println "flexlayout page match: " match)
      (if header
        [flexlayout-with-header header flexlayout-opts]
        [flexlayout-only flexlayout-opts]))
    [:div
     "loaded model is nil."]))

