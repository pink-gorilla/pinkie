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

## dynamic updates

- If spec is an atom, then, clj needs to push changes via websocket from clj into a newly created cljs reagent/atom. 
- The renderers work only with values; the rendering system needs to handle the dereferencing of atoms upon tag replacement.

## Dynamically loaded renderers

- If renderers are loaded at runtime, a loading component must
be displayed until renderer is loaded.

## Syntactic sugar for librarys that need visualization

- Goal is to use meta-data to trigger rendering of function output.
- Say we want a function that creates a vega spec to be rendered with a vega renderer:

```
(defn timeseries-plot 
  "timeseries-plot creates vega spec for a timeseries plot"
  [data] 
  ^{:render-with :p/vega} vega-spec)
```

- The user would then use this with:

```
[:div [:h1 "demo"]
      (timeseries-plot data)]
```

- the rendering system would then internally convert this to:

```
[:div [:h1 "demo"]
      [:p/vega (timeseries-plot data)]]
```

- This saves the library author from writing a wrapper.

## Style as string

- In hiccup styles can be rendered as string.
```
   [:rect {:width "100%" :height "100%"
           :style "background-color: blue; font-size: 14px"}]
```
- Such syntax is not accepted by reagent though, but it should
- The rendering will therefor preprocess this data, so that reagent gets this:
``` 
   [:rect {:width "100%" :height "100%"
           :style {:background-color "blue" :font-size "14px"}}]
```
- This is important for certain libraris that define styles as strings. 



# Clojurescript type based rendering

- All cljs datatypes have default renderers that will be selected based on the type of the data.





# Licence

This code is licensed to you under the MIT licence. See LICENCE.txt for details.
