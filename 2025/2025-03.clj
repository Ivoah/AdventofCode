(require '[clojure.string :as str])

(def toInt #(Integer/parseInt %))
(def init #(-> % reverse rest reverse))

(def banks (map
  #(map toInt (str/split % #""))
  (str/split (slurp "3.example.txt") #"\n")))

(prn (apply + (map banks
  #(+ (* 10 ()) ()))))
