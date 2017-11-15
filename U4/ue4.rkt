;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname ue4) (read-case-sensitive #t) (teachpacks ((lib "image.rkt" "teachpack" "htdp"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "image.rkt" "teachpack" "htdp")) #f)))
;; The alphabet we use is encoded in eight bits and the numbers correspond to ASCII symbols.
;; ASCII number 27 "\e" is used as termination symbol.

;; convert-to-base-four: number -> (listof number)
;; Explanation: Converts positive (strictly > 0) base10 integer numbers to their base4 representation, returned as a list of numbers representing single digits
;; Example: (convert-to-base-four 4) -> (list 1 0)
(define (convert-to-base-four num)
  (cond
    [(zero? num) empty]
    [else (append (convert-to-base-four (quotient num 4)) (list (modulo num 4)))]
  )
)

;; Tests
(check-expect (convert-to-base-four 10) (list 2 2)) ;; pre-defined
(check-expect (convert-to-base-four 100) (list 1 2 1 0)) ;; pre-defined
(check-expect (convert-to-base-four 812) (list 3 0 2 3 0))
;;(check-expect (convert-to-base-four 0) (list 0))



;; convert-from-base-four: (listof number) -> number
;; Explanation:
;; Example: (convert-from-base-four (list 1 0)) -> 4
(define (convert-from-base-four base4)
  ;; Lambda: number, number -> number
  ;; Explanation:
  ;; Example:
  (foldl (lambda (digit num) (+ (* 4 num) digit)) 0 base4) ;; Reads most significant number first, then multiplies already converted part by 4, 'shifting' by one b4 digit
)

;; Tests
(check-expect (convert-from-base-four (list 3 0 2 3 0)) 812)
(check-expect (convert-from-base-four (list 0)) 0)
(check-expect (convert-from-base-four empty) 0)



;; string->encodeable: string -> (listof (listof number))
;; Explanation:
;; Example: (string->encodeable "ABC") -> (list (list 1 0 0 1) (list 1 0 0 2) (list 1 0 0 3))
(define (string->encodeable s)
  ;; Lambda: string -> (listof number)
  ;; Explanation:
  ;; Example: A -> (list 1 0 0 1)
  (map (lambda (s) (convert-to-base-four (string->int s))) (explode s))
)

;; Tests
(check-expect (string->encodeable "fop") (list (list 1 2 1 2) (list 1 2 3 3) (list 1 3 0 0))) ;; pre-defined
(check-expect (string->encodeable "FOP") (list (list 1 0 1 2) (list 1 0 3 3) (list 1 1 0 0))) ;; pre-defined
(check-expect (string->encodeable "F4ncY S#1t") (list (list 1 0 1 2) (list 3 1 0) (list 1 2 3 2) (list 1 2 0 3) (list 1 1 2 1) (list 2 0 0) (list 1 1 0 3) (list 2 0 3) (list 3 0 1) (list 1 3 1 0)))
(check-expect (string->encodeable "") empty)



;; encodable->string: (listof (listof number)) -> string
;; Explanation:
;; Example: 
(define (encodeable->string lon)
  (implode (map int->string (map convert-from-base-four lon))) ;; Two maps are not worth the lambda, I guess?
)

;; Tests
(check-expect (encodeable->string (list (list 1 2 1 2) (list 1 2 3 3) (list 1 3 0 0))) "fop") ;; pre-defined
(check-expect (encodeable->string (list (list 1 0 1 2) (list 1 0 3 3) (list 1 1 0 0))) "FOP") ;; pre-defined
(check-expect (encodeable->string (list (list 1 0 0 1) (list 1 0 0 2) (list 1 0 0 3))) "ABC")
(check-expect (encodeable->string (list (list 3 0 1) (list 1 3 0 1) (list 3 2 0) (list 3 1 0))) "1q84")
(check-expect (encodeable->string empty) "")



;; load-image: string -> (list of color)
;; Loads the image given by path-to-image and converts it to a list of color.
;; Example: (load-image "example.png") -> (list (color 128 255 32 255) ...)
(define (load-image path-to-image)
  true
;;  (image->color-list (bitmap/file path-to-image))
  )

;; store-image: (list of color) string number number -> boolean
;; Saves the image in PNG format at path-to-image.
;; loc: List of color which is supposed be stored in PNG format.
;; path-to-image: Relative path to the location where the image should be saved.
;; width: The width of the image.
;; heigth: The height of the image. 
(define (store-image loc path-to-image width height)
  true
;;  (save-image (color-list->bitmap loc width height) path-to-image)
  )



;; char-to-int: string -> number
;; Explanation: Converts a single character to its position in the alphabet, starting at A = 0. Case is ignored
;; Example:(char-to-int "b") -> 1
(define (char-to-int char)
  (if (> (string->int char) 94) (- (string->int char) 97) (- (string->int char) 65))
)

;; Tests
(check-expect (char-to-int "a") 0)
(check-expect (char-to-int "B") 1)
(check-expect (char-to-int "z") 25)

;; steganographie-enc:
;; Explanation:
;; Example:
(define (steganographie-enc loc m k)
  (local
    ((define password k)
     (define msg m)
     ;; normalize-pw: string -> (listof number)
     ;; Explanation: Turns password into sorted, deduped list of numbers corresponding to positions of password letters in the alphabet
     ;; Example: (normalize-pw "fabcab") -> (list 0 1 2 4)
     (define (normalize-pw pw)
       (sort (map char-to-int (explode pw)) <)
     ))
    (+ 1 1)
  )
)


(define (steganographie-dec loc k)
  true
)
