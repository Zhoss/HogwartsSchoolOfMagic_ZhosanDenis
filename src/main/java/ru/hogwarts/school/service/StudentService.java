package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        if (student == null) {
            logger.error("Input object 'student' is null");
            throw new IllegalArgumentException("Требуется добавить студента");
        }
        logger.info("Was invoked method for create student");
        return this.studentRepository.save(student);
    }

    public Student getStudent(long id) {
        if (id < 0) {
            logger.error("Input id = " + id + " for getting student is incorrect");
            throw new IllegalArgumentException("Требуется указать корректный ID студента");
        }
        logger.info("Was invoked method for getting student");
        return this.studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        if (student == null) {
            logger.error("Input object 'student' is null");
            throw new IllegalArgumentException("Требуется корректно указать студента");
        }
        logger.info("Was invoked method for edit student");
        return this.studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        if (id < 0) {
            logger.error("Input id = " + id + " for delete student is incorrect");
            throw new IllegalArgumentException("Требуется указать корректный ID студента");
        }
        logger.info("Was invoked method for delete student");
        this.studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        if (age < 0 || age >= 18) {
            logger.error("Input age is out of range");
            throw new IllegalArgumentException("Требуется указать корректный возраст студента");
        }
        logger.info("Was invoked method for getting student by age");
        return this.studentRepository.findByAge(age);
    }

    public Collection<Student> getStudentsByAgeBetween(int min, int max) {
        if (min > max || max < 0 || max > 18) {
            logger.error("Input min or max is out of range");
            throw new IllegalArgumentException("Требуется указать корректные границы возраста студентов");
        }
        logger.info("Was invoked method for getting student by age between min and max values");
        return this.studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Faculty getFacultyOfTheStudent(long id) {
        if (id < 0) {
            logger.error("Input id = " + id + " for getting student's faculty is incorrect");
            throw new IllegalArgumentException("Требуется указать корректное id студента");
        }
        logger.info("Was invoked method for getting student's faculty");
        return Objects.requireNonNull(this.studentRepository.findById(id).orElse(null)).getFaculty();
    }

    public Integer getAllStudentsInSchool() {
        logger.info("Was invoked method for getting number of all students in school");
        return this.studentRepository.findNumberOfAllStudents();
    }

    public Double getAverageAge() {
        logger.info("Was invoked method for getting average age of all students in school");
        return this.studentRepository.findAverageAge();
    }

    public Collection<Student> getLast5StudentsInList() {
        logger.info("Was invoked method for getting last 5 students in school (by id)");
        return this.studentRepository.getLast5StudentsInList();
    }

    public Collection<String> getStudentsWithNamesStartingWithLetter(Character letter) {
        return this.studentRepository.findAll().stream()
                .filter(s -> s.getName().charAt(0) == letter)
                .map(s -> s.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getAverageAgeOfAllStudents() {
        return this.studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average().orElse(0);
    }

    public void printAllStudentsInThreads() {
        List<Student> students = this.studentRepository.findAll();

        printStudent(students.get(0));
        printStudent(students.get(1));

        new Thread(() -> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        }).start();

        new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        }).start();
    }

    public synchronized void printAllStudentsInThreadsSynchronized() {
        List<Student> students = this.studentRepository.findAll();

        printStudentSynchronized(students.get(0));
        printStudentSynchronized(students.get(1));

        new Thread(() -> {
            printStudentSynchronized(students.get(2));
            printStudentSynchronized(students.get(3));
        }).start();

        new Thread(() -> {
            printStudentSynchronized(students.get(4));
            printStudentSynchronized(students.get(5));
        }).start();
    }

    private void printStudent(Student student) {
        System.out.println("Студент " + student.getId() + " - " + student.getName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }

    private synchronized void printStudentSynchronized(Student student) {
        System.out.println("Студент " + student.getId() + " - " + student.getName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
}