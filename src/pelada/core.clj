(ns pelada.core
  (:require [pelada.list-handler :as list-handler])
  (:require [pelada.pelada-manager :as manager]))
  
;(println "****** Test Zone ******\n\n")
;(println ">>> Generating the weekly list with default values\n\n")
;(println (list-handler/weekly-list))

(println "\n\n>>> Reading sample.dat\n\n")
(println (list-handler/parser "./sample.dat"))
(println "\n")
(let [pelada (manager/to-pelada-object "./sample.dat/")
      [t1 t2 t3] (manager/team-maker pelada)]
  (println (format "Time 1:\n%s\n\nTime 2:\n%s\n\nTime 3:\n%s" t1 t2 t3)))

(defn -main
  [& args]
  )
