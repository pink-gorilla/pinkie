(ns pinkgorilla.widget.greeter
  (:require
   [reagent.core :as reagent]
   [pinkgorilla.ui.pinkie :refer [register-tag]]
   ))

(defn greeter [& [name]] ; name==state. state is an atom
  (reagent/create-class {:display-name "greeter"
                         :reagent-render (fn []
                                           [:div
                                            (if (nil? name) ; we check if we got an atom.
                                              [:div
                                               [:h1 "Hello"]
                                               [:h2 "World, from Reagent."]]
                                              [:h2 (str "It is so NICE to see " @name)])])}))

(register-tag :greeter greeter)
