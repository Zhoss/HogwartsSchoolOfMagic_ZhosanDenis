package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

class StudentServiceTest {
    private final StudentService out = new StudentService();
    private final Student harry = new Student(0L, "Гарри Поттер", 13);
    private final Student hermione = new Student(0L, "Гермиона Грейнджер", 11);
    private final Student ron = new Student(0L, "Рон Уизли", 12);
    private final Student draco = new Student(0L, "Драко Малфой", 12);
    private final Student nevil = new Student(0L, "Невил Долгопупс", 11);

    @Test
    void addStudent() {
        Student newHarry = new Student(1L, "Гарри Поттер", 13);
        Assertions.assertEquals(newHarry.getName(), out.addStudent(harry).getName());
        Assertions.assertEquals(newHarry.getAge(), out.addStudent(harry).getAge());

        Student newDraco = new Student(2L, "Драко Малфой", 12);
        Assertions.assertEquals(newDraco.getName(), out.addStudent(draco).getName());
        Assertions.assertEquals(newDraco.getAge(), out.addStudent(draco).getAge());
    }

    @Test
    void addingNullThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.addStudent(null));
    }

    @Test
    void getStudent() {
        out.addStudent(harry);
        out.addStudent(hermione);
        out.addStudent(ron);
        out.addStudent(draco);
        out.addStudent(nevil);

        Assertions.assertEquals(hermione, out.getStudent(hermione.getId()));
        Assertions.assertEquals(ron, out.getStudent(ron.getId()));
        Assertions.assertEquals(nevil, out.getStudent(nevil.getId()));
    }

    @Test
    void gettingStudentWithNegativeIdThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.getStudent(-10));
    }

    @Test
    void editStudent() {
        out.addStudent(harry);

        Student harryPotter = new Student(harry.getId(), "Гари Потер", 21);
        Student newHarry = new Student(harry.getId(), "Гари Потер", 21);

        Assertions.assertEquals(harryPotter, out.editStudent(newHarry));
    }

    @Test
    void editingNullThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.editStudent(null));
    }

    @Test
    void editingNonExistingStudentReturnsNull() {
        out.addStudent(harry);
        out.addStudent(hermione);
        out.addStudent(ron);
        out.addStudent(draco);
        out.addStudent(nevil);

        Assertions.assertNull(out.editStudent(new Student(666L, "Волан-де-Морт", 100)));
    }

    @Test
    void deleteStudent() {
        out.addStudent(harry);
        out.addStudent(hermione);
        out.addStudent(ron);
        out.addStudent(draco);
        out.addStudent(nevil);

        out.deleteStudent(draco.getId());
        out.deleteStudent(ron.getId());

        Assertions.assertNull(out.getStudent(draco.getId()));
        Assertions.assertNull(out.getStudent(ron.getId()));
    }

    @Test
    void deletingStudentWithNegativeIdThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.deleteStudent(-10));
    }

    @Test
    void getStudentsByAge() {
        out.addStudent(harry);
        out.addStudent(hermione);
        out.addStudent(ron);
        out.addStudent(draco);
        out.addStudent(nevil);

        List<Student> students11Age = new ArrayList<>();
        students11Age.add(hermione);
        students11Age.add(nevil);

        Assertions.assertEquals(students11Age, out.getStudentsByAge(11));
    }

    @Test
    void gettingStudentsWithNegativeAgesThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.getStudentsByAge(-10));
    }
}