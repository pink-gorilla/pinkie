(ns demo.snippets
  (:require
   [reagent.core :as r]))

(defonce v (r/atom 20))

(def js-demo
  [:div
   [:p "small"]
   [:p/add-one-js {:data {:nr 10 :color "yellow"} :box :sm}]
   [:p "md medium"]
   [:p/add-one-js {:data {:nr 100 :color "red"} :box :md}]
   [:p "lg large"]
   [:p/add-one-js {:data {:nr 100 :color "green"} :box :lg}]
   [:p "custom size"]
   [:p/add-one-js {:data {:nr 10 :color "orange"} :style {:width "60px" :height "60px"}}]

   [:div.grid.grid-cols-2
    [:p/add-one-js {:data {:nr 1000 :color "green"} :box :fl :style {:height "2cm"}}]
    [:p/add-one-js {:data {:nr 1000 :color "blue"} :box :fl :style {:height "3cm"}}]]

   [:p "atom"]
   [:p/add-one-js {:data {:nr @v :color "blue"} :box :sm}]
   [:button.bg-red-700.p-2 {:on-click #(swap! v inc)} "click to add 1"]]
  

  )