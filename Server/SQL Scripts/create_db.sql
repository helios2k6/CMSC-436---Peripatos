CREATE DATABASE PERIPATOS;

CREATE TABLE PERIPATOS.USER(
	id int,
	name varchar(45),
	type varchar(45),
	PRIMARY KEY(id)
);

CREATE TABLE PERIPATOS.COURSE(
	id int,
	name varchar(512),
	PRIMARY KEY(id)
);

CREATE TABLE PERIPATOS.GEOLOCATION(
	id int,
	latitude double,
	longitude double,
	PRIMARY KEY (id)
);

CREATE TABLE PERIPATOS.QUESTION(
	id int,
	title varchar(512),
	body varchar(4096),
	PRIMARY KEY (id)
);

CREATE TABLE PERIPATOS.ANSWER(
	id int,
	submission_date date,
	body varchar(4096),
	PRIMARY KEY (id)
);

CREATE TABLE PERIPATOS.ASSIGNMENT(
	id int,
	name varchar(512),
	due_date date,
	PRIMARY KEY (id)
);

CREATE TABLE PERIPATOS.USER_ASSIGNMENT(
	user_id int,
	assignment_id int,
	FOREIGN KEY (user_id) REFERENCES PERIPATOS.USER(id),
	FOREIGN KEY (assignment_id) REFERENCES PERIPATOS.ASSIGNMENT(id)
);

CREATE TABLE PERIPATOS.COURSE_USER(
	course_id int,
	user_id int,
	FOREIGN KEY (course_id) REFERENCES PERIPATOS.COURSE(id),
	FOREIGN KEY (user_id) REFERENCES PERIPATOS.USER(id)
);

CREATE TABLE PERIPATOS.COURSE_ASSIGNMENT(
	course_id int,
	assignment_id int,
	FOREIGN KEY (course_id) REFERENCES PERIPATOS.COURSE(id),
	FOREIGN KEY (assignment_id) REFERENCES PERIPATOS.ASSIGNMENT(id)
);

CREATE TABLE PERIPATOS.USER_QUESTION(
	user_id int,
	question_id int,
	FOREIGN KEY (user_id) REFERENCES PERIPATOS.USER(id),
	FOREIGN KEY (question_id) REFERENCES PERIPATOS.QUESTION(id)
);

CREATE TABLE PERIPATOS.QUESTION_GEOLOCATION(
	question_id int,
	geolocation_id int,
	FOREIGN KEY (question_id) REFERENCES PERIPATOS.QUESTION(id),
	FOREIGN KEY (geolocation_id) REFERENCES PERIPATOS.GEOLOCATION(id)
);

CREATE TABLE PERIPATOS.USER_ANSWER(
	user_id int,
	answer_id int,
	FOREIGN KEY (user_id) REFERENCES PERIPATOS.USER(id),
	FOREIGN KEY (answer_id) REFERENCES PERIPATOS.ANSWER(id)
);

CREATE TABLE PERIPATOS.ASSIGNMENT_QUESTION(
	assignment_id int,
	question_id int,
	FOREIGN KEY (assignment_id) REFERENCES PERIPATOS.ASSIGNMENT(id),
	FOREIGN KEY (question_id) REFERENCES PERIPATOS.QUESTION(id)
);

CREATE TABLE PERIPATOS.ANSWER_QUESTION_ASSIGNMENT_COURSE(
	answer_id int,
	question_id int,
	assignment_id int,
	course_id int,
	FOREIGN KEY (answer_id) REFERENCES PERIPATOS.ANSWER(id),
	FOREIGN KEY (question_id) REFERENCES PERIPATOS.QUESTION(id),
	FOREIGN KEY (assignment_id) REFERENCES PERIPATOS.ASSIGNMENT(id),
	FOREIGN KEY (course_id) REFERENCES PERIPATOS.COURSE(id)
);