package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    @JsonIgnore
    private Collection<Student> students;

    public Faculty(Long id, String name, String color) {
        setId(id);
        setName(name);
        setColor(color);
    }

    public Faculty() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Collection<Student> getAllStudents() {
        return students;
    }

    public void setId(Long id) {
        if (id >= 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Требуется указать корректный ID факультета (>= 0)");
        }
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty() && !name.isBlank()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Требуется указать корректное название факультета");
        }
    }

    public void setColor(String color) {
        if (color != null && !color.isEmpty() && !color.isBlank()) {
            this.color = color;
        } else {
            throw new IllegalArgumentException("Требуется указать корректный цвет факультета");
        }
    }

    public void setStudents(Collection<Student> students) {
        if (students != null) {
            this.students = students;
        } else {
            throw new IllegalArgumentException("Требуется передать коллекцию студентов");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(id, faculty.id) && Objects.equals(name, faculty.name) && Objects.equals(color, faculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    @Override
    public String toString() {
        return "Факультет Хогвартс - " +
                "ID: " + id +
                ", название: " + name +
                ", цвет: " + color;
    }
}
