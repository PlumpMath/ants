(ns ants.ces)

(defn get-system-fns
  "Return system functions with an id that matches system-ids in order.
   If a key is not found it will not be returned."
  [state system-ids]
  (let [systems (:systems state)]
    (mapv systems system-ids)))

