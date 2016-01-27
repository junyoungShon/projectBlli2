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
drop table blli_member_scrape cascade constraint;
drop table blli_mailing cascade constraint;


drop table blli_member cascade constraint;
CREATE TABLE blli_member (
	member_id            VARCHAR2(30) NOT NULL primary key,
	member_email         VARCHAR2(50) NULL ,
	member_password      VARCHAR2(100) NOT NULL ,
	member_name          VARCHAR2(30) NOT NULL ,
	member_state         NUMBER(2) default 0 ,
	--recommending 		NUMBER(1) NOT NULL, 삭제 
	authority            VARCHAR2(20)
);

	
drop table blli_baby cascade constraint;
CREATE TABLE blli_baby (
	baby_name           VARCHAR2(50) NOT NULL ,
	baby_birthday       DATE NOT NULL ,
	baby_sex            VARCHAR2(10) NOT NULL ,
	baby_photo          VARCHAR2(200) NULL ,
	member_id           VARCHAR2(30) NOT NULL ,
	recommending 		NUMBER(1) NOT NULL, --추가됨 0은 현재비추천 1은 현재추천대상
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
	big_category         VARCHAR2(50) NOT NULL primary key,
	big_category_id          VARCHAR2(30) NOT NULL -- 추가
);


drop table blli_mid_category cascade constraint;
CREATE TABLE blli_mid_category (
	mid_category_id          VARCHAR2(30) NOT NULL, -- 추가
	mid_category         VARCHAR2(100) NOT NULL, -- VARCHAR2(50)을 VARCHAR2(100)으로 수정
	mid_category_info    VARCHAR2(250) NULL ,
	mid_category_main_photo_link VARCHAR2(300) NOT NULL ,
	mid_category_whentouse_min NUMBER(20) NULL , -- 컬럼명 변경
	mid_category_whentouse_max NUMBER(20) NULL , -- 추가
	big_category         VARCHAR2(50) NOT NULL ,
	constraint pk_mid_category primary key(mid_category_id, mid_category),
	constraint fk_mid_cate_big_cate foreign key(big_category) references blli_big_category(big_category)
);


drop table blli_small_product cascade constraint;
CREATE TABLE blli_small_product ( -- naver_shopping_link  VARCHAR2(300) NOT NULL 삭제
   small_product_id     VARCHAR2(30) NOT NULL primary key, -- 추가
   small_product   VARCHAR2(100) NOT NULL ,
   mid_category         VARCHAR2(50) NOT NULL ,
   small_product_maker  VARCHAR2(50) NOT NULL ,
   small_propduct_whentouse_min NUMBER(20) NULL , -- 컬럼명 변경, NOT NULL을 NULL로 변경
   small_propduct_whentouse_max NUMBER(20) NULL , -- 추가
   small_product_dibs_count NUMBER(10) default 0 ,
   small_product_main_photo_link VARCHAR2(300) NOT NULL ,
   small_product_score  NUMBER(4) default 0 ,
   small_product_posting_count NUMBER(8) NOT NULL , -- default 0을 NOT NULL로 변경
   naver_shopping_order NUMBER(5) NOT NULL, -- 추가
   product_register_day DATE NOT NULL, -- 추가
   mid_category_id      VARCHAR2(30) NOT NULL, -- 추가
   constraint fk_small_prod_mid_cate foreign key(mid_category, mid_category_id) references blli_mid_category(mid_category, mid_category_id) -- mid_category_id 추가
);


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
	posting_order            NUMBER(3) NOT NULL, -- 추가
	posting_reply_count      NUMBER(4) NOT NULL, -- 추가
	posting_status            VARCHAR2(30) NOT NULL, -- 추가
	constraint fk_posting_small_prod_id foreign key(small_product_id) references blli_small_product(small_product_id),
	constraint pk_posting primary key(posting_url, small_product) -- 복합키로 변경
);


drop table blli_small_prod_buy_link cascade constraint;
CREATE TABLE blli_small_prod_buy_link (
	small_product_id    VARCHAR2(30) NOT NULL primary key, -- 추가
	buy_link             VARCHAR2(2000) NOT NULL , -- VARCHAR2(50)을 VARCHAR2(2000)으로 변경
	buy_link_price       NUMBER(10) NOT NULL ,
	buy_link_delivery_cost VARCHAR2(30) NOT NULL , -- NUMBER(6)를 VARCHAR2(30)으로 변경
	buy_link_option     VARCHAR2(30) NULL, -- 추가
	seller               VARCHAR2(50) NOT NULL ,
	buy_link_click_count NUMBER(10) default 0 ,
	constraint fk_small_buy_link_small foreign key(small_product_id) references blli_small_product(small_product_id) -- 변경
);


drop table blli_small_product_photo cascade constraint;
CREATE TABLE blli_small_product_photo (
	small_product_photo_link VARCHAR2(300) NOT NULL primary key,
	small_product_id   VARCHAR2(100) NOT NULL ,
	constraint fk_small_prod_photo_small foreign key(small_product_id) references blli_small_product(small_product_id)
);


drop table blli_recomm_mid_category cascade constraint;
CREATE TABLE blli_recomm_mid_category (
	member_id            VARCHAR2(30) NOT NULL ,
	mid_category         VARCHAR2(50) NOT NULL ,
	mid_category_id          VARCHAR2(30) NOT NULL, -- 추가
	constraint fk_recomm_mid_cate_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_recomm_mid_mid_cate foreign key(mid_category,mid_category_id) references blli_mid_category(mid_category,mid_category_id), --수정
	constraint pk_recomm_mid_cate primary key (member_id, mid_category)
);

drop table BLLI_NOT_RECOMM_MID_CATEGORY cascade constraint;
CREATE TABLE BLLI_NOT_RECOMM_MID_CATEGORY(
	member_id            VARCHAR2(30) NOT NULL ,
	mid_category         VARCHAR2(50) NOT NULL ,
	mid_category_id          VARCHAR2(30) NOT NULL, -- 추가
	constraint fk_NOT_recomm_mid_cate_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_NOT_recomm_mid_mid_cate foreign key(mid_category,mid_category_id) references blli_mid_category(mid_category,mid_category_id), --수정
	constraint pk_NOT_recomm_mid_cate primary key (member_id, mid_category)
)



drop table blli_member_dibs cascade constraint;
CREATE TABLE blli_member_dibs (
	member_id            VARCHAR2(30) NOT NULL ,
	small_product_id   VARCHAR2(100) NOT NULL ,
	--16.01.22 추가
	dibs_time			DATE NOT NULL, 
	constraint fk_member_dibs_member_id foreign key(member_id) references blli_member(member_id),
	constraint fk_member_dibs_small_prod foreign key(small_product_id) references blli_small_product(small_product_id),
	constraint pk_member_dibs primary key (member_id, small_product_id)
);


drop table blli_buy_link_click cascade constraint;
CREATE TABLE blli_buy_link_click (
	member_id            VARCHAR2(30) NOT NULL ,
	click_time           DATE NOT NULL ,
	buy_link             VARCHAR2(300) NOT NULL ,
	small_product_id   VARCHAR2(100) NOT NULL ,
	constraint fk_buy_link_click_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_buy_link_click_buy_prod foreign key(buy_link, small_product_id) references blli_small_prod_buy_link(buy_link, small_product_id),
	constraint pk_buy_link_click primary key (member_id, click_time, buy_link, small_product_id)
);



drop table blli_member_scrape cascade constraint;
CREATE TABLE blli_member_scrape (
	member_id            VARCHAR2(30) NOT NULL ,
	posting_url          VARCHAR2(300) NOT NULL ,
	small_product_id   VARCHAR2(30) NOT NULL , -- 추가
	scrape_time			DATE,
	constraint fk_member_scrap_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_member_scrap_post_url foreign key(posting_url) references blli_posting(posting_url),
	constraint fk_member_scrap_small_p_id foreign key(small_product_id) references blli_small_product(small_product_id),
	constraint pk_member_scrap primary key (member_id, posting_url,small_product_id)
);

drop table blli_posting_like cascade constraint;
CREATE TABLE blli_posting_like (
	member_id            VARCHAR2(30) NOT NULL ,
	posting_url          VARCHAR2(300) NOT NULL ,
	small_product_id   VARCHAR2(30) NOT NULL , -- 추가
	LIKE_time			DATE,
	constraint fk_member_LIKE_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_member_LIKE_post_url foreign key(posting_url) references blli_posting(posting_url),
	constraint fk_member_LIKE_small_p_id foreign key(small_product_id) references blli_small_product(small_product_id),
	constraint pk_member_LIKE primary key (member_id, posting_url,small_product_id)
);
drop table blli_posting_dislike cascade constraint;
CREATE TABLE blli_posting_dislike (
	member_id            VARCHAR2(30) NOT NULL ,
	posting_url          VARCHAR2(300) NOT NULL ,
	small_product_id   VARCHAR2(30) NOT NULL , -- 추가
	DISLIKE_time			DATE,
	constraint fk_member_DISLIKE_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_member_DISLIKE_post_url foreign key(posting_url) references blli_posting(posting_url),
	constraint fk_member_DISLIKE_small_p_id foreign key(small_product_id) references blli_small_product(small_product_id),
	constraint pk_member_DISLIKE primary key (member_id, posting_url,small_product_id)
);


drop table blli_mailing cascade constraint;
CREATE TABLE blli_mailing (
	mail_form			VARCHAR2(20) NOT NULL primary key,
	mail_subject		VARCHAR2(100) NOT NULL,
	mail_content_file		VARCHAR2(30) NOT NULL
);


------------------------------------------------------------------------------------------------------------

drop sequence blli_schedule_seq;
create sequence blli_schedule_seq;

------------------------------------------------------------------------------------------------------------
-- 대분류 > 중분류 > 소분류 > 포스팅 순으로 insert


