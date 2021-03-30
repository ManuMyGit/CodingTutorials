delete from course_student;
delete from address;
delete from course;
delete from student;
delete from teacher;

insert into address (id, street, city, state, zip_code, country) values
   (100, '47 W 13th St', 'New York City', 'NY', '10011', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (101, '20 Cooper Square', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (102, '1 E 2nd St', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (103, '75 3rd Ave', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (104, 'Metrotech Center', 'Brooklyn', 'NY', '11201', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (105, '721 Broadway', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (106, '40 E 7th St', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (107, '334 E 25th St', 'New York City', 'NY', '10010', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (108, '120 E 14th St', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (109, '120 E 12th St', 'New York City', 'NY', '10003', 'US');

insert into teacher (id, first_name, last_name) values
   (1, 'Albert', 'Einstein');
insert into teacher (id, first_name, last_name) values
   (2, 'Isaac', 'Newton');
insert into teacher (id, first_name, last_name) values
   (3, 'Niels', 'Bohr');
insert into teacher (id, first_name, last_name) values
   (4, 'Rosalind', 'Franklin');
insert into teacher (id, first_name, last_name) values
   (5, 'Marie', 'Curie');

insert into student(id, first_name, last_name, gender, address, age) values
   (1, 'William', 'Sheakespeare', 'MALE', 100, 50);
insert into student(id, first_name, last_name, gender, address, age) values
   (2, 'Miguel', 'de Cervantes', 'MALE', 101, 51);
insert into student(id, first_name, last_name, gender, address, age) values
   (3, 'Dante', 'Alighieri', 'MALE', 102, 52);
insert into student(id, first_name, last_name, gender, address, age) values
   (4, 'Antonio', 'Machado', 'MALE', 103, 53);
insert into student(id, first_name, last_name, gender, address, age) values
   (5, 'Miguel', 'Hernandez', 'MALE', 104, 54);
insert into student(id, first_name, last_name, gender, address, age) values
   (6, 'Emily', 'Dickinson', 'FEMALE', 105, 55);
insert into student(id, first_name, last_name, gender, address, age) values
   (7, 'Maya', 'Angelou', 'FEMALE', 106, 56);
insert into student(id, first_name, last_name, gender, address, age) values
   (8, 'Sylvia', 'Plath', 'FEMALE', 107, 57);
insert into student(id, first_name, last_name, gender, address, age) values
   (9, 'Emma', 'Lazarus', 'FEMALE', 108, 58);
insert into student(id, first_name, last_name, gender, address, age) values
   (10, 'Mary', 'Shelley', 'FEMALE', 109, 59);

insert into course(id, title, teacher) values
   (1, 'General relativity', 1);
insert into course(id, title, teacher) values
   (2, 'Special relativity', 1);
insert into course(id, title, teacher) values
   (3, 'Gravity', 2);
insert into course(id, title, teacher) values
   (4, 'The structure of the atom', 3);
insert into course(id, title, teacher) values
   (5, 'Structure of the DNA', 4);
insert into course(id, title, teacher) values
   (6, 'Radioactivity', 5);
insert into course(id, title, teacher) values
   (7, 'Radioactive elements', 5);

insert into course_student (student, course) values
   (1, 1);
insert into course_student (student, course) values
   (2, 2);
insert into course_student (student, course) values
   (3, 3);
insert into course_student (student, course) values
   (4, 4);
insert into course_student (student, course) values
   (5, 5);
insert into course_student (student, course) values
   (6, 6);
insert into course_student (student, course) values
   (7, 7);
insert into course_student (student, course) values
   (8, 1);
insert into course_student (student, course) values
   (9, 2);
insert into course_student (student, course) values
   (10, 3);