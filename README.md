# Gorilla Renderable UI - Custom cljs based rendering Pink Gorilla Notebook [![GitHub Actions status |pink-gorilla/gorilla-renderable-ui](https://github.com/pink-gorilla/gorilla-renderable-ui/workflows/CI/badge.svg)](https://github.com/pink-gorilla/gorilla-renderable-ui/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/gorilla-renderable-ui.svg)](https://clojars.org/org.pinkgorilla/gorilla-renderable-ui)

- This project is part of [Pink Gorilla Notebook](https://github.com/pink-gorilla/gorilla-notebook)

# Clojurescript renderer
- Data structures from ClojureScript have to be converted to some kind
of visual repesentation so that the notebook can render them
- For all ClojureScript datatypes default renderers are defined here
- The notebook receives the render-datastructure and renders it to the notebook cell.

# Pinkie
- Reagent Hiccup custom tag registry 
- Will replace tag keywords with react function prior to rendering


## Licence

This code is licensed to you under the MIT licence. See LICENCE.txt for details.
