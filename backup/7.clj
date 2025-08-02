(defn main [{a :events b :foo} c]
  [a b c])

(main {:events 1 "foo" 2} 3)
