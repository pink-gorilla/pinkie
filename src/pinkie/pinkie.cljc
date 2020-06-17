(ns pinkie.pinkie
  (:require
   [clojure.string :as str]
   [clojure.walk :refer [prewalk postwalk]] ; cljs 1.10 still does not have walk fixed
   ; [pinkie.walk :refer [prewalk]] ; TODO: replace this as soon as 1.11 cljs is out.
   #?(:cljs [cljs.pprint])
   #?(:cljs [reagent.core :as r :refer [atom]])
   #?(:cljs [reagent.impl.template :refer [HiccupTag cached-parse]])
 ;  [taoensso.timbre :refer-macros (info)]
   ))

; notes
; has to be cljc file, because register-tag is a macro

;; DEPRECIATED:
; (def custom-renderers (atom {}))

;(defn get-renderer [tag]
;  (tag @custom-renderers))

;(defn renderer-info [[k fun]]
;  {:k k
;   :r (pr-str fun)})

;(defn renderer-list []
;  (map renderer-info (seq @custom-renderers)))

; new code with macro (to capture meta data)

(def component-registry (atom {}))

(defn register-tag
  "DEPRECIATED: please use register-component macro instead.
   registers a reagent component in the pinkie registry."
  [pinkie-tag func]
  (swap! component-registry assoc pinkie-tag {:meta {}
                                              :tag pinkie-tag
                                              :fun ~func}))

(defmacro register-component
  "registers a reagent component in the pinkie registry.
   Captures meta-data of the function symbol."
  [pinkie-tag func]
  `(swap! component-registry assoc ~pinkie-tag {:meta (meta (var ~func))
                                                :tag ~pinkie-tag
                                                :fun ~func}))

(defn get-component [tag]
  (tag @component-registry))

(defn get-renderer [tag]
  (:fun (get-component tag)))

(defn component-list []
  (vals @component-registry))

#?(:cljs
   (defn component-list->str []
     (with-out-str
       (cljs.pprint/print-table
        [:tag :ns :name :category]
        (map #(merge (:meta %) {:tag (:tag %) :fun (:fun %)}) (component-list))))))

#?(:cljs (defn print-components []
           (println
            (component-list->str))))

  ; it would be ideal to let reagent deal with this, but the below line did not work.
  ;(gobj/set reagent.impl.template/tag-name-cache (name k) v)

; mfikes approach would be great, but does not work
; https://github.com/reagent-project/reagent/issues/362

#_(defn register-tag2 [k v]
    (gobj/set reagent.impl.template/tag-name-cache k v))

#_(defn register-tag3 [kw c]
    (register-tag2 (name kw) (r/as-element c)))

#?(:cljs (defn clj->json
           [ds]
           (.stringify js/JSON (clj->js ds))))

(def pinkie-namespace (namespace :p/test))

(defn pinkie-tag? [tag]
  (let [kw-namespace (namespace tag)]
    (= pinkie-namespace kw-namespace)))

(defn pinkie-exclude? [hiccup-vector]
  (contains? (meta hiccup-vector) :r))

(defn- hiccup-vector? [hiccup-vector]
  (and
   (vector? hiccup-vector)
   (not (map-entry? hiccup-vector)); ignore maps
   (keyword? (first hiccup-vector)); reagent syntax requires first element  to be a keyword
   ))

(defn should-replace? [hiccup-vector]
  (if (hiccup-vector? hiccup-vector)
    (let [tag (first hiccup-vector)]
      (and (not (pinkie-exclude? hiccup-vector))
           (pinkie-tag? tag)
           #_(not (html5-tag? tag))))
    false))

(defn unknown-tag
  "ui component for unknown tags - so that we don't need to catch react errors
   Currently not yet used (see resolve function)"
  [tag]
  [:span.unknown-tag {:style {:background-color "red"}}
   (str "Unknown Tag: " tag)])

(defn replace-tag-in-hiccup-vector
  "input: hiccup vector
   if keyword (first position in vector) has been registered via register-tag,
   then it gets replaced with the react function,
   otherwise keyword remains"
  [hiccup-vector]
  (let [;_ (.log js/console "pinkie replacing: " (pr-str hiccup-vector))
        tag (first hiccup-vector)
        render-function (get-renderer tag)]
    (if (nil? render-function)
      (do (println "pinkie unknown tag: " (name tag))
          (unknown-tag tag))
      (into [] (assoc hiccup-vector 0 render-function)))))

(defn  tag-inject
  "resolve function-as symbol to function references in the reagent-hickup-map.
   Leaves regular hiccup data unchanged."
  [hiccup-vector]
  (prewalk
   (fn [x]
     (if (should-replace? x)
       (replace-tag-in-hiccup-vector x)
       x))
   hiccup-vector))

;; Hiccup accepts Style as string, but Reagent does not.
;; Example: [:rect {:width "100%", :height "100%", :style "stroke: none; fill: #FFFFFF;"}]  

(defn to-map-style [s]
  (let [style-vec (map #(str/split % #":") (str/split s #";"))]
    (into {}
          (for [[k v] style-vec]
            [(keyword (str/trim k)) (str/trim v)]))))

(defn is-style? [x]
  ;(println "is-style? " x)
  (if (and (vector? x)
           (= 2 (count x))
           (= (first x) :style)
           (string? (second x)))
    true
    false))

(defn replace-style [x]
  (println "pinkie replacing string style: " x)
  (into [] (assoc x 1 (to-map-style (last x)))))

(defn convert-style-as-strings-to-map
  "resolve function-as symbol to function references in the reagent-hickup-map.
   Leaves regular hiccup data unchanged."
  [hiccup-vector]
  (prewalk
   (fn [x]
     (if (is-style? x)
       (replace-style x)
       x))
   hiccup-vector))

(comment
  (to-map-style "background-color: blue; font-size: 14px")
;=> {:background-color "blue" :font-size "14px"}  
  )


;; RENDER-AS


(defn render-as? [hiccup-vector]
  (contains? (meta hiccup-vector) :p/render-as))

(defn wrap-renderer [x]
  (let [renderer (:p/render-as (meta x))]
    (println "pinkie wrapping renderer " renderer " to: " x)
    ^:R [renderer x]))

(defn convert-render-as
  "resolve function-as symbol to function references in the reagent-hickup-map.
   Leaves regular hiccup data unchanged."
  [hiccup-vector]
  (postwalk
   (fn [x]
     (if (render-as? x)
       (wrap-renderer x)
       x))
   hiccup-vector))

