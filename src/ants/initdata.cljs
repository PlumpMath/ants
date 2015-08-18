(ns ^:figwheel-no-load ants.initdata)
;; ^:figwheel-no-load prevents figwheel from re-appending the stage to the dom each time ants.core is compiled

(def initdata-game
  {:stage-background-color 0x1099bb
   :stage-size-x 800
   :stage-size-y 300})

(def initdata-world
  ;; World backgrounds i.e. tiles and other hidden elements etc.
  {})

(def initdata-entities
  {
   :bunny1
   {:texture "images/bunny.png"
   :start-position-x 200
   :start-pos-y 150}
   :bunny2
   {:texture "images/bunny.png"
    :start-position-x 250
    :start-pos-y 50}
   :bunny3
   {:texture "images/bunny.png"
    :start-position-x 10
    :start-pos-y 50}
   })