package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private static long facultyCount = 0;

    public Faculty addFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Требуется добавить факультет");
        }
        faculty.setId(++facultyCount);
        this.faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty getFaculty(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Требуется указать корректный ID факультета");
        }
        return this.faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Требуется корректно указать факультет");
        }
        if (this.faculties.containsKey(faculty.getId())) {
            this.faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Требуется указать корректный ID факультета");
        }
        if (this.faculties.containsKey(id)) {
            return this.faculties.remove(id);
        }
        return null;
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        if (color == null || color.isEmpty() || color.isBlank()) {
            throw new IllegalArgumentException("Требуется указать корректный цвет факультета");
        }
        return this.faculties.values().stream().
                filter(e -> e.getColor().equals(color)).
                collect(Collectors.toList());
    }
}