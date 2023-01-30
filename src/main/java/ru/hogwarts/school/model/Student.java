package ru.hogwarts.school.model;

import java.util.Objects;

public class Student {
    private Long id;
    private String name;
    private int age;

    public Student(Long id, String name, int age) {
        setId(id);
        setName(name);
        setAge(age);
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
        if (age > 0 && age < 700) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Требуется указать корректный возраст студента");
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
