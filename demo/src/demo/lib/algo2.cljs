(ns demo.lib.algo2
  (:require
   [reagent.core :as r]
   
   ))


(defn clock [_]
 (r/with-let [counter (r/atom 0)
              interval-id (js/setInterval #(swap! counter inc) 1000)]  ; Side effect (runs once)
      [:div "Counter: " @counter]
      (finally
        (println "counter is destroyed.")
        (js/clearInterval interval-id))))