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
(define GWIDTH (/ WIDTH SCALE))
;; The height of the grid
(define GHEIGHT (/ HEIGHT SCALE))

;; Defines a 2D Vector
;; x - The x-value of the vector
;; y - The y-value of the vector
(define-struct vector2d (x y))

;; create-gradients: number -> (listof vector2d)
;; Explanation:
;; Example:
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
(define gradients (create-gradients (* (+ 1 GWIDTH) (+ 1 GHEIGHT))))


;; linear-interpolation: number number number -> number
;; Explanation:
;; Example:
(define (linear-interpolation x y w)
 (+ (* (- 1 w) x) (* w y))    
)
;; Tests
(check-expect (linear-interpolation 2 4 0.5) 3)
(check-expect (linear-interpolation 3 500 0) 3)
(check-expect (linear-interpolation 5 10 0.1) 5.5)



(define (dot-grid-gradient ix iy x y)
  true
)


(define (perlin-noise x y)
  true
)

(define (create-land width height)
  true
)

;(save-image (color-list->bitmap (create-land WIDTH HEIGHT) WIDTH HEIGHT) "land.png")
