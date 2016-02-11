select * from blli_posting where posting_url like '%' || 'http://blog.naver.com/et303' || '%' and small_product_id = '7909155651';

select * from blli_small_product where small_product_status = 'confirmed';

select count(*) from blli_posting;

select * from blli_posting;

select * from blli_small_product;

select * from blli_mid_category;

update blli_mid_category set small_product_count = 0;

update blli_small_product set small_product_status = 'confirmed' where small_product_id = '7610052389';

update blli_small_product set small_product_status = 'unconfirmed' where small_product_status = 'confirmed';

update blli_small_product set small_product_status = 'confirmed' where small_product_status = 'unconfirmed';

update blli_posting set posting_status = 'confirmed' where posting_status = 'unconfirmed';

select * from blli_posting where posting_status = 'dead';

select * from blli_small_product where small_product_status = 'dead';

update blli_posting set posting_status = 'confirmed';

drop table blli_posting cascade constraint;
CREATE TABLE blli_posting (
	posting_url          VARCHAR2(300) NOT NULL ,
	small_product       VARCHAR2(200) NOT NULL ,
	small_product_id   VARCHAR2(30) NOT NULL , -- 추가
	posting_title        VARCHAR2(450) NOT NULL , -- VARCHAR2(100) 에서 VARCHAR2(450)으로 수정!
	posting_summary      VARCHAR2(600) NOT NULL , -- VARCHAR2(300) 에서 VARCHAR2(600)으로 수정!
	posting_content      CLOB NOT NULL ,
	posting_score        NUMBER(4) default 0 ,
	posting_like_count   NUMBER(6) default 0 ,
	posting_dislike_count NUMBER(6) default 0 ,
	posting_media_count  NUMBER(4) NOT NULL , -- default 0 에서 NOT NULL로 수정!
	posting_photo_link   VARCHAR2(1000) NOT NULL , -- VARCHAR2(300)에서 VARCHAR2(1000)으로 수정
	posting_total_residence_time NUMBER(8) default 0 ,
	posting_view_count NUMBER(6) default 0 ,
	posting_scrape_count NUMBER(3) default 0, -- 추가, 컬럼명 변경
	posting_author           VARCHAR2(100) NOT NULL, -- 추가
	posting_date             DATE NOT NULL, -- 추가
	posting_db_insert_date   DATE NOT NULL, -- 추가
	posting_rank            NUMBER(3) NOT NULL, -- 추가, posting_order를 posting_rank로 변경
	posting_reply_count      NUMBER(4) NOT NULL, -- 추가
	posting_status            VARCHAR2(30) NOT NULL, -- 추가
	constraint fk_posting_small_prod_id foreign key(small_product_id) references blli_small_product(small_product_id),
	constraint pk_posting primary key(posting_url, small_product_id) -- 복합키로 변경
);