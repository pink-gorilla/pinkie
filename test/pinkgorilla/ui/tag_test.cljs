(ns pinkgorilla.ui.tag-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [clojure.walk :refer [prewalk]]
   [pinkgorilla.ui.pinkie :refer [tag-inject]]
   [pinkgorilla.ui.text :refer [text]]))

(deftest vector-test  []
  (is (= (vector? [:text 1]) true))
  (is (= (vector? {:text 1}) false)))

;; test if the keyword :math gets replaced with math function

(deftest tag-replacer-map-should-be-unchanged  []
  (is (= (tag-inject {:text 1 :best 2}) {:text 1 :best 2})) ;; tags in a map should not be exchanged,
  (is (= (tag-inject [:text 1 :text 2]) [text 1 :text 2]))) ;; first tag in vector will get replaced


(deftest tag-replacer-exclude  []
  (is (= (tag-inject ^:r [:text 1 :best 2]) [:text 1 :best 2]))) ;^:r is a tag-replace exclude



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

