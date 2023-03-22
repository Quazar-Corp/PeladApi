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
(defn- remove-number-from-list
  "Remove the number of the player in the list"
  [string]
  (->> (str/split string #"\.")
       second))

(defn- sublist
  "The main function of the function get-set-list.
  Responsible for retrieve teh sublist and remove the head(set name)"
  ([lines-list from] (rest (subvec lines-list (.indexOf lines-list from) (- (count lines-list) 1)))) 
  ([lines-list from to] 
  (let [fromIndex (.indexOf lines-list from)
        toIndex (.indexOf lines-list to)]
    (->> (subvec lines-list fromIndex toIndex)
         (rest)
         (filter #(not= % ""))
         (map remove-number-from-list)))))

(defn- get-set-list
  "This function returns the sublist given the name of the set"
  [lines-list set-name]
  (m/match [set-name]
           [:goalkeepers] (sublist lines-list "Goleiros" "Jogadores")
           [:players] (sublist lines-list "Jogadores" "Suplentes")
           [:substitutes] (sublist lines-list "Suplentes" "Convidados")
           [:guests] (sublist lines-list "Convidados" "Comunicados")))

(defn parser
  "This function exists because even that the application know the format of the list
  it can't just trust in the user, so this function will create format that will be
  legible to the application."
  [filepath]
  (let [string-list (str/trim (slurp filepath)) ; TODO: error handling
        lines-list (str/split-lines string-list)
        goalkeepers-list (get-set-list lines-list :goalkeepers)
        players-list (get-set-list lines-list :players)
        substitutes-list (get-set-list lines-list :substitutes)
        guests-list (get-set-list lines-list :guests)]
    {:goalkeepers goalkeepers-list
     :players players-list
     :substitutes substitutes-list
     :guests guests-list}))
