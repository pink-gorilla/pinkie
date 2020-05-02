(ns pinkgorilla.ui.error
  (:require
   [reagent.core :as reagent]
      ;[re-catch.core :as rc]
   ))


;; https://github.com/reagent-project/reagent/issues/466


(defn error-boundary [_ #_comp]
  (let [error (reagent/atom nil)
        info (reagent/atom nil)]
    (reagent/create-class
     {:component-did-catch (fn [_ #_this _ #_e i]
                             (reset! info i))
      :get-derived-state-from-error (fn [e]
                                      (reset! error e)
                                      #js {})
      :reagent-render (fn [comp]
                        (if @error
                          [:div "Something went wrong."]
                          comp))})))