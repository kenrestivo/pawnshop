(ns pawnshop.core
  (:require [clj-http.client :as client]
            [clojure.walk :as walk])
  (:use cheshire.core))





(defn- get-response [url user password method params]
  (-> (client/post url
                   {:body (encode {:jsonrpc "1.0",
                                   :id :cljbitcoin,
                                   :method  method,
                                   :params params })
                    :accept :json
                    :basic-auth [(name user) (name password)]})
      :body
      (decode keyword)))


(defn bitcoin-proxy
  "Takes an url, username, and password, and generates a bitcoin proxy function.
   That function will take a method and however many params the method requires, if any.
   Returns the result portion of the server's response."
  [url user password]
  (fn [method & params]
    (let [resp (get-response url user password method params)]
      (if (-> resp :error nil?)
        (:result resp)
        (throw (Exception. (-> resp :error :message)))))))




 (defn longify-amounts
  "Required for conversion of JSON values to valid 64-bit longs which
   bitcoin amounts must be stored in.
   From the documentation https://en.bitcoin.it/wiki/Proper_Money_Handling_%28JSON-RPC%29
   (int64_t)(value * 1e8 + (value < 0.0 ? -.5 : .5))"
  [value]
  (-> value
      (* 1e8)
      (+ (if (< value 0.0) -0.5 0.5))
      long))


(defn floatify-amounts
  "Convert long bitcoin amounts back to a float"
  [value]
  (/ value 1e8))

(defn all-addresses
  "Takes proxy, returns all the addresses on all accounts"
  [btp]
  (mapcat  #(btp :getaddressesbyaccount (key %))
           (-> (btp :listaccounts) clojure.walk/stringify-keys)))


(defn address-summaries
  "Takes a bitcoin proxy function, and an account name,
    and a list of function names/keywords.
   Returns a map of the addresses and the results of those functions."
  [bitcoin account functions]
  (for [address  (bitcoin :getaddressesbyaccount account)]
    (merge {:address address}
           (zipmap functions
                   (for [f functions]
                     (bitcoin f address))))))



