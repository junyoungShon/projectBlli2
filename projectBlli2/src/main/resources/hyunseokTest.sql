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