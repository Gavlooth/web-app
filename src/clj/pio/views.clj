(ns pio.views
  (:require [rum.core :as rum ]
            [clojure.java.io :as io]
            [ring.middleware.anti-forgery :refer  [*anti-forgery-token*]]))



;(javascript-tag  (str  "var csrfToken = "  \" anti-forgery \" \; ))

(def head
  [:head
   [:script  {:type  "text/javascript"}
        (str  "//<![CDATA[\n"  "var csrf-token = "\"  *anti-forgery-token* \" "\n//]]>")]
   [:meta  {:charset  "UTF-8"}]
   [:meta  {:name  "viewport"
            :content  "width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1"}]
   [:title  "Piu"]
   [:link  {:href  "https://fonts.googleapis.com/css?family=Open+Sans|Quicksand:300,400" :rel  "stylesheet"}]
   [:link  {:href "/css/style.css"  :rel  "stylesheet"}] ])


(defn index [request]
  (rum/render-static-markup
  [:html
    {:lang  "en"}
    head
    [:body

     [:div#app]
    [:script {:type "text/javascript" :src "/js/compiled/pio.js"}]

    ]]))


