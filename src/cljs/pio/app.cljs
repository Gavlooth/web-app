(ns pio.app
    (:require [rum.core :as rum]
              [goog.dom :as dom]))

(def get-element dom/getElement)
(def img "https://hunyadi.info.hu/levente/images/stories/sigplus/birds1/IMG_0759.jpg" )

(enable-console-print!)


(rum/defc upload-component []
  [:div
   [:div
    [:label {:for "upload-file"}
     [:img {:height "300" :width "300" :src img}] ]
    [:input {:type "file"
             :style {:display "none"}
             :name "upload-file"
             :id "upload-file"}]]])

(defn initialize-app []
        (rum/mount (upload-component)
                   (get-element "app")))

(initialize-app)


