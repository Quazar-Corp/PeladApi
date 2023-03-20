(ns pelada.core
  (:require [pelada.list-handler :as list-handler])
  (:require [pelada.pelada-manager :as manager]))
  
;(println "****** Test Zone ******\n\n")
;(println ">>> Generating the weekly list with default values\n\n")
;(println (list-handler/weekly-list))

(println "\n\n>>> Reading sample.dat\n\n")
(println (list-handler/parser "./sample.dat"))

(defn -main
  [& args]
  )
