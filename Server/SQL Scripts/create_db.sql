CREATE DATABASE PERIPATOS;

CREATE TABLE PERIPATOS.USERS(
	username varchar(45) NOT NULL,
	password varchar(20) NOT NULL,
	enabled smallint NOT NULL,
	authority varchar(20) NOT NULL,
	PRIMARY KEY(username)
);

CREATE TABLE PERIPATOS.AUTHORITIES(
	username varchar(45) NOT NULL,
	authority varchar(20) NOT NULL,
	FOREIGN KEY (username) REFERENCES PERIPATOS.USERS(username)
);

CREATE TABLE PERIPATOS.COURSE(
	course_id bigint NOT NULL AUTO_INCREMENT,
	name varchar(512) NOT NULL,
	PRIMARY KEY(course_id)
);

CREATE TABLE PERIPATOS.GEOLOCATION(
	geolocation_id bigint NOT NULL AUTO_INCREMENT,
	latitude double NOT NULL,
	longitude double NOT NULL,
	PRIMARY KEY (geolocation_id)
);

CREATE TABLE PERIPATOS.QUESTION(
	question_id bigint NOT NULL AUTO_INCREMENT,
	title varchar(512) NOT NULL,
	body varchar(4096),
	PRIMARY KEY (question_id)
);

CREATE TABLE PERIPATOS.ANSWER(
	answer_id bigint NOT NULL AUTO_INCREMENT,
	submission_date date NOT NULL,
	body varchar(4096) NOT NULL,
	PRIMARY KEY (answer_id)
);

CREATE TABLE PERIPATOS.ASSIGNMENT(
	assignment_id bigint NOT NULL AUTO_INCREMENT,
	name varchar(512) NOT NULL,
	due_date date NOT NULL,
	PRIMARY KEY (assignment_id)
);

CREATE TABLE PERIPATOS.USER_ASSIGNMENT(
	username varchar(45) NOT NULL,
	assignment_id bigint NOT NULL,
	FOREIGN KEY (username) REFERENCES PERIPATOS.USERS(username),
	FOREIGN KEY (assignment_id) REFERENCES PERIPATOS.ASSIGNMENT(assignment_id)
);

CREATE TABLE PERIPATOS.COURSE_USER(
	course_id bigint NOT NULL,
	username varchar(45) NOT NULL,
	FOREIGN KEY (course_id) REFERENCES PERIPATOS.COURSE(course_id),
	FOREIGN KEY (username) REFERENCES PERIPATOS.USERS(username)
);

CREATE TABLE PERIPATOS.COURSE_ASSIGNMENT(
	course_id bigint NOT NULL,
	assignment_id bigint NOT NULL,
	FOREIGN KEY (course_id) REFERENCES PERIPATOS.COURSE(course_id),
	FOREIGN KEY (assignment_id) REFERENCES PERIPATOS.ASSIGNMENT(assignment_id)
);

CREATE TABLE PERIPATOS.USER_QUESTION(
	username varchar(45) NOT NULL,
	question_id bigint NOT NULL,
	FOREIGN KEY (username) REFERENCES PERIPATOS.USERS(username),
	FOREIGN KEY (question_id) REFERENCES PERIPATOS.QUESTION(question_id)
);

CREATE TABLE PERIPATOS.QUESTION_GEOLOCATION(
	question_id bigint NOT NULL,
	geolocation_id bigint NOT NULL,
	FOREIGN KEY (question_id) REFERENCES PERIPATOS.QUESTION(question_id),
	FOREIGN KEY (geolocation_id) REFERENCES PERIPATOS.GEOLOCATION(geolocation_id)
);

CREATE TABLE PERIPATOS.USER_ANSWER(
	username varchar(45) NOT NULL,
	answer_id bigint NOT NULL,
	FOREIGN KEY (username) REFERENCES PERIPATOS.USERS(username),
	FOREIGN KEY (answer_id) REFERENCES PERIPATOS.ANSWER(answer_id)
);

CREATE TABLE PERIPATOS.ASSIGNMENT_QUESTION(
	assignment_id bigint NOT NULL,
	question_id bigint NOT NULL,
	FOREIGN KEY (assignment_id) REFERENCES PERIPATOS.ASSIGNMENT(assignment_id),
	FOREIGN KEY (question_id) REFERENCES PERIPATOS.QUESTION(question_id)
);

CREATE TABLE PERIPATOS.ANSWER_QUESTION_ASSIGNMENT_COURSE(
	answer_id bigint NOT NULL,
	question_id bigint NOT NULL,
	assignment_id bigint NOT NULL,
	course_id bigint NOT NULL,
	FOREIGN KEY (answer_id) REFERENCES PERIPATOS.ANSWER(answer_id),
	FOREIGN KEY (question_id) REFERENCES PERIPATOS.QUESTION(question_id),
	FOREIGN KEY (assignment_id) REFERENCES PERIPATOS.ASSIGNMENT(assignment_id),
	FOREIGN KEY (course_id) REFERENCES PERIPATOS.COURSE(course_id)
);