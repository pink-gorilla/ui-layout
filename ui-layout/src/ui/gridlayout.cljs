(ns ui.gridlayout
  (:require
   ["react-grid-layout" :as ReactGridLayout]))

; https://github.com/STRML/react-grid-layout
; https://www.npmjs.com/package/react-grid-layout
; 500k weekly npm downloads

(defn gridlayout [data & children]
  [:<>
   [:> ReactGridLayout data
    children]])

