
require.config({
    //urlArgs: "bust=" + (new Date()).getTime(), // awb99: For development cache busting, see: https://stackoverflow.com/questions/8315088/prevent-requirejs-from-caching-required-scripts
    paths: {
        demo: 'js/requiredemo',
        loadstring: 'js/loadstring', // plugin to load modules from strings

        // PINKIE
        'pinkie-highchart': 'js/pinkie/highchart',
        'pinkie-json': 'js/pinkie/json',
        'pinkie-vega': 'js/pinkie/vega',
        'pinkie-math': 'js/pinkie/math',

        // VEGA
        'vega-embed': 'https://cdn.jsdelivr.net/npm/vega-embed?noext',
        'vega-lib': 'https://cdn.jsdelivr.net/npm/vega-lib?noext',
        'vega-lite': 'https://cdn.jsdelivr.net/npm/vega-lite?noext',
        'vega': 'https://cdn.jsdelivr.net/npm/vega?noext',

        // LEGACY VEGA
        //'lvega-embed': 'https://cdn.jsdelivr.net/npm/vega-embed@2.26.1?noext',
        //'lvega-lite': 'https://cdn.jsdelivr.net/npm/vega-lite@1.3.1?noext',
        //'lvega': 'https://cdn.jsdelivr.net/npm/vega@2.6.5?noext',
        //'lvega-dataflow': 'https://cdn.jsdelivr.net/npm/vega-dataflow@2.2.0?noext',

        'highcharts': 'https://code.highcharts.com/7.0.3/highcharts', // .js extension is added automatically

         /* Note the `delayStartupUntil=configured` parameter */
         // 'mathjax' : 'https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=TeX-AMS-MML_SVG-full.js&delayStartupUntil=configured'
         //  'mathjax' : 'https://cdnjs.cloudflare.com/ajax/libs/mathjax/3.0.1/es5/sre/sre_browser.min.js?config=TeX-AMS_HTML&amp;delayStartupUntil=configured' // .js extension is added automatically
           'mathjax' : 'https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml' // .js extension is added automatically
         //"mathjax" : "js/mathjax"
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
