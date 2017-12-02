;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname pnpCalculator) (read-case-sensitive #t) (teachpacks ((lib "gui.rkt" "teachpack" "htdp"))) (htdp-settings #(#t constructor repeating-decimal #t #t none #f ((lib "gui.rkt" "teachpack" "htdp")) #f)))
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

;; eval-expression: symbol number number -> number
;; Explanation: Returns result of multipication/addition/subtraction/division of two numbers given the proper operand symbol
;; Example: (eval-expression + 1 2) -> 3
(define (eval-expression operator n1 n2)
  (cond
    [(symbol=? operator '+) (+ n1 n2)]
    [(symbol=? operator '-) (- n1 n2)]
    [(symbol=? operator '*) (* n1 n2)]
    [(symbol=? operator '/) (/ n1 n2)]
    [else (error 'eval-expression "unknown operation")]
  )
)
;; Tests
(check-expect (eval-expression '+ -5 -1) -6)
(check-expect (eval-expression '+ 4 3) 7)
(check-expect (eval-expression '- 1338 1) 1337)
(check-expect (eval-expression '* 0.5 84) 42)
(check-expect (eval-expression '/ 1 4) 0.25)
(check-error (eval-expression '^ 2 10) "eval-expression: unknown operation")


;; dice: number -> number
;; Explanation: Simulates a dice roll with given number of sides
;; Example: (dice 6) -> 5
(define (dice sides)
  (inexact->exact (ceiling (* (random) sides)))
)
;; Tests
(check-random (dice 6) (+ 1 (random 6)))
(check-random (dice 20) (+ 1 (random 20)))


;; eval-pnp-expression: pnp-expression (listof (list symbol number)) -> number
;; Explanation: Evaluates a pnp-expression to its numeric value recursively, replacing attributes with the supplied values
;; Example: (eval-pnp-expression 'w6 '()) -> 4 [= (dice 6)]
(define (eval-pnp-expression pnp-exp attr-values)
  (local
    ;; find-attr: (listof (listof symbol X)) -> X
    ;; Explanation: Returns the second element of the list in the given list that has needle as its first element
    ;; Example: (find-attr 'hereiam '((whereisit 5)(cantfind 7)(hereiam 2))) -> 2
    ((define (find-attr needle haystack)
       (cond
         [(empty? haystack) (error 'find-attr "couldn't find attribute value")]
         [(symbol=? (first (first haystack)) needle) (second (first haystack))]
         [else (find-attr needle (rest haystack))]
       )
    ))
    (cond
      ;; Number
      [(number? pnp-exp) pnp-exp]
      ;; Tree recursion
      [(pnp-tree? pnp-exp) (eval-expression (pnp-tree-op pnp-exp) (eval-pnp-expression (pnp-tree-left pnp-exp) attr-values) (eval-pnp-expression (pnp-tree-right pnp-exp) attr-values))]
      ;; Catch invalid inputs
      [(not (symbol? pnp-exp)) (error 'eval-pnp-expression "unrecognized expression")]
      ;; Dice
      [(symbol=? pnp-exp 'w4) (dice 4)]
      [(symbol=? pnp-exp 'w6) (dice 6)]
      [(symbol=? pnp-exp 'w8) (dice 8)]
      [(symbol=? pnp-exp 'w10) (dice 10)]
      [(symbol=? pnp-exp 'w12) (dice 12)]
      [(symbol=? pnp-exp 'w20) (dice 20)]
      [(symbol=? pnp-exp 'w100) (dice 100)]
      ;; Attribute
      [(symbol? pnp-exp) (find-attr pnp-exp attr-values)]
    )
  )
)
;; Tests
(check-random (eval-pnp-expression 'w100 '()) (+ 1 (random 100))) ;; given testcase (kinda restricts number generation though :/)
(check-expect (eval-pnp-expression (make-pnp-tree '+ 2 2) attributes) 4) ;; given testcase
(check-expect (eval-pnp-expression 5 attributes) 5)
(check-random (eval-pnp-expression (make-pnp-tree '* '%CH 'w20) attributes) (+ 1 (random 20)))
(check-random (eval-pnp-expression (make-pnp-tree '* '%CO (make-pnp-tree '+ 'w12 'w10)) attributes) (* 2 (+ (+ 1 (random 12) (+ 1 (random 10))))))
(check-error (eval-pnp-expression "crashandburn" attributes) "eval-pnp-expression: unrecognized expression")
(check-error (eval-pnp-expression 'falltopieces! attributes) "find-attr: couldn't find attribute value")

;; eval-pnp-tree: pnp-tree (listof (list symbol number)) -> number
;; Explanation: Evaluates a given pnp-tree to its numerical value, replacing attributes with given values
;;              Technically a stricter version of eval-pnp-expression that does not allow non-tree pnp-expressions (on paper, this one handles them the same way eval-pnp-expression does)
;; Example: (eval-pnp-tree (make-pnp-tree '* '%CH 6)) -> 6
(define (eval-pnp-tree pnp-tree attr-values)
  (eval-pnp-expression pnp-tree attr-values) ;; I don't really get how these functions are supposed to be different, but here you go
)
;; Tests
(check-expect (eval-pnp-tree (make-pnp-tree '+ 2 2) attributes) 4) ;; given testcase
(check-random (eval-pnp-tree (make-pnp-tree '* '%CH 'w20) attributes) (+ 1 (random 20)))
(check-random (eval-pnp-tree (make-pnp-tree '* '%CO (make-pnp-tree '+ 'w12 'w10)) attributes) (* 2 (+ (+ 1 (random 12) (+ 1 (random 10))))))
(check-expect (eval-pnp-tree (make-pnp-tree '* 10 (make-pnp-tree '+ 5 -4)) attributes) 10)
(check-expect (eval-pnp-tree (make-pnp-tree '* '%ST (make-pnp-tree '- '%CO 4)) attributes) -8)

;; list->pnp-tree: (list of symbol symbol/number/list symbol/number/list) -> pnp-tree
;; Explanation: Converts a list of lists with lists consisting of an operatorsymbol  and two operator values (number/symbol/list) to a corresponding pnp-tree
;;              Error on empty input
;; Example: (list->pnp-tree '(+ 1 w4)) -> (make-pnp-tree '+ 1 'w4)
(define (list->pnp-tree los)
  (cond
    [(empty? los) (error 'list->pnp-tree "can't create empty pnp-tree")]
    [(not (list? los)) los]
    [else (make-pnp-tree (first los) (list->pnp-tree (second los)) (list->pnp-tree (third los)))]
  )
)
;; Tests
(check-expect (list->pnp-tree '(+ 2 (* 3 3))) (make-pnp-tree '+ 2 (make-pnp-tree '* 3 3))) ;; given testcase
(check-expect (list->pnp-tree '(* %CH (+ w8 (- 1 1)))) (make-pnp-tree '* '%CH (make-pnp-tree '+ 'w8 (make-pnp-tree '- 1 1))))
(check-expect (list->pnp-tree '(/ (+ %CO 6) w6)) (make-pnp-tree '/ (make-pnp-tree '+ '%CO 6) 'w6))
(check-error (list->pnp-tree '()) "list->pnp-tree: can't create empty pnp-tree")

;; valid-operator?: symbol or string -> boolean
;; Explanation: Checks if a given string or symbol represents a supported operator (*+/-) and returns true/false accordingly
;; Example: (valid-operator? '/) -> true
(define (valid-operator? input)
  (cond
    [(symbol? input) (or (symbol=? input '+) (symbol=? input '*) (symbol=? input '/) (symbol=? input '-))]
    [(string? input) (or (string=? input "+") (string=? input "*") (string=? input "/") (string=? input "-"))]
    [else false]
  )
)
;; Tests
(check-expect (valid-operator? '+) true)
(check-expect (valid-operator? "/") true)
(check-expect (valid-operator? 7) false)
(check-expect (valid-operator? '._.) false)

;; combine-strings: (listof string) -> (listof string)
;; Explanation:
;; Example: (combine-strings (list "t" "h" "r" "o" "w" "+" "a" "w" "a" "y")) -> (list "throw" "+" "away")
(define (combine-strings los)
  (local
    ;; combine: (listof string) (listof string) string -> (listof string)
    ;; Explanation:
    ;; Example: (combine (list "t" "h" "r" "o" "w" "+" "a" "w" "a" "y")) -> (list "away" "+" "throw")
    ((define (combine todo res cache)
       (cond
         [(empty? todo) (cons cache res)]
         [(valid-operator? (first todo)) (combine (rest todo) (cons (first todo) (cons cache res)) "")]
         [else (combine (rest todo) res (string-append cache (first todo)))]
       )
    ))
    (reverse (combine los empty ""))
  )
)
;; Tests
(check-expect (combine-strings (list "3" "3" "+" "4" "-" "w" "2" "0")) (list "33" "+" "4" "-" "w20")) ;; given testcase
(check-expect (combine-strings (explode "%CO+w20")) (list "%CO" "+" "w20"))
(check-expect (combine-strings (explode "1ts*a!!-true")) (list "1ts" "*" "a!!" "-" "true"))



;; infix->prefix: string
;; Explanation:
;; Example:
(define (infix->prefix str)
  (local
    ;; Split string into characters, combine words between operators, reverse result
    ((define combined (reverse (combine-strings (explode str))))
     
     ;; higher-precedence: string string -> boolean
     ;; Explanation: Returns true if the first operator (given as string) has higher or equal operator precedence compared to the second, false otherwise
     ;; Example: (higher-or-equal-precedence "*" "+") -> true
     (define (higher-or-equal-precedence op1 op2)
       (or (string=? op1 "*") (string=? op1 "/") (and (or (string=? op1 "+") (string=? op1 "-")) (or (string=? op2 "+") (string=? op2 "-"))))
     )
     
     ;; string2symbol: string -> symbol or number
     ;; Explanation: Converts a string to its corresponding symbol, or, if it can be interpreted as a number, a number
     ;; Example: (string2symbol "1.3") -> 1.3
     (define (string2symbol string)
       (if (boolean? (string->number string)) (string->symbol string) (string->number string)) ;; string->number returns false if conversion is impossible
     )
     
     ;; reorder: (listof string) (listof string) (listof string) -> (listof string)
     ;; Explanation:
     ;; Example:
     (define (reorder input stack output)
       (cond
         ;; Return output and stack on completition
         [(empty? input) (append output stack)]
         ;; Parse operators
         [(valid-operator? (first input))
          (if (and (not (empty? stack)) (higher-or-equal-precedence (first stack) (first input))) ;; if stack is not empty and first stack operator has higher precedence than input operator
              (reorder input (rest stack) (append output (list (first stack)))) ;; Add prioritized stack operator to output, keep input operator in position to allow multiple stack operators to be written
              (reorder (rest input) (cons (first input) stack) output) ;; Write prioritized operator to stack
          )
         ]
         ;; Append non-operator input to output
         [else (reorder (rest input) stack (append output (list (first input))))]
       )
     )
    )
    ;; Reverse again and convert to symbols
    (map string2symbol (reverse (reorder combined empty empty)))
  )
)
;; Tests
(check-expect (infix->prefix "1+2-3*4+5") (list '+ 1 '- 2 '+ '* 3 4 5)) ;; given testcase
(check-expect (infix->prefix "f*t+n*h") (list '+ '* 'f 't '* 'n 'h))


;; prefix->list: (listof symbol/number) -> (listof symbol number/symbol/list number/symbol/list)
;; Explanation:
;; Example:
(define (prefix->list los)
  (local
    ;; pack: (listof symbol/number) (listof (listof symbol number/symbol/list)) -> (listof symbol number/symbol/list number/symbol/list)
    ;; Explanation:
    ;; Example:
    ((define (pack los stack)
       (cond
         [(and (empty? los) (= (length stack) 1)) (first stack)] ;; Return first stack element on completition
         ;; If current first stack element is saturated, add it as argument for second stack element
         [(and (not (empty? stack)) (= (length (first stack)) 3)) (pack los (cons (append (second stack) (list (first stack))) (rest (rest stack))))]
         ;; If operator is read, open new stack element for operator
         [(valid-operator? (first los)) (pack (rest los) (cons (list (first los)) stack))]
         ;; If non-operator is read, add as argument to topmost stack element
         [else (pack (rest los) (cons (append (first stack) (list (first los))) (rest stack)))]
       )
    ))
    ;; Convert single operand to equivalent list if necessary
    (if (= 1 (length los)) (list '+ (first los) 0) (pack los empty))
  )
)
;; Tests
(check-expect (prefix->list (list '+ '* '%IN 'w4 5)) (list '+ (list '* '%IN 'w4) 5)) ;; given testcase
(check-expect (prefix->list '(+ + + 7 3 4 * 2 3)) '(+ (+ (+ 7 3) 4) (* 2 3)))
(check-expect (prefix->list '(* + 1 9 - 4 + 1 1)) '(* (+ 1 9) (- 4 (+ 1 1))))
(check-expect (prefix->list '(w4)) '(+ w4 0))

;; The following code generates the GUI.
(define formula (make-text "Geben Sie die Formel ein: "))
;; (sic)
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