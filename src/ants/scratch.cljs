(ns ants.scratch)

;
;(def bunnytexture (p/create-texture "images/bunny.png"))
;(def bunny (p/create-sprite bunnytexture))
;(set! (.-position.x bunny) 200)
;(set! (.-position.y bunny) 150)
;(. stage addChild bunny)
;
;(defn create-random-bunny
;  "creates a bunny with a random x and y position"
;  []
;  (let [bunny (p/create-sprite (p/create-texture "images/bunny.png"))]
;    (set! (.-position.x bunny) (rand-int (:stage-size-x @game-state)))
;    (set! (.-position.y bunny) (rand-int (:stage-size-y @game-state)))
;    bunny))
;
;(defn add-bunny-to-stage
;  "adds a bunny to the stage"
;  []
;  (. stage addChild (create-random-bunny)))
