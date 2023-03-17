(ns pelada.list-handler
  (:require [clojure.string :as str])
  (:require [clojure.core.match :as m]))

; DEFAULT VALUES
(def default_owner "Onofre")
(def default_hour "20hrs - 22hrs")
(def default_day "Quarta")

(defn- get-next-wednesday
 "Retrieve the formatted date of the next Wednesday" 
  []
  (let [now (java.time.LocalDate/now (java.time.ZoneId/of "GMT-3"))
        current-day-of-week (.getDayOfWeek now)
        ; 7+3 (current + number of days for the next we) - current day 
        days-to-next-wednesday (- 10 (.getValue current-day-of-week))
        ; Math/max ensure that the number of days is positive
        next-wednesday (.plusDays now (Math/max days-to-next-wednesday 0))
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

; PROCESSMENT
(defn get-set-list
  [lines-list set-name]
  (m/match [set-name]
           [:goalkeeper] "goalkeeper"
           [:players] "players"
           [:substitutes] "substitutes"
           [:guests] "guests"))

(defn parser
  "This function exists because even that the application know the format of the list
  it can't just trust in the user, so this function will create format that will be
  legible to the application."
  [filepath]
  (let [string-list (str/trim (slurp filepath)) ; TODO: error handling
        lines-list (str/split-lines string-list)
        goalkeepers-list (get-set-list lines-list :goalkeeper)]
    (println goalkeepers-list)))

