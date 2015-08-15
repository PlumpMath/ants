(ns ^:figwheel-no-load ants.pixi)
;; ^:figwheel-no-load prevents figwheel from re-appending the stage to the dom each time ants.core is compiled
;;

(def stage (js/PIXI.Container.))

(def renderer (js/PIXI.autoDetectRenderer 500 400))

(defn create-texture
  [image]
  (js/PIXI.Texture.fromImage image))

(defn create-sprite
  [texture]
  (js/PIXI.Sprite. texture))

(defn reset-stage
  []
  (. stage removeChildren))

(.appendChild (.-body js/document) (.-view renderer))

