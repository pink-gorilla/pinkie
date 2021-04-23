(ns pinkie.error
  (:require
   [reagent.core :as reagent]
      ;[re-catch.core :as rc]
   ))


; shows how to impement error boundary:
; https://github.com/reagent-project/reagent/blob/c214466bbcf099eafdfe28ff7cb91f99670a8433/test/reagenttest/testreagent.cljs

; from component-did-catch
; #js {:componentStack 
       ; cmp@http://localhost:8000/r/cljs-runtime/reagent.impl.component.js:508:43
       ; cmp@http://localhost:8000/r/cljs-runtime/reagent.impl.component.js:508:43
       ; cmp@http://localhost:8000/r/cljs-runtime/reagent.impl.component.js:508:43
       ; div


(defn error-boundary [_ #_comp]
  (let [error (reagent/atom nil)
        info (reagent/atom nil)]
    (reagent/create-class
     {:component-did-catch (fn [_  _ i] #_this #_e  ; [this e i]
                             ; i is a js object with stacktrace
                             (println "pinkie component did catch: " i)
                             (reset! info i))

      :get-derived-state-from-error (fn [e]
                                      ; to saves the exception data; gets shows in the dom
                                      ;(println "pinkie component get-derived-state-from-error: " e)
                                      (reset! error e)
                                      #js {})
      :reagent-render (fn [comp]
                        (if @error
                          [:div.bg-red-300
                           "Component Error: "
                           (when @error (pr-str @error))]
                          comp))})))

