(ns demo.app
  (:require
   [taoensso.timbre :refer-macros [debug info error]]
   [frontend.css :refer [css-loader]]
   [frontend.notification :refer [notification-container]]
   [frontend.dialog :refer [modal-container]]
   [webly.spa.env :refer [get-resource-path]]
   [layout.flexlayout.core :refer [flexlayout-model-load create-flexlayout-page]]
   [demo.flexlayoutuix :refer [flexlayout-page]]
   ))

(defn wrap-app [page match]
  [:div
   [modal-container]
   [notification-container]
   [css-loader (get-resource-path)]
   [page match]])
  


(def routes
  [["/"
    ["" {:name 'demo.page.welcome/welcome-page}]
    ["description-list" {:name 'demo.page.desclist/desclist-page}]
    
    ["layout" {:name 'demo.page.layout/layout-page}]
    ["grid-layout" {:name 'demo.page.gridlayout/grid-layout-page}]
    ["flex-layout" {:name 'demo.page.flexlayout/flex-layout-page}]
    ["flex-layout-uix" {:name 'demo.flexlayoutuix/page}]
    ["flex-layout-uix/:model" {:name 'demo.flexlayoutuix/page-model
                               :controllers [{:parameters {:path [:model]}
                                              :start flexlayout-model-load}]
                               :view flexlayout-page}]
    
    ["spaces/"
     ["main" {:name 'demo.page.spaces/spaces-page}]
     ["layout-viewport-lrt" {:name 'demo.page.spaces/spaces-layout-lrt-viewport-page}]
     ["layout-viewport-lrm" {:name 'demo.page.spaces/spaces-layout-lrm-viewport-page}]
     ["layout-fixed-lrt" {:name 'demo.page.spaces/spaces-layout-lrt-fixed-page}]
     ["layout-fixed-lrm" {:name 'demo.page.spaces/spaces-layout-lrm-fixed-page}]
     ]
    ["sidebartree" {:name 'demo.page.sidebartree/sidebar-page}]
    ["tab" {:name 'demo.page.tab/tab-page}]
    ["golden" {:name 'demo.page.golden/page}]]])

  