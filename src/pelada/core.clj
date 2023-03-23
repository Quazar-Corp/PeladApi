(ns pelada.core
 (:require [pelada.command :as cmd])) 

(defn -main
  [& args]
  (if (< (count args) 2)
    (if (= (count args) 1)
      (cmd/command-handler (nth args 0) "")
      (cmd/print-help-wrong-number-args))
    (let [command (nth args 0)
          filepath (nth args 1)]
      (cmd/command-handler command filepath)))) 
