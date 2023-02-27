package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HogwartsSchoolApplicationFacultyTests {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;
    private final long facultyId = 1L;
    private final String facultyName = "Гриффиндор";
    private final String facultyColor = "красный";

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    private ResponseEntity<Faculty> createFacultyGryffindor() {
        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);
        faculty.setStudents(new ArrayList<>());

        return this.restTemplate.postForEntity(
                "http://localhost:" + this.port + "/faculty", faculty, Faculty.class);
    }

    @Test
    void testAddFaculty() throws Exception {
        ResponseEntity<Faculty> response = createFacultyGryffindor();

        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isNotNull();
        Assertions
                .assertThat(response.getBody().getName()).isEqualTo(facultyName);
        Assertions
                .assertThat(response.getBody().getColor()).isEqualTo(facultyColor);
        Assertions
                .assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void testGetFaculty() throws Exception {
        Faculty createdFaculty = createFacultyGryffindor().getBody();

        assert createdFaculty != null;
        ResponseEntity<Faculty> response = this.restTemplate.getForEntity(
                "http://localhost:" + this.port + "/faculty/" + createdFaculty.getId(), Faculty.class);

        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isNotNull();
        Assertions
                .assertThat(response.getBody().getName()).isEqualTo(facultyName);
        Assertions
                .assertThat(response.getBody().getColor()).isEqualTo(facultyColor);
        Assertions
                .assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void testEditFaculty() throws Exception {
        Faculty createdFaculty = createFacultyGryffindor().getBody();

        assert createdFaculty != null;
        createdFaculty.setName("Слизерин");
        createdFaculty.setColor("зеленый");

        this.restTemplate.put("http://localhost:" + this.port + "/faculty", createdFaculty, Faculty.class);

        ResponseEntity<Faculty> response = this.restTemplate.getForEntity(
                "http://localhost:" + this.port + "/faculty/" + createdFaculty.getId(), Faculty.class);

        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isNotNull();
        Assertions
                .assertThat(response.getBody().getName()).isEqualTo(createdFaculty.getName());
        Assertions
                .assertThat(response.getBody().getColor()).isEqualTo(createdFaculty.getColor());
        Assertions
                .assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void testDeleteFaculty() throws Exception {
        Faculty createdFaculty = createFacultyGryffindor().getBody();

        assert createdFaculty != null;
        this.restTemplate.delete("http://localhost:" + this.port + "/faculty/" + createdFaculty.getId());

        ResponseEntity<Faculty> response = this.restTemplate.getForEntity(
                "http://localhost:" + this.port + "/faculty/" + createdFaculty.getId(), Faculty.class);

        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions
                .assertThat(response.getBody()).isNull();
    }

    @Test
    void testGetFacultyByColorOrName() {
        ResponseEntity<Faculty> gryffindor = createFacultyGryffindor();

        Faculty slytherin = new Faculty(2L, "Слизерин", "зеленый");
        slytherin.setStudents(new ArrayList<>());
        this.restTemplate.postForEntity(
                "http://localhost:" + this.port + "/faculty", slytherin, Faculty.class);

        ResponseEntity<Faculty> responseGryffindor = this.restTemplate.exchange(
                "http://localhost:" + this.port + "/faculty/color?color={color}",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                },
                Objects.requireNonNull(gryffindor.getBody()).getColor());

        Assertions
                .assertThat(responseGryffindor.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(Objects.requireNonNull(responseGryffindor.getBody()).getName()).isEqualTo(gryffindor.getBody().getName());
        Assertions
                .assertThat(responseGryffindor.getBody()).isNotNull();
        Assertions
                .assertThat(responseGryffindor.getBody().getName()).isEqualTo(gryffindor.getBody().getName());
        Assertions
                .assertThat(responseGryffindor.getBody().getColor()).isEqualTo(gryffindor.getBody().getColor());
        Assertions
                .assertThat(responseGryffindor.getBody().getId()).isNotNull();


        ResponseEntity<Faculty> responseSlytherin = this.restTemplate.exchange(
                "http://localhost:" + this.port + "/faculty/color?color={color}",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                },
                slytherin.getColor());

        Assertions
                .assertThat(responseSlytherin.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(Objects.requireNonNull(responseSlytherin.getBody()).getName()).isEqualTo(slytherin.getName());
        Assertions
                .assertThat(responseSlytherin.getBody()).isNotNull();
        Assertions
                .assertThat(responseSlytherin.getBody().getName()).isEqualTo(slytherin.getName());
        Assertions
                .assertThat(responseSlytherin.getBody().getColor()).isEqualTo(slytherin.getColor());
        Assertions
                .assertThat(responseSlytherin.getBody().getId()).isNotNull();
    }

    @Test
    void testFindAllStudents() {
        Faculty gryffindor = new Faculty(facultyId, facultyName, facultyColor);
        Student harry = new Student(1L, "Harry Potter", 12, gryffindor);
        List<Student> students = new ArrayList<>(List.of(harry));

        Faculty createdFaculty = facultyRepository.save(gryffindor);
        studentRepository.save(harry);

        ResponseEntity<List<Student>> response = this.restTemplate.exchange(
                "http://localhost:" + this.port + "/faculty/students?id={id}",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                },
                createdFaculty.getId());

        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isNotNull();
        Assertions
                .assertThat(response.getBody()).isNotEmpty();
        Assertions
                .assertThat(response.getBody()).isEqualTo(students);

        studentRepository.deleteAll();
    }
}
