drop database huiwang;
create database huiwang character set utf8;
use huiwang;
set names utf8;

create table users(
    id              bigint primary key not null auto_increment,
	real_name       varchar(30), 
	login_name      varchar(30), 
	passwd          varchar(30),
	id_number       varchar(30),
	sex             varchar(5),
	photo_name  	varchar(200),
	status          varchar(10),
	city_id         bigint,
	province_id     bigint,
	gmt_login     	datetime,
	gmt_created     datetime,
	gmt_modified    datetime
) default charset=utf8;
alter table users add index login_name_index(login_name);
	
create table article(
    id              bigint primary key not null auto_increment,
    title     		varchar(150),
	simple_content  varchar(300),
	content 		text,
	topic_id		bigint,
	topic_name      varchar(50),
	user_id  	    bigint,
	status          varchar(10),
	gmt_created     datetime,
	gmt_modified    datetime
)default charset=utf8;
alter table article add index article_topic_index(topic_id),
add index article_user_index(user_id);

create table article_comment(
    id              bigint primary key not null auto_increment,
	simple_comment  varchar(300),
	comment 		text,
	article_id		bigint,
	user_id  	    bigint,
	status          varchar(10),
	gmt_created     datetime,
	gmt_modified    datetime
)default charset=utf8;
alter table article_comment add index comment_article_index(article_id),
add index comment_user_index(user_id);

create table article_statis(
    id              bigint primary key not null auto_increment,
    read_size       int default 0 comment '阅读次数',
	comment_size	int default 0 comment '评论次数',
	favorite_size 	int default 0 comment '收藏人数',
	care_size		int default 0 comment '关注人数',
	forward_size    int default 0 comment '转发次数',
	praise_size		int default 0 comment '赞人数',
	article_id  	bigint,
	gmt_created     datetime,
	gmt_modified    datetime
)default charset=utf8;
alter table article_statis add index statis_article_index(article_id);

create table article_care(
    id              bigint primary key not null auto_increment,
	article_id		bigint,
	user_id  	    bigint,
	status          varchar(10),
	gmt_created     datetime,
	gmt_modified    datetime
)default charset=utf8;
alter table article_care add index article_care_index(article_id),
add index care_user_index(user_id);

create table article_praise(
    id              bigint primary key not null auto_increment,
	article_id		bigint,
	user_id  	    bigint,
	status          varchar(10),
	gmt_created     datetime,
	gmt_modified    datetime
)default charset=utf8;
alter table article_praise add index article_praise_index(article_id),
add index praise_user_index(user_id);

create table topic(
    id              bigint primary key not null auto_increment,
	t_name			varchar(50),
	status          varchar(10),
	gmt_created     datetime,
	gmt_modified    datetime
)default charset=utf8;

insert into topic values(1, '花季雨季', 'normal', now(), now());
insert into topic values(2, '生活点滴', 'normal', now(), now());
insert into topic values(3, '亲情', 'normal', now(), now());
insert into topic values(4, '心情', 'normal', now(), now());
insert into topic values(5, '坎坷', 'normal', now(), now());
insert into topic values(6, '奋斗', 'normal', now(), now());
insert into topic values(7, '童年', 'normal', now(), now());
insert into topic values(8, '其他', 'normal', now(), now());

create table user_idea(
    id              bigint primary key not null auto_increment,
	content			varchar(500),
	user_id         bigint,
	gmt_created     datetime,
	gmt_modified    datetime
)default charset=utf8;
