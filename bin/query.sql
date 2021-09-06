create database IF NOT EXISTS users;

USE users;

create table if not EXISTS user_info(
username varchar(50) PRIMARY KEY,
password varchar(50) not null);

insert into user_info () values ("admin", "admin");
insert into user_info () values ("mysql", "12345");


