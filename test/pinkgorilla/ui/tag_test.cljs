(ns pinkgorilla.ui.tag-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [clojure.walk :refer [prewalk]]
   ;[pinkgorilla.ui.walk :refer [prewalk postwalk]]
   [pinkgorilla.ui.pinkie :refer [tag-inject]]
   [pinkgorilla.ui.text :refer [text]]))


;; test if the keyword :math gets replaced with math function


(deftest tag-replacer-working  []
  (is (= (tag-inject [:text 1]) [text 1])))


;; tags in a map should not be exchanged, in a vector yes.


(deftest tag-replacer-map-is-no-vector  []
  (is (= (vector? [:text 1]) true))
  (is (= (vector? {:text 1}) false)))

(deftest tag-replacer-map-should-be-unchanged  []
  (let [hiccup-tags-map  {:text 1}]
    (is (= (tag-inject hiccup-tags-map) hiccup-tags-map))))

;;;
;;; simple test; mainly make sure clojure code has not changed 
;;; (there are breaking changes coming - see pinkgorilla.ui.walk)
;;;

(defn rep [x]
  ;(println "************ " x)
  (into [] (assoc x 0 (str "_" (name (first x))))))

(defn norep [x]
  ;(println "= " x)
  x)

(defn tag-inj-demo [xxx]
  (prewalk
   (fn [x]
     ; [:keyword a b c] we want to replace only when we have a vector whose first element is a keyword
     (if (and (and (vector? x) (not (map-entry? x)))  (keyword? (first x))) ; awb99 changed coll? to vector? because we dont want to operate on maps
       (rep x)
       (norep x)))
   xxx))

(deftest tag-replacer-demo []
  (is (=
       (tag-inj-demo [:a 1 :b [2 3] :c 4 :d {:e 5 :f 6}])
       ["_a" 1 :b [2 3] :c 4 :d {:e 5 :f 6}])))

