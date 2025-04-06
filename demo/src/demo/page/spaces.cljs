(ns demo.page.spaces
  (:require
   [spaces.core :as spaces]
   [spaces.layout.screen :as layout-viewport]
   [spaces.layout.fixed :as layout-fixed]
   [demo.lib.link :refer [link-href]]))

(defn nav-layout []
  [:<>
   [link-href "/#/spaces/main" "goto spaces/main"]
   [link-href "/#/spaces/layout-viewport-lrt" "goto spaces/layout-viewport-lrt"]
   [link-href "/#/spaces/layout-viewport-lrm" "goto spaces/layout-viewport-lrm"]
   [link-href "/#/spaces/layout-fixed-lrt" "goto spaces/layout-fixed-lrt"]
   [link-href "/#/spaces/layout-fixed-lrm" "goto spaces/layout-fixed-lrm"]])

(defn spaces-1 []
  [spaces/viewport
   [spaces/left {:size "20%" :style "blue" :trackSize true}
    [spaces/description "Left"]]
   [spaces/fill {:style "green" :trackSize true}
    [spaces/bottom {:size "20%" :style "red" :trackSize true}]
    [spaces/right {:size "20%" :style "blue" :trackSize true}]]])

(defn spaces-page [_]
  [spaces/viewport
   [spaces/top-resizeable {:size 50} [:div "top" [nav-layout]]]
   [spaces/fill {:class "bg-green-500"}
    [:div
     [spaces/left-resizeable {:size 200} "left"]
     [spaces/right-resizeable {:size 200}
              ;"right"
      [:div
       [spaces/fill {:class "bg-blue-500"} "fill"]
       [spaces/bottom-resizeable {:size 50 :class "bg-gray-300"} "bottom"]]]
     [spaces/fill {:class "bg-green-500"}
             ;[spaces-1]
      [:div.bg-red-300.w-full.h-full "filling"]]]]])

;; spaces layout viewport

(defn spaces-layout-lrt-viewport-page [_]
  [:div.bg-red-200
   [layout-viewport/screen-left-right-top {:left [:div.bg-green-300.w-full.h-full "left"]
                                           :right [:div.bg-blue-300.w-full.h-full "right"]
                                           :top [:div.bg-gray-200.w-full.h-full "top"
                                                 [nav-layout]]}]])

(defn spaces-layout-lrm-viewport-page [_]
  [layout-viewport/screen-left-right-middle {:left [:div.bg-green-300.w-full.h-full "left"]
                                             :right [:div.bg-blue-300.w-full.h-full "right"]
                                             :middle [:div.bg-gray-200.w-full.h-full "middle"
                                                      [nav-layout]]}])

;; spaces layout fixed

(defn spaces-layout-lrt-fixed-page [_]
  [:div.bg-blue-200 {:style {:width "100vw" :height "100vh"}}
   [layout-fixed/left-right-top {:left [:div.bg-green-300.w-full.h-full "left"]
                                 :right [:div.bg-blue-300.w-full.h-full "right"]
                                 :top [:div.bg-gray-200.w-full.h-full "top"
                                       [nav-layout]]}]])

(defn spaces-layout-lrm-fixed-page [_]
  [:div.bg-blue-200 {:style {:width "100vw" :height "100vh"}}
   [layout-fixed/left-right-middle {:left [:div.bg-green-300.w-full.h-full "left"]
                                    :right [:div.bg-blue-300.w-full.h-full "right"]
                                    :middle [:div.bg-gray-200.w-full.h-full "middle"
                                             [nav-layout]]}]])



