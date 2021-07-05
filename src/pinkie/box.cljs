(ns pinkie.box)

(defn container-style [box]
  (let [box (or box :sm)]
    {:style
     (case box
       :sm {:width "400px" :height "300px"}
       :md {:width "600px" :height "400px"}
       :lg {:width "800px" :height "600px"}
       :fl {:width "100%"  :height "100%"}
       :fs {:width "100vw" :height "100vw"
            :position "absolute"
            :top 0
            :left 0
            :bottom 0
            :right 0
            :z-index 5000})}))

(defn apply-style [{:keys [box style] :as opts}]
  (let [s (merge (container-style box) opts)]
    ;(println "merged style: " s "box: " box " opts:" opts)
    s))