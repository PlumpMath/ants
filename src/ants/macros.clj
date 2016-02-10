(ns ants.macros)
;; Does it need ^:figwheel-no-load ?
;; taken from https://github.com/shaunlebron/How-To-Debug-CLJS/blob/master/src/example/macros.clj
;;

; Case 1: Show the state of a bunch of variables.
;
;   > (inspect a b c)
;
;   a => 1
;   b => :foo-bar
;   c => ["hi" "world"]
;
; Case 2: Print an expression and its result.
;
;   > (inspect (+ 1 2 3))
;
;   (+ 1 2 3) => 6
;

(defn- inspect-1 [expr]
  `(let [result# ~expr]
     (js/console.info (str (pr-str '~expr) " => " (pr-str result#)))
     result#))

(defmacro inspect [& exprs]
  `(do ~@(map inspect-1 exprs)))

; -------------------------------------------------------------------
; BREAKPOINT macro
; (use to stop the program at a certain point,
; then resume with the browser's debugger controls)
; NOTE: only works when browser debugger tab is open

(defmacro breakpoint []
  '(do (js* "debugger;")
       nil)) ; (prevent "return debugger;" in compiled javascript)


(defmacro forloop [[init test step] & body]
  "For loop implementation. Example:
   (forloop [[i 0] (< i 16) (inc i)] (println i))"
  `(loop [~@init]
     (when ~test
       ~@body
       (recur ~step))))

;; LOCALS

;; Optimized locals that can be used as a mutable piece of state
;; inside local scope
(defmacro local
  ([]
   `(make-array 1))
  ([x]
   `(cljs.core/array ~x)))

(defmacro >> [x v]
  `(aset ~x 0 ~v))

(defmacro << [x]
  `(aget ~x 0))

(defmacro forloop-accum
  "For loop with accumulation into a transient vector"
  [[init test step] & body]
  `(loop [~@init
          res# (transient [])]
     (if ~test
       (do (conj! res# ~@body)
           (recur ~step res#))
       (persistent! res#))))

(defmacro component [name params & r]
  `(defn ~name ~params
     (cljs.core/js-obj "name" ~(keyword (clojure.core/name name)) ~@r)))
