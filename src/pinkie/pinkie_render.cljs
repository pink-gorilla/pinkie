(ns pinkie.pinkie-render
  (:require
   [cljs.pprint]
   [pinkie.error :refer [error-boundary]]
   [pinkie.pinkie :refer [tag-inject convert-style-as-strings-to-map convert-render-as
                          component-list->str]
    :refer-macros [register-component]]
   [pinkie.text]))

(defn reagent-inject [{:keys [map-keywords fix-style]
                       :or {fix-style true}
                       :as options} component]
  (let [;_ (info "map-keywords: " map-keywords "widget: " widget " reagent component: " component)
        ;_ (info "meta data: " (meta component))
        component (convert-render-as component)
        ;_ (println "after convert-render-as " component)
        component (if map-keywords (tag-inject component) component)
        component (if fix-style (convert-style-as-strings-to-map component) component)
        ;_ (info "inject result: " component)
        ]
    [:div.reagent-output component]))

(defn pinkie-render-unsafe
  [output]
  (let [{:keys [hiccup]
         :as options} (:content output)
        options-without-hiccup (dissoc options :hiccup)]
    (reagent-inject options-without-hiccup hiccup)))

(defn ^{:category :pinkie}
  pinkie-render
  "renders a reagent hiccup vector that can contain pinkie components.
   wrapped with react error boundary.
   
   [:p/pinkie {:content {:map-keywords true
               :fix-style false
               :hiccup [:p/vega spec]}}]
   "
  [output]
  [error-boundary
   [pinkie-render-unsafe output]])

(register-component :p/pinkie pinkie-render)

(defn ^{:category :pinkie
        :hidden true}
  components
  "displays pinkie component table as text.
   useful for debugging as it has no dependencies."
  []
  [pinkie.text/text (component-list->str)])

(register-component :p/components components)