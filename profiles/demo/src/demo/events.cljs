(ns demo.events
  (:require
   [taoensso.timbre :as timbre :refer [info]]
   [re-frame.core :refer [reg-event-db dispatch]]))

(reg-event-db
 :demo/start
 (fn [db [_]]
   (info "starting demo app..")
   (dispatch [:webly/status :running])
   (dispatch [:ga/event {:category "pinkie-demo" :action "started" :label 77 :value 13}])
   db))



