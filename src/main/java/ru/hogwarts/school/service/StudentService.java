package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Требуется добавить студента");
        }
        return this.studentRepository.save(student);
    }

    public Student getStudent(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Требуется указать корректный ID студента");
        }
        return this.studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Требуется корректно указать студента");
        }
        return this.studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Требуется указать корректный ID студента");
        }
        this.studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        if (age < 0 || age >= 700) {
            throw new IllegalArgumentException("Требуется указать корректный возраст студента");
        }
        return this.studentRepository.findByAge(age);
    }

    public Collection<Student> getStudentsByAgeBetween(int min, int max) {
        if (min > max || max < 0 || max > 18) {
            throw new IllegalArgumentException("Требуется указать корректные границы возраста студентов");
        }
        return this.studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Faculty getFacultyOfTheStudent(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Требуется указать корректное имя студента");
        }
        return this.studentRepository.findById(id).get().getFaculty();
    }

    public Integer getAllStudentsInSchool() {
        return this.studentRepository.findAllStudents();
    }

    public Integer getAverageAge() {
        return this.studentRepository.findAverageAge();
    }

    public Collection<Student> getLast5StudentsInList() {
        return this.studentRepository.getLast5StudentsInList();
    }
}