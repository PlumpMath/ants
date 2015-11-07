# Dev setup
* Figwheel server
* No routing
* Call pixi directly from clojurescript (i.e. no cljs wrapper)
* Minimal javascript in index.html

# TODOS :

## Setup the game "world" from game-state atoms
* Load the initial data from the init-data namespace


## Make code reloadable
* Use defonce for state atoms or they will be reset every time the code loads
* To update entities: 
** (process state) == new game state.
** (reset! game-state old-state new-state)
** (render app-state)

## Setup basic game engine
* Create atom with game-state, entities and world
* game-state = all the meta data about the game
  * entities = data about each entity in the game (advanced: only update entities that are visible on the stage?)
    * world-state = backgrounds, effects etc (maybe they will be entities?)
  *  For each "tick" update world-state (should we have game-state vs world-state?)
  *  Call renderer and animate the world

## DONE: Prevent figwheel from reloading the stage
* DONE: put stage setup code in separate namespace and use ^:figwheel-no-load?
* DONE: or use simple if to check if stage is not already added

## DONE: Separate game logic from rendering logic
* DONE: Put rendering logic in it's own file.

## DONE: Why isn't the view rendering properly?
* saving ants.core appends another view to the dom "correctly"
* lookup "pixi figwheel"

# Notes:

## Writing reloadable code for Figwheel:
https://github.com/bhauman/lein-figwheel#writing-reloadable-code

-- Use "defonce" for atoms vs. (def state (atom {}))

## Figure out how to get figwheel to call animate on reload.
* How does requestAnimationFrame work?
* How does figwheel update the js files?
