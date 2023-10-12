CREATE SCHEMA IF NOT EXISTS university;
USE university;

CREATE TABLE IF NOT EXISTS tbl_person (
    person_id BIGINT PRIMARY KEY,
    firstName VARCHAR(80),
    lastName VARCHAR(100),
    username VARCHAR(115),
    password VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS tbl_admin (
    admin_id BIGINT PRIMARY KEY,
    person_id BIGINT,
    id VARCHAR(8)
);

CREATE TABLE IF NOT EXISTS tbl_student (
    student_id BIGINT PRIMARY KEY,
    person_id BIGINT,
    academic_id BIGINT,
    id VARCHAR(8)
);

CREATE TABLE IF NOT EXISTS tbl_professor (
    professor_id BIGINT PRIMARY KEY,
    person_id BIGINT,
    academic_id BIGINT,
    id VARCHAR(8)
);

CREATE TABLE IF NOT EXISTS tbl_academic (
    academic_id BIGINT PRIMARY KEY,
    course_id BIGINT,
    course2_id BIGINT,
    course3_id BIGINT,
    course4_id BIGINT,
    course5_id BIGINT,
    course6_id BIGINT
);

CREATE TABLE IF NOT EXISTS tbl_course (
    course_id BIGINT PRIMARY KEY,
    course_name VARCHAR(8),
    price BIGINT
);
