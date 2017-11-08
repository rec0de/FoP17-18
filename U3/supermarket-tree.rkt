;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname supermarket-tree) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
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

;; own sample data for testing
(define chocolate (make-product 44 'chocolate 1.99 'sweets))
(define marshmallow (make-product 49 'marshmallow 1.25 'sweets))
(define cookie (make-product 42 'cookie 0.95 'sweets))
(define jellybean (make-product 47 'jellybean 3.10 'sweets))

(define potato (make-product 83 'potato 1.99 'veggie))
(define onion (make-product 81 'onion 2.56 'veggie))
(define cabbage (make-product 80 'cabbage 0.79 'veggie))
(define carrot (make-product 84 'carrot 1.00 'veggie))

(define salt (make-product 201 'salt 0.32 'spice))

(define sweets (list chocolate marshmallow cookie jellybean))
(define veggies (list potato onion cabbage carrot))
(define everything (append sweets veggies (list salt)))

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

;; Generate reference trees for tests

(define reftree-veggies (make-product-tree-node
 (make-product-tree-node (make-product-tree-node '() '() (make-product 80 'cabbage 0.79 'veggie)) '() (make-product 81 'onion 2.56 'veggie))
 (make-product-tree-node '() '() (make-product 84 'carrot 1 'veggie))
 (make-product 83 'potato 1.99 'veggie)))

;; ====== Problem 5.1 ======

;; insert-products: (listof product) product-tree-node -> product-tree-node
;; Explanation: Appends a list of products to a given product tree, keeping the tree structure intact. Duplicates are ignored. Creates a tree if input tree is empty
;; Example: (insert-products (list ProdA ProdB ProdC) empty) -> (make-product-tree-node (make-product-tree-node empty empty ProdA) (make-product-tree-node empty empty ProdB) ProdC)
(define (insert-products products tree)
  (local
    (
     ;; insert-single: product product-tree-node -> product-tree-node
     ;; Explanation: Inserts a single product to its correct position in the input tree. Tree is not modified if product with identical ID is already in tree
     ;; Example: (insert-single prodA (make-product-tree-node empty empty ProdB)) -> (make-product-tree-node (make-product-tree-node empty empty ProdA) empty ProdB)
     (define (insert-single product tree)
       (cond
         [(empty? tree) (make-product-tree-node empty empty product)]
         [(< (product-id product) (product-id (product-tree-node-prod tree))) (make-product-tree-node (insert-single product (product-tree-node-left tree)) (product-tree-node-right tree) (product-tree-node-prod tree))]
         [(> (product-id product) (product-id (product-tree-node-prod tree))) (make-product-tree-node (product-tree-node-left tree) (insert-single product (product-tree-node-right tree)) (product-tree-node-prod tree))]
         [else tree]
       )
     )
    )
    (cond
      [(empty? products) tree]
      [else (insert-products (rest products) (insert-single (first products) tree))]
    )
  )
)

;; Tests
(check-expect (insert-products empty empty) empty)
(check-expect (insert-products veggies empty) reftree-veggies)
(check-expect (insert-products (list salt) (make-product-tree-node empty empty chocolate)) (make-product-tree-node empty (make-product-tree-node empty empty salt) chocolate))
(check-expect (insert-products veggies reftree-veggies) reftree-veggies)

;; ====== Problem 5.2 ======
;; creation of predicates

;; category=?: product symbol -> boolean
;; Explanation: Returns true if given product belongs to category specified by symbol, false otherwise
;; Example: (category=? carrot 'veggie) -> true
(define (category=? prod categ)
  (symbol=? categ (product-categorie prod))
)

;; Tests
(check-expect (category=? chocolate 'sweets) true)
(check-expect (category=? salt 'sweets) false)
(check-expect (category=? cabbage 'veggie) true)

;; categorie=?: product symbol -> boolean
;; Explanation: Returns true if given product belongs to categorie (sic) specified by symbol, false otherwise
;; Example: (categorie=? carrot 'veggie) -> true
(define (categorie=? prod categ)
  (category=? prod categ)
)

;; Tests
(check-expect (categorie=? chocolate 'sweets) true)
(check-expect (categorie=? salt 'sweets) false)
(check-expect (categorie=? cabbage 'veggie) true)

;; cheaper?: product number -> boolean
;; Explanation: Returns true if product is cheaper than reference price, false if equal of more expensive
;; Example: (cheaper? carrot 5.99) -> true
(define (cheaper? prod value)
  (< (product-price prod) value)
)

;; Tests
(check-expect (cheaper? salt 1) true)
(check-expect (cheaper? chocolate 1.5) false)
(check-expect (cheaper? cabbage 0.79) false)

;; ====== Problem 5.3 ======

;; search-tree: product-tree-node product-tree-node X (product X -> boolean) -> (listof product)
;; Explanation: Returns a list of all products in the input tree that match the specified perdicate. List is sorted by product id in ascending order
;; Example: (product-tree-node reftree-veggies 1 cheaper?) -> (list cabbage)
(define (search-tree tree predicate-value predicate)
  (cond
    [(empty? tree) empty]
    [(predicate (product-tree-node-prod tree) predicate-value) (append (search-tree (product-tree-node-left tree) predicate-value predicate) (list (product-tree-node-prod tree)) (search-tree (product-tree-node-right tree) predicate-value predicate))]
    [else (append (search-tree (product-tree-node-left tree) predicate-value predicate) (search-tree (product-tree-node-right tree) predicate-value predicate))]
  )
)

;; Tests
(check-expect (search-tree (insert-products everything empty) 'sweets category=?) (list cookie chocolate jellybean marshmallow))
(check-expect (search-tree reftree-veggies 1 cheaper?) (list cabbage))
(check-expect (search-tree reftree-veggies 'sweets category=?) empty)

;; ====== Problem 5.4 ======

;; product-list-cost: product-tree-node (listof (listof symbol, number)) -> number
;; Explanation: Takes a list of things to buy in the format (list 'name quantity) and returns the total cost for all items in their respective quantities
;; Example: (product-list-cost reftree-veggies (list (list 'carrot 1) (list 'onion 2))) -> 6.12
(define (product-list-cost tree grocery-list)
  (local
    (
     ;; cost: symbol name product-tree-node -> number
     ;; Explanation: Searches input tree for a product with matching name and returns its price or 0 if no matching product is found
     ;; Example: (cost 'carrot reftree-veggies) -> 1
     (define (cost name tree)
       (cond
         [(empty? tree) 0]
         [(symbol=? name (product-name (product-tree-node-prod tree))) (product-price (product-tree-node-prod tree))]
         [else (max (cost name (product-tree-node-left tree)) (cost name (product-tree-node-right tree)))] ;; Technically we could stop after finding the price in the left tree but saving the result makes everything less elegant. Runtime shouldn't be a concern, anyway.
        )
     )
    )
    (cond
      [(empty? grocery-list) 0]
      [else (+ (* (cost (first (first grocery-list)) tree) (second (first grocery-list))) (product-list-cost tree (rest grocery-list)))]
    )
  )
)

;; Tests
(check-expect (product-list-cost (insert-products everything empty) (list (list 'chocolate 2) (list 'carrot 5))) 8.98)
(check-expect (product-list-cost reftree-veggies (list (list 'shiitake 3) (list 'chili 10) (list 'carrot 2))) 2)
(check-expect (product-list-cost reftree-veggies (list (list 'cabbage 250))) 197.5)
(check-expect (product-list-cost reftree-veggies empty) 0)
(check-expect (product-list-cost reftree-veggies (list (list 'carrot -100))) -100) ;; ONE WEIRD TRICK to earn money at the supermarket...
