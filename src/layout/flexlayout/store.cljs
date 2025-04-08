(ns layout.flexlayout.store
  (:require
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
         (println "layout laoded: " layout-name)
         (reset! a data))
       (fn [err]
         (println "layout load error: " layout-name " error: " err)))))
