(ns pinkgorilla.ui.html5-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [pinkgorilla.ui.htmltags :refer [html5-tag?]]))

(deftest html5-keyword-test []
  (is (= true (html5-tag? :h1)))
  (is (= true (html5-tag? :p)))
  (is (= true (html5-tag? :h1.big)))
  (is (= true (html5-tag? :h1#id-big)))
  (is (= true (html5-tag? :h1#id.big)))
  (is (= false (html5-tag? :bongo/baz)))
  (is (= false (html5-tag? :bongo/baz.big)))
  (is (= false (html5-tag? :bongo/baz#id)))
  (is (= false (html5-tag? :bongo/baz#id.big))))