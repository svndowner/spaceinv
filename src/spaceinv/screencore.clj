(ns spaceinv.core
  (:gen-class))

(require '[lanterna.screen :as s])

(def scr (s/get-screen))


(defn clear-screen [screen]
  (let [blank (apply str (repeat 80 \space))]
    (doseq [row (range 44)]
      (s/put-string screen 0 row blank))))

;; generate list of enemy vectors
;;(def enemyVec)
;;(defn spawnEnemies [])

;(defn putEnemies [] )  ;list of vectors ([1 1 "@"],[2 1 "@"],...)

;;(defn putProjectiles ) ;collision detection here! check if tile is already in use, if yes, delete both.

(defn getInp [inp xp] ;[inp (s/get-key scr)]
  (cond
    (= inp :left) (dec xp)
    (= inp :right) (inc xp)
    :else xp))



(defn mainLoop [a, b]
  (loop [xpos a
         ypos b
         ;enemyvectors
         ;playervector
         ;projectilevector
         ]
    (clear-screen scr)
    (s/put-string scr (/ ypos 8) 0 (str (/ ypos 8)) {:fg :red}) ;putEnemy funtion
    (s/put-string scr xpos 20 "nôn"{:bg :green}) ;putPlayer function
    (s/move-cursor scr (inc xpos) 20)
    (s/redraw scr)
    (Thread/sleep 10) ;wait for input!
    (if (= xpos 60)
        ypos
      (recur (getInp (s/get-key scr) xpos) (inc ypos))   ;(getInp (s/get-key-blocking scr {:interval 10 :timeout 10}) xpos) (inc ypos))
    )))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]


;; game loop
;; sub loop put-string scr x y enemysign

(s/start scr)
(mainLoop 1 1)

(s/stop scr))
