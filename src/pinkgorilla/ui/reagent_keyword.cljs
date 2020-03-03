(ns pinkgorilla.ui.reagent-keyword)

;; awb99: this approach is no longer used.

(defmulti keyword-to-reagent identity)

(defn unknown-renderer [kw & p]
  [:p (str "Unknown Renderer: " kw)])

(defmethod keyword-to-reagent :default [kw &p]
  unknown-renderer)

