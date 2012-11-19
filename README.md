# pawnshop

A thin wrapper around Bitcoind's JSON API.

## Usage

Add [pawnshop "0.1.0-SNAPSHOT"] to your project.clj. Then:

```clojure

(use 'pawnshop.core)

(def bitcoin (bitcoin-proxy "http://127.0.0.1:8332/" "yourlogin" "yourpassword"))

(bitcoin :getinfo)

(bitcoin :getaddressesbyaccount "test account")

(address-summarize bitcoin "test account" [:getreceivedbyaddress :dumpprivkey])


(-> (bitcoin :listaccounts)
    walk/stringify-keys
    (get "test account")
    (longify-amounts))

```

## License

Copyright © 2012 ken restivo

Distributed under the Eclipse Public License, the same as Clojure.
