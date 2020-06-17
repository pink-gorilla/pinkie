(ns pinkie.macros)

(defmacro def-
  "same as def, yielding non-public def"
  [name & decls]
  (list* `def (with-meta name (assoc (meta name) :private true)) decls))

(defmacro fn-meta
  [func]
  `(meta (var ~func)))


