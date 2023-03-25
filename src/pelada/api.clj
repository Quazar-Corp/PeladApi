(ns pelada.api
  (:require [clojure.core.match :as matcher])
  (:require [pelada.pelada-model :as model]))

(def commands-string
  "\n\t--make-teams - Team selection (3 of 5)
  \t--list-portaria - Format the list to send to portaria")
  
(defn print-help-invalid-command
  []
  (println "Invalid command! Available ones:\n" commands-string))

(defn print-help-wrong-number-args
  []
  (println "Please give valid inputs:"
           "\n\n\tlein run <command> <filepath>" commands-string))

(defn team-maker-flow
  "Command flow to --make-teams"
  [filepath] 
  (->> filepath
       (model/to-pelada-object)
       (model/team-maker)
       (model/print-teams)))

(defn concierge-list-flow
  "Command flow to --list-portaria"
  [filepath] 
  (->> filepath
       (model/to-pelada-object)
       (model/concierge-format)))

(defn command-handler
  "Function that will handler the CLI input"
  [command filepath]
  (matcher/match [command]
           ["--make-teams"] (team-maker-flow filepath)
           ["--list-concierge"] (concierge-list-flow filepath)
           ["--server"] (println "Starting server...")
           ["--h"] (println commands-string)
           [_] (print-help-invalid-command)))
