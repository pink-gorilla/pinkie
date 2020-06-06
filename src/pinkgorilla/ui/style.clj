(ns pinkgorilla.dsl.r.style
  (:require
   [clojure.string :as str]
   [clojure.walk :refer [prewalk]]))
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
  ;(println "pinkie replacing string style: " x)
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