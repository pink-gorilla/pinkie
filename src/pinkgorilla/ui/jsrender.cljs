(ns pinkgorilla.ui.jsrender
  "jsrender is part of a jsrequire based module system to render data via javascipt inside
   reagent components. Typically custom ui renderers would use it:
   [jsrender custom-module data]"
  (:require
   [reagent.core :as reagent]
   [cljs-uuid-utils.core :as uuid]))

(defn info [s]
  (.log js/console s))

;; We use RequireJS to load javascript modules.
;; This modules can be third party libraries, or our code.

(defn require-js-module
  "loads a module via js-require
   spec can be either [\"demo\"] or [\"demo!params\"]
   executes a callback with the loaded module"
  [spec callback]
  (let [spec-js (clj->js spec)]
    (.require js/window spec-js callback)
    nil ; suppress returning the require-js module definition
    ))

(defn get-js-module-from-string
  "loads a module, and executes a callback containing the loaded module.
   js-snippet: a string, that defines the module. 
              Example: define([],function(){return 'world!'})
                       define([],function(){return {render: function (name) {return 'hello, ' + name}}})
   Implementation:
   The snippet is passed as a parameter to the 'loadstring' module.
   The loadstring module then does the real work."
  [js-snippet callback]
  (let [spec [(str "loadstring!" js-snippet)]]
    (require-js-module spec callback)))

(defn get-js-module-with-name
  "loads a module (with a given name; the module then will be looked up in the
   require.js module configuration). It then executes the callback, passing it
   the loaded module."
  [module-name callback]
  (let [spec [module-name]]
    (require-js-module spec callback)))


;; rendering


(defn render-data-from-js-module
  "renders data to dom, using a loaded js-module"
  [id-or-el data js-module]
  (if (nil? js-module)
    (info (str "module nil. cannot render data:" (pr-str data)))
    (let [module (js->clj js-module :keywordize-keys true)
          {:keys [render version]} module ; extract the functions from the module        
          data-js (clj->js data)]
    ;(info render-fn)
    ;(info "rendering version " version)
      (render id-or-el data-js)
    ;(info "rendering: " (render-fn id data))
      )))

(defn run-script [id-or-el data module-name]
  (get-js-module-with-name  module-name
                            (partial render-data-from-js-module id-or-el data))
  nil ; suppress returning the require-js module definition
  )

(defn jsrender
  [{:keys [module data]}]
  (let [uuid (uuid/uuid-string (uuid/make-random-uuid))]
    (reagent/create-class
     {:display-name "jsrender"
      :reagent-render (fn [] [:div {:id uuid}])
      :component-did-mount (fn [this]
                             ;(info (str "jsrender init data: " data))
                             (run-script (reagent/dom-node this) data module))
          ;:component-did-update (fn [this]
          ;                        (run-script uuid data snippet))

 ;(let [[_ series-values] (reagent/argv this)]

      :component-will-update (fn [this [_ {:keys [module data]}]]
              ; with changing of parameters, re-render the component. (important for vega charts)
                               (info (str "jsrender new params: " data))
                               (run-script (reagent/dom-node this) data module))})))


