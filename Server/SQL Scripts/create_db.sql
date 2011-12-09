CREATE DATABASE peripatos;

CREATE TABLE peripatos.users(
	username varchar(45) NOT NULL,
	password varchar(20) NOT NULL,
	enabled smallint NOT NULL,
	authority varchar(20) NOT NULL,
	PRIMARY KEY(username)
);

CREATE TABLE peripatos.authorities(
	username varchar(45) NOT NULL,
	authority varchar(20) NOT NULL,
	FOREIGN KEY (username) REFERENCES peripatos.users(username)
);

CREATE TABLE peripatos.course(
	courseId bigint NOT NULL AUTO_INCREMENT,
	name varchar(512) NOT NULL,
	PRIMARY KEY(courseId)
);

CREATE TABLE peripatos.geolocation(
	geolocationId bigint NOT NULL AUTO_INCREMENT,
	latitude double NOT NULL,
	longitude double NOT NULL,
	PRIMARY KEY (geolocationId)
);

CREATE TABLE peripatos.question(
	questionId bigint NOT NULL AUTO_INCREMENT,
	title varchar(512) NOT NULL,
	body varchar(4096),
	PRIMARY KEY (questionId)
);

CREATE TABLE peripatos.answer(
	answerId bigint NOT NULL AUTO_INCREMENT,
	submissionDate date NOT NULL,
	body varchar(4096) NOT NULL,
	PRIMARY KEY (answerId)
);

CREATE TABLE peripatos.assignment(
	assignmentId bigint NOT NULL AUTO_INCREMENT,
	name varchar(512) NOT NULL,
	dueDate date NOT NULL,
	PRIMARY KEY (assignmentId)
);

CREATE TABLE peripatos.user_assignment(
	username varchar(45) NOT NULL,
	assignmentId bigint NOT NULL,
	FOREIGN KEY (username) REFERENCES peripatos.users(username),
	FOREIGN KEY (assignmentId) REFERENCES peripatos.assignment(assignmentId)
);

CREATE TABLE peripatos.course_user(
	courseId bigint NOT NULL,
	username varchar(45) NOT NULL,
	FOREIGN KEY (courseId) REFERENCES peripatos.course(courseId),
	FOREIGN KEY (username) REFERENCES peripatos.users(username)
);

CREATE TABLE peripatos.course_assignment(
	courseId bigint NOT NULL,
	assignmentId bigint NOT NULL,
	FOREIGN KEY (courseId) REFERENCES peripatos.course(courseId),
	FOREIGN KEY (assignmentId) REFERENCES peripatos.assignment(assignmentId)
);

CREATE TABLE peripatos.user_question(
	username varchar(45) NOT NULL,
	questionId bigint NOT NULL,
	FOREIGN KEY (username) REFERENCES peripatos.users(username),
	FOREIGN KEY (questionId) REFERENCES peripatos.question(questionId)
);

CREATE TABLE peripatos.question_geolocation(
	questionId bigint NOT NULL,
	geolocationId bigint NOT NULL,
	FOREIGN KEY (questionId) REFERENCES peripatos.question(questionId),
	FOREIGN KEY (geolocationId) REFERENCES peripatos.geolocation(geolocationId)
);

CREATE TABLE peripatos.user_answer(
	username varchar(45) NOT NULL,
	answerId bigint NOT NULL,
	FOREIGN KEY (username) REFERENCES peripatos.users(username),
	FOREIGN KEY (answerId) REFERENCES peripatos.answer(answerId)
);

CREATE TABLE peripatos.assignment_question(
	assignmentId bigint NOT NULL,
	questionId bigint NOT NULL,
	FOREIGN KEY (assignmentId) REFERENCES peripatos.assignment(assignmentId),
	FOREIGN KEY (questionId) REFERENCES peripatos.question(questionId)
);

CREATE TABLE peripatos.answer_question(
	answerId bigint NOT NULL,
	questionId bigint NOT NULL,
	FOREIGN KEY (answerId) REFERENCES peripatos.answer(answerId),
	FOREIGN KEY (questionId) REFERENCES peripatos.question(questionId)
);

CREATE TABLE peripatos.answer_assignment(
	answerId bigint NOT NULL,
	assignmentId bigint NOT NULL,
	FOREIGN KEY (answerId) REFERENCES peripatos.answer(answerId),
	FOREIGN KEY (assignmentId) REFERENCES peripatos.assignment(assignmentId)
);

CREATE TABLE peripatos.answer_course(
	answerId bigint NOT NULL,
	courseId bigint NOT NULL,
	FOREIGN KEY (answerId) REFERENCES peripatos.answer(answerId),
	FOREIGN KEY (courseId) REFERENCES peripatos.course(courseId)
);