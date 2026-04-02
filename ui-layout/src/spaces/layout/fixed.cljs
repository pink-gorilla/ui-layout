(ns spaces.layout.fixed
  (:require
   [spaces.core :as spaces]))

(defn main-top [{:keys [height width
                        top top-size top-resizeable top-scrollable
                        main main-scrollable]
                 :or {height "100%"
                      width "100%"
                      top-size 50
                      top-resizeable true
                      top-scrollable false
                      main-scrollable true}}]
  ;(println "layout-fixed mt ...")
  [spaces/fixed {:height height :width width}
   (if top-resizeable
     [spaces/top-resizeable {:size top-size :scrollable top-scrollable} top]
     [spaces/top {:size top-size :scrollable top-scrollable} top])
   [spaces/fill {:scrollable main-scrollable}
    main]])

(defn left-right-top [{:keys [height width
                              top top-size top-resizeable top-scrollable
                              left left-size left-resizeable left-scrollable
                              right right-scrollable]
                       :or {height "100%"
                            width "100%"
                            top-size 50
                            top-resizeable true
                            top-scrollable false
                            left-size "50%"
                            left-resizeable true
                            left-scrollable true
                            right-scrollable true}}]
  ;(println "layout-fixed lrt ...")
  [spaces/fixed {:height height :width width}
   (if top-resizeable
     [spaces/top-resizeable {:size top-size :scrollable top-scrollable} top]
     [spaces/top {:size top-size :scrollable top-scrollable} top])
   [spaces/fill
    (if left-resizeable
      [spaces/left-resizeable {:size left-size :scrollable left-scrollable} left]
      [spaces/left {:size left-size :scrollable left-scrollable} left])
    [spaces/fill {:scrollable right-scrollable} right]]])

(defn left-right-middle [{:keys [height width
                                 left left-size left-resizeable  left-scrollable
                                 right right-size right-resizeable  right-scrollable
                                 middle  middle-scrollable]
                          :or {height "100%"
                               width "100%"

                               left-size "25%"
                               left-resizeable true
                               left-scrollable true
                               right-size "25%"
                               right-resizeable true
                               right-scrollable true
                               middle-scrollable true}}]
  ;(println "layout-fixed lrm ...")
  [spaces/fixed {:height height :width width}
   (if left-resizeable
     [spaces/left-resizeable {:size left-size :scrollable left-scrollable} left]
     [spaces/left {:size left-size :scrollable left-scrollable} left])
   (if right-resizeable
     [spaces/right-resizeable {:size right-size :scrollable right-scrollable} right]
     [spaces/right {:size right-size :scrollable right-scrollable} right])
   [spaces/fill {:scrollable middle-scrollable} middle]])
