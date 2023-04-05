(ns pelada.core
 (:require [pelada.api :as api])
 (:gen-class)) 

(defn -main
  [& args]
  (if (< (count args) 2)
    (if (= (count args) 1)
      (api/command-handler (nth args 0) "")
      (api/print-help-wrong-number-args))
    (let [command (nth args 0)
          filepath (nth args 1)]
      (api/command-handler command filepath)))) 
