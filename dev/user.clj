(ns user
  (:require [clojure.tools.namespace.repl
             :refer  [refresh disable-reload! ]]
            [figwheel-sidecar.repl-api :as f]
            [pio.state :refer [server&]]
            [pio.server :refer [-main]]
            [ring.adapter.jetty :as jetty]
            ))


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


(defmethod ig/init-key :adapter/jetty  [_ opts]
    (let  [handler  (atom  (delay  (:handler opts)))
                   options  (-> opts  (dissoc :handler)  (assoc :join? false))]
      {:handler handler
       :server  (jetty/run-jetty  (fn  [req]  (@@handler req)) options)}))

(defmethod ig/halt-key! :adapter/jetty  [_ {:keys  [server]}]
    (.stop server))

(defmethod ig/suspend-key! :adapter/jetty  [_ {:keys  [handler]}]
    (reset! handler  (promise)))

(defmethod ig/resume-key :adapter/jetty  [key opts old-opts old-impl]
    (if  (= (dissoc opts :handler)  (dissoc old-opts :handler))
          (do  (deliver @(:handler old-impl)  (:handler opts))
                      old-impl)
              (do  (ig/halt-key! key old-impl)
                          (ig/init-key key opts))))



