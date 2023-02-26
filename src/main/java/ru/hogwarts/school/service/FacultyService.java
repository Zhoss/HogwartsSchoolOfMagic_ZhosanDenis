package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Objects;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final static Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        if (faculty == null) {
            logger.error("Input object 'faculty' is null");
            throw new IllegalArgumentException("Требуется добавить факультет");
        }
        logger.info("Was invoked method for create faculty");
        return this.facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        if (id < 0) {
            logger.error("Input id = " + id + " for getting faculty is incorrect");
            throw new IllegalArgumentException("Требуется указать корректный ID факультета");
        }
        logger.info("Was invoked method for getting faculty");
        return this.facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculty == null) {
            logger.error("Input object 'faculty' is null");
            throw new IllegalArgumentException("Требуется корректно указать факультет");
        }
        logger.info("Was invoked method for edit faculty");
        return this.facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        if (id < 0) {
            logger.error("Input id = " + id + " for delete faculty is incorrect");
            throw new IllegalArgumentException("Требуется указать корректный ID факультета");
        }
        logger.info("Was invoked method for delete faculty");
        this.facultyRepository.deleteById(id);
    }

    public Faculty getFacultyByColor(String color) {
        if (color == null) {
            logger.warn("Input color is null. Method can provide exception");
        }
        if (color.isEmpty() || color.isBlank()) {
            logger.error("Input color is empty or has incorrect value");
            throw new IllegalArgumentException("Требуется указать корректный цвет факультета");
        }
        logger.info("Was invoked method for getting faculty by color");
        return this.facultyRepository.findByColorIgnoreCase(color);
    }

    public Faculty getFacultyByName(String name) {
        if (name == null) {
            logger.warn("Input name is null. Method can provide exception");
        }
        if (name.isEmpty() || name.isBlank()) {
            logger.error("Input name is empty or has incorrect value");
            throw new IllegalArgumentException("Требуется указать корректное имя факультета");
        }
        logger.info("Was invoked method for getting faculty by name");
        return this.facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Student> findAllStudents(long id) {
        if (id < 0) {
            logger.error("Input id = " + id + " for getting all students of faculty is incorrect");
            throw new IllegalArgumentException("Требуется указать корректный id факультета");
        }
        logger.info("Was invoked method for getting all students of faculty");
        return Objects.requireNonNull(this.facultyRepository.findById(id).orElse(new Faculty())).getStudents();
    }
}