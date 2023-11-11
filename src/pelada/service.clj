(ns pelada.service
  (:require [clojure.java.io :as io])
  (:require [cheshire.core :as json]))

(defn- read-setup
  "Read the setup from a local json file"
  []
  (json/parse-string (slurp (io/resource "setup.json")) true))

(defn- read-pelada-json
  []
  (json/parse-string (slurp "pelada.json") true))

(defn get-setup 
  []
  (read-setup))

(defn read-pelada
  []
  (read-pelada-json))
