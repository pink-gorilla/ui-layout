(ns layout.flexlayout.hooks.size
  (:require
   [uix.core :refer [defhook]]))

(defhook use-size []
  (let [[size set-size!] (uix.core/use-state {:width 0 :height 0})
        ref (uix.core/use-ref nil)]
    (uix.core/use-layout-effect
     (fn []
       (when-let [el @ref]
         (let [parent (.-parentElement el)
               observer (js/ResizeObserver.
                         (fn [entries _]
                           (doseq [entry entries]
                             (let [rect (.. entry -contentRect)]
                               (set-size! {:width (.-width rect)
                                           :height (.-height rect)})))))]
           (.observe observer parent)
           ;; Cleanup
           (fn []
             (.disconnect observer)))))
     [])
    [ref size]))

