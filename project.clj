(defproject pelada ""
  :uberjar-name "pelada-bin.jar"
  :description "Manager to a weekly pelada"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :resource-paths ["resources"]
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/core.match "1.0.1"]
                 [cheshire "5.11.0"]
                 [io.pedestal/pedestal.service "0.6.0"]
                 [io.pedestal/pedestal.jetty "0.6.0"]
                 [ch.qos.logback/logback-classic "1.2.10" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.35"]
                 [org.slf4j/jcl-over-slf4j "1.7.35"]
                 [org.slf4j/log4j-over-slf4j "1.7.35"]]
  :main pelada.core
  :profiles {:uberjar {:aot :all}} 
  :repl-options {:init-ns pelada.core})
