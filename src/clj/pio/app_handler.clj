(ns pio.app-handler
  (:require [bidi.ring :refer [make-handler resources-maybe redirect]]
            [ring.middleware
             [file :refer [wrap-file]]
             [resource :refer [wrap-resource]]
             [content-type :refer [wrap-content-type ]]
             [params :refer [wrap-params]]
             [multipart-params :refer [wrap-multipart-params]]
             [defaults :refer  [api-defaults site-defaults wrap-defaults]]
             [format :refer  [wrap-restful-format]]]
            [ring.util.response :refer [response resource-response]]
            [pio.views :as views]))

(defn ok
  ([]  (ok nil))
  ([body]
   {:status 200
    :headers  {}
    :body body}))
;;



(defn default-page [_]
  (ok (views/index "test")))

(def main
  (wrap-content-type
    (wrap-defaults
      default-page site-defaults)))

(def my-routes

  ["/"  [
         ["index" default-page]
         [""  (redirect default-page)]
         ["" (resources-maybe {:prefix "public/"})]
         [true  (fn [_]  (ok "Not found"))]]])
(def app-handler (make-handler my-routes))

