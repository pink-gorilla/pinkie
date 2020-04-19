(ns pinkgorilla.ui.pinkie-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [clojure.walk :refer [prewalk]]
   [pinkgorilla.ui.pinkie :refer [pinkie-tag? html5-tag? pinkie-exclude? should-replace? tag-inject unknown-tag convert-style-as-strings-to-map convert-render-as]]
   [pinkgorilla.ui.text :refer [text]]))

(deftest clojure-keyword-test []
  (is (= ":p/bar" (str :p/bar)))
  (is (= "p" (str (namespace :p/bar))))
  (is (= "bar" (str (name :p/bar)))))

(deftest pinkie-keyword-test []
  (is (= false (pinkie-tag? :h1)))
  (is (= false (pinkie-tag? :vega)))
  (is (= false (pinkie-tag? :bongo/bar)))
  (is (= false (pinkie-tag? :pronto/baz)))
  (is (= true (pinkie-tag? :p/vega))))

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

(deftest vector-test  []
  (is (= (vector? [:text 1]) true))
  (is (= (vector? {:text 1}) false)))

(deftest pinkie-exclude-test []
  (is (= true (pinkie-exclude? ^:r [:text "hi"])))
  (is (= false (pinkie-exclude? ^:R [:text "hi"])))
  (is (= false (pinkie-exclude? [:text "hi"]))))

(deftest should-replace-test  []
  (is (= false (should-replace? {:text 1 :best 2}))) ; map is no hiccup vector
  (is (= false (should-replace? [:p "hello"]))) ; html5 tag does not get replaced
  (is (= false (should-replace? [:p.big "hello"]))) ; html5 tag (with class)does not get replaced
  (is (= false (should-replace? [:text "hello"]))) ; no pinkie tag
  (is (= false (should-replace? ^:r [:p/text "hello"]))) ; pinkie tag, but pinkie exclude
  (is (= true (should-replace? ^:R [:p/text "hello"]))) ;pinkie tag, with pinkie include
  (is (= true (should-replace? [:p/text "hello"]))) ;pinkie tag
  (is (= true (should-replace? [:p/unknown "hello"]))) ; pinkie tag, that is unknown (but this does not matter if we SHOULD replace)
  (is (= false (should-replace? [:p "hello"]))))


;; test if the keyword :math gets replaced with math function


(deftest tag-replacer-map-should-be-unchanged  []
  (is (= (tag-inject {:p/text 1 :best 2}) {:p/text 1 :best 2})) ;; tags in a map should not be exchanged,
  (is (= (tag-inject [:p/text 1 :y 2]) [text 1 :y 2]))) ;; first tag in vector will get replaced


(deftest tag-replacer-exclude  []
  (is (= (tag-inject ^:r [:p/text 1 :best 2])
         ^:r [:p/text 1 :best 2]))) ;^:r is a tag-replace exclude

(deftest tag-replacer-unknown-tag []
  (is (= (tag-inject [:p/unknown 1 :best 2])
         (unknown-tag :p/unknown)))
  (is (= (tag-inject [:div.big [:p/unknown 1 :best 2]])
         [:div.big (unknown-tag :p/unknown)])))


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

;; HICCUP STYLE STRING => MAP test
;; 
(deftest style-replacer-test []
  (let [style-ok [:rect {:width "100%" :height "100%"
                         :style {:background-color "blue" :font-size "14px"}}]
        style-string [:rect {:width "100%" :height "100%"
                             :style "background-color: blue; font-size: 14px"}]]
    (is (= style-ok (convert-style-as-strings-to-map style-string))) ; string ->map
    (is (= style-ok (convert-style-as-strings-to-map style-ok))) ; no further replacing if already hiccup
    ))

;; render-as test
;; 
(deftest render-as-test []
  (let [raw ^{:p/render-as :p/vega} {:spec 123}
        ok [:p/vega {:spec 123}]
        raw-with-hiccup [:div [:h1 "test"] ^{:p/render-as :p/vega} {:spec 123}]
        ok-with-hiccup [:div [:h1 "test"] [:p/vega {:spec 123}]]]
    (is (= ok (convert-render-as raw)))
    (is (= ok-with-hiccup (convert-render-as raw-with-hiccup)))))