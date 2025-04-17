(ns pinkgorilla.layout.tab
  (:require
   [reagent.core :as r]))

(defn- tab-menu [{:keys [active select-page]
                  :or {active 0}} tabs]
  [:div {:style {:border-bottom "2px solid #eaeaea"
                 :padding-bottom "2px"
                 }}
   (into [:ul {:style {:list-style "none"
                       :display "flex"
                       :flex-direction "row"}}]
         (map-indexed (fn [i v]
                        [:li {:style {:margin-right "5px"
                                      :padding-left "2px"
                                      :padding-right "2px"
                                      :padding-top "0px"
                                      :padding-bottom "0px"
                                      :display "inline"
                                      :cursor "pointer"
                                      :border "1px solid #ccc"
                                      :border-radius "4px"
                                      :background-color (if (= active i) 
                                                          "lightblue"
                                                          "lightgray")}
                              :on-click #(select-page i)}
                         (first v)]) tabs))])

(defn tab [& args]
  (let [active (r/atom 0)]
    (fn [& args]
      (let [[props children] (if (map? (first args))
                               [(first args) (rest args)]
                               [{} args])
            tabs (partition 2 children)]
        ;(println "tabs: " tabs "props: " props)
        [:div props
         [:div {:style {:display "flex"
                        :flex-direction "column"
                        :height "100%"
                        :width "100%"}}
          [tab-menu {:active @active
                     :select-page (fn [i]
                                   ;(println "selected index: " i)
                                    (reset! active i))}
           tabs]
          (let [page (nth tabs @active)]
            (if page
              (second page)
              [:div "no tab selected page:" @active]))]]))))