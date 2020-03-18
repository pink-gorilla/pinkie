(ns pinkgorilla.ui.walk
  " For pinkie tag injection we need a more up to date version
    of walk.

    https://clojure.atlassian.net/browse/CLJ-2031
    https://github.com/clojure/clojurescript/commit/9979feaf69765460238015a8468ba2fc803e3860#diff-f9fd6d8ba81d5a2bb5b7d2cca3289103
  ")

(defn walk
  "Traverses form, an arbitrary data structure.  inner and outer are
  functions.  Applies inner to each element of form, building up a
  data structure of the same type, then applies outer to the result.
  Recognizes all Clojure data structures. Consumes seqs as with doall."

  {:added "1.1"}
  [inner outer form]
  (cond
    (list? form)      (outer (apply list (map inner form)))
    (map-entry? form) (outer (MapEntry. (inner (key form)) (inner (val form)) nil))
    (seq? form)       (outer (doall (map inner form)))
    (record? form)    (outer (reduce (fn [r x] (conj r (inner x))) form form))
    (coll? form)      (outer (into (empty form) (map inner form)))
    :else             (outer form)))

(defn prewalk
  "Like postwalk, but does pre-order traversal."
  {:added "1.1"}
  [f form]
  (walk (partial prewalk f) identity (f form)))

(defn postwalk
  "Performs a depth-first, post-order traversal of form.  Calls f on
  each sub-form, uses f's return value in place of the original.
  Recognizes all Clojure data structures. Consumes seqs as with doall."
  {:added "1.1"}
  [f form]
  (walk (partial postwalk f) f form))