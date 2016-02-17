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