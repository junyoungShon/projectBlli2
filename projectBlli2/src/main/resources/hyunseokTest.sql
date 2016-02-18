<<<<<<< HEAD
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
=======
drop table blli_member cascade constraint;
CREATE TABLE blli_member (
	member_id            VARCHAR2(30) NOT NULL primary key,
	member_email         VARCHAR2(50) NULL ,
	member_password      VARCHAR2(100) NOT NULL ,
	member_name          VARCHAR2(30) NOT NULL ,
	member_state         NUMBER(2) default 0 ,
	--recommending 		NUMBER(1) NOT NULL, 삭제 
	authority            VARCHAR2(20)
>>>>>>> branch 'master' of https://github.com/junyoungShon/projectBlli2.git
);
-- 컬럼 추가(0 : 동의O, 1 : 동의X)
alter table blli_member add(mail_agree NUMBER(1) default 0)

select member_id, member_email, member_name, authority, mail_agree from blli_member;

select mail_subject, mail_content_file from blli_mailing where mail_form = 'recommendingMail';

select bm.member_id, bm.member_email, bm.member_name 
from blli_member bm, (select distinct bb.member_id from blli_baby bb where substr(to_char(baby_birthday), 7) = substr(to_char(sysdate), 7)) idt 
where bm.member_id = idt.member_id and bm.mail_agree = 0

select * from blli_member;

update blli_member set mail_agree = 0 where member_email = 'gonipal@naver.com';


select bsp.small_product, bsp.mid_category, bp.small_product_id, bp.posting_url, bp.posting_title, bp.posting_summary, bp.posting_score, 
bp.posting_like_count, bp.posting_dislike_count, bp.posting_photo_link, bp.posting_author, bp.posting_date, bp.posting_rank from(
	select posting_url, posting_title, posting_summary, posting_score, posting_like_count, posting_dislike_count, posting_photo_link, 
	posting_author, posting_date, posting_rank, small_product_id 
	from blli_posting where posting_url = 'http://blog.naver.com/01084341191/220433590468'
)bp, blli_small_product bsp where bsp.small_product_id = bp.small_product_id;

select posting_url from blli_posting;

<<<<<<< HEAD
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
=======
select posting_url, posting_title, posting_content, small_product, small_product_id from(
	select ceil(rownum/5) as page, posting_url, posting_title, posting_content, small_product, small_product_id from(
		select count(*) over (partition by posting_url) as count_posting_url, posting_url, posting_title, 
		posting_content, small_product, small_product_id from blli_posting where posting_status = 'unconfirmed' order by small_product asc
	) where count_posting_url = 1 
) where page = '1'

select * from blli_small_product where small_product = '위드맘 로히트 2단계 750g';

select * from blli_posting where small_product like '%' || '위드맘' || '%';

update blli_small_product set small_product_status = 'confirmedbyadmin', db_insert_posting_count = 0 where small_product = '위드맘 로히트 2단계 750g';

update blli_posting set posting_status = 'unconfirmed' where small_product = '위드맘 로히트 2단계 750g';

select * from blli_small_product;
>>>>>>> branch 'master' of https://github.com/junyoungShon/projectBlli2.git
