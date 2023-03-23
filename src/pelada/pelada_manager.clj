(ns pelada.pelada-manager
  (:require [pelada.list-handler :as list-handler]))

(defn to-pelada-object
  "The manager abstracts the parser to pelada object"
  [filepath]
  (list-handler/parser filepath))

(defn team-maker-random
  "THis functions is responbile to choice and remove the player
  from the original list. it is tail recursive and returns a tuple with
  the team select and the updated players list"
  [acc players]
  (if (empty? players)
    [acc players]
    (let [chosen-player (rand-nth players)
          team (cons chosen-player acc)
          updated-players (remove #(= chosen-player %) players)]
      (if (= (count team) 5)
        [team updated-players]
        (team-maker-random team updated-players)))))

(defn team-maker
  "This function generate the 3 teams and returns it in tuple form."
  [pelada]
  (let [[first-team players-list-1] (team-maker-random '() (:players pelada))  
        [second-team players-list-2] (team-maker-random '() players-list-1)
        [third-team _] (team-maker-random '() players-list-2)]
    [first-team second-team third-team]))

(defn print-team 
  "Format the team players to print"
  [team]
  (apply str (interpose "\n" team)))

(defn print-teams
  "Pretty print to show the teams"
  [[t1 t2 t3]]
  (println (format "Time 1:\n%s" (print-team t1)))
  (println)
  (println (format "Time 2:\n%s" (print-team t2)))
  (println)
  (println (format "Time 3:\n%s" (print-team t3))))

(defn concierge-format
  "Prints a list formatted to send to the portaria"
  [pelada]
  (doseq [e (:players pelada)]
    (println e)))
