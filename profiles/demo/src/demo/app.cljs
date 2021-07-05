(ns demo.app
  (:require
   [reagent.core :as r]
   [webly.web.handler :refer [reagent-page]]
   [pinkie.default-setup]
   [pinkie.text :refer [text]]
   [pinkie.error :refer [error-boundary]]
   [pinkie.pinkie :refer [tag-inject]]
   ; demo
   [demo.events] ; side-effects
   [demo.exception]
   [demo.jsrender]))

(defn wrap [component]
  [:div
   [error-boundary
    (tag-inject component)]
   [:div.bg-gray-300.m-5.text-blue-400
    [text (pr-str component)]]])

(defonce v (r/atom 20))

(defmethod reagent-page :demo/main [& args]
  [:div
   [:h1 "hello, pinkie"]

   [wrap
    [:p/text "hello,\n world! (text in 2 lines)"]]

   [wrap
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
     [:p/add-one-js {:data {:nr @v :color "blue"} :box :lg}]
     [:button.bg-red-700.p-2 {:on-click #(swap! v inc)} "click to add 1"]]]

   [wrap
    [:p/exc "this goes wrong"]]

   [wrap
    [:p/unknown-something "No renderer registered for this.."]]

   [wrap
    [:div
     [:h1 "html in reagent"]
     [:p "please open developer tools to check if hello world gets printed."]
     [:p/phtml "<script src='/r/hello.js'></script>"]]]])
