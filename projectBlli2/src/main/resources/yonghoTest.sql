
drop table blli_member cascade constraint;
CREATE TABLE blli_member (
	member_id            VARCHAR2(30) NOT NULL primary key,
	member_email         VARCHAR2(50) NULL ,
	member_password      VARCHAR2(100) NOT NULL ,
	member_name          VARCHAR2(30) NOT NULL ,
	member_state         NUMBER(2) default 0 ,
	authority            VARCHAR2(20),
	recommending_baby_name VARCHAR2(50) NOT NULL
);


insert into blli_member(member_id, member_email, member_password, member_name, member_state, authority, recommending_baby_name)
values('yongho', 'sk1597530@gmail.com', '1234', '김용호', '1', '123345', '235');

update blli_member set member_email = 'sk1597530@gmail.com';



select bm.member_id, bm.member_email, bm.member_name 
	from blli_member bm, (select distinct bb.member_id from blli_baby bb where substr(to_char(baby_birthday), 7) = substr(to_char(sysdate), 7)) idt 
	where bm.member_id = idt.member_id;
	
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

insert into blli_baby(baby_name, baby_birthday, baby_sex, baby_photo, member_id) values('아기1', sysdate, '남', '사진', 'yongho');
insert into blli_baby(baby_name, baby_birthday, baby_sex, baby_photo, member_id) values('아기2', sysdate, '남', '사진', 'yongho');
insert into blli_baby(baby_name, baby_birthday, baby_sex, baby_photo, member_id) values('아기3', sysdate, '남', '사진', 'yongho');

select babyt.baby_name, babyt.baby_birthday, babyt.baby_photo 
	from (select bb.baby_name, bb.baby_birthday, bb.baby_photo from blli_baby bb where bb.member_id = 'yongho') babyt
	where substr(to_char(babyt.baby_birthday), 7) = substr(to_char(sysdate), 7);
	
select babyt.baby_name, babyt.baby_birthday, babyt.baby_photo 
	from (select bb.baby_name, bb.baby_birthday, bb.baby_photo from blli_baby bb where bb.member_id = #{value}) babyt
	where substr(to_char(babyt.baby_birthday), 7) = substr(to_char(sysdate), 7);

		
select * from blli_baby;


drop table blli_mailing cascade constraint;
CREATE TABLE blli_mailing (
	mail_form			VARCHAR2(40) NOT NULL primary key,
	mail_subject		VARCHAR2(100) NOT NULL,
	mail_content_file		VARCHAR2(30) NOT NULL
);


insert into blli_member(member_id, member_email, member_password, member_name, member_state, authority, recommending_baby_name) 
	values('sk159753', 'sk1597530@gmail.com', 'dd', 'dd', '0', 'dd', 'dd');
	
select * from blli_member;

insert into blli_mailing(mail_form, mail_subject, mail_content_file) values('getTemporaryPassword', '임시비밀번호입니다!', 'mailForm_getTemporaryPassword.vm');
insert into blli_mailing(mail_form, mail_subject, mail_content_file) values('recommendingMail', '블리가 제안하는 지금 아이에게 필요한 제품!', 'mailForm_recommendingMail.vm');
insert into blli_mailing(mail_form, mail_subject, mail_content_file) values('receiveLinkToGetTemporaryPasswordMail', '비밀번호를 잊으셨군요!', 'mailForm_receiveLinkToGetTemporaryPasswordMail.vm');

select * from blli_mailing;

select * from blli_small_product;

select product_db_insert_date from blli_small_product;

update blli_small_product set small_product_status='confirmed' where small_product_status='unconfirmed';


insert into blli_schedule (schedule_id, member_id, baby_name, schedule_date, schedule_title, schedule_location, schedule_content)
		values (blli_schedule_seq.nextval,'sk1597530@gmail.com','떵이','20001216','제모오옥','장소오오','내요오옹');
		
select * from blli_schedule;

delete from blli_schedule;

update blli_posting set posting_status='confirmed';

update blli_small_product set small_product_whentouse_min='2' where small_product_status='confirmed';
update blli_small_product set small_product_whentouse_max='8' where small_product_status='confirmed';


update blli_schedule set schedule_location = '0000000000000000', schedule_title = '00000000000000', schedule_content = '000000000000' where schedule_id = '266';
