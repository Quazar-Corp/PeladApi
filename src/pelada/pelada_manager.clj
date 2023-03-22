(ns pelada.pelada-manager
  (:require [pelada.list-handler :as list-handler]))

(defn to-pelada-object
  [filepath]
  (list-handler/parser filepath))

(defn team-maker-random
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
  [pelada]
  (let [[first-team players-list-1] (team-maker-random '() (:players pelada))  
        [second-team players-list-2] (team-maker-random '() players-list-1)
        [third-team _] (team-maker-random '() players-list-2)]
    [first-team second-team third-team]))

;(defn- print-team
;  [team]
;  (print-str 
;    (doseq [x team]
;      (format "%s\n" x))))
(defn print-team [team]
  (apply str (interpose "\n" team)))

(defn print-teams
  [[t1 t2 t3]]
  (println (format "Time 1:\n%s" (print-team t1)))
  (println)
  (println (format "Time 2:\n%s" (print-team t2)))
  (println)
  (println (format "Time 3:\n%s" (print-team t3))))
