(ns user
  (:require [clojure.tools.namespace.repl
             :refer  [refresh disable-reload! ]]
            [figwheel-sidecar.repl-api :as f]
            [integrant.core :as ig]
            [pio.state :refer [server&]]
            [pio.app-handler :refer [app-handler]]
            [ring.adapter.jetty :as jetty]))

(def config
    (ig/read-string  (slurp  "config.edn")))


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


#_(def go
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



(defmethod ig/init-key :server [_ options]
    (jetty/run-jetty app-handler options))

(defmethod ig/halt-key! :server [_ server]
    (.stop server))

(def system
    (ig/init config))


