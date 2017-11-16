;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname ue4) (read-case-sensitive #t) (teachpacks ((lib "image.rkt" "teachpack" "2htdp"))) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ((lib "image.rkt" "teachpack" "2htdp")) #f)))
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
  (image->color-list (bitmap/file path-to-image))
)

;; store-image: (list of color) string number number -> boolean
;; Saves the image in PNG format at path-to-image.
;; loc: List of color which is supposed be stored in PNG format.
;; path-to-image: Relative path to the location where the image should be saved.
;; width: The width of the image.
;; heigth: The height of the image. 
(define (store-image loc path-to-image width height)
  (save-image (color-list->bitmap loc width height) path-to-image)
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; steganographie-enc helper functions
;; not using local here to avoid having a huge block of untestable code

;; quick-contains: number (listof number) -> boolean
;; Explanation: Expects a _sorted_ list of numbers and returns true if given number is in list (comparably fast)
;; Example: (quick-contains 2 (list 1 2 3)) -> true
(define (quick-contains num lon)
  (cond
    [(empty? lon) false]
    [(< num (first lon)) false]
    [(= num (first lon)) true]
    [else (quick-contains num (rest lon))]
  )
)
(check-expect (quick-contains 3 (list 1 2 5 7 8 9 384)) false)
(check-expect (quick-contains 50 (list 3 11 23 42 50 51 77)) true)
(check-expect (quick-contains 1337 empty) false)

;; dedupe: (listof X) (X X -> boolean) -> (listof X)
;; Explanation: Removes duplicates from a given list using the specified operator to determine equality
;; Example: (dedupe (list 1 1 2 3 3 4) =) -> (list 1 2 3 4)
(define (dedupe rawlist eqop)
  (local
    ;; contains? : X (listof X) (X X -> boolean) -> boolean
    ;; Explanation:
    ;; Example:
    ((define (contains? needle haystack eqop)
       ;; Lambda: X -> boolean
       ;; Explanation:
       ;; Example:
       (not (empty? (filter (lambda (compare) (eqop needle compare)) haystack)))
    ))
    (cond
      [(empty? rawlist) empty]
      [(contains? (first rawlist) (rest rawlist) eqop) (dedupe (rest rawlist) eqop)]
      [else (cons (first rawlist) (dedupe (rest rawlist) eqop))]
    )
  )
)
(check-expect (dedupe (list 1 1 1 1) =) (list 1))
(check-expect (dedupe (list 1 2 3 4 5 2) =) (list 1 3 4 5 2))

;; normalize-pw: string -> (listof number)
;; Explanation: Turns password into sorted, deduped list of numbers corresponding to positions of password letters in the alphabet
;; Example: (normalize-pw "fabcab") -> (list 0 1 2 5)
(define (normalize-pw pw)
  (sort (dedupe (map char-to-int (explode pw)) =) <)
)
(check-expect (normalize-pw "fabcab") (list 0 1 2 5))
(check-expect (normalize-pw "zAZy") (list 0 24 25))

;; char-in-color: color (listof number) -> color
;; Explanation:
;; Example:
(define (char-in-color col char)
  (local
    ((define r (color-red col))
     (define g (color-green col))
     (define b (color-blue col))
     (define a (color-alpha col))
     ;; rnd: number -> number
     ;; Explanation: Rounds last two bits to zero TODO: more info
     ;; Example: (rnd 255) -> 252
     (define (rnd byte)
       (- byte (modulo byte 4))
     ))
     (make-color (+ (rnd r) (first char)) (+ (rnd g) (second char)) (+ (rnd b) (third char)) (+ (rnd a) (fourth char)))
  )
)
(check-expect (char-in-color (make-color 255 255 255 255) (list 0 1 2 3)) (make-color 252 253 254 255))
(check-expect (char-in-color (make-color 0 0 0 0) (list 3 1 2 0)) (make-color 3 1 2 0))

;; char-from-color: color -> (listof number)
;; Explanation:
;; Example:
(define (char-from-color col)
  (local
    ((define r (color-red col))
     (define g (color-green col))
     (define b (color-blue col))
     (define a (color-alpha col)))
    (list (modulo r 4) (modulo g 4) (modulo b 4) (modulo a 4))
  )
)
(check-expect (char-from-color (make-color 252 253 254 255)) (list 0 1 2 3))
(check-expect (char-from-color (make-color 3 1 2 0)) (list 3 1 2 0))

;; steganographie-enc: (listof color) string string -> (listof color)
;; Explanation:
;; Example:
(define (steganographie-enc loc m k)
  (local
    (;; padd-to-four: (listof number) -> (listof number)
     ;; Explanation:
     ;; Example:
     (define (padd-to-four lst)
       (cond
         ;; Not gonna make that recursive...
         [(> (length lst) 4) (error 'padd-to-four "padding to four needs list with =< 4 elements")]
         [(= (length lst) 4) lst]
         [(= (length lst) 3) (cons 0 lst)]
         [(= (length lst) 2) (append (list 0 0) lst)]
         [(= (length lst) 1) (append (list 0 0 0) lst)]
         [else (error 'padd-to-four "attempting to padd empty list")]
       )
     )
    
     ;; process-pixels: (listof color) number (listof (listof number)) -> (listof color)
     ;; Explanation:
     ;; Example:
     (define (process-pixels loc index message)
       (cond
         [(empty? loc) (if (empty? message) empty (error 'steganographie-enc "message too long (inner check failed)"))] ;; If I got this right it should never happen...
         [(empty? message) loc] ;; If message is fully processed, rest of the image remains unchanged
         [(quick-contains index password)
          (cons (char-in-color (first loc) (first message)) (process-pixels (rest loc) (modulo (+ index 1) 26) (rest message)))]
         [else (cons (first loc) (process-pixels (rest loc) (modulo (+ index 1) 26) message))]
       )
     )
     (define password (normalize-pw k))
     (define msg (map padd-to-four (string->encodeable m))))
    (cond
      [(empty? loc) (error 'steganographie-enc "input image data is empty")]
      [(< (length loc) (* (/ (length msg) (length password)) 26)) (error 'steganographie-enc "message too long")]
      [(not (equal? (first (reverse msg)) (list 0 1 2 3))) (error 'steganographie-enc "message not terminated properly")]
      [else (process-pixels loc 0 msg)]
    )
  )
)

;; Tests
(check-error (steganographie-enc empty "" "") "steganographie-enc: input image data is empty")
(check-error (steganographie-enc (list (make-color 0 0 0 0)) "Hi!" "password") "steganographie-enc: message too long")
(check-error (steganographie-enc (list (make-color 0 0 0) (make-color 0 0 0) (make-color 0 0 0) (make-color 0 0 0)) "a" "abcdefghijklmnop") "steganographie-enc: message not terminated properly")


(define (steganographie-dec loc k)
  (local
    (;; process-pixels: (listof color) number -> (listof (listof number))
     ;; Explanation:
     ;; Example:
     (define (process-pixels loc index)
       (cond
         [(empty? loc) empty]
         [(quick-contains index password)
          (if (equal? (char-from-color (first loc)) (list 0 1 2 3))
              (list (char-from-color (first loc))) ;; Abort on \e, message is completed
              (cons (char-from-color (first loc)) (process-pixels (rest loc) (modulo (+ index 1) 26)))
          )
         ]
         [else (process-pixels (rest loc) (modulo (+ index 1) 26))]
       )
     )
     (define password (normalize-pw k)))
    (cond
      [(empty? loc) (error 'steganographie-dec "input image data is empty")]
      [else (encodeable->string (process-pixels loc 0))]
    )
  )
)

;; Tests
(check-error (steganographie-dec empty "") "steganographie-dec: input image data is empty")
(check-expect (steganographie-dec (load-image "chimera.png") "wisdom") "Chaos is Order yet undeciphered.\e")

;; Recovered Message
;; Suchen Sie sich ein Bild um Ihren Namen, wie er in Moodle steht, in diesem Bild zu verstecken. Benutzen Sie als Passwort "encrypt". Geben Sie das Bild und Ihren Code als ein Zip-Archive ab.
