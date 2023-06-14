package hexlet.code.service.impl;

import com.querydsl.core.types.Predicate;
import hexlet.code.dto.TaskDto;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.service.LabelService;
import hexlet.code.service.TaskService;
import hexlet.code.service.TaskStatusService;
import hexlet.code.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskStatusService taskStatusService;
    private final LabelService labelService;
    private final UserService userService;

    @Override
    public Task getTaskById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getAllTasks(Predicate predicate) {
        return StreamSupport.stream(taskRepository.findAll(predicate).spliterator(), false)
                .toList();
    }

    @Override
    public Task createTask(TaskDto taskDto) {
        User executor = null;
        if (Objects.nonNull(taskDto.getExecutorId())) {
            executor = userService.getUserById(taskDto.getExecutorId());
        }
        List<Label> labels = null;
        if (Objects.nonNull(taskDto.getLabelIds()) && taskDto.getLabelIds().size() > 0) {
            labels = taskDto.getLabelIds().stream()
                    .map(labelService::getLabelById)
                    .toList();
        }

        Task task = Task.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .taskStatus(taskStatusService.getTaskStatusById(taskDto.getTaskStatusId()))
                .author(userService.getCurrentUser())
                .executor(executor)
                .labels(labels)
                .build();

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(long id, TaskDto taskDto) {
        Task task = taskRepository.getById(id);
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setTaskStatus(taskStatusService.getTaskStatusById(taskDto.getTaskStatusId()));

        Long executorId = taskDto.getExecutorId();
        if (executorId != null) {
            task.setExecutor(userService.getUserById(executorId));
        }

        List<Label> labels = null;
        if (Objects.nonNull(taskDto.getLabelIds()) && taskDto.getLabelIds().size() > 0) {
            labels = taskDto.getLabelIds().stream()
                    .map(labelService::getLabelById)
                    .toList();
        }

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.delete(getTaskById(id));
    }
}
