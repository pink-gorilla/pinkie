(ns viz.show
  (:require
   #?(:cljs [pinkie.error :refer [error-boundary]])
   [viz.hiccup :refer [resolve-hiccup]]))

#?(:clj
   (def error-boundary 'error-boundary))

(defn show [resolver h]
  [error-boundary
   (resolve-hiccup resolver h)])

(comment
  (defn clj-vega [spec]
    (str "vega spec: " (pr-str spec)))
  (defn clj [s]
    (cond
      (= s 'vega) clj-vega))

  (show clj ['vega "spec1"])

  (show clj [:div
             [:p "hello world!"]
             ['vega "spec1"]
             ['vega-lite "spec2"]])

;
  )