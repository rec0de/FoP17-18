;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname pnpCalculater) (read-case-sensitive #t) (teachpacks ((lib "gui.rkt" "teachpack" "htdp"))) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ((lib "gui.rkt" "teachpack" "htdp")) #f)))
;; Available Dice | Available Attributes
;; w4 1-4         | %CO Constitution 
;; w6 1-6         | %RE Reaction     
;; w8 1-8         | %ST Strength     
;; w10 1-10       | %IN Intelligence 
;; w12 1-12       | %CH Charisma     
;; w20 1-20       |
;; w100 1-100     |

;; pnp-tree
;; op - The operator used to combine left and right
;; left - A pnp-expression or a pnp-tree
;; right - A pnp-expression or a pnp-tree
;; pnp-expression:
;; A pnp-expression can be one of the following four things: number, dice, attribute or pnp-tree
(define-struct pnp-tree (op left right))

;; the attribute-list used in our tests
(define attributes '(
                     (%CO 2)
                     (%RE 3)
                     (%ST 4)
                     (%IN 6)
                     (%CH 1)))


(define (eval-expression operator n1 n2)
  
  )


(define (eval-pnp-expression pnp-exp attr-values)

  )

(check-random (eval-pnp-expression 'w100 '()) (+ 1 (random 100)))
(check-expect (eval-pnp-expression (make-pnp-tree '+ 2 2) attributes) 4)


(define (eval-pnp-tree pnp-tree attr-values)
  
  )

(check-expect (eval-pnp-tree (make-pnp-tree '+ 2 2) attributes) 4)


(define (list->pnp-tree los)
  
  )

(check-expect (list->pnp-tree '(+ 2 (* 3 3))) (make-pnp-tree '+ 2 (make-pnp-tree '* 3 3)))



(define (combine-strings los)
 
  )

(check-expect (combine-strings (list "3" "3" "+" "4" "-" "w" "2" "0")) (list "33" "+" "4" "-" "w20"))

(define (infix->prefix str)
  
  )

(check-expect (infix->prefix "1+2-3*4+5") (list '+ 1 '- 2 '+ '* 3 4 5))


(define (prefix->list los)
  
  )

(check-expect (prefix->list (list '+ '* '%IN 'w4 5)) (list '+ (list '* '%IN 'w4) 5))

;; The following code generates the GUI.
(define formula (make-text "Geben Sie die Formel ein: "))
(define explenation (make-message "In der Formel können folgende Elemente vorkommen:
Ganze Zahlen,+,-,*,/,
w4,w6,w8,w10,w12,w20,w100
,%CO,%ST,%IN,%RE,%CH"))
(define result (make-message ""))
(define (respond e)
  (draw-message result (number->string (eval-pnp-tree (list->pnp-tree (prefix->list (infix->prefix (text-contents formula)))) attributes)))
  )

(define w (create-window
           (list
            (list explenation)
            (list formula)
            (list result)
            (list
             (make-button "Berechnen" respond)
             (make-button "QUIT" (lambda (e) (hide-window w)))))))
(show-window w)