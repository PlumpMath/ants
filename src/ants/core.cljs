(ns ants.core
  (:require-macros
    [ants.macros :refer [inspect breakpoint << >> local]])
  (:require [ants.ces :as ces]
    [ants.pixi :as p]
            [ants.initdata :as data]
            [ants.utils :as u]
            [dommy.core :as dommy :refer-macros [sel sel1]]))

;; Chocolatier source on github: https://github.com/alexkehayias/chocolatier
;; Recent Functional Game Design:
;; http://htmlpreview.github.io/?https://raw.githubusercontent.com/alexkehayias/functional-game-engine-design/master/game_engine_design.html#/sec-title-slide
;; CES Presentation:
;; http://www.slideshare.net/alexkehayias/clojure-script-game-engine-overview


(enable-console-print!)

;; to get something working we're going to use the simplest state possible, pre-initialized with the world:
(defonce state {:scenes [{:id :scene01
                          :game [:movement :collision :rendering]}]
                :systems [{:movement movement-sys-fn
                           :collision collision-sys-fn
                           :rendering rendering-sys-fn}]
                :components [{:antcomp [:movable :collidable :renderable]}]
                :entities [{:ant01 [[:movable {:x 15 :y 25}]
                                    :collidable
                                    :renderable {:texture "images/ant-sm.png" }]}
                           {:ant02 [[:movable {:x 150 :y 250}]
                                    :collidable
                                    :renderable {:texture "images/ant-sm.png" }]}
                           {:ant03 [[:movable {:x 250 :y 300}]
                                    :collidable
                                    :renderable {:texture "images/ant-sm.png" }]}]
                })

(defonce game-state (atom {}))
(defonce world-state (atom {}))
(defonce entity-state (atom {}))

;(data/load-initial-game-data!)

(defn load-initial-game-data!
  []
  ;; Load the data into each of the corresponding data structures
  (let [game-data initdata-renderer
        world-data initdata-world
        entity-data initdata-entities]
    (do
      (reset! game-state game-data)
      (reset! world-state world-data)
      (reset! entity-state entity-data)
      (inspect world-state)
      (inspect game-state)
      (inspect entity-state))))

;; Create an empty renderer
(def renderer
  (p/create-renderer
    (:stage-size-x @game-state)
    (:stage-size-y @game-state)
    (:stage-background-color @game-state)))

(defn attach-view []
  "Attaches the renderer to the DOM and sets its :id to pixi"
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

(defn setup-entity
  "From the info in the entity map:
  * Creates the texture
  * Creates the sprite
  * Sets the inital position
  * Adds the sprite to the stage"
  [entity]
  (let [texture (p/create-texture (:texture entity))
        sprite (p/create-sprite texture)
        xpos (:start-pos-x entity)
        ypos (:start-pos-y entity)]
    (set! (.-position.x sprite) xpos)
    (set! (.-position.y sprite) ypos)
    (. stage addChild sprite)
    (. renderer render stage)
    ))

(defn setup-entities
  "Calls setup-entity for all entities in the entities atom"
  []
  (u/log "setup-entities called")
  ; The dorun and doseq versions are the same but doseq is more idiomatic.
  ;(dorun
  ;  (map #(setup-entity %) @entity-state))
  (doseq [e @entity-state]
    (setup-entity e))
  )

(defn setup-all
  "Loads the initial game data and sets up game, world and entities"
  []
  (do
    (setup-world)
    (setup-entities)))


(defn animate
  "Main game loop. Performs the following:
  * Render the game stage
  * Takes input (TBD)
  * Moves the player (TBD)
  * Performs collsion checks (TBD)
  * Updates entities (TBD)
  * Requests animation frame
  * Repeats"
  []
  ; Render the game stage
  (. renderer render stage)
  ; Requests animation frame and calls itself
  (js/requestAnimationFrame animate)
  )

(defn init
  []
  (if-not (sel1 :#pixi)
    (attach-view))
  (setup-all)
  (animate)
  )

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  (animate)
  )

;; ;; Start the game on page load
(set! (.-onload js/window) init)