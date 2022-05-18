(ns viz.resolve
  (:require
   [viz.unknown :refer [no-renderer]]))

(defn resolve-fn
  [resolve fn-symbol]
  (if-let [f (resolve fn-symbol)]
    f
    (no-renderer (pr-str fn-symbol))))

(comment

  (defn clj-vega [spec]
    (str "vega spec: " (pr-str spec)))

  (clj-vega "test")

  (defn clj [s]
    (cond
      (= s 'vega) clj-vega))

  (get-render-fn clj 'vega)
  (get-render-fn clj 'vega-lite)
  ((get-render-fn clj 'vega) "spec1")
  ((get-render-fn clj 'vega-lite) "spec2")

;  
  )

