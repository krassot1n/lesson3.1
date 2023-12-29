ALTER TABLE student
ADD CONSTRAINT age_constraint CHECK(age > 16);

ALTER TABLE student
ALTER COLUMN name set not null;

ALTER table student
add constraint unique_name unique(name);

ALTER table faculty
add constraint unique_faculty_pair unique(name,color);

ALTER table student
alter column age set default 20;