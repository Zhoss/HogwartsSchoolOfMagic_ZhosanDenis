package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.List;

class FacultyServiceTest {
    private final FacultyService out = new FacultyService();
    private final Faculty gryffindor = new Faculty(0L, "Гриффиндор", "Red");
    private final Faculty salazar = new Faculty(0L, "Слизерин", "Green");
    private final Faculty ravenclaw = new Faculty(0L, "Когтевран", "Blue");
    private final Faculty hufflepuff = new Faculty(0L, "Пуффендуй", "Red");

    @Test
    void addFaculty() {
        Faculty newGryffindor = new Faculty(0L, "Гриффиндор", "Red");
        Assertions.assertEquals(newGryffindor.getName(), out.addFaculty(gryffindor).getName());
        Assertions.assertEquals(newGryffindor.getColor(), out.addFaculty(gryffindor).getColor());

        Faculty newRavenclaw = new Faculty(0L, "Когтевран", "Blue");
        Assertions.assertEquals(newRavenclaw.getName(), out.addFaculty(ravenclaw).getName());
        Assertions.assertEquals(newRavenclaw.getColor(), out.addFaculty(ravenclaw).getColor());
    }

    @Test
    void addingNullThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.addFaculty(null));
    }

    @Test
    void getFaculty() {
        out.addFaculty(gryffindor);
        out.addFaculty(salazar);
        out.addFaculty(ravenclaw);
        out.addFaculty(hufflepuff);

        Assertions.assertEquals(gryffindor, out.getFaculty(gryffindor.getId()));
        Assertions.assertEquals(salazar, out.getFaculty(salazar.getId()));
        Assertions.assertEquals(hufflepuff, out.getFaculty(hufflepuff.getId()));
    }

    @Test
    void gettingFacultyWithNegativeIdThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.getFaculty(-10));
    }

    @Test
    void editFaculty() {
        out.addFaculty(gryffindor);

        Faculty oldGryffindor = new Faculty(gryffindor.getId(), "Грифффиндор", "Redd");
        Faculty newGryffindor = new Faculty(gryffindor.getId(), "Грифффиндор", "Redd");

        Assertions.assertEquals(oldGryffindor, out.editFaculty(newGryffindor));
    }

    @Test
    void editingNullThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.editFaculty(null));
    }

    @Test
    void editingNonExistingStudentReturnsNull() {
        out.addFaculty(gryffindor);
        out.addFaculty(salazar);
        out.addFaculty(ravenclaw);
        out.addFaculty(hufflepuff);

        Assertions.assertNull(out.editFaculty(new Faculty(666L, "Пожиратели смерти", "black")));
    }

    @Test
    void deleteFaculty() {
        out.addFaculty(gryffindor);
        out.addFaculty(salazar);
        out.addFaculty(ravenclaw);
        out.addFaculty(hufflepuff);

        out.deleteFaculty(gryffindor.getId());
        out.deleteFaculty(ravenclaw.getId());

        Assertions.assertNull(out.getFaculty(gryffindor.getId()));
        Assertions.assertNull(out.getFaculty(ravenclaw.getId()));
    }

    @Test
    void deletingFacultyWithNegativeIdThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.deleteFaculty(-10));
    }

    @Test
    void getFacultiesByColor() {
        out.addFaculty(gryffindor);
        out.addFaculty(salazar);
        out.addFaculty(ravenclaw);
        out.addFaculty(hufflepuff);

        List<Faculty> redFacultyList = new ArrayList<>();
        redFacultyList.add(gryffindor);
        redFacultyList.add(hufflepuff);

        Assertions.assertEquals(redFacultyList, out.getFacultiesByColor("Red"));
    }

    @Test
    void gettingFacultiesWithNegativeAgesThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.getFacultiesByColor(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.getFacultiesByColor(""));
        Assertions.assertThrows(IllegalArgumentException.class, () -> out.getFacultiesByColor(" "));
    }
}