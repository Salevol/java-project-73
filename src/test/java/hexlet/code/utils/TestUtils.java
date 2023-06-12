package hexlet.code.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.component.JWTHelper;
import hexlet.code.controllers.LabelController;
import hexlet.code.controllers.TaskController;
import hexlet.code.controllers.TaskStatusController;
import hexlet.code.dto.LabelDto;
import hexlet.code.dto.TaskDto;
import hexlet.code.dto.TaskStatusDto;
import hexlet.code.dto.UserDto;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static hexlet.code.controllers.UserController.USER_CONTROLLER_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtils {
    public static final String TEST_EMAIL = "email@email.com";
    public static final String TEST_EMAIL_2 = "email_2@email.com";

    public static final String BASE_URL = "";

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    private final UserDto testDTO = new UserDto(
            "first_name",
            "last_name",
            TEST_EMAIL,
            "pwd"
    );

    public UserDto getTestDTO() {
        return testDTO;
    }

    public static final TaskStatusDto TEST_TASK_STATUS_1 = TaskStatusDto.builder()
            .name("Test task status").build();

    public static final TaskStatusDto TEST_TASK_STATUS_2 = TaskStatusDto.builder()
            .name("New test task status").build();

    public static final LabelDto TEST_LABEL = LabelDto.builder()
            .name("Test label").build();

    public static final LabelDto TEST_LABEL_2 = LabelDto.builder()
            .name("Another test label").build();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private JWTHelper jwtHelper;

    public void tearDown() {
        taskRepository.deleteAll();
        taskStatusRepository.deleteAll();
        labelRepository.deleteAll();
        userRepository.deleteAll();
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).get();
    }

    public ResultActions regUser(final UserDto dto) throws Exception {
        final var request = post(USER_CONTROLLER_PATH)
                .content(asJson(dto))
                .contentType(APPLICATION_JSON);

        return perform(request);
    }

    public ResultActions regDefaultUser() throws Exception {
        return regUser(testDTO);
    }

    public ResultActions perform(final MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request);
    }

    public ResultActions perform(final MockHttpServletRequestBuilder request, final String byUser) throws Exception {
        final String token = jwtHelper.expiring(Map.of("username", byUser));
        request.header(AUTHORIZATION, token);

        return perform(request);
    }

    public static String asJson(final Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(final String json, final TypeReference<T> to) throws JsonProcessingException {
        return MAPPER.readValue(json, to);
    }

    public ResultActions regTask(final TaskDto dto, final String byUser) throws Exception {
        final var request =
                MockMvcRequestBuilders.post(BASE_URL + TaskController.TASK_CONTROLLER_PATH)
                        .content(asJson(dto))
                        .contentType(APPLICATION_JSON);

        return perform(request, byUser);
    }

    public ResultActions regDefaultTask(final String byUser) throws Exception {
        regDefaultUser();
        regDefaultTaskStatus(TEST_EMAIL);
        final User user = userRepository.findAll().get(0);
        final TaskStatus taskStatus = taskStatusRepository.findAll().get(0);
        final TaskDto testRegTaskDto = new TaskDto(
                "Task name",
                "Task description",
                taskStatus.getId(),
                user.getId(),
                null
        );
        return regTask(testRegTaskDto, byUser);
    }

    public ResultActions regDefaultTaskStatus(final String byUser) throws Exception {
        return regTaskStatus(TEST_TASK_STATUS_1, byUser);
    }

    public ResultActions regTaskStatus(final TaskStatusDto taskStatusDto, final String byUser) throws Exception {
        final var request =
                MockMvcRequestBuilders.post(BASE_URL + TaskStatusController.TASK_STATUS_CONTROLLER_PATH)
                        .content(asJson(taskStatusDto))
                        .contentType(APPLICATION_JSON);

        return perform(request, byUser);
    }

    public ResultActions regDefaultLabel(final String byUser) throws Exception {
        return regLabel(TEST_LABEL, byUser);
    }

    public ResultActions regLabel(final LabelDto labelDto, final String byUser) throws Exception {
        final var request =
                MockMvcRequestBuilders.post(BASE_URL + LabelController.LABEL_CONTROLLER_PATH)
                        .content(asJson(labelDto))
                        .contentType(APPLICATION_JSON);

        return perform(request, byUser);
    }
}
