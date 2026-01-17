(ns script)

;; Заглушка для random (TODO: реализовать)
(defn- stub-random [n]
  0)

(def- WORDS
  [["Zdravo" "Здрáво" "Привет"]
   ["Hvala" "Хвáла" "Спасибо"]
   ["Molim" "Мóлим" "Пожалуйста"]
   ["Da" "Да" "Да"]
   ["Ne" "Не" "Нет"]
   ["Dobar dan" "Дóбар дан" "Добрый день"]
   ["Ćao" "Чáо" "Привет / Пока"]
   ["Dobro" "Дóбро" "Хорошо"]
   ["Šta" "Шта" "Что"]
   ["Kako" "Кáко" "Как"]
   ["Gde" "Где" "Где"]
   ["Izvinite" "Извѝните" "Извините"]
   ["Kafa" "Кáфа" "Кофе"]
   ["Voda" "Вóда" "Вода"]
   ["Prijatelj" "При́ятель" "Друг"]
   ["Vidimo se" "Вѝдимо се" "Увидимся"]
   ["Živeli" "Жѝвели" "За здоровье (тост)"]
   ["Danas" "Дáнас" "Сегодня"]
   ["Lepo" "Лéпо" "Красиво / Приятно"]
   ["Može" "Мóже" "Можно / Договорились"]])

;; Получить 3 случайных неправильных ответа
(defn- get-wrong-answers [correct-idx]
  ;; (let [all-indices (range (count WORDS))
  ;;       wrong-indices (filter (fn [i] (not= i correct-idx)) all-indices)]
  ;;   ;; TODO: перемешать когда будет random
  ;;   (take 3 wrong-indices))
  [0 1 2])

;; Создать 4 варианта ответа с правильным на позиции correct-pos
(defn- make-options [word-idx correct-pos]
  (let [correct-word (get (get WORDS word-idx) 2)
        wrong-indices (get-wrong-answers word-idx)
        wrong-words (map (fn [i] (get (get WORDS i) 2)) wrong-indices)
        options (vec wrong-words)]
    (vec (concat
          (concat (take correct-pos options)
                  [correct-word])
          (drop correct-pos options)))))

;; Экран вопроса
(defn- quiz-question-view [word-idx correct-pos]
  (let [word (get WORDS word-idx)
        latin (get word 0)
        cyrillic (get word 1)
        options (make-options word-idx correct-pos)]
    [:column
     [:text (str latin " [" cyrillic "]")]
     [:row
      [:button {:click {:type :quiz-answer :word-idx word-idx
                        :correct correct-pos :selected 0}} (get options 0)]
      [:button {:click {:type :quiz-answer :word-idx word-idx
                        :correct correct-pos :selected 1}} (get options 1)]]
     [:row
      [:button {:click {:type :quiz-answer :word-idx word-idx
                        :correct correct-pos :selected 2}} (get options 2)]
      [:button {:click {:type :quiz-answer :word-idx word-idx
                        :correct correct-pos :selected 3}} (get options 3)]]]))

;; Экран результата
(defn- quiz-result-view [word-idx was-correct correct-answer]
  [:column
   [:text (if was-correct "Правильно!" (str "Неправильно! Ответ: " correct-answer))]
   [:button {:click {:type :home}} "Домой"]])

(defn main [event]
  (case (:type event)
    :quiz-start
    (quiz-question-view 0 (stub-random 4))

    :quiz-answer
    (let [word-idx (:word-idx event)
          correct (:correct event)
          selected (:selected event)
          was-correct (= correct selected)
          correct-answer (get (get WORDS word-idx) 2)]
      (quiz-result-view word-idx was-correct correct-answer))

    ;; default - главный экран
    [:column
     [:text "Тест сербских слов"]
     [:button {:click {:type :quiz-start}} "Начать"]]))
