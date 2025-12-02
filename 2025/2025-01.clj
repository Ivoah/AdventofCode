(require '[clojure.string :as str])
(require '[clojure.math :as math])

(def instr (apply vector (map
  #(Integer/parseInt (str/replace (str/replace % #"L" "-") #"R" ""))
  (str/split (slurp "1.txt") #"\n"))))

(defn expand [instr]
  (mapcat #(repeat (abs %1) (int (math/signum %1))) instr))

(defn countZeros [instr]
  (last (reduce
    (fn [acc r]
      (let [p (mod (+ (acc 0) r) 100)]
        [p (+ (acc 1) (if (= p 1) 1 0))]))
    [51 0]
    instr)))

(prn (countZeros instr))
(prn (countZeros (expand instr)))
