(ns pio.views
  (:require [rum.core :as rum ]
            [hiccup
             [element :refer [javascript-tag]]
             [form :refer [hidden-field]]
             [page :refer [html5 include-css include-js]]]
            [clojure.java.io :as io]
            [ring.middleware.anti-forgery :refer  [*anti-forgery-token*]]))






(defn head [& {:keys [js css anti-forgery]}]
  [:head
   [:meta {:charset "UTF-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1"}]
   [:title "Piu"]
   [:link {:href "https://fonts.googleapis.com/css?family=Open+Sans|Quicksand:300,400" :rel "stylesheet"}]
   (when css
     (apply include-css css))
   (when anti-forgery
     (javascript-tag (str "var csrf_token = \"" anti-forgery \" \;)))
   (when js
     (apply include-js js))])



(defn index [request]
  (html5
    {:lang "en"}
    (head  :css ["css/style.css" "css/bulma.css"] :anti-forgery *anti-forgery-token*)
    [:body
     [:div#app
    "parta ola" ]
    (include-js "js/compiled/pio.js") ]))
