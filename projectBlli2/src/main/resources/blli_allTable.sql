drop table blli_member cascade constraint;
drop table blli_baby cascade constraint;
drop table blli_schedule cascade constraint;
drop table blli_auto_login cascade constraint;
drop table blli_big_category cascade constraint;
drop table blli_mid_category cascade constraint;
drop table blli_small_product cascade constraint;
drop table blli_posting cascade constraint;
drop table blli_small_buy_link cascade constraint;
drop table blli_small_product_photo cascade constraint;
drop table blli_recomm_mid_category cascade constraint;
drop table blli_member_dibs cascade constraint;
drop table blli_buy_link_click cascade constraint;
drop table blli_member_scrap cascade constraint;
drop table blli_mailing cascade constraint;


-- 테이블 생성 순서 : 

drop table blli_member cascade constraint;
CREATE TABLE blli_member (
	member_id            VARCHAR2(30) NOT NULL primary key,
	member_email         VARCHAR2(50) NULL ,
	member_password      VARCHAR2(20) NOT NULL ,
	member_name          VARCHAR2(30) NOT NULL ,
	member_state         NUMBER(2) default 0 ,
	authority            VARCHAR2(20),
	recommending_baby_name VARCHAR2(50) NOT NULL
);

	
drop table blli_baby cascade constraint;
CREATE TABLE blli_baby (
	baby_name           VARCHAR2(50) NOT NULL ,
	baby_birthday       DATE NOT NULL ,
	baby_sex            VARCHAR2(10) NOT NULL ,
	baby_photo          VARCHAR2(200) NULL ,
	member_id           VARCHAR2(30) NOT NULL ,
	constraint fk_baby_member_id foreign key(member_id) references blli_member(member_id),
	constraint pk_baby primary key (member_id, baby_name)
);


drop table blli_schedule cascade constraint;
CREATE TABLE blli_schedule (
	schedule_id          NUMBER(10) NOT NULL primary key,
	member_id            VARCHAR2(30) NOT NULL ,
	baby_name            VARCHAR2(50) NOT NULL ,
	schedule_date        DATE NOT NULL ,
	schedule_title       VARCHAR2(80) NOT NULL ,
	schedule_content     CLOB NULL ,
	schedule_location    VARCHAR2(100) NULL ,
	schedule_check_state NUMBER(2) default -1 ,
	constraint fk_schedule_member_id foreign key(member_id, baby_name) references blli_baby(member_id, baby_name)
);


drop table blli_auto_login cascade constraint;
CREATE TABLE blli_auto_login (
	cookie_id            VARCHAR2(300) NOT NULL primary key,
	member_id            VARCHAR2(30) NOT NULL,
	constraint fk_auto_login_member_id foreign key(member_id) references blli_member(member_id)
);


drop table blli_big_category cascade constraint;
CREATE TABLE blli_big_category (
	big_category         VARCHAR2(50) NOT NULL primary key 
);


drop table blli_mid_category cascade constraint;
CREATE TABLE blli_mid_category (
	mid_category         VARCHAR2(50) NOT NULL primary key,
	mid_category_info    VARCHAR2(250) NOT NULL ,
	mid_category_main_photo_link VARCHAR2(300) NOT NULL ,
	mid_category_whentouse NUMBER(20) NOT NULL ,
	big_category         VARCHAR2(50) NOT NULL ,
	constraint fk_mid_cate_big_cate foreign key(big_category) references blli_big_category(big_category)
);


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


drop table blli_posting cascade constraint;
CREATE TABLE blli_posting (
	posting_url          VARCHAR2(300) NOT NULL primary key,
	small_product   VARCHAR2(200) NOT NULL ,
	posting_title        VARCHAR2(100) NOT NULL ,
	posting_summary      VARCHAR2(300) NOT NULL ,
	posting_content      CLOB NOT NULL ,
	posting_score        NUMBER(4) default 0 ,
	posting_like_count   NUMBER(6) default 0 ,
	posting_dislike_count NUMBER(6) default 0 ,
	posting_media_count  NUMBER(3) default 0 ,
	posting_photo_link   VARCHAR2(300) NOT NULL ,
	posting_total_residence_time NUMBER(8) default 0 ,
	posting_view_count NUMBER(6) default 0 ,
	constraint fk_posting_small_prod foreign key(small_product) references blli_small_product(small_product)
);


drop table blli_small_prod_buy_link cascade constraint;
CREATE TABLE blli_small_prod_buy_link (
	buy_link             VARCHAR2(50) NOT NULL ,
	small_product   VARCHAR2(100) NOT NULL ,
	buy_link_price       NUMBER(10) NOT NULL ,
	buy_link_delivery_cost NUMBER(6) NOT NULL ,
	seller               VARCHAR2(50) NOT NULL ,
	buy_link_click_count NUMBER(10) default 0 ,
	constraint fk_small_buy_link_small foreign key(small_product) references blli_small_product(small_product),
	constraint pk_small_buy_link primary key (buy_link, small_product)
);


drop table blli_small_product_photo cascade constraint;
CREATE TABLE blli_small_product_photo (
	small_product_photo_link VARCHAR2(300) NOT NULL primary key,
	small_product   VARCHAR2(100) NOT NULL ,
	constraint fk_small_prod_photo_small foreign key(small_product) references blli_small_product(small_product)
);


drop table blli_recomm_mid_category cascade constraint;
CREATE TABLE blli_recomm_mid_category (
	member_id            VARCHAR2(30) NOT NULL ,
	mid_category         VARCHAR2(50) NOT NULL ,
	constraint fk_recomm_mid_cate_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_recomm_mid_cate_mid_cate foreign key(mid_category) references blli_mid_category(mid_category),
	constraint pk_recomm_mid_cate primary key (member_id, mid_category)
);



drop table blli_member_dibs cascade constraint;
CREATE TABLE blli_member_dibs (
	member_id            VARCHAR2(30) NOT NULL ,
	small_product   VARCHAR2(100) NOT NULL ,
	constraint fk_member_dibs_member_id foreign key(member_id) references blli_member(member_id),
	constraint fk_member_dibs_small_prod foreign key(small_product) references blli_small_product(small_product),
	constraint pk_member_dibs primary key (member_id, small_product)
);


drop table blli_buy_link_click cascade constraint;
CREATE TABLE blli_buy_link_click (
	member_id            VARCHAR2(30) NOT NULL ,
	click_time           DATE NOT NULL ,
	buy_link             VARCHAR2(300) NOT NULL ,
	small_product   VARCHAR2(100) NOT NULL ,
	constraint fk_buy_link_click_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_buy_link_click_buy_prod foreign key(buy_link, small_product) references blli_small_prod_buy_link(buy_link, small_product),
	constraint pk_buy_link_click primary key (member_id, click_time, buy_link, small_product)
);



drop table blli_member_scrap cascade constraint;
CREATE TABLE blli_member_scrap (
	member_id            VARCHAR2(30) NOT NULL ,
	posting_url          VARCHAR2(300) NOT NULL ,
	constraint fk_member_scrap_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_member_scrap_post_url foreign key(posting_url) references blli_posting(posting_url),
	constraint pk_member_scrap primary key (member_id, posting_url)
);


drop table blli_mailing cascade constraint;
CREATE TABLE blli_mailing (
	email_title          VARCHAR2(100) NOT NULL primary key,
	email_content        CLOB NOT NULL 
);



-- 총 12개 테이블
------------------------------------------------------------------------------------------------------------

drop sequence blli_schedule_seq;

create sequence blli_schedule_seq;

------------------------------------------------------------------------------------------------------------


-- 대분류 > 중분류 > 소분류 > 포스팅 순으로 insert


