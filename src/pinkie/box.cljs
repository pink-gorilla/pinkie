(ns pinkie.box)

(defn container-style [box]
  (let [box (or box :sm)]
    {:style
     (case box
       :sm {:width "400px" :height "300px"}
       :md {:width "600px" :height "400px"}
       :lg {:width "800px" :height "600px"}
       :fl {:width "100%"  :height "100%"}
       :na {} ; no sizing
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

#_(defn size-selector [size text current set-size!]
    (let [bg (if (= size current)
               "bg-green-400"
               "bg-yellow-400")]
      [:span.border-solid.ml-2
       {:class bg
        :on-click #(set-size! size)} text]))

#_(defn box [{:keys [size render-fn box-fn data]}]
    (let [size-a (r/atom (or size :small))
          set-size! (fn [size-new]
                      (println "size: " size-new)
                      (reset! size-a size-new))
         ;handle (useFullScreenHandle)  ; hooks need :f> -  https://github.com/reagent-project/reagent/blob/master/doc/ReactFeatures.md
         ;_ (println "handle: " handle)
          ]
      (fn [{:keys [size render-fn box-fn data]}]
        (let [style (container-style @size-a)
              box-fn (or box-fn box-fn-default)]
          [:div
           [:span
            [size-selector :small "sm" @size-a set-size!]
            [size-selector :medium "md" @size-a set-size!]
            [size-selector :large "lg" @size-a set-size!]
            #_[size-selector :full "fs" @size-a set-size!]]
           [:div.bg-blue-300.overflow-hidden  ; in case the renderer ignores our size
            style
            [render-fn (box-fn data style)]]]))))