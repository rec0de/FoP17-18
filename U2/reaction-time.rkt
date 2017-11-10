;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-abbr-reader.ss" "lang")((modname reaction-time) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
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

;; Helper functions

;; Re-implementing max and min to avoid forced conversion to float when comparing with +inf

;; math-min: number number -> number
;; Explanation: Returns the smaller of two given numbers
;; Example: (math-min 8 4) -> 4
(define (math-min a b)
  (if (< a b) a b)
)

;; Tests
(check-expect (math-min 500 499) 499)
(check-expect (math-min -3.14 -4.2) -4.2)

;; math-max: number number -> number
;; Explanation: Returns the larger of two given numbers
;; Example: (math-max 8 4) -> 8
(define (math-max a b)
  (if (< a b) b a)
)

;; Tests
(check-expect (math-max 500 499) 500)
(check-expect (math-max -3.14 -4.2) -3.14)

;; gender-to-number: subject -> number
;; Explanation: Returns -1 if given subject is male (or unrecognized), 1 if subject is female
;; Example: (gender-to-number VP02) -> 1
(define (gender-to-number input-subject)
  (if (symbol=? (person-sex (subject-person input-subject)) 'f) 1 -1)
)

;; Tests
(check-expect (gender-to-number VP01) -1)
(check-expect (gender-to-number VP02) 1)

;; list-sum: (listof number) -> number
;; Explanation: Calculates sum of given list of numbers
;; Example: (list-sum (list 10 1 1 4)) -> 16
(define (list-sum input)
  (if (empty? input) 0 (+ (first input) (list-sum (rest input))))
)

;; Tests
(check-expect (list-sum (list 1 2 3)) 6)
(check-expect (list-sum (list 100 20 500 4)) 624)

;; sum-squared-diffs: (listof number) number -> number
;; Explanation: Caluclates square of difference of each list value to reference value and returns sum of all squared differences
;; Example: (sum-squared-diffs (list 1 2 3 4) 2) -> 6
(define (sum-squared-diffs numbers mean)
  (if (empty? numbers) 0 (+ (sqr (- (first numbers) mean)) (sum-squared-diffs (rest numbers) mean)))
)

;; Tests
(check-expect (sum-squared-diffs empty 123) 0)
(check-expect (sum-squared-diffs (list 5 6 7) 0) 110)
(check-within (sum-squared-diffs (list 1.2 3 100) 50) 7090.44 0.0001)

;; mean-of-given: subject -> number
;; Explanation: Calculates average reaction time of a given subject
;; Example: (mean-of-given VP07) -> 264
(define (mean-of-given subject)
  (/ (list-sum (subject-times subject)) (length (subject-times subject)))
)

;; Tests
(check-within (mean-of-given VP01) 258.6 0.0001)
(check-expect (mean-of-given VP07) 264)

;; std-of-given: subject -> number
;; Explanation: Calculates standart deviation of reaction times given subject
;; Example: (std-of-given VP01) -> ~52.577
(define (std-of-given subject)
  (sqrt (/ (sum-squared-diffs (subject-times subject) (mean-of-given subject)) (- (length (subject-times subject)) 1)))
)

;; Tests
(check-within (std-of-given VP07) 52.3259 0.001)
(check-within (std-of-given VP03) 14.63899 0.001)

;; gender-overhang: (listof subject) -> number
;; Explanation: Returns difference in number of female to male subjects - positive n means n more female subjects, negative n means n more male subjects. 0 if male and female are balanced
;; Example: (gender-overhang (list VP01 VP02 VP03)) -> 1
(define (gender-overhang subjects)
  (cond
    [(empty? subjects) 0]
    [else (+ (gender-overhang (rest subjects)) (gender-to-number (first subjects)))]
  )
)

;; Tests
(check-expect (gender-overhang (list VP01 VP02)) 0)
(check-expect (gender-overhang (list VP02 VP03)) 2)
(check-expect (gender-overhang (list VP01 VP04 VP05 VP02)) -2)
(check-expect (gender-overhang empty) 0)

;; Exercise 6.1

;; youngest-oldest-subject: (listof subject) symbol -> number
;; Explanation: Returns age of oldest / youngest subject in list according to symbol 'youngest or 'oldest
;;              Returns oldest age 0 and youngest age +infinity for empty lists
;; Example: (youngest-oldest-subject subjects 'oldest) -> 28
(define (youngest-oldest-subject people comparator)
  (cond
    [(empty? people) (if (symbol=? comparator 'oldest) 0 +inf.0)]
    [else
      (if (symbol=? comparator 'oldest)
        (math-max (person-age (subject-person (first people))) (youngest-oldest-subject(rest people) 'oldest))
        (math-min (person-age (subject-person (first people))) (youngest-oldest-subject(rest people) 'youngest))
      )
    ]
  )
)

;; Tests
(check-expect (youngest-oldest-subject subjects 'oldest) 28)
(check-expect (youngest-oldest-subject subjects 'youngest) 19)
(check-expect (youngest-oldest-subject empty 'oldest) 0)

;; Exercise 6.2

;; balanced-sex: (listof subject) -> boolean
;; Explanation: Checks if gender distribution of given subject list is balaced. Returns true if number of male and female subjects is equal, false otherwise. Throws error on empty input
;; Example: (balanced-sex (list VP01 VP02 VP03)) -> false
(define (balanced-sex people)
  (if (empty? people) (error 'balanced-sex "no subjects existing") (zero? (gender-overhang people)))
)

;; Tests
(check-expect (balanced-sex (list VP01 VP02)) true)
(check-expect (balanced-sex (list VP02 VP03)) false)
(check-expect (balanced-sex (list VP01 VP04 VP05 VP02)) false)
(check-error (balanced-sex empty) "balanced-sex: no subjects existing")

;; Exercise 6.3

;; mean-of-subject: (listof subject) symbol -> number
;; Explanation: Scans list of subjects for subject with matching ID code and calculates, if found, the average response time for that subject
;; Example: (mean-of-subject subjects 'MW17K) -> 258.6
(define (mean-of-subject people code)
  (cond
    [(empty? people) false] ;; Subject code not found in list
    ;; If subject code is found, return mean of subject
    [(symbol=? (person-code (subject-person (first people))) code) (mean-of-given (first people))]
    ;; Otherwise, check rest of list
    [else (mean-of-subject (rest people) code)]
  )
)

;; Tests
(check-within (mean-of-subject subjects 'MW17K) 258.6 0.0001)
(check-within (mean-of-subject subjects 'MP25G) 229.4 0.0001)

;; Exercise 6.4

;; std-of-subject: (listof subject) symbol -> number
;; Explanation: Scans list of subjects for subject with matching ID code and calculates, if found, the standard deviation of that subject's reaction times
;; Example: (std-of-subject subjects 'MW17K) -> ~52.577
(define (std-of-subject people code)
  (cond
    [(empty? people) false] ;; Subject code not found in list
    ;; If subject code is found, return stdd of subject
    [(symbol=? (person-code (subject-person (first people))) code) (std-of-given (first people))]
    ;; Otherwise, check rest of list
    [else (std-of-subject (rest people) code)]
  )
)

;; Tests
(check-within (std-of-subject subjects 'RA15R) 52.3259 0.001)
(check-within (std-of-subject subjects 'CT03R) 14.63899 0.001)
(check-expect (std-of-subject subjects 'doesntexist) false)

