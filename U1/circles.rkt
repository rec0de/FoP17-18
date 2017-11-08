;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname circles) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; Helper functions / subroutines

;; Math-min
;; math-min: number number -> number
;; Explanation: Returns the smaller of two given numbers
;; Example: (math-min 8 4) -> 4

(define (math-min a b)
  (if (< a b) a b)
)

;; Tests
(check-expect (math-min 500 499) 499)
(check-expect (math-min -5 1) -5)
(check-expect (math-min -3.14 -4.2) -4.2)

;; Math-max
;; math-max: number number -> number
;; Explanation: Returns the larger of two given numbers
;; Example: (math-max 8 4) -> 8

(define (math-max a b)
  (if (< a b) b a)
)

;; Tests
(check-expect (math-max 500 499) 500)
(check-expect (math-max -5 1) 1)
(check-expect (math-max -3.14 -4.2) -3.14)

;; Exercise 7.1
;; distance-centers: number number number number -> number
;; Explanation: Calculates euclidean distance between two points [centerpoints of circles, specifically] (x1 y1) and (x2 y2)
;; Example: (distance-centers 0 0 1 1) -> sqrt 2 ~= 1.41

(define (distance-centers x1 y1 x2 y2)
  (sqrt (+ (sqr (- x1 x2)) (sqr (- y1 y2))))
)

;; Tests
(check-expect (distance-centers 0 0 0 2) 2)
(check-within (distance-centers 1 1 0 0) (sqrt 2) 0.0001)
(check-expect (distance-centers 512 42 512 42) 0)

;; Exercise 7.2
;; circles-position: number number number number number number -> symbol
;; Explanation: Determines the position of two circles  defined by center point and radius (x1, y1, r1, x2, y2, r2) to each other
;;              and returns 'External 'Intersect or 'Internal accordingly ('External for no overlap, 'Intersect for overlapping circles, and 'Internal for a circle fully inside the other)
;;              For the purpose of this function, tangent circles are recognized as intersecting in every case
;; Example: (circles-position 0 0 1 1 0 1) -> 'Intersect 

(define (circles-position x1 y1 r1 x2 y2 r2)
  (cond
    [(< (+ (distance-centers x1 y1 x2 y2) (math-min r1 r2)) (math-max r1 r2)) 'Internal] ;; One circle is inside the other iff the farthest point from the second circle's center (distance + radius) is still within radius of the second circle
    [(> (distance-centers x1 y1 x2 y2) (+ r1 r2)) 'External] ;; Two circles don't touch each other iff the distance btwn center points is larger than the sum of their radii
    [else 'Intersect] ;; Intersection is last remaining possibility
  )
)

;; Tests
(check-expect (circles-position 0 0 10 1 2 2) 'Internal)
(check-expect (circles-position -2 0 1 2 0 1) 'External)
(check-expect (circles-position 0 0 1 1 0 1) 'Intersect)
(check-expect (circles-position 0 0 2 0 1 1) 'Intersect) ;; One circle touching the other from within


;; Exercise 7.3
;; calculate-properties: number number number symbol -> number
;; Explanation: Returns a property of the circle defined by center and radius (x, y, r) specified by symbol. Possible properties: 'Area 'Circumference 'Diameter 'Distance (Distance to origin)
;; Example: (calculate-properties 2 4 10 'Diameter) -> 20
;;          (calculate-properties 2 4 10 'Distance) -> sqrt 20 ~= 4.47
;;          (calculate-properties 2 4 10 'Circumference) -> 20 * pi ~= 62.83
;; NOTE: 'Distance returns distance _of center point_ to origin, not the minimum distance of any point on the circle to origin

(define (calculate-properties x y r s)
  (cond
    [(< r 0) (error 'calculate-properties "radius is negative")]
    [(symbol=? s 'Diameter) (* r 2)]
    [(symbol=? s 'Circumference) (* pi r 2)]
    [(symbol=? s 'Area) (* pi r r)]
    [(symbol=? s 'Distance) (distance-centers 0 0 x y)]
    [else (error 'calculate-properties "unknown property")]
  )
)

;; Tests
(check-expect (calculate-properties 0 0 1 'Diameter) 2)
(check-within (calculate-properties 0 0 2 'Circumference) (* pi 4) 0.0001)
(check-within (calculate-properties 0 0 3 'Area) (* pi 9) 0.0001)
(check-expect (calculate-properties 0 2 2 'Distance) 2)
(check-error (calculate-properties 0 0 1 'Something) "calculate-properties: unknown property")
(check-error (calculate-properties 0 0 -1 'Area) "calculate-properties: radius is negative")