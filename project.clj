(defproject org.pinkgorilla/gorilla-renderable-ui "0.1.2"
  :description "Gorilla Renderable UI - Custom cljs based rendering Pink Gorilla Notebook."
  :url "https://github.com/pink-gorilla/gorilla-renderable-ui"
  :license {:name "MIT"}
  ;:deploy-repositories [["releases" :clojars]]
  :deploy-repositories [["releases" {:url           "https://clojars.org/repo"
                                     :username      :env/release_username
                                     :password      :env/release_password
                                     :sign-releases false}]]
  :dependencies [;; [org.clojure/clojure "1.10.1"]
                 ;; [org.clojure/clojurescript "1.10.520"]
                 [org.pinkgorilla/gorilla-renderable "3.0.0"]
                 [reagent "0.8.1"
                  :exclusions [org.clojure/tools.reader]]   ; needed by pinkie r/atom
                 ]                                          ; used in hiccup rendering

  ;; :source-paths ["src"]
  ;; :test-paths ["test"]
  :plugins [[lein-shell "0.5.0"]]

  :profiles {:dev {:dependencies [[thheller/shadow-cljs "2.8.80"]
                                  [thheller/shadow-cljsjs "0.0.21"]
                                  [clj-kondo "2019.11.23"]]
                   :plugins      [[lein-cljfmt "0.6.6"]
                                  [lein-cloverage "1.1.2"]]
                   :aliases      {"clj-kondo" ["run" "-m" "clj-kondo.main"]}
                   :cloverage    {:codecov? true
                                  ;; In case we want to exclude stuff
                                  ;; :ns-exclude-regex [#".*util.instrument"]
                                  ;; :test-ns-regex [#"^((?!debug-integration-test).)*$$"]
                                  }
                   ;; TODO : Make cljfmt really nice : https://devhub.io/repos/bbatsov-cljfmt
                   :cljfmt       {:indents {as->                [[:inner 0]]
                                            with-debug-bindings [[:inner 0]]
                                            merge-meta          [[:inner 0]]
                                            try-if-let          [[:block 1]]}}}}

  ;; TODO: prep tasks breaks alias???
  ;; :prep-tasks ["build-shadow-ci"]

  :aliases {"build-shadow-ci" ^{:doc "Build shadow-cljs ci"}
                              ["run" "-m" "shadow.cljs.devtools.cli" "compile" ":ci"]
            "test-js"         ^{:doc "Test compiled JavaScript."}
                              ["shell" "npm" "run" "test"]
            "bump-version"    ^{:doc "Roll versions artefact version"}
                              ["change" "version" "leiningen.release/bump-version"]}

  :release-tasks [["vcs" "assert-committed"]
                  ["bump-version" "release"]
                  ["vcs" "commit" "Release %s"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy"]
                  ["bump-version"]
                  ["vcs" "commit" "Begin %s"]
                  ["vcs" "push"]])


