(ns pawnshop.core-test
  (:use clojure.test
        pawnshop.core))



(def bitcoin (bitcoin-proxy "http://127.0.0.1:8332/" "ken" "Yee5XGrJRP"))

(bitcoin :getinfo) 

;; needed because the keys are account names, can have embedded spaces :-(
(-> (bitcoin :listaccounts)
    walk/stringify-keys)


(bitcoin :getblockcount)

(bitcoin :getpeerinfo)

(def addr1 (bitcoin :getnewaddress))

(def addr (bitcoin :getnewaddress "test account"))

(bitcoin :getaddressesbyaccount "test account")

(bitcoin :backupwallet "/tmp/foo")


(get-response "http://127.0.0.1:8332/" "ken" "Yee5XGrJRP" :listaccounts [])
(get-response "http://127.0.0.1:8332/" "ken" "Yee5XGrJRP" :backupwallet ["/tmp/backups/"])



(-> (bitcoin :listaccounts)
    walk/stringify-keys
    (get "test account")
    (longify-amounts))

(bitcoin :getreceivedbyaccount "test account")

