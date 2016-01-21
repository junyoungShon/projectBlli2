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
select * from blli_small_product;

drop table blli_posting_test cascade constraint;
CREATE TABLE blli_posting_test (
	posting_url          VARCHAR2(300) NOT NULL ,
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
	posting_view_count      NUMBER(6) default 0 ,
	posting_scrape_count    NUMBER(3) default 0, -- 추가
	posting_author           VARCHAR2(100) NOT NULL, -- 추가
	posting_date             DATE NOT NULL, -- 추가
	posting_order            NUMBER(3) NOT NULL, -- 추가
	posting_reply_count      NUMBER(4) NOT NULL, -- 추가
	posting_status            VARCHAR2(30) NOT NULL, -- 추가
	constraint fk_posting_small_prod_test foreign key(small_product) references test_small_product(small_product),
	constraint pk_posting_test primary key(posting_url, small_product) 
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

		
select posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, posting_scrape_count, 
		posting_like_count, posting_dislike_count, posting_author, to_char(posting_date,'YYYY.MM.DD') as posting_date
		from blli_posting_test where small_product like '%' || '업그레이드' || '%'
		
select bp.posting_url, bp.posting_title, bp.posting_photo_link, bp.posting_summary, bp.posting_score, bp.small_product, bp.posting_scrape_count, 
		bp.posting_like_count, bp.posting_dislike_count, bp.posting_author, to_char(bp.posting_date,'YYYY.MM.DD') as posting_date from(
	select b.small_product from(		
		select posting_url from blli_posting_test where small_product like '%' || '업그레이드' || '%'
	)p, blli_posting_test b where b.posting_url = p.posting_url 
)sp, blli_posting_test bp group by small_product having count(*) < 2;

select posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, posting_scrape_count, 
		posting_like_count, posting_dislike_count, posting_author, to_char(posting_date,'YYYY.MM.DD') as posting_date
		from blli_posting_test where posting_url
	
select bp.posting_url, bp.posting_title, bp.posting_photo_link, bp.posting_summary, bp.posting_score, bp.small_product, bp.posting_scrape_count, 
		bp.posting_like_count, bp.posting_dislike_count, bp.posting_author, to_char(bp.posting_date,'YYYY.MM.DD') as posting_date from(
	select count(*) over (partition by b.posting_url) as small_product_count, b.posting_url from(		
		select posting_url from blli_posting_test where small_product like '%' || '업그레이드' || '%'
	)p, blli_posting_test b where b.posting_url = p.posting_url 
)sp, blli_posting_test bp where sp.small_product_count = 1 and sp.posting_url = bp.posting_url;

select bp.posting_url, bp.posting_title, bp.posting_photo_link, bp.posting_summary, bp.posting_score, bp.small_product, bp.posting_scrape_count, 
bp.posting_like_count, bp.posting_dislike_count, bp.posting_author, to_char(bp.posting_date,'YYYY.MM.DD') as posting_date from(
	select count(*) over (partition by b.posting_url) as small_product_count, b.posting_url from(		
		select posting_url from blli_posting_test
	)p, blli_posting_test b where b.posting_url = p.posting_url 
)sp, blli_posting_test bp where sp.small_product_count = 1 and sp.posting_url = bp.posting_url
		
select posting_url, posting_title, small_product from(
	select count(*) over (partition by posting_url) as small_product_count, posting_url, posting_title, small_product from blli_posting_test
)s where s.small_product_count > 1
		
select small_product from blli_posting_test where posting_url = 'http://blog.naver.com/2000tsk/220330030245';
		
CREATE TABLE blli_big_category (
	big_category         VARCHAR2(50) NOT NULL primary key,
	category_id          VARCHAR2(30) NOT NULL -- 추가
);
drop table blli_big_category;

select * from blli_big_category;

CREATE TABLE blli_mid_category (
	mid_category         VARCHAR2(100) NOT NULL, -- VARCHAR2(50)을 VARCHAR2(100)으로 수정
	mid_category_info    VARCHAR2(250) NULL ,
	mid_category_main_photo_link VARCHAR2(300) NOT NULL ,
	mid_category_whentouse NUMBER(20) NULL ,
	big_category         VARCHAR2(50) NOT NULL ,
	category_id          VARCHAR2(30) NOT NULL, -- 추가
	constraint pk_mid_category primary key (mid_category, category_id), -- category와 id를 복합키로 변경
	constraint fk_mid_cate_big_cate foreign key(big_category) references blli_big_category(big_category)
);

update blli_mid_category set category_id = 'asd' where mid_category = '가디건' and big_category = '임부복';

select * from blli_mid_category;

select category_id, big_category from blli_big_category

2011.03
create table dayday_test(
	dayday DATE primary key
)
insert into dayday_test(dayday) values('2011.03.01');
select * from dayday_test;

select * from blli_small_product;

select * from blli_small_prod_buy_link;













































		
		