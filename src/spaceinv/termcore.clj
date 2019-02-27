(ns spaceinv.core
  (:gen-class))

(require '[lanterna.terminal :as t])

(def term (t/get-terminal))

(defn spawnRow [y]
 (let [symb (cond
                (= y 1) "#"
                (= y 2) "@"
                (= y 3) "="
                (= y 4) "v")]
  (loop [xpos 1
         ypos y
         rowList '()]
    (if (= xpos 11)
      rowList
      (recur (inc xpos) ypos (conj rowList [symb xpos ypos]))))))

(defn spawnAll []
  (loop [fullList '()
         cnt 1]
    (if (= cnt 5)
      fullList
      (recur (apply conj fullList (spawnRow cnt)) (inc cnt)))))

(def enemyList (spawnAll))


(defn drawEnemies [enLst]
  (when (seq enLst)
    (let [cur (first enLst)]
    (t/put-string term (get cur 0) (get cur 1) (get cur 2)))
    (recur (rest enLst))))
                

;;(defn putProjectiles ) ;collision detection here! check if tile is already in use, if yes, delete both.


(defn getInp [inp xp] ;[inp (s/get-key scr)]
  (cond
    (= inp :left) (dec xp)
    (= inp :right) (inc xp)
    :else xp))

(defn displayScore [score]
  (t/put-string term (str "Score: " score) 61 42))


(defn mainLoop [a, b, e, s]
  (loop [xpos a
         ypos b
         enemyList e
         ;playervector
         ;projectilevector
         score s
         ]
    (t/clear term)
    (drawEnemies enemyList)
    (t/put-string term "nôn" xpos 40) ;putPlayer function
    (displayScore s)
    (t/move-cursor term (inc xpos) 40)
    (Thread/sleep 10) ;wait for input!
    (if (= xpos 60)
        ypos
      (recur (getInp (t/get-key term) xpos)
             (inc ypos)
             enemyList ;check set against projectiles
             score)
    )))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]


;; game loop
;; sub loop put-string scr x y enemysign

(t/start term)
(mainLoop 1 1 enemyList 100)

(t/stop term))
