(ns demo.jsrender
  (:require
   [pinkie.pinkie :refer-macros [register-component]]
   [pinkie.jsrender :refer [render-js]]
   ["/demo/calculator" :as calculator :refer [addone]]))

(println "calculator: " calculator)
(println "addone: " addone)

(defn add-one [data-clj]
  [:div.bg-green-300.m-5
   [:p "javascript calculator:"]
   [render-js {:f addone :data data-clj}]])

(register-component :p/add-one-js add-one)
