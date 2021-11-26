(defproject hanami-workshop "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [aerial.hanami "0.15.1"]
		 [io.github.nextjournal/clerk "0.3.233"]
                 [scicloj/notespace "4-alpha-16"]] ;; ERROR WITH -17
  :repl-options {:nrepl-middleware [scicloj.notespace.v4.nrepl/middleware]}
) 
