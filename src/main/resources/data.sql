INSERT INTO users (id, login_id,password,name,nickname,money) VALUES (1, 'asd', 'asd','tester1','tester1',200000);
INSERT INTO users (id, login_id,password,name,nickname,money) VALUES (2, 'qwe', 'qwe','tester2','tester2',1000000);
INSERT INTO items (id, item_name,price,quantity,seller_id) VALUES (1, 'testItem1', 12500,38,1);
INSERT INTO items (id, item_name,price,quantity,seller_id) VALUES (2, 'testItem2', 32000,12,2);
INSERT INTO orders (id, item_id,buyer_id,seller_id,total_amount,quantity,order_date) VALUES (1, 1, 2,1,12500*2,2,'2025-1-20');
INSERT INTO orders (id, item_id,buyer_id,seller_id,total_amount,quantity,order_date) VALUES (2, 2, 1,2,32000*4,4,'2025-3-24');

--     private Long id;
--     private Item item;
--     private User buyer;
--     private User seller;
--     private Integer totalAmount;
--     private Integer quantity;
--     private LocalDate orderDate;