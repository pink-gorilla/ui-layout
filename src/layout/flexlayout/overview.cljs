(ns layout.flexlayout.overview
  (:require
   [taoensso.timbre :refer-macros [debug info error]]
   [reagent.core :as r]
   [reagent.ratom :as ratom]
   [nano-id.core :refer [nano-id]]
   [human-id.core :refer [human-id]]
   [reitit.frontend.easy :as rfe]
   [uix.core :refer [$ defui defhook]]
   [uix.dom]
   [layout.flexlayout.store :as store]
   ))



;; overview page helper

(defonce layouts-a (r/atom []))

(defn flexlayout-models-load [{:keys [link category]
                               :or {category "default"
                                    link :flexlayout}
                               :as opts}]
  (println "load models category: " category " link: " link)
  (store/load-layouts->atom layouts-a category))

(defn one [link-type layout-name]
  [:li [:a {:href (rfe/href link-type {:model layout-name})} layout-name]])


(defn flexlayout-overview-page [match]
  (let [layout-new (human-id)
        category (or (get-in match [:data :category]) "default") 
        link-type (or (get-in match [:data :link]) :flexlayout) 
        one-el (fn [ln] [one link-type ln])]
    (println "overview page category: " category)
    ;(println "overview match: " match)
    [:div  {:style {:height "100vh"
                    :width "100vw"
                    :top "0"
                    :left "0"
                    :margin "0"
                    :padding "0"}}
     [:h1 "new layout"]
     [one-el layout-new]
     [:h1 "existing layouts"]
     (into [:ol]
           (map one-el @layouts-a)  )]))