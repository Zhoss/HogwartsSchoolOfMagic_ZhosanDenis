-- liquibase formatted sql

-- changeset dzhosan:1
CREATE INDEX student_name_index
ON students (name);

-- changeset dzhosan:2
CREATE INDEX faculty_name_and_color_index
ON faculty (name, color);
