(ns container
  (:require
   [pinkgorilla.layout.sidebar]
   [pinkgorilla.layout.tab]
   [pinkgorilla.layout.description-list]
   [pinkgorilla.layout.panel]
   [pinkgorilla.layout.sidebar-tree]))

(def sidebar pinkgorilla.layout.sidebar/sidebar)

(def tab pinkgorilla.layout.tab/tab)

(def description-list pinkgorilla.layout.description-list/description-list)

(def panel pinkgorilla.layout.panel/panel)

(def with-sidebar-menu pinkgorilla.layout.sidebar-tree/with-sidebar-menu)

; 'tab pinkgorilla.layout.tailsui/tab
;'tabs pinkgorilla.layout.tailsui/tabs
