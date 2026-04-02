(ns build
  (:require
   [clojure.tools.build.api :as b]
   [org.corfield.build :as bb])) ; https://github.com/seancorfield/build-clj

(def lib 'org.pinkgorilla/ui-layout)
(def version (format "0.5.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s-%s.jar" (name lib) version))

(defn jar "build the JAR" [opts]
  (println "Building hte JAR")
   (b/copy-dir {:src-dirs ["src"
                          "resources"
                          "target/node_modules"]
               :target-dir class-dir})
  (-> opts
      (assoc :lib lib
             :version version
             :src-pom "pom-template.xml"
             :transitive true)
      (bb/jar)))

(defn deploy "Deploy the JAR to Clojars." [opts]
  (println "Deploying to Clojars.")
  (-> opts
      (assoc :lib lib
             :version version
              :artifact (b/resolve-path jar-file)
             )
      (bb/deploy)))

