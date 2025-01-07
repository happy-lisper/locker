(ns locker)

(def ^:dynamic *skip-factor* 0)
(let [lock (Object.)]
  (defn sync-println [& args]
    (when (zero? (rand-int *skip-factor*)) (locking lock (apply println args)))))

(require
       '[clojure.core.async :as a :refer [<!! >!! <! >! chan go go-loop timeout]]
       )

(defn random-wait[] (timeout (+ 1000 (rand-int 10000))))

(defn make-locker[lb-name]
       (let [c (chan)]
              (go-loop []
                     (let [[rc order-number order-created-ts order-shipped-ts] (<! c)
                            delta (- (System/currentTimeMillis) order-shipped-ts)]
                            (sync-println lb-name order-number (- (System/currentTimeMillis) order-created-ts) "Order Delivered" 
                                   (if (> delta 10) (str "IS DELAYED by " delta ) ""))
                            (<! (random-wait)) ;; simulate waiting for a pick up
                            (>! rc "done"))
                     (recur))
              c))

(defn make-locker-box[lb-name size]
       (let [ c (chan 10000)
              lockers (vec (take size (repeatedly (partial make-locker lb-name))))
              locker-index (atom 0)]
              (go-loop []
                     (let [[rc order-number] (<! c)]
                            (>! rc (lockers @locker-index))
                             (swap! locker-index #(mod (inc %) size)))
                     (recur))
              [lb-name c]))

(defn make-order[[lb-name lb]]
       (let [ c (chan)
              order-created-ts (System/currentTimeMillis)
              order-number (random-uuid)]
              (go
                     (sync-println lb-name order-number order-created-ts "Order Created")
                     (<! (random-wait))
                     (sync-println lb-name order-number (- (System/currentTimeMillis) order-created-ts) "Order Shipped")
                     (<! (random-wait))
                     (>! lb [c order-number])
                     (let [locker (<! c)]
                            (>! locker [c order-number order-created-ts (System/currentTimeMillis)])
                            (sync-println lb-name order-number (- (System/currentTimeMillis) order-created-ts) "Order Picked Up" (<! c)))
                     )))

