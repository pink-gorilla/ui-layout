(ns ui.overlay
  (:require
   [reagent.dom :refer [render unmount-component-at-node]]
   [taoensso.timbre :refer-macros [debugf infof warn warnf errorf]]))

;; dom node ops

(defn- dom-add [id]
  (infof "adding overlay root div")
  (let [div (.createElement js/document "div")
        body (.-body js/document)]
    (.setAttribute div "id" id)
    (.appendChild body div)
    div))

(defn- dom-remove [id]
  (when-let [elem  (.getElementById js/document id)]
    (infof "removing dom-overlay: %s" id)
    (let [parent (.-parentElement elem)]
      ;(error "link: " elem "parent: " parent)
      (.removeChild parent elem))))

;; reagent ops

(defn overlay-add
  "Adds hiccup as an overlay to current page.
   hiccup needs to be a vector
   id needs to be a string"
  [id hiccup]
  (let [div (dom-add id)]
    (dom-add id)
    (render hiccup div)))

(defn overlay-remove
  "Removes an overlay that has been added with overlay-add from the dom.
   id needs to be a string"
  [id]
  (let [div (.getElementById js/document id)]
    (unmount-component-at-node div)
    (dom-remove id)))
