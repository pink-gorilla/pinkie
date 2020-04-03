# Gorilla Renderable UI - Custom cljs based rendering Pink Gorilla Notebook [![GitHub Actions status |pink-gorilla/gorilla-renderable-ui](https://github.com/pink-gorilla/gorilla-renderable-ui/workflows/CI/badge.svg)](https://github.com/pink-gorilla/gorilla-renderable-ui/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/gorilla-renderable-ui.svg)](https://clojars.org/org.pinkgorilla/gorilla-renderable-ui)

- This project is part of [Pink Gorilla Notebook](https://github.com/pink-gorilla/gorilla-notebook)

Clojurescript functions and reagent components used in notebook.
This project is always included in the notebook.

# Clojurescript renderer
- Data structures from ClojureScript have to be converted to some kind
of visual repesentation so that the notebook can render them
- For all ClojureScript datatypes default renderers are defined here
- The notebook receives the render-datastructure and renders it to the notebook cell.

# Pinkie - Value viewer
- Reagent Hiccup custom tag registry 
- Will replace tag keywords with react function prior to rendering
Wert x soll dargestellt werden.
Value viewer: f(x)    
f= render funktion. Render funktion passiert immer in clojurescript. Implementiert als reagent render [f x]
x = darzustellende daten. X muss serialisierbar sein ueber transit. Wenn x nicht ueber transit serialisierbar ist dann muss clojure repl daten umwandeln (z.B. bei bildern); am besten ueber client pull, nicht push.
Im Moment haben wir typ basiertes rendering also (lookup type) = f. Fuer jeden typ gibt es einen lookup der render funktion.
Der gleiche typ kann aber unterschiedlich ausgegeben werden.
Wichtig ist, dass clojure einfach auf dynamisch definierbare vale viewer zugreifen kann. Das werden wir mit keywords und hiccup syntax beibehalten:
[:p/vega x]
[:p/leaflet x]
[:p/custom-plot x]
Clojurescript muss eine keyword mapping tabelle haben.
Ist ein keyword nicht gemappt kommt error komponente zur anzeige
Die value viewer werden in eine lazy load Komponente gewrappt - solange klasse bucht geladen ist kommt 'loading viewer…' 
Wenn (type x) = atom und clojure => clojure muss value aenderung ueber websocket pushen.




## Licence

This code is licensed to you under the MIT licence. See LICENCE.txt for details.
