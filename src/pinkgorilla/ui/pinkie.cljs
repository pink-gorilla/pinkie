(ns pinkgorilla.ui.pinkie
  (:require
   ;[goog.object :as gobj]
   ;[taoensso.timbre :refer-macros (info)]
   [reagent.core :as r :refer [atom]]
   [reagent.impl.template]
   [pinkgorilla.ui.text :refer [text]]))

(def custom-renderers (atom {}))

(defn register-tag [k v]
  (swap! custom-renderers assoc k v)
  ; it would be ideal to let reagent deal with this, but the below line did not work.
  ;(gobj/set reagent.impl.template/tag-name-cache (name k) v)
  )

(defn clj->json
  [ds]
  (.stringify js/JSON (clj->js ds)))

(defn widget-not-found
  "ui component for unknown tags - so that we don't need to catch react errors
   Currently not yet used (see resolve function)"
  [name]
  [:div.widget-not-found {:style {:background-color "red"}}
   [:h3 "WIDGET NOT FOUND!"]
   [:p (str "You have entered: " (clj->json name))]])

(defn resolve-function
  "replaces keyword with react function,
   if not found return input.
   TODO: if keyword not registered, and not a reagent tag, then return widget-not-found
         awb99: the issue is that the undocumented reagent functions to get a keyword list dont work."
  [s]
  (let [;pinkgorilla.output.reagentwidget (cljs.core/resolve (symbol widget-name)) ; this is what we want, but resolve is a macro
        ;_ (info "resolving-function " s)
        v (s @custom-renderers)
        ;_ (info "renderer found: " v)
        ]
    (if (nil? v) s v)))

(defn resolve-vector
  "input: hiccup vector
   if keyword (first position in vector) has been registered via register-tag,
   then it gets replaced with the react function,
   otherwise keyword remains"
  [x]
  (let [;_ (info "reagent function found: " x)
        ;_ (info "type of arg: " (type (first (rest x))))
        ; a [(resolve-function (first x))]
        b (into [] (assoc x 0 (resolve-function (first x))))
        ;b (into [] (assoc x 0 :h1))
        ]
    ;(info "a is: " a)
    ;(info "b is: " b)

    ;a
    b))

(defn resolve-functions
  "resolve function-as symbol to function references in the reagent-hickup-map.
   Leaves regular hiccup data unchanged."
  [reagent-hiccup-syntax]
  (clojure.walk/prewalk
   (fn [x]
     (if (and (coll? x) (keyword? (first x)))
       (resolve-vector x)
       x))
   reagent-hiccup-syntax))

(defn print-registered-tags []
  (text
   (with-out-str
     (cljs.pprint/print-table
      (map #(assoc {} :k (first %) :r (pr-str (last %))) (seq @custom-renderers))))))
