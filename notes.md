;; Dev setup
;; -- Figwheel server
;; -- No routing
;; -- Call pixi directly from clojurescript (i.e. no cljs wrapper)
;; -- Minimal javascript in index.html

;; TODOS :

;; * DONE: Figure out why the view isn't rendering properly
;; -- saving ants.core appends another view to the dom "correctly"
;; -- lookup "pixi figwheel"
;;

* Figure out how to make code reloadable (should solve the reloading problems).
--
--
--

;; * Figure out how to get figwheel to call animate on reload.
;; -- How does requestAnimationFrame work?
;; -- How does figwheel update the js files?
;;
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
;; * Setup the game "world"
;; -- Load the initial data from the init-data namespace
;; --

Writing reloadable code for Figwheel:
https://github.com/bhauman/lein-figwheel#writing-reloadable-code

-- Use "defonce" for atoms vs. (def state (atom {}))

