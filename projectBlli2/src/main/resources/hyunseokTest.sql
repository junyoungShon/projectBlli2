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