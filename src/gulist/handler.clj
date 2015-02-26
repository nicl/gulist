(ns gulist.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [gulist.store :as store]
            [ring.middleware.json :as json]))

(defroutes app-routes
  (GET "" [] (frontend/get-list))
  (GET "/list" [] (store/get-list))
  (GET "/list/:id" [id] (str "Return list for user with ID: " id))
  (GET "/user/:id/preferences" [id] (str "Return preferences for user with ID: " id))
  (PUT "/user/:id/preferences" [id] (str "Update preferences for user with ID: " id))
  (PUT "/list/:id/upvotes" [id] (store/upvote id))
  (DELETE "/user/:id" [id] (str "Delete user with ID: " id))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (json/wrap-json-body {:keywords? true})
      (json/wrap-json-response)))
