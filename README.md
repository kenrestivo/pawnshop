# pawnshop

A thin wrapper around Bitcoind's JSON API.

## Usage

Add

```clojure

[pawnshop "0.1.0-SNAPSHOT"]

```

to your project.clj. Then:


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

You can access anything in the [Bitcoind API]:  https://en.bitcoin.it/wiki/Original_Bitcoin_client/API_calls_list

Just keywordize or quote the function name, and give it as the second argument to the bitcoin proxy function, and any arguments it requires afterwards.

## TO DO



## License

Copyright © 2012 ken restivo

Distributed under the Eclipse Public License, the same as Clojure.
