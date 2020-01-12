(ns pinkgorilla.ui.pinkie
  (:require
   [goog.object :as gobj]
   ;[taoensso.timbre :refer-macros (info)]
   [reagent.core :as r :refer [atom]]
   [reagent.impl.template]))

(def custom-renderers (atom {}))

(defn register-tag [k v]
  (swap! custom-renderers assoc k v)
  ;(gobj/set reagent.impl.template/tag-name-cache (name k) v)
  )

(defn clj->json
  [ds]
  (.stringify js/JSON (clj->js ds)))

(defn widget-not-found
  [name]
  [:div.widget-not-found {:style {:background-color "red"}}
   [:h3 "WIDGET NOT FOUND!"]
   [:p "You need to specify the fully-qualified name of the widget"]
   [:p "Example: widget.hello/world"]
   [:p (str "You have entered: " (clj->json name))]])

(defn resolve-function [s]
  (let [;pinkgorilla.output.reagentwidget (cljs.core/resolve (symbol widget-name)) ; this is what we want, but resolve is a macro
        ;_ (info "resolving-function " s)
        v (s @custom-renderers)
        ;_ (info "renderer found: " v)
        ]
    (if (nil? v) s v)))

(defn resolve-vector [x]
  (let [;_ (info "reagent function found: " x)
        ;_ (info "type of arg: " (type (first (rest x))))
        a [(resolve-function (first x))]
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



