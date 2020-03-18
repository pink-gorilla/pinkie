(ns pinkgorilla.ui.jsrender
  "jsrender is part of a jsrequire based module system to render data via javascipt inside
   reagent components. Typically custom ui renderers would use it:
   [jsrender custom-module data]"
  (:require
   [reagent.core :as reagent]
   [cljs-uuid-utils.core :as uuid]))

(defn info [str]
  (.log js/console str))

;; SCRIPT INJECTION could be done by adding script-elements to the dom with addChild,
;; But we currently use RequireJS to load modules from third party components.

; this is the container of loaded scripts
; (def jsscript-container (reagent/atom {}))
; (def jsscript-root (.getElementById js/document "jsscript-root"))

; (defn inject-script
;  "inject a javascript snippet to the dom
;   Does not keep track of which scripts were loaded or not."
;  [javascript-snippet]
;  (let [script (.createElement js/document "script")]
;    (.setAttribute script "type" "text/javascript")
;    ;(.setAttribute script "src" "helper.js")
;    (set! (.-innerHTML script) javascript-snippet)
;    (.appendChild jsscript-root script)
;    ))


(defn execute-render [id-or-el data module-js]
  (let [;_ (info "executing script!")
        module (js->clj module-js :keywordize-keys true)
        data-js (clj->js data)
        ;_ (info "module:" module)
        render-fn (:render module)
        version (:version module)]
    ;(info render-fn)
    ;(info "rendering version " version)
    (render-fn id-or-el data-js)
    ;(info "rendering: " (render-fn id data))
    ))

;; This works:
;; (.require js/window (clj->js ["demo"]) #(info "result: " %))
;; (run-script "define([],function(){return 'world!'})")
;; (run-script "andreas" "define([],function(){return {render: function (name) {return 'hello, ' + name}}})")


(defn run-script [id-or-el data javascript-snippet]
  (let [module (str "loadstring!" javascript-snippet)
        ;module "demo"
        modules-js (clj->js [module])
        ;_ (info "js module source: " modules-js)
        ]
    (.require js/window modules-js (partial execute-render id-or-el data))
    ;x (js/require modules-js #(info "rcvd2: " %))    ; it should also work this way.
    nil ; suppress returning the require-js module definition
    ))

(defn jsrender
  [{:keys [module data]}]
  (let [uuid (uuid/uuid-string (uuid/make-random-uuid))]
    (reagent/create-class
     {:display-name "jsrender"
      :reagent-render (fn [] [:div {:id uuid}])
      :component-did-mount (fn [this]
                                 ;(run-script uuid data snippet)
                             (info (str "jsrender init data: " data))
                             (run-script (reagent/dom-node this) data module))
          ;:component-did-update (fn [this]
          ;                        (run-script uuid data snippet))

 ;(let [[_ series-values] (reagent/argv this)]

      :component-will-update (fn [this [_ {:keys [module data]}]]
              ; with changing of parameters, re-render the component. (important for vega charts)
                               (info (str "jsrender new params: " data))
                                   ;(run-script uuid data snippet)
                               (run-script (reagent/dom-node this) data module))})))


