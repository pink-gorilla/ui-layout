(ns layout.flexlayout.store
  (:require
   [taoensso.timbre :refer-macros [debug info error]]
   [reagent.core :as r]
   [flowy.reflower :refer [task]]))

(defn save-layout [layout-name layout-data]
  (let [t (task 'layout.flexlayout.store/save-layout layout-name layout-data)]
    (t (fn [c]
         (println "layout saved successfully."))
       (fn [err]
         (println "could not save layout error: " err)))))

(defn load-layout->atom [a layout-name]
  (let [t (task 'layout.flexlayout.store/load-layout layout-name)]
    (t (fn [data]
         (info "layout loaded: " layout-name)
         ;(info "layout data:" data)
         (reset! a data))
       (fn [err]
         (println "layout load error: " layout-name " error: " err)))))
