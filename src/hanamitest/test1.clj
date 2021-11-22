;; Experiments starting from some of the code in Kira McLean's
;; 11/21/2021 scicloj Hanami workshop

(ns hanamitest.test1
  (:require
   [nextjournal.clerk :as clerk]
   [aerial.hanami.common :as hc :refer [RMV]]
   [aerial.hanami.templates :as ht]))

;; Comments display LaTeX if placed between dollar signs, like this: \$\alpha^{q^n}\$

;; $\alpha^{q^n}$

;; Example of programmatic from LaTeX from
;; https://github.com/nextjournal/clerk/blob/main/notebooks/viewer_api.clj .
;; Note that you have to escape the backslashes.

(clerk/tex "f^{\\circ n} = \\underbrace{f \\circ f \\circ \\cdots \\circ f}_{n\\text{ times}}.\\,")

;; Let's try it.  Here's Freshman's Dream, valid in a finite field if $p$ is
;; the field's characteristic:
(clerk/tex "(\\alpha + \\beta)^{p^n} = (\\alpha^{p^n} + \\beta^{p^n})")

;; Code for the charts displayed below will be defined later in the file.
(declare point-chart-example line-chart-example layered-chart-example)

;; I don't understand where the preceding data display is coming from.

;; Click on the three-dots button to the right of the chart for useful options.
(clerk/vl point-chart-example)

(clerk/vl line-chart-example)

(clerk/vl layered-chart-example)

;; -----------------------

;; ## Setup 
;; ### Start a clerk web server and file watcher

(comment
  (clerk/serve! {:browse? true :watch-paths ["src"]})
  (clerk/show! "src/hanamitest/test1.clj")
)

;; -----------------------

;; Charts displayed above

(def point-chart-example
  (hc/xform ht/point-chart 
            :UDATA "https://vega.github.io/vega-lite/data/cars.json"
            :X "Horsepower"
            :Y "Miles_per_Gallon"
            :YTITLE "Miles per gallon"
            :COLOR "Origin"))


(def line-chart-example
  (hc/xform ht/line-chart
            :FDATA "resources/data/annual-mean-temp.csv"
            :X "Year" ; field name from data
            :XTYPE "temporal" ; display Year data as dates not numbers
            :Y "Annual Mean Temperature" ; field name from data
            :YSCALE {:zero false} ; Don't require zero as bottom Y value
            :TRANSFORM [{:filter {:field "Geography" :equal "UK"}}] ; only show UK
            :MCOLOR "firebrick" ; color of line
            :WIDTH 600 ; width of plot
            ))

(def layered-chart-example
  (hc/xform ht/layer-chart
            :FDATA "resources/data/annual-mean-temp.csv"
            :LAYER
            [(hc/xform ht/line-chart
                       :X "Year"
                       :XTYPE "temporal"
                       :Y "Annual Mean Temperature"
                       :YSCALE {:zero false}
                       :TRANSFORM [{:filter {:field "Geography" :equal "UK"}}]
                       :WIDTH 700)
             (hc/xform ht/line-chart
                       :MCOLOR "firebrick"
                       :X "Year"
                       :XTYPE "temporal"
                       :Y "Annual Mean Temperature"
                       :YSCALE {:zero false}
                       :TRANSFORM [{:filter {:field "Geography" :equal "UK"}}
                                   {:loess :Y :on :X}]
                       :WIDTH 700)]))


;; TIP: The upper-case keywords (e.g. :X) are Hanami *values*,
;; whereas lowercase keywords (e.g. :zero, :filter) are Vega-lite *keys*.
