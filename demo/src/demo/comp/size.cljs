(ns demo.comp.size
  (:require
   [uix.core :refer [defhook]]
   [uix.dom :as dom]))

(defhook use-parent-size []
  (let [[size set-size!] (uix.core/use-state {:width 0 :height 0})
        ref (uix.core/use-ref nil)]
    (uix.core/use-effect
     (fn []
       (when-let [el (.-current ref)]
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
     ;#js
     [])

    ;; return a tuple: [ref size]
    [ref size]))


;const [height, setHeight] = useState (null);
;const [width, setWidth] = useState (null);
;const div = useCallback (node => {if (node !== null) {setHeight (node.getBoundingClientRect () .height);
;                                                      ;setWidth (node.getBoundingClientRect () .width);
;                                                      }}, []);

;return (<div ref= {div} >
;             ...
;             </div>)