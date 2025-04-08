(ns layout.flexlayout.store
  (:require
   [babashka.fs :as fs]
   [clojure.edn :as edn]
   [clojure.string :as str]
   [clojure.java.io :as io]
   [modular.fipp :refer [pprint-str]]))

(defn filename-layout [{:keys [store-path]} layout-name]
  (str store-path 
       (if (str/ends-with? store-path "/") "" "/")
       layout-name ".edn"))

(defn save-layout [{:keys [store-path] :as this} layout-name layout-data]
  (if store-path
    (let [filename (filename-layout this layout-name)]
      (fs/create-dirs store-path)
      (println "saving layout to file: " filename)
      (spit filename (pprint-str layout-data)))
    (println "not saving layout " layout-name " - no layout-dir defined."))
  :done)

(defn load-template [{:keys [store-path template-resource-path]}]
  (-> (str template-resource-path "/default.edn")
      (io/resource)
      (slurp) 
      (edn/read-string)))

(defn load-layout [{:keys [store-path] :as this} layout-name]
  (let [filename  (filename-layout this layout-name)]
    (if (and store-path (fs/exists? filename))
      (-> (slurp filename) (edn/read-string))
      (load-template this))))

(defn layout-list [{:keys [store-path]}]
  (->> (fs/list-dir store-path "*.edn")
       (map fs/file-name)
       (map #(subs % 0 (- (count %) 4)))))

(comment
  (def this {:store-path ".flexlayout"
             :template-resource-path "flexlayout-template"})

  (layout-list this)

  (load-layout this "bongo")
  (load-layout this "hugo")

; 
  )