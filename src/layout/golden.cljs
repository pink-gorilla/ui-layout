(ns layout.golden
  (:require
   [reagent.core :as r]
   [reagent.dom.client :as dom]
   ["golden-layout" :refer [GoldenLayout ItemType LayoutConfig ComponentContainer LayoutManager ResolvedComponentItemConfig]]
   ))

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


(defn handleBindComponentEvent [^ComponentContainer container ^ResolvedComponentItemConfig itemConfig]
  (let [layout ^GoldenLayout @layout-ref
        component-type-name (.resolveComponentTypeName ResolvedComponentItemConfig itemConfig)
        component (.createVirtualComponent layout container component-type-name (.-componentState itemConfig))
        comp-root-el (.-rootHtmlElment component)]
    (.appendChild (._layoutElement layout) comp-root-el)
    (.set (._boundComponentMap container component))
       ;container.virtualRectingRequiredEvent = (container, width, height) => this.handleContainerVirtualRectingRequiredEvent (container, width, height);
      ;container.virtualVisibilityChangeRequiredEvent = (container, visible) => this.handleContainerVisibilityChangeRequiredEvent (container, visible);
    #js {:component component
         :virtual true}))

(defn init-layout [layout-config]
  (let [_ (println "creating golden layout")
        dom-el (.getElementById js/document "golden-layout-container")
        _ (println "dom el: " dom-el)
        ;config #js {}
        layout (GoldenLayout. dom-el handleBindComponentEvent) ; config 
        _ (println "golden layout created.")]

    ;(register-reagent layout "clock" clock)
    ;(register-reagent layout "PanelA" panel-a)
    (register-reagent-components layout)

    #_(.registerComponentFactoryFunction layout  "sayHi" (fn [^ComponentContainer container state]
                                                         (println "sayHi " container state)
                                                         (let [el (.getElement container)]
                                                           (println "el: " el)
                                                           (let [t (.-textContent el)]
                                                             (println "t: " t)
                                                             (set! (.-bongo js/window) el)
                                                             (set! (.-textContent el) (.-name state))
                                              ;(set! t (str "Hi " (.-name state)))
                                                             )
                                           ; container.getElement () .text ('Hi '+ state.name); 
                                                           )))
    (println "init layout")
    ;(.init layout)

    (.loadLayout layout layout-config)

    (println "done")
    (reset! layout-ref layout)))


(defn golden-layout-component [{:keys [layout-config style]}]
  (r/create-class
   {:component-did-mount #(init-layout layout-config)
    :reagent-render (fn []
                      [:div#golden-layout-container {:style style}])}))


(defn save-layout []
  (let [layout ^GoldenLayout @layout-ref
        data (.saveLayout layout)]
    (println "saved layout: " data)))


(defn add-component [cmp]
  (let [;cmp #js {:type "component"
        ;         :componentType "clock"
        ;         :componentState #js {:name "Wolfram"}}
        ;location #js [ #js {:typeId (-> LayoutManager .-LocationSelector .-TypeId .-FirstRow)
        ;                    :index 2}]
        layout ^GoldenLayout @layout-ref
        _ (println "adding: " cmp)
        ;addedItem (.newItemAtLocation layout cmp location)
        ;addedItem (.newComponent layout comp)
        ;addedItem (.addComponentAtLocation layout comp)
        addedItem (.addItem layout cmp (.-defaultLocationSelectors LayoutManager))]
    (println "addedItem: " addedItem)
    ;(let [ls (.-LocationSelector LayoutManager)]
    ;  (println "location selector: " ls)
    ;  nil)
    
    ))