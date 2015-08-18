(ns ants.pixi
  (:require-macros
    [ants.macros :refer [inspect breakpoint]])
  )
;; ^:figwheel-no-load prevents figwheel from re-appending the stage to the dom each time ants.core is compiled
;; The Pixi library is loaded in the index.html file and it's being called directly via JavaScript interop

(defn create-renderer
  "Creates the Pixi renderer object with x and y size"
  [x-size y-size bg-color]
  (js/PIXI.autoDetectRenderer x-size y-size (clj->js {:backgroundColor bg-color})))

(defn create-stage
  "Creates the top-level Pixi container that we can render to"
  []
  (js/PIXI.Container.))

(defn create-texture
  [image]
  (js/PIXI.Texture.fromImage image))

(defn create-sprite
  [texture]
  (js/PIXI.Sprite. texture))

(defn reset-stage
  []
  (. ants.core/stage removeChildren))

