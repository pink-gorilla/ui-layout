(ns layout.flexlayout.store
  (:require
   [babashka.fs :as fs]
   [clojure.edn :as edn]
   [modular.fipp :refer [pprint-str]]))

(defn save-layout [{:keys [layout-dir]} layout-name layout-data]
  (if layout-dir
    (spit (str layout-dir layout-name ".edn") (pprint-str layout-data))
    (println "not saving layout " layout-name " - no layout-dir defined.")))

(defn load-layout [{:keys [layout-dir]} layout-name]
  (let [filename (str layout-dir layout-name ".edn")]
    (when (and layout-dir (fs/exists? filename))
      (-> (slurp filename) (edn/read-string)))))

(defn files [{:keys [layout-dir]}]
  (->> (fs/list-dir layout-dir "*.edn")
       (map fs/file-name)
       (map #(subs % 0 (- (count %) 4)))))

(comment
  (files {:layout-dir ".data/public/layout"})

; 
  )