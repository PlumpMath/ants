(ns ^:figwheel-always ants.core
  (:require [ants.pixi :as p]))

;; Dev setup
;; -- Figwheel server
;; -- No routing
;; -- Call pixi directly from clojurescript (i.e. no cljs wrapper)
;; -- Minimal javascript in index.html

;; Todos
;; * Prevent figwheel from reloading the stage
;; -- DONE: put stage setup code in separate namespace and use ^:figwheel-no-load?
;; -- DONE: or use simple if to check if stage is not already added
;;
;; * Separate game logic from rendering logic
;; -- DONE: Put rendering logic in it's own file.
;;
;; * Setup basic game engine
;; -- Create atom with game-state, entities and world
;; ---- game-state = all the meta data about the game
;; ---- entities = data about each entity in the game (advanced: only update entities that are visible on the stage?)
;; ---- world-state = backgrounds, effects etc (maybe they will be entities?)
;; -- For each "tick" update world-state (should we have game-state vs world-state?)
;; -- Call renderer and animate the world
;;

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce world-state (atom {}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

(def bunnytexture (p/create-texture "images/bunny.png"))

;; (def bunny (js/PIXI.Sprite. bunnytexture))

(def bunny (p/create-sprite bunnytexture))

(set! (.-position.x bunny) 200)
(set! (.-position.y bunny) 150)

(. p/stage addChild bunny)

(defn animate
  []
  (do
    (. p/renderer render p/stage)
    (js/requestAnimationFrame animate)
    (set! (.-rotation bunny) (+ 0.02 (.-rotation bunny)))
    ))

(animate)
