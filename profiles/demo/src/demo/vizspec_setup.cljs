(ns demo.vizspec-setup
  (:require
   [viz.show :as viz]
   [pinkie.text]))

(defn customer [{:keys [first last]}]
  [:div.bg-yellow-500.m3
   "Hello: "
   [:span first]
   [:span.text-blue-500.text-bold last]])


(defn resolver [t]
  (cond
    (= t 'customer) customer
    (= t 'text) pinkie.text/text))

(defn show [h]
  (viz/show resolver h))