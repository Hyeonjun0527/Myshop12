
--INSERT INTO product VALUES (seq_product_prod_no.nextval, '피카츄', '전기를 이용한 공격을 하는 귀여운 포켓몬', '20210401', 500000, 'pikachu.jpg', TO_DATE('2021/04/01 10:30:00', 'YYYY/MM/DD HH24:MI:SS'), 50);
--INSERT INTO product VALUES (seq_product_prod_no.nextval, '이브이', '진화의 가능성이 다양한 포켓몬', '20210315', 300000, 'eevee.jpg', TO_DATE('2021/03/15 12:45:00', 'YYYY/MM/DD HH24:MI:SS'), 70);
--INSERT INTO product VALUES (seq_product_prod_no.nextval, '창파나이트', '강력한 물리 공격을 자랑하는 철갑 포켓몬', '20210228', 850000, 'corviknight.jpg', TO_DATE('2021/02/28 09:20:00', 'YYYY/MM/DD HH24:MI:SS'), 95);
--INSERT INTO product VALUES (seq_product_prod_no.nextval, '뮤', '전설의 포켓몬 중 하나로 다양한 기술을 사용 가능', '20210130', 1500000, 'mew.jpg', TO_DATE('2021/01/30 16:00:00', 'YYYY/MM/DD HH24:MI:SS'), 40);
--INSERT INTO product VALUES (seq_product_prod_no.nextval, '가디', '충성심 높은 불꽃 포켓몬', '20201225', 400000, 'growlithe.jpg', TO_DATE('2020/12/25 14:55:00', 'YYYY/MM/DD HH24:MI:SS'), 55);
--INSERT INTO product VALUES (seq_product_prod_no.nextval, '파이리', '몸에서 불꽃을 뿜는 귀여운 드래곤 포켓몬', '20201120', 300000, 'charmander.jpg', TO_DATE('2020/11/20 11:11:00', 'YYYY/MM/DD HH24:MI:SS'), 45);
--INSERT INTO product VALUES (seq_product_prod_no.nextval, '꼬부기', '등껍질로 몸을 보호하는 물 포켓몬', '20201010', 320000, 'squirtle.jpg', TO_DATE('2020/10/10 13:45:00', 'YYYY/MM/DD HH24:MI:SS'), 65);
--INSERT INTO product VALUES (seq_product_prod_no.nextval, '리자몽', '하늘을 나는 강력한 불의 포켓몬', '20200915', 980000, 'charizard.jpg', TO_DATE('2020/09/15 17:30:00', 'YYYY/MM/DD HH24:MI:SS'), 85);
--INSERT INTO product VALUES (seq_product_prod_no.nextval, '푸린', '사랑스러운 노래로 상대를 잠재우는 포켓몬', '20200808', 200000, 'jigglypuff.jpg', TO_DATE('2020/08/08 12:00:00', 'YYYY/MM/DD HH24:MI:SS'), 60);
--INSERT INTO product VALUES (seq_product_prod_no.nextval, '거북왕', '거대한 등껍질을 가진 물 포켓몬', '20200701', 750000, 'blastoise.jpg', TO_DATE('2020/07/01 10:20:00', 'YYYY/MM/DD HH24:MI:SS'), 80);



--insert into transaction values (seq_transaction_tran_no.nextval,'user12', '1', '김철수', '010-1234-5678','서울시 강남구','배송시 연락주세요','b',to_date('2012/11/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'),to_date('2012/11/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'),14000);
--insert into transactionDetail values (seq_tranDetail_detail_no.nextval, 10000, 10000, 2, 4000);
--insert into transactionDetail values (seq_tranDetail_detail_no.nextval, 10000, 10001, 1, 10000);



--NOT NULL에 조건을 걸어주면서 참조키를 걸면 서로 반드시 존재하는 사이가 된다. transaction 과 transactionDetail