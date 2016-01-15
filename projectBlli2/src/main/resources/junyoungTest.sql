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