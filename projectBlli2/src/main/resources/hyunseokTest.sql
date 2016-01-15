drop table blli_small_product cascade constraint;
CREATE TABLE blli_small_product (
	small_product   VARCHAR2(100) NOT NULL primary key,
	mid_category         VARCHAR2(50) NOT NULL ,
	small_product_maker  VARCHAR2(50) NOT NULL ,
	small_propduct_whentouse NUMBER(20) NOT NULL ,
	small_product_dibs_count NUMBER(10) default 0 ,
	small_product_main_photo_link VARCHAR2(300) NOT NULL ,
	small_product_score  NUMBER(4) default 0 ,
	small_product_posting_count NUMBER(8) default 0 ,
	naver_shopping_link  VARCHAR2(300) NOT NULL ,
	constraint fk_small_prod_mid_cate foreign key(mid_category) references blli_mid_category(mid_category)
);


drop table blli_posting_test cascade constraint;
CREATE TABLE blli_posting_test (
	posting_url          VARCHAR2(300) NOT NULL primary key,
	small_product   VARCHAR2(200) NOT NULL ,
	posting_title        VARCHAR2(450) NOT NULL , -- VARCHAR2(100) 에서 VARCHAR2(450)으로 수정!
	posting_summary      VARCHAR2(600) NOT NULL , -- VARCHAR2(300) 에서 VARCHAR2(600)으로 수정!
	posting_content      CLOB NOT NULL ,
	posting_score        NUMBER(4) default 0 ,
	posting_like_count   NUMBER(6) default 0 ,
	posting_dislike_count NUMBER(6) default 0 ,
	posting_media_count  NUMBER(3) NOT NULL , -- default 0 에서 NOT NULL로 수정!
	posting_photo_link   VARCHAR2(300) NOT NULL ,
	posting_total_residence_time NUMBER(8) default 0 ,
	posting_view_count NUMBER(6) default 0 ,
	posting_scrape_count NUMBER(3) default 0, -- 추가
	constraint fk_posting_small_prod_test foreign key(small_product) references test_small_product(small_product)
);
select * from blli_posting_test;


drop table test_small_product;
create table test_small_product(
	small_product VARCHAR2(100) primary key
);
insert into test_small_product(small_product) values('하은맘 프라임 샴푸의자 업그레이드형');
insert into test_small_product(small_product) values('하은맘 프라임 샴푸의자');
insert into test_small_product(small_product) values('플라팜 허그샴푸의자');
insert into test_small_product(small_product) values('아가명가 샴푸의자');
insert into test_small_product(small_product) values('베이비캠프 컴포트 샴푸체어');

select small_product from test_small_product;