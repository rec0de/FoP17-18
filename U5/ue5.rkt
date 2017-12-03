;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname ue5) (read-case-sensitive #t) (teachpacks ((lib "image.rkt" "teachpack" "2htdp"))) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ((lib "image.rkt" "teachpack" "2htdp")) #f)))
;; Width of the final picture
(define WIDTH 512)
;; Height of the final picture
(define HEIGHT 512)
;; Number of grid cells
(define SCALE 64)
;; The width of the grid
(define GWIDTH (ceiling (/ WIDTH SCALE))) ;; Modified to allow image sizes where height / width aren't multiples of scale
;; The height of the grid
(define GHEIGHT (ceiling (/ HEIGHT SCALE))) ;; Modified to allow image sizes where height / width aren't multiples of scale

;; Defines a 2D Vector
;; x - The x-value of the vector
;; y - The y-value of the vector
(define-struct vector2d (x y))

;; create-gradients: number -> (listof vector2d)
;; Explanation: Generates n random vectors of length 1
;; Example: (create-gradients 1) -> (list (make-vector2d #i0.055253518154322646 #i0.9984723575200116))
(define (create-gradients n)
  (local
    ;; ranVec: X -> vector2d
    ;; Explanation: Generates random vector on unit circle - input parameter is ignored and only provided for easier usage with build-list
    ;; Example: (ranVec 'couldntcareless) -> (make-vector2d #i0.055253518154322646 #i0.9984723575200116)
    ((define (ranVec ignoreme)
       (local
         ((define rannum (random))) ;; Random number to be used for vector generation
         (make-vector2d (cos (* rannum 2 pi)) (sin (* rannum 2 pi)))
       )
    ))
    (build-list n ranVec)
  )
)
;; Tests
(define testvec (first (create-gradients 1)))
(check-expect (length (create-gradients 3)) 3)
(check-expect (length (create-gradients 0)) 0)
(check-expect (create-gradients 0) empty)
(check-within (sqrt (+ (sqr (vector2d-x testvec)) (sqr (vector2d-y testvec)))) 1 0.0001)

;; fade: number -> number
;; Uses a Sigmoid function to smooth numbers betwenn 0 to 1
;; Example: (fade 1) -> 1
(define (fade t)
  (+ (- (* 6 t t t t t) (* 15 t t t t)) (* 10 t t t))
)
;; Tests
(check-expect (fade 1) 1)
(check-within (fade 0.5) 0.5 0.001)
(check-within (fade 0.3) 0.16308 0.001)
(check-within (fade 0.8) 0.94208 0.001)

;; Globally store the list of gradients
(define gradients (create-gradients (ceiling(* (+ 1 GWIDTH) (+ 1 GHEIGHT))))) ;; Modified to allow image sizes where height / width aren't multiples of scale


;; linear-interpolation: number number number -> number
;; Explanation: Calculates weighted interpolated value between x and y
;; Example: (linear-interpolation 0 10 0.25) ->  2.5
(define (linear-interpolation x y w)
 (+ (* (- 1 w) x) (* w y))    
)
;; Tests
(check-expect (linear-interpolation 2 4 0.5) 3)
(check-expect (linear-interpolation 3 500 0) 3)
(check-expect (linear-interpolation 5 10 0.1) 5.5)

;; nth-element: (listof X) number -> X
;; Explanation: Returns the n-th element of given list, starting at zero. Error on out-of-bounds index. Slooooooooow.
;; Example: (nth-element (list 1 2 3) 0) -> 1
(define (nth-element lst index)
  (cond
    [(empty? lst) (error 'nth-element "can't access nonexistant index of list")] ;; index not in list
    [(zero? index) (first lst)] ;; index references first element
    [else (nth-element (rest lst) (- index 1))]
  )
)
;; Tests
(check-expect (nth-element (list 'r 'k 'b 'n 'e 'o) 3) 'n)
(check-expect (nth-element (list 1 2 3 4 5 6 7) 1) 2)
(check-error (nth-element (list 'a 'l 'l) 5) "nth-element: can't access nonexistant index of list")

;; scalar-mult: vector2d vector2d -> number
;; Explanation: Returns scalar product of two 2d vectors
;; Example: (scalar-mult (make-vector2d 1 2) (make-vector2d 3 3)) -> 9
(define (scalar-mult vec-a vec-b)
  (+ (* (vector2d-x vec-a) (vector2d-x vec-b)) (* (vector2d-y vec-a) (vector2d-y vec-b)))
)
;; Tests
(check-expect (scalar-mult (make-vector2d 0 0) (make-vector2d 523 5712)) 0)
(check-expect (scalar-mult (make-vector2d 1 2) (make-vector2d -7 8)) 9)

;; dot-grid-gradient: number number number number -> number
;; Explanation: Calculates scalar product of a gradient specified by coordinates and the vector between that gradient and the specified pixel
;;              Parameter order is pixel_x pixel_y gradient_x gradient_y
;; Example: (dot-grid-gradient 30 30 0 0) -> -23.96...
(define (dot-grid-gradient ix iy x y)
  (local
    ;; coords2gradient: number number -> vector2d
    ;; Explanation: Returns gradient of a given grid intersection by looking up in gradients list
    ;; Example: (coords2gradient 2 1) -> [random gradient vector at list position 17]
    ((define (coords2gradient x y)
       (nth-element gradients (+ (* y GWIDTH) x)) ;; index of gradient is x + y*gradients_per_row
    ))
    (scalar-mult (coords2gradient x y) (make-vector2d (- ix x) (- iy y)))
  )
)
;; Tests
;; (not specified)

;; perlin-noise: number number -> number
;; Explanation: Calculates the perlin-noise value for a given pixel by looking up all corner gradients and interpolating values according to given formula
;;              Output is scaled to range 0-1
;; Example: (not specified)
(define (perlin-noise x y)
  (local
    ((define rx (/ x SCALE))
     (define ry (/ y SCALE))
     ;; Calculate corner values
     (define top-left (dot-grid-gradient rx ry (floor rx) (floor ry)))
     (define top-right (dot-grid-gradient rx ry (ceiling rx) (floor ry)))
     (define bot-left (dot-grid-gradient rx ry (floor rx) (ceiling ry)))
     (define bot-right (dot-grid-gradient rx ry (ceiling rx) (ceiling ry)))
     (define top (linear-interpolation top-left top-right (fade (- rx (floor rx)))))
     (define bot (linear-interpolation bot-left bot-right (fade (- rx (floor rx)))))
     (define final (linear-interpolation top bot (fade (- ry (floor ry)))))
    )
    (+ (/ final 2) 0.5) ;; Scale final value from -1..1 to 0..1
  ) 
)
;; Tests
(check-within (perlin-noise (* (random) WIDTH) (* (random) HEIGHT)) 0.5 0.5)

;; Pulling color definitions to global scope for easier modification
;; Color definitions
(define deepsea (make-color 24 120 177))
(define sea (make-color 32 164 243))
(define beach (make-color 244 208 111))
(define grasslands (make-color 122 229 130))
(define grasslands2 (make-color 100 188 107))
(define grasslands3 (make-color 78 146 83))
(define mountains (make-color 52 052 52))
(define snow (make-color 200 200 200))

;; number2color: number -> color
;; Explanation: Converts a number between 0 and 1 to a color by interpreting the number as terrain height
;; Example: (number2color 0.4) -> beach -> (make-color 244 208 111)
(define (number2color num)
  (cond
    [(< num 0.32) deepsea] ;; Deep Sea
    [(< num 0.38) sea] ;; Sea
    [(< num 0.45) beach] ;; Beach
    [(< num 0.52) grasslands] ;; Grasslands
    [(< num 0.59) grasslands2] 
    [(< num 0.65) grasslands3]
    [(< num 0.74) mountains] ;; Mountains
    [else snow]
  )
)
;; Tests
(check-expect (number2color 0.5) grasslands)
(check-expect (number2color 0.8) snow)

;; create-land: number number -> (listof color)
;; Explanation: Creates a list of colors representing a width*height image colored according to the perlin-noise value of each pixel
;; Example: (not specified)
(define (create-land width height)
  ;; Lambda: number -> color
  ;; Explanation: Converts the index of a pixel to its coordinates and returns the color corresponding to the perlin-noise value of that pixel
  ;; Example: 578 -> (make-color 32 164 243)
  (build-list (* width height) (lambda (index) (number2color (perlin-noise (modulo index WIDTH) (floor (/ index WIDTH)))))) ;; x y coords are index mod width and integer part of index/width
)
;; Tests
;; (not specified)

;;(save-image (color-list->bitmap (create-land WIDTH HEIGHT) WIDTH HEIGHT) "land.png")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Performance optimized functions below    ;;
;; Not really relevant, but cool either way ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; perlin-noise-on-steroids: number number vector2d vector2d vector2d vector2d -> number
;; Explanation: Calculates the perlin-noise value for a given pixel using given corner gradients to reduce runtime
;;              Output is scaled to range 0-1
;; Example: (not specified)
(define (perlin-noise-on-steroids rx ry corner-top-left corner-top-right corner-bot-left corner-bot-right)
  (local
     ((define frx (floor rx))
     (define fry (floor ry))
     (define crx (ceiling rx))
     (define cry (ceiling ry))
     (define top (linear-interpolation (scalar-mult corner-top-left (make-vector2d (- rx frx) (- ry fry))) (scalar-mult corner-top-right (make-vector2d (- rx crx) (- ry fry))) (fade (- rx frx))))
     (define bot (linear-interpolation (scalar-mult corner-bot-left (make-vector2d (- rx frx) (- ry cry))) (scalar-mult corner-bot-right (make-vector2d (- rx crx) (- ry cry))) (fade (- rx frx))))
    )
    (+ (/ (linear-interpolation top bot (fade (- ry fry))) 2) 0.5) ;; Scale final value from -1..1 to 0..1
  ) 
)
;; Tests
;; (not specified)

;; create-land-and-hurry: number number -> (listof color)
;; Explanation: Creates a list of colors representing a width*height image colored according to the perlin-noise value of each pixel.
;;              Makes heavy use of caching to cut down runtime using perlin-noise-on-steroids. Code is barely readable, but works.
;;              Performs about 4.5x better than regular perlin noise for 512x512 images. Output should be identical to regular create-land
;; Example: (not specified)
(define (create-land-and-hurry width height)
  (local
    (
     ;; coords2gradient: number number -> vector2d
     ;; Explanation: Returns gradient of a given grid intersection by looking up in gradients list
     ;; Example: (coords2gradient 2 1) -> [random gradient vector at list position 17]
     (define (coords2gradient x y)
       (nth-element gradients (+ (* y GWIDTH) x)) ;; index of gradient is x + y*gradients_per_row
     )
     ;; Cached corner gradients initial definition
     (define cur-top-left  (coords2gradient 0 0))
     (define cur-top-right (coords2gradient 1 0))
     (define cur-bot-left  (coords2gradient 0 1))
     (define cur-bot-right (coords2gradient 1 1))
     (define cur-x 0.5)
     (define cur-y 0.5)
     ;; recalc: -> undefined
     ;; Explanation: Recalculates all cached corner gradients according to cur-x and cur-y
     ;; Example: (recalc!) -> undefined
     (define (recalc!)
       (begin
         (set! cur-top-left  (coords2gradient (floor cur-x) (floor cur-y)))
         (set! cur-top-right (coords2gradient (ceiling cur-x) (floor cur-y)))
         (set! cur-bot-left  (coords2gradient (floor cur-x) (ceiling cur-y)))
         (set! cur-bot-right  (coords2gradient (ceiling cur-x) (ceiling cur-y)))
       )
     )
     ;; inc-x: number -> undefined
     ;; Explanation: Increments x value used for calculation of cached gradients or resets to zero if the x value of the current pixel wrapped around to zero
     ;;              newx is x value of currently processed pixel divided by SCALE
     ;; Example: (inc-y) -> undefined
     (define (inc-x! newx)
       (begin
         (set! cur-x (+ (floor newx) 0.5))
         (recalc!)
       )
     )
     ;; inc-y: -> undefined
     ;; Explanation: Increments y value used for calculation of cached gradients by one, resets x value and recalculates cached gradients
     ;; Example: (inc-y) -> undefined
     (define (inc-y!)
       (begin
         (set! cur-y (+ cur-y 1))
         (set! cur-x 0.5)
         (recalc!)
       )
     )
     ;; create-pixels: number -> color
     ;; Explanation: Creates a color element corresponding to perlin-noise value of the pixel at index.
     ;; Example: (create-pixel 0) -> (make-color ....)
     (define (create-pixel index)
       (local
         ((define x (/ (modulo index WIDTH) SCALE))
          (define y (/ (floor (/ index WIDTH)) SCALE)))
         (begin
           (if (or (>= x (+ cur-x 0.5)) (= 0 x)) (inc-x! x) (if (>= y (+ cur-y 0.5)) (inc-y!) false)) ;; Borderline magic - keeps cached gradients fresh
           (number2color (perlin-noise-on-steroids x y cur-top-left cur-top-right cur-bot-left cur-bot-right))
         )
       )
     )
    )
    (build-list (* width height) create-pixel)
  )
)
;; Tests
;; (not specified)
;;(save-image (color-list->bitmap (create-land-and-hurry WIDTH HEIGHT) WIDTH HEIGHT) "t2.png")