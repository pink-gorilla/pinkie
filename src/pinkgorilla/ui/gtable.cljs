(ns pinkgorilla.ui.gtable
  "a reagent component to display an array of arrays 
   as html table
   individual items are reagent components
   successor to legacy-gorilla table-view"
  (:require
   [pinkgorilla.ui.pinkie :refer [register-tag]]))

(defn row [r]
  [:tr
   (into [] (map-indexed
             (fn [i c]
               {:key (keyword (str "table-col-" i))}
               [:tr c]) r))])

(defn gtable
  [rows]
  [:table
   [:tbody
    (into [] (map-indexed
              (fn [i r]
                {:key (keyword (str "table-row-" i))}
                [row r]) rows))]])


(register-tag :gtable gtable)