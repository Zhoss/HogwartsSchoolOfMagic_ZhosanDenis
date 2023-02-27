ALTER TABLE students ADD CONSTRAINT student_age_constraint CHECK (age >= 16);

ALTER TABLE students ADD CONSTRAINT unique_student_name UNIQUE (name);
ALTER TABLE students ALTER COLUMN name SET NOT NULL;

ALTER TABLE faculty ADD CONSTRAINT unique_faculty_name_and_color UNIQUE (name, color);

ALTER TABLE students ALTER COLUMN age SET DEFAULT 20;
