(ns pinkgorilla.ui.core-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [cljs.pprint]
   [pinkgorilla.ui.pinkie :refer [registered-tags]]))


(defn print-registered-tags []
  (with-out-str
    (cljs.pprint/print-table (registered-tags))))

(println (print-registered-tags))