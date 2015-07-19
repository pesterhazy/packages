(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.10" :scope "test"]
                  [cljsjs/boot-cljsjs "0.5.0"  :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "1.4.2-0")
(bootlaces! +version+)

(task-options!
 pom  {:project     'cljsjs/tabletop
       :version     +version+
       :description "Tabletop.js gives spreadsheets legs"
       :url         "https://github.com/jsoma/tabletop"
       :scm         {:url "https://github.com/jsoma/tabletop"}
       ;; :license     {"BSD" "http://opensource.org/licenses/BSD-3-Clause"}
       })

(deftask package []
  (comp
   (download :url      "https://github.com/jsoma/tabletop/archive/v1.4.2.zip"
             :checksum "40439D542BF767615AE2208DC667EDD2"
             :unzip    true)
   (sift     :move     {#"^tabletop-.*/src/tabletop.js"
                        "cljsjs/tabletop/development/tabletop.inc.js"})
   (minify   :in       "cljsjs/tabletop/development/tabletop.inc.js"
             :out      "cljsjs/tabletop/production/tabletop.min.inc.js")
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name "cljsjs.tabletop")))
