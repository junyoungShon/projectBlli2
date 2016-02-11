imvestt@hanmail.net

drop table blli_member;

create table blli_member(
	member_id varchar2(50) primary key,
	member_email varchar2(50) not null,
	member_password varchar2(100) not null,
	member_Name varchar2(20) not null,
	member_state number(1) not null,
	recommending_baby_name varchar(20) not null,
	authority varchar2(10) not null
)

create table blli_baby(
	member_id varchar2(50) primary key,
	baby_name varchar2(50) not null,
	baby_birthday date not null,
	baby_sex varchar2(10) not null,
	baby_photo varchar2(200) not null
)
drop table blli_baby

insert into BLLI_MEMBER (member_Id,member_password,member_email,member_name,member_state,recommending_child_name,authority)
		values ('asdf','asdf','asdf','asdf',1,'asdf','ROLE_USER');
		
		

drop table blli_recomm_mid_category cascade constraint;
CREATE TABLE blli_recomm_mid_category (
	member_id            VARCHAR2(30) NOT NULL ,
	mid_category         VARCHAR2(50) NOT NULL ,
	category_id          VARCHAR2(30) NOT NULL, -- 추가
	constraint fk_recomm_mid_cate_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_recomm_mid_mid_cate foreign key(mid_category,category_id) references blli_mid_category(mid_category,category_id), --수정
	constraint pk_recomm_mid_cate primary key (member_id, mid_category)
);

--회원의 추천 기피 중분류 제품 테이블
CREATE TABLE BLLI_NOT_RECOMM_MID_CATEGORY(
	member_id            VARCHAR2(30) NOT NULL ,
	mid_category         VARCHAR2(50) NOT NULL ,
	mid_category_id          VARCHAR2(30) NOT NULL, -- 추가
	constraint fk_NOT_recomm_mid_cate_mem_id foreign key(member_id) references blli_member(member_id),
	constraint fk_NOT_recomm_mid_mid_cate foreign key(mid_category,category_id) references blli_mid_category(mid_category,category_id), --수정
	constraint pk_NOT_recomm_mid_cate primary key (member_id, mid_category)
)
select * from (select rownum as rn,SMALL_PRODUCT, MID_CATEGORY,mid_CATEGORY_ID, SMALL_PRODUCT_MAKER, SMALL_PRODUCT_WHENTOUSE_MIN, SMALL_PRODUCT_WHENTOUSE_MAX, SMALL_PRODUCT_DIBS_COUNT, 
		SMALL_PRODUCT_MAIN_PHOTO_LINK, SMALL_PRODUCT_SCORE, SMALL_PRODUCT_POSTING_COUNT, NAVER_SHOPPING_RANK, PRODUCT_REGISTER_DAY,small_product_id
		from BLLI_SMALL_PRODUCT
		where MID_CATEGORY = '8191428972' and '1' >= SMALL_PRODUCT_WHENTOUSE_MIN and '1' <= SMALL_PRODUCT_WHENTOUSE_MAX
		order by SMALL_PRODUCT_DIBS_COUNT desc) where rn<3

select 
from recommMidCategory
where
--중분류 추천 제외 대상 쿼리
select bnrmc.mid_category as noRecommendMidCategory
from blli_member bm , BLLI_NOT_RECOMM_MID_CATEGORY bnrmc
where bm.member_id = bnrmc.member_id

--기간에 따른 중분류 추천 제품 쿼리
select  mid_category
from BLLI_MID_CATEGORY
where 0 >= mid_category_whentouse_min and 0<=mid_category_whentouse_min

-- 기간에 따른 중분류 추천 이후 중분류 추천 제외 
select  mid_category,mid_category_info,mid_category_main_photo_link,mid_category_whentouse_min,mid_category_whentouse_max,big_category,category_id
		from BLLI_MID_CATEGORY
		where 0 >= mid_category_whentouse_min and 0<=mid_category_whentouse_min and not mid_category = (select bnrmc.mid_category as noRecommendMidCategory
		from blli_member bm , BLLI_NOT_RECOMM_MID_CATEGORY bnrmc
		where 'imvestt@hanmail.net' = bnrmc.member_id)


### The error occurred while setting parameters
### SQL: select  mid_category,mid_category_info,mid_category_main_photo_link,mid_category_whentouse_min,mid_category_whentouse_max,big_category,category_id   from BLLI_MID_CATEGORY   where 0  = mid_category_whentouse_min and 0<= >mid_category_whentouse_min and not mid_category = (select bnrmc.mid_category as noRecommendMidCategory   from blli_member bm , BLLI_NOT_RECOMM_MID_CATEGORY bnrmc   where bm.member_id = bnrmc.member_id)
### Cause: java.sql.SQLException: ORA-00936: 누락된 표현식
select  mid_category,mid_category_info,mid_category_main_photo_link,mid_category_whentouse_min,mid_category_whentouse_max,big_category,category_id   
from BLLI_MID_CATEGORY   
where 0  = mid_category_whentouse_min and 0<= >mid_category_whentouse_min and not mid_category = 
(select bnrmc.mid_category as noRecommendMidCategory   from blli_member bm , BLLI_NOT_RECOMM_MID_CATEGORY bnrmc   where bm.member_id = bnrmc.member_id)

select  mid_category,mid_category_info,mid_category_main_photo_link,mid_category_whentouse_min,mid_category_whentouse_max,big_category,category_id   
from BLLI_MID_CATEGORY   
where ?  = mid_category_whentouse_min and ?<= >mid_category_whentouse_min and not mid_category = 
(select bnrmc.mid_category as noRecommendMidCategory   from blli_member bm , BLLI_NOT_RECOMM_MID_CATEGORY bnrmc   where ? = bnrmc.member_id)
### Cause: java.sql.SQLException: ORA-00936: 누락된 표현식



insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('A사의레토르트이유식12개월용', '레토르트이유식', '50000737', '1', 12, 12, 100, '11', 1, 11, '1', 1, sysdate)

insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('B사의레토르트이유식12개월용', '레토르트이유식', '50000737', '1', 12, 12, 100, '11', 1, 11, '1', 1, sysdate)

insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('C사의레토르트이유식12개월용', '레토르트이유식', '50000737', '1', 12, 12, 0, '11', 1, 11, '1', 1, sysdate)




insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('A사의레토르트이유식13개월용', '레토르트이유식', '50000737', '1', 13, 13, 100, '11', 1, 11, '1', 1, sysdate)

insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('B사의레토르트이유식13개월용', '레토르트이유식', '50000737', '1', 13, 13, 100, '11', 1, 11, '1', 1, sysdate)

insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('C사의레토르트이유식13개월용', '레토르트이유식', '50000737', '1', 13, 13, 0, '11', 1, 11, '1', 1, sysdate)



insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('A사의노리개젖꼭지12개월용', '노리개젖꼭지', '50000746', '1', 12, 12, 100, '11', 1, 11, '1', 1, sysdate)

insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('B사의노리개젖꼭지12개월용', '노리개젖꼭지', '50000746', '1', 12, 12, 100, '11', 1, 11, '1', 1, sysdate)

insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('C사의노리개젖꼭지12개월용', '노리개젖꼭지', '50000746', '1', 12, 12, 0, '11', 1, 11, '1', 1, sysdate)





insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('A사의젖병12개월용', '젖병', '50000743', '1', 12, 12, 100, '11', 1, 11, '1', 1, sysdate)

insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('B사젖병12개월용', '젖병', '50000743', '1', 12, 12, 100, '11', 1, 11, '1', 1, sysdate)

insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('C사의젖병12개월용', '젖병', '50000743', '1', 12, 12, 0, '11', 1, 11, '1', 1, sysdate)



insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('A사의유축기12개월용', '유축기', '50000673', '1', 12, 12, 100, '11', 1, 11, '1', 1, sysdate)

insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('B사유축기12개월용', '유축기', '50000673', '1', 12, 12, 100, '11', 1, 11, '1', 1, sysdate)

insert into
"SCOTT"."BLLI_SMALL_PRODUCT" ("SMALL_PRODUCT", "MID_CATEGORY", "CATEGORY_ID", "SMALL_PRODUCT_MAKER", "SMALL_PROPDUCT_WHENTOUSE_MIN", "SMALL_PROPDUCT_WHENTOUSE_MAX", "SMALL_PRODUCT_DIBS_COUNT", 
"SMALL_PRODUCT_MAIN_PHOTO_LINK", "SMALL_PRODUCT_SCORE", "SMALL_PRODUCT_POSTING_COUNT", "NAVER_SHOPPING_LINK", "NAVER_SHOPPING_ORDER", "PRODUCT_REGISTER_DAY") 
values('C사의유축기12개월용', '유축기', '50000673', '1', 12, 12, 0, '11', 1, 11, '1', 1, sysdate)



select SMALL_PRODUCT, MID_CATEGORY,CATEGORY_ID, SMALL_PRODUCT_MAKER, SMALL_PROPDUCT_WHENTOUSE_MIN, SMALL_PROPDUCT_WHENTOUSE_MAX, SMALL_PRODUCT_DIBS_COUNT, 
SMALL_PRODUCT_MAIN_PHOTO_LINK, SMALL_PRODUCT_SCORE, SMALL_PRODUCT_POSTING_COUNT, NAVER_SHOPPING_LINK, NAVER_SHOPPING_ORDER, PRODUCT_REGISTER_DAY from BLLI_SMALL_PRODUCT

select rownum as rn,SMALL_PRODUCT, MID_CATEGORY, SMALL_PRODUCT_DIBS_COUNT
from BLLI_SMALL_PRODUCT
where MID_CATEGORY = '유축기'
order by SMALL_PRODUCT_DIBS_COUNT desc

select * from (select rownum as rn,SMALL_PRODUCT, MID_CATEGORY,CATEGORY_ID, SMALL_PRODUCT_MAKER, SMALL_PROPDUCT_WHENTOUSE_MIN, SMALL_PROPDUCT_WHENTOUSE_MAX, SMALL_PRODUCT_DIBS_COUNT, 
SMALL_PRODUCT_MAIN_PHOTO_LINK, SMALL_PRODUCT_SCORE, SMALL_PRODUCT_POSTING_COUNT, NAVER_SHOPPING_LINK, NAVER_SHOPPING_ORDER, PRODUCT_REGISTER_DAY
from BLLI_SMALL_PRODUCT
where MID_CATEGORY = '유축기' and 12  >= SMALL_PROPDUCT_WHENTOUSE_MIN and 12<= SMALL_PROPDUCT_WHENTOUSE_MAX
order by SMALL_PRODUCT_DIBS_COUNT desc) where rn<3



insert into "SCOTT"."BLLI_POSTING" ("POSTING_URL", "SMALL_PRODUCT", "SMALL_PRODUCT_ID", "POSTING_TITLE", "POSTING_SUMMARY", "POSTING_CONTENT", "POSTING_MEDIA_COUNT", "POSTING_PHOTO_LINK", "POSTING_AUTHOR", "POSTING_DATE", "POSTING_ORDER", "POSTING_REPLY_COUNT", "POSTING_STATUS") 
values('http://blog.naver.com/dear_hyde/220517255806', 'Cowala 트루 오리지널 골드+ 3...', '7903518970', '[코알라분유] 뉴질랜드 코알라 조제분...', 'asdfasdf', 'asdfasdf', 5, 'http://postfiles6.na...', '디어 태브라더', '2014-05-05', 1, 1, '1')


select posting_url,small_product,small_product_id,posting_title,posting_summary,posting_content,posting_score,posting_like_count,
		posting_dislike_count,posting_media_count,posting_photo_link,posting_total_residence_time,posting_view_count,posting_scrap_count,posting_author,posting_date
		from blli_posting
		where posting_status = 'confirmed'
		order by posting_score asc;
		
		
		create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)
		
		
		
		select * from(
		select rownum rn ,posting_url,small_product,small_product_id,posting_title,posting_summary,posting_content,posting_score,posting_like_count,
		posting_dislike_count,posting_media_count,posting_photo_link,posting_total_residence_time,posting_view_count,posting_scrape_count,posting_author,posting_date
		from(
		select posting_url,small_product,small_product_id,posting_title,posting_summary,posting_content,posting_score,posting_like_count,
		posting_dislike_count,posting_media_count,posting_photo_link,posting_total_residence_time,posting_view_count,posting_scrape_count,posting_author,posting_date
		from blli_posting
		where posting_status = 'confirmed' and small_product_id = 5666321696
		order by posting_score desc)
		) where rn = 3
		
		select count(*) from blli_small_product
		select * from
		(select rownum as rn,mid_category_id,small_product_id,small_product_dibs_count,detail_view_count,naver_shopping_rank,product_register_day,product_db_insert_date from blli_small_product
		)where rn>500
		select rownum as rn,mid_category_id,small_product_id,small_product_dibs_count,detail_view_count,naver_shopping_rank,product_register_day,product_db_insert_date from blli_small_product where small_product_id = null
		select count(*) from blli_posting
		update blli_small_product set small_product_status = 'confirmed'
		update blli_posting set posting_status = 'confirmed'
		
		select posting_url,small_product_id,posting_like_count,posting_dislike_count,posting_total_residence_time/posting_view_count as avgResi
		posting_media_count,posting_total_residence_time,posting_view_count,posting_scrape_count,posting_rank,posting_date,posting_reply_count,posting_db_insert_date
		from blli_posting
		where posting_status = 'confirmed'
		order by asc
		select small_product_id,small_product_dibs_count,detail_view_count,naver_shopping_rank,product_register_day,product_db_insert_date from blli_small_product
		update blli_posting set posting_db_insert_date = sysdate 
		update blli_small_product set product_db_insert_date = sysdate 
		update blli_small_product set detail_view_count = 0 
		update blli_small_product set detail_view_count = 0 
		update blli_posting set posting_db_insert_date = '2015-02-03'
		select * from blli_posting where posting_url='http://blog.naver.com/mykid0430/220491426583'
		select * from blli_small_product where small_product_score < 70
		
		select avg(small_product_score) from blli_small_product where small_product_status = 'confirmed'
		select avg(posting_score) from blli_posting where posting_status = 'confirmed' 
		
		select small_product_score from blli_small_product where small_product_score>100 and  small_product_status = 'confirmed'
		select posting_score from blli_posting where posting_score>100 and posting_status = 'confirmed' 
		
		select posting_score from blli_posting
		
		select count(*) from blli_posting where posting_status = 'confirmed'
		
		update blli_mid_category set small_product_count = 1000;
		select mid_category_id,small_product_id,small_product_dibs_count,detail_view_count,naver_shopping_rank,product_register_day,product_db_insert_date from blli_small_product
		select sum(buy_link_click_count) from blli_small_prod_buy_link where small_product_id =7909155651
		update blli_small_prod_buy_link set buy_link_click_count = 1   where small_product_id =7909155651
		
		select small_product_id,small_product_score from blli_small_product where mid_category_id = '50000217' and small_product_status ='confirmed' order by small_product_score desc;
		