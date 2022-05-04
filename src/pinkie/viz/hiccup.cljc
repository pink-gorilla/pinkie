(ns pinkie.viz.hiccup
  (:require
   [clojure.walk :refer [prewalk]] ; cljs 1.10 still does not have walk fixed
   [pinkie.viz.resolve :refer [resolve-fn]]))

(defn- hiccup-with-fn-symbol? [hiccup-vector]
  (and
   (vector? hiccup-vector)
   (not (map-entry? hiccup-vector)); ignore maps
   (symbol? (first hiccup-vector)); reagent syntax requires first element  to be a keyword
   ))

(defn- replace-tag-in-hiccup-vector
  "input: hiccup vector
   if keyword (first position in vector) has been registered via register-tag,
   then it gets replaced with the react function,
   otherwise keyword remains"
  [resolve h]
  (let [tag (first h)
        render-function (resolve-fn resolve tag)]
    (into [] (assoc h 0 render-function))))

(defn resolve-hiccup
  "resolve function-as symbol to function references in the reagent-hickup-map.
   Leaves regular hiccup data unchanged."
  [resolve h]
  (prewalk
   (fn [x]
     (if (hiccup-with-fn-symbol? x)
       (replace-tag-in-hiccup-vector resolve x)
       x))
   h))

(comment
  (defn clj-vega [spec]
    (str "vega spec: " (pr-str spec)))
  (defn clj [s]
    (cond
      (= s 'vega) clj-vega))

  (resolve-hiccup clj ['vega "spec1"])

  (resolve-hiccup clj [:div
                       [:p "hello world!"]
                       ['vega "spec1"]
                       ['vega-lite "spec2"]])

;  
  )







