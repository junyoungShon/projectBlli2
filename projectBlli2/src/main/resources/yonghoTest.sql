

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


drop table blli_mailing cascade constraint;
CREATE TABLE blli_mailing (
	mail_form			VARCHAR2(20) NOT NULL primary key,
	mail_subject		VARCHAR2(100) NOT NULL,
	mail_content_file		VARCHAR2(30) NOT NULL
);


insert into blli_member(member_id, member_email, member_password, member_name, member_state, authority, recommending_baby_name) 
	values('sk159753', 'sk1597530@gmail.com', 'dd', 'dd', '0', 'dd', 'dd');
	
select * from blli_member;

insert into blli_mailing(mail_form, mail_subject, mail_content_file) values('findPassword', 'mail_subject', 'mailForm_findPassword.vm');
select * from blli_mailing;