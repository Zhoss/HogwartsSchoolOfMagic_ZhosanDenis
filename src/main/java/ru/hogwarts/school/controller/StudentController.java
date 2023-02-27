package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student addedStudent = this.service.addStudent(student);
        return ResponseEntity.ok(addedStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student foundStudent = this.service.getStudent(id);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editedStudent = this.service.editStudent(student);
        if (editedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        this.service.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@PathVariable int age) {
        Collection<Student> studentsByAge = this.service.getStudentsByAge(age);
        if (studentsByAge.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentsByAge);
    }

    @GetMapping("age")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(this.service.getStudentsByAgeBetween(min, max));
    }

    @GetMapping("faculty")
    public ResponseEntity<Faculty> getFacultyOfTheStudent(@RequestParam long id) {
        Faculty foundFaculty = this.service.getFacultyOfTheStudent(id);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @GetMapping("students")
    public ResponseEntity<Integer> getAllStudentsInSchool() {
        Integer studentsAmount = this.service.getAllStudentsInSchool();
        if (studentsAmount == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentsAmount);
    }

    @GetMapping("age/average")
    public ResponseEntity<Integer> getAverageAge() {
        Integer studentsAverageAge = this.service.getAverageAge();
        if (studentsAverageAge == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentsAverageAge);
    }

    @GetMapping("students/last5")
    public ResponseEntity<Collection<Student>> getLastStudentsInList() {
        Collection<Student> lastStudents = this.service.getLast5StudentsInList();
        if (lastStudents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lastStudents);
    }

    @GetMapping("students/nameStarts/{letter}")
    public ResponseEntity<Collection<String>> getStudentsWithNamesStartingWithLetter(@PathVariable Character letter) {
        Collection<String> students = this.service.getStudentsWithNamesStartingWithLetter(letter);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("students/age/average")
    public ResponseEntity<Double> getAverageAgeOfAllStudents() {
        Double studentsAverageAge = this.service.getAverageAgeOfAllStudents();
        if (studentsAverageAge == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentsAverageAge);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}