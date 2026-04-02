(ns pinkgorilla.layout.sidebar-tree)

(defn item [{:keys [name href on-click class]}]
  [:li
   [:a (merge {}
              (if href {:href href} {})
              (if on-click {:on-click on-click} {}))
    [:i {:class class}]
    name]])

(defn menu [{:keys [name items href on-click class]}]
  [:li
   [:a (merge {}
              (if href {:href href} {})
              (if on-click {:on-click on-click} {}))
    [:i {:class class}]]
   (into [:ul.nav-flyout
          [:li [:span.menu name]]]
         (map item items))])

(defn with-sidebar-menu [{:keys [name items page]
                          :or {name "menu"
                               items []}}]
  [:<>
   [:aside.sidebar
    [:header name]
    [:nav.sidebar-nav
     (into [:ul]
           (map menu items))]]
   [:div.sidebar-main
    page]])