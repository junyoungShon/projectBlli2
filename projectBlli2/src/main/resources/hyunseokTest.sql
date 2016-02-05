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
select * from blli_small_product order by small_product_posting_count asc;
select small_product, small_product_id, small_product_posting_count from blli_small_product order by small_product_posting_count asc
select * from blli_small_product where 1496 > small_product_posting_count order by small_product_posting_count asc;
select small_product_posting_count from blli_small_product where small_product = '대성토이즈 뉴 말하는 고가 사다리차';
select count(*) from blli_small_product where small_product_posting_count = 0;
select * from blli_posting where small_product = '대성토이즈 뉴 말하는 고가 사다리차';

select posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, posting_scrape_count, posting_like_count, posting_dislike_count, posting_author, posting_date from(
	select ceil(rownum/5) as page, posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, 
	posting_scrape_count, posting_like_count, posting_dislike_count, posting_author, to_char(posting_date, 'YYYY.MM.DD') as posting_date 
	from blli_posting where small_product like '%' || '아이' || '%' and posting_status = 'confirmed'
) where page = '2'

create table test_log(
	category varchar2(300) not null
)
drop table test_log
insert into test_log values('a')
select * from test_log

update blli_mid_category set mid_category_whentouse_min = 0 where mid_category_whentouse_min > 0 or mid_category_whentouse_min is null and mid_category = '국내분유'

update blli_posting set posting_db_insert_date = '2016.02.04'

update blli_small_product set product_db_insert_date = '2016.02.04'

update blli_posting set posting_status = 'confirmed'

select * from blli_mid_category;

select  mid_category,mid_category_info,mid_category_main_photo_link,mid_category_whentouse_min,mid_category_whentouse_max,big_category,mid_category_id
		from BLLI_MID_CATEGORY
		where 16 >= mid_category_whentouse_min and 16 <= mid_category_whentouse_min

select * from blli_big_category;

select * from blli_mid_category;

select * from blli_small_product where posting_count is not null;

select count(*) from blli_small_product;

select count(*) from blli_posting;

select count(*) from blli_posting where posting_media_count < 30

select avg(posting_media_count) from blli_posting where posting_media_count < 30

select max(posting_media_count) from blli_posting;

select * from blli_posting;

select * from blli_posting where small_product='노발락 AD 450g'

select small_product_id, min_price, small_product, small_product_main_photo_link, small_product_whentouse_min, 
	small_product_whentouse_max, small_product_posting_count, small_product_score from(
	select ceil(rownum/10) as page, bl.small_product_id, bl.min_price, sp.small_product, sp.small_product_main_photo_link, sp.small_product_whentouse_min, 
	sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
		select b.small_product_id, min(b.buy_link_price) as min_price from (
			select small_product_id from blli_small_product where small_product like '%' || #{searchWord} || '%' and small_product_status = 'confirmed'
		)s, blli_small_prod_buy_link b where s.small_product_id = b.small_product_id  group by b.small_product_id
	)bl, blli_small_product sp where bl.small_product_id = sp.small_product_id order by sp.small_product_score desc
) where page = '1'

  select rownum, posting_url,small_product,small_product_id,posting_title,posting_summary,posting_content,posting_score,posting_like_count,
  posting_dislike_count,posting_media_count,posting_photo_link,posting_total_residence_time,posting_view_count,posting_scrape_count,posting_author,posting_date
  from blli_posting
  where posting_status = 'unconfirmed' and small_product_id = 6960322059 and rownum = 2
  order by posting_score desc

select * from blli_small_prod_buy_link where small_product_id =  5707335265 and rownum <= 5 order by buy_link_price asc
select * from blli_small_product where small_product_id = 6960322059

select * from blli_small_product where mid_category = '캐릭터/패션인형' and rownum <= 5 order by small_product_score desc

select posting_url, posting_photo_link from blli_posting where small_product = '금보 헬로키티 차' order by posting_score desc

select * from(
	select ceil(rownum/10) as page, bl.small_product_id, bl.min_price, sp.small_product, sp.small_product_main_photo_link, sp.small_product_whentouse_min, 
	sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
		select b.small_product_id, min(b.buy_link_price) as min_price from (
			select small_product_id from blli_small_product where small_product like '%' || '토끼' || '%' and small_product_status = 'confirmed'
		)s, blli_small_prod_buy_link b where s.small_product_id = b.small_product_id  group by b.small_product_id
	)bl, blli_small_product sp where bl.small_product_id = sp.small_product_id
) where page = 1

select sp.mid_category, bl.small_product_id, bl.min_price, sp.small_product, sp.small_product_main_photo_link, sp.small_product_whentouse_min, 
sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
	select b.small_product_id, min(b.buy_link_price) as min_price from (
		select small_product_id from blli_small_product where small_product = '' and small_product_status = 'confirmed'
	)s, blli_small_prod_buy_link b where s.small_product_id = b.small_product_id  group by b.small_product_id
)bl, blli_small_product sp where bl.small_product_id = sp.small_product_id

select * from(
	select ceil(rownum/10) as page, bl.small_product_id, bl.min_price, sp.small_product, sp.small_product_main_photo_link, sp.small_product_whentouse_min, 
	sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
		select b.small_product_id, min(b.buy_link_price) as min_price from (
			select small_product_id from blli_small_product where small_product like '%' || '' || '%' and small_product_status = 'confirmed'
		)s, blli_small_prod_buy_link b where s.small_product_id = b.small_product_id  group by b.small_product_id
	)bl, blli_small_product sp where bl.small_product_id = sp.small_product_id
) where page = '1'

select small_product, mid_category from blli_small_product where small_product_status = 'confirmed'

select sp.mid_category, bl.small_product_id, bl.min_price, sp.small_product, sp.small_product_main_photo_link, sp.small_product_whentouse_min, 
sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
	select b.small_product_id, min(b.buy_link_price) as min_price from (
		select small_product_id from blli_small_product where small_product = '포그니 토끼친구들 유아용 침구' and small_product_status = 'confirmed'
	)s, blli_small_prod_buy_link b where s.small_product_id = b.small_product_id  group by b.small_product_id
)bl, blli_small_product sp where bl.small_product_id = sp.small_product_id

select small_product_id, min_price, small_product, small_product_main_photo_link, small_product_whentouse_min, 
	small_product_whentouse_max, small_product_posting_count, small_product_score from(
	select ceil(rownum/10) as page, bl.small_product_id, bl.min_price, sp.small_product, sp.small_product_main_photo_link, sp.small_product_whentouse_min, 
	sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
		select b.small_product_id, min(b.buy_link_price) as min_price from (
			select small_product_id from blli_small_product where small_product like '%' || '' || '%' and small_product_status = 'confirmed' 
		)s, blli_small_prod_buy_link b where s.small_product_id = b.small_product_id  group by b.small_product_id 
	)bl, blli_small_product sp where bl.small_product_id = sp.small_product_id order by sp.small_product_score desc
) where page = '1'

select small_product_id, min_price, small_product, small_product_main_photo_link, small_product_whentouse_min, 
	small_product_whentouse_max, small_product_posting_count, small_product_score from(
	select ceil(rownum/10) as page, bl.small_product_id, bl.min_price, sp.small_product, sp.small_product_main_photo_link, sp.small_product_whentouse_min, 
	sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
		select b.small_product_id, min(b.buy_link_price) as min_price from (
			select small_product_id from blli_small_product where small_product like '%' || '' || '%' and small_product_status = 'confirmed'
		)s, blli_small_prod_buy_link b where s.small_product_id = b.small_product_id  group by b.small_product_id
	)bl, blli_small_product sp where bl.small_product_id = sp.small_product_id order by sp.small_product_score desc
) where page = '1'

select * from(
	select ceil(rownum/10) as page, bl.small_product_id, bl.min_price, sp.small_product, sp.small_product_main_photo_link, 
	sp.small_product_whentouse_min, sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
		select b.small_product_id, min(b.buy_link_price) as min_price from (
			select small_product_id from blli_small_product where mid_category = '낮잠이불' and small_product_status = 'confirmed'
		)s, blli_small_prod_buy_link b where s.small_product_id = b.small_product_id  group by b.small_product_id
	)bl, blli_small_product sp where bl.small_product_id = sp.small_product_id order by sp.small_product_score desc
) where page = '1'

select mid_category from blli_small_product where small_product = '포그니 토끼친구들 유아용 침구'

select * from blli_small_product where mid_category = '낮잠이불' and small_product_status = 'confirmed'

update blli_small_product set small_product_status = 'confirmed' where mid_category = '낮잠이불'

update blli_small_product set small_product_score = 87 where small_product = '큐비앤맘 포비 이불 요세트'

select ceil(count(*)/10) from blli_small_product where small_product like '%' || '' || '%' and small_product_status = 'confirmed'

select * from blli_small_prod_buy_link where small_product_id = 5707335265 and 5 >= rownum order by buy_link_price asc

select posting_url, posting_photo_link from blli_posting where small_product = '포그니 토끼친구들 유아용 침구' order by posting_score desc

update blli_posting set posting_status = 'confirmed' where small_product = '포그니 토끼친구들 유아용 침구'

select ceil(count(*)/10) from blli_small_product where mid_category = '낮잠이불'

update blli_small_product set small_product_status = 'confirmed' where small_product = '포그니 토끼친구들 유아용 침구'

select * from blli_small_product where mid_category = '낮잠이불' and 5 >= rownum and small_product_status = 'confirmed' and small_product != '포그니 토끼친구들 유아용 침구' order by small_product_score desc

select * from blli_posting where small_product like '%' || '토끼' || '%'

select small_product, posting_url, posting_photo_link from blli_posting where small_product like '%' || '큐비앤맘' || '%' and posting_status = 'unconfirmed' order by posting_score desc

select small_product_id, small_product, small_product_score from(
	select ceil(rownum/5) as page, small_product_id, small_product, small_product_score from(
		select small_product_id, small_product, small_product_score
		from blli_small_product where mid_category = '낮잠이불' and small_product_status = 'confirmed' and small_product != '포그니 비엔나 유아용 침구 이불 요세트' order by small_product_score desc
	)
) where page = '3'

update blli_small_product set small_product_score = 90 where small_product_id = '5707335265'
  
select * from(
	select ceil(rownum/5) as page, bl.small_product_id, bl.buy_link_price, sp.small_product, sp.small_product_main_photo_link, sp.small_product_whentouse_min, sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
		select b.small_product_id, min(b.buy_link_price) as buy_link_price from (
			select small_product_id from blli_small_product where mid_category = '캐릭터/패션인형'
		)s, blli_small_prod_buy_link b where s.small_product_id = b.small_product_id  group by b.small_product_id
	)bl, blli_small_product sp where bl.small_product_id = sp.small_product_id
) where page = '1'

select bl.small_product_id, bl.min_price, sp.small_product, sp.small_product_main_photo_link, sp.small_product_whentouse_min, sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
	select b.small_product_id, min(b.buy_link_price) as min_price from (
		select small_product_id from blli_small_product where small_product = '하은맘 프라임 샴푸의자'
	)s, blli_small_prod_buy_link b where s.small_product_id = b.small_product_id  group by b.small_product_id
)bl, blli_small_product sp where bl.small_product_id = sp.small_product_id

select bl.buy_link, bl.buy_link_price, bl.buy_link_delivery_cost, bl.buy_link_option, bl.seller, sp.small_product_id, sp.small_product_main_photo_link, 
sp.small_product_whentouse_min, sp.small_product_whentouse_max, sp.small_product_posting_count, sp.small_product_score from(
	select small_product_id, small_product_main_photo_link, small_product_whentouse_min, small_product_whentouse_max, small_product_posting_count, small_product_score 
	from blli_small_product where small_product = '하은맘 프라임 샴푸의자'
)sp, blli_small_prod_buy_link bl where sp.small_product_id = bl.small_product_id

select * from blli_small_prod_buy_link where small_product_id = '5666293165'

select * from blli_small_product where small_product_id = '5666293165';

select * from blli_small_prod_buy_link

select mid_category from blli_small_product;

select max(ceil(rownum/5)) from blli_posting where small_product like '%' || '' || '%' and posting_status = 'confirmed'

update blli_small_product set small_product_status = 'unconfirmed';

select * from blli_big_category;

select * from blli_posting;

select count(*) from blli_small_product;

update blli_small_product set search_time = '2016.01.26' where small_product_id = '6845396598';

select * from blli_posting where small_product like '%' || '아이팜' || '%';

update blli_posting set posting_status = 'confirmed' where small_product like '%' || '아이팜' || '%';

select * from blli_small_prod_buy_link;

select * from blli_mid_category;

update blli_posting set posting_score = 83, posting_like_count = 15, posting_dislike_count = 3, posting_scrape_count = 5 where posting_title = '아기욕조 아이팜 즐거운목욕시간';

select posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, posting_scrape_count, posting_like_count, posting_dislike_count, posting_author, posting_date from(
	select ceil(rownum/5) as page, posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, 
	posting_scrape_count, posting_like_count, posting_dislike_count, posting_author, posting_date from(
		select posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, 
		posting_scrape_count, posting_like_count, posting_dislike_count, posting_author, to_char(posting_date, 'YYYY.MM.DD') as posting_date 
		from blli_posting where small_product like '%' || '' || '%' and posting_status = 'confirmed' order by posting_score desc
	)
) where page = 1;

select * from blli_posting;

select * from blli_small_product where small_product_status = 'confirmed';

select small_product, small_product_id, small_product_posting_count, search_time from blli_small_product where small_product_status = 'unconfirmed' or small_product_status = 'confirmed' order by search_time asc nulls first;

select count(*) from blli_small_product;

select mid_category_id from blli_mid_category where mid_category = '유아동전집';

select * from blli_small_product where small_product_id = '8582539413';

select count(distinct(mid_category)) from blli_small_product;

select * from blli_small_product where small_product = '미미월드 똘똘이 울보 내동생';
select * from blli_small_product where small_product_id = '5666293165';

select small_product, mid_category, small_propduct_whentouse_min, small_propduct_whentouse_max, small_product_main_photo_link from(
	select ceil(rownum/5) as page, small_product, mid_category, small_propduct_whentouse_min, small_propduct_whentouse_max, small_product_main_photo_link 
	from blli_small_product where small_product_status = 'unconfirmed'
) where page = '100'

select max(ceil(rownum/5)) from blli_small_product where small_product_status = 'unconfirmed'

select * from blli_small_product;
select count(*) from blli_small_product where small_product_posting_count = 0;
select count(*) from blli_small_product where small_product_posting_count > 514;
select * from blli_small_product where mid_category = '캐릭터/패션인형';

select avg(small_product_posting_count) from blli_small_product where small_product_posting_count > 100
select * from blli_small_product order by small_product_posting_count desc

select count(*) from blli_small_product where small_product_posting_count > 100 order by small_product_posting_count asc

select * from blli_posting;
select count(posting_url) from blli_posting;

select page, posting_url, posting_title, small_product from(
	select ceil(rownum/10) as page, posting_url, posting_title, small_product from(
		select count(*) over (partition by posting_url) as small_product_count, posting_url, posting_title, small_product from blli_posting where posting_status = 'unconfirmed'
	) where small_product_count > 1
) where page = '170';

select * from(
	select ceil(row_num/5) as page, posting_url, posting_title, small_product from(
		select (dense_rank() over (order by posting_url)) row_num, posting_url, posting_title, small_product from(
			select count(*) over (partition by posting_url) as small_product_count, posting_url, posting_title, small_product from blli_posting where posting_status = 'unconfirmed'
		) where small_product_count > 1
	)
) where page = '168';

select count(*) from(
	select count(*) over (partition by posting_url) as small_product_count, posting_url from blli_posting where posting_status = 'unconfirmed'
) where small_product_count > 1;

select count(distinct(posting_url)) from(
	select count(*) over (partition by posting_url) as small_product_count, posting_url from blli_posting where posting_status = 'unconfirmed'
) where small_product_count > 1;

select max(b.small_product_posting_count) from(
	select small_product_id from blli_posting
)a, blli_small_product b where a.small_product_id = b.small_product_id;

select * from blli_posting where posting_title = '네추럴블라썸 프린세스라벨 구입 !! 핑크라벨과 비교 후기.';
select * from blli_posting where posting_url = 'http://blog.naver.com/1339cat/70141939182';
update blli_posting set small_product = #{smallProduct} where posting_url = 'http://blog.naver.com/1030yhm/220546395420';
update blli_posting set posting_status = 'unconfirmed' where posting_url = 'http://blog.naver.com/4lang2/60213053247';

alter database datafile '/db03/mydata.dbf' resize 1024m;

select * from blli_posting where small_product = '손가락인형 병아리';

select * from blli_posting;

select small_product, small_product_id, small_product_posting_count from blli_small_product where small_product_posting_count > 0 order by small_product_posting_count asc;

SELECT b.posting_title
FROM (SELECT rownum as rnum, posting_title FROM blli_posting) b
WHERE b.rnum BETWEEN 1000 AND 2000;

select * from blli_posting where small_product = 'C&H Creative 리락쿠마 인형 70cm';

drop table blli_posting_test cascade constraint;
CREATE TABLE blli_posting_test (
	posting_url          VARCHAR2(300) NOT NULL ,
	small_product   VARCHAR2(200) NOT NULL ,
	posting_title        VARCHAR2(450) NOT NULL , -- VARCHAR2(100) 에서 VARCHAR2(450)으로 수정!
	posting_summary      VARCHAR2(600) NOT NULL , -- VARCHAR2(300) 에서 VARCHAR2(600)으로 수정!
	posting_content      CLOB NOT NULL ,
	posting_score        NUMBER(4) default 0 ,
	posting_like_count   NUMBER(6) default 0 ,
	posting_dislike_count NUMBER(6) default 0 ,
	posting_media_count  NUMBER(3) NOT NULL , -- default 0 에서 NOT NULL로 수정!
	posting_photo_link   VARCHAR2(300) NOT NULL ,
	posting_total_residence_time NUMBER(8) default 0 ,
	posting_view_count      NUMBER(6) default 0 ,
	posting_scrape_count    NUMBER(3) default 0, -- 추가
	posting_author           VARCHAR2(100) NOT NULL, -- 추가
	posting_date             DATE NOT NULL, -- 추가
	posting_order            NUMBER(3) NOT NULL, -- 추가
	posting_reply_count      NUMBER(4) NOT NULL, -- 추가
	posting_status            VARCHAR2(30) NOT NULL, -- 추가
	constraint fk_posting_small_prod_test foreign key(small_product) references test_small_product(small_product),
	constraint pk_posting_test primary key(posting_url, small_product) 
);

select * from blli_posting_test;

drop table test_small_product;
create table test_small_product(
	small_product VARCHAR2(100) primary key
);
insert into test_small_product(small_product) values('하은맘 프라임 샴푸의자 업그레이드형');
insert into test_small_product(small_product) values('하은맘 프라임 샴푸의자');
insert into test_small_product(small_product) values('플라팜 허그샴푸의자');
insert into test_small_product(small_product) values('아가명가 샴푸의자');
insert into test_small_product(small_product) values('베이비캠프 컴포트 샴푸체어');

select small_product from test_small_product;

		
select posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, posting_scrape_count, 
		posting_like_count, posting_dislike_count, posting_author, to_char(posting_date,'YYYY.MM.DD') as posting_date
		from blli_posting_test where small_product like '%' || '업그레이드' || '%'
		
select bp.posting_url, bp.posting_title, bp.posting_photo_link, bp.posting_summary, bp.posting_score, bp.small_product, bp.posting_scrape_count, 
		bp.posting_like_count, bp.posting_dislike_count, bp.posting_author, to_char(bp.posting_date,'YYYY.MM.DD') as posting_date from(
	select b.small_product from(		
		select posting_url from blli_posting_test where small_product like '%' || '업그레이드' || '%'
	)p, blli_posting_test b where b.posting_url = p.posting_url 
)sp, blli_posting_test bp group by small_product having count(*) < 2;

select posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, posting_scrape_count, 
		posting_like_count, posting_dislike_count, posting_author, to_char(posting_date,'YYYY.MM.DD') as posting_date
		from blli_posting_test where posting_url
	
select bp.posting_url, bp.posting_title, bp.posting_photo_link, bp.posting_summary, bp.posting_score, bp.small_product, bp.posting_scrape_count, 
		bp.posting_like_count, bp.posting_dislike_count, bp.posting_author, to_char(bp.posting_date,'YYYY.MM.DD') as posting_date from(
	select count(*) over (partition by b.posting_url) as small_product_count, b.posting_url from(		
		select posting_url from blli_posting_test where small_product like '%' || '업그레이드' || '%'
	)p, blli_posting_test b where b.posting_url = p.posting_url 
)sp, blli_posting_test bp where sp.small_product_count = 1 and sp.posting_url = bp.posting_url;

select bp.posting_url, bp.posting_title, bp.posting_photo_link, bp.posting_summary, bp.posting_score, bp.small_product, bp.posting_scrape_count,
	bp.posting_like_count, bp.posting_dislike_count, bp.posting_author, to_char(bp.posting_date,'YYYY.MM.DD') as posting_date from(
	select count(*) over (partition by b.posting_url) as small_product_count, b.posting_url from(		
		select posting_url from blli_posting where small_product like '%' || '' || '%' and posting_status = 'confirmed'
	)p, blli_posting b where b.posting_url = p.posting_url 
)sp, blli_posting bp where sp.small_product_count = 1 and sp.posting_url = bp.posting_url;

alter table blli_posting rename column posting_scrap_count to posting_scrape_count; 

select posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, posting_scrape_count, posting_like_count, posting_dislike_count, posting_author, posting_date from(
	select ceil((dense_rank() over (order by posting_url))/5) page, posting_url, posting_title, posting_photo_link, posting_summary, posting_score, small_product, 
	posting_scrape_count, posting_like_count, posting_dislike_count, posting_author, to_char(posting_date, 'YYYY.MM.DD') as posting_date 
	from blli_posting where small_product like '%' || '' || '%' and posting_status = 'confirmed'
) where page = '3';

update blli_posting set posting_status = 'unconfirmed';

select * from blli_posting;

select bp.posting_url, bp.posting_title, bp.posting_photo_link, bp.posting_summary, bp.posting_score, bp.small_product, bp.posting_scrape_count, 
bp.posting_like_count, bp.posting_dislike_count, bp.posting_author, to_char(bp.posting_date,'YYYY.MM.DD') as posting_date from(
	select count(*) over (partition by b.posting_url) as small_product_count, b.posting_url from(		
		select posting_url from blli_posting_test
	)p, blli_posting_test b where b.posting_url = p.posting_url 
)sp, blli_posting_test bp where sp.small_product_count = 1 and sp.posting_url = bp.posting_url
		
select posting_url, posting_title, small_product from(
	select count(*) over (partition by posting_url) as small_product_count, posting_url, posting_title, small_product from blli_posting_test
)s where s.small_product_count > 1
		
select small_product from blli_posting_test where posting_url = 'http://blog.naver.com/2000tsk/220330030245';
		
CREATE TABLE blli_big_category (
	big_category         VARCHAR2(50) NOT NULL primary key,
	category_id          VARCHAR2(30) NOT NULL -- 추가
);
drop table blli_big_category;

select * from blli_big_category;

CREATE TABLE blli_mid_category (
	mid_category         VARCHAR2(100) NOT NULL, -- VARCHAR2(50)을 VARCHAR2(100)으로 수정
	mid_category_info    VARCHAR2(250) NULL ,
	mid_category_main_photo_link VARCHAR2(300) NOT NULL ,
	mid_category_whentouse NUMBER(20) NULL ,
	big_category         VARCHAR2(50) NOT NULL ,
	category_id          VARCHAR2(30) NOT NULL, -- 추가
	constraint pk_mid_category primary key (mid_category, category_id), -- category와 id를 복합키로 변경
	constraint fk_mid_cate_big_cate foreign key(big_category) references blli_big_category(big_category)
);

update blli_mid_category set category_id = 'asd' where mid_category = '가디건' and big_category = '임부복';

select * from blli_mid_category;

select category_id, big_category from blli_big_category

create table dayday_test(
	dayday DATE primary key
)
insert into dayday_test(dayday) values('2011.03.01');
select * from dayday_test;

select * from blli_mid_category where mid_category = '수경';
select mid_category from blli_mid_category;

select * from blli_small_product order by small_product_posting_count desc;
select small_product, small_product_posting_count from blli_small_product order by small_product_posting_count desc;
select * from blli_small_product where small_product = '지구촌친구 뽀글이 가방걸이';
select count(*) from blli_small_product;
select distinct(mid_category) from blli_small_product;
select * from blli_small_product where mid_category = '유아음료';

조건1)
select * from blli_small_product where small_product_posting_count > 1000;
조건2)
select * from blli_small_product where small_product_posting_count >= 20 and small_product_posting_count <= 50 and naver_shopping_order > 10;

select * from blli_small_prod_buy_link;

select posting_url, posting_title, posting_summary, small_product from(
	select ceil(rownum/5) as page, posting_url, posting_title, posting_summary, small_product from(
		select count(*) over (partition by posting_url) as count_posting_url, posting_url, posting_title, posting_summary, small_product from blli_posting where posting_status = 'unconfirmed'
	) where count_posting_url = 1
) where page = '10';

select count(*) from(
	select count(*) over (partition by posting_url) as count_posting_url from blli_posting where posting_status = 'unconfirmed'
) where count_posting_url = 2









































		
		
