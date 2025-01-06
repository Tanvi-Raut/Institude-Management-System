create database  universitymanagementdb;
use universitymanagementdb;

create table student
(
name varchar(40),
sname varchar(40),
roll_no varchar(20)primary key,
dob varchar(40),
address varchar(100),
phone varchar(12),
email varchar(40)unique,
class_x varchar(20),
class_xii varchar(20),
aadhar varchar(20)unique,
course varchar(40),
branch varchar(40)

);
commit;
select * from  student;

create table teacher
(
name varchar(20),
sname varchar(20),
emp_id varchar(20) primary key,
dob varchar(40),
address varchar(100),
phone varchar(40),
email varchar(40) unique,
class_x varchar(20),
class_xii varchar(20),
aadhar varchar(20)unique,
qualification varchar(40),
department varchar(40)
);
commit;

select * from teacher;



create table studentleave
(
roll_no varchar (20),
duration varchar(20),
ldate varchar(20));
commit;
select * from studentleave;


create table teacherleave
(
emp_id varchar(20),
duration varchar(20),
ldate varchar(20));
commit;
select * from teacherleave;


create table fee
(
   course varchar(20),
   semister1 varchar(20),
   semister2 varchar(20),
   semister3 varchar(20),
   semister4 varchar(20),
   semister5 varchar(20),
   semister6 varchar(20),
   semister7 varchar(20),
   semister8 varchar(20)
);
insert into fee values("BTech","48000","43000","43000","43000","43000","43000","43000","43000");
insert into fee values("Bsc","40000","35000","35000","35000","35000","35000","Invalid Sem","Invalid Sem");
insert into fee values("BCA","35000","34000","34000","34000","34000","34000","Invalid Sem","Invalid Sem");
insert into fee values("MTech","65000","60000","60000","60000","Invalid Sem","Invalid Sem","Invalid Sem","Invalid Sem");
insert into fee values("Msc","47500","45000","45000","45000","Invalid Sem","Invalid Sem","Invalid Sem","Invalid Sem");
insert into fee values("MCA","43000","42000","49000","45000","Invalid Sem","Invalid Sem","Invalid Sem","Invalid Sem");
insert into fee values("Bcom","22000","20000","20000","20000","20000","20000","Invalid Sem","Invalid Sem");
insert into fee values("Mcom","36000","30000","30000","30000","Invalid Sem","Invalid Sem","Invalid Sem","Invalid Sem");
insert into fee values("BA","45000","38000","38000","39000","38000","38000","Invalid Sem","Invalid Sem");
insert into fee values("MA","65000","60000","60000","60000","Invalid Sem","Invalid Sem","Invalid Sem","Invalid Sem");
commit;
select * from fee;



create table collegefee 
(
  roll_no varchar(20),
  course varchar(20),
  branch varchar(20),
  semester varchar(20),
   total varchar(20));
  commit;
  select * from collegefee;
 
  
  create table mprint 
  (
    subject varchar(20),
    total_m int(10),
    obtained_m int (10)
    );
    commit;
    select * from mprint;
    
    
    



