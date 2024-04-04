(ns pinkie.ui.gtable)

(defn grow [r]
  (into [:tr] (map-indexed
               (fn [i c]
                 [:td {:key (str "table-col-" i)} c])
               r)))

(defn ^{:category :layout}
  gtable
  "a reagent component to display an array of arrays 
   as html table
   individual items are reagent components
   successor to legacy-gorilla table-view"
  [rows]
  [:table
   [:tbody
    (map-indexed
     (fn [i r]
       ^:key (str "table-row-" i) [grow r])
     rows)]])
