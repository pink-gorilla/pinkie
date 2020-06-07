(ns pinkgorilla.ui.pinkie-registry-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [cljs.pprint]
   [pinkgorilla.ui.macros :refer-macros [fn-meta]]
   [pinkgorilla.ui.pinkie :refer [component-list print-components get-component get-renderer] :refer-macros [register-component]]
   [pinkgorilla.ui.jsrender :refer [render-js]] ; this registers jsrender
   [pinkgorilla.ui.html :refer [html]]
   [pinkgorilla.ui.text :refer [text]]
   [pinkgorilla.ui.gtable :refer [gtable]]
   [pinkgorilla.ui.pinkie-render :refer [components]]))

(defn ^{:category :pinkie-test
        :doc "just used for testing"
        :export true}
  button
  ;"just testing"
  []
  [:p "I am a button"])

(println "register-component"
         (macroexpand '(register-component :p/testbutton2 button)))

(register-component :p/testbutton button)

(print-components)

(deftest registry-lookup-test []
  (is (= (get-renderer :p/text) text)) ; render fubctions in registry are identical
  (is (= (get-renderer :p/phtml) html))
  (is (= (get-renderer :p/gtable) gtable))
  (is (= (get-renderer :p/components) components)))

(defn renderers-with-category []
  (->> (component-list)
       (filter #(get-in % [:meta :category]))))

(deftest registry-meta-test []
  (is (> (count (renderers-with-category)) 0)) ; some components have to have valid category
  (is (= (get-in (get-component :p/phtml) [:meta :category]) :ui-general)) ;checks if register/lookup works and meta storage
  (is (= (:category (fn-meta render-js)) :pinkie))) ;render js has to be validly defined


