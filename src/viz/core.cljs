(ns viz.core
  (:require
   [viz.hiccup]
   [viz.resolve]
   [viz.show]
   [viz.unknown]))

(def show viz.show/show)

(def resolve-hiccup viz.hiccup/resolve-hiccup)

(def resolve-fn viz.resolve/resolve-fn)

(def no-renderer viz.unknown/no-renderer)



