(ns pinkgorilla.ui.reagent-keyword)

; #?(:clj (defmulti query-params-to-storage (fn [t p] t))

(defmulti keyword-to-reagent identity)

(defn unknown-renderer [kw & p]
  [:p (str "Unknown Renderer: " kw)])

(defmethod keyword-to-reagent :default [kw &p]
  unknown-renderer)

