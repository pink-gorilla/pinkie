(ns pinkie.ui.jsrender
  "jsrender is part of a jsrequire based module system to render data via javascipt inside
   reagent components. Typically custom ui renderers would use it:
   [jsrender custom-module data]"
  (:require
   [reagent.core :as reagent]
   [reagent.dom]
   [cljs-uuid-utils.core :as uuid]
   [pinkie.ui.error :refer [error-boundary]]
   [pinkie.ui.box :refer [container-style apply-box-style]]))

(defn info [s]
  (.log js/console s))

(defn render-function-impl
  [{:keys [f data box] :as spec}]
  (let [uuid (uuid/uuid-string (uuid/make-random-uuid))]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
    (reagent/create-class
     {:display-name "render-function-impl"
      :reagent-render (fn [{:keys [f data box] :as spec}] ;; remember to repeat parameters
                        [:div (-> (apply-box-style spec)
                                  (assoc :id uuid)
                                  (dissoc :f))])
      :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             ;(println "c-d-m: " this)
                             ;(info (str "jsrender init data: " data))
                             (f (reagent.dom/dom-node this) data))
      :component-did-update (fn [this old-argv]
                              (let [new-argv (rest (reagent/argv this))
                                    [arg1] new-argv
                                    {:keys [f data]} arg1]
                                ;(println "component did update: " this "argv: " new-argv)
                                (f (reagent.dom/dom-node this) data)))

      ; 2021 04 awb99: component-will-update is depeciated 
      ;:component-will-update (fn [this [_ {:keys [f data]}]]
      ;        ; with changing of parameters, re-render the component. (important for vega charts)
      ;                         (info (str "jsrender new params: " data))
      ;                         (f (reagent.dom/dom-node this) data))
      })))

(defn render-clj [spec]
  [error-boundary
   [render-function-impl spec]])

(defn ^{:category :pinkie}
  render-js
  "reagent component that renders a js function,
       calls 
       parameters:
         f    the js render function
              gets js data
         data a clojure datastructure that will be converted to js
              before calling f"
  [{:keys [data] :as spec}]
  [render-clj (assoc spec :data (clj->js data))])



