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
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty addedFaculty = this.service.addFaculty(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty foundFaculty = this.service.getFaculty(id);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editedFaculty = this.service.editFaculty(faculty);
        if (editedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editedFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable long id) {
        this.service.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("color")
    public ResponseEntity<Faculty> getFacultyByColorOrName(@RequestParam(required = false) String color,
                                                           @RequestParam(required = false) String name) {
        if (color != null) {
            if (this.service.getFacultyByColor(color) == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(this.service.getFacultyByColor(color));
        }
        if (name != null) {
            if (this.service.getFacultyByName(name) == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(this.service.getFacultyByName(name));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("students")
    public ResponseEntity<Collection<Student>> findAllStudents(String name) {
        if (this.service.findAllStudents(name).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.service.findAllStudents(name));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}