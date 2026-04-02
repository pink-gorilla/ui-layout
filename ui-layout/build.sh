#!/bin/sh


clojure -X:webly:npm-install

./scripts/copy_res.sh

clojure -T:build jar