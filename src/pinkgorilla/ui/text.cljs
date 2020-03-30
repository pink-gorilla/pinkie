(ns pinkgorilla.ui.text
  "Render text (as string) to html
   works with \n newlines
   Needed because \n is meaningless in html"
  (:require
   [clojure.string :as str]
   [pinkgorilla.ui.pinkie :refer [register-tag]]))

;; stolen from gorilla-notebook.

(defn line-with-br [t]
  [:div
   [:span.font-mono.text-lg.whitespace-pre t]
   [:br]])

(defn text [t]
  (let [lines (str/split t #"\n")]
    (into [:div {:gorilla_ui "text"}] (map line-with-br lines))))

(register-tag :p/text text)

(comment

  (text "hello\nworld"))