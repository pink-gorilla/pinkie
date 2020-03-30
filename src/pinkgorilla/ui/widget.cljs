(ns pinkgorilla.ui.widget
  "create state atom from clojure"
  (:require
   [clojure.walk :as w]
   [clojure.string :as str]
   [reagent.core :as reagent :refer [atom]]
   [pinkgorilla.ui.pinkie :refer [register-tag]]))

(defn info [str]
  (.log js/console str))

(def state-atom
  (reagent/atom
   {:test 123}))

; state

(defn resolve-state [x]
  (info (str "found :widget: " x))
  state-atom)

(defn walk-state [x]
  (if (= x :widget)
    (resolve-state x)
    x))

; key

(defn widget-key [x]
  (when (keyword? x)
    (let [s (name x)]
      (when (str/starts-with? s "widget:")
        (let [name-key (subs s 7)
              _ (info (str "widget key found: " name-key))
              key (keyword name-key)]
          key)))))

(defn is-key? [x]
  (let [k (widget-key x)]
    (if (nil? k) false true)))

(defn deref-key [k]
  (k @state-atom))

(defn walk-key [x]
  (if (is-key? x)
    (let [k (widget-key x)]
      [deref-key k])
    x))

(defn resolve-widget
  "replaces :widget inside a reagent-hiccup with state atom.
   Leaves regular hiccup data unchanged."
  [reagent-hiccup-syntax]
  (->> reagent-hiccup-syntax
       (clojure.walk/prewalk walk-state)
       (clojure.walk/prewalk walk-key)))

(defn widget-state []
  [:div
   [:h1 "widget state: "]
   [:p (str @state-atom)]])

(register-tag :p/widget-state widget-state)