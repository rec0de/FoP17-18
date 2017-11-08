;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname supermarket-tree-template) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; product models a product of a supermarket
;; id: number - the product-id
;; name: symbol - name of the product
;; price: number - price of the product in Euro
;; categorie: symbol - the categorie which the product matches
(define-struct product (id name price categorie))

;; examples
(define apple (make-product 100 'apple 0.99 'fruit))
(define melon (make-product 105 'melon 4.49 'fruit))
(define orange (make-product 109 'orange 1.59 'fruit))
(define banana (make-product 111 'banana 1.29 'fruit))
(define cherry (make-product 113 'cherry 3.49 'fruit))

(define water (make-product 55 'water 0.5 'drink))
(define tea (make-product 57 'tea 0.99 'drink))
(define cola (make-product 60 'cola 1.29 'drink))


(define fruits (list apple melon cherry orange banana))
(define drinks (list tea water cola))
(define mixed (append fruits drinks))


;; A product-tree-node models one node of a product-tree. It is either
;; 1. empty or
;; 2. it contains:
;; left: product-tree-node - root of the left subtree, 
;;  product ids are smaller than the product id of the current product
;; right: product-tree-node - root of the right subtree, 
;;  product ids are bigger than the product id of the current product
;; prod: product - a product of the supermarket
(define-struct product-tree-node (left right prod))

;;example
(define example-tree (make-product-tree-node
                      (make-product-tree-node empty empty apple)
                      (make-product-tree-node empty empty orange)
                      melon))

;; ====== Problem 5.1 ======

;; insert-products:
;; 
;; example:
(define (insert-products products tree)




;; ====== Problem 5.2 ======
;; creation of predicates

;; categorie=?: 
;; 
;; example:
(define (categorie=? prod categ)


;; cheaper?: 
;; 
;; example:
(define (cheaper? prod value)





;; ====== Problem 5.3 ======

;; search-tree:
;;
;; example:
(define (search-tree tree predicate-value predicate)


;; ====== Problem 5.4 ======

;; product-list-cost: 
;;
;; example:
(define (product-list-cost tree grocery-list)
  
