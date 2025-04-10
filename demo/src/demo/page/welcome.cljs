(ns demo.page.welcome
  (:require
   [reagent.core :as r]
   [ui.rnd :refer [rnd]]
   [ui.overlay :as overlay]
   ;[demo.lib.debounce :refer [save-input-debounced!]]
   ))


(defn info-content []
  (let [*state (r/atom {:name "Peter"})]
    (fn []
      [:div.bg-green-300.p-5.border.border-solid.border-blue-800.w-full.h-full.overflow-hidden
       [:h1 "INFO !!!"]])))

(defn info-box []
  [rnd {:bounds "window"
        :scale 0.7
        :default {:width 200
                  :height 200
                  :x 200
                  :y 100}
        :style {:display "flex"
                       ;:alignItems "center"
                :justifyContent "center"
                :border "solid 1px #ddd"
                :background "#f0f0f0"}}
    ;[:h1 "asdf"]
   [info-content]])


(defn hello []
  [:h1 "hello - overlay."])


(defn welcome-page [{:keys [route-params query-params handler] :as route}]
  [:div
   [info-box]
   [:h1 "ui-layout demos"]

   [:a {:on-click #(overlay/overlay-add "6969" [hello])} 
    [:p "overlay-add"]]
   [:a {:on-click #(overlay/overlay-remove "6969")}
    [:p "overlay-remove"]]

   [:a {:href "/#/spaces/main"} [:p "layout"]]
   [:a {:href "/#/description-list"} [:p "Description List"]]
   [:a {:href "/#/grid-layout"} [:p "Grid Layout"]]
  
   [:a {:href "/#/flex-layout"} [:p "Flex Layout"]]
   [:a {:href "/#/flex-layout-uix"} [:p "Flex Layout UIX2"]]

   [:a {:href "/#/spaces/main"} [:p "Spaces Layout"]]
   [:a {:href "/#/sidebartree"} [:p "sidebar tree"]]
   [:a {:href "/#/tab"} [:p "tab"]]
   [:a {:href "/#/golden"} [:p "golden"]]
   ])


