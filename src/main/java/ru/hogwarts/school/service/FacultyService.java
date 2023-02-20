package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Objects;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Требуется добавить факультет");
        }
        return this.facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Требуется указать корректный ID факультета");
        }
        return this.facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Требуется корректно указать факультет");
        }
        return this.facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Требуется указать корректный ID факультета");
        }
        this.facultyRepository.deleteById(id);
    }

    public Faculty getFacultyByColor(String color) {
        if (color.isEmpty() || color.isBlank()) {
            throw new IllegalArgumentException("Требуется указать корректный цвет факультета");
        }
        return this.facultyRepository.findByColorIgnoreCase(color);
    }

    public Faculty getFacultyByName(String name) {
        if (name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Требуется указать корректное имя факультета");
        }
        return this.facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Student> findAllStudents(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Требуется указать корректный id факультета");
        }
        return Objects.requireNonNull(this.facultyRepository.findById(id).orElse(new Faculty())).getStudents();
    }
}