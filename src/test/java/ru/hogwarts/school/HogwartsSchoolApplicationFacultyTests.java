package ru.hogwarts.school;

import net.minidev.json.JSONObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

import java.util.Objects;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HogwartsSchoolApplicationFacultyTests {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;
    private final long facultyId = 5L;
    private final String facultyName = "Грифффффиндор";
    private final String facultyColor = "крассссный";

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void testAddFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        String response = this.restTemplate.postForObject("http://localhost:" + this.port + "/faculty", faculty, String.class);

        Assertions
                .assertThat(response)
                .isNotNull();

        System.out.println(response);
    }

    @Test
    void testGetFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(this.facultyId);
        faculty.setName(this.facultyName);
        faculty.setColor(this.facultyColor);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", facultyName);
        facultyObject.put("color", facultyColor);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + this.port + "/faculty/" + facultyId, String.class))
                .isEqualTo(facultyObject.toString());
    }

    @Test
    void testEditFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(this.facultyId);
        faculty.setName(this.facultyName);
        faculty.setColor(this.facultyColor);

        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);

        ResponseEntity<Faculty> response =
                this.restTemplate.exchange("http://localhost:" + this.port + "/faculty" + facultyId, HttpMethod.PUT, entity, Faculty.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo(facultyName);
    }

    @Test
    void testDeleteFaculty() throws Exception {
        this.restTemplate.delete("http://localhost:" + this.port + "/faculty" + facultyId);
    }
}
