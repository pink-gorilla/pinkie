(ns pinkie.ui.core
  (:require
   [pinkie.ui.error]
   [pinkie.ui.text]
   [pinkie.ui.html]
   [pinkie.ui.jsrender]
   [pinkie.ui.box]
   [pinkie.ui.gtable]))

(def error-boundary  pinkie.ui.error/error-boundary)
(def text pinkie.ui.text/text)
(def html pinkie.ui.html/html)
(def render-js pinkie.ui.jsrender/render-js)
(def gtable pinkie.ui.gtable/gtable)