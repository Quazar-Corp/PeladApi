(ns pelada.command
  (:require [pelada.list-handler :as list-handler])
  (:require [clojure.core.match :as m])
  (:require [pelada.pelada-manager :as manager]))

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
       (manager/to-pelada-object)
       (manager/team-maker)
       (manager/print-teams)))

(defn concierge-list-flow
  "Command flow to --list-portaria"
  [filepath] 
  (->> filepath
       (manager/to-pelada-object)
       (manager/concierge-format)))

(defn command-handler
  "Function that will handler the CLI input"
  [command filepath]
  (m/match [command]
           ["--make-teams"] (team-maker-flow filepath)
           ["--list-concierge"] (concierge-list-flow filepath)
           ["--h"] (println commands-string)
           [_] (print-help-invalid-command)))
