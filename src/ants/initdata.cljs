(ns ants.initdata
  ;(:require [ants.core :as ants])
  )
;; ^:figwheel-no-load prevents figwheel from re-appending the stage to the dom each time ants.core is compiled

(def initdata-renderer
  {:stage-background-color 0x1099bb
   :stage-size-x 500
   :stage-size-y 300})

(def initdata-game
 ;; Game-related data. Maybe this should be conflated with initdata-world
 {})

(def initdata-world
  ;; World backgrounds i.e. tiles and other hidden elements etc.
  {})

(def initdata-entities
 [{:texture "images/ant-sm.png"
   :start-pos-x 200
   :start-pos-y 150}
  {:texture "images/ant-sm.png"
   :start-pos-x 150
   :start-pos-y 250}
  {:texture "images/ant-sm.png"
   :start-pos-x 215
   :start-pos-y 56}
  ])



;(def initdata-entities
;  {
;   :bunny1
;   {:texture "images/bunny.png"
;   :start-position-x 200
;   :start-pos-y 150}
;   :bunny2
;   {:texture "images/bunny.png"
;    :start-position-x 250
;    :start-pos-y 50}
;   :bunny3
;   {:texture "images/bunny.png"
;    :start-position-x 10
;    :start-pos-y 50}
;   })