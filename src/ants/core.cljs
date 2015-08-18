(ns ^:figwheel-always ants.core
  (:require-macros
    [ants.macros :refer [inspect breakpoint << >> local]]
    )
  (:require [ants.pixi :as p]
            [ants.initdata :as data]
            [ants.utils :as u]
            [dommy.core :as dommy :refer-macros [sel sel1]]))

(enable-console-print!)

(defonce game-state (atom {}))
(defonce world-state (atom {}))
(defonce entity-state (atom {}))

(defn load-initial-game-data!
  []
  ;; Load the data into each of the corresponding data structures
  (let [game-data data/initdata-game
        world-data data/initdata-world
        entity-data data/initdata-entities]
    (do
      (reset! game-state game-data)
      (reset! world-state world-data)
      (reset! entity-state entity-data)
      (inspect world-state)
      (inspect game-state)
      (inspect entity-state))))

(load-initial-game-data!)

;; Create an empty renderer
(def renderer
  (p/create-renderer
    (:stage-size-x @game-state)
    (:stage-size-y @game-state)
    (:stage-background-color @game-state)))

(defn attach-view
  []
  (do
    (dommy/append! (sel1 :#app) (.-view renderer))
    (dommy/set-attr! (sel1 [:#app :canvas]) :id "pixi")))

;; Create the container element
(def stage
  (p/create-stage))

(defn setup-world
  "Takes the data in the game-state map and sets up the corresponding elements"
  []
  (u/log "Setup world called"))

(defn setup-entities
  "Takes the data in the entity-state map and sets up the corresponding elements"
  []
  (u/log "Setup entitles called"))

(def bunnytexture (p/create-texture "images/bunny.png"))
(def bunny (p/create-sprite bunnytexture))
(set! (.-position.x bunny) 200)
(set! (.-position.y bunny) 150)
(. stage addChild bunny)

(def foo5 nil)

(defn create-random-bunny
  "creates a bunny with a random x and y position"
  []
  (let [bunny (p/create-sprite (p/create-texture "images/bunny.png"))]
    (set! (.-position.x bunny) (rand-int (:stage-size-x @game-state)))
    (set! (.-position.y bunny) (rand-int (:stage-size-y @game-state)))
    bunny))

(defn setup-all
  "Loads the initial game data and sets up game, world and entities"
  []
  (do
    (setup-world)
    (setup-entities)))

(defn animate
  []
  (do
    (. renderer render stage)
    (js/requestAnimationFrame animate)
    (set! (.-rotation bunny) (+ 0.02 (.-rotation bunny)))
    ))

(defn main
  []
  (do
    (if-not (sel1 :#pixi)
      (attach-view))
    (animate)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  (animate)
  )

;; ;; Start the game on page load
(set! (.-onload js/window) main)
