(ns pinkgorilla.ui.jsrender
  "jsrender is part of a jsrequire based module system to render data via javascipt inside
   reagent components. Typically custom ui renderers would use it:
   [jsrender custom-module data]"
  (:require
   [reagent.core :as reagent]
   [reagent.dom]
   [cljs-uuid-utils.core :as uuid]
   [pinkgorilla.ui.error :refer [error-boundary]]))

(defn info [s]
  (.log js/console s))

(defn render-function-impl
  [{:keys [f data]}]
  (let [uuid (uuid/uuid-string (uuid/make-random-uuid))]
    (reagent/create-class
     {:display-name "render-function"
      :reagent-render (fn [] [:div {:id uuid}])
      :component-did-mount (fn [this]
                             ;(info (str "jsrender init data: " data))
                             (f (reagent.dom/dom-node this) data))
          ;:component-did-update (fn [this]
          ;                        (run-script uuid data snippet))

 ;(let [[_ series-values] (reagent/argv this)]

      :component-will-update (fn [this [_ {:keys [f data]}]]
              ; with changing of parameters, re-render the component. (important for vega charts)
                               (info (str "jsrender new params: " data))
                               (f (reagent.dom/dom-node this) data))})))

(defn render-clj [data]
  [error-boundary
   [render-function-impl data]])

(defn render-js [{:keys [f data]}]
  (let [data-js {:f f :data (clj->js data)}]
    [render-clj data-js]))

