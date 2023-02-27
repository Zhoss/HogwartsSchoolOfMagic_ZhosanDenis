SELECT students.name, students.age, faculty.name
FROM students
LEFT JOIN faculty ON students.faculty_id = faculty.id;

SELECT students.name, students.age, avatar.file_path
FROM students
INNER JOIN avatar ON students.id = avatar.student_id;