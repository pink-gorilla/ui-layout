# ui-layout [![GitHub Actions status |pink-gorilla/ui-layout](https://github.com/pink-gorilla/ui-layout/workflows/CI/badge.svg)](https://github.com/pink-gorilla/ui-layout/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/ui-layout.svg)](https://clojars.org/org.pinkgorilla/ui-layout)


## Container controls
- container/tab 
- container/sidebar 
- container/panel
- container/description-list 

## debounce
- ui.debounce/debounce

## react.rnd (resize and dragable)
- ui.rnd/rnd 

## react gridlayout
- ui.gridlayout/gridlayout

## react.spaces
- spaces/viewport 
- spaces/fill
- spaces/left
- spaces/right pinkgorilla.layout.spaces/right
- spaces/top pinkgorilla.layout.spaces/top
- spaces/bottom pinkgorilla.layout.spaces/bottom
- spaces/
- spaces/left-resizeable pinkgorilla.layout.spaces/left-resizeable
- spaces/right-resizeable pinkgorilla.layout.spaces/right-resizeable
- spaces/top-resizeable pinkgorilla.layout.spaces/top-resizeable
- spaces/bottom-resizeable 
- spaces/centered-vertically 
- spaces/description

# popover
- ui.popover/popover
- ui.popover/tooltip 

# Demo

```
cd demo
clojure -X:webly:npm-install
clojure -X:webly:compile
clojure -X:webly:run
```

Navigate your webbrowser to port 8080. 



ui-flexlayout OLD
clojure-quant/quanta-studio/src/quanta/studio/layout/algo.cljs
clojure-quant/quanta-studio/src/quanta/studio/page/layout.cljs
pink-gorilla/dali-table/demo/src/demo/helper/daliclj.cljs
pink-gorilla/dali-table/demo/src/demo/page/layout_viewer.cljs
pink-gorilla/dali-table/demo/src/demo/page/layout.cljs


layout.flexlayout.core NEW
clojure-quant/algo-juan/quanta-studio/src/quanta/studio/flexlayout/layout/screener.cljs
clojure-quant/algo-juan/quanta-studio/src/quanta/studio/flexlayout/layout/algocharts.cljs
clojure-quant/algo-juan/quanta-studio/src/quanta/studio/flexlayout/layout/backtest.cljs
clojure-quant/algo-juan/quanta-studio/src/quanta/studio/flexlayout/component/backtest.cljs
clojure-quant/algo-juan/quanta-studio/src/quanta/studio/flexlayout/dali/backtest.cljs
clojure-quant/algo-juan/quanta-studio/src/quanta/studio/flexlayout/component/screener.cljs
clojure-quant/algo-juan/quanta-studio/src/quanta/studio/routes.cljs

