(ns ui.rnd
  "resizeable and draggable component"
  (:require
   ["react-rnd" :refer [Rnd]]))

(defn rnd [data & [child]]
  [:> Rnd data child])


