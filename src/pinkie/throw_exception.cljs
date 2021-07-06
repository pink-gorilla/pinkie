(ns pinkie.throw-exception)

(defn ^{:category :pinkie
        :hidden true}
  exception-component
  "a component that throws exceptions for testing."
  []
  (throw {:type :custom-error
          :message "Something unpleasant occurred"}))
