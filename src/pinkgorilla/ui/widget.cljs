(ns pinkgorilla.ui.widget
  "create state atom from clojure"
  (:require
   ;[goog.object :as gobj]
   [clojure.walk :as w]
   ;[taoensso.timbre :refer-macros (info)]
   [reagent.core :as reagent :refer [atom]]
   [pinkgorilla.ui.pinkie :refer [register-tag]]))


(defn info [str]
  (.log js/console str))


(def state-atom
  (reagent/atom
   {:name "bongo"
    :time "5 before 12"}))

(defn resolve-state [x]
  (info (str "found :widget-state: " x))
  state-atom)

(defn resolve-atoms
  "replaces :widget-state inside a reagent-hiccup with state atom.
   Leaves regular hiccup data unchanged."
  [reagent-hiccup-syntax]
  (clojure.walk/prewalk
   (fn [x]
     (if (= x :widget-state)
       (resolve-state x)
       x))
   reagent-hiccup-syntax))


(defn widget
  [component]
  (let [component (resolve-atoms component)
        ;_ (info "resolved component with state: " component)
        ]
    (reagent/create-class
     {:display-name "with-state"
      :reagent-render (fn []
                        [:div.reagent
                         component
                         [:p (str "state: " @state-atom)]])})))


(register-tag :widget widget)