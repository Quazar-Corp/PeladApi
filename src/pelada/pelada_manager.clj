(ns pelada.pelada-manager
  (:require [pelada.list-handler :as list-handler]))

(defn to-pelada-object
  [filepath]
  list-handler/parser filepath)

(defn team-maker
  [])
