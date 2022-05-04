(ns demo.jsrender
  (:require
   [pinkie.pinkie :refer-macros [register-component]]
   [pinkie.jsrender :refer [render-js]]
   ["/demo/calculator" :as calculator :refer [addone]]))

(defn add-one [spec]
   [render-js (merge spec {:f addone})])

(register-component :p/add-one-js add-one)
