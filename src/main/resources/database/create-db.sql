DROP TABLE class_schedule IF EXISTS;
DROP TABLE student_class IF EXISTS;
DROP TABLE student IF EXISTS;
DROP TABLE class IF EXISTS;

CREATE TABLE student (
  id INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(50),
  email  VARCHAR(50)
);

CREATE TABLE class (
  id INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(30),
  description VARCHAR(300),
  teacher_name VARCHAR(50),
  time_minutes INTEGER

);

CREATE TABLE class_schedule (
  id INTEGER IDENTITY PRIMARY KEY,
  class_id INTEGER FOREIGN KEY REFERENCES class(id) ON DELETE CASCADE,
  start_time TIMESTAMP,

);

CREATE TABLE student_class (
  id INTEGER IDENTITY PRIMARY KEY,
  student_id INTEGER FOREIGN KEY REFERENCES student(id),
  class_id INTEGER FOREIGN KEY REFERENCES class(id) ON DELETE CASCADE,
);