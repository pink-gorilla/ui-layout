(ns layout.flexlayout.store
  (:require
   [taoensso.timbre :refer-macros [debug info error]]
   [reagent.core :as r]
   [flowy.reflower :refer [task]]))

(defn save-layout [category layout-name layout-data]
  (let [t (task 'layout.flexlayout.store/save-layout category layout-name layout-data)]
    (t (fn [c]
         (println "layout saved successfully."))
       (fn [err]
         (println "could not save layout error: " err)))))

(defn load-layout->atom [a category layout-name]
  (let [t (task 'layout.flexlayout.store/load-layout category layout-name)]
    (t (fn [data]
         (info "layout loaded: " layout-name)
         ;(info "layout data:" data)
         (reset! a data))
       (fn [err]
         (println "layout load error: " layout-name " error: " err)))))

(defn load-layouts->atom [a category]
  (let [t (task 'layout.flexlayout.store/layout-list category)]
    (t (fn [data]
         ;(info "layout data:" data)
         (reset! a data))
       (fn [err]
         (println "layouts load error: " err)))))

