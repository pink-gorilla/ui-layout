(ns spaces.core
  (:require
   ["react-spaces" :as spaces]))

(defn wrap [c]
  (fn [opts & [child]]
    [:> c opts child]))

(defn wrap-many [c]
  (fn [& args]
    (into [:> c]
          args)))

;; top-level-spaces: Used at the top level of all other spaces.

(def viewport (wrap-many spaces/ViewPort))
(def fixed (wrap-many spaces/Fixed))

;; anchored-spaces:  can be used within the top-level-spaces

(def left (wrap spaces/Left))
(def right (wrap spaces/Right))
(def top (wrap spaces/Top))
(def bottom (wrap spaces/Bottom))

(def left-resizeable (wrap spaces/LeftResizable))
(def right-resizeable (wrap spaces/RightResizable))
(def top-resizeable (wrap spaces/TopResizable))
(def bottom-resizeable (wrap spaces/BottomResizable))

;; other

(def fill (wrap-many spaces/Fill))
(def positioned (wrap spaces/Positioned)) ;A space which can be absolutely placed within a parent space either by top, left, width and height or by top, left, right and bottom.

(def layer (wrap spaces/Layer)) ; Layers allow you to create layers within a parent space

(def centered (wrap spaces/Centered)) ; Centres the content of a space horizontally and vertically.
(def centered-vertically (wrap spaces/CenteredVertically)) ; Centres the content of a space vertically.

;; helper

(def description (wrap spaces/Description))



