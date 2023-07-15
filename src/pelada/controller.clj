(ns pelada.controller
  (:require [clojure.java.io :as io])
  (:require [cheshire.core :as json])
  (:require [clojure.string :as str])
  (:require [pelada.model :as model])
  (:require [clojure.core.match :as matcher]))
  
; Parser Functions
(defn- read-setup
  "Read the setup from a local json file"
  []
  (json/parse-string (slurp (io/resource "setup.json")) true))

(defn- is-player-empty?
  [string]
  (let [splitted-list (str/split string #"\.")
        second-el (second splitted-list)
        flag (= (not= second-el "") (not= second-el nil))]
    flag))

(defn- remove-number-from-list
  "Remove the number of the player in the list"
  [string]
  (->> (str/split string #"\.")
       second
       str/trim))

(defn- remove-check-emoji
  [string]
  (str/replace string #"✅" ""))

(defn- sublist
  "The main function of the function get-set-list.
  Responsible for retrieve teh sublist and remove the head(set name)"
  ([lines-list from]
   (->>
     (subvec lines-list (.indexOf lines-list from) (- (count lines-list) 1))
     (rest)
     (filter #(not= % ""))
     (filter #(not= % nil))
     (filter is-player-empty?) ; In case it has a number but not the player name
     (map remove-number-from-list) 
     (map remove-check-emoji))) 
  ([lines-list from to] 
  (let [fromIndex (.indexOf lines-list from)
        toIndex (.indexOf lines-list to)]
    (->> 
      (subvec lines-list fromIndex toIndex)
      (rest)
      (filter #(not= % ""))
      (filter #(not= % nil))
      (filter is-player-empty?) ; In case it has a number but not the player name
      (map remove-number-from-list)
      (map remove-check-emoji)))))

(defn- get-set-list
  "This function returns the sublist given the name of the set"
  [lines-list set-name]
  (matcher/match [set-name]
           [:goalkeepers] (sublist lines-list "Goleiros:" "Linha:")
           [:players] (sublist lines-list "Linha:" "Suplentes/Convidados:")
           [:substitutes] (sublist lines-list "Suplentes/Convidados:" "Sub 15:")
           [:minors] (sublist lines-list "Sub 15:")))

(defn- raw-list-pre-processing
  [filepath]
  (->> filepath
       (slurp)
       (str/trim)))

(defn- cli-parser
  "This function exists because even that the application know the format of the list
  it can't just trust in the user, so this function will create format that will be
  legible to the application."
  [filepath]
  ;(println (str (raw-list-pre-processing filepath) "\n|"))
  (let [string-list (raw-list-pre-processing filepath) ; TODO: error handling
        lines-list (str/split-lines string-list)
        goalkeepers-list (get-set-list lines-list :goalkeepers)
        players-list (get-set-list lines-list :players)
        substitutes-list (get-set-list lines-list :substitutes)
        minors-list (get-set-list lines-list :minors)]
    {:goalkeepers goalkeepers-list
     :players players-list
     :substitutes substitutes-list
     :minors minors-list}))

; Controller Functions
(defn to-pelada
  "Abstracts the parser to pelada model"
  [filepath]
  (cli-parser filepath))

(defn generate-weekly-list
  "Returns the weekly list with the updated date"
  []
  (println (model/weekly-list read-setup)))

(defn make-teams
  "Generate the 3 teams"
  [pelada]
  (model/team-maker pelada))

(defn print-teams
  "Print the 3 teams formatted"
  [[t1 t2 t3]]
  (model/print-teams [t1 t2 t3]))

(defn print-formatted-list-to-concierge
  "Print the formatted list to concierge"
  [pelada]
  (model/concierge-format pelada))
