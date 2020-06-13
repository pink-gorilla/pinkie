# Pinkie
 [![GitHub Actions status |pink-gorilla/gorilla-renderable-ui](https://github.com/pink-gorilla/pinkie/workflows/CI/badge.svg)](https://github.com/pink-gorilla/pinkie/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/gorilla-renderable-ui.svg)](https://clojars.org/org.pinkgorilla/gorilla-renderable-ui)

- Pinkie is a simple registry for reagent components. 
- Instead of referring to components by symbol, you refer to by keyword.
- Think of it as new html tags that are linked to your reagent components.
- This is useful to declaratively specify ui. 
- This project is used in [Notebook](https://github.com/pink-gorilla/gorilla-notebook) and [Goldly](https://github.com/pink-gorilla/goldly).
- Pinkie has minimal dependencies, so it can be painlessly integrated to your project. 


# Pinkie DSL

The idea is to extend Reagent hiccup syntax to do visualizations in an easier way.

## :p/ renderers

The idea is that renderers are just reagent components, that can be triggered with (namespaced keywords). 

```  
[:p/vega spec]
[:p/leaflet spec]
[:p/bongo spec]
```

- Tags with :p namespace will be rendered with the corresponding render function.
- The tags are namespaced for 2 reasons: 1. When user trys to render to a non-existing renderer, then an appropriate error-component can be displayed, and 2. the tag replacing will then not replace tags that it should not replace (for example svg drawing instructions inide a : svg tag, or other custom dsl that could be confused with reagent hiccup syntax).  
- The spec needs to be serializable to clojurescript. In cases where data is not serializable, it needs to be converted before using pinkie system (for example to render images). 
- The rendering system needs to handle the management of the tag replacement table. In pinkie, the renderers can be added dynamically at runtime. 

The reagent syntax can be nested:

```  
[:div
  [:h1 "demo"] 
  [:p/vega spec]]
```

## cljs - reagent atoms / dynamic updates

- If spec is a dereferenced reagent atom, then reagent will re-render the
  component upon change of the data. 
- Note that this works only on cljs. (Goldly will be able to convert 
  clojure atoms to mirrored reagent atoms.)

## Dynamically loaded renderers

- If renderers are loaded at runtime, a loading component must
be displayed until renderer is loaded.


## Style as string

- In hiccup styles can be rendered as string.
```
   [:rect {:width "100%" :height "100%"
           :style "background-color: blue; font-size: 14px"}]
```
- Such syntax is not accepted by reagent though, but it should
- Pinkie will therefore preprocess this data, so that reagent gets this:
``` 
   [:rect {:width "100%" :height "100%"
           :style {:background-color "blue" :font-size "14px"}}]
```
- This is important for certain libraris that define styles as strings
  (happens for example in R html output). 


# Licence

This code is licensed to you under the MIT licence. See LICENCE.txt for details.
