(ns pelada.model)

(def default_owner "Onofre")
(def default_hour "20hrs - 22hrs")
(def default_day "Quarta")

(defn- days-to-next-wednesday
  "This functions calculates the day to next wednesday based on the current day"
  [today]
  (if (= today 3)
    0
    (if (< today 3)
      (- 3 today)
      (- 10 today)))
  )

(defn- get-next-wednesday
 "Retrieve the formatted date of the next Wednesday" 
  []
  (let [now (java.time.LocalDate/now (java.time.ZoneId/of "GMT-3"))
        current-day-of-week (.getValue (.getDayOfWeek now))
        ; Math/max ensure that the number of days is positive
        next-wednesday (.plusDays now (Math/max (days-to-next-wednesday current-day-of-week) 0))
        formatter (java.time.format.DateTimeFormatter/ofPattern "dd/MM")]
    (.format next-wednesday formatter)))

(defn- list-string
  "Format the string with the given values"
  [date day hour owner]
   (str "*Lista pelada " day
        " " date
        " PN Boulevard, " hour
        "*\n\n"
        "*Goleiros*\n" "1.\n2."
        "\n\n"
        "*Jogadores*\n" 
        "1. " owner
        "\n\n"
        "*Suplentes*\n1."
        "\n\n"
        "*Convidados*\n1."
  ))

(defn weekly-list
  "Generate the weekly list for the pelada"
  ([] (list-string (get-next-wednesday) default_day default_hour default_owner))
  ([date] (list-string date default_day default_hour default_owner))
  ([date day] (list-string date day default_hour default_owner))
  ([date day hour] (list-string date day hour default_owner)))

(defn- team-maker-random
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

(defn- print-team 
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
  "Prints a list formatted to send to the concierge"
  [pelada]
  (doseq [e (:players pelada)]
    (println e)))
