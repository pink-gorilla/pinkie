(defproject org.pinkgorilla/pinkie "0.3.13"
  :description "Pinkie reagent component registry."
  :url "https://github.com/pink-gorilla/pinkie"
  :license {:name "MIT"}
  :deploy-repositories [["releases" {:url           "https://clojars.org/repo"
                                     :username      :env/release_username
                                     :password      :env/release_password
                                     :sign-releases false}]]

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
  :resource-paths  ["resources" ; not from npm
                    "target/webly" ; bundle
                    "target/node_modules"] ; css png resources from npm modules

  :dependencies [[com.lucasbradstreet/cljs-uuid-utils "1.0.2"]]

  :profiles
  {:dev {:dependencies [[org.pinkgorilla/webly "0.3.35"]
                        [clj-kondo "2021.03.31"]]
         :plugins      [[lein-cljfmt "0.6.6"]
                        [lein-cloverage "1.1.2"]
                        [lein-ancient "0.6.15"]
                        [lein-shell "0.5.0"]]
         :aliases      {"clj-kondo"
                        ["run" "-m" "clj-kondo.main"]

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
                                  try-if-let          [[:block 1]]}}}

   :demo {:dependencies [[com.fasterxml.jackson.core/jackson-core "2.12.0"]]
          :resource-paths  ["profiles/demo/resources"]
          :source-paths ["profiles/demo/src"]}}

  :aliases
  {"demo"  ^{:doc "Runs UI components via webserver."}
   ["with-profile" "+demo" "run" "-m" "webly.user.app.app" "demo.edn" "watch"]

   "npm-install"
   ["with-profile" "+demo" "run" "-m" "webly.user.app.app" "demo.edn" "npm-install"]

   "build-shadow-ci" ^{:doc "Build shadow-cljs ci"}
   ["with-profile" "+demo" "run" "-m" "webly.user.app.app" "demo.edn" "ci"]

   "test-run" ^{:doc "Test compiled JavaScript."}
   ["shell" "npm" "test"]

   "test-js" ^{:doc "Compile & Run JavaScript."}
   ["do" "build-shadow-ci" ["test-run"]]}
;                                            
  )


