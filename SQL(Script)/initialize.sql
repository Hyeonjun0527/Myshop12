

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


INSERT INTO product VALUES (seq_product_prod_no.nextval, '피카츄', '전기를 이용한 공격을 하는 귀여운 포켓몬', '20210401', 500000, 'pikachu.jpg', TO_DATE('2021/04/01 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 50);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '이브이', '진화의 가능성이 다양한 포켓몬', '20210315', 300000, 'eevee.jpg', TO_DATE('2021/03/15 12:45:00', 'YYYY/MM/DD HH24:MI:SS'), 70);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '창파나이트', '강력한 물리 공격을 자랑하는 철갑 포켓몬', '20210228', 850000, 'corviknight.jpg', TO_DATE('2021/02/28 09:20:00', 'YYYY/MM/DD HH24:MI:SS'), 95);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '뮤', '전설의 포켓몬 중 하나로 다양한 기술을 사용 가능', '20210130', 1500000, 'mew.jpg', TO_DATE('2021/01/30 16:00:00', 'YYYY/MM/DD HH24:MI:SS'), 40);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '가디', '충성심 높은 불꽃 포켓몬', '20201225', 400000, 'growlithe.jpg', TO_DATE('2020/12/25 14:55:00', 'YYYY/MM/DD HH24:MI:SS'), 55);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '파이리', '몸에서 불꽃을 뿜는 귀여운 드래곤 포켓몬', '20201120', 300000, 'charmander.jpg', TO_DATE('2020/11/20 11:11:00', 'YYYY/MM/DD HH24:MI:SS'), 45);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '꼬부기', '등껍질로 몸을 보호하는 물 포켓몬', '20201010', 320000, 'squirtle.jpg', TO_DATE('2020/10/10 13:45:00', 'YYYY/MM/DD HH24:MI:SS'), 65);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '리자몽', '하늘을 나는 강력한 불의 포켓몬', '20200915', 980000, 'charizard.jpg', TO_DATE('2020/09/15 17:30:00', 'YYYY/MM/DD HH24:MI:SS'), 85);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '푸린', '사랑스러운 노래로 상대를 잠재우는 포켓몬', '20200808', 200000, 'jigglypuff.jpg', TO_DATE('2020/08/08 12:00:00', 'YYYY/MM/DD HH24:MI:SS'), 60);
INSERT INTO product VALUES (seq_product_prod_no.nextval, '거북왕', '거대한 등껍질을 가진 물 포켓몬', '20200701', 750000, 'blastoise.jpg', TO_DATE('2020/07/01 10:20:00', 'YYYY/MM/DD HH24:MI:SS'), 80);



insert into transaction values (seq_transaction_tran_no.nextval,'user12', '1', '김철수', '010-1234-5678','서울시 강남구','배송시 연락주세요','b',to_date('2012/11/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'),to_date('2012/11/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'),14000);

insert into transactionDetail values (seq_tranDetail_detail_no.nextval, 10000, 10000, 2, 4000);
insert into transactionDetail values (seq_tranDetail_detail_no.nextval, 10000, 10001, 1, 10000);

select * from users;
select * from product;
select * from transaction;
select * from transactionDetail;
select * from category;


commit
;


--NOT NULL에 조건을 걸어주면서 참조키를 걸면 서로 반드시 존재하는 사이가 된다. transaction 과 transactionDetail