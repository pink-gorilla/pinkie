(ns pinkgorilla.ui.pinkie-render
  (:require
   [cljs.pprint]
   [pinkgorilla.ui.error :refer [error-boundary]]
   [pinkgorilla.ui.pinkie :refer [tag-inject convert-style-as-strings-to-map convert-render-as
                                  component-list->str]
    :refer-macros [register-component]]
   [pinkgorilla.ui.text]))

(defn reagent-inject [{:keys [map-keywords]} component]
  (let [;_ (info "map-keywords: " map-keywords "widget: " widget " reagent component: " component)
        ;_ (info "meta data: " (meta component))
        component (convert-render-as component)
        ;_ (println "after convert-render-as " component)
        component (if map-keywords (tag-inject component) component)
        component (if map-keywords (convert-style-as-strings-to-map component) component)
        ;_ (info "inject result: " component)
        ]
    [:div.reagent-output component]))

(defn pinkie-render-unsafe
  [output]
  (let [{:keys [hiccup map-keywords widget]} (:content output)]
    (reagent-inject {:map-keywords map-keywords :widget widget} hiccup)))

(defn ^{:category :pinkie}
  pinkie-render [output]
  [error-boundary
   [pinkie-render-unsafe output]])

(register-component :p/pinkie pinkie-render)

(defn ^{:category :pinkie} components []
  [pinkgorilla.ui.text/text (component-list->str)])

(register-component :p/components components)