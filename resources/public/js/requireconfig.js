
require.config({
    //urlArgs: "bust=" + (new Date()).getTime(), // awb99: For development cache busting, see: https://stackoverflow.com/questions/8315088/prevent-requirejs-from-caching-required-scripts
    paths: {
        demo: 'js/requiredemo',
        loadstring: 'js/loadstring', // plugin to load modules from strings

        // VEGA
        'vega-embed': 'https://cdn.jsdelivr.net/npm/vega-embed?noext',
        'vega-lib': 'https://cdn.jsdelivr.net/npm/vega-lib?noext',
        'vega-lite': 'https://cdn.jsdelivr.net/npm/vega-lite?noext',
        'vega': 'https://cdn.jsdelivr.net/npm/vega?noext',

        // LEGACY VEGA
        'lvega-embed': 'https://cdn.jsdelivr.net/npm/vega-embed@2.26.1?noext',
        'lvega-lite': 'https://cdn.jsdelivr.net/npm/vega-lite@1.3.1?noext',
        'lvega': 'https://cdn.jsdelivr.net/npm/vega@2.6.5?noext',
        'lvega-dataflow': 'https://cdn.jsdelivr.net/npm/vega-dataflow@2.2.0?noext',

        'highcharts': 'https://code.highcharts.com/7.0.3/highcharts', // .js extension is added automatically

         /* Note the `delayStartupUntil=configured` parameter */
       // 'mathjax' : 'https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=TeX-AMS-MML_SVG-full.js&delayStartupUntil=configured'
      //  'mathjax' : 'https://cdnjs.cloudflare.com/ajax/libs/mathjax/3.0.1/es5/sre/sre_browser.min.js?config=TeX-AMS_HTML&amp;delayStartupUntil=configured' // .js extension is added automatically
          'mathjax' : 'https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml' // .js extension is added automatically
    },
    shim: {
        // VEGA dependencies
        "vega-lite": {deps: ["vega"]},
        "vega-embed": {deps: ["vega-lite"]},

        "lvega-vega": {deps: ["lvega-dataflow"]},
        "lvega-lite": {deps: ["lvega"]},
        "lvega-embed": {deps: ["lvega-lite"]},
        mathjax: {
            exports: "MathJax",
            init: function () {
                console.log("MyMathJax init via requirejs config..");
                console.log("MathJax: " + MathJax)
                var config = {
                         messageStyle: "none",
                         showProcessingMessages: false,
                         skipStartupTypeset:     true,
                         tex2jax:                {inlineMath: [["@@", "@@"]]}};
                //MathJax.Hub.Config({tex: {inlineMath: [['$', '$'], ['\\(', '\\)']]},
                //                    svg: {fontCache: 'global'} 
                //                   });
              //MathJax.Hub.Startup.onload();
              
              return MathJax;
            }
        }
    }

});

// ?noext

//'vega-embed':  'vega-embed@3?noext',
//      'vega-lib': 'vega-lib?noext',
//      'vega-lite': 'vega-lite@2?noext',
//      'vega': 'vega@3?noext'

/*
function testRequireJS() {
    require(["demo"], function (demo) {
        demo.render("ABC-DEMO-123", {a: 15, b: 10, t: "render test"});
    });

    // var world_module = "define([],function(){return 'world!'})";
    var world_module = "define([],function(){return {a: function (name) {return 'hello, ' + name}}})";
    require(["loadstring!" + world_module],
        function (x) {
            console.log("Hello " + x);
        })

    require(["vega"], function (y) {
        console.log("Vega Embed ");
        console.log(y);
    })
}
*/
//testRequireJS();


/* awb99 2020 03 05: this was old code in notebook

;; <!--script src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
;; <script type="text/x-mathjax-config">
;; MathJax.Hub.Config({
;;                    tex2jax: {
;;                              inlineMath: [['$','$']],
;;                              processClass: "mathjax",
;;                              ignoreClass: "no-mathjax"
;;                              }
;;                    });
;; </script-->

(defn queue-mathjax-rendering
  [id]
  (if-let [mathjax (.-MathJax js/window)]
    (doto (.-Hub mathjax)
      (.Queue #js ["Typeset" (.-Hub mathjax) id]))
    (warn "Missing global MathJax")))


(defn output-latex
  [output _] ; seg-id
  (let [uuid (uuid/uuid-string (uuid/make-random-uuid))
        span-kw (keyword (str "span#" uuid))]
    (reagent/create-class
     {:component-did-mount  (fn [_]
                              (queue-mathjax-rendering uuid))
      ;; :component-did-update (fn [_ _])
      :reagent-render       (fn []
                              [value-wrap
                               (get output :value)
                               [span-kw {:class                   "latex-span"
                                         :dangerouslySetInnerHTML {:__html (str "@@" (:content output) "@@")}}]])})))



;; TODO: MathJax does not kick in with advanced optimization
(defn init-mathjax-globally!
  "Initialize MathJax globally"
  []
  (if-let [mathjax (.-MathJax js/window)]
    (doto (.-Hub mathjax)
      (.Config (clj->js {:messageStyle           "none"
                         :showProcessingMessages false
                         :skipStartupTypeset     true
                         :tex2jax                {:inlineMath (clj->js [["@@", "@@"]])}}))
      (.Configured))
    (print "MathJax unavailable")))

    */