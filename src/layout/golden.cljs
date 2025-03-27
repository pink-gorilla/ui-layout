(ns layout.golden
  (:require
   [reagent.core :as r]
   [reagent.dom.client :as dom]
   ["golden-layout" :refer [GoldenLayout ItemType LayoutConfig ComponentContainer LayoutManager ResolvedComponentItemConfig]]))

(defn register-reagent [^GoldenLayout layout name reagent-ui-fn]
  (println "registering reagent component: " name)
  (.registerComponentFactoryFunction
   layout name
   (fn [^ComponentContainer container state]
     (println "creating reagent panel: " name)
     (let [root (dom/create-root (.getElement container))]
       (dom/render root [reagent-ui-fn state])
       (.on container "destroy"
            (fn []
              (println "reagent container " name " has been destroyed.")
              (dom/unmount root)
              (println "react root for reagent container " name " unmounted.")))))))

(defonce reagent-components-a (atom {}))

(defn register-reagent-component [name reagent-ui-fn]
  (swap! reagent-components-a assoc name reagent-ui-fn))

(defn register-reagent-components [^GoldenLayout layout]
  (doall (map (fn [[name reagent-ui-fn]]
                (register-reagent layout name reagent-ui-fn)) @reagent-components-a)))

(defonce layout-ref (atom nil))

#_(defn handleBindComponentEvent [^ComponentContainer container ^ResolvedComponentItemConfig itemConfig]
    (println "handle Bind Component Event: " itemConfig)
    (let [layout ^GoldenLayout @layout-ref
          component-type-name (.resolveComponentTypeName ResolvedComponentItemConfig itemConfig)
          component (.createVirtualComponent layout container component-type-name (.-componentState itemConfig))
        ;ComponentContainer
          comp-root-el (.-rootHtmlElment component)]
      (.appendChild (._layoutElement layout) comp-root-el)
      (.set (._boundComponentMap container component))
       ;container.virtualRectingRequiredEvent = (container, width, height) => this.handleContainerVirtualRectingRequiredEvent (container, width, height);
      ;container.virtualVisibilityChangeRequiredEvent = (container, visible) => this.handleContainerVisibilityChangeRequiredEvent (container, visible);
      (println "component: " component)
      #js {:component component
           :virtual true}))

(defn init-layout [layout-config]
  (let [dom-el (.getElementById js/document "golden-layout-container")
        layout (GoldenLayout. dom-el #_handleBindComponentEvent)]
    ; register all registered react components 
    (register-reagent-components layout)
    ; example how to register a static html component    
    #_(.registerComponentFactoryFunction layout  "sayHi" (fn [^ComponentContainer container state]
                                                           (println "sayHi " container state)
                                                           (let [el (.getElement container)]
                                                             (println "el: " el)
                                                             (let [t (.-textContent el)]
                                                               (println "t: " t)
                                                               (set! (.-bongo js/window) el)
                                                               (set! (.-textContent el) (.-name state))
                                              ;(set! t (str "Hi " (.-name state)))
                                                               ))))
    (when layout-config
      (.loadLayout layout (clj->js layout-config)))
    (reset! layout-ref layout)))

(defn golden-layout-component [{:keys [layout-config style]}]
  (r/create-class
   {:component-did-mount #(init-layout layout-config)
    :reagent-render (fn []
                      [:div#golden-layout-container {:style style}])}))

(defn save-layout []
  (let [layout ^GoldenLayout @layout-ref
        _ (println "save-layout: golden-layout: " layout)
        data-js (.saveLayout layout)
        data (js->clj data-js)]
    (println "saved layout: " (pr-str data))
    data))

(defn load-layout [layout-config]
  (let [layout ^GoldenLayout @layout-ref
        layout-config-js (clj->js layout-config)]
    (.loadLayout layout layout-config-js)))

(defn add-component [cmp]
  (let [_ (println "adding: " cmp)
        cmp (clj->js cmp)
        ;location #js [ #js {:typeId (-> LayoutManager .-LocationSelector .-TypeId .-FirstRow)
        ;                    :index 2}]
        layout ^GoldenLayout @layout-ref
        ;addedItem (.newItemAtLocation layout cmp location)
        ;addedItem (.newComponent layout comp)
        ;addedItem (.addComponentAtLocation layout comp)
        addedItem (.addItem layout cmp (.-defaultLocationSelectors LayoutManager))]
    (println "addedItem: " addedItem)
    ;(let [ls (.-LocationSelector LayoutManager)]
    ;  (println "location selector: " ls)
    ;  nil)
    ))