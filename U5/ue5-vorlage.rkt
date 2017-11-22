;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname perlinnoise) (read-case-sensitive #t) (teachpacks ((lib "image.rkt" "teachpack" "2htdp"))) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ((lib "image.rkt" "teachpack" "2htdp")) #f)))
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

(define (create-gradients n)

  )


;; fade: number -> number
;; Uses a Sigmoid function to smooth numbers betwenn 0 to 1
;; Example: (fade 1) -> 1
(define (fade t)
  (+ (- (* 6 t t t t t) (* 15 t t t t)) (* 10 t t t))
  )

(check-expect (fade 1) 1)
(check-within (fade 0.5) 0.5 0.001)
(check-within (fade 0.3) 0.16308 0.001)
(check-within (fade 0.8) 0.94208 0.001)

;; Globally store the list of gradients
(define gradients (create-gradients (* (+ 1 GWIDTH) (+ 1 GHEIGHT))))


(define (linear-interpolation x y w)
      
  )



(define (dot-grid-gradient ix iy x y)
  
  )


(define (perlin-noise x y)
  
  )

(define (create-land width height)
  
  )

;(save-image (color-list->bitmap (create-land WIDTH HEIGHT) WIDTH HEIGHT) "land.png")
