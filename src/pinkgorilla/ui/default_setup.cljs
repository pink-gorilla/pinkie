(ns pinkgorilla.ui.default-setup
  (:require
   [pinkgorilla.ui.pinkie] ; tag injection
   [pinkgorilla.ui.widget] ; create atom state from clojure
      
   ; reagent components
   [pinkgorilla.ui.html] ; html with script injection
   [pinkgorilla.ui.jsrender] ; module loader 
   [pinkgorilla.ui.text] ; text with newline
   
   ; ui widgets
   [pinkgorilla.widget.acombo]
   [pinkgorilla.widget.greeter]
   [pinkgorilla.widget.ainput]
   [pinkgorilla.widget.combo]
   [pinkgorilla.widget.slider]))