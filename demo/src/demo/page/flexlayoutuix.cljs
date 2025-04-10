(ns demo.page.flexlayoutuix
  (:require
   [reagent.core :as r]
   [uix.core :refer [$ defui defhook]]
   [uix.dom]
   [layout.flexlayout.core :refer [flex-layout save-layout flexlayout-page flexlayout-model-load add-node]]
   [layout.flexlayout.comp.option] ; side effects
   [demo.comp.flowy] ; side effects
   [demo.comp.demo ] ; side effects
   ))

(def components
  {:wiki {:type "tab" :name "wikipedia" :component "url"
          :config "https://en.wikipedia.org/wiki/Main_Page"}
   :uix-counter {:type "tab" :name "uixcount" :component "uixcounter"}
   :size {:type "tab" :name "size" :component "size"}
   :reagent-counter {:type "tab" :name "rcount" :component "reagent-counter"}
   :reagent-clock {:type "tab" 
                   :name "rclock" 
                   :component "reagent-clock"
                   :state {:edit [{:type :select :path :background-color :name "background-color"
                                   :spec ["red" "green" "blue" "white" "yellow" "orange"]}]
                           :value {:background-color "yellow"}}}
   :server-fortune {:type "tab" :name "sfortune" :component "server-fortune"}
   :server-counter {:type "tab" :name "scounter" :component "server-counter"}
   :option {:type "tab" :name "option" :component "clj-options2"}
   
   })

(defn add [component-key]
  (add-node (component-key components)))

(defn header []
  [:div {:style {:background "red"
                 :height "1.5em"}}
   [:button {:on-click #(save-layout)} "save"]
   [:button {:on-click #(add :wiki)} "wiki"]
   [:button {:on-click #(add :uix-counter)} "uix-counter"]
   [:button {:on-click #(add :size)} "size"]
   [:button {:on-click #(add :reagent-counter)} "reagent-counter"]
   [:button {:on-click #(add :reagent-clock)} "reagent-clock"]
   [:button {:on-click #(add :server-fortune)} "server-fortune"]
   [:button {:on-click #(add :server-counter)} "server-counter"]
   [:button {:on-click #(add :option)} "option"]])

(def flexlayout-page-wrapped
  (flexlayout-page {:header header}))

;; standalone

(defn mount []
  (let [root (uix.dom/create-root (js/document.getElementById "app"))]
    (uix.dom/render-root 
     ($ :div (r/as-element [flexlayout-page-wrapped]))
     root)))