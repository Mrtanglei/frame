use study;
drop table if exists other_user_account;
create table other_user_account(
  id bigint(20) auto_increment primary key,
  account varchar(255) not null comment '账户',
  is_admin bit(1) default false not null comment '是否是管理员',
  is_system bit(1) default false not null comment '是否是超级管理员',
  password varchar(255) not null comment '密码',
  password_salty varchar(255) not null comment '密码加密盐'
)engine = InnoDB default charset = utf8 comment '账户表';

insert into other_user_account values (default ,'admin',true,true,'f7c2f7c26d7c3ecdb7f8f1e15ceeff3e',
'bade8208e4a286d12b67da29ba2c2e6b7f6a935e69f9e4941c471b279eb57dfccb53b966bb008522e33737f44c63b4e7');

drop table if exists other_login_record;
create table other_login_record(
  id bigint(20) auto_increment primary key,
  user_account bigint(20) not null comment '登录账户',
  login_time datetime not null comment '登录时间',
  ip varchar(255) comment '登录的ip地址'
)engine = InnoDB default charset = utf8 comment '登录记录表';