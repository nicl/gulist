(defproject gulist "0.1.0-SNAPSHOT"
  :description "Presents a personalised list of Guardian content"
  :url "https://github.com/nicl/gulist"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.8.8"]
            [lein-environ "1.0.0"]]
  :ring {:handler gulist.handler/app}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.3.2"]
                 [clj-http "1.0.1"]
                 [ring/ring-json "0.3.1"]
                 [environ "1.0.0"]
                 [clj-time "0.9.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]]
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
