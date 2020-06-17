(ns pinkie.default-setup
  (:require
   [pinkie.pinkie] ; tag injection
   [pinkie.pinkie-render] ; create atom state from clojure

   ; reagent components
   [pinkie.html] ; html with script injection
   [pinkie.jsrender] ; module loader 
   [pinkie.text] ; text with newline
   [pinkie.gtable] ; table (legacy gorilla compatibility)
   ))