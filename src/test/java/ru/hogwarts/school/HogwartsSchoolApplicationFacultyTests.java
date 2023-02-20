package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

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
    private TestRestTemplate restTemplate;
    private final long facultyId = 1L;
    private final String facultyName = "Гриффиндор";
    private final String facultyColor = "красный";

    @AfterEach
    public void afterEach() {
        facultyRepository.deleteAll();
    }

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
        createFacultyGryffindor();

        ResponseEntity<Faculty> response = this.restTemplate.getForEntity(
                "http://localhost:" + this.port + "/faculty/" + facultyId, Faculty.class);

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
        createFacultyGryffindor();

        Faculty newFaculty = new Faculty();
        newFaculty.setId(1L);
        newFaculty.setName("Слизерин");
        newFaculty.setColor("Зеленый");
        newFaculty.setStudents(new ArrayList<>());

        this.restTemplate.put("http://localhost:" + this.port + "/faculty", newFaculty, Faculty.class);

        ResponseEntity<Faculty> response = this.restTemplate.getForEntity(
                "http://localhost:" + this.port + "/faculty/" + facultyId, Faculty.class);

        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isNotNull();
        Assertions
                .assertThat(response.getBody().getName()).isEqualTo(newFaculty.getName());
        Assertions
                .assertThat(response.getBody().getColor()).isEqualTo(newFaculty.getColor());
        Assertions
                .assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void testDeleteFaculty() throws Exception {
        createFacultyGryffindor();

        this.restTemplate.delete("http://localhost:" + this.port + "/faculty/" + facultyId);

        ResponseEntity<Faculty> response = this.restTemplate.getForEntity(
                "http://localhost:" + this.port + "/faculty/" + facultyId, Faculty.class);

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
        ResponseEntity<Faculty> facultyResponseEntity = createFacultyGryffindor();
        Faculty gryffindor = facultyResponseEntity.getBody();
        System.out.println(gryffindor);

        Student harry = new Student(1L, "Harry Potter", 12, gryffindor);
        Student ron = new Student(2L, "Ron Weasley", 12, gryffindor);

        assert gryffindor != null;
        gryffindor.setStudents(new ArrayList<>(List.of(harry, ron)));
        System.out.println(gryffindor);

        List<Student> students = new ArrayList<>(List.of(harry, ron));
        System.out.println(students);

        Long id = facultyId;
        ParameterizedTypeReference<List<Student>> typeRef = new ParameterizedTypeReference<List<Student>>() {
        };
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<Student>> response = this.restTemplate.exchange(
                "http://localhost:" + this.port + "/faculty/students?id={id}",
                HttpMethod.GET,
                httpEntity,
                typeRef,
                id.toString());

        System.out.println(response);

        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isNotNull();
        Assertions
                .assertThat(response.getBody()).isNotEmpty();
        Assertions
                .assertThat(response.getBody()).isEqualTo(students);
    }
}
