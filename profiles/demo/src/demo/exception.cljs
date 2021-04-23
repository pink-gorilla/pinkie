(ns demo.exception
  (:require
   [pinkie.pinkie :refer-macros [register-component]]
))


(defn ^{:category :pinkie
        :hidden true}
  exception-component
  "a component that throws exceptions for testing."
  []
  (throw {:type :custom-error
          :message "Something unpleasant occurred"}))

(register-component :p/exc exception-component)