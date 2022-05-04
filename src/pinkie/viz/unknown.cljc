(ns pinkie.viz.unknown)

(defn no-renderer
  "ui component for unknown tags - so that we don't need to catch react errors
   Currently not yet used (see resolve function)"
  [tag]
  (fn [& _]
    [:span.unknown-tag {:style {:background-color "red"}}
     (str "Unknown Tag: " tag)]))
