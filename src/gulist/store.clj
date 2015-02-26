(ns gulist.store
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]
            [ring.util.response :refer :all]))

(def content-api-url (env :content-api-url))
(def content-api-key (env :content-api-key))

(defn- get-content []
  (let [url (str content-api-url "/search")
        params {"api-key" content-api-key
                "page-size" "20"
                "show-tags" "all"
                "show-fields" "byline"}]
    (-> (client/get url {:as :json :query-params params})
        :body
        :response
        :results)))

(defn- filter [list] list)

(defn- format [list] (map :id list))

(defn- as-response [list] (response list))

(defn get-list []
  (-> (get-content) filter format as-response))
