
(ns pinkgorilla.ui.rendererCLJS
  "equivalent to pinkgorilla.ui.renderer, but for clojurescript
   renders clojurescript data structure to html"
  (:require
   [clojure.string :as string]
   [pinkgorilla.ui.gorilla-renderable :refer [Renderable render]]))


;;; Helper functions


;; A lot of things render to an HTML span, with a class to mark the type of thing. This helper constructs the rendered
;; value in that case.


(defn- span-render
  [thing class]
  {:type :html
   :content [:span {:class class} (pr-str thing)]
   :value (pr-str thing)})

(defn- span
  [class value]
  [:span {:class class} value]
  ;; "<span class='clj-lazy-seq'>)</span>"
  )


;;; ** Renderers for basic Clojure forms **



; nil values are a distinct thing of their own


(extend-type nil
  Renderable
  (render [self]
    (span-render self "clj-nil")))

(extend-type cljs.core/Keyword
  Renderable
  (render [self]
    (span-render self "clj-keyword")))

(extend-type cljs.core/Symbol
  Renderable
  (render [self]
    (span-render self "clj-symbol")))

; would be cool to be able to use meta data to switch between
; if meta ^:br is set, then convert \n to [:br] otherwise render the string as it is.
; however clojure does not support meta data for strings
(extend-type string
  Renderable
  (render [self]
    (span-render self "clj-string")))

#_(extend-type char
    Renderable
    (render [self]
      (span-render self "clj-char")))

(extend-type number
  Renderable
  (render [self]
    (span-render self "clj-long")))

(extend-type boolean
  Renderable
  (render [self]
    (span-render self "clj-boolean")))



;; When we render a map we will map over its entries, which will yield key-value pairs represented as vectors. To render
;; the map we render each of these key-value pairs with this helper function. They are rendered as list-likes with no
;; bracketing. These will then be assembled in to a list-like for the whole map by the IPersistentMap render function.


(defn- render-map-entry
  [entry]
  {:type :list-like
   :open nil
   :close nil
   :separator [:span " "]
   :items (map render entry)
   :value (pr-str entry)})

(extend-type cljs.core/PersistentArrayMap
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-map" "{")
     :close (span "clj-map" "}")
     :separator [:span ", "]
     :items (map render-map-entry self)
     :value (pr-str self)}))

(extend-type cljs.core/LazySeq
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-lazy-seq" "(")
     :close (span "clj-lazy-seq" ")")
     :separator [:span " "]
     :items (map render self)
     :value (pr-str self)}))

(extend-type cljs.core/PersistentVector
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-vector" "[")
     :close (span "clj-vector" "]")
     :separator [:span " "]
     :items (map render self)
     :value (pr-str self)}))

(extend-type cljs.core/PersistentHashSet
  Renderable
  (render [self]
    {:type :list-like
     :open (span "clj-set" "#{")
     :close (span  "clj-set" "}")
     :separator [:span " "]
     :items (map render self)
     :value (pr-str self)}))


;; This still needs to be implemented:
;; cljs.core/Range
;; cljs.core/Var
;; cljs.core/List
;; cljs.core/PersistentTreeMap

;; A default, catch-all renderer that takes anything we don't know what to do with and calls str on it.

;; https://grokbase.com/t/gg/clojure/121d2w4vhn/is-this-a-bug-extending-protocol-on-js-object
;; david nolan:
;; You should never extend js/Object.
;; It's unfortunate since this means we can't currently use js/Object to
;; provide default protocol implementations as we do in Clojure w/o fear of
;; conflicts with JavaScript libraries.


#_(extend-type js/Object
    Renderable
    (render [self]
      (span-render self "clj-unkown")))

(extend-type default
  Renderable
  (render [self]
    (println "unkown type: " (type self))
    (span-render self "clj-unknown")))