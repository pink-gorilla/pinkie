(ns pinkie.html5-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [pinkie.tag.htmltags :refer [html5-tag?]]))

(deftest html5-keyword-test []
  (is (= true (html5-tag? :h1)))
  (is (= true (html5-tag? :p)))
  (is (= false (html5-tag? :bongo/baz))))

#_(deftest html5-keyword-test-styling []
    (is (= true (html5-tag? :h1.big)))
    (is (= true (html5-tag? :h1#id-big)))
    (is (= true (html5-tag? :h1#id.big)))
    (is (= false (html5-tag? :bongo/baz.big)))
    (is (= false (html5-tag? :bongo/baz#id)))
    (is (= false (html5-tag? :bongo/baz#id.big))))

