


(ns pinkgorilla.ui.htmltags)

(def html5-tags
  #{:<>   ; this is technically the reagent-ignore keyword
    :> ; another reagent tag
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