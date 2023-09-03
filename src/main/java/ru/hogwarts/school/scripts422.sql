CREATE table human(
                      id serial primary key,
                      name varchar(150),
                      age int,
                      driver_license boolean);

CREATE table car(
                      id serial primary key,
                      brand varchar(150),
                      model varchar(150),
                      cost money);

alter table human add column car_id serial;
alter table human
add constraint FK_car_id FOREIGN KEY (car_id)
references human.car_id(car_id)
on update set null

select s.name, s.age, faculty.name
from student as s
         inner join faculty on student.faculty_id = faculty.id;

select s.id, s.name, avtr.id
from student as s
         inner join avatar avtr on s.id = avtr.student_id;