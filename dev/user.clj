(ns user
  (:require [clojure.tools.namespace.repl
             :refer  [refresh disable-reload! ]]
            [figwheel-sidecar.repl-api :as f]
           [pio.state :refer [server&]]
            [pio.server :refer [-main]]))




(def go
(do
     (disable-reload! (create-ns 'pio.state))
  (fn [x]
      (when ( = x :stop )
       (when @server&
        (.stop @server&)))
      (when (= x :reset)
        (when @server&
          (.stop @server&)
          (reset! server& nil))
        (refresh)
        (reset! server& (#'-main)))
      (when (= x :init)
        (reset! server& (#'-main))))))


(defn fig-start
  "This starts the figwheel server and watch based auto-compiler."
  []
  (f/start-figwheel!))

(defn fig-stop
  "Stop the figwheel server and watch based auto-compiler."
  []
  (f/stop-figwheel!))

(defn cljs-repl
  "Launch a ClojureScript REPL that is connected to your build and host environment."
  []
  (f/cljs-repl))
