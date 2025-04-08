(ns layout.flexlayout.store
  (:require
   [babashka.fs :as fs]
   [clojure.edn :as edn]
   [clojure.string :as str]
   [modular.fipp :refer [pprint-str]]))

(defn filename-layout [flexlayout-path layout-name]
  (str flexlayout-path 
       (if (str/ends-with? flexlayout-path "/") "" "/")
       layout-name ".edn"))

(defn save-layout [flexlayout-path layout-name layout-data]
  (if flexlayout-path
    (let [filename (filename-layout flexlayout-path layout-name)]
      (fs/create-dirs flexlayout-path)
      (println "saving layout to file: " filename)
      (spit filename (pprint-str layout-data)))
    (println "not saving layout " layout-name " - no layout-dir defined."))
  :done)

(defn load-layout [flexlayout-path layout-name]
  (let [filename  (filename-layout flexlayout-path layout-name)]
    (when (and flexlayout-path (fs/exists? filename))
      (-> (slurp filename) (edn/read-string)))))

(defn layout-list [flexlayout-path]
  (->> (fs/list-dir flexlayout-path "*.edn")
       (map fs/file-name)
       (map #(subs % 0 (- (count %) 4)))))

(comment
  (layout-list {:flexlayout-path ".data/public/layout"})

; 
  )