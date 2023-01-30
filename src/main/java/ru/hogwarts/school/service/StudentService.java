package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private static long studentCount = 0;

    public Student addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Требуется добавить студента");
        }
        student.setId(++studentCount);
        this.students.put(student.getId(), student);
        return student;
    }

    public Student getStudent(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Требуется указать корректный ID студента");
        }
        return this.students.get(id);
    }

    public Student editStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Требуется корректно указать студента");
        }
        if (this.students.containsKey(student.getId())) {
            this.students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Требуется указать корректный ID студента");
        }
        if (this.students.containsKey(id)) {
            return this.students.remove(id);
        }
        return null;
    }

    public Collection<Student> getStudentsByAge(int age) {
        if (age < 0 || age >= 700) {
            throw new IllegalArgumentException("Требуется указать корректный возраст студента");
        }
        return this.students.values().stream().
                filter(e -> e.getAge() == age).
                collect(Collectors.toList());
    }
}