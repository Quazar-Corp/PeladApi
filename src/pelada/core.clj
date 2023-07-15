(ns pelada.core
 (:require [pelada.cli :as cli])
 (:gen-class)) 

(defn -main
  [& args]
  (if (< (count args) 2)
    (if (= (count args) 1)
      (cli/command-handler (nth args 0) "")
      (cli/print-help-wrong-number-args))
    (let [command (nth args 0)
          filepath (nth args 1)]
      (cli/command-handler command filepath)))) 
