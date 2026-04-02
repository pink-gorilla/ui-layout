(ns pinkgorilla.debounce
  (:require
   [goog.functions]
   [taoensso.timbre :as timbre :refer-macros [trace debug debugf info infof warn error]])
  (:import
   [goog.async Debouncer]))

; https://martinklepsch.org/posts/simple-debouncing-in-clojurescript.html

#_(defn debounce [f interval]
    (let [dbnc (Debouncer. f interval)]
    ;; We use apply here to support functions of various arities
      (fn [& args]
        (.apply (.-fire dbnc) dbnc (to-array args)))))

(defn debounce [f interval]
  (let [d (goog.functions.debounce f interval)]
    (info "debounced fn with interval: " interval)
    d))

