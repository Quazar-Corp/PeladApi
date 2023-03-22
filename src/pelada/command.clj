(ns pelada.command
  (:require [pelada.list-handler :as list-handler])
  (:require [clojure.core.match :as m])
  (:require [pelada.pelada-manager :as manager]))

(defn team-maker-flow
  "Command flow to --make-teams"
  [filepath] 
  (->> filepath
       (manager/to-pelada-object)
       (manager/team-maker)
       (manager/print-teams)))

(defn portaria-list-flow
  "Command flow to --list-portaria"
  [filepath] 
  (->> filepath
       (manager/to-pelada-object)
       (manager/portaria-format)))

(defn command-handler
  "Function that will handler the CLI input"
  [filepath command]
  (m/match [command]
           ["--make-teams"] (team-maker-flow filepath)
           ["--list-portaria"] (portaria-list-flow filepath)
           [""] (println "Please, use a command."
                         "\n\t --make-teams - Team selection (3 of 5)"
                         "\n\t --list-portaria - Format the list to send to portaria")))
