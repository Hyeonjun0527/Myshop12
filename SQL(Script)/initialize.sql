ALTER SESSION SET NLS_LANGUAGE = 'AMERICAN';

ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

ALTER SESSION SET NLS_DATE_LANGUAGE = 'AMERICAN';

desc transactionDetail;
desc transaction;
desc product;
desc users;
desc category;


set pagesize 1000
set linesize 1000

DROP TABLE transactionDetail;
DROP TABLE transaction;
DROP TABLE product;
DROP TABLE users;
DROP TABLE category;

DROP SEQUENCE seq_product_prod_no;
DROP SEQUENCE seq_transaction_tran_no;
DROP SEQUENCE seq_tranDetail_detail_no;

CREATE SEQUENCE seq_product_prod_no		 	INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE seq_transaction_tran_no	INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE seq_tranDetail_detail_no	INCREMENT BY 1 START WITH 10000;


CREATE TABLE users ( 
	user_id 			VARCHAR2(20)	NOT NULL,
	user_name 	VARCHAR2(50)	NOT NULL,
	password 		VARCHAR2(10)	NOT NULL,
	role 					VARCHAR2(5) 		DEFAULT 'user',
	ssn 					VARCHAR2(13),
	cell_phone 		VARCHAR2(14),
	addr 				VARCHAR2(100),
	email 				VARCHAR2(50),
	reg_date 		DATE,
	PRIMARY KEY(user_id)
);


CREATE TABLE product ( 
	prod_no 						NUMBER 				NOT NULL,
	prod_name 				VARCHAR2(100) 	NOT NULL,
	prod_detail 				VARCHAR2(200),
	manufacture_day		VARCHAR2(12),
	price 							NUMBER(10),
	image_file 					VARCHAR2(100),
	reg_date 					DATE,
    stock_quantity            NUMBER  DEFAULT 0 NOT NULL,
    CONSTRAINT chk_stock_quantity CHECK (STOCK_QUANTITY >= 0),
	PRIMARY KEY(prod_no)
);

CREATE TABLE transaction ( 
	tran_no 					NUMBER 			NOT NULL,
	buyer_id 				VARCHAR2(20)	NOT NULL REFERENCES users(user_id),
	payment_option		CHAR(3),
	receiver_name 		VARCHAR2(20),
	receiver_phone		VARCHAR2(14),
	demailaddr 			VARCHAR2(100),
	dlvy_request 			VARCHAR2(100),
	tran_status_code	CHAR(3),
	order_date 			DATE,
	dlvy_date 				DATE,
    total_price             NUMBER NOT NULL,
	PRIMARY KEY(tran_no)
);
CREATE TABLE transactionDetail(
    detail_no NUMBER NOT NULL,
    tran_no NUMBER NOT NULL,
    prod_no NUMBER NOT NULL,
    type_quantity NUMBER NOT NULL,
    type_price NUMBER NOT NULL,
    PRIMARY KEY(detail_no),
    FOREIGN KEY(prod_no) REFERENCES product(prod_no),
    FOREIGN KEY(tran_no) REFERENCES transaction(tran_no)
);

CREATE TABLE category
(
    category_no   NUMBER(16),
    category_name VARCHAR2(100) NOT NULL,
    PRIMARY KEY (category_no),
    UNIQUE (category_name)
);


INSERT 
INTO users ( user_id, user_name, password, role, ssn, cell_phone, addr, email, reg_date ) 
VALUES ( 'admin', 'admin', '1234', 'admin', NULL, NULL, '서울시 서초구', 'admin@mvc.com',to_date('2012/01/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS')); 

INSERT 
INTO users ( user_id, user_name, password, role, ssn, cell_phone, addr, email, reg_date ) 
VALUES ( 'manager', 'manager', '1234', 'admin', NULL, NULL, NULL, 'manager@mvc.com', to_date('2012/01/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'));          

INSERT INTO users 
VALUES ( 'user01', 'SCOTT', '1111', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user02', 'SCOTT', '2222', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user03', 'SCOTT', '3333', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user04', 'SCOTT', '4444', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user05', 'SCOTT', '5555', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user06', 'SCOTT', '6666', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user07', 'SCOTT', '7777', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user08', 'SCOTT', '8888', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user09', 'SCOTT', '9999', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user10', 'SCOTT', '1010', 'user', NULL, NULL, NULL, NULL, sysdate); 

INSERT INTO users 
VALUES ( 'user11', 'SCOTT', '1111', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user12', 'SCOTT', '1212', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user13', 'SCOTT', '1313', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user14', 'SCOTT', '1414', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user15', 'SCOTT', '1515', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user16', 'SCOTT', '1616', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user17', 'SCOTT', '1717', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user18', 'SCOTT', '1818', 'user', NULL, NULL, NULL, NULL, sysdate);

INSERT INTO users 
VALUES ( 'user19', 'SCOTT', '1919', 'user', NULL, NULL, NULL, NULL, sysdate);


INSERT INTO product VALUES (seq_product_prod_no.nextval, '피카츄', '전기를 이용한 공격을 하는 귀여운 포켓몬', '2021-04-01', 500000, 'pikachu.jpg', TO_DATE('2021-04-01', 'YYYY-MM-DD'), 50);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '이브이', '진화의 가능성이 다양한 포켓몬', '2021-03-15', 300000, 'eevee.jpg', TO_DATE('2021-03-15', 'YYYY-MM-DD'), 70);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '창파나이트', '강력한 물리 공격을 자랑하는 철갑 포켓몬', '2021-02-28', 850000, 'corviknight.jpg', TO_DATE('2021-02-28', 'YYYY-MM-DD'), 95);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '뮤', '전설의 포켓몬 중 하나로 다양한 기술을 사용 가능', '2021-01-30', 1500000, 'mew.jpg', TO_DATE('2021-01-30', 'YYYY-MM-DD'), 40);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '가디', '충성심 높은 불꽃 포켓몬', '2020-12-25', 400000, 'growlithe.jpg', TO_DATE('2020-12-25', 'YYYY-MM-DD'), 55);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '파이리', '몸에서 불꽃을 뿜는 귀여운 드래곤 포켓몬', '2020-11-20', 300000, 'charmander.jpg', TO_DATE('2020-11-20', 'YYYY-MM-DD'), 45);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '꼬부기', '등껍질로 몸을 보호하는 물 포켓몬', '2020-10-10', 320000, 'squirtle.jpg', TO_DATE('2020-10-10', 'YYYY-MM-DD'), 65);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '리자몽', '하늘을 나는 강력한 불의 포켓몬', '2020-09-15', 980000, 'charizard.jpg', TO_DATE('2020-09-15', 'YYYY-MM-DD'), 85);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '푸린', '사랑스러운 노래로 상대를 잠재우는 포켓몬', '2020-08-08', 200000, 'jigglypuff.jpg', TO_DATE('2020-08-08', 'YYYY-MM-DD'), 60);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '거북왕', '거대한 등껍질을 가진 물 포켓몬', '2020-07-01', 750000, 'blastoise.jpg', TO_DATE('2020-07-01', 'YYYY-MM-DD'), 80);


select * from users;
select * from product;
select * from transaction;
select * from transactionDetail;
select * from category;


commit
;

