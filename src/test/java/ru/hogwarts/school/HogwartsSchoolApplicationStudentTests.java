package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
class HogwartsSchoolApplicationStudentTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    private final long facultyId = 1L;
    private final String facultyName = "Гриффиндор";
    private final String facultyColor = "красный";
    private final Faculty gryffindor = new Faculty(facultyId, facultyName, facultyColor);
    private final long studentId = 1L;
    private final String studentName = "Гарри Поттер";
    private final int studentAge = 12;

    private Student createHarryPotter() {
        Student harry = new Student();
        harry.setId(studentId);
        harry.setName(studentName);
        harry.setAge(studentAge);
        harry.setFaculty(gryffindor);
        return harry;
    }

    private JSONObject createGryffindorJSON(Faculty faculty) throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", faculty.getName());
        facultyObject.put("color", faculty.getColor());
        return facultyObject;
    }

    private JSONObject createHarryPotterJSON(Student student) throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", student.getName());
        studentObject.put("age", student.getAge());
        studentObject.put("faculty", createGryffindorJSON(gryffindor));
        return studentObject;
    }

    @Test
    void addStudentTest() throws Exception {
        Student harry = createHarryPotter();

        JSONObject studentObject = createHarryPotterJSON(harry);

        when(studentRepository.save(any(Student.class))).thenReturn(harry);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.name").value(studentName))
                .andExpect(jsonPath("$.age").value(studentAge));
    }

    @Test
    void getStudentTest() throws Exception {
        Student harry = createHarryPotter();

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(harry));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.name").value(studentName))
                .andExpect(jsonPath("$.age").value(studentAge));
    }

    @Test
    void editStudentTest() throws Exception {
        Student newHarry = new Student(1L, "Драко Малфой", 13, gryffindor);

        JSONObject studentObject = createHarryPotterJSON(newHarry);

        when(studentRepository.save(any(Student.class))).thenReturn(newHarry);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newHarry.getId()))
                .andExpect(jsonPath("$.name").value(newHarry.getName()))
                .andExpect(jsonPath("$.age").value(newHarry.getAge()));
    }

    @Test
    void deleteStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + studentId))
                .andExpect(status().isOk());
        verify(studentService, times(1)).deleteStudent(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void getStudentsByAgeTest() throws Exception {
        int student12YearsAge = 12;
        Student harry = createHarryPotter();
        Student ron = new Student(2L, "Рон Уизли", 12, gryffindor);

        List<Student> students12Years = new ArrayList<>();
        students12Years.add(harry);
        students12Years.add(ron);

        when(studentRepository.findByAge(any(int.class))).thenReturn(students12Years);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/" + student12YearsAge)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(studentId))
                .andExpect(jsonPath("$[0].name").value(studentName))
                .andExpect(jsonPath("$[0].age").value(studentAge))
                .andExpect(jsonPath("$[1].id").value(ron.getId()))
                .andExpect(jsonPath("$[1].name").value(ron.getName()))
                .andExpect(jsonPath("$[1].age").value(ron.getAge()));
    }

    @Test
    void getStudentsByAgeBetweenTest() throws Exception {
        int min = 11;
        int max = 13;
        Student harry = createHarryPotter();
        Student ron = new Student(2L, "Рон Уизли", 12, gryffindor);

        List<Student> students12Years = new ArrayList<>();
        students12Years.add(harry);
        students12Years.add(ron);

        when(studentRepository.findStudentsByAgeBetween(any(int.class), any(int.class))).thenReturn(students12Years);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?min=" + min + "&max=" + max)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(studentId))
                .andExpect(jsonPath("$[0].name").value(studentName))
                .andExpect(jsonPath("$[0].age").value(studentAge))
                .andExpect(jsonPath("$[1].id").value(ron.getId()))
                .andExpect(jsonPath("$[1].name").value(ron.getName()))
                .andExpect(jsonPath("$[1].age").value(ron.getAge()));
    }

    @Test
    void getFacultyOfTheStudentTest() throws Exception {
        Student harry = createHarryPotter();
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(harry));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty?id=" + studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor));
    }
}
