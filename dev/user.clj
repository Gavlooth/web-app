(ns user
  (:require [clojure.tools.namespace.repl
             :refer  [refresh disable-reload! ]]
            [pio.server :refer [-main]]
            [figwheel-sidecar.repl-api :as f]
            [mount.core :refer  [defstate] :as mount]
            [pio.app-handler :refer [app-handler]]
            [ring.adapter.jetty :as jetty]))


(defstate server :start  (#-main)
                 :stop   (do
                         (.stop server)
                         (refresh)))
