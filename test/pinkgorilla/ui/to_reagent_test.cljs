(ns pinkgorilla.ui.to-reagent-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [pinkgorilla.ui.reagent-keyword :refer [keyword-to-reagent unknown-renderer]]
   [pinkgorilla.ui.pinkie :refer [register-tag]]))

(deftest pinkie-register
  (is (= (keyword-to-reagent :BONGO :a 4)
         unknown-renderer)))

(deftest pinkie-register
  (is (= (register-tag :bongo nil)
         {:bongo nil})))