(ns pio.server
 (:require [pio.app-handler :refer [app-handler]]
           [ring.adapter.jetty :refer [run-jetty]]))



(defn -main  []
    (run-jetty app-handler  {:port 12345 :join? false}))
