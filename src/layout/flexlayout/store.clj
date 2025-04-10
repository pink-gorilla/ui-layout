(ns layout.flexlayout.store
  (:require
   [babashka.fs :as fs]
   [clojure.edn :as edn]
   [clojure.string :as str]
   [clojure.java.io :as io]
   [modular.fipp :refer [pprint-str]]))

(defn filename-layout [{:keys [store-path]} category layout-name]
  (str store-path 
       (if (str/ends-with? store-path "/") "" "/")
       category "/"
       layout-name ".edn"))

(defn save-layout [{:keys [store-path] :as this} category layout-name layout-data]
  (if store-path
    (let [filename (filename-layout this category layout-name)]
      (fs/create-dirs store-path)
      (println "saving layout to file: " filename)
      (spit filename (pprint-str layout-data)))
    (println "not saving layout " layout-name " - no layout-dir defined."))
  :done)

(defn load-template [{:keys [template-resource-path]} category]
  (-> (str template-resource-path "/" category ".edn")
      (io/resource)
      (slurp) 
      (edn/read-string)))

(defn load-layout [{:keys [store-path] :as this} category layout-name]
  (let [filename  (filename-layout this category layout-name)]
    (if (and store-path (fs/exists? filename))
      (-> (slurp filename) (edn/read-string))
      (load-template this category))))

(defn layout-list [{:keys [store-path]} category]
  (let [path (str store-path "/" category)]
    (->> (fs/list-dir path  "*.edn")
         (map fs/file-name)
         (map #(subs % 0 (- (count %) 4))))))

(comment
  (def this {:store-path ".flexlayout"
             :template-resource-path "flexlayout-template"})

  (layout-list this "default")

  (load-layout this "default" "bongo")
  (load-layout this "defailt" "hugo")

; 
  )