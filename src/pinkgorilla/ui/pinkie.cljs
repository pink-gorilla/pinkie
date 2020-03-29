(ns pinkgorilla.ui.pinkie
  (:require
  ; [goog.object :as gobj]
   [reagent.core :as r :refer [atom]]
   [reagent.impl.template]
 ;  [taoensso.timbre :refer-macros (info)]
   [clojure.walk :refer [prewalk]] ; cljs 1.10 still does not have walk fixed
  ; [pinkgorilla.ui.walk :refer [prewalk]] ; TODO: replace this as soon as 1.11 cljs is out.
   ))

(def custom-renderers (atom {}))

(defn renderer-list []
  (map #(assoc {} :k (first %) :r (pr-str (last %))) (seq @custom-renderers)))

(defn register-tag [k v]
  (swap! custom-renderers assoc k v)
  ; it would be ideal to let reagent deal with this, but the below line did not work.
  ;(gobj/set reagent.impl.template/tag-name-cache (name k) v)
  )

; mfikes approach would be great, but does not work
; https://github.com/reagent-project/reagent/issues/362

#_(defn register-tag2 [k v]
    (gobj/set reagent.impl.template/tag-name-cache k v))

#_(defn register-tag3 [kw c]
    (register-tag2 (name kw) (r/as-element c)))

(defn clj->json
  [ds]
  (.stringify js/JSON (clj->js ds)))

(def html5-tags
  #{:<>   ; this is technically the reagent-ignore keyword
    :a :abbr :address :area :article :aside :audio
    :b :base :bdi :bdo :blockquote :body :br :button
    :canvas :caption :cite :code :col :colgroup
    :data :datalist :dd :del :dfn :div :dl :dt
    :em :embed
    :fieldset :figcaption :figure :footer :form
    :h1 :h2 :h3 :h4 :h5 :h6 :head :header :hr :html
    :i :iframe :img :input :ins
    :kbd :keygen
    :label :legend :li :link
    :main :map :mark :meta :meter
    :nav :noscript
    :object :ol :optgroup :option :output
    :p :param :pre :progress
    :q
    :rb :rp :rt :rtc :ruby
    :s :samp :script :section :select :small :source :span :strong :style :sub :sup
    :table :tbody :td :template :textarea :tfoot :th :thead :time :title :tr :track
    :u :ul
    :var :video
    :wbr})

(defn unknown-tag
  "ui component for unknown tags - so that we don't need to catch react errors
   Currently not yet used (see resolve function)"
  []
  [:div.unknown-tag {:style {:background-color "red"}}
   [:h3 "Unknown Tag!"]])

(defn resolve-function
  "replaces hiccup-tag with the react function, that was registered via add-tag
   if keyword is no registered function, and not a html5 keyword, then nil will be returned"
  [tag]
  (let [; reagent also has :div#main.big which we have to transform to :div
        tag-typed (reagent.impl.template/cached-parse tag) ; #js {:name "<>", :id nil, :class nil, :custom false}
        ;_ (.log js/console "tag typed:" (pr-str tag-typed))
        tag-clean (keyword (:name (js->clj tag-typed :keywordize-keys true)))
        ;_ (.log js/console "tag clean:" tag-clean)
        v (tag-clean @custom-renderers)
        ;_ (info "renderer found: " v)
        ]
    (cond
      (contains? html5-tags tag-clean) tag
      (not (nil? v)) v
      :else nil)))

(defn resolve-hiccup-vector
  "input: hiccup vector
   if keyword (first position in vector) has been registered via register-tag,
   then it gets replaced with the react function,
   otherwise keyword remains"
  [x]
  (let [reagent-keyword (first x)
        render-function (resolve-function reagent-keyword)]
    (if (nil? render-function)
      (do (.log js/console "replacing: " (pr-str x))
          [unknown-tag reagent-keyword])
      (into [] (assoc x 0 render-function)))))

(defn- hiccup-vector? [x]
  (and
   (vector? x)
   (not (map-entry? x)); ignore maps
   (keyword? (first x)); reagent syntax requires first element  to be a keyword
   ))

(defn resolve-functions
  "resolve function-as symbol to function references in the reagent-hickup-map.
   Leaves regular hiccup data unchanged."
  [reagent-hiccup-syntax]
  (prewalk
   (fn [x]
     (if (hiccup-vector? x)
       (resolve-hiccup-vector x)
       x))
   reagent-hiccup-syntax))

(defn tag-inject
  "replace reagent hiccup tags with registered functions"
  [reagent-hiccup]
  (let [injected (resolve-functions reagent-hiccup)]
    ;(info "tag-inject:" injected)
    injected))
