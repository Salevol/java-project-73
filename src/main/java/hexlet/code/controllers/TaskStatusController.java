package hexlet.code.controllers;

import hexlet.code.dto.TaskStatusDto;
import hexlet.code.model.TaskStatus;
import hexlet.code.service.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${base-url}" + TaskStatusController.TASK_STATUS_CONTROLLER_PATH)
public class TaskStatusController {
    public static final String TASK_STATUS_CONTROLLER_PATH = "/statuses";
    public static final String ID = "/{id}";
    private final TaskStatusService taskStatusService;

    @GetMapping(ID)
    public TaskStatus getById(@PathVariable("id") long id) {
        return taskStatusService.getTaskStatusById(id);
    }

    @GetMapping
    public List<TaskStatus> getAll() {
        return taskStatusService.getAllTaskStatuses();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TaskStatus create(@RequestBody @Valid TaskStatusDto taskStatusDto) {
        return taskStatusService.createTaskStatus(taskStatusDto);
    }

    @PutMapping(ID)
    public TaskStatus update(@PathVariable("id") long id,
                             @RequestBody @Valid TaskStatusDto taskStatusDto) {
        return taskStatusService.updateTaskStatus(id, taskStatusDto);
    }

    @DeleteMapping(ID)
    public void delete(@PathVariable("id") long id) {
        taskStatusService.deleteTaskStatus(id);
    }
}