(ns demo.app
  (:require
   [reagent.core :as r]
   [frontend.page :refer [reagent-page]]
   [pinkie.default-setup]
   [pinkie.html :refer [html]] ; html with script injection
   [pinkie.text :refer [text]]
   [pinkie.error :refer [error-boundary]]
   [pinkie.throw-exception :refer [exception-component]]
   [pinkie.pinkie :refer [tag-inject] :refer-macros [register-component]]
   ; demo
   [demo.events] ; side-effects
   [demo.jsrender]
   [demo.snippets :as s]
   [demo.vizspec-setup :refer [show]]
   ))

(register-component :p/text text)
(register-component :p/phtml html)
(register-component :p/exc exception-component)


(defn wrap [component]
  [:div.m-5.bg-blue-200
   [:div.bg-gray-300.m-5.text-blue-400
    [text (pr-str component)]]
   [error-boundary
    (tag-inject component)]
   ])



(defmethod reagent-page :demo/main [& args]
  [:div
   [:h1 "hello, pinkie"]

   [wrap
    [:p/text "hello,\n world! (text in 2 lines)"]]

   [wrap
    s/js-demo]

   [wrap
    [:p/exc "this goes wrong"]]

   [wrap
    [:p/unknown-something "No renderer registered for this.."]]

   [wrap
    [:div
     [:h1 "html in reagent"]
     [:p "please open developer tools to check if hello world gets printed."]
     [:p/phtml "<script src='/r/hello.js'></script>"]]]

   [:h1 "now new viz-spec system:"]

   [show
    [:div
     ['customer {:first "John" :last "Doe"}]
     ['customer {:first "Werner" :last "von Braun"}]
     ['text "And now\nWe see!\nBut this is really long\nand extended"]]]
   
   ])
