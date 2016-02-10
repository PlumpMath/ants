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

(declare render-entity)

(defonce test-state {:e1 [
                          {:component :position :x 10 :y 10 :a 0}
                          {:component :renderable :fn render-entity}
                          {:component :dimensions :x 20 :y 27}
                          ]
                     :e2 [
                          {:component :position :x 50 :y 50 :a 0}
                          {:component :renderable :fn render-entity}
                          {:component :dimensions :x 20 :y 27}
                          ]
                     })

(def atomstate (atom
                      {:e1 [
                          {:component :position :x 10 :y 10 :a 0}
                          {:component :dimensions :x 20 :y 27}
                          ]
                     :e2 [
                          {:component :position :x 50 :y 50 :a 0}
                          {:component :dimensions :x 20 :y 27}
                          ]
                       }))

(def t {:e0 [{:c :blah :x 1 :y 2}
            {:c :foo :x 2 :y 5}]
        :e1 [{:c :blah :x 1 :z 2}
             {:c :foo :x 5 :z 12}]})

(def ta (atom
         {:ea [{:name :position :x 1 :y 2}
            {:name :renderable }]
        :eb [{:name :blah :x 1 :z 2}
             {:name :foo :x 5 :z 12}]}))

(def altatom (atom
               {:ea {:position {:x 50 :y 75 :a 0}
                     :movement {:direction 45 :speed 10}}}
               {:eb {:position {:x 80 :y 100 :a 0}
                     :movement {:direction 90 :speed 50}}}))

(defn get-id []
  (swap! ids inc))

(defn positions [pred coll]
  "returns the index of the first item in coll that satisfies pred"
  (keep-indexed (fn [idx x]
                  (when (pred x)
                    idx))
                coll))

(defn drop-nth [n coll]
  "returns coll with the nth element removed"
  (->> coll
       (map vector (iterate inc 1))
       (remove #(zero? (mod (first %) n)))
       (map second)))

; from Mhubert on slack
;
;(defn contains-component [target entity-name entity-vals] ... )
;
;(filter #(contains-component? :target %1 %2) entity-map)
;
; would you not pass in just the val to your contains? function? eg.
; (when (contains-component? (:e2 entity-map)) …)

(defn seq-contains? [coll target]
  "Returns true if coll contains target, nil otherwise"
  (some #(= target %) coll))

(defn contains-component? [c m]
  "given an entity with an entity name and vector of components returns true if entity contains component"
  (let [entity-name (first (keys m))
        entity-components (first (vals m))
        target c
        __ (u/log {:entity-name entity-name :entity-components entity-components :target-component target})]
    (if
      (seq-contains? entity-components target)
      true
      false)))

(defn all-entities [c s]
  "Given a component c return all entities that participate in that component from game state
  NOT WORKING"
  (let [component c
        state (deref s)
        _ (u/log {:component component :state state})]
    (filter #(contains-component? component %) state )))

(defn create-entity! [state]
  "adds entity with given name to state atom with default position component"
  ;(swap! state update-in [(gensym name)] (fnil conj []) {:c :position :x 50 :y 50})
  (let [m (vector {:c :init})
        id (keyword (str "e" (get-id)))]
    (swap! state assoc id m)))

(defn remove-entity! [state name]
  "removes entity with given name from state atom"
  (u/log "remove entity called - does nothing"))

(defn add-component! [state e m]
  "add given component c to entity e in the game state"
  ;; (swap! ta update-in [:e] conj {:c :bing :x 1 :y 5})
  (let [entity e
        component m
        ;; ov (entity (deref state))
        ;; nv (conj ov c)
        _ (u/log {:entity entity :component component})]
    (swap! state update-in (vector entity) conj component)))

(defn remove-component! [state e c]
  "remove given component c from entity e in the game state"
  (let [entity e
        component c
        idx (inc (first (positions #(= component (:component %1)) (entity @state))))
        ov (entity (deref state))
        nv (vec (drop-nth idx ov))
        __ (u/log {:idx idx :ov ov :nv nv})]
    (-> state
        (swap! assoc entity nv ))))

;(def ast atomstate)

;(create-entity! ast :e)

;(swap! ast assoc-in [:e7 0] {:component :foo :a 1 :b 2} )

;; Not used - just here so figwheel compiles
(defonce game-state {})
(defonce entity-state {})

(defn position [x y angle]
           :x x
           :y y
           :angle (or angle 0))

(defn renderable [func]
           :fn func)

(defn dimensions [x y]
           ; I'm assuming this should correspond to the dimensions of the image
           ; which represents the entity.
           :x x
           :y y)

(defn render-background []
  (u/log "setup-entities called")
  )

(defn render-entity []
  (u/log "setup-entities called")
  )

(defn level1 []
  ; define the entities in the game
  [:background [(renderable render-background)
                (dimensions 300 200)]
   :ant1 [(position 10 10 0)
          (renderable render-entity)
          (dimensions 20 27)]
   ])

(defn create-renderer
  "Creates the Pixi renderer object with x and y size"
  [x-size y-size bg-color]
  (js/PIXI.autoDetectRenderer x-size y-size (clj->js {:backgroundColor bg-color})))

(defn create-stage
  "Creates the top-level Pixi container that we can render to"
  []
  (js/PIXI.Container.))

;; Create the container element
(def stage
  (create-stage))

(defn create-texture
  [image]
  (js/PIXI.Texture.fromImage image))

(defn create-sprite
  [texture]
  (js/PIXI.Sprite. texture))

(defn reset-stage []
  "Removes all entities from the stage"
  (. stage removeChildren))

(def renderer
  (create-renderer 500 300 0x1099bb))



(defn attach-view []
  "Attaches the renderer to the DOM and sets its :id to pixi"
  (do
    (dommy/append! (sel1 :#app) (.-view renderer))
    (dommy/set-attr! (sel1 [:#app :canvas]) :id "pixi")))

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
  (let [texture (create-texture (:texture entity))
        sprite (create-sprite texture)
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


; Here for reference
;(defonce test-state {:e1 [
;                          {:component :position :x 10 :y 10 :a 0}
;                          {:component :renderable :fn render-entity}
;                          {:component :dimensions :x 20 :y 27}
;                          ]
;                     :e2 [
;                          {:component :position :x 50 :y 50 :a 0}
;                          {:component :renderable :fn render-entity}
;                          {:component :dimensions :x 20 :y 27}
;                          ]
;                     })

;(defn create-entity [state name]
;  "adds a unique entity with given name as prefix to the game state "
;  (-> state
;      (assoc (gensym name) (vector))))

;(defn remove-entity! [state n]
;  "removes entity with name from state"
;  (-> state
;      (dissoc n)))

;(def atom-state (atom
;                      {:e1 [
;                            {:component :position :x 10 :y 10 :a 0}
;                            {:component :renderable :fn true}
;                            {:component :dimensions :x 20 :y 27}
;                            ]
;                       :e2 [
;                            {:component :position :x 50 :y 50 :a 0}
;                            {:component :rednerable :fn true}
;                            {:component :dimensions :x 20 :y 27}
;                            ]
;                       }))


;(defn load-scene [scene]
;  (doseq [[name comps] (partition-all 2 scene)]
;    (let [ent (create-entity! name)]
;      (doseq [c comps]
;        (add-component ent c)))))

(defn game-loop
  "Main game loop. Performs the following:
  * Render the game stage
  * Takes input (TBD)
  * Moves the player and other entities
  * Performs collsion checks (TBD)
  * Updates entities
  * Requests animation frame (renders)
  * Repeats"
  []
  ; Render the game stage
  (. renderer render stage)
  ; Requests animation frame and calls itself
  (js/requestAnimationFrame game-loop)
  )

(defn init
  []
  (if-not (sel1 :#pixi)
    (attach-view))
  ;(load-scene level1)
  (game-loop)
  )

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  (game-loop)
  )

;; ;; Start the game on page load
(set! (.-onload js/window) init)