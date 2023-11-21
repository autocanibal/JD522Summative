create database UniversityResourceManagement;

create table Departments(
dep_id int primary key not null,
dep_name varchar(255)
);

create table Courses(
course_id int primary key not null,
course_name varchar(255),
course_materials varchar(500),
grades varchar(1000) null,
students_ids varchar(1000) null,
dep_id int null,
fac_id int null,
foreign key (dep_id) references Departments(dep_id)
);

create table Faculties(
fac_id int primary key not null,
fac_name varchar(255),
dep_id int null,
course_id int null,
foreign key (dep_id) references Departments(dep_id),
foreign key (course_id) references Courses(course_id)
);
ALTER TABLE Courses
ADD FOREIGN KEY (fac_id) REFERENCES Faculties(fac_id);

create table Students(
stu_id int primary key not null,
stu_name varchar(255),
dep_id int null,
fac_id int null,
course_id int null,
grades varchar(1000) null,
course_materials varchar(500) null,
foreign key (dep_id) references Departments(dep_id),
foreign key (course_id) references Courses(course_id),
foreign key (fac_id) references Faculties(fac_id)
);