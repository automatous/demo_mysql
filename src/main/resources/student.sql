drop table if EXISTS t_student;
create table t_student
(
    stuno int(4),
    stuname varchar(10),
    gradeId int(3)
);
insert into t_student values(1,'zs',23);
insert into t_student values(2,'ls',24);
insert into t_student values(3,'ww',25);