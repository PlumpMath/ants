(ns ^:figwheel-always ants.core
  (:require))

;; Dev
;; Figwheel server
;; No routing
;; Call pixi directly from clojurescript (i.e. no cljs wrapper)
;; Minimal javascript (if possible)
;;

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce world-state (atom {}))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

(def renderer (js/PIXI.autoDetectRenderer 400 300))

(.appendChild (.-body js/document) (.-view renderer))

(defonce stage (js/PIXI.Container.))

(def bunnytexture (js/PIXI.Texture.fromImage "images/bunny.png"))

(def bunny (js/PIXI.Sprite. bunnytexture))

(set! (.-position.x bunny) 200)
(set! (.-position.y bunny) 150)

(. stage addChild bunny)

(defn animate
  []
  (do
    (. renderer render stage)
    (js/requestAnimationFrame animate)
    (set! (.-rotation bunny) (+ 0.02 (.-rotation bunny)))
    ))

(animate)