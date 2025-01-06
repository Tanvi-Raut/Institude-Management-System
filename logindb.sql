create database logindb;
use logindb;
create table login
(
 id int primary key  auto_increment,
 first_name varchar(20) ,
 middle_name varchar(20),
 last_name varchar(20),
 address varchar(150) ,
 username varchar(20),
 pass varchar(20) 
 );
 commit;
 insert into login values("Tanu123","12345");
select * from login;


create table first
(
 id int,
 name varchar(20)
);
select * from first;






 
 