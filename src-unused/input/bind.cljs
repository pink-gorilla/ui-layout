(ns pinkgorilla.input.bind
  (:require
   [taoensso.timbre :as timbre :refer-macros [trace debug debugf info infof warn error]]))

(defn bind [control]
  (fn [{:keys [on-change] :as options} a path]
    (let [val (if path
                (get-in @a path)
                @a)
          change-val-fn (fn [v]
                          (if path
                            (swap! a assoc-in path v)
                            (reset! a v))
                          ;(info "control changed to: " v)
                          (when on-change
                            (on-change v)))]
      [control (merge options
                      {:on-change change-val-fn
                       :value val})])))