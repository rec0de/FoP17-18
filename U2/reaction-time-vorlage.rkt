(define-struct person (age sex code))

(define-struct subject (person times))

(define VP01 (make-subject (make-person 22 'm 'MW17K) (list 220 301 189 272 311)))
(define VP02 (make-subject (make-person 25 'f 'MP25G) (list 234 197 253 257 206)))
(define VP03 (make-subject (make-person 23 'f 'CT03R) (list 197 202 214 222 233)))
(define VP04 (make-subject (make-person 20 'm 'MM09R) (list 273 314 257 264 217)))
(define VP05 (make-subject (make-person 19 'm 'KR22I) (list 198 197 228 253 199)))
(define VP06 (make-subject (make-person 26 'm 'FR01B) (list 212 204 289 294 223)))
(define VP07 (make-subject (make-person 28 'f 'RA15R) (list 258 323 189 247 303)))
(define VP08 (make-subject (make-person 22 'm 'RP18R) (list 221 307 182 271 316)))
(define VP09 (make-subject (make-person 24 'f 'GH31W) (list 230 295 304 264 237)))
(define VP10 (make-subject (make-person 19 'f 'OM29Q) (list 299 194 242 303 243)))

(define subjects (list VP01 VP02 VP03 VP04 VP05 VP06 VP07 VP08 VP09 VP10))

;; Exercise 6.1

;; youngest-oldest-subject:
;; Explanation:
;; Example:
(define (youngest-oldest-subject people comparator)
  
)

;; Tests

;; Exercise 6.2

;; balanced-sex:
;; Explanation:
;; Example:
(define (balanced-sex people)
  
 )

;; Tests

;; Exercise 6.3


;; mean-of-subject:
;; Explanation:
;; Example:
(define (mean-of-subject people code)

  )

;; Tests


;; Exercise 6.4


;; std-of-subject:
;; Explanation:
;; Example:
(define (std-of-subject people code)
  
  )

;; Tests
