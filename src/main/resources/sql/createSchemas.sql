CREATE SCHEMA university_schemas;

GRANT SELECT ON ALL SEQUENCES IN SCHEMA university_schemas TO university;
GRANT SELECT ON ALL TABLES IN SCHEMA university_schemas TO university;
ALTER DEFAULT PRIVILEGES IN SCHEMA university_schemas GRANT SELECT ON TABLES TO university;


--student--------------------------------------------------------------------------
drop SEQUENCE university_schemas.student_seq;
CREATE SEQUENCE university_schemas.student_seq START 1;

drop TABLE university_schemas.student;
CREATE TABLE university_schemas.student (
               id bigint NOT NULL PRIMARY KEY DEFAULT nextval('university_schemas.student_seq'::regclass),
               student_id bigint NOT NULL,
               name varchar(64) NOT NULL,
               surname varchar(64) NOT NULL,
               dateBirth date
);

drop index university_schemas.student_id_idx;
create unique index student_id_idx  on university_schemas.student(id);
create unique index name_surname_id_idx  on university_schemas.student(name,surname);

--student--------------------------------------------------------------------------
drop SEQUENCE university_schemas.student_group_seq;
CREATE SEQUENCE university_schemas.student_group_seq START 1;

drop TABLE university_schemas.student_group;
CREATE TABLE university_schemas.student_group (
                                            id bigint NOT NULL PRIMARY KEY DEFAULT nextval('university_schemas.student_group_seq'::regclass),
                                            group_id bigint NOT NULL,
                                            name varchar(64) NOT NULL,
                                            university_id bigint NOT NULL,
                                            dateBirth date
);

drop index university_schemas.student_group_id_idx;
create unique index student_group_id_idx  on university_schemas.student_group(id);

--university--------------------------------------------------------------------------
drop SEQUENCE university_schemas.university_seq;
CREATE SEQUENCE university_schemas.university_seq START 1;

drop TABLE university_schemas.university;
CREATE TABLE university_schemas.university (
                                                  id bigint NOT NULL PRIMARY KEY DEFAULT nextval('university_schemas.student_group_seq'::regclass),
                                                  university_id bigint NOT NULL,
                                                  name varchar(64) NOT NULL

);

drop index university_schemas.university_id_idx;
create unique index university_id_idx  on university_schemas.university(id);
create unique index university_un_id_idx  on university_schemas.university(university_id);


select * from university_schemas.university;
select * from university_schemas.student_group;
select * from university_schemas.student;
