(ns demo.app
  (:require
   [reagent.core :as r]
   [webly.user.app.app :refer [webly-run!]]
   [webly.web.handler :refer [reagent-page]]
   [pinkie.default-setup]
   [pinkie.text :refer [text]]
   [pinkie.error :refer [error-boundary]]
   [pinkie.pinkie :refer [tag-inject]]
   ; demo
   [demo.routes :refer [routes-api routes-app]]
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
     [:p "10 - fixed"]
     [:p/add-one-js 10]
     [:p/add-one-js 100]
     [:p/add-one-js 1000]
     [:p "atom"]
     [:p/add-one-js @v]
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








(defn ^:export start []
  (webly-run! routes-api routes-app))