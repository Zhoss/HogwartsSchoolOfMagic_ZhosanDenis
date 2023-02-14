package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Objects;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @JsonBackReference
    private Faculty faculty;

    public Student(Long id, String name, int age, Faculty faculty) {
        setId(id);
        setName(name);
        setAge(age);
        setFaculty(faculty);
    }

    public Student() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setId(Long id) {
        if (id >= 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Требуется указать корректный ID студента (> 0)");
        }
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty() && !name.isBlank()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Требуется указать корректное имя студента");
        }
    }

    public void setAge(int age) {
        if (age > 0 && age <= 18) { //Изначально 0 не был лишним - дабы задать верхнюю границу я нашел возраст самого старого
            // персонажа вселенной. Но ты прав - в рамках возраста именно студентов корректно использовать возраст 18
            this.age = age;
        } else {
            throw new IllegalArgumentException("Требуется указать корректный возраст студента");
        }
    }

    public void setFaculty(Faculty faculty) {
        if (faculty != null) {
            this.faculty = faculty;
        } else {
            throw new IllegalArgumentException("Требуется указать корректный факультет студента");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(id, student.id) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "Студент Хогвартс - " +
                "ID: " + id +
                ", имя: " + name +
                ", возраст: " + age;
    }
}
