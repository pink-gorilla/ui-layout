(ns spaces.layout.screen
  (:require
   [spaces.core :as spaces]))

(defn screen-main-top [{:keys [top top-size top-resizeable top-scrollable
                               main main-scrollable]
                        :or {top-size 50
                             top-resizeable true
                             top-scrollable false
                             main-scrollable true}}]
  ;(println "layout-viewport mt ...")
  [spaces/viewport
   (if top-resizeable
     [spaces/top-resizeable {:size top-size :scrollable top-scrollable} top]
     [spaces/top {:size top-size :scrollable top-scrollable} top])
   [spaces/fill {:scrollable main-scrollable}
    main]])

(defn screen-left-right-top [{:keys [top top-size top-resizeable top-scrollable
                                     left left-size left-resizeable left-scrollable
                                     right right-scrollable]
                              :or {top-size 50
                                   top-resizeable true
                                   top-scrollable false
                                   left-size "50%"
                                   left-resizeable true
                                   left-scrollable true
                                   right-scrollable true}}]
  ;(println "layout-viewport lrt ...")
  [spaces/viewport
   (if top-resizeable
     [spaces/top-resizeable {:size top-size :scrollable top-scrollable} top]
     [spaces/top {:size top-size :scrollable top-scrollable} top])
   [spaces/fill
    (if left-resizeable
      [spaces/left-resizeable {:size left-size :scrollable left-scrollable} left]
      [spaces/left {:size left-size :scrollable left-scrollable} left])
    [spaces/fill {:scrollable right-scrollable} right]]])

(defn screen-left-right-middle [{:keys [left left-size left-resizeable left-scrollable
                                        right right-size right-resizeable right-scrollable
                                        middle middle-scrollable]
                                 :or {left-size "25%"
                                      left-resizeable true
                                      left-scrollable true
                                      right-size "25%"
                                      right-resizeable true
                                      right-scrollable true
                                      middle-scrollable true}}]
   ;(println "layout-viewport lrm ...")
  [spaces/viewport
   (if left-resizeable
     [spaces/left-resizeable {:size left-size :scrollable left-scrollable} left]
     [spaces/left {:size left-size :scrollable left-scrollable} left])
   (if right-resizeable
     [spaces/right-resizeable {:size right-size :scrollable right-scrollable} right]
     [spaces/right {:size right-size :scrollable right-scrollable} right])
   [spaces/fill {:scrollable middle-scrollable} middle]])
