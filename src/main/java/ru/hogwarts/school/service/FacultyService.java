package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

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
        return this.facultyRepository.findById(id).get();
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

    public Collection<Faculty> getFacultiesByColor(String color) {
        if (color == null || color.isEmpty() || color.isBlank()) {
            throw new IllegalArgumentException("Требуется указать корректный цвет факультета");
        }
        return this.facultyRepository.findByColor(color);
    }
}