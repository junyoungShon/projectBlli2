select * from blli_posting where posting_url like '%' || 'http://blog.naver.com/et303' || '%' and small_product_id = '7909155651';

select * from blli_small_product where small_product_status = 'confirmed';

select count(*) from blli_posting;

select * from blli_posting;

select * from blli_small_product;

select * from blli_mid_category;

select * from blli_small_product;

update blli_mid_category set small_product_count = 0;

update blli_small_product set small_product_status = 'confirmed' where small_product_id = '7610052389';

update blli_small_product set small_product_status = 'unconfirmed' where small_product_status = 'confirmed';

update blli_small_product set small_product_status = 'confirmed' where small_product_status = 'unconfirmed';

update blli_posting set posting_status = 'confirmed' where posting_status = 'unconfirmed';

select * from blli_posting where posting_status = 'dead';

select * from blli_small_product where small_product_status = 'dead';

update blli_posting set posting_status = 'confirmed';

update blli_small_product set small_product_whentouse_min = 2;
update blli_small_product set small_product_whentouse_max = 8;

CREATE TABLE blli_log_big_category (
	execute_time DATE PRIMARY KEY,
	method_name VARCHAR2(50) NOT NULL,
	executor VARCHAR2(20) NOT NULL,
	big_category_count NUMBER(2) NOT NULL,
	insert_big_category_count NUMBER(2) NOT NULL,
	update_big_category_count NUMBER(2) NOT NULL,
	exception_count NUMBER(2) NOT NULL,
	run_time VARCHAR2(20) NOT NULL
);

CREATE TABLE log_big_category_exception (
	execute_time DATE NOT NULL,
	big_category_id VARCHAR2(20) NOT NULL,
	exception_message VARCHAR2(500) NOT NULL
	constraint fk_big_category_exception foreign key(execute_time) references blli_log_big_category(execute_time),
	constraint pk_big_category_exception primary key(execute_time, big_category_id)
);

CREATE TABLE blli_log_mid_category (
	
);

CREATE TABLE log_mid_category_exception (
	target VARCAHR2(300) NOT NULL,
	exception_message VARCHAR2(500) NOT NULL
);

CREATE TABLE blli_log_small_product (
	
);

CREATE TABLE log_small_product_exception (
	target VARCAHR2(300) NOT NULL,
	exception_message VARCHAR2(500) NOT NULL
);

CREATE TABLE blli_log_posting (
	
);

CREATE TABLE log_posting_exception (
	target VARCAHR2(300) NOT NULL,
	exception_message VARCHAR2(500) NOT NULL
);

CREATE TABLE blli_log_mail (
	
);

CREATE TABLE log_mail_exception (
	target VARCAHR2(300) NOT NULL,
	exception_message VARCHAR2(500) NOT NULL
);

select * from (
			select buy_link_price,rownum as rn from blli_small_prod_buy_link where small_product_id = #{value} 
		) where rn =1
		
select * from blli_small_product where mid_category_id = '50000855'

select count(*) from blli_small_product;

select * from blli_small_product;
		
		
select * from (select rownum as rn,SMALL_PRODUCT, MID_CATEGORY,mid_CATEGORY_ID, SMALL_PRODUCT_MAKER, SMALL_PRODUCT_WHENTOUSE_MIN, SMALL_PRODUCT_WHENTOUSE_MAX, SMALL_PRODUCT_DIBS_COUNT, 
		SMALL_PRODUCT_MAIN_PHOTO_LINK, SMALL_PRODUCT_SCORE, SMALL_PRODUCT_POSTING_COUNT, NAVER_SHOPPING_RANK, PRODUCT_REGISTER_DAY,small_product_id
		from BLLI_SMALL_PRODUCT
		where MID_CATEGORY_id = '50000854' and 2  >= SMALL_PRODUCT_WHENTOUSE_MIN and 2 <= SMALL_PRODUCT_WHENTOUSE_MAX
		order by SMALL_PRODUCT_DIBS_COUNT desc) where rn<3