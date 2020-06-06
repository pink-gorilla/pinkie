(ns pinkgorilla.ui.default-setup
  (:require
   [pinkgorilla.ui.pinkie] ; tag injection
   [pinkgorilla.ui.pinkie-render] ; create atom state from clojure

   ; reagent components
   [pinkgorilla.ui.html] ; html with script injection
   [pinkgorilla.ui.jsrender] ; module loader 
   [pinkgorilla.ui.text] ; text with newline
   [pinkgorilla.ui.gtable] ; table (legacy gorilla compatibility)
   ))