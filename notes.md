# ANTS TODOS:

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

# Figwheel Notes:

## Writing reloadable code for Figwheel:
https://github.com/bhauman/lein-figwheel#writing-reloadable-code

-- Use "defonce" for atoms vs. (def state (atom {}))

## Figure out how to get figwheel to call animate on reload.
* How does requestAnimationFrame work?
* How does figwheel update the js files?

# Dev System Setup

* Figwheel server
* No routing
* Call pixi directly from ClojureScript (i.e. no cljs wrapper)
* Minimal javascript in index.html

# Ants Requirements

## Scenes

* The only scene is the main ants screen.

## Systems

* Movement
* Collision
* AI System
* Rendering System
* Events System
* Input

### AI System

* Updates entities state based on AI rules

### Render System

* Updates the screen based on current game state.
* Should be called last?

### Event System

* Passes messages back and forth between components.

## Components

* :moveable - allows the entity to move to a new location in the world
* :collidable - allows the entity to collide with other entities.

## Entities

* Ants are the only entity in the system (for now)
* {:ant-ID [:movable :collidable]}

# Component Entity System Notes

From: Chocolatier CES namespace

Gameloop:   recursive function that calls all systems in a scene

Scenes:     collection of system functions called by the game loop

Systems:    functions that operates on a components and returns updated game state.

Entities:   unique IDs that have a list of components to participate in

Components: hold state and lists of functions relating to a certain aspect.

State:      stores state for components, entities, and systems

Using one state hashmap should be performant enough even though it
creates a new copy on every change to state in the game loop due to
structural sharing

### Scene

Scene: a collection of system labels in the order the system should be executed in.

Scene example:
{:title-screen [:main-menu :input]
:game [:input :movement :ai]}

### System

System: a fn that returns updated state. Works on a collection of entities

System example:
{:movement f}

### Component

Component: a fn that returns updated component state & collection of events for one entity
polymorphism is achieved via entity IDs using multimethods

Component example:
{:moveable f}

### Entities

Entity: a unique label (ID) & a collection of component labels (IDs)

Entity example:
{:player1 [:controllable :moveable :collidable]}

### Events

Events: Global, anything can emit an event. Events can be subscribed to.

# Core Idea:

Reduce the game state over a collection of functions to get the next frame

```
(reduce (fn [accum f] (f accum))
       state
       [f1 f2 f3 f4])
```

## Component based modelling:

HeatSeekingMissile : :movable :explosive :heatSeeking
SidewinderMissile : :movable :explosive :sidewinder

Game State is a data structure : {}

## Game Loop

The game loop recursively calls a function of state e.g. (f (f state))

The game function reduces over all system functions:
(reduce (fn [state f] (f state)) init-state [s1 s2 s3 s4])

A system function reduces over entities that participate in a component e.g. :
(reduce f state entity-ids)

A component function takes state and returns new state modified for that entity ID only e.g. :
(fn [state] ...)

## Other Parts of the CES

Principle of Least Privilege (PLP)
Only have access to what you need & only change what belongs to you
Make it easy to do the right thing

### Lenses (encourage PLP)

A way of isolating wider inputs to a function
Prevents access to things the functions doesn't care about
Eliminates the need for common call signatures
Implementation details hidden from the body-fn

Lense example:

```
(combine-fn (body-fn (args-fn input)))

(defn lense [args-f body-f combine-f]
  (fn [input]
    (combine-f input (body-f (args-f input)))))

(def component-fn
  (lense (fn [state] (get state :foo))
         (fn [component-state] (update component-state :x inc))
         (fn [state results] (assoc state :foo results))))
```

The body function is isolated from knowing anything about the input data

```
(component-fn {:renderer {} :stuff {} :foo {:x 1}})
>> {:renderer {} :stuff {} :foo {:x 2}}
```

(Component function is called with the entire state as input)

```
(defn component-f
; Default args, but can be customized when declaring the component
[entity-id component-state opts]
; Returns updated component state
(body ...))
```

Wrapped with mk-component-fn it returns a function that takes
game state and entity-id as arguments

```
((mk-component-fn :component-1 component-f) state :player1)
```

mk-component-fn is a lense
By default, the lense calls the component function with just the state it needs

To customize, provide an :args-fn and :format-fn to mk-component-fn options:

```
Need example!
```

### Polymorphism

Polymorphism = (Process entities differently based on their component type (trad. data type or class))


# Questions:

How to keep track of the connection between entities in the engine and pixi objects?
i.e. sprites have a texture and and id?
- Could save the reference to the sprite with the entity information.

How to implement different "layers" in the system.
- E.g. Should there be a "food" layer or should "food" exist on the "main" layer with the ants.


# Reference

## Chocolatier