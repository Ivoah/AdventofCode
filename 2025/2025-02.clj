(require '[clojure.string :as str])

(def toLong #(Long/parseLong %))
(defn parseRange [s]
  (let [ss (map toLong (str/split s #"-"))]
    (range (first ss) (+ (last ss) 1))))

(def ids (mapcat
  parseRange
  (str/split (str/trim (slurp "2.txt")) #",")))

(println (apply + (filter #(re-matches #"(\d+)\1" (str %)) ids)))
(println (apply + (filter #(re-matches #"(\d+)\1+" (str %)) ids)))
