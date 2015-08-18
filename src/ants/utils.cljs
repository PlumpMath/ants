(ns ^:figwheel-no-load ants.utils)
;; ^:figwheel-no-load prevents figwheel from re-appending the stage to the dom each time ants.core is compiled

(defn log
  "Simple helper to log to Clojurescipt dev console"
  [data]
  (js/console.log (pr-str data)))

;; http://stackoverflow.com/questions/24635974/clojurescript-format-string-with-goog-string-format-doesnt-substitute
(defn hex-color [& args]
  (apply str "#" (map #(.toString % 16) args)))
