(ns pinkie.pinkie-registry-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [cljs.pprint]
   [pinkie.macros :refer-macros [fn-meta]]
   [pinkie.pinkie :refer [component-list print-components get-component get-renderer] :refer-macros [register-component]]
   [pinkie.jsrender :refer [render-js]] ; this registers jsrender
   [pinkie.html :refer [html]]
   [pinkie.text :refer [text]]
   [pinkie.gtable :refer [gtable]]
   [pinkie.pinkie-render :refer [components]]))

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
(register-component :p/text text)
(register-component :p/phtml html)
(register-component :p/gtable gtable)
(register-component :p/components components)

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
  (is (= (get-in (get-component :p/phtml) [:meta :category]) :ui)) ;checks if register/lookup works and meta storage
  (is (= (:category (fn-meta render-js)) :pinkie))) ;render js has to be validly defined


