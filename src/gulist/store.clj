(ns gulist.store
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]
            [ring.util.response :refer :all]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clojure.math.numeric-tower :refer [expt]]))

(def content-api-url (env :content-api-url))
(def content-api-key (env :content-api-key))

(def votes (atom {})) ;; in-memory for now

(defn- with-vote [vs item]
  (let [id (:id item)
        v (inc (get vs id 0))]
    (assoc item :votes v)))

(defn- with-votes [votes list]
  (map (partial with-vote votes) list))

(defn- get-content []
  (let [url (str content-api-url "/search")
        params {"api-key" content-api-key
                "page-size" "20"
                "show-tags" "all"
                "show-fields" ["byline" "internalComposerCode"]}]
    (->> (client/get url {:as :json :query-params params})
         :body
         :response
         :results
         (with-votes @votes))))

(defn- gu-filter [list] list)

(defn- temperature
  "votes / (age in hours + 1)^gravity"
  ([item]
     (temperature item (:votes item)))
  ([item votes]
     (let [now (t/now)
           published-at (f/parse (f/formatters :date-time-no-ms)
                                 (:webPublicationDate item))
           age (/ (t/in-seconds (t/interval published-at now)) (* 60 60))
           gravity 1.8]
       (/ votes (expt (+ age 1) gravity)))))

;; (defn- boost
;;   [boosters list]
;;   ;; a boost-factor is a fn: item -> score
;;   list)

(defn- gu-sort
  "The magic sauce - sort by freshness, but certain boost and lift factors too"
  [list]
  (let [boosted (reverse (sort-by temperature list))]
    boosted))

(defn- gu-format [list] (map (juxt :id :votes :webPublicationDate) list))

(defn- gu-response [m] (response m))

(defn- gu-list-response [list] (gu-response {:list list}))



(defn get-list []
  (-> (get-content) gu-filter gu-sort gu-format gu-list-response))

(defn upvote [id]
  (letfn [(add-vote [vs id]
            (assoc vs id (inc (get vs id 0))))]
    (gu-response {:votes (get (swap! votes add-vote id) id)})))
