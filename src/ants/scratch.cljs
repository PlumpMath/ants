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

; Chocolatier Reference

; Scenes:

{:default
 [:input :user-input :ai :broad-collision :narrow-collision :movement :tiles :animate :audio :render :events]}

; Systems:

{:movement #object[Function "function (state){
var entities = chocolatier.engine.ces.entities_with_component.call(null,state,component_id);
var component_fn = chocolatier.engine.ces.get_component_fn.call(null,state,component_id);
return f.call(null,state,component_fn,entities);
}"],
 :broad-collision #object[Function "function (state){
return f.call(null,state);
}"],
 :collision-debug #object[Function "function (state){
var entities = chocolatier.engine.ces.entities_with_component.call(null,state,component_id);
var component_fn = chocolatier.engine.ces.get_component_fn.call(null,state,component_id);
return f.call(null,state,component_fn,entities);
}"],
 :events #object[Function "function (state){
return f.call(null,state);
}"],
 :ai #object[Function "function (state){
var entities = chocolatier.engine.ces.entities_with_component.call(null,state,component_id);
var component_fn = chocolatier.engine.ces.get_component_fn.call(null,state,component_id);
return f.call(null,state,component_fn,entities);
}"],
 :user-input #object[Function "function (state){
var entities = chocolatier.engine.ces.entities_with_component.call(null,state,component_id);
var component_fn = chocolatier.engine.ces.get_component_fn.call(null,state,component_id);
return f.call(null,state,component_fn,entities);
}"],
 :animate #object[Function "function (state){
var entities = chocolatier.engine.ces.entities_with_component.call(null,state,component_id);
var component_fn = chocolatier.engine.ces.get_component_fn.call(null,state,component_id);
return f.call(null,state,component_fn,entities);
}"],
 :narrow-collision #object[Function "function (state){
return f.call(null,state);
}"],
 :render #object[Function "function (state){
return f.call(null,state);
}"],
 :tiles #object[Function "function (state){
return f.call(null,state);
}"],
 :audio #object[Function "function (state){
return f.call(null,state);
}"],
 :input #object[Function "function (state){
return f.call(null,state);}"]}

; Components:

{:moveable #object
               [Function "function (state,entity_id,sys_opts)
               {switch(arguments.length)
               {case 2:
                return G__17054__2.call(this,state,entity_id);
                case 3:
                return G__17054__3.call(this,state,entity_id,sys_opts);}
                throw(new Error('Invalid arity: ' + arguments.length));}"],
 :ai #object
               [Function "function (state,entity_id,sys_opts)
               {switch(arguments.length)
               {case 2:
               return G__17054__2.call(this,state,entity_id);
               case 3:
               return G__17054__3.call(this,state,entity_id,sys_opts);}
               throw(new Error('Invalid arity: ' + arguments.length));}"],
 :controllable #object
               [Function "function (state,entity_id,sys_opts)
               {switch(arguments.length)
               {case 2:
               return G__17054__2.call(this,state,entity_id);
               case 3:
               return G__17054__3.call(this,state,entity_id,sys_opts);}
               throw(new Error('Invalid arity: ' + arguments.length));}"],
 :animateable #object
               [Function "function (state,entity_id,sys_opts)
               {switch(arguments.length)
               {case 2:
               return G__17054__2.call(this,state,entity_id);
               case 3:
               return G__17054__3.call(this,state,entity_id,sys_opts);}
               throw(new Error('Invalid arity: ' + arguments.length));}"],
 :collision-debuggable #object
               [Function "function (state,entity_id,sys_opts)
               {switch(arguments.length)
               {case 2:
               return G__17054__2.call(this,state,entity_id);
               case 3:
               return G__17054__3.call(this,state,entity_id,sys_opts);}
               throw(new Error('Invalid arity: ' + arguments.length));
}"]}

; Entities

{
 :G__4 (:ai :collision-debuggable :collidable :animateable :moveable),
 :G__3 (:ai :collision-debuggable :collidable :animateable :moveable),
 :player1 (:moveable :collision-debuggable :collidable :controllable :animateable),
 :G__1 (:ai :collision-debuggable :collidable :animateable :moveable),
 :G__2 (:ai :collision-debuggable :collidable :animateable :moveable)
 }

