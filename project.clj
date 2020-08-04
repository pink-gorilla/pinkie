(defproject org.pinkgorilla/pinkie "0.2.11-SNAPSHOT"
  :description "Pinkie reagent component registry."
  :url "https://github.com/pink-gorilla/pinkie"
  :license {:name "MIT"}
  :deploy-repositories [["releases" {:url           "https://clojars.org/repo"
                                     :username      :env/release_username
                                     :password      :env/release_password
                                     :sign-releases false}]]

  ;; TODO: prep tasks breaks alias???
  ;; :prep-tasks ["build-shadow-ci"]

  :release-tasks [["vcs" "assert-committed"]
                  ["bump-version" "release"]
                  ["vcs" "commit" "Release %s"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy"]
                  ["bump-version"]
                  ["vcs" "commit" "Begin %s"]
                  ["vcs" "push"]]

  :target-path  "target/jar"
  :source-paths ["src"]
  :test-paths ["test"]

  :managed-dependencies [[com.google.code.findbugs/jsr305 "3.0.2"]
                         [commons-codec "1.14"]]

  :dependencies [;; [org.clojure/clojure "1.10.1"]
                 ;; [org.clojure/clojurescript "1.10.520"]
                 ; awb99: adding timbre logging here would fuck up the kernel-shadowdeps bundle compilation.
                 ;[com.taoensso/timbre "4.10.0"]             ; clojurescript logging
                 [com.lucasbradstreet/cljs-uuid-utils "1.0.2"] ;; awb99: in encoding, and clj/cljs proof
                 ]

  :profiles {:dev {:dependencies [[reagent "0.10.0"; reagent is dev-only, so clients do not need to manage reagent version
                                   :exclusions [org.clojure/tools.reader
                                                cljsjs/react
                                                cljsjs/react-dom]]

                                  [thheller/shadow-cljs "2.10.19"]
                                  [thheller/shadow-cljsjs "0.0.21"]
                                  [clj-kondo "2020.07.29"]]
                   :plugins      [[lein-cljfmt "0.6.6"]
                                  [lein-cloverage "1.1.2"]
                                  [lein-ancient "0.6.15"]
                                  [lein-shell "0.5.0"]]
                   :aliases      {"clj-kondo"
                                  ["run" "-m" "clj-kondo.main"]

                                  "build-shadow-ci" ^{:doc "Build shadow-cljs ci"}
                                  ["run" "-m" "shadow.cljs.devtools.cli" "compile" ":ci"]

                                  "test-run" ^{:doc "Test compiled JavaScript."}
                                  ["shell" "./node_modules/karma/bin/karma" "start" "--single-run"]

                                  "test-js" ^{:doc "Compile & Run JavaScript."}
                                  ["do" "build-shadow-ci" ["test-run"]]

                                  "bump-version"    ^{:doc "Roll versions artefact version"}
                                  ["change" "version" "leiningen.release/bump-version"]}
                   :cloverage    {:codecov? true
                                  ;; In case we want to exclude stuff
                                  ;; :ns-exclude-regex [#".*util.instrument"]
                                  ;; :test-ns-regex [#"^((?!debug-integration-test).)*$$"]
                                  }
                   ;; TODO : Make cljfmt really nice : https://devhub.io/repos/bbatsov-cljfmt
                   :cljfmt       {:indents {as->                [[:inner 0]]
                                            with-debug-bindings [[:inner 0]]
                                            merge-meta          [[:inner 0]]
                                            try-if-let          [[:block 1]]}}}})


