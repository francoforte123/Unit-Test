package Unit.Test;

import Unit.Test.Controller.PersonController;
import Unit.Test.Entities.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    PersonController userController;

    @Test
    void contextLoads() {
        assertThat(userController).isNotNull();
    }

    @Test
    void createUserTest() throws Exception {
        Person postUser = createPostRequest();
        assertThat(postUser).isNotNull();
        Person getUser = getUserById(postUser.getId());
        assertThat(getUser).isNotNull();
        assertThat(getUser.getName()).isEqualTo(postUser.getName());
        assertThat(getUser.getName()).isEqualTo(postUser.getName());
    }

    @Test
    void getAllUsersTest() throws Exception {
        createPostRequest();
        createPostRequest();
        createPostRequest();
        MvcResult result = this.mockMvc.perform(get("/users/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<Person> studentsFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        System.out.println("Students in database are: " + studentsFromResponse.size());
        assertThat(studentsFromResponse.size()).isEqualTo(3);
    }

    @Test
    void updateStudent() throws Exception {
        Person user = createPostRequest();
        String newName = "Mario";
        assertThat(user).isNotNull();
        user.setName(newName);
        String studentJSON = objectMapper.writeValueAsString(user);
        mockMvc.perform(put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Person studentFromResponseGet = getUserById(user.getId());
        assertThat(studentFromResponseGet).isNotNull();
        assertThat(studentFromResponseGet.getName()).isEqualTo(newName);
    }

    @Test
    void deleteStudent() throws Exception {
        Person user = createPostRequest();
        assertThat(user).isNotNull();
        mockMvc.perform(delete("/users/" + user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Person getUser = getUserById(user.getId());
        assertThat(getUser).isNull();
    }

    private Person getUserById(Long id) throws Exception {
        MvcResult result = mockMvc.perform(get("/users/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String userJSON = result.getResponse().getContentAsString();
        if (userJSON.isEmpty()) return null;
        return objectMapper.readValue(userJSON, Person.class);
    }

    private Person createPostRequest() throws Exception {
        Person user = createStudent();
        String userJSON = objectMapper.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        userJSON = result.getResponse().getContentAsString();
        return objectMapper.readValue(userJSON, Person.class);
    }


    private Person createStudent() throws Exception {
        List<String> names = List.of("Mario", "Marco", "Franco", "Sofia");
        List<String> surnames = List.of("Rossi", "Giallo", "Marrone", "Celeste");
        Person user = new Person();
        Random random = new Random();
        user.setName(names.get(random.nextInt(4)));
        user.setSurname(surnames.get(random.nextInt(4)));
        return user;
    }
}
