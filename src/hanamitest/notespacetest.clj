(ns hanamitest.notespacetest
    (:require [scicloj.notespace.v4.api :as notespace]
              [scicloj.kindly.api :as kindly]
              [scicloj.kindly.kind :as kind]
              [aerial.hanami.common :as hc :refer [RMV]]
              [aerial.hanami.templates :as ht]))


(comment
  (notespace/restart!))

;; Browse localhost:1903 to see the browser view.

(+ 1 14)

;; Freshman's dream: $(\alpha + \beta)^{p^n} = (\alpha^{p^n} + \beta^{p^n})$

(declare point-chart-example line-chart-example layered-chart-example)



;; -----------------------

;; Hanami chart code:
(comment
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
  )

;; TIP: The upper-case keywords (e.g. :X) are Hanami *values*,
;; whereas lowercase keywords (e.g. :zero, :filter) are Vega-lite *keys*.
