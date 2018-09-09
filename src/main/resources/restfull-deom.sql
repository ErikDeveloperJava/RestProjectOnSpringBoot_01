create database rest_demo character set utf8 collate utf8_general_ci;

use rest_demo;

create table user(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  surname varchar(255) not null ,
  email varchar(255) not null ,
  password varchar(255) not null ,
  role varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table product(
  id int not null auto_increment primary key ,
  title varchar(255) not null ,
  descritpion text not null ,
  price double not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table category(
  id int not null auto_increment primary key ,
  name varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table product_category(
  product_id int not null ,
  category_id int not null ,
  foreign key (product_id) references product(id) on delete cascade ,
  foreign key (category_id) references category(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;